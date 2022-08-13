package com.dreamsoftware.tcs.mail.model.service.impl;

import com.dreamsoftware.tcs.mail.content.AbstractMailContentBuilder;
import com.dreamsoftware.tcs.mail.model.AbstractMailRequestDTO;
import com.dreamsoftware.tcs.mail.model.service.IMailClientService;
import com.dreamsoftware.tcs.persistence.nosql.entity.EmailEntity;
import com.dreamsoftware.tcs.persistence.nosql.entity.EmailTypeEnum;
import com.dreamsoftware.tcs.persistence.nosql.repository.EmailRepository;
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
            sendMail(message, request.getEmail(), request.getType());
        } catch (MessagingException ex) {
            logger.error(request.getEntityType().getName() + " - MessagingException: " + ex.getMessage());
        }
    }

    /**
     * Private Methods
     */
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
