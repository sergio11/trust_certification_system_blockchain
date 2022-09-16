package com.dreamsoftware.tcs.persistence.bc.repository.entity;

import java.math.BigInteger;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 *
 * @author ssanchez
 */
@Data
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
@NoArgsConstructor
public abstract class AbstractBlockchainEventEntity {

    /**
     * Address
     */
    private String address;

    /**
     * Block Hash
     */
    private String blockHash;

    /**
     * Block Number
     */
    private BigInteger blockNumber;

    /**
     * Block Number Raw
     */
    private String blockNumberRaw;

    /**
     * Data
     */
    private String data;

    /**
     * Log Index
     */
    private BigInteger logIndex;

    /**
     * Log Index Raw
     */
    private String logIndexRaw;

    /**
     * Transaction Hash
     */
    private String transactionHash;

    /**
     * Transaction Index
     */
    private BigInteger transactionIndex;

    /**
     * Transaction Index Raw
     */
    private String transactionIndexRaw;
}
