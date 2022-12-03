package com.dreamsoftware.tcs.web.validation.validator;

import com.dreamsoftware.tcs.persistence.nosql.repository.NotificationRepository;
import com.dreamsoftware.tcs.web.validation.constraints.NotificationShouldExist;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 *
 * @author ssanchez
 */
public class NotificationShouldExistValidator implements ConstraintValidator<NotificationShouldExist, String> {

    @Autowired
    private NotificationRepository notificationRepository;

    @Override
    public boolean isValid(String id, ConstraintValidatorContext context) {
        return ObjectId.isValid(id) && notificationRepository.countById(new ObjectId(id)) > 0;
    }
}
