package com.dreamsoftware.blockchaingateway.web.dto.response;

import com.dreamsoftware.blockchaingateway.web.serder.GMTDateSerializer;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author ssanchez
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AccessTokenDTO {

    /**
     * Token
     */
    @JsonProperty("token")
    private String token;

    /**
     * sub
     */
    @JsonProperty("sub")
    private Long sub;

    /**
     * Audience
     */
    @JsonProperty("audience")
    private String audience;

    /**
     * CreatedAt
     */
    @JsonProperty("create_at")
    @JsonSerialize(using = GMTDateSerializer.class)
    private Date createdAt;

    /**
     * Expiration At
     */
    @JsonProperty("expiration_at")
    @JsonSerialize(using = GMTDateSerializer.class)
    private Date expirationAt;
}
