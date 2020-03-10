package br.com.noverde.challenge.service.pipeline;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import br.com.noverde.challenge.entity.Loan;
import br.com.noverde.challenge.enumeration.RefusedPolicyEnum;
import br.com.noverde.challenge.exception.PolicyException;
import br.com.noverde.challenge.service.RestTemplateService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class ScorePolicyPipeline implements PolicyPipeline {

	@Value("${score.limit}")
	private Integer scoreLimit;
	
	@Autowired
	private RestTemplateService restService;

	@Override
	public void runPolicy(Loan loan) {
		Integer score = restService.retrieveScore(loan.getCpf());
		loan.setScore(score);
		if (score < scoreLimit) {
			log.info("Loan refused by score: {}", loan);
			throw new PolicyException(RefusedPolicyEnum.SCORE);
		}

	}

	@Override
	public Integer getOrder() {
		return 2;
	}

}
