package com.dreamsoftware.tcs.web.dto.internal;

import java.net.URI;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author ssanchez
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreatedOrderDTO {

    /**
     * Order Id
     */
    private String orderId;

    /**
     * Approval Link
     */
    private URI approvalLink;

}
