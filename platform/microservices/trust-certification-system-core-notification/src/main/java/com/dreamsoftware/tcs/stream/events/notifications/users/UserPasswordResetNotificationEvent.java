package com.dreamsoftware.tcs.stream.events.notifications.users;

import com.dreamsoftware.tcs.stream.events.AbstractEvent;
import com.dreamsoftware.tcs.utils.EntityAnnotation;
import lombok.*;

/**
 *
 * @author ssanchez
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@EntityAnnotation(entityClass = UserPasswordResetNotificationEvent.class)
public class UserPasswordResetNotificationEvent extends AbstractEvent {

    /**
     * User Id
     */
    private String email;

    /**
     * User Full Name
     */
    private String fullName;

    /**
     * Confirmation Token
     */
    private String confirmationToken;

    /**
     * Locale
     */
    private String locale;
}
