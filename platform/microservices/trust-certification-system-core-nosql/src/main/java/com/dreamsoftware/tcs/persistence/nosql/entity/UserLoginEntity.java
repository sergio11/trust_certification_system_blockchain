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
import lombok.Builder;
import org.springframework.data.mongodb.core.mapping.DBRef;

/**
 *
 * @author ssanchez
 */
@Data
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document(collection = UserLoginEntity.COLLECTION_NAME)
public class UserLoginEntity {

    public final static String COLLECTION_NAME = "user_logins";

    /**
     * Id
     */
    @Id
    private ObjectId id;

    /**
     * User Agent
     */
    @Field("user_agent")
    private String userAgent;

    /**
     * Location Latitude
     */
    @Field("location_lat")
    private Float locationLat;

    /**
     * Location Longitude
     */
    @Field("location_long")
    private Float locationLong;

    /**
     * Address
     */
    @Field("address")
    private String address;

    /**
     * Created At
     */
    @Field("created_at")
    private Date createdAt;

    /**
     * Platform
     */
    @Field("platform")
    private UserLoginPlatformEnum platform;

    /**
     * Device Group
     */
    @Field("user")
    @DBRef
    private UserEntity user;

}
