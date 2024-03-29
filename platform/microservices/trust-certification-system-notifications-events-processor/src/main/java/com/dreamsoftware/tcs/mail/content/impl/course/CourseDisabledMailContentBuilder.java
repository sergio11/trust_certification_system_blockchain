package com.dreamsoftware.tcs.mail.content.impl.course;

import com.dreamsoftware.tcs.mail.content.AbstractMailContentBuilder;
import com.dreamsoftware.tcs.mail.model.course.CourseDisabledMailRequestDTO;
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
public class CourseDisabledMailContentBuilder extends AbstractMailContentBuilder<CourseDisabledMailRequestDTO> {

    /**
     *
     * @param request
     * @return
     * @throws MessagingException
     */
    @Override
    public MimeMessage buildContent(final CourseDisabledMailRequestDTO request) throws MessagingException {
        Assert.notNull(request, "Request can not be null");
        Assert.notNull(mailContentProperties.getCourseDisabledMailTemplate(), "Mail Template can not be null");
        Assert.hasLength(mailContentProperties.getCourseDisabledMailTemplate(), "Mail Template can not be empty");
        log.debug("CourseDisabledMailContentBuilder CALLED!");
        // Generate Email Subject
        final String subject = resolveString("mail_course_disabled_subject_title", request.getLocale(),
                new Object[]{request.getCaName(), request.getCourseName()});

        final Context context = new Context(request.getLocale());
        context.setVariable("caName", request.getCaName());
        context.setVariable("courseName", request.getCourseName());

        return buildMimeMessage(subject, request.getEmail(), context, mailContentProperties.getCourseDisabledMailTemplate(), null);
    }

}
