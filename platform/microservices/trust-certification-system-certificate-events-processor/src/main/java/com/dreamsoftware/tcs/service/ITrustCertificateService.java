package com.dreamsoftware.tcs.service;

import com.dreamsoftware.tcs.stream.events.certificate.OnNewCertificateIssuedEvent;
import com.dreamsoftware.tcs.stream.events.certificate.OnNewIssueCertificateRequestEvent;
import com.dreamsoftware.tcs.persistence.bc.repository.entity.CertificateIssuedEntity;
import com.dreamsoftware.tcs.persistence.nosql.entity.CertificateIssuanceRequestEntity;

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
    CertificateIssuedEntity issueCertificate(OnNewIssueCertificateRequestEvent event) throws Exception;

    /**
     *
     * @param event
     * @return
     */
    CertificateIssuanceRequestEntity saveCertificate(OnNewCertificateIssuedEvent event);
}
