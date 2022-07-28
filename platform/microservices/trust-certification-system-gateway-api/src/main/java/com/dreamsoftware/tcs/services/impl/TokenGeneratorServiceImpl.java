package com.dreamsoftware.tcs.services.impl;

import com.dreamsoftware.tcs.services.ITokenGeneratorService;
import java.security.SecureRandom;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

/**
 *
 * @author ssanchez
 */
@Service
public class TokenGeneratorServiceImpl implements ITokenGeneratorService {

    protected static SecureRandom random = new SecureRandom();

    @Override
    public String generateToken(String id) {
        Assert.notNull(id, "Id can not be null");
        Assert.hasLength(id, "Id can not be empty");
        long longToken = Math.abs(random.nextLong());
        return (id.toLowerCase().trim().replace(" ", "_") + ":" + Long.toString(longToken, 16));
    }
}
