package com.dreamsoftware.tcs.persistence.nosql.entity;

import lombok.*;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import java.util.Date;

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
    @Field("full_name")
    @Indexed(unique = true)
    private String fullName;

    /**
     * Email
     */
    @Field("email")
    @Indexed(unique = true)
    private String email;

    /**
     * Password
     */
    @Field("password")
    private String password;

    /**
     * Wallet Hash
     */
    @Field("wallet_hash")
    @Indexed(unique = true)
    private String walletHash;

    /**
     * State
     */
    @Field("state")
    private UserStateEnum state = UserStateEnum.PENDING_ACTIVATE;

    /**
     * Account State
     */
    @Field("account_state")
    private UserAccountStateEnum accountState = UserAccountStateEnum.DISABLED;

    /**
     * User Type
     */
    @Field("type")
    private UserTypeEnum type;

    /**
     * Language
     */
    @Field("language")
    private String language;

    /**
     * Confirmation Token
     */
    @Field("confirmation_token")
    private String confirmationToken;

    /**
     * Activation Date
     */
    @Field("activation_date")
    private Date activationDate;

    /**
     * Last Password Updated
     */
    @Field("last_password_updated")
    private Date lastPasswordUpdated;

    /**
     * Has Credentials Expired
     */
    @Field("has_credentials_expired")
    private Boolean hasCredentialsExpired = false;

    /**
     * Certification Authority
     */
    @DBRef
    private CertificationAuthorityEntity ca;

    /**
     * Authority
     */
    @DBRef
    private AuthorityEntity authority;

    /**
     * Avatar
     */
    @DBRef
    private ImageDataEntity avatar;

    /**
     * Auth Provider
     */
    @DBRef
    private AuthenticationProviderEntity authProvider;

}
