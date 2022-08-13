package com.dreamsoftware.tcs.service.impl;

import com.dreamsoftware.tcs.mail.content.AbstractMailContentBuilder;
import com.dreamsoftware.tcs.model.mail.AbstractMailRequestDTO;
import com.dreamsoftware.tcs.model.mail.CADisabledMailRequestDTO;
import com.dreamsoftware.tcs.service.IMailClientService;
import com.dreamsoftware.tcs.persistence.nosql.entity.EmailEntity;
import com.dreamsoftware.tcs.persistence.nosql.entity.EmailTypeEnum;
import com.dreamsoftware.tcs.persistence.nosql.repository.EmailRepository;
import com.dreamsoftware.tcs.model.mail.UserPendingValidationMailRequestDTO;
import com.dreamsoftware.tcs.model.mail.CAEnabledMailRequestDTO;
import com.dreamsoftware.tcs.model.mail.UserActivatedEventMailRequestDTO;
import com.dreamsoftware.tcs.persistence.nosql.repository.UserRepository;
import java.util.Date;
import java.util.Optional;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
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
     * @param request
     */
    @Override
    public void sendMail(final UserActivatedEventMailRequestDTO request) {
        Assert.notNull(request, "request can not be null");
        Assert.notNull(request.getEmail(), "Email can not be null");
        Assert.hasLength(request.getEmail(), "Email can be empty");
        logger.debug("sendMail - UserActivatedEventMailRequestDTO");
        sendMail(UserActivatedEventMailRequestDTO.class, request, EmailTypeEnum.USER_ACTIVATED);
    }

    /**
     *
     * @param request
     */
    @Override
    public void sendMail(final UserPendingValidationMailRequestDTO request) {
        Assert.notNull(request, "request can not be null");
        Assert.notNull(request.getEmail(), "Email can not be null");
        Assert.hasLength(request.getEmail(), "Email can be empty");
        logger.debug("sendMail - UserPendingValidationMailRequestDTO");
        sendMail(UserPendingValidationMailRequestDTO.class, request, EmailTypeEnum.USER_PENDING_VALIDATION);
    }

    /**
     *
     * @param request
     */
    @Override
    public void sendMail(final CAEnabledMailRequestDTO request) {
        Assert.notNull(request, "request can not be null");
        Assert.notNull(request.getEmail(), "Email can not be null");
        Assert.hasLength(request.getEmail(), "Email can be empty");
        logger.debug("sendMail - CAEnabledMailRequestDTO");
        sendMail(CAEnabledMailRequestDTO.class, request, EmailTypeEnum.CA_ENABLED);
    }

    /**
     *
     * @param request
     */
    @Override
    public void sendMail(final CADisabledMailRequestDTO request) {
        Assert.notNull(request, "request can not be null");
        Assert.notNull(request.getEmail(), "Email can not be null");
        Assert.hasLength(request.getEmail(), "Email can be empty");
        logger.debug("sendMail - CADisabledMailRequestDTO");
        sendMail(CADisabledMailRequestDTO.class, request, EmailTypeEnum.CA_DISABLED);
    }

    /**
     * Private Methods
     */
    /**
     *
     * @param <T>
     * @param clazz
     * @param request
     * @param type
     */
    private <T extends AbstractMailRequestDTO> void sendMail(final Class<T> clazz, final T request, final EmailTypeEnum type) {
        try {
            final AbstractMailContentBuilder<T> mailContentBuilder = getMailContentBuilder(clazz);
            final MimeMessage message = mailContentBuilder.buildContent(request);
            sendMail(message, request.getEmail(), type);
        } catch (MessagingException ex) {
            logger.error(clazz.getName() + " - MessagingException: " + ex.getMessage());
        }
    }

    /**
     * Send Mail
     *
     * @param message
     * @param userId
     * @param type
     */
    private void sendMail(final MimeMessage message,
            final String userId,
            final EmailTypeEnum type) {
        try {
            mailSender.send(message);
        } catch (final MailException ex) {
            final EmailEntity emailEntity = Optional.ofNullable(
                    emailRepository.findByUserIdAndType(new ObjectId(userId), type))
                    .map((emailEntitySaved) -> {
                        emailEntitySaved.setLastChance(new Date());
                        emailEntitySaved.setError(ex.getMessage());
                        return emailEntitySaved;
                    }).orElseGet(() -> {
                final EmailEntity newEmailEntity = new EmailEntity();
                newEmailEntity.setUser(userRepository.findById(new ObjectId(userId)).orElse(null));
                newEmailEntity.setType(type);
                newEmailEntity.setLastChance(new Date());
                newEmailEntity.setError(ex.getMessage());
                return newEmailEntity;
            });
            emailRepository.save(emailEntity);
        }
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
