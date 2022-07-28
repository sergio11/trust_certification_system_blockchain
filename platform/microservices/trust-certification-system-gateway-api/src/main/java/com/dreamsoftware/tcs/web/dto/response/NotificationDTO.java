package com.dreamsoftware.tcs.web.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
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
public class NotificationDTO {

    /**
     * Notification Id
     */
    @JsonProperty("identity")
    private String id;

    /**
     * Title
     */
    @JsonProperty("title")
    private String title;

    /**
     * Level
     */
    @JsonProperty("level")
    private String level;

    /**
     * Message
     */
    @JsonProperty("message")
    private String message;
}
