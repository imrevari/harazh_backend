package ua.com.harazh.oblik.domain.dto;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import ua.com.harazh.oblik.domain.RepairOrder;

public class ResponseOrderDto {
	
	
	private Long id;

	private ResponseCustomerDto customer;

	private ResponseCarDto car;

	private List<ResponseWorkDto> works;

	private List<ResponsePartCountDto> partCounts;
	
	private String orderOpened;
	
	private String orderClosed;
	
	private boolean payedFor;
	
	private Double amountPayedInAdvance;

	private String problem;
	
	

	public ResponseOrderDto() {
		super();
	}



	public ResponseOrderDto(RepairOrder order) {
		super();
		this.id = order.getId();
		this.customer = new ResponseCustomerDto(order.getCustomer());
		this.car = new ResponseCarDto(order.getCar());	
		if (!Objects.isNull(order.getWorks())) {
			this.works = order.getWorks().stream().map((e) -> new ResponseWorkDto(e)).collect(Collectors.toList());
		}
		if (!Objects.isNull(order.getPartCounts())) {
			this.partCounts = order.getPartCounts().stream().map((e) -> new ResponsePartCountDto(e)).collect(Collectors.toList());
		}
		
		//TODO if date not appearing correclty fix it
		this.orderOpened = order.getOrderOpened().toString();
		if (!Objects.isNull(order.getOrderClosed())) {
			this.orderClosed = order.getOrderClosed().toString();
		}	
		this.payedFor = order.isPayedFor();
		this.amountPayedInAdvance = order.getAmountPayedInAdvance();
		this.problem = order.getProblem();
	}



	public Long getId() {
		return id;
	}



	public void setId(Long id) {
		this.id = id;
	}



	public ResponseCustomerDto getCustomer() {
		return customer;
	}



	public void setCustomer(ResponseCustomerDto customer) {
		this.customer = customer;
	}



	public ResponseCarDto getCar() {
		return car;
	}



	public void setCar(ResponseCarDto car) {
		this.car = car;
	}



	public List<ResponseWorkDto> getWorks() {
		return works;
	}



	public void setWorks(List<ResponseWorkDto> works) {
		this.works = works;
	}



	public List<ResponsePartCountDto> getPartCounts() {
		return partCounts;
	}



	public void setPartCounts(List<ResponsePartCountDto> partCounts) {
		this.partCounts = partCounts;
	}



	public String getOrderOpened() {
		return orderOpened;
	}



	public void setOrderOpened(String orderOpened) {
		this.orderOpened = orderOpened;
	}



	public String getOrderClosed() {
		return orderClosed;
	}



	public void setOrderClosed(String orderClosed) {
		this.orderClosed = orderClosed;
	}



	public boolean isPayedFor() {
		return payedFor;
	}



	public void setPayedFor(boolean payedFor) {
		this.payedFor = payedFor;
	}



	public Double getAmountPayedInAdvance() {
		return amountPayedInAdvance;
	}



	public void setAmountPayedInAdvance(Double amountPayedInAdvance) {
		this.amountPayedInAdvance = amountPayedInAdvance;
	}



	public String getProblem() {
		return problem;
	}



	public void setProblem(String problem) {
		this.problem = problem;
	}
	
	
	

}
