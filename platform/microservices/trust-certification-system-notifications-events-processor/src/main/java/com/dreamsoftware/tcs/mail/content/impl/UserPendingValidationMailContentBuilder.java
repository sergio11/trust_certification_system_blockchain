package com.dreamsoftware.tcs.mail.content.impl;

import com.dreamsoftware.tcs.mail.content.AbstractMailContentBuilder;
import com.dreamsoftware.tcs.mail.model.UserPendingValidationMailRequestDTO;
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
public class UserPendingValidationMailContentBuilder extends AbstractMailContentBuilder<UserPendingValidationMailRequestDTO> {

    /**
     *
     * @param request
     * @return
     * @throws MessagingException
     */
    @Override
    public MimeMessage buildContent(final UserPendingValidationMailRequestDTO request) throws MessagingException {
        Assert.notNull(request, "Request can not be null");
        Assert.notNull(mailContentProperties.getUserPendingValidationMailTemplate(), "Mail Template can not be null");
        Assert.hasLength(mailContentProperties.getUserPendingValidationMailTemplate(), "Mail Template can not be empty");

        // Generate Email Subject
        final String subject = resolveString("mail_user_pending_validation_subject_title", request.getLocale(),
                new Object[]{request.getName()});

        final Context context = new Context(request.getLocale());
        context.setVariable("name", request.getName());
        /*context.setVariable("activateUrl",  applicationContext.
                ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/v1/accounts/activate?token={token}")
                        .buildAndExpand(request.getConfirmationToken()));*/
        return buildMimeMessage(subject, request.getEmail(), context, mailContentProperties.getUserPendingValidationMailTemplate(), null);
    }

}
