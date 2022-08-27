package com.dreamsoftware.tcs.mail.content.impl;

import com.dreamsoftware.tcs.mail.content.AbstractMailContentBuilder;
import com.dreamsoftware.tcs.mail.model.CertificationCourseRegisteredMailRequestDTO;
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
public class CertificationCourseRegisteredMailContentBuilder extends AbstractMailContentBuilder<CertificationCourseRegisteredMailRequestDTO> {

    /**
     *
     * @param request
     * @return
     * @throws MessagingException
     */
    @Override
    public MimeMessage buildContent(final CertificationCourseRegisteredMailRequestDTO request) throws MessagingException {
        Assert.notNull(request, "Request can not be null");
        Assert.notNull(mailContentProperties.getCertificationCourseRegisteredMailTemplate(), "Mail Template can not be null");
        Assert.hasLength(mailContentProperties.getCertificationCourseRegisteredMailTemplate(), "Mail Template can not be empty");
        // Generate Email Subject
        final String subject = resolveString("mail_certification_course_registered_subject_title", request.getLocale(),
                new Object[]{});

        final Context context = new Context(request.getLocale());

        return buildMimeMessage(subject, request.getEmail(), context, mailContentProperties.getCertificationCourseRegisteredMailTemplate(), null);
    }

}
