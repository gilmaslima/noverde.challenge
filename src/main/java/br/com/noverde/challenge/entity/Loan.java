package br.com.noverde.challenge.entity;

import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.springframework.beans.BeanUtils;

import br.com.noverde.challenge.enumeration.RefusedPolicyEnum;
import br.com.noverde.challenge.enumeration.ResultEnum;
import br.com.noverde.challenge.enumeration.StatusEnum;
import br.com.noverde.challenge.model.LoanRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Loan {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id", updatable = false, unique = true, nullable = false)
	private UUID id;
	

	private String name;

	private String cpf;

	private Date birthdate;

	private BigDecimal amount;

	private Integer terms;

	private BigDecimal income;

	
	private StatusEnum status = StatusEnum.PROCESSING;
	
	private ResultEnum result;
	
	private RefusedPolicyEnum refusedPolicy;
	
	private Double commitment;
	
	private Integer score;
	
	private BigDecimal parcelInterest;
	
	public static Loan createFromRequest(LoanRequest request) {
		
		Loan loan = new Loan();
		BeanUtils.copyProperties(request, loan);
		
		return loan;
	}

	
}
