package ua.com.harazh.oblik.domain.dto;

import ua.com.harazh.oblik.domain.Part;

public class RetailPartResponseDto {
	
	
	private Long id;
	
	private String name;
	
	private String description;
	
	private Double retailPrice;

	
	public RetailPartResponseDto() {
		super();
	}


	public RetailPartResponseDto(Part part) {
		super();
		this.id = part.getId();
		this.name = part.getName();
		this.description = part.getDescription();
		this.retailPrice = part.getRetailPrice();
	}


	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
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
	
	
	
	

}
