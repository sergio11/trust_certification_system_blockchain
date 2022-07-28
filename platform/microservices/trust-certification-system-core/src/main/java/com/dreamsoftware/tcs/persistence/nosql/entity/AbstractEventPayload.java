package com.dreamsoftware.tcs.persistence.nosql.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 *
 * @author ssanchez
 */
@Getter
@AllArgsConstructor
public class AbstractEventPayload {

    /**
     * Event Type
     */
    @Field("type")
    private String type;

}
