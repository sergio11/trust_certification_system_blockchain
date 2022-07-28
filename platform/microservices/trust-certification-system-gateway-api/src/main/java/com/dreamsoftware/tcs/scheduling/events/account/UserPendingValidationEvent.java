package com.dreamsoftware.tcs.scheduling.events.account;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.context.ApplicationEvent;

/**
 * User Pending Validation Event
 *
 * @author ssanchez
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class UserPendingValidationEvent extends ApplicationEvent {

    private final String userId;

    public UserPendingValidationEvent(Object source, final String userId) {
        super(source);
        this.userId = userId;
    }
}
