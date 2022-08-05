package com.dreamsoftware.tcs.service;

import com.dreamsoftware.tcs.persistence.nosql.entity.CertificateIssuanceRequestEntity;
import com.dreamsoftware.tcs.persistence.nosql.entity.CertificationCourseEntity;
import com.dreamsoftware.tcs.persistence.nosql.entity.CreatedOrderEntity;
import com.dreamsoftware.tcs.persistence.nosql.entity.UserEntity;

/**
 *
 * @author ssanchez
 */
public interface INotificationService {

    /**
     *
     * @param user
     */
    void onUserAccountValidated(final UserEntity user);

    /**
     *
     * @param order
     */
    void onUserOrderCompleted(final CreatedOrderEntity order);

    /**
     *
     * @param certificate
     */
    void onUserCertificateValidated(final CertificateIssuanceRequestEntity certificate);

    /**
     *
     * @param certificationCourseEntity
     */
    void onCACertificationCourseRegistered(final CertificationCourseEntity certificationCourseEntity);

}
