package com.dreamsoftware.tcs.scheduling.events.certificate;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.context.ApplicationEvent;

/**
 * Certificate Disabled Event
 *
 * @author ssanchez
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class CertificateDisabledEvent extends ApplicationEvent {

    private final String id;

    public CertificateDisabledEvent(Object source, final String id) {
        super(source);
        this.id = id;
    }
}
