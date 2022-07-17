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
public class SFTPProperties {

    /**
     * HostName of the server
     */
    @Value("${sftp.hostname}")
    private String hostName;

    /**
     * Port of the server
     */
    @Value("${sftp.port}")
    private Integer port;

    /**
     * UserName to login
     */
    @Value("${sftp.username}")
    private String username;

    /**
     * Password to login
     */
    @Value("${sftp.password}")
    private String password;

    /**
     * Remote Folder
     */
    @Value("${sftp.remoteFolder}")
    private String remoteFolder;

    /**
     * Get Connection String
     *
     * @return
     */
    public String getConnectionString() {
        return "sftp://" + username + ":" + password + "@" + hostName + "/" + remoteFolder + "/";
    }

}
