package com.dreamsoftware.tcs.services;

import com.dreamsoftware.tcs.web.dto.request.PlaceTokensOrderRequestDTO;
import com.dreamsoftware.tcs.web.dto.response.OrderDetailDTO;
import com.dreamsoftware.tcs.web.dto.response.TokenPricesDTO;
import org.bson.types.ObjectId;

/**
 *
 * @author ssanchez
 */
public interface ITokenManagementService {

    /**
     *
     * @param tokenCount
     * @return
     * @throws java.lang.Exception
     */
    TokenPricesDTO getTokenPrices(final Long tokenCount) throws Exception;

    /**
     *
     * @param walletHash
     * @return
     * @throws java.lang.Exception
     */
    Long getMyTokens(final String walletHash) throws Exception;

    /**
     *
     * @param orderRequest
     * @return
     * @throws java.lang.Exception
     */
    OrderDetailDTO placeTokensOrder(final PlaceTokensOrderRequestDTO orderRequest) throws Exception;

    /**
     *
     * @param externalOrderId
     * @return
     * @throws Exception
     */
    OrderDetailDTO confirmOrder(final String externalOrderId) throws Exception;

    /**
     *
     * @param walletHash
     * @param clientId
     * @return
     * @throws java.lang.Exception
     */
    Long getTokensByClient(final String walletHash, final ObjectId clientId) throws Exception;

}
