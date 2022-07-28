package com.dreamsoftware.tcs.web.validation.validator;

import com.dreamsoftware.tcs.web.validation.constraints.ValidTranslationFile;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import org.springframework.web.multipart.MultipartFile;

/**
 * .xls	Microsoft Excel	application/vnd.ms-excel .xlsx Microsoft Excel (OpenXML)
 * application/vnd.openxmlformats-officedocument.spreadsheetml.sheet
 *
 * @author ssanchez
 */
public class ValidTranslationFileValidator implements ConstraintValidator<ValidTranslationFile, MultipartFile> {

    private final static String MICROSOFT_EXCEL = "application/vnd.ms-excel";
    private final static String MICROSOFT_EXCEL_OPEN_XML = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";

    @Override
    public boolean isValid(MultipartFile file, ConstraintValidatorContext cvc) {
        return !file.isEmpty() && (MICROSOFT_EXCEL_OPEN_XML.equals(file.getContentType())
                || MICROSOFT_EXCEL.equals(file.getContentType()));

    }
}
