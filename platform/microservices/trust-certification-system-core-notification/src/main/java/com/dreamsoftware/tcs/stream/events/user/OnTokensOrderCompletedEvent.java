package com.dreamsoftware.tcs.stream.events.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;

/**
 *
 * @author ssanchez
 *
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OnTokensOrderCompletedEvent {

    /**
     * Order Id
     */
    private ObjectId orderId;

}
