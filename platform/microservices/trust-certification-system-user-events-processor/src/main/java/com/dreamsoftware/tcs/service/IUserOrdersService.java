package com.dreamsoftware.tcs.service;

import com.dreamsoftware.tcs.persistence.nosql.entity.CreatedOrderEntity;
import com.dreamsoftware.tcs.stream.events.user.NewTokensOrderApprovedEvent;
import com.dreamsoftware.tcs.stream.events.user.OnTokensOrderCompletedEvent;

public interface IUserOrdersService {

    /**
     *
     * @param event
     * @return
     * @throws java.lang.Exception
     */
    Long addTokensToWallet(final NewTokensOrderApprovedEvent event) throws Exception;

    /**
     *
     * @param event
     * @return
     */
    CreatedOrderEntity completeOrder(final OnTokensOrderCompletedEvent event);
}
