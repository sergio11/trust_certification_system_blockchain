package com.dreamsoftware.tcs.mail.content.impl.certificate;

import com.dreamsoftware.tcs.mail.content.AbstractMailContentBuilder;
import com.dreamsoftware.tcs.mail.model.certificate.CertificateGeneratedMailRequestDTO;
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
public class CertificateGeneratedMailContentBuilder extends AbstractMailContentBuilder<CertificateGeneratedMailRequestDTO> {

    /**
     *
     * @param request
     * @return
     * @throws MessagingException
     */
    @Override
    public MimeMessage buildContent(final CertificateGeneratedMailRequestDTO request) throws MessagingException {
        Assert.notNull(request, "Request can not be null");
        Assert.notNull(mailContentProperties.getCertificateGeneratedMailTemplate(), "Mail Template can not be null");
        Assert.hasLength(mailContentProperties.getCertificateGeneratedMailTemplate(), "Mail Template can not be empty");
        // Generate Email Subject
        final String subject = resolveString("mail_certificate_generated_subject_title", request.getLocale(),
                new Object[]{});

        final Context context = new Context(request.getLocale());

        return buildMimeMessage(subject, request.getEmail(), context, mailContentProperties.getCertificateGeneratedMailTemplate(), null);
    }

}
