package com.dreamsoftware.blockchaingateway.mail.properties;

import java.io.Serializable;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Mail Properties
 *
 * @author sergio
 */
@Data
@Component
public class MailProperties implements Serializable {

    private static final long serialVersionUID = 1L;

    @Value("${mail.from}")
    private String mailFrom;

    @Value("${mail.templates.base.dir}")
    private String mailTemplatesBaseDir;

    @Value("${mail.resources.base.dir}")
    private String mailResourcesBaseDir;

    @Value("${mail.registration.success.template.name}")
    private String registrationSuccessTemplate;

    @Value("${mail.confirm.account.activation.template.name}")
    private String confirmAccountActivationTemplate;
}
