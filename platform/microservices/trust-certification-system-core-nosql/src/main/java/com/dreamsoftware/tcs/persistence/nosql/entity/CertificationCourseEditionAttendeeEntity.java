package com.dreamsoftware.tcs.persistence.nosql.entity;

import lombok.*;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Field;
import java.util.Date;

/**
 * Certification Course Edition Attendee Entity
 *
 * @author ssanchez
 */
@Data
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CertificationCourseEditionAttendeeEntity {

    /**
     * Attended Count
     */
    @Field("attended_count")
    private Long attendedCount;

    /**
     * Enrolled At
     */
    @Field("enrolled_at")
    private Date enrolledAt;

    /**
     * Security Token
     */
    @Field("security_token")
    private String securityToken;

    /**
     * Student Entity
     */
    @DBRef
    private UserEntity student;
}
