package com.stacksimply.restservices.validators;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * 
 ^ represents starting character of the string.
(?=.*[0-9]) represents a digit must occur at least once.
(?=.*[a-z]) represents a lower case alphabet must occur at least once.
(?=.*[A-Z]) represents an upper case alphabet that must occur at least once.
(?=.*[@#$%^&-+=()] represents a special character that must occur at least once.
(?=\\S+$) white spaces don’t allowed in the entire string.
.{8, 20} represents at least 8 characters and at most 20 characters.
 $ represents the end of the string.
 *
 */

public class PassWordCheckValidator implements ConstraintValidator<PasswordCheck, String> {

	private static final String PASSWORD_PATTREN = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&-+=()])(?=\\\\S+$).{8, 20}$";
	private Pattern pattern;
	private Matcher matcher;

	public PassWordCheckValidator() {
		pattern = Pattern.compile(PASSWORD_PATTREN);
	}

	@Override
	public void initialize(PasswordCheck passwordCheck) {
		passwordCheck.message();
	}

	@Override
	public boolean isValid(String password, ConstraintValidatorContext context) {
		if (password == null)
			return false;
		matcher = pattern.matcher(password);
		return matcher.matches();
	}

}
