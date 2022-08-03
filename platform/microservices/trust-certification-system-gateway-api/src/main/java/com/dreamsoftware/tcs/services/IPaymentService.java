package com.dreamsoftware.tcs.services;

import com.dreamsoftware.tcs.persistence.nosql.entity.UserEntity;
import com.dreamsoftware.tcs.web.dto.internal.CreatedOrderDTO;
import java.net.URI;

public interface IPaymentService {

    /**
     * Create Order
     *
     * @param userEntity
     * @param totalAmount
     * @param returnUrl
     */
    CreatedOrderDTO createOrder(final UserEntity userEntity, final Double totalAmount, final URI returnUrl);

    /**
     * Capture Order
     *
     * @param orderId
     */
    void captureOrder(final String orderId);
}
