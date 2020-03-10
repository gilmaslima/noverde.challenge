package br.com.noverde.challenge.service.pipeline;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

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
@TestPropertySource(properties = { "score.limit=600"})
public class ScorePolicyPipelineTest {

	
	@Autowired
	private ScorePolicyPipeline pipeline;
	
	@MockBean
	private RestTemplateService restService;
	
	
	
	@Test
	public void runValidPolicy() {
		try {
			final int score = 680;
			Loan loan = new Loan();
			loan.setCpf("25545487788");
			Mockito.when(restService.retrieveScore(Mockito.anyString())).thenReturn(score);
			
			pipeline.runPolicy(loan);
			
			
		} catch (PolicyException e) {
			fail();
		}
		
	}
	
	
	@Test
	public void runInvalidPolicy() {
		try {
			final int score = 599;
			Loan loan = new Loan();
			loan.setCpf("25545487788");
			
			Mockito.when(restService.retrieveScore(Mockito.anyString())).thenReturn(score);
			
			pipeline.runPolicy(loan);
			
			
			fail();
		} catch (PolicyException e) {
			assertEquals(e.getRefusePolicy(), RefusedPolicyEnum.SCORE);
		}
		
	}
	
	
	@Test
	public void getOrder() {
		
		assertTrue(2 == pipeline.getOrder());
		
	}
	
	
	@Configuration
	@Import(ScorePolicyPipeline.class)
	static class TestConfig {

		@Bean
		RestTemplateService restTemplateService() {
			return Mockito.mock(RestTemplateService.class);
		}
		
	}

}
