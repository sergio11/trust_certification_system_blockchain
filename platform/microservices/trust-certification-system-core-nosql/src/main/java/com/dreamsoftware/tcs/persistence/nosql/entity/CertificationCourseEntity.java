package com.dreamsoftware.tcs.persistence.nosql.entity;

import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.TextIndexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * Certification Entity
 *
 * @author ssanchez
 */
@Data
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document(collection = CertificationCourseEntity.COLLECTION_NAME)
public class CertificationCourseEntity {

    public final static String COLLECTION_NAME = "certification_courses";

    /**
     * Id
     */
    @Id
    private ObjectId id;

    /**
     * Course Name
     */
    @Field("name")
    @TextIndexed(weight=3)
    private String name;

    /**
     * Course Description
     */
    @Field("description")
    @TextIndexed
    private String description;

    /**
     *  Duration In Hours
     */
    @Field("duration_in_hours")
    private Long durationInHours;

    /**
     * Created At
     */
    @Field("created_at")
    private Date createdAt = new Date();

    /**
     * Updated At
     */
    @Field("updated_at")
    private Date updatedAt;

    /**
     * Status
     */
    @Field("status")
    private CertificationCourseStateEnum status;

    /**
     * Cost of issuing certificate
     */
    @Field("cost_issue_certificate")
    private Long costOfIssuingCertificate;

    /**
     * Cost of renewing certificate
     */
    @Field("cost_renew_certificate")
    private Long costOfRenewingCertificate;

    /**
     * CA
     */
    @DBRef
    private CertificationAuthorityEntity ca;
}
