package com.dreamsoftware.tcs.mail.model.service.impl;

import com.dreamsoftware.tcs.mail.content.AbstractMailContentBuilder;
import com.dreamsoftware.tcs.mail.model.AbstractMailRequestDTO;
import com.dreamsoftware.tcs.mail.model.service.IMailClientService;
import com.dreamsoftware.tcs.persistence.nosql.entity.EmailEntity;
import com.dreamsoftware.tcs.persistence.nosql.repository.EmailRepository;
import com.dreamsoftware.tcs.persistence.nosql.repository.UserRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Date;
import java.util.Optional;
import java.util.logging.Level;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.core.ResolvableType;
import org.springframework.mail.MailException;
import org.springframework.stereotype.Service;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.util.Assert;

/**
 * Mail Client Service
 *
 * @author ssanchez
 */
@Service
@RequiredArgsConstructor
public class MailClientServiceImpl implements IMailClientService {

    private final Logger logger = LoggerFactory.getLogger(MailClientServiceImpl.class);

    private final JavaMailSender mailSender;
    private final EmailRepository emailRepository;
    private final ApplicationContext applicationContext;
    private final UserRepository userRepository;
    private final ObjectMapper objectMapper;

    /**
     *
     * @param numberOfEmailsToForwarding
     */
    @Override
    public void forwardEmails(int numberOfEmailsToForwarding) {
        Assert.isTrue(numberOfEmailsToForwarding > 0, "Number of mails should be greater than 0");
    }

    /**
     *
     * @param <T>
     * @param request
     */
    @Override
    public <T extends AbstractMailRequestDTO> void sendMail(final T request) {
        Assert.notNull(request, "request can not be null");
        Assert.notNull(request.getEmail(), "Email can not be null");
        Assert.hasLength(request.getEmail(), "Email can be empty");
        try {
            final AbstractMailContentBuilder<T> mailContentBuilder = getMailContentBuilder(request.getEntityType());
            final MimeMessage message = mailContentBuilder.buildContent(request);
            mailSender.send(message);
        } catch (final MessagingException | MailException ex) {
            logger.error(request.getEntityType().getName() + " - MailException: " + ex.getMessage());
            saveFailedEmail(ex.getMessage(), request);
        }
    }

    /**
     * Private Methods
     */
    /**
     *
     * @param <T>
     * @param ex
     * @param request
     */
    private <T extends AbstractMailRequestDTO> void saveFailedEmail(final String errorMessage, final T request) {
        EmailEntity emailEntity = emailRepository.findByUserEmailAndType(request.getEmail(), request.getType())
                .map((emailEntitySaved) -> {
                    emailEntitySaved.setLastChance(new Date());
                    emailEntitySaved.setError(errorMessage);
                    return emailEntitySaved;
                }).orElse(null);

        if (emailEntity == null) {
            try {
                emailEntity = EmailEntity
                        .builder()
                        .user(userRepository.findOneByEmail(request.getEmail()).orElse(null))
                        .type(request.getType())
                        .lastChance(new Date())
                        .error(errorMessage)
                        .payload(objectMapper.writeValueAsString(request))
                        .build();
            } catch (JsonProcessingException ex) {
                logger.error("JsonProcessingException:  " + ex.getMessage());
            }
        }
        emailRepository.save(emailEntity);
    }

    /**
     *
     * @param <T>
     * @param clazz
     * @return
     */
    private <T extends AbstractMailRequestDTO> AbstractMailContentBuilder<T> getMailContentBuilder(Class<T> clazz) {
        ResolvableType type = ResolvableType.forClassWithGenerics(AbstractMailContentBuilder.class, clazz);
        return (AbstractMailContentBuilder<T>) applicationContext.getBeanProvider(type).getObject();
    }
}
