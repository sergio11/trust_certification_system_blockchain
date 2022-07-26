package com.dreamsoftware.blockchaingateway.service.impl;

import com.dreamsoftware.blockchaingateway.config.properties.IPFSProperties;
import com.dreamsoftware.blockchaingateway.service.IipfsGateway;
import io.ipfs.api.IPFS;
import io.ipfs.api.MerkleNode;
import io.ipfs.api.NamedStreamable;
import java.io.File;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

/**
 *
 * @author ssanchez
 */
@Service("ipfsGatewayImpl")
@RequiredArgsConstructor
@Slf4j
public class IpfsGatewayImpl implements IipfsGateway {

    private final IPFSProperties ipfsProperties;

    /**
     *
     * @param fileToSave
     * @return
     * @throws IOException
     */
    @Override
    public String save(File fileToSave) throws IOException {
        Assert.notNull(fileToSave, "File can not be null");
        IPFS ipfs = new IPFS(ipfsProperties.getConnectionAddress());
        NamedStreamable.FileWrapper file = new NamedStreamable.FileWrapper(fileToSave);
        MerkleNode addResult = ipfs.add(file).get(0);
        return addResult.hash.toHex();
    }

}
