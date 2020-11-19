package ua.com.harazh.oblik.domain.dto;


import ua.com.harazh.oblik.domain.Work;

public class ResponseCompletedWorkDto {
	
	
	private String workName;
	
	private Double price;
	
	private String licencePlate;
	
	private Long orderId;
	
	private String orderClosed;
	
	public ResponseCompletedWorkDto() {
		super();
	}
	
	public ResponseCompletedWorkDto(Work work, ResponseOrderDto responseOrderDto) {
		this.workName = work.getWorkName();
		this.price = work.getPrice();
		this.licencePlate = responseOrderDto.getCar().getLicencePlate();
		this.orderId = responseOrderDto.getId();
		this.orderClosed = responseOrderDto.getOrderClosed();
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
	
	
	

}
