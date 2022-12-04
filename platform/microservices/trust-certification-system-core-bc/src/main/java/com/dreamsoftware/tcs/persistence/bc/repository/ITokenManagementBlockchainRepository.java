package com.dreamsoftware.tcs.persistence.bc.repository;

import com.dreamsoftware.tcs.persistence.exception.RepositoryException;

/**
 *
 * @author ssanchez
 */
public interface ITokenManagementBlockchainRepository {

    /**
     *
     * @param walletHash
     * @throws RepositoryException
     */
    void configureInitialTokenFundsToStudent(final String walletHash) throws RepositoryException;

    /**
     *
     * @param walletHash
     * @throws RepositoryException
     */
    void configureInitialTokenFundsToCa(final String walletHash) throws RepositoryException;

    /**
     *
     * @param walletHash
     * @throws RepositoryException
     */
    void configureInitialTokenFundsToAdmin(final String walletHash) throws RepositoryException;

    /**
     * Add Tokens
     *
     * @param walletHash
     * @param tokens
     * @throws com.dreamsoftware.tcs.persistence.exception.RepositoryException
     */
    void addTokens(final String walletHash, final Long tokens) throws RepositoryException;

    /**
     * Get Token Price in Weis
     *
     * @param tokenCount
     * @return
     * @throws com.dreamsoftware.tcs.persistence.exception.RepositoryException
     */
    Long getTokenPriceInWeis(final Long tokenCount) throws RepositoryException;

    /**
     * Get My Tokens
     *
     * @param walletHash
     * @return
     * @throws com.dreamsoftware.tcs.persistence.exception.RepositoryException
     */
    Long getMyTokens(final String walletHash) throws RepositoryException;

    /**
     * Get Tokens By Client
     *
     * @param walletHash
     * @param clientWalletHash
     * @return
     * @throws com.dreamsoftware.tcs.persistence.exception.RepositoryException
     */
    Long getTokensByClient(final String walletHash, final String clientWalletHash) throws RepositoryException;

    /**
     * Generate Tokens
     *
     * @param walletHash
     * @param tokenCount
     * @throws RepositoryException
     */
    void generateTokens(final String walletHash, final Long tokenCount) throws RepositoryException;

    /**
     *
     * @param fromWalletHash
     * @param toWalletHash
     * @param tokenCount
     * @throws RepositoryException
     */
    void transfer(final String fromWalletHash, final String toWalletHash, final Long tokenCount) throws RepositoryException;

}
