package ua.com.harazh.oblik.domain.dto;

import java.util.Objects;

import ua.com.harazh.oblik.domain.Work;

public class ResponseWorkDto {
	
	private Long id;
	
	private String workName;
	
	private Double price;
	
	private String description;
	
	private boolean workDone;
	
	private String userName;

	public ResponseWorkDto() {
		super();
	}

	public ResponseWorkDto(Work work) {
		super();
		this.id = work.getId();
		this.workName = work.getWorkName();
		this.price = work.getPrice();
		this.workDone = work.isWorkDone();
		if (!Objects.isNull(work.getWorkType() )) {
			this.description = work.getWorkType().getDescription();
		}else{
			this.description = "";
		}
		if (!Objects.isNull(work.getOblikUser()) ) {
			this.userName = work.getOblikUser().getName();
		}
	}

	public Long getId() {
		return id;
	}



	public void setId(Long id) {
		this.id = id;
	}



	public String getWorkName() {
		return workName;
	}



	public void setWorkName(String workName) {
		this.workName = workName;
	}



	public Double getPrice() {
		return price;
	}



	public void setPrice(Double price) {
		this.price = price;
	}



	public String getDescription() {
		return description;
	}



	public void setDescription(String description) {
		this.description = description;
	}



	public boolean isWorkDone() {
		return workDone;
	}



	public void setWorkDone(boolean workDone) {
		this.workDone = workDone;
	}


	public String getUserName() {
		return userName;
	}


	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	
	

}
