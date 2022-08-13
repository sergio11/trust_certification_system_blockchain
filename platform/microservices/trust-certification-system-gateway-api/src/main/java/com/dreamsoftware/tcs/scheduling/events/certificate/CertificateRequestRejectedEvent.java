package com.dreamsoftware.tcs.scheduling.events.certificate;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.context.ApplicationEvent;

/**
 * Certificate Request Rejected Event
 *
 * @author ssanchez
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class CertificateRequestRejectedEvent extends ApplicationEvent {

    private final String id;

    public CertificateRequestRejectedEvent(Object source, final String id) {
        super(source);
        this.id = id;
    }
}
