package com.dreamsoftware.tcs.scheduling.events.ca;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.context.ApplicationEvent;

/**
 * CA Enabled Event
 *
 * @author ssanchez
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class CAEnabledEvent extends ApplicationEvent {

    private final String walletHash;

    public CAEnabledEvent(Object source, final String walletHash) {
        super(source);
        this.walletHash = walletHash;
    }
}
