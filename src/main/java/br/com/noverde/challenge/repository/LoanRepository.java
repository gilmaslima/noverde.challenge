package br.com.noverde.challenge.repository;

import java.util.UUID;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import br.com.noverde.challenge.entity.Loan;

@Repository
public interface LoanRepository extends CrudRepository<Loan, UUID> {

	

}
