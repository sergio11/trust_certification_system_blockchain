package com.dreamsoftware.tcs.mail.content.impl;

import com.dreamsoftware.tcs.mail.content.AbstractMailContentBuilder;
import com.dreamsoftware.tcs.mail.model.CertificateDisabledMailRequestDTO;
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
public class CertificateDisabledMailContentBuilder extends AbstractMailContentBuilder<CertificateDisabledMailRequestDTO> {

    /**
     *
     * @param request
     * @return
     * @throws MessagingException
     */
    @Override
    public MimeMessage buildContent(final CertificateDisabledMailRequestDTO request) throws MessagingException {
        Assert.notNull(request, "Request can not be null");
        Assert.notNull(mailContentProperties.getCertificateDisabledMailTemplate(), "Mail Template can not be null");
        Assert.hasLength(mailContentProperties.getCertificateDisabledMailTemplate(), "Mail Template can not be empty");
        // Generate Email Subject
        final String subject = resolveString("mail_certificate_disabled_subject_title", request.getLocale(),
                new Object[]{request.getName(), request.getCertificateId()});

        final Context context = new Context(request.getLocale());
        context.setVariable("name", request.getName());
        context.setVariable("certificateId", request.getCertificateId());

        return buildMimeMessage(subject, request.getEmail(), context, mailContentProperties.getCertificateDisabledMailTemplate(), null);
    }

}
