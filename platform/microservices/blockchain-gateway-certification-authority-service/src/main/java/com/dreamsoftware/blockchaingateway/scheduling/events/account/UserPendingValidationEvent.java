package com.dreamsoftware.blockchaingateway.scheduling.events.account;

import com.dreamsoftware.blockchaingateway.web.dto.response.SimpleUserDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.context.ApplicationEvent;

/**
 * User Pending Validation Event
 *
 * @author ssanchez
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class UserPendingValidationEvent extends ApplicationEvent {

    private final SimpleUserDTO userDTO;

    public UserPendingValidationEvent(Object source, final SimpleUserDTO userDTO) {
        super(source);
        this.userDTO = userDTO;
    }

}
