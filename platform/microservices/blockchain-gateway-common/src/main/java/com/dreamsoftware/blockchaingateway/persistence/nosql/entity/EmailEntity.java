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
@Document(collection = EmailEntity.COLLECTION_NAME)
public class EmailEntity {

    public final static String COLLECTION_NAME = "emails";

    /**
     * Id
     */
    @Id
    private ObjectId id;

    /**
     * Error
     */
    @Field("error")
    private String error;

    /**
     * Type
     */
    @Field("type")
    private EmailTypeEnum type;

    /**
     * Last Chance
     */
    @Field("last_chance")
    private Date lastChance = new Date();

    /**
     * User
     */
    @DBRef
    private UserEntity user;
}