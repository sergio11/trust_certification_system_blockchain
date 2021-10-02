package com.dreamsoftware.blockchaingateway.services.mail;

import com.dreamsoftware.blockchaingateway.web.dto.internal.SendMailForActivateAccountDTO;
import com.dreamsoftware.blockchaingateway.web.dto.internal.SendMailForConfirmActivationDTO;

/**
 *
 * @author ssanchez
 */
public interface IMailClientService {

    /**
     * Send Mail
     *
     * @param request
     */
    void sendMail(final SendMailForActivateAccountDTO request);

    /**
     * Send Mail
     *
     * @param request
     */
    void sendMail(final SendMailForConfirmActivationDTO request);
}
