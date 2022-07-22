package com.dreamsoftware.blockchaingateway.persistence.nosql.entity;

import java.math.BigInteger;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * Blockchain Log Entity
 *
 * @author ssanchez
 */
@Data
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
@NoArgsConstructor
public abstract class AbstractBlockchainEventEntity {

    /**
     * ID
     */
    @Id
    private ObjectId id;

    /**
     * Address
     */
    @Field("address")
    private String address;

    /**
     * Block Hash
     */
    @Field("block_hash")
    private String blockHash;

    /**
     * Block Number
     */
    @Field("block_number")
    private BigInteger blockNumber;

    /**
     * Block Number Raw
     */
    @Field("block_number_raw")
    private String blockNumberRaw;

    /**
     * Data
     */
    @Field("data")
    private String data;

    /**
     * Log Index
     */
    @Field("log_index")
    private BigInteger logIndex;

    /**
     * Log Index Raw
     */
    @Field("log_index_raw")
    private String logIndexRaw;

    /**
     * Transaction Hash
     */
    @Field("transaction_hash")
    private String transactionHash;

    /**
     * Transaction Index
     */
    @Field("transaction_index")
    private BigInteger transactionIndex;

    /**
     * Transaction Index Raw
     */
    @Field("transaction_index_raw")
    private String transactionIndexRaw;

}
