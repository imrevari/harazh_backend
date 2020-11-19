package ua.com.harazh.oblik.domain.dto;

import ua.com.harazh.oblik.domain.WorkType;

public class WorkTypeResponseDto {
	
	
	private Long id;
	
	private String name;
	
	private String description;
	
	private Double price;
	
	private WorkCategoryResponseDto workCategoryResponseDto;
	

	
	public WorkTypeResponseDto() {
		super();
	}



	public WorkTypeResponseDto(WorkType workType) {
		super();
		this.id = workType.getId();
		this.name = workType.getName();
		this.description = workType.getDescription();
		this.price = workType.getPrice();
		this.workCategoryResponseDto = new WorkCategoryResponseDto(workType.getWorkCategory());
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



	public Double getPrice() {
		return price;
	}



	public void setPrice(Double price) {
		this.price = price;
	}



	public WorkCategoryResponseDto getWorkCategoryResponseDto() {
		return workCategoryResponseDto;
	}



	public void setWorkCategoryResponseDto(WorkCategoryResponseDto workCategoryResponseDto) {
		this.workCategoryResponseDto = workCategoryResponseDto;
	}
	
	
	
	

}
