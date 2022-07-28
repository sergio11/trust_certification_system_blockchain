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
public class UserActivatedEvent extends ApplicationEvent {

    private final String userId;

    public UserActivatedEvent(Object source, final String userId) {
        super(source);
        this.userId = userId;
    }
}
