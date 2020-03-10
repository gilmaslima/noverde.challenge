package br.com.noverde.challenge.enumeration;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum ResultEnum {

	@JsonProperty("approved")
	APPROVED,
	
	@JsonProperty("refused")
	REFUSED
}
