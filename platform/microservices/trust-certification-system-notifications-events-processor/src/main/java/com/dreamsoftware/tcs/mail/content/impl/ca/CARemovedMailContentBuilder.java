package com.dreamsoftware.tcs.mail.content.impl.ca;

import com.dreamsoftware.tcs.mail.content.AbstractMailContentBuilder;
import com.dreamsoftware.tcs.mail.model.ca.CertificationAuthorityRemovedMailRequestDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.thymeleaf.context.Context;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

/**
 *
 * @author ssanchez
 */
@Component
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@Slf4j
public class CARemovedMailContentBuilder extends AbstractMailContentBuilder<CertificationAuthorityRemovedMailRequestDTO> {

    /**
     *
     * @param request
     * @return
     * @throws MessagingException
     */
    @Override
    public MimeMessage buildContent(final CertificationAuthorityRemovedMailRequestDTO request) throws MessagingException {
        Assert.notNull(request, "Request can not be null");
        Assert.notNull(mailContentProperties.getCaRemovedMailTemplate(), "Mail Template can not be null");
        Assert.hasLength(mailContentProperties.getCaRemovedMailTemplate(), "Mail Template can not be empty");
        log.debug("CARemovedMailContentBuilder CALLED!");
        // Generate Email Subject
        final String subject = resolveString("mail_ca_removed_subject_title", request.getLocale(),
                new Object[]{request.getName()});

        final Context context = new Context(request.getLocale());
        context.setVariable("name", request.getName());

        return buildMimeMessage(subject, request.getEmail(), context, mailContentProperties.getCaRemovedMailTemplate(), null);
    }

}
