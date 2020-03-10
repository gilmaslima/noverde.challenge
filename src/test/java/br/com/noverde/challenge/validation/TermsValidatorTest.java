package br.com.noverde.challenge.validation;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import javax.validation.ConstraintValidatorContext;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
@TestPropertySource(properties = { "valid.terms=6,9,12"})
public class TermsValidatorTest {

	
	@Autowired
	private TermsValidator validator;
	
	
	@Test
	public void valid() {
		
		ConstraintValidatorContext context = Mockito.mock(ConstraintValidatorContext.class);
		
		boolean valid = validator.isValid(6, context);
		
		assertTrue(valid);
		
	}
	
	@Test
	public void notValid() {
		
		ConstraintValidatorContext context = Mockito.mock(ConstraintValidatorContext.class);
		
		boolean valid = validator.isValid(5, context);
		
		assertFalse(valid);
		
	}

	
	@Configuration
	@Import(TermsValidator.class)
	static class TestConfig {

	}

	
}
