package ua.com.harazh.oblik.domain.dto;


import ua.com.harazh.oblik.domain.Work;

import java.util.Objects;

public class ResponseCompletedWorkDto {
	
	
	private String workName;
	
	private Double price;
	
	private String licencePlate;
	
	private Long orderId;
	
	private String orderClosed;

	private String userName;

	private Double salary;
	
	public ResponseCompletedWorkDto() {
		super();
	}
	
	public ResponseCompletedWorkDto(Work work, ResponseOrderDto responseOrderDto) {
		this.workName = work.getWorkName();
		this.price = work.getPrice();
		this.licencePlate = responseOrderDto.getCar().getCarMade();
		this.orderId = responseOrderDto.getId();
		this.orderClosed = Objects.isNull(work.getDoneAt()) ? responseOrderDto.getOrderOpened() : work.getDoneAt().toString();
		this.userName = work.getOblikUser().getName();
		this.salary = work.getSalary();
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

	public String getLicencePlate() {
		return licencePlate;
	}

	public void setLicencePlate(String licencePlate) {
		this.licencePlate = licencePlate;
	}

	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	public String getOrderClosed() {
		return orderClosed;
	}

	public void setOrderClosed(String orderClosed) {
		this.orderClosed = orderClosed;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Double getSalary() {
		return salary;
	}

	public void setSalary(Double salary) {
		this.salary = salary;
	}
}
