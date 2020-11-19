package ua.com.harazh.oblik.domain.dto;

public class CreateCarDto {
	
//	@NotBlank(message = "{vin.null}")
	private String vinCode;
	
	private String licencePlate;
	
	private String carMade;
	

	public CreateCarDto() {
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


	
	
	
	

}
