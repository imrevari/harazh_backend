package ua.com.harazh.oblik.validator.order;

import java.util.Objects;
import java.util.Optional;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import ua.com.harazh.oblik.domain.Part;
import ua.com.harazh.oblik.domain.dto.AddPartCountDto;
import ua.com.harazh.oblik.repository.PartRepository;

@Component
public class AddPartCountValidator implements Validator{
	
	private PartRepository partRepository;

	public AddPartCountValidator(PartRepository partRepository) {
		super();
		this.partRepository = partRepository;
	}

	@Override
	public boolean supports(Class<?> clazz) {
		return AddPartCountDto.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {

		AddPartCountDto addPartCountDto = (AddPartCountDto) target;
		
		if(!Objects.isNull(addPartCountDto.getPartId())) {
			Optional<Part> part = partRepository.findById(addPartCountDto.getPartId());
			
			if (!part.isPresent()) {
				errors.rejectValue("partId", "part.wrongId");
			}
		}

	}

}
