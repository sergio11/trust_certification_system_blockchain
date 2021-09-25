package com.dreamsoftware.blockchaingateway.persistence.nosql.entity;

/**
 *
 * @author ssanchez
 */
public enum UserStateEnum {
    ACTIVATE, PENDING_DELETE, PENDING_ACTIVATE, LOCKED, EXPIRED;
}
