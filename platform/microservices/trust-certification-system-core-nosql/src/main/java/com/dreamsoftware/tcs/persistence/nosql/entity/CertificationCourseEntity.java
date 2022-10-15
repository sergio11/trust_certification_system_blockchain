package com.dreamsoftware.tcs.persistence.nosql.entity;

import java.util.Date;
import java.util.List;
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
    private String name;

    /**
     * Course Description
     */
    @Field("description")
    private String description;

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
     * Status
     */
    @Field("status")
    private CertificationCourseStateEnum status;

    /**
     * Editions
     */
    @DBRef
    private List<CertificationCourseEditionEntity> editions;

    /**
     * CA
     */
    @DBRef
    private CertificationAuthorityEntity ca;
}
