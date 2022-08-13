package com.dreamsoftware.tcs.scheduling.events.ca;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.context.ApplicationEvent;

/**
 * CA Disabled Event
 *
 * @author ssanchez
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class CADisabledEvent extends ApplicationEvent {

    private final String walletHash;

    public CADisabledEvent(Object source, final String walletHash) {
        super(source);
        this.walletHash = walletHash;
    }
}
