package com.dreamsoftware.tcs.web.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
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
public class OrderDetailDTO {

    /**
     * Identity
     */
    @JsonProperty("identity")
    private String id;

    /**
     * Order Id
     */
    @JsonProperty("order_id")
    private String orderId;

    /**
     * Approval Link
     */
    @JsonProperty("approval_link")
    private String approvalLink;

    /**
     * Status
     */
    @JsonProperty("status")
    private String status;

    /**
     * Tokens
     */
    @JsonProperty("tokens")
    private Long tokens;

    /**
     * Amount
     */
    @JsonProperty("amount")
    private Double amount;

    /**
     * User Id
     */
    @JsonProperty("user_id")
    private String userId;

}
