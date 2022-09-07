package com.dreamsoftware.tcs.services;

import com.dreamsoftware.tcs.web.dto.response.SimpleSocialUserDTO;
import java.io.IOException;

/**
 *
 * @author ssanchez
 */
public interface IGoogleService {

    /**
     *
     * @param accessToken
     * @return
     * @throws IOException
     */
    SimpleSocialUserDTO fetchUserInformation(final String accessToken) throws IOException;

    /**
     * Get Google Id by access token
     *
     * @param accessToken
     * @return
     * @throws java.io.IOException
     */
    String getGoogleIdByAccessToken(final String accessToken) throws IOException;

    /**
     * Get Username for access token
     *
     * @param accessToken
     * @return
     * @throws IOException
     */
    String getUserNameForAccessToken(final String accessToken) throws IOException;
}
