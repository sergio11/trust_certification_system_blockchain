package com.dreamsoftware.tcs.stream.events.user;

import com.dreamsoftware.tcs.utils.EntityAnnotation;
import lombok.*;

/**
 *
 * @author ssanchez
 *
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EntityAnnotation(entityClass = EnableCertificationAuthorityMemberEvent.class)
public class EnableCertificationAuthorityMemberEvent extends AbstractUserManagementEvent {

    /**
     * Certification Authority Id
     */
    private String caId;

    /**
     * Certification Authority Member Id
     */
    private String memberId;

    /**
     * Certification Authority Admin Id
     */
    private String adminId;

}
