package br.com.noverde.challenge.service;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.noverde.challenge.entity.Loan;
import br.com.noverde.challenge.enumeration.RefusedPolicyEnum;
import br.com.noverde.challenge.enumeration.ResultEnum;
import br.com.noverde.challenge.enumeration.StatusEnum;
import br.com.noverde.challenge.exception.PolicyException;
import br.com.noverde.challenge.repository.LoanRepository;
import br.com.noverde.challenge.service.pipeline.AgePolicyPipeline;
import br.com.noverde.challenge.service.pipeline.CommitmentPolicyPipeline;
import br.com.noverde.challenge.service.pipeline.PolicyPipeline;
import br.com.noverde.challenge.service.pipeline.ScorePolicyPipeline;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CreditEngineServiceTest {

	@MockBean
	private LoanRepository repository;

	@MockBean
	private List<PolicyPipeline> pipelines;

	@Autowired
	private CreditEngineService service;

	@Test
	public void processLoanSucess() {
		
		Loan loan = Mockito.mock(Loan.class);
		
		service.processLoan(loan);
		
		Mockito.verify(loan).setResult(ResultEnum.APPROVED);
		
		Mockito.verify(loan).setStatus(StatusEnum.COMPLETED);
		Mockito.verify(repository).save(loan);
		
	}
	
	
	@Test
	public void processLoanError() {
		
		Loan loan = Mockito.mock(Loan.class);
		
		Mockito.doThrow(new PolicyException(RefusedPolicyEnum.AGE)).when(pipelines).forEach(Mockito.any());
		service.processLoan(loan);
		
		
		Mockito.verify(loan).setResult(ResultEnum.REFUSED);
		
		Mockito.verify(loan).setStatus(StatusEnum.COMPLETED);
		Mockito.verify(repository).save(loan);
		
	}

	@Configuration
	@Import(CreditEngineService.class)
	static class TestConfig {

		@Bean
		LoanRepository loanRepository() {
			return Mockito.mock(LoanRepository.class);
		}

		@Bean
		List<PolicyPipeline> pipelines() {
			return Arrays.asList(Mockito.mock(AgePolicyPipeline.class), Mockito.mock(ScorePolicyPipeline.class),
					Mockito.mock(CommitmentPolicyPipeline.class));
		}

		

	}
}
