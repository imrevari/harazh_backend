package ua.com.harazh.oblik.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import ua.com.harazh.oblik.domain.dto.CreateWorkTypeDto;

import java.util.Objects;

@Entity
public class WorkType {
	
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable = false)
	private String name;
	
	@Column(columnDefinition = "TEXT")
	private String description;
	
	@Column(nullable = false)
	private Double price;
	
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "work_category_id")
	private WorkCategory workCategory;
	
	private Double salary;


	public WorkType() {
		super();
	}

	
	
	public WorkType(CreateWorkTypeDto dto) {
		super();
		this.name = dto.getName().trim();
		this.description = dto.getDescription();
		this.price = dto.getPrice();
		if(Objects.isNull(dto.getSalary())){
			this.salary = null;
		}else{
			this.salary = dto.getSalary();
		}
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}

	public WorkCategory getWorkCategory() {
		return workCategory;
	}

	public void setWorkCategory(WorkCategory workCategory) {
		this.workCategory = workCategory;
	}

	public Double getSalary() {
		return salary;
	}

	public void setSalary(Double salary) {
		this.salary = salary;
	}
}
