package com.dreamsoftware.tcs.mail.content.impl.certificate;

import com.dreamsoftware.tcs.mail.content.AbstractMailContentBuilder;
import com.dreamsoftware.tcs.mail.model.certificate.CertificateRequestAcceptedMailRequestDTO;
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
public class CertificateRequestAcceptedMailContentBuilder extends AbstractMailContentBuilder<CertificateRequestAcceptedMailRequestDTO> {

    /**
     *
     * @param request
     * @return
     * @throws MessagingException
     */
    @Override
    public MimeMessage buildContent(final CertificateRequestAcceptedMailRequestDTO request) throws MessagingException {
        Assert.notNull(request, "Request can not be null");
        Assert.notNull(mailContentProperties.getCertificateRequestAcceptedMailTemplate(), "Mail Template can not be null");
        Assert.hasLength(mailContentProperties.getCertificateRequestAcceptedMailTemplate(), "Mail Template can not be empty");
        log.debug("CertificateRequestAcceptedMailContentBuilder CALLED!");
        // Generate Email Subject
        final String subject = resolveString("mail_certificate_request_accepted_subject_title", request.getLocale(),
                new Object[]{request.getName(), request.getCertificateId()});

        final Context context = new Context(request.getLocale());
        context.setVariable("name", request.getName());
        context.setVariable("certificateId", request.getCertificateId());

        return buildMimeMessage(subject, request.getEmail(), context, mailContentProperties.getCertificateRequestAcceptedMailTemplate(), null);
    }

}
