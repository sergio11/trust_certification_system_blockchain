package com.dreamsoftware.blockchaingateway.persistence.nosql.entity;

import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
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
@Document(collection = UserEntity.COLLECTION_NAME)
public class UserEntity {

    public final static String COLLECTION_NAME = "users";

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
     * Email
     */
    @Field("email")
    private String email;

    /**
     * User Type
     */
    @Field("type")
    private UserTypeEnum type;

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
     * State
     */
    @Field("state")
    private UserStateEnum state;

    /**
     * Last Login Access
     */
    @Field("last_login_access")
    private Date lastLoginAccess;

    /**
     * Authority
     */
    @DBRef
    private AuthorityEntity authority;

}
