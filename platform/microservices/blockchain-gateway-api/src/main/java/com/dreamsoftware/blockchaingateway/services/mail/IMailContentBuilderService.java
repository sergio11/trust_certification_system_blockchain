package com.dreamsoftware.blockchaingateway.services.mail;

import com.dreamsoftware.blockchaingateway.web.dto.internal.SendMailForActivateAccountDTO;
import com.dreamsoftware.blockchaingateway.web.dto.internal.SendMailForConfirmActivationDTO;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

/**
 * Mail Content Builder Service
 *
 * @author ssanchez
 */
public interface IMailContentBuilderService {

    /**
     *
     * @param request
     * @return
     * @throws MessagingException
     */
    MimeMessage buildContent(final SendMailForConfirmActivationDTO request) throws MessagingException;

    /**
     *
     * @param request
     * @return
     * @throws javax.mail.MessagingException
     */
    MimeMessage buildContent(final SendMailForActivateAccountDTO request) throws MessagingException;
}
