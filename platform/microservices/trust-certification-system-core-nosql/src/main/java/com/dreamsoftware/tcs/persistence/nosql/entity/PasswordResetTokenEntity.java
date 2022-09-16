package com.dreamsoftware.tcs.persistence.nosql.entity;

import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * Password Reset Token Entity
 *
 * @author ssanchez
 */
@Data
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document(collection = PasswordResetTokenEntity.COLLECTION_NAME)
public class PasswordResetTokenEntity {

    public final static String COLLECTION_NAME = "reset_password_tokens";

    /**
     * ID
     */
    @Id
    private ObjectId id;

    /**
     * Token
     */
    @Field("token")
    private String token;

    /**
     * Expiry Date
     */
    @Field("expiry_date")
    private Date expiryDate;

    /**
     * User
     */
    @DBRef
    private UserEntity user;

}
