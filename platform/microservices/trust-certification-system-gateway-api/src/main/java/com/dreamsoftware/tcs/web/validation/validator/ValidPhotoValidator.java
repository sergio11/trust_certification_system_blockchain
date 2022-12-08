package com.dreamsoftware.tcs.web.validation.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.dreamsoftware.tcs.web.validation.constraints.ValidPhoto;
import org.springframework.http.MediaType;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author ssanchez
 */
public class ValidPhotoValidator implements ConstraintValidator<ValidPhoto, MultipartFile> {

    @Override
    public boolean isValid(MultipartFile file, ConstraintValidatorContext cvc) {
        final MediaType fileMediaType = MediaType.parseMediaType(file.getContentType());
        return !file.isEmpty() && (fileMediaType.equals(MediaType.IMAGE_PNG)
                || fileMediaType.equals(MediaType.IMAGE_JPEG) || fileMediaType.equals(MediaType.IMAGE_GIF));
    }

}
