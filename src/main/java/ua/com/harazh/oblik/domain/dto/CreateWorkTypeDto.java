package ua.com.harazh.oblik.domain.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class CreateWorkTypeDto {
	
	@NotBlank(message = "{workName.notNull}")
	private String name;
	
	private String description;
	
	@NotNull(message = "{workPrice.notNull}")
	private Double price;
	
	@NotNull(message = "{workCategoryName.notNull}")
	private Long workCategory; 
	
	

	public CreateWorkTypeDto() {
		super();
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



	public Double getPrice() {
		return price;
	}



	public void setPrice(Double price) {
		this.price = price;
	}



	public Long getWorkCategory() {
		return workCategory;
	}



	public void setWorkCategory(Long workCategory) {
		this.workCategory = workCategory;
	}
	
	
	
	
}
