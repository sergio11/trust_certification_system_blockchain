package com.dreamsoftware.tcs.stream.events.notifications.users;

import com.dreamsoftware.tcs.persistence.nosql.entity.UserTypeEnum;
import com.dreamsoftware.tcs.stream.events.AbstractEvent;
import com.dreamsoftware.tcs.utils.EntityAnnotation;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 *
 * @author ssanchez
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EntityAnnotation(entityClass = UserRegisteredNotificationEvent.class)
public class UserRegisteredNotificationEvent extends AbstractEvent {

    /**
     * User Wallet Hash
     */
    private String walletHash;

    /**
     *  User Type
     */
    private UserTypeEnum type;
}
