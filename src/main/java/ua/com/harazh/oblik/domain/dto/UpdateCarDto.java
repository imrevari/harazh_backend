package ua.com.harazh.oblik.domain.dto;

public class UpdateCarDto {
	
//	@NotBlank(message = "{vin.null}")
	private String vinCode;
	
	private String licencePlate;
	
	private String carMade;
	
	private Long id;

	public UpdateCarDto() {
		super();
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
	
	
	

}
