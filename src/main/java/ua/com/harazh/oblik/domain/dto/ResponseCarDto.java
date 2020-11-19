package ua.com.harazh.oblik.domain.dto;

import javax.validation.constraints.NotNull;

import ua.com.harazh.oblik.domain.Car;

public class ResponseCarDto {
	
	private Long id;
	
	@NotNull
	private String vinCode;
	
	private String licencePlate;
	
	private String carMade;

	public ResponseCarDto() {
		super();
	}

	public ResponseCarDto(Car car) {
		super();
		this.id = car.getId();
		this.vinCode = car.getVinCode();
		this.licencePlate = car.getLicencePlate();
		this.carMade = car.getCarMade();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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
	
	
	

}
