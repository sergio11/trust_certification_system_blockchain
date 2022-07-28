package com.dreamsoftware.tcs.scheduling.tasks.accounts;

import com.dreamsoftware.tcs.services.IAccountsService;
import com.dreamsoftware.tcs.services.mail.IMailClientService;
import lombok.RequiredArgsConstructor;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 *
 * @author ssanchez
 */
@Component
@RequiredArgsConstructor
public class AccountsTasks {

    private static final Logger logger = LoggerFactory.getLogger(AccountsTasks.class);

    private final IMailClientService mailClienService;
    private final IAccountsService accountsService;

    @Value("${task.account.unsuccessful.mail.forwarding.number.of.emails.to.forward}")
    private Integer numberOfEmailsToForwarding;

    /**
     * Delete Unactivated Accounts Task
     */
    @Scheduled(cron = "${task.account.delete.unactivated.accounts}")
    public void configureDeleteUnactivatedAccountsTask() {
        logger.debug("configureDeleteUnactivatedAccountsTask CALLED");
        accountsService.deleteUnactivatedAccounts();
    }

    /**
     * Unsuccessful Mail Forwarding
     */
    @Scheduled(cron = "${task.account.unsuccessful.mail.forwarding}")
    public void unsuccessfulMailForwarding() {
        logger.debug("unsuccessfulMailForwarding CALLED");
        mailClienService.forwardEmails(numberOfEmailsToForwarding);
    }
}
