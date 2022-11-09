package com.dreamsoftware.tcs.stream.events.user;

import com.dreamsoftware.tcs.stream.events.AbstractEvent;
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
@EntityAnnotation(entityClass = NewStudentEvent.class)
public class NewStudentEvent extends AbstractEvent {

    /**
     * Wallet Hash
     */
    private String walletHash;

}
