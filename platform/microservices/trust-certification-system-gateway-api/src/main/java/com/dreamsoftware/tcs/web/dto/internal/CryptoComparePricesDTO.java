package com.dreamsoftware.tcs.web.dto.internal;

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

    private Double USD;
    private Double EUR;

}
