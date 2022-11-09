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
@EntityAnnotation(entityClass = CertificationAuthorityMemberRemovedNotificationEvent.class)
public class CertificationAuthorityMemberRemovedNotificationEvent extends AbstractEvent {

    /**
     * User Wallet Hash
     */
    private String userWalletHash;
}
