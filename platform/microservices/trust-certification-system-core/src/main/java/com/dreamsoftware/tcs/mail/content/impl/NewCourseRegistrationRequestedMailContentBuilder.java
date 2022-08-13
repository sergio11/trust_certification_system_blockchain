package com.dreamsoftware.tcs.mail.content.impl;

import com.dreamsoftware.tcs.mail.content.AbstractMailContentBuilder;
import com.dreamsoftware.tcs.mail.model.CourseDisabledMailRequestDTO;
import com.dreamsoftware.tcs.mail.model.NewCourseRegistrationRequestedMailRequestDTO;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 *
 * @author ssanchez
 */
@Component
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class NewCourseRegistrationRequestedMailContentBuilder extends AbstractMailContentBuilder<NewCourseRegistrationRequestedMailRequestDTO> {

    @Override
    public MimeMessage buildContent(NewCourseRegistrationRequestedMailRequestDTO request) throws MessagingException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
