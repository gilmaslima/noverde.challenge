package br.com.noverde.challenge.service.pipeline;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.math.BigDecimal;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.noverde.challenge.entity.Loan;
import br.com.noverde.challenge.enumeration.RefusedPolicyEnum;
import br.com.noverde.challenge.exception.PolicyException;
import br.com.noverde.challenge.service.RestTemplateService;

@RunWith(SpringRunner.class)
@SpringBootTest
@TestPropertySource(properties = { "valid.terms=6,9,12"})
public class CommitmentPolicyPipelineTest {
	
	
	@Autowired
	private CommitmentPolicyPipeline pipeline;
	
	@MockBean
	private RestTemplateService restService;
	
	
	@Test
	public void runValidPolicy() {
		try {
			final Double commitment = 0.15;
			Loan loan = new Loan();
			loan.setCpf("25545487788");
			loan.setScore(840);
			loan.setIncome(BigDecimal.valueOf(15000));
			loan.setTerms(6);
			loan.setAmount(BigDecimal.valueOf(1000));
			
			Mockito.when(restService.retrieveCommitment(Mockito.anyString())).thenReturn(commitment);
			
			pipeline.runPolicy(loan);
			
			
		} catch (PolicyException e) {
			fail();
		}
		
	}

	
	
	@Test
	public void runInvalidPolicy() {
		try {
			final Double commitment = 0.9;
			Loan loan = new Loan();
			loan.setCpf("25545487788");
			loan.setScore(600);
			loan.setIncome(BigDecimal.valueOf(1000));
			loan.setTerms(6);
			loan.setAmount(BigDecimal.valueOf(4000));
			
			Mockito.when(restService.retrieveCommitment(Mockito.anyString())).thenReturn(commitment);
			
			pipeline.runPolicy(loan);
			
			fail();
		} catch (PolicyException e) {
			assertEquals(e.getRefusePolicy(), RefusedPolicyEnum.COMMITMENT);
		}
		
	}
	
	@Test
	public void getOrder() {
		
		assertTrue(3 == pipeline.getOrder());
		
	}
	
	
	@Configuration
	@Import(CommitmentPolicyPipeline.class)
	static class TestConfig {

		@Bean
		RestTemplateService restTemplateService() {
			return Mockito.mock(RestTemplateService.class);
		}
		
	}

}
