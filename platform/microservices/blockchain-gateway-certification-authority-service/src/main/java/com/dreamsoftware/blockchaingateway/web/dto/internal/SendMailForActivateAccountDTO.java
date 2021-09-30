package com.dreamsoftware.blockchaingateway.web.dto.internal;

import java.util.Locale;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 *
 * @author ssanchez
 */
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SendMailForActivateAccountDTO {

    /**
     * Id
     */
    private String id;

    /**
     * Email
     */
    private String email;

    /**
     * Firname
     */
    private String firstname;

    /**
     * Last Name
     */
    private String lastname;

    /**
     * Locale
     */
    private Locale locale;
    /**
     * Confirmatino Token
     */
    private String confirmationToken;
}
