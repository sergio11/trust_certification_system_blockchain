package com.dreamsoftware.tcs.service;

import com.dreamsoftware.tcs.model.SendMailForActivateAccountDTO;
import com.dreamsoftware.tcs.model.SendMailForConfirmActivationDTO;
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
