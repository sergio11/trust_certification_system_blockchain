package com.dreamsoftware.tcs.service.impl;

import java.security.SecureRandom;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import com.dreamsoftware.tcs.service.ISecurityTokenGeneratorService;
import com.google.common.hash.Hashing;
import com.google.common.io.ByteSource;
import java.io.IOException;

/**
 *
 * @author ssanchez
 */
@Service
public class SecurityTokenGeneratorServiceImpl implements ISecurityTokenGeneratorService {

    protected static SecureRandom random = new SecureRandom();

    /**
     *
     * @param id
     * @return
     */
    @Override
    public String generateToken(final String id) {
        Assert.notNull(id, "Id can not be null");
        Assert.hasLength(id, "Id can not be empty");
        final String token = (id.toLowerCase().trim().replace(" ", "_") + ":" + Long.toString(Math.abs(random.nextLong()), 51));
        try {
            return ByteSource.wrap(token.getBytes())
                    .hash(Hashing.sha256()).toString();
        } catch (IOException ex) {
            return token;
        }
    }
}
