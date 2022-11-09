package com.dreamsoftware.tcs.service;

import com.dreamsoftware.tcs.stream.events.AbstractEvent;

public interface IDispatcherEventHandlerService {

    /**
     *
     * @param eventPayload
     */
    void onlyProcessEvent(final String eventPayload);

    /**
     *
     * @param eventPayload
     * @return
     * @throws Exception
     */
    AbstractEvent processEventAndGetResult(final String eventPayload) throws Exception;
}
