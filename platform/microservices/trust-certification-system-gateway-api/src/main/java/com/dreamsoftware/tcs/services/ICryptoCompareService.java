package com.dreamsoftware.tcs.services;

import com.dreamsoftware.tcs.persistence.exception.RepositoryException;
import com.dreamsoftware.tcs.web.dto.internal.CryptoComparePricesDTO;

/**
 *
 * @author ssanchez
 */
public interface ICryptoCompareService {

    /**
     * Get Eth Prices
     *
     * @return
     * @throws RepositoryException
     */
    CryptoComparePricesDTO getEthPrices() throws RepositoryException;

}
