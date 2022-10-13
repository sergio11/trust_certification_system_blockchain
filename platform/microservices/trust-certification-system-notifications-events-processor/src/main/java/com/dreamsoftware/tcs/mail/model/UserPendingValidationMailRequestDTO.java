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
@EntityAnnotation(entityClass = UserPendingValidationMailRequestDTO.class)
public class UserPendingValidationMailRequestDTO extends AbstractMailRequestDTO {

    /**
     * Id
     */
    private final String id;

    /**
     * Name
     */
    private final String name;

    /**
     * Confirmation Token
     */
    private final String confirmationToken;

    /**
     *
     * @param id
     * @param name
     * @param confirmationToken
     * @param email
     * @param locale
     */
    @Builder
    public UserPendingValidationMailRequestDTO(final String id, final String name, final String confirmationToken, final String email, final Locale locale) {
        super(email, locale);
        this.id = id;
        this.name = name;
        this.confirmationToken = confirmationToken;
    }

}
