package br.com.noverde.challenge.service.pipeline;

import br.com.noverde.challenge.entity.Loan;

public interface PolicyPipeline {

	public abstract void runPolicy(Loan loan);
	
	public abstract Integer getOrder();
}
