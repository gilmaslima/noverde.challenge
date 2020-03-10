package br.com.noverde.challenge.exception;

import br.com.noverde.challenge.enumeration.RefusedPolicyEnum;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PolicyException extends RuntimeException {

	private static final long serialVersionUID = -641664646114049957L;
	private RefusedPolicyEnum refusePolicy;
	
}
