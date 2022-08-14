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
@EntityAnnotation(entityClass = ResetPasswordMailRequestDTO.class)
public class ResetPasswordMailRequestDTO extends AbstractMailRequestDTO {

    /**
     * Id
     */
    private String id;

    /**
     * Name
     */
    private String name;

    /**
     * Token
     */
    private String token;

    /**
     *
     * @param id
     * @param name
     * @param token
     * @param email
     * @param locale
     */
    @Builder
    public ResetPasswordMailRequestDTO(final String id, final String name, final String token, final String email, final Locale locale) {
        super(email, locale);
        this.id = id;
        this.name = name;
    }

    @Override
    public EmailTypeEnum getType() {
        return EmailTypeEnum.RESET_PASSWORD;
    }
}
