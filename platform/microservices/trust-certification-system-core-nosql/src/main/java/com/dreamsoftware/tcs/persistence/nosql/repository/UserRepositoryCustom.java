package com.dreamsoftware.tcs.persistence.nosql.repository;

import java.util.Date;

/**
 *
 * @author ssanchez
 */
public interface UserRepositoryCustom {

    /**
     *
     * @param walletHash
     * @param date
     */
    void updateLastPasswordReset(final String walletHash, final Date date);

}
