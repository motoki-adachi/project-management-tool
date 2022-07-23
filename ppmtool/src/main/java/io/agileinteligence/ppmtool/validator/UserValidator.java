package io.agileinteligence.ppmtool.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import io.agileinteligence.ppmtool.domain.User;

@Component
public class UserValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
	return User.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
	User user = (User) target;
	if (user.getPassword().length() < 6) {
	    errors.rejectValue("password", "Length", "パスワードは6文字以上で入力してください");
	}

	if (!user.getPassword().equals(user.getConfirmPassword())) {
	    errors.rejectValue("confirmPassword", "Match", "パスワードが一致していません");
	}
    }

}
