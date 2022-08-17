package com.dreamsoftware.tcs.mail.content.impl;

import com.dreamsoftware.tcs.mail.content.AbstractMailContentBuilder;
import com.dreamsoftware.tcs.mail.model.CertificateEnabledMailRequestDTO;
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
public class CertificateEnabledMailContentBuilder extends AbstractMailContentBuilder<CertificateEnabledMailRequestDTO> {

    /**
     *
     * @param request
     * @return
     * @throws MessagingException
     */
    @Override
    public MimeMessage buildContent(CertificateEnabledMailRequestDTO request) throws MessagingException {
        Assert.notNull(request, "Request can not be null");
        Assert.notNull(mailContentProperties.getCertificateEnabledMailTemplate(), "Mail Template can not be null");
        Assert.hasLength(mailContentProperties.getCertificateEnabledMailTemplate(), "Mail Template can not be empty");
        // Generate Email Subject
        final String subject = resolveString("mail_certificate_enabled_subject_title", request.getLocale(),
                new Object[]{request.getName(), request.getCertificateId()});

        final Context context = new Context(request.getLocale());
        context.setVariable("name", request.getName());
        context.setVariable("certificateId", request.getCertificateId());

        return buildMimeMessage(subject, request.getEmail(), context, mailContentProperties.getCertificateEnabledMailTemplate(), null);
    }

}
