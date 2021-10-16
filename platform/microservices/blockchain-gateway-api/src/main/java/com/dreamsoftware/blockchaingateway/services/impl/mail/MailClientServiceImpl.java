package com.dreamsoftware.blockchaingateway.services.impl.mail;

import com.dreamsoftware.blockchaingateway.services.mail.IMailClientService;
import com.dreamsoftware.blockchaingateway.services.mail.IMailContentBuilderService;
import com.dreamsoftware.blockchaingateway.persistence.nosql.entity.EmailEntity;
import com.dreamsoftware.blockchaingateway.persistence.nosql.entity.EmailTypeEnum;
import com.dreamsoftware.blockchaingateway.persistence.nosql.entity.UserEntity;
import com.dreamsoftware.blockchaingateway.persistence.nosql.repository.EmailRepository;
import com.dreamsoftware.blockchaingateway.persistence.nosql.repository.UserRepository;
import com.dreamsoftware.blockchaingateway.i18n.service.I18NService;
import com.dreamsoftware.blockchaingateway.web.dto.internal.SendMailForActivateAccountDTO;
import java.util.Date;
import java.util.Optional;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.MailException;
import org.springframework.stereotype.Service;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.util.Assert;

/**
 *
 * @author ssanchez
 */
@Service
@RequiredArgsConstructor
public class MailClientServiceImpl implements IMailClientService {

    private Logger logger = LoggerFactory.getLogger(MailClientServiceImpl.class);

    private final IMailContentBuilderService mailContentBuilderService;
    private final JavaMailSender mailSender;
    private final EmailRepository emailRepository;
    private final UserRepository userRepository;
    private final I18NService i18nService;

    /**
     *
     * @param numberOfEmailsToForwarding
     */
    @Override
    public void forwardEmails(int numberOfEmailsToForwarding) {
        Assert.isTrue(numberOfEmailsToForwarding > 0, "Number of mails should be greater than 0");
    }

    @Override
    public void sendMailForActivateAccount(String id) {
        Assert.notNull(id, "Id can not be null");
        Assert.hasLength(id, "Id can be empty");
        Assert.isTrue(ObjectId.isValid(id), "Id is invalid");
        try {

            final UserEntity user = userRepository.findById(new ObjectId(id)).orElseThrow(() -> {
                throw new IllegalStateException("User not found!");
            });

            final SendMailForActivateAccountDTO request = SendMailForActivateAccountDTO.builder()
                    .email(user.getEmail())
                    .firstname(user.getName())
                    .locale(i18nService.parseLocaleOrDefault(user.getLanguage()))
                    .id(id)
                    .confirmationToken(user.getConfirmationToken())
                    .build();

            final MimeMessage message = mailContentBuilderService.buildContent(request);
            sendMail(message, request.getId(), EmailTypeEnum.CONFIRM_ACCOUNT_ACTIVATION);
        } catch (Throwable ex) {
            logger.debug("MessagingException: " + ex.getMessage());
        }
    }

    @Override
    public void sendMailForConfirmActivation(String id) {
        Assert.notNull(id, "Id can not be null");
        Assert.hasLength(id, "Id can be empty");
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
}
