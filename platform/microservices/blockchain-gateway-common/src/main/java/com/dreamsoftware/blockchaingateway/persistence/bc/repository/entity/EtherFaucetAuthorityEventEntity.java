package com.dreamsoftware.blockchaingateway.persistence.bc.repository.entity;

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
public class EtherFaucetAuthorityEventEntity extends AbstractBlockchainEventEntity {

    /**
     * Event Type
     */
    private EtherFaucetEventTypeEnum type;

    /**
     * Amount
     */
    private BigInteger amount;
}
