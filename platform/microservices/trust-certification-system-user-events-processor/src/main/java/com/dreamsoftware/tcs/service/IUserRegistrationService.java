package com.dreamsoftware.tcs.service;

import org.springframework.messaging.support.GenericMessage;

public interface IUserRegistrationService {

    /**
     * @param event
     * @return
     */
    String handle(final GenericMessage<String> event) throws Exception;
}
