package com.dreamsoftware.tcs.persistence.nosql.entity;

import lombok.*;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Date;

/**
 * Certification Course Edition Entity
 *
 * @author ssanchez
 */
@Data
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CertificationCourseEditionEntity {

    /**
     * Course Name
     */
    @Field("name")
    private String name;

    /**
     * Start at
     */
    @Field("started_at")
    private Date start_at;

    /**
     * End at
     */
    @Field("ended_at")
    private Date end_at;

    /**
     * Created At
     */
    @Field("created_at")
    private Date createdAt;

    /**
     * Attendee Control
     */
    @Field("attendee_control")
    private CertificationCourseAttendeeControlEntity attendeeControlEntity;
}
