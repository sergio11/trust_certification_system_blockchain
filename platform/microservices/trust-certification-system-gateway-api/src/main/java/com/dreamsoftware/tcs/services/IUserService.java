package com.dreamsoftware.tcs.services;

import com.dreamsoftware.tcs.web.dto.response.UserLoginDTO;
import org.bson.types.ObjectId;
import org.springframework.data.domain.Page;

/**
 *
 * @author ssanchez
 */
public interface IUserService {

    /**
     *
     * @param userId
     * @param page
     * @param size
     * @return
     */
    Page<UserLoginDTO> findLoginHistoryByUserIdPaginated(final ObjectId userId, final Integer page, final Integer size);

    /**
     *
     * @param userId
     */
    void deleteLoginHistoryForUserId(final ObjectId userId);

}
