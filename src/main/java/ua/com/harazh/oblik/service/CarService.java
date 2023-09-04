package ua.com.harazh.oblik.service;

import java.util.*;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ua.com.harazh.oblik.domain.Car;
import ua.com.harazh.oblik.domain.Customer;
import ua.com.harazh.oblik.domain.dto.CreateCarDto;
import ua.com.harazh.oblik.domain.dto.ResponseCarDto;
import ua.com.harazh.oblik.domain.dto.UpdateCarDto;
import ua.com.harazh.oblik.exception.ExceptionWithMessage;
import ua.com.harazh.oblik.repository.CarRepository;

@Service
@Transactional
@PropertySource("classpath:messages.properties")
public class CarService {
	
	
	private CarRepository carRepository;
	
	@Value("${car.wrongId}")
	private String wrongCarIdMessage;
	
	
	@Autowired
	public CarService(CarRepository carRepository) {
		super();
		this.carRepository = carRepository;
	}


	public List<ResponseCarDto> getAllCars() {
		List<Car> listToReturn = carRepository.findAll();
		Collections.sort(listToReturn, (b, a) -> Integer.compare(a.getRepairOrders().size(), b.getRepairOrders().size()));
		return carListToDtoOrNewList(listToReturn);
	}

	public ResponseCarDto getCarById(Long productId) {
		Optional<Car> carToReturn = carRepository.findById(productId);
		
		return carOptionalToDtoOrNull(carToReturn);
		
	}

	public ResponseCarDto getCarByVin(String vin) {
		Optional<Car> carToReturn = carRepository.findByVinCode(vin);
		
		return carOptionalToDtoOrNull(carToReturn);
	}

	public List<ResponseCarDto> getCarsByPartialVin(String vin) {
		List<Car> listToReturn = carRepository.findByVinCodeContaining(vin);
		
		return carListToDtoOrNewList(listToReturn);
	}
	
	public ResponseCarDto save(CreateCarDto carDto) {
		
		Car carToReturn = carRepository.save(new Car(carDto));
		
		return new ResponseCarDto(carToReturn);
	}

	public ResponseCarDto updateCar(UpdateCarDto createCarDto, Long carId) {
		
		Optional<Car> carFromDb = carRepository.findById(carId);

		if (!carFromDb.isPresent()) {
			throw new ExceptionWithMessage(wrongCarIdMessage, "id");
		}
		
		Car toReturn = updateAndSave(carFromDb, createCarDto);
		
		return new ResponseCarDto(toReturn);
	}

	public List<ResponseCarDto> getAllCarByCustomer(Customer customer) {

		List<Car> cars = carRepository.findByCustomer(customer.getId());
		return carListToDtoOrNewList(cars);
	}
	
	
	private Car updateAndSave(Optional<Car> carFromDb, UpdateCarDto createCarDto) {
		Car carToUpdate = carFromDb.get();
		
		if (!Objects.isNull(createCarDto.getVinCode())) { carToUpdate.setVinCode(createCarDto.getVinCode());}
		if (!Objects.isNull(createCarDto.getLicencePlate())) { carToUpdate.setLicencePlate(createCarDto.getLicencePlate());}
		if (!Objects.isNull(createCarDto.getCarMade())) { carToUpdate.setCarMade(createCarDto.getCarMade());}
		
		return carRepository.save(carToUpdate);
	}
	
	private List<ResponseCarDto> carListToDtoOrNewList(List<Car> listToReturn) {
		if (listToReturn.isEmpty()) {
			return new ArrayList<ResponseCarDto>();
		}
		return listToReturn.stream().map((e) -> new ResponseCarDto(e)).collect(Collectors.toList());
	}

	private ResponseCarDto carOptionalToDtoOrNull(Optional<Car> carToReturn) {
		return carToReturn.map((e) -> new ResponseCarDto(e)).orElse(null);
	}
}
