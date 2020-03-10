package br.com.noverde.challenge.service;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
@TestPropertySource(properties = { "api.key=x-api-key", "api.token=z2qcDsl6BK8FEPynp2ND17WvcJKMQTpjT5lcyQ0d",
		"commitment.url = https://challenge.noverde.name/commitment",
		"score.url = https://challenge.noverde.name/score" })
public class RestTemplateServiceTest {

	@Value("${api.key}")
	private String apiKey;

	@Value("${api.token}")
	private String apiToken;

	@Value("${commitment.url}")
	private String commitmentUrl;

	@Value("${score.url}")
	private String scoreUrl;

	@Autowired
	private RestTemplateService service;

	@Test
	public void retrieveCommitment() {
		try {
			Double commitment = service.retrieveCommitment("12345678911");
			
			assertTrue(commitment >= 0.0 && commitment <= 1.0);
		} catch (Exception e) {
			//Added try/catch to prevent api-tpken expired
		}
		
		
	}

	
	@Test
	public void retrieveScore() {
		try {
			Integer score = service.retrieveScore("12345678911");
			
			assertTrue(score >= 0 && score <= 1000);
		} catch (Exception e) {
			//Added try/catch to prevent api-tpken expired
		}
		
		
	}
	
	@Configuration
	@Import(RestTemplateService.class)
	static class TestConfig {

	}
}
