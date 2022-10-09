package com.dreamsoftware.tcs.stream.events.user;

import com.dreamsoftware.tcs.persistence.nosql.entity.UserTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author ssanchez
 *
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OnNewUserRegistrationEvent {

    /**
     * User Name
     */
    private String userId;

    /**
     * Wallet Hash
     */
    private String walletHash;

    /**
     * User Type
     */
    private UserTypeEnum userType;
}
