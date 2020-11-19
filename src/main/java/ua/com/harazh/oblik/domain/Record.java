package ua.com.harazh.oblik.domain;

import javax.persistence.*;

@Entity
public class Record {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "oblik_user_id", nullable = false)
	private OblikUser oblikUser;
	
	private String action;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "car_id")
	private Car car;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "repair_order_id")
	private RepairOrder repairOrder;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "work_id")
	private Work work;
	

	public Record() {
		super();
	}


	public String getAction() {
		return action;
	}


	public void setAction(String action) {
		this.action = action;
	}


	public Car getCar() {
		return car;
	}


	public void setCar(Car car) {
		this.car = car;
	}


	public Work getWork() {
		return work;
	}


	public void setWork(Work work) {
		this.work = work;
	}


	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public OblikUser getOblikUser() {
		return oblikUser;
	}


	public void setOblikUser(OblikUser oblikUser) {
		this.oblikUser = oblikUser;
	}


	public RepairOrder getRepairOrder() {
		return repairOrder;
	}


	public void setRepairOrder(RepairOrder repairOrder) {
		this.repairOrder = repairOrder;
	}


	
	
	
	
	
	

}
