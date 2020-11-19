package ua.com.harazh.oblik.validator.user;

import java.util.Objects;
import java.util.Optional;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import ua.com.harazh.oblik.domain.OblikUser;
import ua.com.harazh.oblik.domain.dto.UpdateUserDto;
import ua.com.harazh.oblik.repository.UserRepository;

@Component
public class UpdatingUserValidator implements Validator{
	
	
	private UserRepository userRepository;
	
	

	public UpdatingUserValidator(UserRepository userRepository) {
		super();
		this.userRepository = userRepository;
	}

	@Override
	public boolean supports(Class<?> clazz) {
		return UpdateUserDto.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		UpdateUserDto updateUserDto = (UpdateUserDto) target;
		
		if(!Objects.isNull(updateUserDto.getName())) {
			Optional<OblikUser> optional = userRepository.findByName(updateUserDto.getName());

			if (optional.isPresent() && optional.get().getId() != updateUserDto.getId()) {
				errors.rejectValue("name", "user.alreadyExists");
			}
		}
		
		
	}

}
