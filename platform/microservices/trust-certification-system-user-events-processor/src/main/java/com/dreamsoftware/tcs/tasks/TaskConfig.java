package com.dreamsoftware.tcs.tasks;

import com.dreamsoftware.tcs.persistence.nosql.entity.UserStateEnum;
import com.dreamsoftware.tcs.persistence.nosql.repository.UserRepository;
import com.dreamsoftware.tcs.service.IPasswordResetTokenService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.javacrumbs.shedlock.spring.annotation.SchedulerLock;
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

    private final UserRepository userRepository;
    private final IPasswordResetTokenService passwordResetTokenService;

    /**
     * Delete Unactivated Accounts Task
     */
    @Scheduled(cron = "${task.delete.unactivated.accounts}")
    @SchedulerLock(name = "configureDeleteUnactivatedAccountsTask")
    public void configureDeleteUnactivatedAccountsTask() {
        log.debug("configureDeleteUnactivatedAccountsTask CALLED");
        userRepository.deleteByState(UserStateEnum.PENDING_ACTIVATE);
    }

    /**
     * Delete Expired Password Reset Tokens
     */
    @Scheduled(cron = "${task.delete.expired.password.reset.tokens}")
    @SchedulerLock(name = "configureDeleteExpiredPasswordResetTokens")
    public void configureDeleteExpiredPasswordResetTokens() {
        log.debug("configureDeleteExpiredPasswordResetTokens CALLED");
        passwordResetTokenService.deleteExpiredTokens();
    }

}
