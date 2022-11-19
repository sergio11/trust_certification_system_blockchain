package com.dreamsoftware.tcs.mail.content.impl.course;

import com.dreamsoftware.tcs.mail.content.AbstractMailContentBuilder;
import com.dreamsoftware.tcs.mail.model.course.CourseEditionRegisteredMailRequestDTO;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
public class CourseEditionRegisteredMailContentBuilder extends AbstractMailContentBuilder<CourseEditionRegisteredMailRequestDTO> {

    /**
     *
     * @param request
     * @return
     * @throws MessagingException
     */
    @Override
    public MimeMessage buildContent(final CourseEditionRegisteredMailRequestDTO request) throws MessagingException {
        Assert.notNull(request, "Request can not be null");
        Assert.notNull(mailContentProperties.getCertificationCourseEditionRegisteredMailTemplate(), "Mail Template can not be null");
        Assert.hasLength(mailContentProperties.getCertificationCourseEditionRegisteredMailTemplate(), "Mail Template can not be empty");
        log.debug("CourseEditionRegisteredMailContentBuilder CALLED!");
        // Generate Email Subject
        final String subject = resolveString("mail_certification_course_edition_registered_subject_title", request.getLocale(),
                new Object[]{});

        final Context context = new Context(request.getLocale());

        return buildMimeMessage(subject, request.getEmail(), context, mailContentProperties.getCertificationCourseEditionRegisteredMailTemplate(), null);
    }

}
