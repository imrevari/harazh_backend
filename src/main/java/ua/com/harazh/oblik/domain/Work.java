package ua.com.harazh.oblik.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
public class Work {
	
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	//good connection
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "work_type_id")
	private WorkType workType;
	
	@Column(nullable = false)
	private boolean workDone;

	@Column(columnDefinition = "TIMESTAMP")
	private LocalDateTime doneAt;
	
	private String workName;

	private Double price;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "oblik_user_id")
	private OblikUser oblikUser;
	
	private Double salary;

	public Work() {
		super();
	}
	
	

	public Work(WorkType workType, boolean cementBeforeDone, OblikUser oblikUser) {
		super();
		//if value is true, use diff constructor
		if(cementBeforeDone) {
			this.setPrice(workType.getPrice());
			this.setWorkName(workType.getName());
		}
		this.workType = workType;
		this.workDone = false;
		this.oblikUser = oblikUser;
		if(Objects.isNull(workType.getSalary())){
			this.salary = oblikUser.getPercentage() * workType.getPrice();
		}else{
			this.salary = workType.getSalary();
		}

	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public boolean isWorkDone() {
		return workDone;
	}

	public void setWorkDone(boolean workDone) {
		this.workDone = workDone;
	}

	public WorkType getWorkType() {
		return workType;
	}

	public void setWorkType(WorkType workType) {
		this.workType = workType;
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

	public OblikUser getOblikUser() {
		return oblikUser;
	}

	public void setOblikUser(OblikUser oblikUser) {
		this.oblikUser = oblikUser;
	}

	public LocalDateTime getDoneAt() {
		return doneAt;
	}

	public void setDoneAt(LocalDateTime doneAt) {
		this.doneAt = doneAt;
	}

	public Double getSalary() {
		return salary;
	}

	public void setSalary(Double salary) {
		this.salary = salary;
	}
}
