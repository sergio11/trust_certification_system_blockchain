package com.dreamsoftware.tcs.mail.model.certificate;

import com.dreamsoftware.tcs.mail.model.AbstractMailRequestDTO;
import com.dreamsoftware.tcs.utils.EntityAnnotation;
import java.util.Locale;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

/**
 *
 * @author ssanchez
 */
@Getter
@EqualsAndHashCode(callSuper = false)
@EntityAnnotation(entityClass = CertificateRequestAcceptedMailRequestDTO.class)
public class CertificateRequestAcceptedMailRequestDTO extends AbstractMailRequestDTO {

    /**
     * Id
     */
    private final String certificateId;

    /**
     * Name
     */
    private final String name;

    /**
     * Qualification
     */
    private final Long qualification;

    /**
     *
     * @param certificateId
     * @param name
     * @param qualification
     * @param email
     * @param locale
     */
    @Builder
    public CertificateRequestAcceptedMailRequestDTO(final String certificateId, final String name, final Long qualification, final String email, final Locale locale) {
        super(email, locale);
        this.certificateId = certificateId;
        this.name = name;
        this.qualification = qualification;
    }

}
