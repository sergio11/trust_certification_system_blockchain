package com.dreamsoftware.tcs.tcs;

import com.dreamsoftware.tcs.model.CertificationCourseRegisteredEvent;
import com.dreamsoftware.tcs.persistence.nosql.entity.CertificationCourseEntity;
import com.dreamsoftware.tcs.persistence.nosql.entity.CertificationCourseStateEnum;
import com.dreamsoftware.tcs.persistence.nosql.entity.UserEntity;
import com.dreamsoftware.tcs.persistence.nosql.repository.CertificationCourseRepository;
import com.dreamsoftware.tcs.persistence.nosql.repository.UserRepository;
import java.util.Date;
import java.util.function.Consumer;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * Certification Course Registered Processor
 *
 * @author ssanchez
 */
@Component("courseRegisteredProcessor")
@RequiredArgsConstructor
public class CertificationCourseRegisteredProcessor implements Consumer<CertificationCourseRegisteredEvent> {

    private final Logger logger = LoggerFactory.getLogger(CertificationCourseRegisteredProcessor.class);

    private final CertificationCourseRepository certificationCourseRepository;
    private final UserRepository userRepository;

    @Override
    public void accept(CertificationCourseRegisteredEvent event) {
        logger.debug("CertificationCourseRegisteredEvent CALLED!");
        final UserEntity caUser = userRepository.findOneByWalletHash(event.getCaWalletHash()).orElseThrow(() -> new IllegalStateException("CA Wallet Hash not found"));
        final CertificationCourseEntity certificationCourseEntity = CertificationCourseEntity
                .builder()
                .ca(caUser)
                .createdAt(new Date())
                .status(CertificationCourseStateEnum.ENABLED)
                .courseId(event.getCertificationCourse().getId())
                .build();
        certificationCourseRepository.save(certificationCourseEntity);

    }
}
