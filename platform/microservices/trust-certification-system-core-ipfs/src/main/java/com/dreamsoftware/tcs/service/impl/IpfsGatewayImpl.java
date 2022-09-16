package com.dreamsoftware.tcs.service.impl;

import com.dreamsoftware.tcs.config.properties.IPFSProperties;
import com.dreamsoftware.tcs.service.IipfsGateway;
import io.ipfs.api.IPFS;
import io.ipfs.api.MerkleNode;
import io.ipfs.api.NamedStreamable;
import io.ipfs.multihash.Multihash;
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
    public String save(File fileToSave, Boolean deleteOnSave) throws IOException {
        Assert.notNull(fileToSave, "File can not be null");
        IPFS ipfs = new IPFS(ipfsProperties.getConnectionAddress());
        NamedStreamable.FileWrapper file = new NamedStreamable.FileWrapper(fileToSave);
        MerkleNode addResult = ipfs.add(file).get(0);
        if (deleteOnSave) {
            fileToSave.delete();
        }
        return addResult.hash.toHex();
    }

    /**
     *
     * @param hashHex
     * @throws Exception
     */
    @Override
    public byte[] get(final String hashHex) throws Exception {
        Assert.notNull(hashHex, "Hash can not be null");
        IPFS ipfs = new IPFS(ipfsProperties.getConnectionAddress());
        return ipfs.get(Multihash.fromHex(hashHex));
    }

}
