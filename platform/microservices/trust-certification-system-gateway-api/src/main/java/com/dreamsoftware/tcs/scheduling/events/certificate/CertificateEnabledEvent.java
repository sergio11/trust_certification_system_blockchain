package com.dreamsoftware.tcs.scheduling.events.certificate;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.context.ApplicationEvent;

/**
 * Certificate Enabled Event
 *
 * @author ssanchez
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class CertificateEnabledEvent extends ApplicationEvent {

    private final String id;

    public CertificateEnabledEvent(Object source, final String id) {
        super(source);
        this.id = id;
    }
}
