package com.dreamsoftware.blockchaingateway.web.validation.constraints;

import com.dreamsoftware.blockchaingateway.web.validation.validator.ShouldBeValidISOLanguageValidator;
import java.lang.annotation.Documented;
import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.CONSTRUCTOR;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.ElementType.TYPE_USE;
import java.lang.annotation.Retention;
import static java.lang.annotation.RetentionPolicy.RUNTIME;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

@Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE})
@Retention(RUNTIME)
@Constraint(validatedBy = ShouldBeValidISOLanguageValidator.class)
@Documented
public @interface ShouldBeValidISOLanguage {

    String message() default "{constraints.email.unique}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
