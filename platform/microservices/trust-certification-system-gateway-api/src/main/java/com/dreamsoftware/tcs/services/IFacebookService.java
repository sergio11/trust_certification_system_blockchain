package com.dreamsoftware.tcs.services;

import com.dreamsoftware.tcs.web.dto.response.SimpleSocialUserDTO;
import com.restfb.exception.FacebookOAuthException;

/**
 *
 * @author ssanchez
 */
public interface IFacebookService {

    /**
     * Get Facebook Id by Access Token
     *
     * @param accessToken
     * @return
     */
    String getFbIdByAccessToken(final String accessToken) throws FacebookOAuthException;

    /**
     * Fetch User Information
     *
     * @param fbId
     * @param accessToken
     * @return
     */
    SimpleSocialUserDTO fetchUserInformation(final String fbId, final String accessToken) throws FacebookOAuthException;

    /**
     * Get Username for Access Token
     *
     * @param accessToken
     * @return
     * @throws FacebookOAuthException
     */
    String getUserNameForAccessToken(final String accessToken) throws FacebookOAuthException;

}
