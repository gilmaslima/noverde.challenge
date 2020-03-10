package br.com.noverde.challenge.service.pipeline;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import br.com.noverde.challenge.entity.Loan;
import br.com.noverde.challenge.enumeration.RefusedPolicyEnum;
import br.com.noverde.challenge.exception.PolicyException;
import br.com.noverde.challenge.service.RestTemplateService;
import br.com.noverde.challenge.utils.CommitmentUtils;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class CommitmentPolicyPipeline implements PolicyPipeline {
	
	@Autowired
	private RestTemplateService restService;

	@Value("#{'${valid.terms}'.split(',')}")
	private List<Integer> validTerms;
	
	@Override
	public void runPolicy(Loan loan) {
		final BigDecimal commitment = BigDecimal.valueOf(restService.retrieveCommitment(loan.getCpf()));
		final Integer score = loan.getScore();
		final BigDecimal amount = loan.getAmount();
		final BigDecimal income = loan.getIncome();
		final BigDecimal maxParcel = income.subtract(income.divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_EVEN)
				.multiply(commitment.multiply(BigDecimal.valueOf(100))));
		List<Integer> termsFound = new ArrayList<>();
		validTerms.stream().filter(p -> p >= loan.getTerms()).forEachOrdered(parcelNumber -> {
			BigDecimal parcelInterest = CommitmentUtils.getParcelInterest(score, parcelNumber, amount);

			if (parcelInterest.compareTo(maxParcel) < 1 && termsFound.isEmpty()) {
				termsFound.add(parcelNumber);
				// Attribute added to save installment interest
				loan.setParcelInterest(parcelInterest);
				loan.setTerms(parcelNumber);
				log.info("Loan apporved with installment: {}", loan.getParcelInterest());
			}
		});

		if (termsFound.isEmpty()) {
			log.info("Loan refused by commitment: {}", loan);
			throw new PolicyException(RefusedPolicyEnum.COMMITMENT);
		}

	}

	@Override
	public Integer getOrder() {
		return 3;
	}

}
