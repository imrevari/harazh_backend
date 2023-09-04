package ua.com.harazh.oblik.domain;

import javax.persistence.*;

import ua.com.harazh.oblik.domain.dto.CreateCarDto;

import java.util.List;

@Entity
public class Car {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable = false)
	private String vinCode;
	
	private String licencePlate;
	
	private String carMade;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "car")
	private List<RepairOrder> repairOrders;
	

	public Car() {
		super();
	}
	
	


	public Car(CreateCarDto car) {
		super();
		this.vinCode = car.getVinCode().trim();
		this.licencePlate = car.getLicencePlate();
		this.carMade = car.getCarMade();
	}




	public String getVinCode() {
		return vinCode;
	}


	public void setVinCode(String vinCode) {
		this.vinCode = vinCode;
	}


	public String getLicencePlate() {
		return licencePlate;
	}


	public void setLicencePlate(String licencePlate) {
		this.licencePlate = licencePlate;
	}


	public String getCarMade() {
		return carMade;
	}


	public void setCarMade(String carMade) {
		this.carMade = carMade;
	}


	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}

	public List<RepairOrder> getRepairOrders() {
		return repairOrders;
	}

	public void setRepairOrders(List<RepairOrder> repairOrders) {
		this.repairOrders = repairOrders;
	}
}
