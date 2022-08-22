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
@EntityAnnotation(entityClass = UserOrderCompletedMailRequestDTO.class)
public class UserOrderCompletedMailRequestDTO extends AbstractMailRequestDTO {

    /**
     * Id
     */
    private String orderId;

    /**
     * Amount EUR
     */
    private Double amountEUR;

    /**
     * Amount USD
     */
    private Double amountUSD;

    /**
     * Amount Wei
     */
    private Long amountWEI;

    /**
     * Token Price EUR
     */
    private Double tokenPriceEUR;

    /**
     * Token Price USD
     */
    private Double tokenPriceUSD;

    /**
     * Tokens
     */
    private Long tokens;

    /**
     * User Name
     */
    private String userName;

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

    @Override
    public EmailTypeEnum getType() {
        return EmailTypeEnum.USER_ORDER_COMPLETED;
    }

}
