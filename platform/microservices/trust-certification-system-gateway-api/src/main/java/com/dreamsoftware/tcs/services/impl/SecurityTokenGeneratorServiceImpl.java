package com.dreamsoftware.tcs.services.impl;

import java.security.SecureRandom;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import com.dreamsoftware.tcs.services.ISecurityTokenGeneratorService;

/**
 *
 * @author ssanchez
 */
@Service
public class SecurityTokenGeneratorServiceImpl implements ISecurityTokenGeneratorService {

    protected static SecureRandom random = new SecureRandom();

    @Override
    public String generateToken(String id) {
        Assert.notNull(id, "Id can not be null");
        Assert.hasLength(id, "Id can not be empty");
        long longToken = Math.abs(random.nextLong());
        return (id.toLowerCase().trim().replace(" ", "_") + ":" + Long.toString(longToken, 16));
    }
}
