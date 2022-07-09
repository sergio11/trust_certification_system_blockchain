package com.dreamsoftware.blockchaingateway.service.impl;

import com.dreamsoftware.blockchaingateway.config.properties.SFTPProperties;
import com.dreamsoftware.blockchaingateway.service.ISftpGateway;
import java.io.File;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.schmizz.sshj.SSHClient;
import net.schmizz.sshj.sftp.RemoteResourceInfo;
import net.schmizz.sshj.sftp.SFTPClient;
import net.schmizz.sshj.transport.verification.PromiscuousVerifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

/**
 *
 * @author ssanchez
 */
@Service("SftpGatewayImpl")
@RequiredArgsConstructor
@Slf4j
public class SftpGatewayImpl implements ISftpGateway {

    private static final Logger logger = LoggerFactory.getLogger(SftpGatewayImpl.class);

    private final SFTPProperties sftpProperties;

    /**
     * Send File to SFTP
     *
     * @param fileToSend
     */
    @Override
    public void sendFile(final File fileToSend) {
        Assert.notNull(fileToSend, "Upload File can not be null");

        try (SSHClient sshClient = setupSshj();
                SFTPClient sftpClient = sshClient.newSFTPClient()) {

            logger.debug("File To Send Path -> " + fileToSend.getAbsolutePath());
            logger.debug("File To Send -> " + fileToSend.getName());

            // Put File into FTP directory
            sftpClient.put(fileToSend.getAbsolutePath(), sftpProperties.getRemoteFolder() + fileToSend.getName());

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     *
     * @param filePath
     */
    @Override
    public void syncRemoteFolderTo(final String filePath) {
        Assert.notNull(filePath, "File Path can not be null");

        try (SSHClient sshClient = setupSshj();
                SFTPClient sftpClient = sshClient.newSFTPClient()) {

            final File fileDestination = new File(filePath);
            if (!fileDestination.exists()) {
                fileDestination.mkdirs();
            }

            for (RemoteResourceInfo remoteResource : sftpClient.ls(sftpProperties.getRemoteFolder())) {
                logger.debug("Remote Resource Info -> " + remoteResource.getName());
                sftpClient.get(remoteResource.getPath(), fileDestination.getAbsolutePath());
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Setup SSHJ
     *
     * @return
     * @throws IOException
     */
    private SSHClient setupSshj() throws IOException {
        SSHClient client = new SSHClient();
        client.addHostKeyVerifier(new PromiscuousVerifier());
        client.connect(sftpProperties.getHostName(), sftpProperties.getPort());
        client.authPassword(sftpProperties.getUsername(), sftpProperties.getPassword());
        return client;
    }
}
