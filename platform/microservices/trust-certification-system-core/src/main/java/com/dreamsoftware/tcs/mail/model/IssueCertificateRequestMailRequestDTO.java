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
@EntityAnnotation(entityClass = IssueCertificateRequestMailRequestDTO.class)
public class IssueCertificateRequestMailRequestDTO extends AbstractMailRequestDTO {

    /**
     * Id
     */
    private String certificateId;

    /**
     * Name
     */
    private String name;

    /**
     * Qualification
     */
    private Long qualification;

    /**
     *
     * @param certificateId
     * @param name
     * @param qualification
     * @param email
     * @param locale
     */
    @Builder
    public IssueCertificateRequestMailRequestDTO(final String certificateId, final String name, final Long qualification, final String email, final Locale locale) {
        super(email, locale);
        this.certificateId = certificateId;
        this.name = name;
        this.qualification = qualification;
    }

    @Override
    public EmailTypeEnum getType() {
        return EmailTypeEnum.ISSUE_CERTIFICATE_REQUEST;
    }

}
