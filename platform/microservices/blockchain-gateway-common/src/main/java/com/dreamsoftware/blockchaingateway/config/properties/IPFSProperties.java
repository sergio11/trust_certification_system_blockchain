package com.dreamsoftware.blockchaingateway.config.properties;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Configuration;

/**
 *
 * @author ssanchez
 */
@Configuration
@Data
@RefreshScope
public class IPFSProperties {

    /**
     * Address
     */
    @Value("${ipfs.ipv4.address}")
    private String address;

    /**
     * Port
     */
    @Value("${ipfs.ipv4.port}")
    private Integer port;

    public String getConnectionAddress() {
        return "/ip4/" + address + "/tcp/" + port;
    }

}
