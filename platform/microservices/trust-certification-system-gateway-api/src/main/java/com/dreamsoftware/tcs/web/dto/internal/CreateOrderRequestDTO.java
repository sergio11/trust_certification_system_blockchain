package com.dreamsoftware.tcs.web.dto.internal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.net.URI;

/**
 *
 * @author ssanchez
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateOrderRequestDTO {

    /**
     * Payer Id
     */
    private String payerId;

    /**
     * Payer Email
     */
    private String payerEmail;

    /**
     * Total Amount
     */
    private Double totalAmount;

    /**
     * Return URL
     */
    private URI returnUrl;

}
