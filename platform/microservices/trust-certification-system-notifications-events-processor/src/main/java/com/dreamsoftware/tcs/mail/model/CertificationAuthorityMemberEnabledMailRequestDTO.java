package com.dreamsoftware.tcs.mail.model;

import com.dreamsoftware.tcs.utils.EntityAnnotation;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.Locale;

/**
 *
 * @author ssanchez
 */
@Getter
@EqualsAndHashCode(callSuper = false)
@EntityAnnotation(entityClass = CertificationAuthorityMemberEnabledMailRequestDTO.class)
public class CertificationAuthorityMemberEnabledMailRequestDTO extends AbstractMailRequestDTO {

    /**
     * Id
     */
    private final String id;

    /**
     * name
     */
    private final String name;

    /**
     *
     * @param id
     * @param name
     * @param email
     * @param locale
     */
    @Builder
    public CertificationAuthorityMemberEnabledMailRequestDTO(final String id, final String name, final String email, final Locale locale) {
        super(email, locale);
        this.id = id;
        this.name = name;
    }
}
