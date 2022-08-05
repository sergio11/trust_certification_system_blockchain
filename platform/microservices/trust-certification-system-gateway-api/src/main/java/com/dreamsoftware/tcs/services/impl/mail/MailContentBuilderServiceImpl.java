package com.dreamsoftware.tcs.services.impl.mail;

import com.dreamsoftware.tcs.config.mail.properties.MailProperties;
import com.dreamsoftware.tcs.services.mail.IMailContentBuilderService;
import com.dreamsoftware.tcs.web.dto.internal.SendMailForActivateAccountDTO;
import com.dreamsoftware.tcs.web.dto.internal.SendMailForConfirmActivationDTO;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.Map;
import java.util.Map.Entry;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.thymeleaf.ITemplateEngine;
import org.thymeleaf.context.Context;

/**
 * Mail Content Builder
 */
@Service
@RequiredArgsConstructor
public class MailContentBuilderServiceImpl implements IMailContentBuilderService {

    private final Logger logger = LoggerFactory.getLogger(MailContentBuilderServiceImpl.class);

    private final MailProperties mailContentProperties;
    private final ITemplateEngine templateEngine;
    private final JavaMailSender mailSender;

    @Override
    public MimeMessage buildContent(SendMailForConfirmActivationDTO request) throws MessagingException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     *
     * @param request
     * @return
     * @throws MessagingException
     */
    @Override
    public MimeMessage buildContent(SendMailForActivateAccountDTO request) throws MessagingException {
        Assert.notNull(mailContentProperties.getConfirmAccountActivationTemplate(), "Account Activation Success Template can not be null");
        Assert.hasLength(mailContentProperties.getConfirmAccountActivationTemplate(), "Account Activation Success Template can not be empty");

        // Generate Email Subject
        String subject = "mail_registration_success_subject_title";

        final Context context = new Context(request.getLocale());
        context.setVariable("firstname", request.getFirstname());
        context.setVariable("lastname", request.getLastname());
        context.setVariable("activateUrl",
                ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/v1/accounts/activate?token={token}")
                        .buildAndExpand(request.getConfirmationToken()));

        return buildMimeMessage(subject, request.getEmail(), context, mailContentProperties.getConfirmAccountActivationTemplate(), null);
    }

    /**
     * Build Mime Message
     *
     * @param subject
     * @param target
     * @param context
     * @param templateName
     * @param inlineResourcePathList
     * @return
     * @throws MessagingException
     */
    private MimeMessage buildMimeMessage(final String subject, final String target, final Context context, final String templateName, final Map<String, String> inlineResourcePathMap) throws MessagingException {
        final MimeMessageHelper messageHelper = createMimeMessageHelper();
        messageHelper.setSubject(subject);
        messageHelper.setFrom(mailContentProperties.getMailFrom());
        messageHelper.setTo(target);
        messageHelper.setText(templateEngine.process(templateName, context), true);
        if (inlineResourcePathMap != null) {
            for (final Entry<String, String> inlineResource : inlineResourcePathMap.entrySet()) {
                messageHelper.addInline(inlineResource.getKey(),
                        new ClassPathResource(inlineResource.getValue()));
            }
        }
        return messageHelper.getMimeMessage();

    }

    /**
     * Create Mime Message Helper
     *
     * @return
     * @throws MessagingException
     */
    private MimeMessageHelper createMimeMessageHelper() throws MessagingException {
        return new MimeMessageHelper(this.mailSender.createMimeMessage(), true, "UTF-8");
    }

}
