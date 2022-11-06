package com.dreamsoftware.tcs.mail.content.impl.certificate;

import com.dreamsoftware.tcs.mail.content.AbstractMailContentBuilder;
import com.dreamsoftware.tcs.mail.model.certificate.IssueCertificateRequestMailRequestDTO;
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
public class IssueCertificateRequestMailContentBuilder extends AbstractMailContentBuilder<IssueCertificateRequestMailRequestDTO> {

    /**
     *
     * @param request
     * @return
     * @throws MessagingException
     */
    @Override
    public MimeMessage buildContent(final IssueCertificateRequestMailRequestDTO request) throws MessagingException {
        Assert.notNull(request, "Request can not be null");
        Assert.notNull(mailContentProperties.getIssueCertificateRequestMailTemplate(), "Mail Template can not be null");
        Assert.hasLength(mailContentProperties.getIssueCertificateRequestMailTemplate(), "Mail Template can not be empty");
        // Generate Email Subject
        final String subject = resolveString("mail_issue_certificate_request_subject_title", request.getLocale(),
                new Object[]{request.getName()});

        final Context context = new Context(request.getLocale());
        context.setVariable("name", request.getName());

        return buildMimeMessage(subject, request.getEmail(), context, mailContentProperties.getIssueCertificateRequestMailTemplate(), null);
    }

}
