package com.dreamsoftware.blockchaingateway.services.mail;

import com.dreamsoftware.blockchaingateway.web.dto.internal.SendMailForActivateAccountDTO;
import com.dreamsoftware.blockchaingateway.web.dto.internal.SendMailForConfirmActivationDTO;

/**
 *
 * @author ssanchez
 */
public interface IMailClientService {

    /**
     * Send Mail for activate account
     *
     * @param id
     */
    void sendMailForActivateAccount(final String id);

    /**
     * Send Mail For confirm activation
     *
     * @param id
     */
    void sendMailForConfirmActivation(final String id);

}
