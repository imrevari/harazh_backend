package ua.com.harazh.oblik.service;

import java.util.*;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ua.com.harazh.oblik.domain.Customer;
import ua.com.harazh.oblik.domain.dto.CreateCustomerDto;
import ua.com.harazh.oblik.domain.dto.ResponseCarDto;
import ua.com.harazh.oblik.domain.dto.ResponseCustomerDto;
import ua.com.harazh.oblik.domain.dto.UpdateCustomerDto;
import ua.com.harazh.oblik.exception.ExceptionWithMessage;
import ua.com.harazh.oblik.repository.CustomerRepository;

@Service
@Transactional
@PropertySource("classpath:messages.properties")
public class CustomerService {
	
	
	private CustomerRepository customerRepository;

	private CarService carService;
	
	@Value("${cust.wrongId}")
	private String wrongCustomerIdMessage;
	
	@Value("${cust.alreadyExists}")
	private String customerAlreadyExistsMessage;

	@Autowired
	public CustomerService(CustomerRepository customerRepository, CarService carService) {
		super();
		this.customerRepository = customerRepository;
		this.carService = carService;
	}

	public ResponseCustomerDto createNewCustomer(CreateCustomerDto createCustomerDto) {
	
		Customer toReturn = customerRepository.save(new Customer(createCustomerDto));
		
		return new ResponseCustomerDto(toReturn);
		
	}

	public List<ResponseCustomerDto> getAllCustomer() {
		
		List<Customer> listOfCustomers = customerRepository.findAll();
		Collections.sort(listOfCustomers, (a, b) -> Integer.compare(b.getRepairOrders().size(), a.getRepairOrders().size()));
		
		return entityListToDtoOrNewList(listOfCustomers);
	}

	public List<ResponseCustomerDto> getAllCustomerWithCars() {
		List<Customer> listOfCustomers = customerRepository.findAll();
		Collections.sort(listOfCustomers, (a, b) -> Integer.compare(b.getRepairOrders().size(), a.getRepairOrders().size()));
		return entityListToDtoWithCarsOrNewList(listOfCustomers);
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

	public ResponseCustomerDto findCustomersWithCarsById(Long id) {
		Optional<Customer> optional = customerRepository.findById(id);
		if (!optional.isPresent()) {
			return null;
		}
		ResponseCustomerDto responseCustomerDto = new ResponseCustomerDto(optional.get());
		List<ResponseCarDto> cars = carService.getAllCarByCustomer(optional.get());
		responseCustomerDto.setListOfCars(cars);
		return responseCustomerDto;
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
		
		if(customerInDb.isPresent() && !(customerInDb.get().getId().equals(updateCustomerDto.getId()))) {
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

	private List<ResponseCustomerDto> entityListToDtoWithCarsOrNewList(List<Customer> listOfCustomers){
		if (listOfCustomers.isEmpty()) {
			return new ArrayList<ResponseCustomerDto>();
		}
		return listOfCustomers.stream().map(
				(e) ->
				{
					ResponseCustomerDto responseCustomerDto = new ResponseCustomerDto(e);
					List<ResponseCarDto> cars = carService.getAllCarByCustomer(e);
					responseCustomerDto.setListOfCars(cars);
					return responseCustomerDto;
				}
		).collect(Collectors.toList());
	}

	

	

	
	
	
	

}
