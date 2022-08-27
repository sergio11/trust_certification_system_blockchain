package com.dreamsoftware.tcs.tasks;

import com.dreamsoftware.tcs.mail.service.IMailClientService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.javacrumbs.shedlock.spring.annotation.SchedulerLock;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 *
 * @author ssanchez
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class TaskConfig {

    private final IMailClientService mailClienService;

    @Value("${task.unsuccessful.mail.forwarding.number.of.emails.to.forward}")
    private Integer numberOfEmailsToForwarding;

    /**
     * Unsuccessful Mail Forwarding
     */
    @Scheduled(cron = "${task.unsuccessful.mail.forwarding}")
    @SchedulerLock(name = "unsuccessfulMailForwarding")
    public void unsuccessfulMailForwarding() {
        log.debug("unsuccessfulMailForwarding CALLED");
        mailClienService.forwardEmails(numberOfEmailsToForwarding);
    }
}
