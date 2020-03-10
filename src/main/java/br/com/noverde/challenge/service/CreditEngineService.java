package br.com.noverde.challenge.service;

import java.util.Comparator;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import br.com.noverde.challenge.entity.Loan;
import br.com.noverde.challenge.enumeration.ResultEnum;
import br.com.noverde.challenge.enumeration.StatusEnum;
import br.com.noverde.challenge.exception.PolicyException;
import br.com.noverde.challenge.repository.LoanRepository;
import br.com.noverde.challenge.service.pipeline.PolicyPipeline;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class CreditEngineService {


	@Autowired
	private LoanRepository repository;

	
	@Autowired
	private List<PolicyPipeline> pipelines;
	
	@PostConstruct
	public void init(){
		Comparator<PolicyPipeline> comparator = new Comparator<PolicyPipeline>() {
		    @Override
		    public int compare(PolicyPipeline o1, PolicyPipeline o2) {
		        return o1.getOrder().compareTo(o2.getOrder());
		    }
		};
		pipelines.sort(comparator);
	}
	
	@Async
	public void processLoan(Loan loan) {
		try {
			log.info("Processing loan: {}", loan);

			pipelines.forEach(pipe -> {
				pipe.runPolicy(loan);
			});
			
			loan.setResult(ResultEnum.APPROVED);

		} catch (PolicyException e) {
			loan.setRefusedPolicy(e.getRefusePolicy());
			loan.setResult(ResultEnum.REFUSED);
			loan.setAmount(null);
			loan.setTerms(null);
		} finally {
			loan.setStatus(StatusEnum.COMPLETED);
			repository.save(loan);
		}

	}

	
	
	

}
