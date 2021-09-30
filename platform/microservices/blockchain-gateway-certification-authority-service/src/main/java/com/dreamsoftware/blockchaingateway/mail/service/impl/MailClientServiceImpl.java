package com.dreamsoftware.blockchaingateway.mail.service.impl;

import com.dreamsoftware.blockchaingateway.mail.service.IMailClientService;
import com.dreamsoftware.blockchaingateway.mail.service.IMailContentBuilderService;
import com.dreamsoftware.blockchaingateway.persistence.nosql.entity.EmailEntity;
import com.dreamsoftware.blockchaingateway.persistence.nosql.entity.EmailTypeEnum;
import com.dreamsoftware.blockchaingateway.persistence.nosql.repository.EmailRepository;
import com.dreamsoftware.blockchaingateway.persistence.nosql.repository.UserRepository;
import com.dreamsoftware.blockchaingateway.web.dto.internal.SendMailForActivateAccountDTO;
import com.dreamsoftware.blockchaingateway.web.dto.internal.SendMailForConfirmActivationDTO;
import java.util.Date;
import java.util.Optional;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
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

    @Override
    public void sendMail(SendMailForActivateAccountDTO request) {
        Assert.notNull(request, "Request can not be null");
        Assert.notNull(request.getEmail(), "Email can not be null");
        Assert.hasLength(request.getEmail(), "Email can be empty");
        logger.debug("Send Mail for Activate Account");
        try {
            final MimeMessage message = mailContentBuilderService.buildContent(request);
            sendMail(message, request.getEmail(), EmailTypeEnum.CONFIRM_ACCOUNT_ACTIVATION);
        } catch (MessagingException ex) {
            logger.debug("MessagingException: " + ex.getMessage());
        }
    }

    @Override
    public void sendMail(SendMailForConfirmActivationDTO request) {
        Assert.notNull(request, "Request can not be null");
        Assert.notNull(request.getEmail(), "Email can not be null");
        Assert.hasLength(request.getEmail(), "Email can be empty");
        try {
            final MimeMessage message = mailContentBuilderService.buildContent(request);
            sendMail(message, request.getEmail(), EmailTypeEnum.CONFIRM_ACCOUNT_ACTIVATION);
        } catch (MessagingException ex) {
            logger.debug("MessagingException: " + ex.getMessage());
        }
    }

    /**
     * Send Mail
     *
     * @param message
     * @param targetEmail
     * @param type
     */
    private void sendMail(final MimeMessage message,
            final String targetEmail,
            final EmailTypeEnum type) {
        try {
            mailSender.send(message);
        } catch (final MailException ex) {
            final EmailEntity emailEntity = Optional.ofNullable(
                    emailRepository.findByUserEmailAndType(targetEmail, type))
                    .map((emailEntitySaved) -> {
                        emailEntitySaved.setLastChance(new Date());
                        emailEntitySaved.setError(ex.getMessage());
                        return emailEntitySaved;
                    }).orElseGet(() -> {
                final EmailEntity newEmailEntity = new EmailEntity();
                newEmailEntity.setUser(userRepository.findOneByEmail(targetEmail).orElse(null));
                newEmailEntity.setType(type);
                newEmailEntity.setLastChance(new Date());
                newEmailEntity.setError(ex.getMessage());
                return newEmailEntity;
            });
            emailRepository.save(emailEntity);
        }
    }

}
