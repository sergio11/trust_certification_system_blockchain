package com.dreamsoftware.tcs.persistence.nosql.entity;

import java.math.BigInteger;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

/**
 *
 * @author ssanchez
 */
@Getter
@EqualsAndHashCode(callSuper = false)
public class EtherFaucetEventPayloadEntity extends AbstractEventPayload {

    /**
     * Amount
     */
    private BigInteger amount;

    /**
     *
     * @param amount
     * @param type
     */
    @Builder
    public EtherFaucetEventPayloadEntity(BigInteger amount, String type) {
        super(type);
        this.amount = amount;
    }

}
