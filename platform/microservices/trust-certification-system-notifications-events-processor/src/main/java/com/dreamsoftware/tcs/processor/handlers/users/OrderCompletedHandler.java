package com.dreamsoftware.tcs.processor.handlers.users;

import com.dreamsoftware.tcs.i18n.service.I18NService;
import com.dreamsoftware.tcs.mail.model.user.UserOrderCompletedMailRequestDTO;
import com.dreamsoftware.tcs.mail.service.IMailClientService;
import com.dreamsoftware.tcs.persistence.nosql.entity.UserEntity;
import com.dreamsoftware.tcs.persistence.nosql.repository.CreatedOrderRepository;
import com.dreamsoftware.tcs.persistence.nosql.repository.UserRepository;
import com.dreamsoftware.tcs.processor.handlers.AbstractNotificationHandler;
import com.dreamsoftware.tcs.service.INotificationService;
import com.dreamsoftware.tcs.stream.events.notifications.users.OrderCompletedNotificationEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

/**
 *
 * @author ssanchez
 */
@Component
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@RequiredArgsConstructor
@Slf4j
public class OrderCompletedHandler extends AbstractNotificationHandler<OrderCompletedNotificationEvent> {

    private final IMailClientService mailClientService;
    private final UserRepository userRepository;
    private final I18NService i18nService;
    private final CreatedOrderRepository createdOrderRepository;
    private final INotificationService notificationService;

    /**
     *
     * @param notification
     */
    @Override
    public void onHandle(final OrderCompletedNotificationEvent notification) {
        Assert.notNull(notification, "Notification can not be null");
        log.debug("UserRegisteredNotificationHandler handled!");
        createdOrderRepository.findById(new ObjectId(notification.getOrderId())).ifPresent((orderEntitySaved) -> {
            notificationService.onUserOrderCompleted(orderEntitySaved);
            final UserEntity userEntity = orderEntitySaved.getUser();
            mailClientService.sendMail(UserOrderCompletedMailRequestDTO.builder()
                    .orderId(orderEntitySaved.getId().toString())
                    .amountEUR(orderEntitySaved.getAmountEUR())
                    .amountUSD(orderEntitySaved.getAmountUSD())
                    .amountWEI(orderEntitySaved.getAmountWEI())
                    .email(userEntity.getEmail())
                    .userName(userEntity.getFullName())
                    .tokenPriceEUR(orderEntitySaved.getTokenPriceEUR())
                    .tokenPriceUSD(orderEntitySaved.getTokenPriceUSD())
                    .tokens(orderEntitySaved.getTokens())
                    .locale(i18nService.parseLocaleOrDefault(userEntity.getLanguage()))
                    .build());

        });

    }

}
