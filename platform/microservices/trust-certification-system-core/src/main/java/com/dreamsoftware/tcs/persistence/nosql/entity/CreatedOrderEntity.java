package com.dreamsoftware.tcs.persistence.nosql.entity;

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
 * Created Order Entity
 *
 * @author ssanchez
 */
@Data
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document(collection = CreatedOrderEntity.COLLECTION_NAME)
public class CreatedOrderEntity {

    public final static String COLLECTION_NAME = "created_order";

    /**
     * Id
     */
    @Id
    private ObjectId id;

    /**
     * External Order Id
     */
    @Field("order_id")
    private String externalOrderId;

    /**
     * Approval Link
     */
    @Field("approval_link")
    private String approvalLink;

    /**
     * Order Status
     */
    @Field("status")
    private CreatedOrderStateEnum status;

    /**
     * Created At
     */
    @Field("created_at")
    private Date createdAt;

    /**
     * Approved At
     */
    @Field("approved_at")
    private Date approvedAt;

    /**
     * Completed At
     */
    @Field("completed_at")
    private Date completedAt;

    /**
     * Tokens
     */
    @Field("tokens")
    private Long tokens;

    /**
     * Token Price EUR
     */
    @Field("token_price_EUR")
    private Double tokenPriceEUR;

    /**
     * Token Price USD
     */
    @Field("token_price_USD")
    private Double tokenPriceUSD;

    /**
     * Amount EUR
     */
    @Field("amount_EUR")
    private Double amountEUR;

    /**
     * Amount USD
     */
    @Field("amount_USD")
    private Double amountUSD;

    /**
     * Amount WEI
     */
    @Field("amount_WEI")
    private Long amountWEI;

    /**
     * User
     */
    @DBRef
    private UserEntity user;
}
