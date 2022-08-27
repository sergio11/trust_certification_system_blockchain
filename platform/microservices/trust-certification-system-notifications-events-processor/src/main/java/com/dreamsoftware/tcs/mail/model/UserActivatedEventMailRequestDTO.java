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
@EntityAnnotation(entityClass = UserActivatedEventMailRequestDTO.class)
public class UserActivatedEventMailRequestDTO extends AbstractMailRequestDTO {

    /**
     * Id
     */
    private final String id;

    /**
     * Firname
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
    public UserActivatedEventMailRequestDTO(final String id, final String name, final String email, final Locale locale) {
        super(email, locale);
        this.id = id;
        this.name = name;
    }

    /**
     *
     * @return
     */
    @Override
    public EmailTypeEnum getType() {
        return EmailTypeEnum.USER_ACTIVATED;
    }
}
