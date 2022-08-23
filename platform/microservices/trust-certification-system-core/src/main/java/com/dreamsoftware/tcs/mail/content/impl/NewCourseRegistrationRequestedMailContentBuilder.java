package com.dreamsoftware.tcs.mail.content.impl;

import com.dreamsoftware.tcs.mail.content.AbstractMailContentBuilder;
import com.dreamsoftware.tcs.mail.model.NewCourseRegistrationRequestedMailRequestDTO;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.thymeleaf.context.Context;

/**
 *
 * @author ssanchez
 */
@Component
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class NewCourseRegistrationRequestedMailContentBuilder extends AbstractMailContentBuilder<NewCourseRegistrationRequestedMailRequestDTO> {

    /**
     *
     * @param request
     * @return
     * @throws MessagingException
     */
    @Override
    public MimeMessage buildContent(final NewCourseRegistrationRequestedMailRequestDTO request) throws MessagingException {
        Assert.notNull(request, "Request can not be null");
        Assert.notNull(mailContentProperties.getNewCourseRegistrationRequestedMailTemplate(), "Mail Template can not be null");
        Assert.hasLength(mailContentProperties.getNewCourseRegistrationRequestedMailTemplate(), "Mail Template can not be empty");
        // Generate Email Subject
        final String subject = resolveString("mail_new_course_registration_requested_subject_title", request.getLocale(),
                new Object[]{request.getCaName(), request.getCourseName()});

        final Context context = new Context(request.getLocale());
        context.setVariable("caName", request.getCaName());
        context.setVariable("courseName", request.getCourseName());

        return buildMimeMessage(subject, request.getEmail(), context, mailContentProperties.getNewCourseRegistrationRequestedMailTemplate(), null);
    }

}
