package com.dreamsoftware.tcs.mail.model.ca;

import com.dreamsoftware.tcs.mail.model.AbstractMailRequestDTO;
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
@EntityAnnotation(entityClass = CertificationAuthorityRemovedMailRequestDTO.class)
public class CertificationAuthorityRemovedMailRequestDTO extends AbstractMailRequestDTO {

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
    public CertificationAuthorityRemovedMailRequestDTO(final String id, final String name, final String email, final Locale locale) {
        super(email, locale);
        this.id = id;
        this.name = name;
    }
}
