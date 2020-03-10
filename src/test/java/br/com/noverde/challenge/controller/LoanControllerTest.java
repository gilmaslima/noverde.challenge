package br.com.noverde.challenge.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Optional;
import java.util.UUID;

import javax.validation.ConstraintViolationException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.noverde.challenge.entity.Loan;
import br.com.noverde.challenge.model.LoanRequest;
import br.com.noverde.challenge.model.LoanResponse;
import br.com.noverde.challenge.service.LoanService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class LoanControllerTest {

	@Autowired
	private LoanController controller;

	@MockBean
	private LoanService service;

	@Test
	public void postWithEmptyData() {
		try {
			controller.createLoan(new LoanRequest());
		} catch (Exception e) {
			assertTrue(e instanceof ConstraintViolationException);
		}
	}

	@Test
	public void postErrorHandler() {

		LoanRequest request = createRequest();
		Mockito.when(service.createLoan(request)).thenThrow(NullPointerException.class);
		ResponseEntity<Integer> response = controller.createLoan(request);
		assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), response.getStatusCodeValue());
	}

	@Test
	public void postWithValidData() {
		LoanRequest request = createRequest();
		Mockito.when(service.createLoan(request)).thenReturn(new Loan());
		ResponseEntity<HashMap> response = controller.createLoan(request);
		assertEquals(HttpStatus.CREATED.value(), response.getStatusCodeValue());
		assertTrue(response.getBody() instanceof HashMap);

	}

	@Test
	public void heathCheck() {
		ResponseEntity<Integer> check = controller.heathCheck();
		assertEquals(HttpStatus.OK.value(), check.getStatusCodeValue());
	}

	@Test
	public void getValidLoan() {
		UUID id = UUID.randomUUID();

		Loan loan = new Loan();
		loan.setId(id);
		Optional<Loan> opt = Optional.of(loan);
		Mockito.when(service.getLoanById(id)).thenReturn(opt);

		ResponseEntity<LoanResponse> response = controller.getLoan(id);
		assertEquals(id, response.getBody().getId());

	}

	@Test
	public void getInvalidLoan() {
		UUID id = UUID.randomUUID();

		Optional<Loan> opt = Optional.empty();
		Mockito.when(service.getLoanById(id)).thenReturn(opt);

		ResponseEntity<LoanResponse> response = controller.getLoan(id);
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertNull(response.getBody());

	}

	@Test
	public void getLoanErrorHandler() {

		UUID id = UUID.randomUUID();

		Mockito.when(service.getLoanById(id)).thenThrow(NullPointerException.class);
		ResponseEntity<LoanResponse> response = controller.getLoan(id);
		assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
		assertNull(response.getBody());
		
	}

	private LoanRequest createRequest() {
		LoanRequest request = new LoanRequest();
		request.setAmount(BigDecimal.valueOf(2000));
		request.setBirthdate(new Date());
		request.setCpf("12244578977");
		request.setIncome(BigDecimal.valueOf(5000));
		request.setTerms(6);
		return request;
	}

	@Configuration
	@Import(LoanController.class)
	static class TestConfig {
		@Bean
		LoanService loanService() {
			return Mockito.mock(LoanService.class);
		}
	}
}
