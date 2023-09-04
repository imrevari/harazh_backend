package ua.com.harazh.oblik.domain.dto;

import ua.com.harazh.oblik.domain.Customer;

import java.util.List;

public class ResponseCustomerDto {
	
	
	private Long id;
	
	private String firstName;
	
	private String lastName;
	
	private String telNumber;
	
	private String email;

	private List<ResponseCarDto> listOfCars;

	public ResponseCustomerDto() {
		super();
	}

	public ResponseCustomerDto(Customer customer) {
		super();
		this.id = customer.getId();
		this.firstName = customer.getFirstName();
		this.lastName = customer.getLastName();
		this.telNumber = customer.getTelNumber();
		this.email = customer.getEmail();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public List<ResponseCarDto> getListOfCars() {
		return listOfCars;
	}

	public void setListOfCars(List<ResponseCarDto> listOfCars) {
		this.listOfCars = listOfCars;
	}
}
