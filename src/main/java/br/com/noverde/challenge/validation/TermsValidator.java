package br.com.noverde.challenge.validation;

import java.util.List;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class TermsValidator implements ConstraintValidator<TermsConstraint, Integer> {

	@Value("#{'${valid.terms}'.split(',')}")
	private List<Integer> validTerms;

	@Override
	public boolean isValid(Integer value, ConstraintValidatorContext context) {
		
		return validTerms.contains(value);
	}

}
