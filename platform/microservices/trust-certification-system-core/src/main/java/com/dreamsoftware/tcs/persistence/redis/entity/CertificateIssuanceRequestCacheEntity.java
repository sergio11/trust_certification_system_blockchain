package com.dreamsoftware.tcs.persistence.redis.entity;

import org.springframework.data.annotation.Id;
import com.redis.om.spring.annotations.Document;
import lombok.*;
import org.springframework.data.mongodb.core.index.Indexed;

/**
 *
 * @author ssanchez
 */
@Data
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document
public class CertificateIssuanceRequestCacheEntity {

    @Id
    private String id;

    /**
     * Status
     */
    @Indexed
    private CertificateStatusEnum status;

    /**
     * CA id
     */
    @Indexed
    private String caId;

    /**
     * Student Id
     */
    @Indexed
    private String studentId;

}
