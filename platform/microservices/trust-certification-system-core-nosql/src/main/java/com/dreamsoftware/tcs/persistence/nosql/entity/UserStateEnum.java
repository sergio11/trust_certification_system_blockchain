package com.dreamsoftware.tcs.persistence.nosql.entity;

/**
 *
 * @author ssanchez
 */
public enum UserStateEnum {
    PENDING_DELETE, PENDING_ACTIVATE, PENDING_VALIDATE, VALIDATED, LOCKED, EXPIRED;
}
