package ua.com.harazh.oblik.validator.customer;

import java.util.Objects;
import java.util.Optional;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import ua.com.harazh.oblik.domain.Customer;
import ua.com.harazh.oblik.domain.dto.CreateCustomerDto;
import ua.com.harazh.oblik.repository.CustomerRepository;

@Component
public class ExistingCustomerValidator implements Validator{
	
	private CustomerRepository customerRepository;
	
	

	public ExistingCustomerValidator(CustomerRepository customerRepository) {
		super();
		this.customerRepository = customerRepository;
	}

	@Override
	public boolean supports(Class<?> clazz) {
		return CreateCustomerDto.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		CreateCustomerDto createCustomerDto = (CreateCustomerDto) target;

		if(!Objects.isNull(createCustomerDto.getFirstName()) && !Objects.isNull(createCustomerDto.getLastName()) && !Objects.isNull(createCustomerDto.getTelNumber()) ) {
			Optional<Customer> customerInDb = customerRepository.findByFirstNameAndLastNameAndTelNumber(createCustomerDto.getFirstName().trim(),
					createCustomerDto.getLastName().trim(), createCustomerDto.getTelNumber().trim());
			
			if(customerInDb.isPresent()) {		
				errors.rejectValue("firstName", "cust.alreadyExists");
				errors.rejectValue("lastName", "cust.alreadyExists");
				errors.rejectValue("telNumber", "cust.alreadyExists");
			}
		}
		

		if(!Objects.isNull(createCustomerDto.getTelNumber())) {
//			 if (!createCustomerDto.getTelNumber().matches("^[\\+]([0-9]{3,6})-([0-9]{5,8})$")) {
			 if (!createCustomerDto.getTelNumber().matches("^[\\+]([0-9]{10,14})$")) {
		            errors.rejectValue("telNumber", "tell.wrongFormat");
			 }
		}
		
		if(!createCustomerDto.getEmail().equals("") && !createCustomerDto.getEmail().matches(
	                "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$")) {
	            errors.rejectValue("email", "email.invalidInput");
	    }	
		 
	}

}
