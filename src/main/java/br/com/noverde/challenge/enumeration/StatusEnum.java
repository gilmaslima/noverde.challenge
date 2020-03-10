package br.com.noverde.challenge.enumeration;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum StatusEnum {
	
	@JsonProperty("processing")
	PROCESSING,
	
	@JsonProperty("completed")
	COMPLETED;
	
}


