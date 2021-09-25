package com.dreamsoftware.blockchaingateway.persistence.nosql.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 *
 * @author ssanchez
 */
@Data
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = CertificationAuthorityEntity.COLLECTION_NAME)
public class CertificationAuthorityEntity {

    public final static String COLLECTION_NAME = "certification_authorities";

    /**
     * Id
     */
    @Id
    private ObjectId id;

    /**
     * Name
     */
    @Field("name")
    private String name;

    /**
     * Password
     */
    @Field("password")
    private String passwordHash;

    /**
     * Wallet Hash
     */
    @Field("wallet_hash")
    private String walletHash;

    /**
     * Is Activated
     */
    @Field("is_activated")
    private Boolean isActivated = false;

}
