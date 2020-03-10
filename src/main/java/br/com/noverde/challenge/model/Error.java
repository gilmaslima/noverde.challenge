package br.com.noverde.challenge.model;

import java.util.Arrays;
import java.util.List;

import lombok.Data;

@Data
public class Error {

	
	private List<String> errors;
 
    public Error(List<String> errors) {
        super();
        this.errors = errors;
    }
 
    public Error(String error) {
        super();
        errors = Arrays.asList(error);
    }
	
}
