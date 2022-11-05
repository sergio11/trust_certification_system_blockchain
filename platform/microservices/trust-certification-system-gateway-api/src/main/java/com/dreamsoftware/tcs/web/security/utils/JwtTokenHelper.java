package com.dreamsoftware.tcs.web.security.utils;

import com.dreamsoftware.tcs.web.dto.response.AccessTokenDTO;
import com.dreamsoftware.tcs.web.security.userdetails.ICommonUserDetailsAware;
import com.google.common.hash.Hashing;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import java.util.Collection;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mobile.device.Device;
import org.springframework.stereotype.Component;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
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
    static final String CLAIM_KEY_AUTHORITIES = "authorities";
    static final String CLAIM_KEY_CLIENT_ADDR = "clientAddr";
    static final String CLAIM_KEY_USER_AGENT = "clientUserAgent";

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
    public String getSubFromToken(final String token) {
        Assert.notNull(token, "Token can not be null");
        final Claims claims = getClaimsFromToken(token);
        return String.valueOf(claims.getSubject());
    }

    /**
     * Get Authorities from token
     *
     * @param token
     * @return
     */
    public Collection<String> getAuthoritiesFromToken(final String token) {
        Assert.notNull(token, "Token can not be null");
        final Claims claims = getClaimsFromToken(token);
        return (Collection<String>) claims.get(CLAIM_KEY_AUTHORITIES);
    }

    /**
     *
     * @param token
     * @return
     */
    public Date getCreatedAtFromToken(final String token) {
        Assert.notNull(token, "Token can not be null");
        final Claims claims = getClaimsFromToken(token);
        return (Date) claims.get(CLAIM_KEY_CREATED);
    }

    /**
     * Refresh Token
     *
     * @param token
     * @return
     */
    public AccessTokenDTO refreshToken(final String token) {
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
     * @param <T>
     * @param userDetails
     * @param device
     * @param userAgent
     * @param remoteAddr
     * @return
     */
    public <T> AccessTokenDTO generateToken(final ICommonUserDetailsAware<T> userDetails, final Device device, final String userAgent, final String remoteAddr) {

        final Date createdAt = new Date();
        final Date expirationAt = new Date(createdAt.getTime() + expiration * 1000);
        final String audience = generateAudience(device);

        Map<String, Object> claims = new HashMap<>();
        claims.put(CLAIM_KEY_SUB, userDetails.getUserId());
        claims.put(CLAIM_KEY_AUDIENCE, audience);
        claims.put(CLAIM_KEY_CREATED, createdAt);
        claims.put(CLAIM_KEY_CLIENT_ADDR, Hashing.sha512().hashBytes(remoteAddr.getBytes()).toString());
        claims.put(CLAIM_KEY_USER_AGENT, Hashing.sha512().hashBytes(userAgent.getBytes()).toString());
        claims.put(CLAIM_KEY_AUTHORITIES, userDetails.getAuthorities().stream()
                .map((auth) -> auth.getAuthority()).toArray());

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
     * @param clientAddr
     * @param clientUserAgent
     * @return
     */
    public Boolean validateToken(final String token, final String clientAddr, final String clientUserAgent) {
        return !isTokenExpired(token) && isFromSameClientAddr(token, clientAddr) && isFromSameUserAgent(token, clientUserAgent);
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
        } catch (ExpiredJwtException | MalformedJwtException | SignatureException | UnsupportedJwtException | IllegalArgumentException e) {
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
     *
     * @param clientAddr
     * @return
     */
    private Boolean isFromSameClientAddr(final String token, final String clientAddr) {
        final Claims claims = getClaimsFromToken(token);
        final String tokenClientAddr = (String) claims.get(CLAIM_KEY_CLIENT_ADDR);
        return Hashing.sha512().hashBytes(clientAddr.getBytes()).toString().equals(tokenClientAddr);
    }

    /**
     *
     * @param token
     * @param clientUserAgent
     * @return
     */
    private Boolean isFromSameUserAgent(final String token, final String clientUserAgent) {
        final Claims claims = getClaimsFromToken(token);
        final String tokenUserAgent = (String) claims.get(CLAIM_KEY_USER_AGENT);
        return Hashing.sha512().hashBytes(clientUserAgent.getBytes()).toString().equals(tokenUserAgent);
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
                .setId(UUID.randomUUID().toString())
                .setExpiration(expirationDate)
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
    }

}
