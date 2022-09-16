package com.dreamsoftware.tcs.persistence.nosql.entity;

import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * Notification Entity
 *
 * @author ssanchez
 */
@Data
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document(collection = NotificationEntity.COLLECTION_NAME)
public class NotificationEntity {

    public final static String COLLECTION_NAME = "notifications";

    /**
     * Id
     */
    @Id
    private ObjectId id;

    /**
     * Created At
     */
    @Field("created_at")
    private Date createdAt;

    /**
     * Title
     */
    @Field("title")
    private String title;

    /**
     * Level
     */
    @Field
    private NotificationLevelEnum level;

    /**
     * Message
     */
    @Field("message")
    private String message;

    /**
     * User
     */
    @DBRef
    private UserEntity user;
}
