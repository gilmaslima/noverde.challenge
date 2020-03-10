package br.com.noverde.challenge.validation;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;

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
@TestPropertySource(properties = { "valid.amounts=1000.00,4000.00"})
public class AmountValidatorTest {

	
	@Autowired
	private AmountValidator validator;
	
	
	@Test
	public void valid() {
		
		ConstraintValidatorContext context = Mockito.mock(ConstraintValidatorContext.class);
		
		boolean valid = validator.isValid(BigDecimal.valueOf(2000.00), context);
		
		assertTrue(valid);
		
	}
	
	@Test
	public void notValid() {
		
		ConstraintValidatorContext context = Mockito.mock(ConstraintValidatorContext.class);
		
		boolean valid = validator.isValid(BigDecimal.valueOf(999.00), context);
		
		assertFalse(valid);
		
	}

	
	@Configuration
	@Import(AmountValidator.class)
	static class TestConfig {

	}

	
}
