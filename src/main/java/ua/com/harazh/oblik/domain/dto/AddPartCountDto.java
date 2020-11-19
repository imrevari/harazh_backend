package ua.com.harazh.oblik.domain.dto;

import javax.validation.constraints.NotNull;

public class AddPartCountDto {
	
	@NotNull(message = "{partId.null}")
	private Long partId;
	
	@NotNull(message = "{partAmount.null}")
	private Integer amount;
	
	public AddPartCountDto() {
		super();
	}


	public Long getPartId() {
		return partId;
	}


	public void setPartId(Long partId) {
		this.partId = partId;
	}


	public Integer getAmount() {
		return amount;
	}


	public void setAmount(Integer amount) {
		this.amount = amount;
	}
	
	
	

}
