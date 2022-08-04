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
     * External Order Id
     */
    @JsonProperty("external_order_id")
    private String externalOrderId;

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
     * Token Price EUR
     */
    @JsonProperty("token_price_EUR")
    private Double tokenPriceEUR;

    /**
     * Token Price USD
     */
    @JsonProperty("token_price_USD")
    private Double tokenPriceUSD;

    /**
     * Amount EUR
     */
    @JsonProperty("amount_EUR")
    private Double amountEUR;

    /**
     * Amount USD
     */
    @JsonProperty("amount_USD")
    private Double amountUSD;

    /**
     * Amount WEI
     */
    @JsonProperty("amount_WEI")
    private Long amountWEI;

    /**
     * User Id
     */
    @JsonProperty("user_id")
    private String userId;

}
