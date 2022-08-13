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
@EntityAnnotation(entityClass = CADisabledMailRequestDTO.class)
public class CADisabledMailRequestDTO extends AbstractMailRequestDTO {

    /**
     * Id
     */
    private String id;

    /**
     * Name
     */
    private String name;

    /**
     *
     * @param id
     * @param name
     * @param email
     * @param locale
     */
    @Builder
    public CADisabledMailRequestDTO(final String id, final String name, final String email, final Locale locale) {
        super(email, locale);
        this.id = id;
        this.name = name;
    }

    @Override
    public EmailTypeEnum getType() {
        return EmailTypeEnum.CA_DISABLED;
    }
}
