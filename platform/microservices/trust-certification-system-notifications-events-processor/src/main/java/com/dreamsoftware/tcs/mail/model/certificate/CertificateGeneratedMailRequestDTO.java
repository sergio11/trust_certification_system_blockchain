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
@EntityAnnotation(entityClass = CertificateGeneratedMailRequestDTO.class)
public class CertificateGeneratedMailRequestDTO extends AbstractMailRequestDTO {

    /**
     * Id
     */
    private final String certificateId;

    /**
     * CA Name
     */
    private final String caName;

    /**
     * Qualification
     */
    private final Long qualification;

    /**
     *
     * @param certificateId
     * @param caName
     * @param qualification
     * @param email
     * @param locale
     */
    @Builder
    public CertificateGeneratedMailRequestDTO(final String certificateId, final String caName, final Long qualification, final String email, final Locale locale) {
        super(email, locale);
        this.certificateId = certificateId;
        this.caName = caName;
        this.qualification = qualification;
    }

}
