package br.com.noverde.challenge.config;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import br.com.noverde.challenge.service.pipeline.AgePolicyPipeline;
import br.com.noverde.challenge.service.pipeline.CommitmentPolicyPipeline;
import br.com.noverde.challenge.service.pipeline.PolicyPipeline;
import br.com.noverde.challenge.service.pipeline.ScorePolicyPipeline;

@Configuration
public class PipelineConfig {

	@Autowired
	@Qualifier("agePolicyPipeline")
	private AgePolicyPipeline agePipeline;

	@Autowired
	@Qualifier("scorePolicyPipeline")
	private ScorePolicyPipeline scorePipeline;

	
	@Autowired
	@Qualifier("commitmentPolicyPipeline")
	private CommitmentPolicyPipeline commitmentPipeline;

	
	@Bean("pipelines")
	public List<PolicyPipeline> pipelines() {
		return Arrays.asList(agePipeline, scorePipeline, commitmentPipeline);
	}
	
	

}
