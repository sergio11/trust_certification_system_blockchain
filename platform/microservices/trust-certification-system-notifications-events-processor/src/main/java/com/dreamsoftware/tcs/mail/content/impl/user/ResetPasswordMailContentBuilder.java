package com.dreamsoftware.tcs.mail.content.impl.user;

import com.dreamsoftware.tcs.mail.content.AbstractMailContentBuilder;
import com.dreamsoftware.tcs.mail.model.user.ResetPasswordMailRequestDTO;
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
public class ResetPasswordMailContentBuilder extends AbstractMailContentBuilder<ResetPasswordMailRequestDTO> {

    /**
     *
     * @param request
     * @return
     * @throws MessagingException
     */
    @Override
    public MimeMessage buildContent(final ResetPasswordMailRequestDTO request) throws MessagingException {
        Assert.notNull(request, "Request can not be null");
        Assert.notNull(mailContentProperties.getUserResetPassword(), "Mail Template can not be null");
        Assert.hasLength(mailContentProperties.getUserResetPassword(), "Mail Template can not be empty");
        log.debug("ResetPasswordMailContentBuilder CALLED!");
        // Generate Email Subject
        final String subject = resolveString("mail_user_reset_password_subject_title", request.getLocale(),
                new Object[]{request.getName()});

        final Context context = new Context(request.getLocale());
        context.setVariable("name", request.getName());

        return buildMimeMessage(subject, request.getEmail(), context, mailContentProperties.getUserResetPassword(), null);
    }

}
