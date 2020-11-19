package ua.com.harazh.oblik.validator.car;

import java.util.Objects;
import java.util.Optional;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import ua.com.harazh.oblik.domain.Car;
import ua.com.harazh.oblik.domain.dto.CreateCarDto;
import ua.com.harazh.oblik.repository.CarRepository;

@Component
public class NewCarExistingVinValidator  implements Validator{
	
	
	private CarRepository carRepository;
	
	

	public NewCarExistingVinValidator(CarRepository carRepository) {
		this.carRepository = carRepository;
	}

	@Override
	public boolean supports(Class<?> clazz) {
		return CreateCarDto.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
	
		CreateCarDto dto = (CreateCarDto)target;
		
		if(!Objects.isNull(dto.getVinCode()) && !dto.getVinCode().isEmpty()) {
			Optional<Car> carFromDb = carRepository.findByVinCode(dto.getVinCode());
			
			if (carFromDb.isPresent()) {
				errors.rejectValue("vinCode", "vin.alreadyExists");
			}
		}
		
		if(!Objects.isNull(dto.getLicencePlate())) {
			
			Optional<Car> carFromDb = carRepository.findByLicencePlate(dto.getLicencePlate());
			
			if (carFromDb.isPresent()) {
				errors.rejectValue("licencePlate", "licensePlate.alreadyExists");
			}
		}
	}

}
