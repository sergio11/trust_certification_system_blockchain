package com.dreamsoftware.tcs.mail.content.impl.certificate;

import com.dreamsoftware.tcs.mail.content.AbstractMailContentBuilder;
import com.dreamsoftware.tcs.mail.model.certificate.CertificateVisibilityChangedMailRequestDTO;
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
public class CertificateVisibilityMailContentBuilder extends AbstractMailContentBuilder<CertificateVisibilityChangedMailRequestDTO> {

    /**
     *
     * @param request
     * @return
     * @throws MessagingException
     */
    @Override
    public MimeMessage buildContent(final CertificateVisibilityChangedMailRequestDTO request) throws MessagingException {
        Assert.notNull(request, "Request can not be null");
        Assert.notNull(mailContentProperties.getCertificateVisibilityChangedMailTemplate(), "Mail Template can not be null");
        Assert.hasLength(mailContentProperties.getCertificateVisibilityChangedMailTemplate(), "Mail Template can not be empty");
        log.debug("CertificateVisibilityMailContentBuilder CALLED!");
        // Generate Email Subject
        final String subject = resolveString("mail_certificate_visibility_changed_subject_title", request.getLocale(),
                new Object[]{request.getName(), request.getCertificateId()});

        final Context context = new Context(request.getLocale());
        context.setVariable("name", request.getName());
        context.setVariable("certificateId", request.getCertificateId());
        context.setVariable("isVisible", request.getIsVisible());

        return buildMimeMessage(subject, request.getEmail(), context, mailContentProperties.getCertificateVisibilityChangedMailTemplate(), null);
    }

}
