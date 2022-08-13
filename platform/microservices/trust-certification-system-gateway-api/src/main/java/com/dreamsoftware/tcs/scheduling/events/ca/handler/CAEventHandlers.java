package com.dreamsoftware.tcs.scheduling.events.ca.handler;

import com.dreamsoftware.tcs.i18n.service.I18NService;
import com.dreamsoftware.tcs.model.mail.CADisabledMailRequestDTO;
import com.dreamsoftware.tcs.model.mail.CAEnabledMailRequestDTO;
import com.dreamsoftware.tcs.persistence.nosql.repository.UserRepository;
import com.dreamsoftware.tcs.service.IMailClientService;
import com.dreamsoftware.tcs.scheduling.events.ca.CADisabledEvent;
import com.dreamsoftware.tcs.scheduling.events.ca.CAEnabledEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.util.Assert;

/**
 *
 * @author ssanchez
 */
@Component
@RequiredArgsConstructor
public class CAEventHandlers {

    private static final Logger logger = LoggerFactory.getLogger(CAEventHandlers.class);

    private final IMailClientService mailClientService;
    private final UserRepository userRepository;
    private final I18NService i18nService;

    /**
     *
     * @param event
     */
    @Async
    @EventListener
    void handle(final CAEnabledEvent event) {
        Assert.notNull(event.getWalletHash(), "Wallet Hash can not be null");
        logger.debug("CAEnabledEvent handled!");
        userRepository.findOneByWalletHash(event.getWalletHash()).ifPresent((user) -> {
            mailClientService.sendMail(CAEnabledMailRequestDTO
                    .builder()
                    .email(user.getEmail())
                    .id(user.getId().toString())
                    .name(user.getName())
                    .locale(i18nService.parseLocaleOrDefault(user.getLanguage()))
                    .build());
        });

    }

    /**
     *
     * @param event
     */
    @Async
    @EventListener
    void handle(final CADisabledEvent event) {
        Assert.notNull(event.getWalletHash(), "Wallet Hash can not be null");
        logger.debug("CADisabledEvent handled!");
        userRepository.findOneByWalletHash(event.getWalletHash()).ifPresent((user) -> {
            mailClientService.sendMail(CADisabledMailRequestDTO
                    .builder()
                    .email(user.getEmail())
                    .id(user.getId().toString())
                    .name(user.getName())
                    .locale(i18nService.parseLocaleOrDefault(user.getLanguage()))
                    .build());
        });
    }
}
