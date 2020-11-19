package ua.com.harazh.oblik.validator.customer;

import java.util.Objects;
import java.util.Optional;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import ua.com.harazh.oblik.domain.Customer;
import ua.com.harazh.oblik.domain.dto.UpdateCustomerDto;
import ua.com.harazh.oblik.repository.CustomerRepository;

@Component
public class UpdateCustomerValidator implements Validator{
	
private CustomerRepository customerRepository;
	
	

	public UpdateCustomerValidator(CustomerRepository customerRepository) {
		super();
		this.customerRepository = customerRepository;
	}

	@Override
	public boolean supports(Class<?> clazz) {
		return UpdateCustomerDto.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		UpdateCustomerDto updateCustomerDto = (UpdateCustomerDto) target;
		
		if(!Objects.isNull(updateCustomerDto.getFirstName()) && !Objects.isNull(updateCustomerDto.getLastName()) && !Objects.isNull(updateCustomerDto.getTelNumber()) ) {
			Optional<Customer> customerInDb = customerRepository.findByFirstNameAndLastNameAndTelNumber(updateCustomerDto.getFirstName().trim(),
					updateCustomerDto.getLastName().trim(), updateCustomerDto.getTelNumber().trim());
			
			if(customerInDb.isPresent() && (customerInDb.get().getId() != updateCustomerDto.getId()) ) {
//				System.out.println("I was called " + updateCustomerDto.getId());
				errors.rejectValue("firstName", "cust.alreadyExists");
			}
		}
		
		if(!updateCustomerDto.getEmail().equals("") && !updateCustomerDto.getEmail().matches(
                "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$")) {
            errors.rejectValue("email", "email.invalidInput");
    }	
	}

}
