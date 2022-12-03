package com.dreamsoftware.tcs.services;

import com.dreamsoftware.tcs.web.dto.internal.CreateOrderRequestDTO;
import com.dreamsoftware.tcs.web.dto.internal.CreatedOrderDTO;

public interface IPaymentService {

    /**
     * Create Order
     * @param createOrderRequestDTO
     * @return
     */
    CreatedOrderDTO createOrder(final CreateOrderRequestDTO createOrderRequestDTO);

    /**
     * Capture Order
     *
     * @param orderId
     */
    void captureOrder(final String orderId);
}
