package com.dreamsoftware.tcs.scheduling.events.course;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.context.ApplicationEvent;

/**
 * New Course Registration Requested Event
 *
 * @author ssanchez
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class NewCourseRegistrationRequestedEvent extends ApplicationEvent {

    private final String caWalletHash;
    private final String courseName;

    public NewCourseRegistrationRequestedEvent(Object source, final String caWalletHash, final String courseName) {
        super(source);
        this.caWalletHash = caWalletHash;
        this.courseName = courseName;
    }
}
