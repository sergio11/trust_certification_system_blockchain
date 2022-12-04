package com.dreamsoftware.tcs.web.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author ssanchez
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CertificationCourseAttendeeControlDetailDTO {

    /**
     * Min Percentage Attendance Required
     */
    @JsonProperty("min_percentage_attendance_required")
    private Integer minPercentageAttendanceRequired;

    /**
     * Max Attendee Count
     */
    @JsonProperty("max_attendee_count")
    private Long maxAttendeeCount;

    /**
     * Max Attendance Count
     */
    @JsonProperty("max_attendance_count")
    private Long maxAttendanceCount;

    /**
     * Enroll Cost
     */
    @JsonProperty("enroll_cost")
    private Long enrollCost;
}
