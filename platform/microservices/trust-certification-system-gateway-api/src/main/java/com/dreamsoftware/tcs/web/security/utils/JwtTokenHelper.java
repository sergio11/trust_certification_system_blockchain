package com.dreamsoftware.tcs.web.security.utils;

import com.dreamsoftware.tcs.web.dto.response.AccessTokenDTO;
import com.dreamsoftware.tcs.web.security.userdetails.ICommonUserDetailsAware;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mobile.device.Device;
import org.springframework.stereotype.Component;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import org.bson.types.ObjectId;
import org.springframework.util.Assert;

/**
 * JWT Token Helper
 */
@Component
public class JwtTokenHelper {

    static final String CLAIM_KEY_SUB = "sub";
    static final String CLAIM_KEY_AUDIENCE = "audience";
    static final String CLAIM_KEY_CREATED = "created";
    static final String CLAIM_KEY_EXPIRED = "exp";

    static final String AUDIENCE_UNKNOWN = "unknown";
    static final String AUDIENCE_WEB = "web";
    static final String AUDIENCE_MOBILE = "mobile";
    static final String AUDIENCE_TABLET = "tablet";

    /**
     * JWT Token Secret
     */
    @Value("${jwt.token.secret}")
    private String secret;

    /**
     * JWT Token expiration
     */
    @Value("${jwt.token.expiration}")
    private Long expiration;

    /**
     * Get Sub from token
     *
     * @param token
     * @return
     */
    public String getSubFromToken(String token) {
        final Claims claims = getClaimsFromToken(token);
        return String.valueOf(claims.getSubject());
    }

    /**
     * Refresh Token
     *
     * @param token
     * @return
     */
    public AccessTokenDTO refreshToken(String token) {
        Assert.notNull(token, "Token can not be null");
        final Date createdAt = new Date();
        final Date expirationAt = new Date(createdAt.getTime() + expiration * 1000);
        final Claims claims = getClaimsFromToken(token);
        claims.put(CLAIM_KEY_CREATED, createdAt);

        final String refreshedToken = doGenerateToken(claims, expirationAt);

        return AccessTokenDTO.builder()
                .audience(claims.getAudience())
                .createdAt(createdAt)
                .expirationAt(expirationAt)
                .token(refreshedToken)
                .sub((String) claims.get(CLAIM_KEY_SUB))
                .build();
    }

    /**
     * Generate Token
     *
     * @param userDetails
     * @param device
     * @return
     */
    public AccessTokenDTO generateToken(ICommonUserDetailsAware<ObjectId> userDetails, Device device) {

        final Date createdAt = new Date();
        final Date expirationAt = new Date(createdAt.getTime() + expiration * 1000);
        final String audience = generateAudience(device);

        Map<String, Object> claims = new HashMap<>();
        claims.put(CLAIM_KEY_SUB, userDetails.getUserId());
        claims.put(CLAIM_KEY_AUDIENCE, audience);
        claims.put(CLAIM_KEY_CREATED, createdAt);

        final String token = doGenerateToken(claims, expirationAt);

        return AccessTokenDTO.builder()
                .audience(audience)
                .createdAt(createdAt)
                .expirationAt(expirationAt)
                .token(token)
                .sub(userDetails.getUserId().toString())
                .build();

    }

    /**
     * Validate Token
     *
     * @param token
     * @return
     */
    public Boolean validateToken(String token) {
        return !isTokenExpired(token);
    }

    /**
     * Get Created date from token
     *
     * @param token
     * @return
     */
    private Date getCreatedDateFromToken(String token) {
        Date created;
        try {
            final Claims claims = getClaimsFromToken(token);
            created = new Date((Long) claims.get(CLAIM_KEY_CREATED));
        } catch (Exception e) {
            created = null;
        }
        return created;
    }

    /**
     * Get Expiration Date from token
     *
     * @param token
     * @return
     */
    private Date getExpirationDateFromToken(String token) {
        Date expDate;
        try {
            final Claims claims = getClaimsFromToken(token);
            expDate = claims.getExpiration();
        } catch (Exception e) {
            expDate = new Date();
        }
        return expDate;
    }

    /**
     * Get Audience From Token
     *
     * @param token
     * @return
     */
    private String getAudienceFromToken(final String token) {
        String audience;
        try {
            final Claims claims = getClaimsFromToken(token);
            audience = (String) claims.get(CLAIM_KEY_AUDIENCE);
        } catch (Exception e) {
            audience = null;
        }
        return audience;
    }

    /**
     * Can token be refreshed
     *
     * @param token
     * @param lastPasswordReset
     * @return
     */
    private Boolean canTokenBeRefreshed(String token, Date lastPasswordReset) {
        final Date created = getCreatedDateFromToken(token);
        return !isCreatedBeforeLastPasswordReset(created, lastPasswordReset)
                && (!isTokenExpired(token) || ignoreTokenExpiration(token));
    }

    /**
     *
     * @param token
     * @return
     */
    private Claims getClaimsFromToken(String token) {
        Claims claims;
        try {
            claims = Jwts.parser()
                    .setSigningKey(secret)
                    .parseClaimsJws(token)
                    .getBody();
        } catch (Exception e) {
            claims = null;
        }
        return claims;
    }

    /**
     * is Token Expired
     *
     * @param token
     * @return
     */
    private Boolean isTokenExpired(String token) {
        return getExpirationDateFromToken(token).before(new Date());
    }

    /**
     * is Created Before Last Password Reset
     *
     * @param created
     * @param lastPasswordReset
     * @return
     */
    private Boolean isCreatedBeforeLastPasswordReset(Date created, Date lastPasswordReset) {
        return (lastPasswordReset != null && created.before(lastPasswordReset));
    }

    /**
     * Generate Audience
     *
     * @param device
     */
    private String generateAudience(Device device) {
        String audience = AUDIENCE_UNKNOWN;
        if (device.isNormal()) {
            audience = AUDIENCE_WEB;
        } else if (device.isTablet()) {
            audience = AUDIENCE_TABLET;
        } else if (device.isMobile()) {
            audience = AUDIENCE_MOBILE;
        }
        return audience;
    }

    /**
     * Ignore Token Expiration
     *
     * @param token
     */
    private Boolean ignoreTokenExpiration(String token) {
        String audience = getAudienceFromToken(token);
        return (AUDIENCE_TABLET.equals(audience) || AUDIENCE_MOBILE.equals(audience));
    }

    /**
     * Do Generate Token
     *
     * @param claims
     * @param expirationDate
     * @return
     */
    private String doGenerateToken(final Map<String, Object> claims, final Date expirationDate) {
        return Jwts.builder()
                .setClaims(claims)
                .setExpiration(expirationDate)
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
    }

}
