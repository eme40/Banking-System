package com.eric.World.Banking.app.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Target({ElementType.FIELD,ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = EmailValidator.class)
public @interface ValidEmail {
  String message() default "Invalid email";
  Class<?> [] groups() default {};

  Class<? extends Payload>[] payload() default {};
}
