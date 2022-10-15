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
     * Created At
     */
    @Field("created_at")
    private Date createdAt;

    /**
     * User Entity
     */
    @DBRef
    private UserEntity user;
}
