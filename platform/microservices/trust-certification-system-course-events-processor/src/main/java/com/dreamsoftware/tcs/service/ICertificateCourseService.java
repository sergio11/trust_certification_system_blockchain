package com.dreamsoftware.tcs.service;

import com.dreamsoftware.tcs.stream.events.course.CertificationCourseRegisteredEvent;
import com.dreamsoftware.tcs.stream.events.course.CourseCertificateRegistrationRequestEvent;
import com.dreamsoftware.tcs.persistence.bc.repository.entity.CertificationCourseModelEntity;
import com.dreamsoftware.tcs.persistence.exception.RepositoryException;
import com.dreamsoftware.tcs.persistence.nosql.entity.CertificationCourseEntity;

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
    CertificationCourseModelEntity onRegisterNewCertificateCourse(final CourseCertificateRegistrationRequestEvent event) throws RepositoryException;

    /**
     *
     * @param event
     * @return
     */
    CertificationCourseEntity onNewCertificateCourseRegistered(final CertificationCourseRegisteredEvent event);
}
