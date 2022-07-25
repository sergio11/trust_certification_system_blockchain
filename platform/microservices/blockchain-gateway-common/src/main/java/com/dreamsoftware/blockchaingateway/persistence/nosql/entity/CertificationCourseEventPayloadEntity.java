package com.dreamsoftware.blockchaingateway.persistence.nosql.entity;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 *
 * @author ssanchez
 */
@Getter
@EqualsAndHashCode(callSuper = false)
public class CertificationCourseEventPayloadEntity extends AbstractEventPayload {

    /**
     * Course Id
     */
    @Field("course_id")
    private String courseId;

    /**
     *
     * @param courseId
     * @param type
     */
    @Builder
    public CertificationCourseEventPayloadEntity(String courseId, String type) {
        super(type);
        this.courseId = courseId;
    }
}
