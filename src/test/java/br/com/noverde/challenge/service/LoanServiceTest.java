package br.com.noverde.challenge.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Optional;
import java.util.UUID;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.noverde.challenge.entity.Loan;
import br.com.noverde.challenge.model.LoanRequest;
import br.com.noverde.challenge.repository.LoanRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
public class LoanServiceTest {

	
	@Autowired
	private LoanService service;
	
	@MockBean
    private LoanRepository repository;

	@MockBean
    private CreditEngineService asyncService;
	
	
	
	
	@Test
	public void createLoan() {
		
		UUID id = UUID.randomUUID();
		Loan loan = new Loan();
		loan.setId(id);
		
		Mockito.when(repository.save(Mockito.any(Loan.class))).thenReturn(loan);
		
		Loan loanFromDb = service.createLoan(new LoanRequest());
		
		assertEquals(id, loanFromDb.getId());
		
		
	}
	
	
	@Test
	public void findLoanById() {
		
		UUID id = UUID.randomUUID();
		Loan loan = new Loan();
		loan.setId(id);
		
		Optional<Loan> opt = Optional.of(loan);
		Mockito.when(repository.findById(id)).thenReturn(opt);
		
		Optional<Loan> loanFromDb = service.getLoanById(id);
		
		assertTrue(loanFromDb.isPresent());
		
		assertEquals(id, loanFromDb.get().getId());
		
		
	}
	

	@Test
	public void notFindLoanById() {
		
		UUID id = UUID.randomUUID();
		Loan loan = new Loan();
		loan.setId(id);
		
		Optional<Loan> opt = Optional.empty();
		Mockito.when(repository.findById(id)).thenReturn(opt);
		
		Optional<Loan> loanFromDb = service.getLoanById(id);
		
		assertTrue(loanFromDb.isEmpty());
		
	}

	
	
	@Configuration
	@Import(LoanService.class)
	static class TestConfig {
		
		@Bean
		LoanRepository loanRepository() {
			return Mockito.mock(LoanRepository.class);
		}
		
		@Bean
		CreditEngineService creditEngineService() {
			return Mockito.mock(CreditEngineService.class);
		}
	}
	
}
