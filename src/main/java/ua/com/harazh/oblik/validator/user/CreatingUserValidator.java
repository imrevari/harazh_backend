package ua.com.harazh.oblik.validator.user;

import java.util.Objects;
import java.util.Optional;
import java.util.regex.Pattern;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import ua.com.harazh.oblik.domain.OblikUser;
import ua.com.harazh.oblik.domain.dto.UserCreationDto;
import ua.com.harazh.oblik.repository.UserRepository;

@Component
public class CreatingUserValidator implements Validator{
	
	private UserRepository userRepository;

	public CreatingUserValidator(UserRepository userRepository) {
		super();
		this.userRepository = userRepository;
	}

	@Override
	public boolean supports(Class<?> clazz) {
		return UserCreationDto.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		UserCreationDto userCreationDto = (UserCreationDto) target;
		
		if(!Objects.isNull(userCreationDto.getName())) {
			Optional<OblikUser> optional = userRepository.findByName(userCreationDto.getName());

			if (optional.isPresent()) {
				errors.rejectValue("name", "user.alreadyExists");
			}
		}
		
		if (userCreationDto.getPassword().length() < 6 ) {
            errors.rejectValue("password", "password.wrongLength");
        }
        if (!isPasswordProper(userCreationDto.getPassword())){

            errors.rejectValue("password", "password.mustContain");

        }
	}
	
	private boolean isPasswordProper(String password){
        int property = 0;

        if (Pattern.compile("[A-Z]").matcher(password).find()) {

            property += 1;
        }if (Pattern.compile("[a-z]").matcher(password).find()){

            property += 1;
        }if(Pattern.compile("[0-9]").matcher(password).find()){
            ;
            property += 1;
        }

        if (property < 3){
            return false;
        }
        return true;
    }

}
