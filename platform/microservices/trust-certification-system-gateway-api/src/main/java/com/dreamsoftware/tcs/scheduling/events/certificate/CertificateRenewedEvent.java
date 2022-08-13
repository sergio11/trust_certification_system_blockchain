package com.dreamsoftware.tcs.scheduling.events.certificate;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.context.ApplicationEvent;

/**
 * Certificate Renewed Event
 *
 * @author ssanchez
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class CertificateRenewedEvent extends ApplicationEvent {

    private final String id;

    public CertificateRenewedEvent(Object source, final String id) {
        super(source);
        this.id = id;
    }
}
