package br.com.noverde.challenge.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NoverdeResponse {
	
	private Double commitment;
	
	private Integer score;
}
