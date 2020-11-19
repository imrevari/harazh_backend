package ua.com.harazh.oblik.domain.dto;

import java.util.Objects;

import ua.com.harazh.oblik.domain.PartCount;

public class ResponsePartCountDto {
	
	private Long id;
	
	private Long partId;
	
	private String partName;
	
	private String description;
	
	private Double retailPrice;
	
	private Integer amount;
	
	

	public ResponsePartCountDto() {
		super();
	}

	

	public ResponsePartCountDto(PartCount partCount) {
		super();
		this.id = partCount.getId();
		if (!Objects.isNull(partCount.getPart() )) {
			this.partName = partCount.getPart().getName();
			this.description = partCount.getPart().getDescription();
			this.retailPrice = partCount.getPart().getRetailPrice();
			this.partId = partCount.getPart().getId();
		} else {
			this.partName = partCount.getPartName();
			this.description = "";
			this.retailPrice = partCount.getRetailPrice();
			this.partId = null;
		}
		this.amount = partCount.getAmount();
	}



	public Long getId() {
		return id;
	}



	public void setId(Long id) {
		this.id = id;
	}



	public String getPartName() {
		return partName;
	}



	public void setPartName(String partName) {
		this.partName = partName;
	}



	public String getDescription() {
		return description;
	}



	public void setDescription(String description) {
		this.description = description;
	}



	public Double getRetailPrice() {
		return retailPrice;
	}



	public void setRetailPrice(Double retailPrice) {
		this.retailPrice = retailPrice;
	}



	public Integer getAmount() {
		return amount;
	}



	public void setAmount(Integer amount) {
		this.amount = amount;
	}



	public Long getPartId() {
		return partId;
	}



	public void setPartId(Long partId) {
		this.partId = partId;
	}
	
	
	
	
}
