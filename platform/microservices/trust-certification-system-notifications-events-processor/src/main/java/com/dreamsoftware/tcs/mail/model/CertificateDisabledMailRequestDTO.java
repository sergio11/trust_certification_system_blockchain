package com.dreamsoftware.tcs.mail.model;

import com.dreamsoftware.tcs.persistence.nosql.entity.EmailTypeEnum;
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
@EntityAnnotation(entityClass = CertificateDisabledMailRequestDTO.class)
public class CertificateDisabledMailRequestDTO extends AbstractMailRequestDTO {

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
    public CertificateDisabledMailRequestDTO(final String certificateId, final String name, final String email, final Locale locale) {
        super(email, locale);
        this.certificateId = certificateId;
        this.name = name;
    }

    /**
     *
     * @return
     */
    @Override
    public EmailTypeEnum getType() {
        return EmailTypeEnum.CERTIFICATE_DISABLED;
    }

}
