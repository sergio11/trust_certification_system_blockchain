package com.dreamsoftware.blockchaingateway.persistence.nosql.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Builder;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 *
 * @author ssanchez
 */
@Getter
@EqualsAndHashCode(callSuper = false)
public class CertificationAuthorityEventPayloadEntity extends AbstractEventPayload {

    /**
     * CA NAME
     */
    @Field("ca_name")
    private String name;

    /**
     *
     * @param name
     * @param type
     */
    @Builder
    public CertificationAuthorityEventPayloadEntity(String name, String type) {
        super(type);
        this.name = name;
    }
}
