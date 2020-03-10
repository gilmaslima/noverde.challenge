package br.com.noverde.challenge.enumeration;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum RefusedPolicyEnum {

	@JsonProperty("age")
	AGE,
	
	@JsonProperty("score")
	SCORE,
	
	@JsonProperty("commitment")
	COMMITMENT
}
