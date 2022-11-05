package com.dreamsoftware.tcs.web.dto.internal;

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
public class CryptoComparePricesDTO {

    @JsonProperty("USD")
    private Double USD;
    @JsonProperty("EUR")
    private Double EUR;

}
