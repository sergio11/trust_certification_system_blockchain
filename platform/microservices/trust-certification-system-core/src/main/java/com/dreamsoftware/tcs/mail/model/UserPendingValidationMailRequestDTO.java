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
@EntityAnnotation(entityClass = UserPendingValidationMailRequestDTO.class)
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

    @Override
    public EmailTypeEnum getType() {
        return EmailTypeEnum.USER_PENDING_VALIDATION;
    }

}
