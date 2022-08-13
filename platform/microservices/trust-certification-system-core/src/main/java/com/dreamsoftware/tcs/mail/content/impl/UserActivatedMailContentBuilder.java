package com.dreamsoftware.tcs.mail.content.impl;

import com.dreamsoftware.tcs.mail.content.AbstractMailContentBuilder;
import com.dreamsoftware.tcs.mail.model.UserActivatedEventMailRequestDTO;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 *
 * @author ssanchez
 */
@Component
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class UserActivatedMailContentBuilder extends AbstractMailContentBuilder<UserActivatedEventMailRequestDTO> {

    /**
     *
     * @param request
     * @return
     * @throws MessagingException
     */
    @Override
    public MimeMessage buildContent(final UserActivatedEventMailRequestDTO request) throws MessagingException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
