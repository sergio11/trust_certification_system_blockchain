package com.dreamsoftware.tcs.web.dto.response;

import com.dreamsoftware.tcs.web.serder.GMTDateSerializer;
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
public class SimpleUserLoginDTO {

    /**
     * User Agent
     */
    @JsonProperty("user_agent")
    private String userAgent;

    /**
     * Platform
     */
    @JsonProperty("platform")
    private String platform;

    /**
     * Create Date
     */
    @JsonProperty("create_at")
    @JsonSerialize(using = GMTDateSerializer.class)
    protected Date createAt;

}
