package br.com.noverde.challenge.controller;

import java.util.HashMap;
import java.util.Optional;
import java.util.UUID;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import br.com.noverde.challenge.entity.Loan;
import br.com.noverde.challenge.model.LoanRequest;
import br.com.noverde.challenge.model.LoanResponse;
import br.com.noverde.challenge.service.LoanService;
import lombok.extern.slf4j.Slf4j;

@RestController
@Validated
@Slf4j
public class LoanController {

	
	@Autowired
	private LoanService service;


	@PostMapping(path = "/loan", produces = { "application/json; charset=UTF-8" })
	public ResponseEntity createLoan(@Valid @RequestBody LoanRequest request) {
		try {
			log.info("Init request loan endpoint {}", request);
			
			Loan loan = service.createLoan(request);
			
			log.info("Loan created: {}", loan);
			
			HashMap<String, UUID> map = new HashMap<>();
			map.put("id", loan.getId());
			return new ResponseEntity<>(map, HttpStatus.CREATED);
			
		} catch (Exception e) {
			log.error("unexpected error", e);
			return new ResponseEntity<Integer>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	
	@GetMapping(path = "/loan/{id}", produces = { "application/json; charset=UTF-8" })
	public ResponseEntity getLoan(@PathVariable UUID id) {
		try {
			
			Optional<Loan> optional = service.getLoanById(id);
			if(optional.isPresent()) {
				return new ResponseEntity<LoanResponse>(LoanResponse.createFromLoan(optional.get()), HttpStatus.CREATED);
			}
			return new ResponseEntity<>(HttpStatus.OK);
			
		} catch (Exception e) {
			log.error("unexpected error", e);
			return new ResponseEntity<Integer>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping(path = "/")
	public ResponseEntity<Integer> heathCheck(){
		return new ResponseEntity<Integer>(HttpStatus.OK);
	}
}
