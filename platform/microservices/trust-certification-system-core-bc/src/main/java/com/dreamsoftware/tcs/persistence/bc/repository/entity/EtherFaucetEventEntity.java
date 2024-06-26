package com.dreamsoftware.tcs.persistence.bc.repository.entity;

import java.math.BigInteger;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * Ether Faucet Entity
 *
 * @author ssanchez
 */
@Data
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
@NoArgsConstructor
public class EtherFaucetEventEntity extends AbstractBlockchainEventEntity {

    /**
     * Event Type
     */
    private EtherFaucetEventTypeEnum type;

    /**
     * Amount
     */
    private BigInteger amount;
}
