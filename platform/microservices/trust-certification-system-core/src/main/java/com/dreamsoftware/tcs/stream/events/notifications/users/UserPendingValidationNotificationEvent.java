package com.dreamsoftware.tcs.stream.events.notifications.users;

import com.dreamsoftware.tcs.stream.events.notifications.AbstractNotificationEvent;
import com.dreamsoftware.tcs.utils.EntityAnnotation;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 *
 * @author ssanchez
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@EntityAnnotation(entityClass = UserPendingValidationNotificationEvent.class)
public class UserPendingValidationNotificationEvent extends AbstractNotificationEvent {

    /**
     * User Id
     */
    private final String userId;
}
