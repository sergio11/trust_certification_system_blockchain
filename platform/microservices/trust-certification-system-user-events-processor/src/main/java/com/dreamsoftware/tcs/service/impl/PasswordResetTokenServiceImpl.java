package com.dreamsoftware.tcs.service.impl;

import com.dreamsoftware.tcs.persistence.nosql.entity.PasswordResetTokenEntity;
import com.dreamsoftware.tcs.persistence.nosql.repository.PasswordResetTokenRepository;
import com.dreamsoftware.tcs.persistence.nosql.repository.UserRepository;
import com.dreamsoftware.tcs.service.IPasswordResetTokenService;
import com.dreamsoftware.tcs.service.ISecurityTokenGeneratorService;
import java.util.Calendar;
import java.util.Date;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

/**
 *
 * @author ssanchez
 */
@Service("passwordResetTokenService")
@RequiredArgsConstructor
public class PasswordResetTokenServiceImpl implements IPasswordResetTokenService {

    private static final int EXPIRATION = 60 * 24;

    private final PasswordResetTokenRepository passwordResetTokenRepository;
    private final ISecurityTokenGeneratorService tokenGeneratorService;
    private final UserRepository userRepository;

    /**
     *
     * @param email
     * @return
     */
    @Override
    public String createPasswordResetTokenForUserWithEmail(final String email) {
        Assert.notNull(email, "User Email can not be null");
        Assert.hasLength(email, "User Email can not be empty");
        return userRepository.findOneByEmail(email).map(userEntity -> {
            final PasswordResetTokenEntity resetTokenToSaved = passwordResetTokenRepository.save(PasswordResetTokenEntity.builder()
                    .expiryDate(calculateExpiryDate(EXPIRATION))
                    .user(userEntity)
                    .token(tokenGeneratorService.generateToken(String.valueOf(userEntity.getId())))
                    .build());
            return resetTokenToSaved.getToken();
        }).orElse(null);
    }

    /**
     *
     * @param email
     * @return
     */
    @Override
    public String getPasswordResetTokenForUserWithEmail(final String email) {
        return passwordResetTokenRepository.findByUserEmail(email).getToken();
    }

    /**
     *
     * @param id
     * @param token
     * @return
     */
    @Override
    public Boolean isValid(final String id, final String token) {
        Assert.notNull(token, "token can not be null");
        Assert.hasLength(token, "token can not be empty");
        Assert.notNull(id, "id can not be null");
        Assert.hasLength(id, "id can not be empty");
        Boolean isValid = Boolean.TRUE;
        Calendar cal = Calendar.getInstance();
        PasswordResetTokenEntity passToken = passwordResetTokenRepository.findByToken(token);
        return isValid;
    }

    /**
     *
     */
    @Override
    public void deleteExpiredTokens() {
        passwordResetTokenRepository.deleteByExpiryDateBefore(new Date());
    }

    /**
     *
     * @param expiryTimeInMinutes
     * @return
     */
    private Date calculateExpiryDate(final int expiryTimeInMinutes) {
        final Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(new Date().getTime());
        cal.add(Calendar.MINUTE, expiryTimeInMinutes);
        return new Date(cal.getTime().getTime());
    }

}
