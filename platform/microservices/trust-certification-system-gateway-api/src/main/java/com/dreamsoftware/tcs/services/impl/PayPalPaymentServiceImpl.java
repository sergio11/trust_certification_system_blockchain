package com.dreamsoftware.tcs.services.impl;

import com.dreamsoftware.tcs.config.properties.PaypalProperties;
import com.dreamsoftware.tcs.service.ISecurityTokenGeneratorService;
import com.dreamsoftware.tcs.services.IPaymentService;
import com.dreamsoftware.tcs.web.dto.internal.CreateOrderRequestDTO;
import com.dreamsoftware.tcs.web.dto.internal.CreatedOrderDTO;
import com.paypal.core.PayPalHttpClient;
import com.paypal.http.HttpResponse;
import com.paypal.orders.*;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Collections;
import java.util.NoSuchElementException;

/**
 * PayPal Payment Service Implementation
 *
 * @author ssanchez
 */
@Service("paymentService")
@RequiredArgsConstructor
public class PayPalPaymentServiceImpl implements IPaymentService {

    private final static String CHECKOUT_PAYMENT_INTENT = "CAPTURE";
    private final static String CURRENCY_CODE = "EUR";
    private final static String APPROVE_LINK_REL = "approve";
    private final static String SECURITY_TOKEN_PARAM = "st";

    /**
     * PayPal Http Client
     */
    private final PayPalHttpClient payPalHttpClient;
    private final PaypalProperties paypalProperties;
    private final ISecurityTokenGeneratorService securityTokenGeneratorService;

    /**
     *
     * @param createOrderRequestDTO
     * @return
     */
    @Override
    @SneakyThrows
    public CreatedOrderDTO createOrder(final CreateOrderRequestDTO createOrderRequestDTO) {
        Assert.notNull(createOrderRequestDTO.getPayerId(), "Payer id can not be null");
        Assert.notNull(createOrderRequestDTO.getPayerEmail(), "Payer email can not be null");
        Assert.notNull(createOrderRequestDTO.getTotalAmount(), "Total Amount can not be null");
        Assert.notNull(createOrderRequestDTO.getReturnUrl(), "ReturnUrl can not be null");

        final OrderRequest orderRequest = new OrderRequest();
        orderRequest.checkoutPaymentIntent(CHECKOUT_PAYMENT_INTENT);
        final Payee payee = new Payee();
        payee.email(paypalProperties.getPayeeEmailAddress());
        final PurchaseUnitRequest purchaseUnitRequest = new PurchaseUnitRequest()
                .payee(payee)
                .amountWithBreakdown(new AmountWithBreakdown()
                        .currencyCode(CURRENCY_CODE)
                        .value(createOrderRequestDTO.getTotalAmount().toString()));
        orderRequest.purchaseUnits(Collections.singletonList(purchaseUnitRequest));
        final Payer payer = new Payer();
        payer.email(createOrderRequestDTO.getPayerEmail());
        final Name payerName = new Name();
        payer.name(payerName);
        orderRequest.payer(payer);
        final String securityToken = securityTokenGeneratorService.generateToken(createOrderRequestDTO.getPayerId());
        orderRequest.applicationContext(new ApplicationContext().returnUrl(UriComponentsBuilder.fromUri(createOrderRequestDTO.getReturnUrl())
                .queryParam(SECURITY_TOKEN_PARAM, securityToken)
                .build().toUriString()));
        final OrdersCreateRequest ordersCreateRequest = new OrdersCreateRequest().requestBody(orderRequest);
        final HttpResponse<Order> orderHttpResponse = payPalHttpClient.execute(ordersCreateRequest);
        final Order order = orderHttpResponse.result();
        final LinkDescription approveUri = extractApprovalLink(order);
        return CreatedOrderDTO.builder()
                .orderId(order.id())
                .securityToken(securityToken)
                .approvalLink(URI.create(approveUri.href()))
                .build();
    }

    /**
     *
     * @param orderId
     */
    @Override
    @SneakyThrows
    public void captureOrder(final String orderId) {
        Assert.notNull(orderId, "Order Id can not be null");
        final OrdersCaptureRequest ordersCaptureRequest = new OrdersCaptureRequest(orderId);
        payPalHttpClient.execute(ordersCaptureRequest);
    }

    /**
     * Private Methods
     */
    /**
     *
     * @param order
     * @return
     */
    private LinkDescription extractApprovalLink(Order order) {
        LinkDescription approveUri = order.links().stream()
                .filter(link -> APPROVE_LINK_REL.equals(link.rel()))
                .findFirst()
                .orElseThrow(NoSuchElementException::new);
        return approveUri;
    }
}
