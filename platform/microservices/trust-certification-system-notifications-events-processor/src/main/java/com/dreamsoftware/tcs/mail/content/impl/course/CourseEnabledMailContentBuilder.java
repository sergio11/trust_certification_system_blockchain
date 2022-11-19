package com.dreamsoftware.tcs.mail.content.impl.course;

import com.dreamsoftware.tcs.mail.content.AbstractMailContentBuilder;
import com.dreamsoftware.tcs.mail.model.course.CourseEnabledMailRequestDTO;
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
public class CourseEnabledMailContentBuilder extends AbstractMailContentBuilder<CourseEnabledMailRequestDTO> {

    /**
     *
     * @param request
     * @return
     * @throws MessagingException
     */
    @Override
    public MimeMessage buildContent(final CourseEnabledMailRequestDTO request) throws MessagingException {
        Assert.notNull(request, "Request can not be null");
        Assert.notNull(mailContentProperties.getCourseEnabledMailTemplate(), "Mail Template can not be null");
        Assert.hasLength(mailContentProperties.getCourseEnabledMailTemplate(), "Mail Template can not be empty");
        log.debug("CourseEnabledMailContentBuilder CALLED!");
        // Generate Email Subject
        final String subject = resolveString("mail_course_enabled_subject_title", request.getLocale(),
                new Object[]{request.getCaName(), request.getCourseName()});

        final Context context = new Context(request.getLocale());
        context.setVariable("caName", request.getCaName());
        context.setVariable("courseName", request.getCourseName());

        return buildMimeMessage(subject, request.getEmail(), context, mailContentProperties.getCourseEnabledMailTemplate(), null);
    }

}
