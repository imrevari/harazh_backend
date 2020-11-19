package ua.com.harazh.oblik.domain.dto;

import ua.com.harazh.oblik.domain.WorkCategory;

public class WorkCategoryResponseDto {
	
	private Long id;
	
	private String categoryName;
	
	

	public WorkCategoryResponseDto(WorkCategory workCategory) {
		this.id = workCategory.getId();
		this.categoryName = workCategory.getCategoryName();
	}



	public WorkCategoryResponseDto() {
		super();
	}



	public Long getId() {
		return id;
	}



	public void setId(Long id) {
		this.id = id;
	}



	public String getCategoryName() {
		return categoryName;
	}



	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}
	
	
	
	
	

}
