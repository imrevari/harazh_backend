package ua.com.harazh.oblik.validator.order;

import java.util.Objects;
import java.util.Optional;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import ua.com.harazh.oblik.domain.Car;
import ua.com.harazh.oblik.domain.Customer;
import ua.com.harazh.oblik.domain.dto.CreateOrderDto;
import ua.com.harazh.oblik.repository.CarRepository;
import ua.com.harazh.oblik.repository.CustomerRepository;

@Component
public class CreateOrdervalidator implements Validator{
	
	private CarRepository carRepository;
	
	private CustomerRepository customerRepository;

	public CreateOrdervalidator(CarRepository carRepository, CustomerRepository customerRepository) {
		super();
		this.carRepository = carRepository;
		this.customerRepository = customerRepository;
	}

	@Override
	public boolean supports(Class<?> clazz) {
		return CreateOrderDto.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		CreateOrderDto createOrderDto = (CreateOrderDto) target;
		
		if(!Objects.isNull(createOrderDto.getCar()) && !Objects.isNull(createOrderDto.getCustomer())) {
			Optional<Car> car = carRepository.findById(createOrderDto.getCar());
			
			Optional<Customer> customer = customerRepository.findById(createOrderDto.getCustomer());
			if (!car.isPresent()) {
				errors.rejectValue("car", "car.wrongId");
			}	
			if(!customer.isPresent()) {
				errors.rejectValue("customer", "cust.wrongId");
			}
		}
		
	}

}
