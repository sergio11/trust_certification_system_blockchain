package com.dreamsoftware.tcs.mail.content;

import com.dreamsoftware.tcs.config.properties.MailProperties;
import com.dreamsoftware.tcs.model.mail.AbstractMailRequestDTO;
import java.util.Map;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.thymeleaf.ITemplateEngine;
import org.thymeleaf.context.Context;

/**
 *
 * @author ssanchez
 * @param <T>
 */
public abstract class AbstractMailContentBuilder<T extends AbstractMailRequestDTO> {

    @Autowired
    protected MailProperties mailContentProperties;

    @Autowired
    private ITemplateEngine templateEngine;

    @Autowired
    private JavaMailSender mailSender;

    /**
     *
     * @param request
     * @return
     * @throws MessagingException
     */
    public abstract MimeMessage buildContent(final T request) throws MessagingException;

    /**
     * Build Mime Message
     *
     * @param subject
     * @param target
     * @param context
     * @param templateName
     * @return
     * @throws MessagingException
     */
    protected MimeMessage buildMimeMessage(final String subject, final String target, final Context context, final String templateName, final Map<String, String> inlineResourcePathMap) throws MessagingException {
        final MimeMessageHelper messageHelper = createMimeMessageHelper();
        messageHelper.setSubject(subject);
        messageHelper.setFrom(mailContentProperties.getMailFrom());
        messageHelper.setTo(target);
        messageHelper.setText(templateEngine.process(templateName, context), true);
        if (inlineResourcePathMap != null) {
            for (final Map.Entry<String, String> inlineResource : inlineResourcePathMap.entrySet()) {
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
    protected MimeMessageHelper createMimeMessageHelper() throws MessagingException {
        return new MimeMessageHelper(this.mailSender.createMimeMessage(), true, "UTF-8");
    }

}
