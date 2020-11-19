package ua.com.harazh.oblik.domain.dto;

import javax.validation.constraints.NotBlank;

public class CreateWorkCategoryDto {
	
	
	@NotBlank(message = "{workCategoryName.notNull}")
	private String categoryName;

	
	
	public CreateWorkCategoryDto() {
		super();
	}
	
	

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}
	
	
	

}
