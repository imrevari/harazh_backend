package ua.com.harazh.oblik.domain.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;





public class CreateOrderDto {
	
	@NotNull(message = "{custId.null}")
	private Long customer;

	@NotNull(message = "{carId.null}")
	private Long car;
	
	private Double amountPayedInAdvance;
	
	@NotBlank(message = "{problem.null}")
	private String problem;
	
	

	public CreateOrderDto() {
		super();
	}



	public Long getCustomer() {
		return customer;
	}



	public void setCustomer(Long customer) {
		this.customer = customer;
	}



	public Long getCar() {
		return car;
	}



	public void setCar(Long car) {
		this.car = car;
	}


	public Double getAmountPayedInAdvance() {
		return amountPayedInAdvance;
	}



	public void setAmountPayedInAdvance(Double amountPayedInAdvance) {
		this.amountPayedInAdvance = amountPayedInAdvance;
	}



	public String getProblem() {
		return problem;
	}



	public void setProblem(String problem) {
		this.problem = problem;
	}
	
	
	

}
