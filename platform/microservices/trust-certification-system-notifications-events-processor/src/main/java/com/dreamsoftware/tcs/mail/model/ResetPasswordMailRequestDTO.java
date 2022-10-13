package com.dreamsoftware.tcs.mail.model;

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
    private final String id;

    /**
     * Name
     */
    private final String name;

    /**
     * Token
     */
    private final String token;

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
        this.token = token;
    }
}
