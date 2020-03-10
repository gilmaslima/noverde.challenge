Desafio Noverde - Engenheiro de Software Senior
===============================================

## Overview
Aplicação backend para processar as requisições de crédito.
[Detalhes](https://github.com/noverde/challenge/blob/master/README.md)


## Tecnologia utilizada

Linguagem:
* Java 8 

Frameworks:
* Spring Boot - utilizado para injeção de dependências e inversão de controle, camada rest, acesso ao banco de dados e Log de aplicação.
* Lombok - declaração de Get/Set, construtores e builders para as classes Java.
* Swaggwer - documentação dos endpoints rest.
* Mockito - integrado com o Spring-boot-test para Mock e Verify de teste unitários.

Banco de dados:
* MySql em ambiente produtivo.
* H2 em memória para ambiente local.

Build:
* Maven - foi utilizado na estrutura de projeto e empacotamento do artefato de deploy (jar)

Cobertura de código:
* Jacoco - integrado ao build do maven provê um relatório de percentual de cobertura de teste do projeto


## Camadas da aplicação
A aplicação segue uma divisão por pacotes conforme abaixo:
* ``br.com.noverde.challenge`` - pacote raíz da aplicação, classes:
[Application](https://github.com/gilmaslima/noverde.challenge/blob/master/src/main/java/br/com/noverde/challenge/Application.java) - Responsável por iniciar a aplição e o contexto do Spring.

* ``br.com.noverde.challenge.controller`` - nesse pacote estão as classes Rest Controller:
[LoanController](https://github.com/gilmaslima/noverde.challenge/blob/master/src/main/java/br/com/noverde/challenge/controller/LoanController.java)
- método createLoan 
  > disponibiliza o endpoint rest POST /loan
  > requisição de exemplo: 
  > {
  >  "name": "João do Teste",
  >  "cpf": "12345678901",
  >  "birthdate": "10/03/2000",
  >  "amount": 4000,
  >  "terms": 6,
  >  "income": 1000
  >	}
  >
  > possíveis respostas:
  > Código HTTP 201 salvo com sucesso
  > Código HTTP 400 request com campos inválidos
  > Código HTTP 500 quando houver um erro inesperado na aplicação

- método getLoan
  > disponibilizao o endpoint GET /loan/{id}
  > é passado o id no path
  > exemplo de resposta:
  > {
  >   "id": "41117444-7384-4d83-bd03-f145ff8c1207",
  >  "status": "completed",
  >  "result": "approved",
  >  "refused_policy": null,
  >  "amount": 4000.00,
  >  "terms": 9
  >	}
  > Código HTTP 200 ao retornar o json
  > Código HTTP 500 quando houver um erro inesperado na aplicação na requisição

* ``br.com.noverde.challenge.utils`` - nesse pacote está a classe 
[CommitmentUtils](https://github.com/gilmaslima/noverde.challenge/blob/master/src/main/java/br/com/noverde/challenge/utils/CommitmentUtils.java) responsável pelo cálculo de parcela.


* ``br.com.noverde.challenge.model`` - nesse pacote estão as classes que representam os objetos de request e response:
[Error](https://github.com/gilmaslima/noverde.challenge/blob/master/src/main/java/br/com/noverde/challenge/model/Error.java) - Representa o objeto de erro utilizado na resposta 
[LoanRequest](https://github.com/gilmaslima/noverde.challenge/blob/master/src/main/java/br/com/noverde/challenge/model/LoanRequest.java) - representa o objeto/json usado na requisição do endpoint POST /loan.
[LoanResponse](https://github.com/gilmaslima/noverde.challenge/blob/master/src/main/java/br/com/noverde/challenge/model/LoanResponse.java) - representa o objeto usado no retorno da consulta GET /loan/{id}.
[NoverdeRequest](https://github.com/gilmaslima/noverde.challenge/blob/master/src/main/java/br/com/noverde/challenge/model/NoverdeRequest.java) - representa o objeto/json usado na requisição dos endpoint de consulta (commitment e score).
[NoverdeResponse](https://github.com/gilmaslima/noverde.challenge/blob/master/src/main/java/br/com/noverde/challenge/model/NoverdeResponse.java) - representa o objeto/json de resposta da requisição dos endpoint de consulta (commitment e score).


* ``br.com.noverde.challenge.entity`` - Mapeamento ORM:
[Loan](https://github.com/gilmaslima/noverde.challenge/blob/master/src/main/java/br/com/noverde/challenge/entity/Loan.java) - Classe que mapeia a tabela de banco de dados Loan. 


* ``br.com.noverde.challenge.repository`` - contém os repositórios de acesso ao banco de dados da aplicação:
[LoanRepository](https://github.com/gilmaslima/noverde.challenge/blob/master/src/main/java/br/com/noverde/challenge/repository/LoanRepository.java) - Interface para manipulação da tabela Loan.

* ``br.com.noverde.challenge.service`` - Camada de serviço da aplicação.
[LoanService](https://github.com/gilmaslima/noverde.challenge/blob/master/src/main/java/br/com/noverde/challenge/service/LoanService.java) - Faz a orquestração das chamadas de banco de dados, retorna objeto para o controller e envia objeto loan para processamento assícrono.
[CreditEngineService](https://github.com/gilmaslima/noverde.challenge/blob/master/src/main/java/br/com/noverde/challenge/service/CreditEngineService.java) - Realiza o processamento de forma assíncrona e orquestra as pipelines do motor de crédito.
[RestTemplateService](https://github.com/gilmaslima/noverde.challenge/blob/master/src/main/java/br/com/noverde/challenge/service/RestTemplateService.java) - Responsável pela comunicação Rest com a API da noverde.

* ``br.com.noverde.challenge.service.pipeline`` - Camada com as políticas de validação usadas no motor de crédito.
[PolicyPipeline](https://github.com/gilmaslima/noverde.challenge/blob/master/src/main/java/br/com/noverde/challenge/service/pipeline/PolicyPipeline.java) - Interface modelo para as políticas de validação.
[AgePolicyPipeline](https://github.com/gilmaslima/noverde.challenge/blob/master/src/main/java/br/com/noverde/challenge/service/pipeline/AgePolicyPipeline.java) - Responsável pela validação de idade.
[ScorePolicyPipeline](https://github.com/gilmaslima/noverde.challenge/blob/master/src/main/java/br/com/noverde/challenge/service/pipeline/ScorePolicyPipeline.java) - Responsável pela validação de score.
[CommitmentPolicyPipeline](https://github.com/gilmaslima/noverde.challenge/blob/master/src/main/java/br/com/noverde/challenge/service/pipeline/CommitmentPolicyPipeline.java) - Responsável pela validação/cálculo de juros das parcelas.


* ``br.com.noverde.challenge.validation`` - Pacote com os validadores customizados do Json de request.
[AmountConstraint](https://github.com/gilmaslima/noverde.challenge/blob/master/src/main/java/br/com/noverde/challenge/validation/AmountConstraint.java) - Interface annotation usada na validação do campo ``amount`` durante o request da rota /loan.
[AmountValidator](https://github.com/gilmaslima/noverde.challenge/blob/master/src/main/java/br/com/noverde/challenge/validation/AmountValidator.java) - Implementação de ConstraintValidator que é acionada para executar a validação do campo amount.
[TermsConstraint](https://github.com/gilmaslima/noverde.challenge/blob/master/src/main/java/br/com/noverde/challenge/validation/TermsConstraint.java) - Interface annotation usada na validação do campo ``terms`` durante o request da rota /loan.
[TermsValidator](https://github.com/gilmaslima/noverde.challenge/blob/master/src/main/java/br/com/noverde/challenge/validation/TermsValidator.java) - Implementação de ConstraintValidator que é acionada para executar a validação do campo terms.


## Configuração da aplicação
``src/main/resources``
[application-local.properties](https://github.com/gilmaslima/noverde.challenge/blob/master/src/main/resources/application-local.properties) - Contém as configurações da aplicação para ser executada localmente:
    
    spring.jpa.hibernate.ddl-auto=create
	spring.datasource.url=jdbc:h2:mem:loandb
	spring.datasource.driverClassName=org.h2.Driver
	spring.datasource.username=sa
	spring.datasource.password=
	spring.jpa.database-platform=org.hibernate.dialect.H2Dialect
	logging.level.root=INFO
	logging.level.org.springframework.web=INFO
	logging.level.org.hibernate=INFO
	server.port=8000
	api.key=x-api-key
	api.token=z2qcDsl6BK8FEPynp2ND17WvcJKMQTpjT5lcyQ0d
	noverde.chalenge.url = https://challenge.noverde.name/
	commitment.url = ${noverde.chalenge.url}commitment
	score.url = ${noverde.chalenge.url}score
	valid.terms=6,9,12
	valid.amounts=1000.00,4000.00
	score.limit=600

[application.properties](https://github.com/gilmaslima/noverde.challenge/blob/master/src/main/resources/application.properties) - Contém as configurações da aplicação para ser executada em ambiente produtivo:
    
	spring.jpa.hibernate.ddl-auto=update
	spring.datasource.url=jdbc:mysql://localhost:3306/loandb
	spring.datasource.username=root
	spring.datasource.password=12345678
	logging.level.root=INFO
	logging.level.org.springframework.web=INFO
	logging.level.org.hibernate=INFO
	server.port=8000
	api.key=x-api-key
	api.token=z2qcDsl6BK8FEPynp2ND17WvcJKMQTpjT5lcyQ0d
	noverde.chalenge.url = https://challenge.noverde.name/
	commitment.url = ${noverde.chalenge.url}commitment
	score.url = ${noverde.chalenge.url}score
	valid.terms=6,9,12
	valid.amounts=1000.00,4000.00
	score.limit=600
