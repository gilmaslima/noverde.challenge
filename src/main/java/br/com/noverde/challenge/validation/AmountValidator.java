package br.com.noverde.challenge.validation;

import java.math.BigDecimal;
import java.util.List;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class AmountValidator implements ConstraintValidator<AmountConstraint, BigDecimal> {

	@Value("#{'${valid.amounts}'.split(',')}")
	private List<BigDecimal> validAmounts;

	@Override
	public boolean isValid(BigDecimal value, ConstraintValidatorContext context) {
		return value.doubleValue() >= validAmounts.get(0).doubleValue() && value.doubleValue() <= validAmounts.get(1).doubleValue();
	}

}
