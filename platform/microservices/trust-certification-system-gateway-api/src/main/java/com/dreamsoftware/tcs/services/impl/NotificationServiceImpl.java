package com.dreamsoftware.tcs.services.impl;

import com.dreamsoftware.tcs.mapper.NotificationMapper;
import com.dreamsoftware.tcs.persistence.nosql.repository.NotificationRepository;
import com.dreamsoftware.tcs.services.INotificationService;
import com.dreamsoftware.tcs.web.dto.response.NotificationDTO;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

/**
 * Notification Service Impl
 *
 * @author ssanchez
 */
@Service("notificationService")
@RequiredArgsConstructor
public class NotificationServiceImpl implements INotificationService {

    private final Logger logger = LoggerFactory.getLogger(NotificationServiceImpl.class);

    /**
     * Notification Repository
     */
    private final NotificationRepository notificationRepository;

    /**
     * Notification Mapper
     */
    private final NotificationMapper notificationMapper;

    /**
     *
     * @param userId
     * @param page
     * @param size
     * @return
     */
    @Override
    public Page<NotificationDTO> findPaginated(ObjectId userId, Integer page, Integer size) {
        Assert.notNull(userId, "User Id can not be null");
        Assert.notNull(page, "Page can not be null");
        Assert.notNull(size, "Size can not be null");
        return findPaginated(userId, PageRequest.of(page, size));
    }

    /**
     *
     * @param userId
     * @param pageable
     * @return
     */
    @Override
    public Page<NotificationDTO> findPaginated(ObjectId userId, Pageable pageable) {
        Assert.notNull(userId, "User Id can not be null");
        Assert.notNull(pageable, "Pageable can not be null");
        return notificationRepository.findByUserIdOrderByCreateAtDesc(userId, pageable)
                .map(notificationMapper::entityToDTO);
    }

    /**
     *
     * @param id
     * @return
     */
    @Override
    public NotificationDTO findById(ObjectId id) {
        Assert.notNull(id, "Id can not be null");
        return notificationRepository.findById(id)
                .map(notificationMapper::entityToDTO)
                .orElseThrow(() -> new IllegalStateException("Notification not found"));
    }

    /**
     *
     * @param id
     */
    @Override
    public void deleteById(ObjectId id) {
        Assert.notNull(id, "Id can not be null");
        notificationRepository.deleteById(id);
    }

    /**
     *
     * @param notificationId
     * @param userId
     * @return
     */
    @Override
    public Boolean isTheOwner(String notificationId, ObjectId userId) {
        Assert.notNull(notificationId, "Id can not be null");
        Assert.notNull(userId, "Id can not be null");
        return notificationRepository.countByIdAndUserId(new ObjectId(notificationId), userId) > 0;
    }
}
