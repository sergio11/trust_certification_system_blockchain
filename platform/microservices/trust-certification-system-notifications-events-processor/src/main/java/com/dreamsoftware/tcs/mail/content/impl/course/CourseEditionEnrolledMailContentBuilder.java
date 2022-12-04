package com.dreamsoftware.tcs.mail.content.impl.course;

import com.dreamsoftware.tcs.mail.content.AbstractMailContentBuilder;
import com.dreamsoftware.tcs.mail.model.course.CourseEditionEnrolledMailRequestDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.thymeleaf.context.Context;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

/**
 *
 * @author ssanchez
 */
@Component
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@Slf4j
public class CourseEditionEnrolledMailContentBuilder extends AbstractMailContentBuilder<CourseEditionEnrolledMailRequestDTO> {

    /**
     *
     * @param request
     * @return
     * @throws MessagingException
     */
    @Override
    public MimeMessage buildContent(final CourseEditionEnrolledMailRequestDTO request) throws MessagingException {
        Assert.notNull(request, "Request can not be null");
        Assert.notNull(mailContentProperties.getCourseEditionDeletedMailTemplate(), "Mail Template can not be null");
        Assert.hasLength(mailContentProperties.getCourseEditionDeletedMailTemplate(), "Mail Template can not be empty");
        log.debug("CourseEditionEnrolledMailContentBuilder CALLED!");
        // Generate Email Subject
        final String subject = resolveString("mail_course_edition_enrolled_subject_title", request.getLocale(),
                new Object[]{request.getStudentName(), request.getCourseName()});

        final Context context = new Context(request.getLocale());
        context.setVariable("studentName", request.getStudentName());
        context.setVariable("courseName", request.getCourseName());

        return buildMimeMessage(subject, request.getEmail(), context, mailContentProperties.getCourseEditionDeletedMailTemplate(), null);
    }

}
