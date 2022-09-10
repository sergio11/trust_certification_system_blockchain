package com.dreamsoftware.tcs.stream.events.notifications.users;

import com.dreamsoftware.tcs.stream.events.notifications.AbstractNotificationEvent;
import com.dreamsoftware.tcs.utils.EntityAnnotation;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Builder;
import lombok.Data;

/**
 *
 * @author ssanchez
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@EntityAnnotation(entityClass = UserPendingValidationNotificationEvent.class)
public class UserPendingValidationNotificationEvent extends AbstractNotificationEvent {

    /**
     * User Id
     */
    private String userId;
}
