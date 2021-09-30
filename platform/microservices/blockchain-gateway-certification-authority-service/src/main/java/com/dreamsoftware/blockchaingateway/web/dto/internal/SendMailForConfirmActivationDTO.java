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
public class SendMailForConfirmActivationDTO {

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
    private String name;

    /**
     * Locale
     */
    private Locale locale;
}
