package com.dreamsoftware.tcs.model.mail;

import java.util.Locale;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 *
 * @author ssanchez
 */
@Getter
@AllArgsConstructor
public abstract class AbstractMailRequestDTO {

    /**
     * Email
     */
    private String email;

    /**
     * Locale
     */
    private Locale locale;

}
