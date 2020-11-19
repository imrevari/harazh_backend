package ua.com.harazh.oblik.controller;

import java.util.List;
import java.util.Objects;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ua.com.harazh.oblik.domain.dto.CreateCarDto;
import ua.com.harazh.oblik.domain.dto.ResponseCarDto;
import ua.com.harazh.oblik.domain.dto.UpdateCarDto;
import ua.com.harazh.oblik.service.CarService;
import ua.com.harazh.oblik.validator.car.NewCarExistingVinValidator;
import ua.com.harazh.oblik.validator.car.UpdateCarExistingVinValidator;

@RestController
@RequestMapping("/cars")
public class CarController {
	
	
	private CarService carService;
	private NewCarExistingVinValidator newCarExistingVinValidator;
	private UpdateCarExistingVinValidator updateCarExistingVinValidator;

	@Autowired
	public CarController(CarService carService, NewCarExistingVinValidator carExistingVinValidator, UpdateCarExistingVinValidator updateCarExistingVinValidator) {
		super();
		this.carService = carService;
		this.newCarExistingVinValidator = carExistingVinValidator;
		this.updateCarExistingVinValidator = updateCarExistingVinValidator;
	}
	
	@InitBinder("createCarDto")
    protected void initNewCarBinder(WebDataBinder binder) {
        binder.addValidators(newCarExistingVinValidator);
    }
	
	@InitBinder("updateCarDto")
    protected void initUpdateCarBinder(WebDataBinder binder) {
        binder.addValidators(updateCarExistingVinValidator);
    }
	
	

	@GetMapping
	public ResponseEntity<List<ResponseCarDto>> getAllPartsWithRetailPrice() {
        List<ResponseCarDto> carListItems = carService.getAllCars();

        return new ResponseEntity<>(carListItems, HttpStatus.OK);
    }
	
	@GetMapping("/id/{carId}")
	public ResponseEntity<ResponseCarDto> getCarById(@PathVariable Long carId) {
				
        ResponseCarDto carToReturn = carService.getCarById(carId);

        return objectToResponseOrNotFound(carToReturn);
    }
	
	@GetMapping("/vin/{vin}")
	public ResponseEntity<ResponseCarDto> getCarByFullVin(@PathVariable String vin) {
				
        ResponseCarDto carToReturn = carService.getCarByVin(vin);

        return objectToResponseOrNotFound(carToReturn);
    }
	
	@GetMapping("/p_vin/{vin}")
	public ResponseEntity<List<ResponseCarDto>> getCarByPartialVin(@PathVariable String vin) {
				
		List<ResponseCarDto> carListItems = carService.getCarsByPartialVin(vin);

		
		//TODO maybe return empty list
        if (carListItems.isEmpty()) {
        	return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(carListItems, HttpStatus.OK);
    }
	
	
	@PostMapping
	public ResponseEntity<ResponseCarDto> createNewCar(@Valid @RequestBody CreateCarDto createCarDto) {
		
		ResponseCarDto carToReturn = carService.save(createCarDto);
		
		if (Objects.isNull(carToReturn)) {
			return new ResponseEntity<>(HttpStatus.CONFLICT);
		}

        return new ResponseEntity<>(carToReturn, HttpStatus.CREATED);
    }
	
	@PutMapping("/{carId}")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<ResponseCarDto> updateCar(@Valid @RequestBody UpdateCarDto updateCarDto, @PathVariable Long carId) {
		
		ResponseCarDto carToReturn = carService.updateCar(updateCarDto, carId);

        return objectToResponseOrNotFound(carToReturn);
    }
	
	
	@DeleteMapping("/{carId}")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<?> deleteCar(@PathVariable Long carId) {
		
		//TODO write code

        return new ResponseEntity<>( HttpStatus.OK);
    } 
	
	
	
	
	private <T>  ResponseEntity<T> objectToResponseOrNotFound(T dto){
		if (Objects.isNull(dto)) {
        	return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(dto, HttpStatus.OK);
	}

	
}
