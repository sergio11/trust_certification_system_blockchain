package com.dreamsoftware.tcs.services;

import com.dreamsoftware.tcs.web.dto.response.NotificationDTO;
import org.bson.types.ObjectId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 *
 * @author ssanchez
 */
public interface INotificationService {

    /**
     *
     * @param userId
     * @param page
     * @param size
     * @return
     */
    Page<NotificationDTO> findPaginated(final ObjectId userId, final Integer page, final Integer size);

    /**
     *
     * @param userId
     * @param pageable
     * @return
     */
    Page<NotificationDTO> findPaginated(final ObjectId userId, final Pageable pageable);

    /**
     *
     * @param id
     * @return
     */
    NotificationDTO findById(final ObjectId id);

    /**
     *
     * @param id
     */
    void deleteById(final ObjectId id);

    /**
     *
     * @param notificationId
     * @param userId
     * @return
     */
    Boolean isTheOwner(String notificationId, ObjectId userId);

}
