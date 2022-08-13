package com.dreamsoftware.tcs.mail.model.service;

import com.dreamsoftware.tcs.mail.model.AbstractMailRequestDTO;

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
     *
     * @param <T>
     * @param request
     */
    <T extends AbstractMailRequestDTO> void sendMail(final T request);

}
