package com.stacksimply.restservices.validators;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = PassWordCheckValidator.class)
public @interface PasswordCheck {
	String message() default "{Password.constraints.message}";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};

}
