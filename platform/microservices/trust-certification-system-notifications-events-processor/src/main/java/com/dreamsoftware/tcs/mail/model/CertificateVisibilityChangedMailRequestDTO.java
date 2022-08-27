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
@EntityAnnotation(entityClass = CertificateVisibilityChangedMailRequestDTO.class)
public class CertificateVisibilityChangedMailRequestDTO extends AbstractMailRequestDTO {

    /**
     * Id
     */
    private final String certificateId;

    /**
     * Name
     */
    private final String name;

    /**
     * Is Visible
     */
    private final Boolean isVisible;

    /**
     *
     * @param certificateId
     * @param name
     * @param isVisible
     * @param email
     * @param locale
     */
    @Builder
    public CertificateVisibilityChangedMailRequestDTO(final String certificateId, final String name, final Boolean isVisible, final String email, final Locale locale) {
        super(email, locale);
        this.certificateId = certificateId;
        this.name = name;
        this.isVisible = isVisible;
    }

    /**
     *
     * @return
     */
    @Override
    public EmailTypeEnum getType() {
        return EmailTypeEnum.CERTIFICATE_VISIBILITY_CHANGED;
    }

}
