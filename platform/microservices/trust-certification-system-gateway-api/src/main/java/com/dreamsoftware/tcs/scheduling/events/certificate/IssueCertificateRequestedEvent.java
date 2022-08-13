package com.dreamsoftware.tcs.scheduling.events.certificate;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.context.ApplicationEvent;

/**
 * Issue Certificate Requested Event
 *
 * @author ssanchez
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class IssueCertificateRequestedEvent extends ApplicationEvent {

    private final String id;

    public IssueCertificateRequestedEvent(Object source, final String id) {
        super(source);
        this.id = id;
    }
}
