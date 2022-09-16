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
 *
 * @author ssanchez
 */
@Data
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document(collection = DeviceEntity.COLLECTION_NAME)
public class DeviceGroupEntity {

    public final static String COLLECTION_NAME = "devices_group";

    /**
     * Id
     */
    @Id
    private ObjectId id;

    /**
     * Notification Key Name
     */
    @Field("notification_key_name")
    private String notificationKeyName;

    /**
     * Notification Key
     */
    @Field("notification_key")
    private String notificationKey;

    /**
     * Create At
     */
    @Field("created_at")
    private Date createdAt;

    /**
     * Owner
     */
    @Field("owner")
    @DBRef
    private UserEntity owner;

}
