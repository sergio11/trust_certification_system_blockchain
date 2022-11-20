package com.dreamsoftware.tcs.mail.content.impl.user;

import com.dreamsoftware.tcs.mail.content.AbstractMailContentBuilder;
import com.dreamsoftware.tcs.mail.model.user.UserActivatedEventMailRequestDTO;
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
public class UserActivatedMailContentBuilder extends AbstractMailContentBuilder<UserActivatedEventMailRequestDTO> {

    /**
     *
     * @param request
     * @return
     * @throws MessagingException
     */
    @Override
    public MimeMessage buildContent(final UserActivatedEventMailRequestDTO request) throws MessagingException {
        Assert.notNull(request, "Request can not be null");
        Assert.notNull(mailContentProperties.getUserActivatedMailTemplate(), "Mail Template can not be null");
        Assert.hasLength(mailContentProperties.getUserActivatedMailTemplate(), "Mail Template can not be empty");
        log.debug("UserActivatedMailContentBuilder CALLED!");
        // Generate Email Subject
        final String subject = resolveString("mail_user_activated_subject_title", request.getLocale(),
                new Object[]{request.getName()});

        final Context context = new Context(request.getLocale());
        context.setVariable("name", request.getName());

        return buildMimeMessage(subject, request.getEmail(), context, mailContentProperties.getUserActivatedMailTemplate(), null);
    }

}
