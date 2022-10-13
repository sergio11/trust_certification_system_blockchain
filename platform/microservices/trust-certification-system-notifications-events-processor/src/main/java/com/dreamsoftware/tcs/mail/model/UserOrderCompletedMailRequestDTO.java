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
@EntityAnnotation(entityClass = UserOrderCompletedMailRequestDTO.class)
public class UserOrderCompletedMailRequestDTO extends AbstractMailRequestDTO {

    /**
     * Id
     */
    private final String orderId;

    /**
     * Amount EUR
     */
    private final Double amountEUR;

    /**
     * Amount USD
     */
    private final Double amountUSD;

    /**
     * Amount Wei
     */
    private final Long amountWEI;

    /**
     * Token Price EUR
     */
    private final Double tokenPriceEUR;

    /**
     * Token Price USD
     */
    private final Double tokenPriceUSD;

    /**
     * Tokens
     */
    private final Long tokens;

    /**
     * User Name
     */
    private final String userName;

    /**
     *
     * @param orderId
     * @param amountEUR
     * @param amountUSD
     * @param amountWEI
     * @param tokenPriceEUR
     * @param tokenPriceUSD
     * @param tokens
     * @param userName
     * @param email
     * @param locale
     */
    @Builder
    public UserOrderCompletedMailRequestDTO(final String orderId, final Double amountEUR, final Double amountUSD,
            final Long amountWEI, final Double tokenPriceEUR, final Double tokenPriceUSD, final Long tokens, final String userName, final String email, final Locale locale) {
        super(email, locale);
        this.orderId = orderId;
        this.amountEUR = amountEUR;
        this.amountUSD = amountUSD;
        this.amountWEI = amountWEI;
        this.tokenPriceEUR = tokenPriceEUR;
        this.tokenPriceUSD = tokenPriceUSD;
        this.tokens = tokens;
        this.userName = userName;
    }

}
