package ua.com.harazh.oblik.validator.car;

import java.util.Objects;
import java.util.Optional;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import ua.com.harazh.oblik.domain.Car;
import ua.com.harazh.oblik.domain.dto.UpdateCarDto;
import ua.com.harazh.oblik.repository.CarRepository;

@Component
public class UpdateCarExistingVinValidator implements Validator{
	
	private CarRepository carRepository;

	public UpdateCarExistingVinValidator(CarRepository carRepository) {
		this.carRepository = carRepository;
	}

	@Override
	public boolean supports(Class<?> clazz) {
		return UpdateCarDto.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		UpdateCarDto dto = (UpdateCarDto)target;
		
		if(!Objects.isNull(dto.getVinCode()) && !dto.getVinCode().isEmpty()) {
			
			Optional<Car> carFromDb = carRepository.findByVinCode(dto.getVinCode());
			
			if (carFromDb.isPresent() && carFromDb.get().getId() != dto.getId()) {
				errors.rejectValue("vinCode", "vin.alreadyExists");
			}
		}
		
		
		if(!Objects.isNull(dto.getLicencePlate())) {
			
			Optional<Car> carFromDb = carRepository.findByLicencePlate(dto.getLicencePlate());
			
			if (carFromDb.isPresent() && carFromDb.get().getId() != dto.getId()) {
				errors.rejectValue("licencePlate", "licensePlate.alreadyExists");
			}
		}
		
	}

}
