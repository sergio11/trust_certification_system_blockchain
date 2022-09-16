package com.dreamsoftware.tcs.persistence.nosql.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
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
@Document(collection = DeviceRegistrationRequestEntity.COLLECTION_NAME)
public class DeviceRegistrationRequestEntity {

    public final static String COLLECTION_NAME = "device_registration_request";

    /**
     *
     */
    @Id
    private ObjectId id;

    /**
     * Device ID
     */
    @Field("device_id")
    private String deviceId;

    /**
     * Registration Token
     */
    @Field("registration_token")
    private String registrationToken;

    /**
     * Owner
     */
    @Field("owner")
    private ObjectId owner;

    /**
     * Created At
     */
    @Field("created_at")
    private Date createdAt;

    /**
     * Failed Attempts
     */
    @Field("failed_attempts")
    private Integer failedAttempts = 0;

    /**
     * Last Time Tried
     */
    @Field("last_time_tried")
    private Date lastTimeTried;

}
