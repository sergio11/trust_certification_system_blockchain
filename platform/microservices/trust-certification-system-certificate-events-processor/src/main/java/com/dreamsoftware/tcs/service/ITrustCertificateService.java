package com.dreamsoftware.tcs.service;

import com.dreamsoftware.tcs.model.events.OnNewCertificateIssuedEvent;
import com.dreamsoftware.tcs.model.events.OnNewIssueCertificateRequestEvent;
import com.dreamsoftware.tcs.persistence.bc.repository.entity.CertificateIssuedBcEntity;

/**
 *
 * @author ssanchez
 */
public interface ITrustCertificateService {

    /**
     *
     * @param event
     * @return
     * @throws java.lang.Exception
     */
    CertificateIssuedBcEntity issueCertificate(OnNewIssueCertificateRequestEvent event) throws Exception;

    /**
     *
     * @param event
     */
    void saveCertificate(OnNewCertificateIssuedEvent event);
}
