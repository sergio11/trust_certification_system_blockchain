package com.dreamsoftware.tcs.model.mail;

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
public class UserPendingValidationMailRequestDTO extends AbstractMailRequestDTO {

    /**
     * Id
     */
    private String id;

    /**
     * Name
     */
    private String name;

    /**
     * Confirmation Token
     */
    private String confirmationToken;

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
