package com.usps.api.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import com.usps.api.validator.PaginateValidator;

@Documented
@Constraint(validatedBy = PaginateValidator.class)
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface Paginate { 
    String message() default "{error.validation.paginate_request_parameters_not_valid}";
     
    Class<?>[] groups() default {};
     
    Class<? extends Payload>[] payload() default {};
}
