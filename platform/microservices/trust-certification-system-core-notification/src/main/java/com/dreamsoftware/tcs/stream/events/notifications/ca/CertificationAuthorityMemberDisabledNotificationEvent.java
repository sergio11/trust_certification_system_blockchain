package com.dreamsoftware.tcs.stream.events.notifications.ca;

import com.dreamsoftware.tcs.stream.events.AbstractEvent;
import com.dreamsoftware.tcs.utils.EntityAnnotation;
import lombok.*;

/**
 *
 * @author ssanchez
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EntityAnnotation(entityClass = CertificationAuthorityMemberDisabledNotificationEvent.class)
public class CertificationAuthorityMemberDisabledNotificationEvent extends AbstractEvent {

    /**
     *  User Wallet hash
     */
    private String userWalletHash;
}
