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
public class CAEnabledMailRequestDTO extends AbstractMailRequestDTO {

    /**
     * Id
     */
    private String id;

    /**
     * name
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
    public CAEnabledMailRequestDTO(final String id, final String name, final String email, final Locale locale) {
        super(email, locale);
        this.id = id;
        this.name = name;
    }
}
