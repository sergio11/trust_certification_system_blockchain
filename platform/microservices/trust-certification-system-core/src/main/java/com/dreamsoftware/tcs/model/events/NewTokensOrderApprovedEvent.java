package com.dreamsoftware.tcs.model.events;

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
public class NewTokensOrderApprovedEvent {

    /**
     * Order Id
     */
    private ObjectId orderId;

}