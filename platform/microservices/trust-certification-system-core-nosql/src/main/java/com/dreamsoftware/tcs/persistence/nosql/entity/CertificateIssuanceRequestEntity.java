package com.dreamsoftware.tcs.persistence.nosql.entity;

import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * Certificate issuance request Entity
 *
 * @author ssanchez
 */
@Data
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document(collection = CertificateIssuanceRequestEntity.COLLECTION_NAME)
public class CertificateIssuanceRequestEntity {

    public final static String COLLECTION_NAME = "certificate_issuance_request";

    /**
     * Id
     */
    @Id
    private ObjectId id;

    /**
     * Certificate Id
     */
    @Field("certificate_id")
    private String certificateId;

    /**
     * Certificate Status
     */
    @Field("status")
    private CertificateStatusEnum status;

    /**
     * Created At
     */
    @Field("created_at")
    private Date createdAt;

    /**
     * Updated At
     */
    @Field("updated_at")
    private Date updatedAt;

    /**
     * Qualification
     */
    @Field("qualification")
    private Long qualification;

    /**
     * Course
     */
    @DBRef
    private CertificationCourseEditionEntity course;

    /**
     * Student
     */
    @DBRef
    private UserEntity student;

    /**
     * CA
     */
    @DBRef
    private UserEntity caMember;
}
