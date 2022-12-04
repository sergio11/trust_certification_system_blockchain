package com.dreamsoftware.tcs.web.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
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
public class SaveCertificationCourseAttendeeControlDTO {

    /**
     * Min Percentage Attendance Required
     */
    @Schema(description = "Min Percentage Attendance Required", required = true)
    @JsonProperty("minPercentageAttendanceRequired")
    private Integer minPercentageAttendanceRequired;

    /**
     * Max Attendee Count
     */
    @Schema(description = "Max Attendee Count", required = true)
    @JsonProperty("maxAttendeeCount")
    private Long maxAttendeeCount;

    /**
     * Max Attendance Count
     */
    @Schema(description = "Max Attendance Count", required = true)
    @JsonProperty("maxAttendanceCount")
    private Long maxAttendanceCount;

    /**
     * Course registration cost in TCS tokens
     */
    @Schema(description = "Course registration cost in TCS tokens")
    @JsonProperty("enrollCost")
    private Long enrollCost;
}
