package com.dreamsoftware.tcs.tcs;

import com.dreamsoftware.tcs.model.OnNewCertificateIssuedEvent;
import com.dreamsoftware.tcs.persistence.nosql.entity.CertificateIssuedEntity;
import com.dreamsoftware.tcs.persistence.nosql.entity.UserEntity;
import com.dreamsoftware.tcs.persistence.nosql.repository.CertificateIssuedRepository;
import com.dreamsoftware.tcs.persistence.nosql.repository.UserRepository;
import java.util.Date;
import java.util.function.Consumer;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * New Certificate Issued Processor
 *
 * @author ssanchez
 */
@Component("newCertificateIssuedProcessor")
@RequiredArgsConstructor
public class NewCertificateIssuedProcessor implements Consumer<OnNewCertificateIssuedEvent> {

    private final Logger logger = LoggerFactory.getLogger(NewCertificateIssuedProcessor.class);

    private final UserRepository userRepository;
    private final CertificateIssuedRepository certificateIssuedRepository;

    @Override
    public void accept(OnNewCertificateIssuedEvent event) {
        logger.debug("NewCertificateIssuedProcessor CALLED!");
        final UserEntity caUserEntity = userRepository.findOneByWalletHash(event.getCaWalletHash()).orElseThrow(() -> new IllegalStateException("CA Wallet Hash not found"));
        final UserEntity studentEntity = userRepository.findOneByWalletHash(event.getStudentWalletHash()).orElseThrow(() -> new IllegalStateException("Student Wallet Hash not found"));
        final CertificateIssuedEntity certificateIssued = CertificateIssuedEntity.builder()
                .ca(caUserEntity)
                .student(studentEntity)
                .certificateId(event.getCertificateId())
                .createdAt(new Date())
                .build();
        certificateIssuedRepository.save(certificateIssued);
    }
}
