package com.dreamsoftware.tcs.persistence.nosql.entity;

import lombok.AllArgsConstructor;
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
@Document(collection = TranslationEntity.COLLECTION_NAME)
public class TranslationEntity {

    public final static String COLLECTION_NAME = "translations";

    /**
     * Id
     */
    @Id
    private ObjectId id;

    /**
     * Name
     */
    @Field("name")
    private String name;

    /**
     * Language
     */
    @Field("language")
    private TranslationLanguageEnum language;

    /**
     * Value
     */
    @Field("value")
    private String value;
}
