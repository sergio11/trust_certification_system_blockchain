package com.dreamsoftware.tcs.persistence.nosql.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import lombok.Builder;
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
@Document(collection = ImageDataEntity.COLLECTION_NAME)
public class ImageDataEntity {

    public final static String COLLECTION_NAME = "images_metadata";

    /**
     * Id
     */
    @Id
    private ObjectId id;

    /**
     * Image Name
     */
    @Field("name")
    @Indexed
    private String name;

    /**
     * Image Content Type
     */
    @Field("content_type")
    private String contentType;

    /**
     * Size
     */
    @Field("size")
    private Long size;

    /**
     * Image Extension
     */
    @Field("ext")
    private String ext;

}
