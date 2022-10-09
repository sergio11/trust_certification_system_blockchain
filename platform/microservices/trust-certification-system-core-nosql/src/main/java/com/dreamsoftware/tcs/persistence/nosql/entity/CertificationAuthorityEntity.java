package com.dreamsoftware.tcs.persistence.nosql.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import java.util.Date;
import lombok.Builder;

/**
 *
 * @author ssanchez
 */
@Data
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document(collection = CertificationAuthorityEntity.COLLECTION_NAME)
public class CertificationAuthorityEntity {

    public final static String COLLECTION_NAME = "certification_authorities";

    /**
     * Id
     */
    @Id
    private ObjectId id;

    /**
     * Name
     */
    @Field("name")
    @Indexed(unique = true)
    private String name;

    /**
     * Location
     */
    @Field("location")
    private String location;

    /**
     * Executive Director
     */
    @Field("executive_director")
    private String executiveDirector;

    /**
     * Support Mail
     */
    @Field("support_mail")
    private String supportMail;

    /**
     * Creation Date
     */
    @Field("created_date")
    private Date createdDate;

    /**
     * Default Cost of issuing certificate
     */
    @Field("default_cost_of_issuing_certificate")
    private Integer defaultCostOfIssuingCertificate;

    /**
     * Admin
     */
    @DBRef
    private UserEntity admin;

}
