package com.dreamsoftware.tcs.web.validation.constraints;

import com.dreamsoftware.tcs.web.validation.validator.CertificateShouldDisableValidator;
import javax.validation.Constraint;
import javax.validation.Payload;
import javax.validation.constraints.NotNull;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE})
@Retention(RUNTIME)
@Constraint(validatedBy = CertificateShouldDisableValidator.class)
@NotNull
@Documented
public @interface CertificateShouldDisable {

    String message() default "{constraints.valid.objectid}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
