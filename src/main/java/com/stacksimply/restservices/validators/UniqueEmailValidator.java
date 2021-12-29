package com.stacksimply.restservices.validators;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;
import com.stacksimply.restservices.repositories.UserRespository;

public class UniqueEmailValidator implements ConstraintValidator<UniqueEmail, String> {

	@Autowired
	UserRespository userRespository;

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		return !userRespository.findByEmail(value).isPresent();

	}

}
