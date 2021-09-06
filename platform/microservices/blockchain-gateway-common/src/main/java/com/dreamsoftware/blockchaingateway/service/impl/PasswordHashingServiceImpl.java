package com.dreamsoftware.blockchaingateway.service.impl;

import lombok.RequiredArgsConstructor;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Service;
import com.dreamsoftware.blockchaingateway.service.IPasswordHashingService;

/**
 *
 * @author ssanchez
 */
@Service
@RequiredArgsConstructor
public class PasswordHashingServiceImpl implements IPasswordHashingService {

    @Override
    public String hash(String clearText) {
        return BCrypt.hashpw(clearText, BCrypt.gensalt());
    }

    @Override
    public Boolean check(String clearText, String hashText) {
        return BCrypt.checkpw(clearText, hashText);
    }
}
