package com.dreamsoftware.tcs.services.mail;

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
