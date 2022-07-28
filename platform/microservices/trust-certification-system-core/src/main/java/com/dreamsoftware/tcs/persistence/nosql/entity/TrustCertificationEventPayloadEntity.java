package com.dreamsoftware.tcs.persistence.nosql.entity;

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
public class TrustCertificationEventPayloadEntity extends AbstractEventPayload {

    /**
     * Certificate Id
     */
    @Field("certificate_id")
    private String certificateId;

    /**
     *
     * @param certificateId
     * @param type
     */
    @Builder
    public TrustCertificationEventPayloadEntity(String certificateId, String type) {
        super(type);
        this.certificateId = certificateId;
    }
}
