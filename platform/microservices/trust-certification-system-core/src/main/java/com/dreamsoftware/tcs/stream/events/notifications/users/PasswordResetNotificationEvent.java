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
@EntityAnnotation(entityClass = PasswordResetNotificationEvent.class)
public class PasswordResetNotificationEvent extends AbstractNotificationEvent {

    /**
     * Id
     */
    private String id;

    /**
     * Token
     */
    private String token;

    /**
     * Expiry Date
     */
    private String expiryDate;

    /**
     * Name
     */
    private String name;

    /**
     * Mail
     */
    private String email;

    /**
     * Locale
     */
    private String locale;
}
