package com.dreamsoftware.tcs.services.impl;

import com.dreamsoftware.tcs.mapper.UserGoogleMapper;
import com.dreamsoftware.tcs.services.IGoogleService;
import com.dreamsoftware.tcs.web.dto.response.SimpleSocialUserDTO;
import com.google.api.services.oauth2.Oauth2;
import com.google.api.services.oauth2.model.Userinfo;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

/**
 *
 * @author ssanchez
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class GoogleServiceImpl implements IGoogleService {

    private final ApplicationContext appCtx;
    private final UserGoogleMapper userGoogleMapper;

    /**
     *
     * @param accessToken
     * @return
     * @throws IOException
     */
    @Override
    public SimpleSocialUserDTO fetchUserInformation(final String accessToken) throws IOException {
        Assert.notNull(accessToken, "Access token can not be null");
        Oauth2 oauth2 = appCtx.getBean(Oauth2.class, accessToken);
        Userinfo userInfo = oauth2.userinfo().v2().me().get().execute();
        return userGoogleMapper.entityToDto(userInfo);
    }

    /**
     *
     * @param accessToken
     * @return
     * @throws IOException
     */
    @Override
    public String getGoogleIdByAccessToken(final String accessToken) throws IOException {
        Assert.notNull(accessToken, "Token can not be null");
        Assert.hasLength(accessToken, "Token can not be empty");
        Oauth2 oauth2 = appCtx.getBean(Oauth2.class, accessToken);
        Userinfo userInfo = oauth2.userinfo().v2()
                .me().get().execute();
        return userInfo.getId();
    }

    /**
     *
     * @param accessToken
     * @return
     * @throws IOException
     */
    @Override
    public String getUserNameForAccessToken(final String accessToken) throws IOException {
        Assert.notNull(accessToken, "Token can not be null");
        Assert.hasLength(accessToken, "Token can not be empty");
        Oauth2 oauth2 = appCtx.getBean(Oauth2.class, accessToken);
        Userinfo userInfo = oauth2.userinfo().v2().me().get().execute();
        return userInfo.getName();
    }

}
