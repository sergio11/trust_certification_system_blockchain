package com.dreamsoftware.blockchaingateway.web.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import java.util.Date;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

/**
 *
 * @author ssanchez
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SimpleUserDTO {

    public static String ACTIVATE_STATE = "ACTIVATE";
    public static String PENDING_DELETE_STATE = "PENDING_DELETE";
    public static String PENDING_ACTIVATE_STATE = "PENDING_ACTIVATE";

    /**
     * User Identity
     */
    @JsonProperty("identity")
    private Long identity;

    /**
     * User name
     */
    @JsonProperty("name")
    private String name;

    /**
     * User Type
     */
    @JsonProperty("type")
    private String type;

    /**
     * User Email
     */
    @JsonProperty("email")
    private String email;

    /**
     * User State
     */
    @JsonProperty("state")
    private String state;

}
