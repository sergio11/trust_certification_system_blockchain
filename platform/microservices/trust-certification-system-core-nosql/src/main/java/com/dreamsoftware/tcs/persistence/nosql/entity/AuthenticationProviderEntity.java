package com.dreamsoftware.tcs.persistence.nosql.entity;

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
 *
 * @author ssanchez
 */
@Data
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document(collection = AuthenticationProviderEntity.COLLECTION_NAME)
public class AuthenticationProviderEntity {

    public final static String COLLECTION_NAME = "authentication_providers";

    /**
     * Id
     */
    @Id
    private ObjectId id;

    /**
     * Provider Key
     */
    @Field("provider_key")
    private String key;

    /**
     * Provider Token
     */
    @Field("provider_token")
    private String token;

    /**
     * Provider Type
     */
    @Field("type")
    private AuthenticationProviderTypeEnum type;

}
