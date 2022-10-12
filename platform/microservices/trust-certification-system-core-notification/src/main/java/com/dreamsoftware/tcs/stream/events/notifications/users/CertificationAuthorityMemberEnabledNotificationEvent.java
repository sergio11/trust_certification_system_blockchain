package com.dreamsoftware.tcs.stream.events.notifications.users;

import com.dreamsoftware.tcs.stream.events.notifications.AbstractNotificationEvent;
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
@EntityAnnotation(entityClass = CertificationAuthorityMemberEnabledNotificationEvent.class)
public class CertificationAuthorityMemberEnabledNotificationEvent extends AbstractNotificationEvent {

    /**
     *  User Wallet hash
     */
    private String userWalletHash;
}
