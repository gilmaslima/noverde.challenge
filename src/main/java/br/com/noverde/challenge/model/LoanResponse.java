package br.com.noverde.challenge.model;

import java.math.BigDecimal;
import java.util.UUID;

import org.springframework.beans.BeanUtils;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import br.com.noverde.challenge.entity.Loan;
import br.com.noverde.challenge.enumeration.RefusedPolicyEnum;
import br.com.noverde.challenge.enumeration.ResultEnum;
import br.com.noverde.challenge.enumeration.StatusEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonPropertyOrder({
    "id",
    "status",
    "result",
    "refusedPolicy",
    "amount",
    "terms"
})
public class LoanResponse {

	
	private UUID id;

	private StatusEnum status;

	private ResultEnum result;

	@JsonProperty("refused_policy")
	private RefusedPolicyEnum refusedPolicy;
	
	private BigDecimal amount;

	private Integer terms;
	
	public LoanResponse(UUID id) {
		this.id = id;
	}

	public static LoanResponse createFromLoan(Loan loan) {
		LoanResponse response = new LoanResponse();
		BeanUtils.copyProperties(loan, response);
				
		return response;
	}
}
