package com.dreamsoftware.tcs.config.properties;

import java.io.Serializable;
import javax.annotation.PostConstruct;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

/**
 * Mail Properties
 *
 * @author sergio
 */
@Data
@Component
@RefreshScope
public class MailProperties implements Serializable {

    private Logger logger = LoggerFactory.getLogger(MailProperties.class);

    private static final long serialVersionUID = 1L;

    @Value("${mail.from}")
    private String mailFrom;

    @Value("${mail.templates.base.dir}")
    private String mailTemplatesBaseDir;

    @Value("${mail.resources.base.dir}")
    private String mailResourcesBaseDir;

    @Value("${mail.templates.accounts.user-activated}")
    private String useActivatedMailTemplate;

    @Value("${mail.templates.accounts.pending-validation}")
    private String userPendingValidationMailTemplate;

    @PostConstruct
    protected void onPostConstruct() {
        logger.debug("mailFrom: " + mailFrom);
        logger.debug("mailTemplatesBaseDir: " + mailTemplatesBaseDir);
        logger.debug("mailResourcesBaseDir: " + mailResourcesBaseDir);
        logger.debug("useActivatedMailTemplate: " + useActivatedMailTemplate);
        logger.debug("userPendingValidationMailTemplate: " + userPendingValidationMailTemplate);
    }
}
