package br.com.noverde.challenge.service.pipeline;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;

import org.springframework.stereotype.Component;

import br.com.noverde.challenge.entity.Loan;
import br.com.noverde.challenge.enumeration.RefusedPolicyEnum;
import br.com.noverde.challenge.exception.PolicyException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class AgePolicyPipeline implements PolicyPipeline {

	@Override
	public void runPolicy(Loan loan) {
		log.info("Running age policy");
		LocalDate birthDate = loan.getBirthdate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		LocalDate now = LocalDate.now();
		long age = ChronoUnit.YEARS.between(birthDate, now);
		if (age < 18) {
			log.info("Loan refused by age: {}", loan);
			throw new PolicyException(RefusedPolicyEnum.AGE);
		}
	}

	@Override
	public Integer getOrder() {
		return 1;
	}

}
