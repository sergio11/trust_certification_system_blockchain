package com.dreamsoftware.tcs.service;

import com.dreamsoftware.tcs.model.events.CertificationCourseRegisteredEvent;
import com.dreamsoftware.tcs.model.events.CourseCertificateRegistrationRequestEvent;
import com.dreamsoftware.tcs.persistence.bc.repository.entity.CertificationCourseModelEntity;
import com.dreamsoftware.tcs.persistence.exception.RepositoryException;

/**
 *
 * @author ssanchez
 */
public interface ICertificateCourseService {

    /**
     *
     * @param event
     * @return
     * @throws RepositoryException
     */
    CertificationCourseModelEntity register(final CourseCertificateRegistrationRequestEvent event) throws RepositoryException;

    /**
     *
     * @param event
     */
    void register(final CertificationCourseRegisteredEvent event);
}
