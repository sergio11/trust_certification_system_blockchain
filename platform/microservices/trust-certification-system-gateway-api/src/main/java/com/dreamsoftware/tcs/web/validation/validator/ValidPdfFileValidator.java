package com.dreamsoftware.tcs.web.validation.validator;

import com.dreamsoftware.tcs.web.validation.constraints.ValidPdfFile;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import org.springframework.http.MediaType;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author ssanchez
 */
public class ValidPdfFileValidator implements ConstraintValidator<ValidPdfFile, MultipartFile> {

    @Override
    public boolean isValid(MultipartFile file, ConstraintValidatorContext cvc) {
        final MediaType fileMediaType = MediaType.parseMediaType(file.getContentType());
        return !file.isEmpty() && fileMediaType.equals(MediaType.APPLICATION_PDF);
    }

}
