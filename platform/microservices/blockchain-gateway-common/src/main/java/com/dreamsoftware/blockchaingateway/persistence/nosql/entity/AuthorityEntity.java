package com.dreamsoftware.blockchaingateway.persistence.nosql.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
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
@Builder
@Document(collection = AuthorityEntity.COLLECTION_NAME)
public class AuthorityEntity {

    public final static String COLLECTION_NAME = "authorities";

    /**
     * ID
     */
    @Id
    private ObjectId id;

    /**
     * Type
     */
    @Field("type")
    private AuthorityEnum type;

    /**
     * Description
     */
    @Field("description")
    private String description;
}
