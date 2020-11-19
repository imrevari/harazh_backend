package ua.com.harazh.oblik.domain.dto;

import javax.validation.constraints.NotBlank;

public class CreateCustomerDto {
	
	@NotBlank(message = "{name.notNull}")
	private String firstName;
	
	private String lastName;
	
	private String telNumber;
	
	private String email;

	public CreateCustomerDto() {
		super();
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getTelNumber() {
		return telNumber;
	}

	public void setTelNumber(String telNumber) {
		this.telNumber = telNumber;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	
	

}
