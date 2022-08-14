package com.dreamsoftware.tcs.scheduling.events.account;

import com.dreamsoftware.tcs.web.dto.internal.PasswordResetTokenDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.context.ApplicationEvent;

/**
 *
 * @author ssanchez
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class PasswordResetEvent extends ApplicationEvent {

    private final PasswordResetTokenDTO resetPasswordToken;

    public PasswordResetEvent(Object source, final PasswordResetTokenDTO resetPasswordToken) {
        super(source);
        this.resetPasswordToken = resetPasswordToken;
    }

}
