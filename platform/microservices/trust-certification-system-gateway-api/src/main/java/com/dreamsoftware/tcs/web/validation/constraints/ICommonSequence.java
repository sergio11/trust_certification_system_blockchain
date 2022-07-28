package com.dreamsoftware.tcs.web.validation.constraints;

import javax.validation.GroupSequence;
import javax.validation.groups.Default;

/**
 *
 * @author ssanchez
 */
@GroupSequence({Default.class, IExtended.class, IGlobal.class})
public interface ICommonSequence {

}
