package br.com.noverde.challenge.service.pipeline;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.noverde.challenge.entity.Loan;
import br.com.noverde.challenge.enumeration.RefusedPolicyEnum;
import br.com.noverde.challenge.exception.PolicyException;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AgePolicyPipelineTest {

	@Autowired
	private AgePolicyPipeline pipeline;
	
	
	@Test
	public void runValidPolicy() {
		try {
			LocalDate localDate = LocalDate.of(2000, 1, 1);
			Loan loan = new Loan();
			Date birthdate=  Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
			loan.setBirthdate(birthdate);
			pipeline.runPolicy(loan);
			
		} catch (PolicyException e) {
			fail();
		}
		
	}
	
	
	@Test
	public void runInvalidPolicy() {
		try {
			LocalDate localDate = LocalDate.of(2002, 3, 11);
			Loan loan = new Loan();
			Date birthdate=  Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
			loan.setBirthdate(birthdate);
			pipeline.runPolicy(loan);
			
			fail();
		} catch (PolicyException e) {
			assertEquals(e.getRefusePolicy(), RefusedPolicyEnum.AGE);
		}
		
	}
	
	
	@Test
	public void getOrder() {
		
		assertTrue(1 == pipeline.getOrder());
		
	}
	
	@Configuration
	@Import(AgePolicyPipeline.class)
	static class TestConfig {

	}
}
