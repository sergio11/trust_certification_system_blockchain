package com.dreamsoftware.tcs.web.validation.constraints.group;

import com.dreamsoftware.tcs.web.validation.constraints.group.IGroups.IAccountShouldActive;
import com.dreamsoftware.tcs.web.validation.constraints.group.IGroups.IAccountShouldNotLocked;
import com.dreamsoftware.tcs.web.validation.constraints.group.IGroups.IAccountShouldNotPendingDelete;
import com.dreamsoftware.tcs.web.validation.constraints.group.IGroups.IEmailShouldExist;
import com.dreamsoftware.tcs.web.validation.constraints.group.IGroups.IShouldNotBeAFacebookUser;
import com.dreamsoftware.tcs.web.validation.constraints.group.IGroups.IShouldNotBeAGoogleUser;
import com.dreamsoftware.tcs.web.validation.constraints.group.IGroups.IValidEmail;
import javax.validation.GroupSequence;

/**
 *
 * @author ssanchez
 */
@GroupSequence({
    IValidEmail.class,
    IEmailShouldExist.class,
    IShouldNotBeAFacebookUser.class,
    IShouldNotBeAGoogleUser.class,
    IAccountShouldActive.class,
    IAccountShouldNotPendingDelete.class,
    IAccountShouldNotLocked.class})
public interface IResetPasswordSequence {
}
