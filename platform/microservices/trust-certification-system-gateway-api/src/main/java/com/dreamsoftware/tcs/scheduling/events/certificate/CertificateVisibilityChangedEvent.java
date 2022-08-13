package com.dreamsoftware.tcs.scheduling.events.certificate;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.context.ApplicationEvent;

/**
 * Certificate Visibility Changed Event
 *
 * @author ssanchez
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class CertificateVisibilityChangedEvent extends ApplicationEvent {

    private final String id;
    private final Boolean isVisible;

    public CertificateVisibilityChangedEvent(Object source, final String id, final Boolean isVisible) {
        super(source);
        this.id = id;
        this.isVisible = isVisible;
    }
}
