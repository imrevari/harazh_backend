package ua.com.harazh.oblik.validator.user;

import java.util.regex.Pattern;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import ua.com.harazh.oblik.domain.dto.ChangePasswordDto;

@Component
public class ChangePasswordValidator implements Validator{

	@Override
	public boolean supports(Class<?> clazz) {
		return ChangePasswordDto.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		ChangePasswordDto changePasswordDto = (ChangePasswordDto) target;
		
		
		
		if (changePasswordDto.getNewPassword().length() < 6 ) {
            errors.rejectValue("newPassword", "password.wrongLength");
        }
        if (!isPasswordProper(changePasswordDto.getNewPassword())){

            errors.rejectValue("newPassword", "password.mustContain");

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
