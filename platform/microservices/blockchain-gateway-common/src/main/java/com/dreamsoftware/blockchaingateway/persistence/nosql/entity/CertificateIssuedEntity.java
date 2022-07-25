package com.dreamsoftware.blockchaingateway.persistence.nosql.entity;

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
 * Certificate Issued Entity
 *
 * @author ssanchez
 */
@Data
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document(collection = CertificateIssuedEntity.COLLECTION_NAME)
public class CertificateIssuedEntity {

    public final static String COLLECTION_NAME = "certificate_issued";

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
     * Created At
     */
    @Field("created_at")
    private Date createdAt;

    /**
     * Student
     */
    @DBRef
    private UserEntity student;

    /**
     * CA
     */
    @DBRef
    private UserEntity ca;
}
