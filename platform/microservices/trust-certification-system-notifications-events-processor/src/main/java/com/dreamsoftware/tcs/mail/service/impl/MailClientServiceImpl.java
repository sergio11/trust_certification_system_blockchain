package com.dreamsoftware.tcs.mail.service.impl;

import com.dreamsoftware.tcs.mail.content.AbstractMailContentBuilder;
import com.dreamsoftware.tcs.mail.model.AbstractMailRequestDTO;
import com.dreamsoftware.tcs.mail.service.IMailClientService;
import com.dreamsoftware.tcs.persistence.nosql.entity.EmailEntity;
import com.dreamsoftware.tcs.persistence.nosql.repository.EmailRepository;
import com.dreamsoftware.tcs.persistence.nosql.repository.UserRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Date;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.context.ApplicationContext;
import org.springframework.core.ResolvableType;
import org.springframework.data.domain.Pageable;
import org.springframework.mail.MailException;
import org.springframework.stereotype.Service;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.util.Assert;

/**
 * Mail Client Service
 *
 * @author ssanchez
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class MailClientServiceImpl implements IMailClientService {

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
        emailRepository.findAllByOrderByLastChanceAsc(Pageable.ofSize(numberOfEmailsToForwarding))
                .forEach(this::sendMail);

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
            mailSender.send(buildMimeMessage(request));
        } catch (final MessagingException | MailException ex) {
            log.debug(request.getEntityType().getName() + " - MailException: " + ex.getMessage());
            saveFailedEmail(null, ex.getMessage(), request);
        }
    }

    /**
     * Private Methods
     */
    /**
     *
     * @param email
     */
    private void sendMail(final EmailEntity email) {
        Assert.notNull(email, "email can not be null");
        AbstractMailRequestDTO request = null;
        try {
            request = objectMapper.readValue(email.getPayload(), AbstractMailRequestDTO.class);
            mailSender.send(buildMimeMessage(request));
            emailRepository.delete(email);
        } catch (final MessagingException | MailException ex) {
            log.error(" - MailException: " + ex.getMessage());
            saveFailedEmail(email.getId(), ex.getMessage(), request);
        } catch (final JsonProcessingException ex) {
            log.error(" - JsonProcessingException: " + ex.getMessage());
            emailRepository.delete(email);
        }
    }

    /**
     *
     * @param emailId
     * @param errorMessage
     * @param request
     * @param <T>
     */
    private <T extends AbstractMailRequestDTO> void saveFailedEmail(final ObjectId emailId, final String errorMessage, final T request) {

        EmailEntity emailEntity = null;

        if (emailId != null) {
            emailEntity = emailRepository.findById(emailId)
                    .map((emailEntitySaved) -> {
                        emailEntitySaved.setLastChance(new Date());
                        emailEntitySaved.setError(errorMessage);
                        return emailEntitySaved;
                    }).orElse(null);
        }

        if (emailEntity == null) {
            try {
                emailEntity = EmailEntity
                        .builder()
                        .user(userRepository.findOneByEmail(request.getEmail()).orElse(null))
                        .lastChance(new Date())
                        .error(errorMessage)
                        .payload(objectMapper.writeValueAsString(request))
                        .build();
            } catch (JsonProcessingException ex) {
                log.error("JsonProcessingException:  " + ex.getMessage());
            }
        }

        if(emailEntity != null) {
            emailRepository.save(emailEntity);
        }
    }

    /**
     *
     * @param <T>
     * @param request
     * @return
     * @throws MessagingException
     */
    private <T extends AbstractMailRequestDTO> MimeMessage buildMimeMessage(final T request) throws MessagingException {
        final AbstractMailContentBuilder<T> mailContentBuilder = getMailContentBuilder(request.getEntityType());
        return mailContentBuilder.buildContent(request);
    }

    /**
     *
     * @param <T>
     * @param clazz
     * @return
     */
    private <T extends AbstractMailRequestDTO> AbstractMailContentBuilder<T> getMailContentBuilder(Class<T> clazz) {
        ResolvableType type = ResolvableType.forClassWithGenerics(AbstractMailContentBuilder.class, clazz);
        return applicationContext.<AbstractMailContentBuilder<T>>getBeanProvider(type).getObject();
    }
}
