package br.com.noverde.challenge.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import br.com.noverde.challenge.model.NoverdeRequest;
import br.com.noverde.challenge.model.NoverdeResponse;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class RestTemplateService {

	@Value("${api.key}")
	private String apiKey;

	@Value("${api.token}")
	private String apiToken;

	@Value("${commitment.url}")
	private String commitmentUrl;

	@Value("${score.url}")
	private String scoreUrl;

	@Retryable(
		      value = { RestClientException.class }, 
		      maxAttempts = 15,
		      backoff = @Backoff(delay = 10000))
	public Integer retrieveScore(String cpf) {

		log.info("Retrieving score for cpf: {}", cpf);
		HttpHeaders headers = buildHeaders();
		RestTemplate restTemplate = new RestTemplate();
		HttpEntity<NoverdeRequest> request = new HttpEntity<>(new NoverdeRequest(cpf), headers);
		ResponseEntity<NoverdeResponse> response = restTemplate.exchange(scoreUrl, HttpMethod.POST, request,
				NoverdeResponse.class);

		NoverdeResponse body = response.getBody();
		log.info("Returned score for cpf: {}", cpf);

		return body.getScore();

	}

	@Retryable(
		      value = { RestClientException.class }, 
		      maxAttempts = 15,
		      backoff = @Backoff(delay = 10000))
	public Double retrieveCommitment(String cpf) {

		log.info("Retrieving commitment for cpf: {}", cpf);
		HttpHeaders headers = buildHeaders();
		RestTemplate restTemplate = new RestTemplate();
		HttpEntity<NoverdeRequest> request = new HttpEntity<>(new NoverdeRequest(cpf), headers);
		ResponseEntity<NoverdeResponse> response = restTemplate.exchange(commitmentUrl, HttpMethod.POST, request,
				NoverdeResponse.class);

		NoverdeResponse body = response.getBody();
		log.info("Returned commitment for cpf: {}", cpf);

		return body.getCommitment();

	}

	private HttpHeaders buildHeaders() {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.add(apiKey, apiToken);
		return headers;
	}

}
