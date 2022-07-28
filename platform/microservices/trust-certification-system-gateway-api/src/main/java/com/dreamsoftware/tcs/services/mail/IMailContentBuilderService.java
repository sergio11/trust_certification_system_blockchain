package com.dreamsoftware.tcs.services.mail;

import com.dreamsoftware.tcs.web.dto.internal.SendMailForActivateAccountDTO;
import com.dreamsoftware.tcs.web.dto.internal.SendMailForConfirmActivationDTO;
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
