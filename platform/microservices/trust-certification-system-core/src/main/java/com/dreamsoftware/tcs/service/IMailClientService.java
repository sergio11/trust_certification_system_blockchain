package com.dreamsoftware.tcs.service;

import com.dreamsoftware.tcs.model.mail.CADisabledMailRequestDTO;
import com.dreamsoftware.tcs.model.mail.UserPendingValidationMailRequestDTO;
import com.dreamsoftware.tcs.model.mail.CAEnabledMailRequestDTO;
import com.dreamsoftware.tcs.model.mail.UserActivatedEventMailRequestDTO;

/**
 *
 * @author ssanchez
 */
public interface IMailClientService {

    /**
     * Forwards Emails
     *
     * @param numberOfEmailsToForwarding
     */
    void forwardEmails(int numberOfEmailsToForwarding);

    /**
     * Send Mail for activate account
     *
     * @param request
     */
    void sendMail(final UserActivatedEventMailRequestDTO request);

    /**
     * Send Mail For confirm activation
     *
     * @param request
     */
    void sendMail(final UserPendingValidationMailRequestDTO request);

    /**
     * Send Mail For CA Enabled
     *
     * @param request
     */
    void sendMail(final CAEnabledMailRequestDTO request);

    /**
     * Send Mail For CA Disabled
     *
     * @param request
     */
    void sendMail(final CADisabledMailRequestDTO request);

}
