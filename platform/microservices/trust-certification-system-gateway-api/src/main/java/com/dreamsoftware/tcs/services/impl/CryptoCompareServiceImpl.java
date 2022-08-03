package com.dreamsoftware.tcs.services.impl;

import com.dreamsoftware.tcs.config.mail.properties.CryptoCompareProperties;
import com.dreamsoftware.tcs.persistence.exception.RepositoryException;
import com.dreamsoftware.tcs.services.ICryptoCompareService;
import com.dreamsoftware.tcs.web.dto.internal.CryptoComparePricesDTO;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.logging.Level;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

/**
 *
 * @author ssanchez
 */
@Service
@RequiredArgsConstructor
public class CryptoCompareServiceImpl implements ICryptoCompareService {

    private final Logger logger = LoggerFactory.getLogger(CryptoCompareServiceImpl.class);

    /**
     * Rest Template
     */
    @Qualifier("cryptoCompareRestTemplate")
    private final RestTemplate restTemplate;

    /**
     * Crypto Compare Properties
     */
    private final CryptoCompareProperties cryptoCompareProperties;

    /**
     * Get ETH prices
     *
     * @return
     */
    @Override
    public CryptoComparePricesDTO getEthPrices() throws RepositoryException {
        try {
            final URI pricesUrl = new URI(cryptoCompareProperties.getBaseUrl().concat("/data/price?fsym=ETH&tsyms=USD,EUR"));
            return restTemplate.getForObject(pricesUrl, CryptoComparePricesDTO.class);
        } catch (URISyntaxException | RestClientException ex) {
            throw new RepositoryException(ex.getMessage(), ex);
        }
    }

}
