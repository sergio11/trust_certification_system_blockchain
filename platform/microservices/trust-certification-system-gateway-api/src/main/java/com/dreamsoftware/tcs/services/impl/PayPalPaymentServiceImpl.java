package com.dreamsoftware.tcs.services.impl;

import com.dreamsoftware.tcs.persistence.nosql.entity.UserEntity;
import com.dreamsoftware.tcs.services.IPaymentService;
import com.dreamsoftware.tcs.service.ISecurityTokenGeneratorService;
import com.dreamsoftware.tcs.web.dto.internal.CreatedOrderDTO;
import com.paypal.core.PayPalHttpClient;
import com.paypal.http.HttpResponse;
import com.paypal.orders.AmountWithBreakdown;
import com.paypal.orders.ApplicationContext;
import com.paypal.orders.LinkDescription;
import com.paypal.orders.Name;
import com.paypal.orders.Order;
import com.paypal.orders.OrderRequest;
import com.paypal.orders.OrdersCaptureRequest;
import com.paypal.orders.OrdersCreateRequest;
import com.paypal.orders.Payer;
import com.paypal.orders.PurchaseUnitRequest;
import io.jsonwebtoken.lang.Assert;
import java.net.URI;
import java.util.Arrays;
import java.util.NoSuchElementException;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

/**
 * PayPal Payment Service Implementation
 *
 * @author ssanchez
 */
@Service("paymentService")
@RequiredArgsConstructor
public class PayPalPaymentServiceImpl implements IPaymentService {

    private final static String CHECKOUT_PAYMENT_INTENT = "CAPTURE";
    private final static String CURRENCY_CODE = "EU";
    private final static String APPROVE_LINK_REL = "approve";
    private final static String SECURITY_TOKEN_PARAM = "st";

    /**
     * PayPal Http Client
     */
    private final PayPalHttpClient payPalHttpClient;
    private final ISecurityTokenGeneratorService securityTokenGeneratorService;

    /**
     *
     * @param userEntity
     * @param totalAmount
     * @param returnUrl
     * @return
     */
    @Override
    @SneakyThrows
    public CreatedOrderDTO createOrder(final UserEntity userEntity, final Double totalAmount, final URI returnUrl) {
        Assert.notNull(userEntity, "User Entity can not be null");
        Assert.notNull(totalAmount, "Total Amount can not be null");
        Assert.notNull(returnUrl, "ReturnUrl can not be null");

        final OrderRequest orderRequest = new OrderRequest();
        orderRequest.checkoutPaymentIntent(CHECKOUT_PAYMENT_INTENT);
        final PurchaseUnitRequest purchaseUnitRequest = new PurchaseUnitRequest()
                .amountWithBreakdown(new AmountWithBreakdown()
                        .currencyCode(CURRENCY_CODE)
                        .value(totalAmount.toString()));
        orderRequest.purchaseUnits(Arrays.asList(purchaseUnitRequest));
        final Payer payer = new Payer();
        payer.email(userEntity.getEmail());
        final Name payerName = new Name();
        payerName.fullName(userEntity.getName());
        payer.name(payerName);
        payer.payerId(userEntity.getId().toString());
        orderRequest.payer(payer);
        final String securityToken = securityTokenGeneratorService.generateToken(userEntity.getId().toString());
        orderRequest.applicationContext(new ApplicationContext().returnUrl(UriComponentsBuilder.fromUri(returnUrl)
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
