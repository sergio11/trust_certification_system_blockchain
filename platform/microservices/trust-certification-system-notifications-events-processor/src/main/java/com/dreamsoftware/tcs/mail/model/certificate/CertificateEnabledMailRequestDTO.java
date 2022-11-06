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
@EntityAnnotation(entityClass = CertificateEnabledMailRequestDTO.class)
public class CertificateEnabledMailRequestDTO extends AbstractMailRequestDTO {

    /**
     * Id
     */
    private final String certificateId;

    /**
     * Name
     */
    private final String name;

    /**
     *
     * @param certificateId
     * @param name
     * @param email
     * @param locale
     */
    @Builder
    public CertificateEnabledMailRequestDTO(final String certificateId, final String name, final String email, final Locale locale) {
        super(email, locale);
        this.certificateId = certificateId;
        this.name = name;
    }

}
