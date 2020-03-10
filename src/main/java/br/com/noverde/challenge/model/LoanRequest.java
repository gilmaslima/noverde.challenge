package br.com.noverde.challenge.model;

import java.math.BigDecimal;
import java.util.Date;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;

import br.com.noverde.challenge.validation.AmountConstraint;
import br.com.noverde.challenge.validation.TermsConstraint;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoanRequest {

	
	@NotNull(message="The property name is required")
	@ApiParam(required=true)
	private String name;
	
	@NotNull(message="The property cpf is required")
	@ApiParam(required=true)
	private String cpf;
	
	@NotNull(message="The property birthdate is required")
	@JsonFormat(pattern="dd/MM/yyyy")
	@ApiParam(required=true)
	private Date birthdate;
	
	@NotNull(message="The property amount is required")
	@ApiParam(required=true)
	@AmountConstraint
	private BigDecimal amount;
	
	@NotNull(message="The property terms is required")
	@ApiParam(required=true)
	@TermsConstraint
	private Integer terms;
	
	@NotNull(message="The property income is required")
	@ApiParam(required=true)
	@JsonFormat(shape = Shape.NUMBER_FLOAT)
	private BigDecimal income; 
	
	
}
