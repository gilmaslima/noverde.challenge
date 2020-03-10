package br.com.noverde.challenge.service;

import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.noverde.challenge.entity.Loan;
import br.com.noverde.challenge.model.LoanRequest;
import br.com.noverde.challenge.repository.LoanRepository;

@Service
public class LoanService {

	@Autowired
	private LoanRepository repository;

	@Autowired
	private CreditEngineService asyncService;

	public Loan createLoan(LoanRequest request) {
		
		Loan loan = repository.save(Loan.createFromRequest(request));
		
		asyncService.processLoan(loan);
		
		return loan;
	}

	public Optional<Loan> getLoanById(UUID id) {
		
		return repository.findById(id); 
		
	}

	
	
	
}
