package com.dreamsoftware.tcs.web.dto.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import java.net.URI;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

/**
 *
 * @author ssanchez
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PlaceTokensOrderRequestDTO {

    /**
     * Tokens to buy
     */
    @Schema(description = "Tokens to buy", required = true)
    @JsonProperty("tokens")
    @NotNull(message = "{tokens_not_null}")
    private Long tokens;

    /**
     * Wallet Hash
     */
    @JsonIgnore
    private String walletHash;

    /**
     * Confirm Order Uri
     */
    @JsonIgnore
    private URI confirmOrderUri;

}
