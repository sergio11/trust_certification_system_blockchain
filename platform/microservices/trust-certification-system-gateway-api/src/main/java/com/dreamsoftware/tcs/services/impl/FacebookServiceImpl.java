package com.dreamsoftware.tcs.services.impl;

import com.dreamsoftware.tcs.config.properties.FacebookProperties;
import com.dreamsoftware.tcs.mapper.UserFacebookMapper;
import com.dreamsoftware.tcs.services.IFacebookService;
import com.dreamsoftware.tcs.web.dto.response.SimpleSocialUserDTO;
import com.restfb.DefaultFacebookClient;
import com.restfb.FacebookClient;
import com.restfb.Parameter;
import com.restfb.Version;
import com.restfb.exception.FacebookOAuthException;
import com.restfb.types.User;
import io.jsonwebtoken.lang.Assert;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 *
 * @author ssanchez
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class FacebookServiceImpl implements IFacebookService {

    private final FacebookProperties facebookProperties;
    private final UserFacebookMapper userFacebookMapper;

    /**
     *
     * @param accessToken
     * @return
     * @throws FacebookOAuthException
     */
    @Override
    public String getFbIdByAccessToken(final String accessToken) throws FacebookOAuthException {
        Assert.notNull(accessToken, "accessToken can not be null");
        FacebookClient facebookClient = createFacebookClient(accessToken);
        User user = facebookClient.fetchObject("me", User.class);
        return user.getId();
    }

    /**
     *
     * @param fbId
     * @param accessToken
     * @return
     * @throws FacebookOAuthException
     */
    @Override
    public SimpleSocialUserDTO fetchUserInformation(final String fbId, final String accessToken) throws FacebookOAuthException {
        Assert.notNull(fbId, "fbId can not be null");
        Assert.notNull(accessToken, "accessToken can not be null");
        Assert.hasLength(accessToken, "Token can not be empty");
        final FacebookClient facebookClient = createFacebookClient(accessToken);
        final User user = facebookClient.fetchObject("me", User.class,
                Parameter.with("fields", "email, name, first_name, last_name, birthday, locale"));
        final SimpleSocialUserDTO userFacebookDTO = userFacebookMapper.entityToDto(user);
        userFacebookDTO.setAccessToken(accessToken);
        userFacebookDTO.setAvatarUrl(fetchUserPicture(accessToken));
        return userFacebookDTO;
    }

    /**
     *
     * @param accessToken
     * @return
     * @throws FacebookOAuthException
     */
    @Override
    public String getUserNameForAccessToken(final String accessToken) throws FacebookOAuthException {
        Assert.notNull(accessToken, "accessToken can not be null");
        Assert.hasLength(accessToken, "Token can not be empty");
        FacebookClient facebookClient = createFacebookClient(accessToken);
        User user = facebookClient.fetchObject("me", User.class, Parameter.with("fields", "name"));
        return user != null ? user.getName() : "";
    }

    /**
     * Create Facebook Client
     *
     * @param accessToken
     * @return
     */
    private FacebookClient createFacebookClient(final String accessToken) {
        return new DefaultFacebookClient(accessToken, Version.LATEST);
    }

    /**
     * Fetch User Picture
     *
     * @param accessToken
     * @return
     */
    private String fetchUserPicture(String accessToken) {
        Assert.notNull(accessToken, "Access Token can not be null");
        final FacebookClient facebookClient = createFacebookClient(accessToken);
        final String pictureRequest = String.format("picture.width(%d).height(%d)", facebookProperties.getPictureWidth(), facebookProperties.getPictureHeight());
        User user = facebookClient.fetchObject("me", User.class, Parameter.with("fields", pictureRequest));
        return user.getPicture() != null ? user.getPicture().getUrl() : null;
    }

}
