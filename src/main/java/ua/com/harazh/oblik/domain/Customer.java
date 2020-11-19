package ua.com.harazh.oblik.domain;

import java.util.List;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import ua.com.harazh.oblik.domain.dto.CreateCustomerDto;


@Entity
public class Customer {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable = false)
	private String firstName;
	
	private String lastName;
	
	private String telNumber;
	
	private String email;

	//good connection
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "customer")
	private List<RepairOrder> repairOrders;
	
	

	public Customer() {
		super();
	}
	
	



	public Customer(CreateCustomerDto dto) {
		super();
		this.firstName = dto.getFirstName().trim();
		
		if(!Objects.isNull(dto.getLastName())) {
			this.lastName = dto.getLastName().trim();
		}
		if(!Objects.isNull(dto.getTelNumber())) {
			this.telNumber = dto.getTelNumber().trim();
		}
		if(!Objects.isNull(dto.getEmail())) {
			this.email = dto.getEmail().trim();
		}
		
		
		
	}





	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	


	public List<RepairOrder> getRepairOrders() {
		return repairOrders;
	}



	public void setRepairOrders(List<RepairOrder> repairOrders) {
		this.repairOrders = repairOrders;
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



	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((firstName == null) ? 0 : firstName.hashCode());
		result = prime * result + ((lastName == null) ? 0 : lastName.hashCode());
		result = prime * result + ((telNumber == null) ? 0 : telNumber.hashCode());
		return result;
	}



	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Customer other = (Customer) obj;
		if (firstName == null) {
			if (other.firstName != null)
				return false;
		} else if (!firstName.equals(other.firstName))
			return false;
		if (lastName == null) {
			if (other.lastName != null)
				return false;
		} else if (!lastName.equals(other.lastName))
			return false;
		if (telNumber == null) {
			if (other.telNumber != null)
				return false;
		} else if (!telNumber.equals(other.telNumber))
			return false;
		return true;
	}
	
	
	
	
	

}
