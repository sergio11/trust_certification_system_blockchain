package com.dreamsoftware.blockchaingateway.persistence.nosql.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * Authority Entity
 *
 * @author ssanchez
 */
@Data
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = CertificationAuthorityEventEntity.COLLECTION_NAME)
public class CertificationAuthorityEventEntity extends AbstractBlockchainEventEntity {

    public final static String COLLECTION_NAME = "ca_bc_events";

    /**
     * Event Type
     */
    @Field("type")
    private CertificationAuthorityEventTypeEnum type;

    /**
     * CA Name
     */
    @Field("ca_name")
    private String name;
}
