package ua.com.harazh.oblik.controller;

import java.util.List;
import java.util.Objects;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ua.com.harazh.oblik.domain.dto.CreateCustomerDto;
import ua.com.harazh.oblik.domain.dto.NameDto;
import ua.com.harazh.oblik.domain.dto.ResponseCustomerDto;
import ua.com.harazh.oblik.domain.dto.UpdateCustomerDto;
import ua.com.harazh.oblik.service.CustomerService;
import ua.com.harazh.oblik.validator.customer.ExistingCustomerValidator;
import ua.com.harazh.oblik.validator.customer.UpdateCustomerValidator;

@RestController
@RequestMapping("/")
public class CustomerController {
	
	
	private CustomerService customerService;
	
	private ExistingCustomerValidator existingCustomerValidator;
	
	private UpdateCustomerValidator udateCustomerValidator;

	@Autowired
	public CustomerController(CustomerService customerService, ExistingCustomerValidator existingCustomerValidator,
			UpdateCustomerValidator udateCustomerValidator) {
		super();
		this.customerService = customerService;
		this.existingCustomerValidator = existingCustomerValidator;
		this.udateCustomerValidator = udateCustomerValidator;
	}
	
	@InitBinder("createCustomerDto")
    protected void initNewCustomerBinder(WebDataBinder binder) {
        binder.addValidators(existingCustomerValidator);
    }
	
	@InitBinder("updateCustomerDto")
    protected void initUpdateCustomerBinder(WebDataBinder binder) {
        binder.addValidators(udateCustomerValidator);
    }


	@PostMapping
	@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_SENIOR_USER')")
	public ResponseEntity<ResponseCustomerDto> createNewCustomer(@Valid @RequestBody CreateCustomerDto createCustomerDto){
		
		ResponseCustomerDto responseCustomerDto = customerService.createNewCustomer(createCustomerDto);
		
		if(Objects.isNull(responseCustomerDto)) {
			return new ResponseEntity<>(HttpStatus.CONFLICT);
		}
		
		
		return new ResponseEntity<>(responseCustomerDto, HttpStatus.CREATED);
	}
	
	@GetMapping
	public ResponseEntity<List<ResponseCustomerDto>> getAllCustomers(){
		
		List<ResponseCustomerDto> listToReturn = customerService.getAllCustomer();
		return new ResponseEntity<>(listToReturn, HttpStatus.OK);
	}

	@GetMapping("/with_cars")
	public ResponseEntity<List<ResponseCustomerDto>> getAllCustomersWithCars(){
		List<ResponseCustomerDto> listToReturn = customerService.getAllCustomerWithCars();
		return new ResponseEntity<>(listToReturn, HttpStatus.OK);
	}
	
	@GetMapping("/name")
	public ResponseEntity<List<ResponseCustomerDto>> getCustomersByFirstName(@RequestBody NameDto name){
		
		List<ResponseCustomerDto> listToReturn = customerService.findCustomersByName(name.getName().trim());
		
		if (listToReturn.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<>(listToReturn, HttpStatus.OK);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<ResponseCustomerDto> getCustomerById(@PathVariable Long id){
		
		ResponseCustomerDto listToReturn = customerService.findCustomersById(id);
		
		if (Objects.isNull(listToReturn)) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<>(listToReturn, HttpStatus.OK);
	}

	@GetMapping("/cust_for_order/{id}")
	public ResponseEntity<ResponseCustomerDto> getCustomerByIdWithCars(@PathVariable Long id){
		ResponseCustomerDto listToReturn = customerService.findCustomersWithCarsById(id);
		if (Objects.isNull(listToReturn)) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(listToReturn, HttpStatus.OK);
	}
	
	@PutMapping("/{id}")
	@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_SENIOR_USER')")
	public ResponseEntity<ResponseCustomerDto> updateCustomer(@PathVariable Long id, @Valid @RequestBody UpdateCustomerDto updateCustomerDto){
		ResponseCustomerDto listToReturn = customerService.updateCustomer(id, updateCustomerDto);
		
		if (Objects.isNull(listToReturn)) {
			return new ResponseEntity<>(HttpStatus.CONFLICT);
		}
		
		return new ResponseEntity<>(listToReturn, HttpStatus.OK);
	}
	

}
