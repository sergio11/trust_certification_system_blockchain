package com.dreamsoftware.tcs.persistence.nosql.entity;

import lombok.*;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
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
@Document(collection = CertificationCourseEditionEntity.COLLECTION_NAME)
public class CertificationCourseEditionEntity {

    public final static String COLLECTION_NAME = "certification_course_editions";

    /**
     * Id
     */
    @Id
    private ObjectId id;


    /**
     * Course Name
     */
    @Field("name")
    private String name;

    /**
     * Start at
     */
    @Field("started_at")
    private Date startAt;

    /**
     * End at
     */
    @Field("ended_at")
    private Date endAt;

    /**
     * Created At
     */
    @Field("created_at")
    private Date createdAt;

    /**
     * Cost of issuing certificate
     */
    @Field("cost_issue_certificate")
    private Long costOfIssuingCertificate;

    /**
     * Duration in hours
     */
    @Field("duration_in_hours")
    private Long durationInHours;

    /**
     * Expiration in days
     */
    @Field("expiration_in_days")
    private Long expirationInDays;

    /**
     * Can be renewed
     */
    @Field("can_be_renewed")
    private Boolean canBeRenewed;

    /**
     * Cost of renewing certificate
     */
    @Field("cost_renew_certificate")
    private Long costOfRenewingCertificate;

    /**
     * Status
     */
    @Field("status")
    private CertificationCourseStateEnum status;

    /**
     * Attendee Control
     */
    @Field("attendee_control")
    private CertificationCourseAttendeeControlEntity attendeeControl;

    /**
     * Certification Course
     */
    @DBRef
    private CertificationCourseEntity course;

    /**
     * CA Member
     */
    @DBRef
    private UserEntity caMember;
}
