package com.dreamsoftware.tcs.stream.events.notifications.ca;

import com.dreamsoftware.tcs.stream.events.notifications.AbstractNotificationEvent;
import com.dreamsoftware.tcs.utils.EntityAnnotation;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 *
 * @author ssanchez
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EntityAnnotation(entityClass = CADisabledNotificationEvent.class)
public class CADisabledNotificationEvent extends AbstractNotificationEvent {

    /**
     * CA Wallet Hash
     */
    private String walletHash;

}
