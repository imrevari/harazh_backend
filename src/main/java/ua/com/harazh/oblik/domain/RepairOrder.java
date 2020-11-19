package ua.com.harazh.oblik.domain;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;


@Entity
public class RepairOrder {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	//good connection
	@ManyToOne
	@JoinColumn(name = "customer_id", nullable = false)
	private Customer customer;

	//good connection
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "car_id")
	private Car car;

	//good connection
	@OneToMany(fetch = FetchType.LAZY)
	@JoinColumn(name = "repair_order_id")
	private List<Work> works;

	//good connection
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "repairOrder")
	private List<PartCount> partCounts;
	
	@Column(columnDefinition = "TIMESTAMP", nullable = false)
	private LocalDateTime orderOpened;
	
	@Column(columnDefinition = "TIMESTAMP")
	private LocalDateTime orderClosed;
	
	private boolean payedFor;
	
	private Double amountPayedInAdvance;
	
	
	@Column(columnDefinition = "TEXT", nullable = false)
	private String problem;
	

	public RepairOrder() {
		super();
	}
	
	


	public RepairOrder(Customer customer, Car car) {
		super();
		this.customer = customer;
		this.car = car;
		this.orderOpened = LocalDateTime.now();
		this.payedFor = false;
	}




	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public Customer getCustomer() {
		return customer;
	}


	public void setCustomer(Customer customer) {
		this.customer = customer;
	}


	public List<Work> getWorks() {
		return works;
	}


	public void setWorks(List<Work> works) {
		this.works = works;
	}


	public LocalDateTime getOrderOpened() {
		return orderOpened;
	}


	public void setOrderOpened(LocalDateTime orderOpened) {
		this.orderOpened = orderOpened;
	}


	public LocalDateTime getOrderClosed() {
		return orderClosed;
	}


	public void setOrderClosed(LocalDateTime orderClosed) {
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


	public Car getCar() {
		return car;
	}


	public void setCar(Car car) {
		this.car = car;
	}


	public List<PartCount> getPartCounts() {
		return partCounts;
	}


	public void setPartCounts(List<PartCount> partCounts) {
		this.partCounts = partCounts;
	}


	public String getProblem() {
		return problem;
	}


	public void setProblem(String problem) {
		this.problem = problem;
	}

	
	
	
	
	

}
