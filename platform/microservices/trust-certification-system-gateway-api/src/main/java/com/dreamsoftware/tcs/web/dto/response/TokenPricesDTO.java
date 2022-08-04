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
public class TokenPricesDTO {

    /**
     * Token Count
     */
    @JsonProperty("token_count")
    private Long tokenCount;

    /**
     * Token Price in EUR
     */
    @JsonProperty("token_price_EUR")
    private Double tokenPriceInEUR;

    /**
     * Token Price USD
     */
    @JsonProperty("token_price_USD")
    private Double tokenPriceInUSD;
}
