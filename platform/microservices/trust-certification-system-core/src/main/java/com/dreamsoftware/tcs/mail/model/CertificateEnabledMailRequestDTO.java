package com.dreamsoftware.tcs.mail.model;

import com.dreamsoftware.tcs.persistence.nosql.entity.EmailTypeEnum;
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
public class CertificateEnabledMailRequestDTO extends AbstractMailRequestDTO {

    /**
     * Id
     */
    private String certificateId;

    /**
     * Name
     */
    private String name;

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

    @Override
    public EmailTypeEnum getType() {
        return EmailTypeEnum.CERTIFICATE_ENABLED;
    }

}
