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
@Document(collection = DeviceEntity.COLLECTION_NAME)
public class DeviceEntity {

    public final static String COLLECTION_NAME = "devices";

    /**
     * Id
     */
    @Id
    private ObjectId id;

    /**
     * Device Name
     */
    @Field("device_id")
    private String deviceId;

    /**
     * Registration Token
     */
    @Field("registration_token")
    private String registrationToken;

    /**
     * Created At
     */
    @Field("created_at")
    private Date createdAt;

    /**
     * Device Type
     */
    @Field("device_type")
    private DeviceTypeEnum deviceTypeEnum;

    /**
     * Device Group
     */
    @Field("device_group")
    @DBRef
    private DeviceGroupEntity deviceGroup;

}
