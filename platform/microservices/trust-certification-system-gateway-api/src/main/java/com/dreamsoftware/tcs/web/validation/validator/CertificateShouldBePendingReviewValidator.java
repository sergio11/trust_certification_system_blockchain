package com.dreamsoftware.tcs.web.validation.validator;

import com.dreamsoftware.tcs.persistence.nosql.entity.CertificateStatusEnum;
import com.dreamsoftware.tcs.persistence.nosql.repository.CertificateIssuanceRequestRepository;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import com.dreamsoftware.tcs.web.validation.constraints.CertificateShouldBePendingReview;

/**
 *
 * @author ssanchez
 */
public class CertificateShouldBePendingReviewValidator implements ConstraintValidator<CertificateShouldBePendingReview, String> {

    @Autowired
    private CertificateIssuanceRequestRepository certificateIssuanceRequestRepository;

    @Override
    public boolean isValid(String id, ConstraintValidatorContext context) {
        return ObjectId.isValid(id) && certificateIssuanceRequestRepository.countByIdAndStatus(new ObjectId(id), CertificateStatusEnum.PENDING_REVIEW) > 0;
    }
}
