package com.dreamsoftware.tcs.services.impl;

import com.dreamsoftware.tcs.mapper.UserDetailMapper;
import com.dreamsoftware.tcs.mapper.UserLoginMapper;
import com.dreamsoftware.tcs.persistence.nosql.entity.CertificationAuthorityEntity;
import com.dreamsoftware.tcs.persistence.nosql.entity.UserEntity;
import com.dreamsoftware.tcs.persistence.nosql.repository.UserLoginRepository;
import com.dreamsoftware.tcs.persistence.nosql.repository.UserRepository;
import com.dreamsoftware.tcs.services.IUserService;
import com.dreamsoftware.tcs.web.dto.response.UserDetailDTO;
import com.dreamsoftware.tcs.web.dto.response.UserLoginDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
public class UserServiceImpl implements IUserService {

    private final UserLoginMapper userLoginMapper;
    private final UserLoginRepository userLoginRepository;
    private final UserRepository userRepository;
    private final UserDetailMapper userDetailMapper;

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

    /**
     *
     * @param id
     * @return
     * @throws Throwable
     */
    @Override
    public UserDetailDTO getDetail(final String id) throws Throwable {
        Assert.notNull(id, "User Id can not be null");
        log.debug("Get user detail, id: " + id);
        final UserEntity userEntity = userRepository.findById(new ObjectId(id)).orElseThrow(() -> {
            throw new IllegalStateException("User Not Found");
        });
        return userDetailMapper.entityToDTO(userEntity);
    }

}
