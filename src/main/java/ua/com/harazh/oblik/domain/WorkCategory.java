package ua.com.harazh.oblik.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import ua.com.harazh.oblik.domain.dto.CreateWorkCategoryDto;

@Entity
public class WorkCategory {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable = false)
	private String categoryName;
	
	

	public WorkCategory() {
		super();
	}
	
	
	public WorkCategory(CreateWorkCategoryDto createWorkCategoryDto) {
		this.categoryName = createWorkCategoryDto.getCategoryName();
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
