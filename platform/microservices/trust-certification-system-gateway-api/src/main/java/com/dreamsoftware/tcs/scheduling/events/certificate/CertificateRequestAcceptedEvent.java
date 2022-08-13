package com.dreamsoftware.tcs.scheduling.events.certificate;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.context.ApplicationEvent;

/**
 * Certificate Request Accepted Event
 *
 * @author ssanchez
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class CertificateRequestAcceptedEvent extends ApplicationEvent {

    private final String id;

    public CertificateRequestAcceptedEvent(Object source, final String id) {
        super(source);
        this.id = id;
    }
}
