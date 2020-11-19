package ua.com.harazh.oblik.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ua.com.harazh.oblik.domain.Customer;
import ua.com.harazh.oblik.domain.dto.CreateCustomerDto;
import ua.com.harazh.oblik.domain.dto.ResponseCustomerDto;
import ua.com.harazh.oblik.domain.dto.UpdateCustomerDto;
import ua.com.harazh.oblik.exception.ExceptionWithMessage;
import ua.com.harazh.oblik.repository.CustomerRepository;

@Service
@Transactional
@PropertySource("classpath:messages.properties")
public class CustomerService {
	
	
	private CustomerRepository customerRepository;
	
	@Value("${cust.wrongId}")
	private String wrongCustomerIdMessage;
	
	@Value("${cust.alreadyExists}")
	private String customerAlreadyExistsMessage;

	@Autowired
	public CustomerService(CustomerRepository customerRepository) {
		super();
		this.customerRepository = customerRepository;
	}

	public ResponseCustomerDto createNewCustomer(CreateCustomerDto createCustomerDto) {
	
		Customer toReturn = customerRepository.save(new Customer(createCustomerDto));
		
		return new ResponseCustomerDto(toReturn);
		
	}

	public List<ResponseCustomerDto> getAllCustomer() {
		
		List<Customer> listOfCustomers = customerRepository.findAll();

		return entityListToDtoOrNewList(listOfCustomers);
	}
	
	public List<ResponseCustomerDto> findCustomersByName(String name) {
		
		List<Customer> listOfCustomers = customerRepository.findByFirstName(name);

		return entityListToDtoOrNewList(listOfCustomers);
	}
	
	public ResponseCustomerDto findCustomersById(Long id) {
		Optional<Customer> optional = customerRepository.findById(id);
		
		if (!optional.isPresent()) {
			return null;
		}
		
		return new ResponseCustomerDto(optional.get());
	}
	
	public ResponseCustomerDto updateCustomer(Long id, UpdateCustomerDto updateCustomerDto) {
		
		Optional<Customer> optional = customerRepository.findById(id);
		
		if (!optional.isPresent()) {
			throw new ExceptionWithMessage(wrongCustomerIdMessage, "id");
		}
		
		Customer customer = optional.get();
		
		if (!Objects.isNull(updateCustomerDto.getFirstName()) ) { customer.setFirstName(updateCustomerDto.getFirstName().trim()); }
		if (!Objects.isNull(updateCustomerDto.getLastName()) ) { customer.setLastName(updateCustomerDto.getLastName().trim()); }
		if (!Objects.isNull(updateCustomerDto.getEmail()) ) { customer.setEmail(updateCustomerDto.getEmail().trim()); }
		if (!Objects.isNull(updateCustomerDto.getTelNumber()) ) { customer.setTelNumber(updateCustomerDto.getTelNumber().trim()); }
		
		
		Optional<Customer> customerInDb = customerRepository.findByFirstNameAndLastNameAndTelNumber(customer.getFirstName(),
				customer.getLastName(), customer.getTelNumber());
		
		if(customerInDb.isPresent() && (customerInDb.get().getId() != updateCustomerDto.getId())) {		
			throw new ExceptionWithMessage(customerAlreadyExistsMessage, "firstName");
		}
				
		Customer toReturn = customerRepository.save(customer);
		
		return new ResponseCustomerDto(toReturn);
	}
	
	
	
	
	
	private List<ResponseCustomerDto> entityListToDtoOrNewList(List<Customer> listOfCustomers){
		if (listOfCustomers.isEmpty()) {
			return new ArrayList<ResponseCustomerDto>();
		}
		
		return listOfCustomers.stream().map((e) -> new ResponseCustomerDto(e)).collect(Collectors.toList());
	}

	

	

	
	
	
	

}
