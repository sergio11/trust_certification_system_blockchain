package com.dreamsoftware.tcs.mail.content.impl;

import com.dreamsoftware.tcs.mail.content.AbstractMailContentBuilder;
import com.dreamsoftware.tcs.mail.model.UserActivatedEventMailRequestDTO;
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

    @Override
    public MimeMessage buildContent(UserPendingValidationMailRequestDTO request) throws MessagingException {
        Assert.notNull(request, "Request can not be null");
        Assert.notNull(mailContentProperties.getConfirmAccountActivationTemplate(), "Account Activation Success Template can not be null");
        Assert.hasLength(mailContentProperties.getConfirmAccountActivationTemplate(), "Account Activation Success Template can not be empty");

        // Generate Email Subject
        String subject = "mail_registration_success_subject_title";

        final Context context = new Context(request.getLocale());
        context.setVariable("name", request.getName());
        /*context.setVariable("activateUrl",
                ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/v1/accounts/activate?token={token}")
                        .buildAndExpand(request.getConfirmationToken()));*/

        return buildMimeMessage(subject, request.getEmail(), context, mailContentProperties.getConfirmAccountActivationTemplate(), null);
    }

}
