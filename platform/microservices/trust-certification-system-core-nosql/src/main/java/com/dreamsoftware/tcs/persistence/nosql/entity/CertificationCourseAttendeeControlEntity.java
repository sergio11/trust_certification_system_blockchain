package com.dreamsoftware.tcs.persistence.nosql.entity;

import lombok.*;
import org.springframework.data.mongodb.core.mapping.Field;
import java.util.List;

/**
 * Certification Course Attendee Control Entity
 *
 * @author ssanchez
 */
@Data
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CertificationCourseAttendeeControlEntity {

    /**
     * Min Percentage Attendance Required
     */
    @Field("min_percentage_attendance_required")
    private int minPercentageAttendanceRequired;

    /**
     * Max Attendee Count
     */
    @Field("max_attendee_count")
    private long maxAttendeeCount;

    /**
     * Max Attendance Count
     */
    @Field("max_attendance_count")
    private long maxAttendanceCount;

    /**
     * Enroll Cost
     */
    @Field("enroll_cost")
    private long enrollCost;

    /**
     * Attended Users
     */
    @Field("attended_users")
    private List<CertificationCourseEditionAttendeeEntity> attendedUsers;
}
