package com.dreamsoftware.blockchaingateway.services.impl.mail;

import com.dreamsoftware.blockchaingateway.mail.properties.MailProperties;
import com.dreamsoftware.blockchaingateway.services.mail.IMailContentBuilderService;
import com.dreamsoftware.blockchaingateway.web.dto.internal.SendMailForActivateAccountDTO;
import com.dreamsoftware.blockchaingateway.web.dto.internal.SendMailForConfirmActivationDTO;
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
import org.thymeleaf.ITemplateEngine;
import org.thymeleaf.context.Context;

/**
 * Mail Content Builder
 */
@Service
@RequiredArgsConstructor
public class MailContentBuilderServiceImpl implements IMailContentBuilderService {

    private Logger logger = LoggerFactory.getLogger(MailContentBuilderServiceImpl.class);

    private final MailProperties mailContentProperties;
    private final ITemplateEngine templateEngine;
    private final JavaMailSender mailSender;

    @Override
    public MimeMessage buildContent(SendMailForConfirmActivationDTO request) throws MessagingException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public MimeMessage buildContent(SendMailForActivateAccountDTO request) throws MessagingException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
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
        for (final Entry<String, String> inlineResource : inlineResourcePathMap.entrySet()) {
            messageHelper.addInline(inlineResource.getKey(),
                    new ClassPathResource(inlineResource.getValue()));
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
