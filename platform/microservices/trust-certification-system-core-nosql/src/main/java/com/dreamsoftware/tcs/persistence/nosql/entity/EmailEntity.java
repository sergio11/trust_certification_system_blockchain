package com.dreamsoftware.tcs.persistence.nosql.entity;

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
import lombok.Builder;

/**
 *
 * @author ssanchez
 */
@Data
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = EmailEntity.COLLECTION_NAME)
@Builder
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
     * Last Chance
     */
    @Field("last_chance")
    private Date lastChance;

    /**
     * Payload
     */
    @Field("payload")
    private String payload;

    /**
     * User
     */
    @DBRef
    private UserEntity user;
}
