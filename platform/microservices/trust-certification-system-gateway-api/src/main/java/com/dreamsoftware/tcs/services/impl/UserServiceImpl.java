package com.dreamsoftware.tcs.services.impl;

import com.dreamsoftware.tcs.mapper.UserLoginMapper;
import com.dreamsoftware.tcs.persistence.nosql.repository.UserLoginRepository;
import com.dreamsoftware.tcs.services.IUserService;
import com.dreamsoftware.tcs.web.dto.response.UserLoginDTO;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

/**
 *
 * @author ssanchez
 */
@Service("userService")
@RequiredArgsConstructor
public class UserServiceImpl implements IUserService {

    private final UserLoginMapper userLoginMapper;
    private final UserLoginRepository userLoginRepository;

    /**
     *
     * @param userId
     * @param page
     * @param size
     * @return
     */
    @Override
    public Page<UserLoginDTO> findLoginHistoryByUserIdPaginated(final ObjectId userId, final Integer page, final Integer size) {
        Assert.notNull(userId, "User Id can not be null");
        Assert.notNull(page, "Page can not be null");
        Assert.notNull(size, "size can not be null");
        return userLoginRepository.findByUserId(PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdAt")), userId)
                .map(userLoginMapper::entityToDTO);
    }

    /**
     *
     * @param userId
     */
    @Override
    public void deleteLoginHistoryForUserId(final ObjectId userId) {
        Assert.notNull(userId, "User Id can not be null");
        userLoginRepository.deleteAllByUserId(userId);
    }

}
