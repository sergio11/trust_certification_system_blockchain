package com.dreamsoftware.tcs.mapper;

import com.dreamsoftware.tcs.persistence.nosql.entity.AuthenticationProviderEntity;
import com.dreamsoftware.tcs.persistence.nosql.entity.AuthenticationProviderTypeEnum;
import com.dreamsoftware.tcs.persistence.nosql.entity.AuthorityEntity;
import com.dreamsoftware.tcs.persistence.nosql.entity.AuthorityEnum;
import com.dreamsoftware.tcs.persistence.nosql.entity.UserEntity;
import com.dreamsoftware.tcs.persistence.nosql.entity.UserStateEnum;
import com.dreamsoftware.tcs.persistence.nosql.repository.AuthorityRepository;
import com.dreamsoftware.tcs.web.dto.response.SignUpSocialUserDTO;
import java.util.List;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Sign Up Social User Mapper
 *
 * @author ssanchez
 */
@Mapper(unmappedTargetPolicy = org.mapstruct.ReportingPolicy.IGNORE)
public abstract class SignUpSocialUserMapper {

    /**
     * Password Encoder
     */
    @Autowired
    protected PasswordEncoder passwordEncoder;

    /**
     * Authority Repository
     */
    @Autowired
    protected AuthorityRepository authorityRepository;

    /**
     * Sign Up User Social DTO to User Entity
     *
     * @param user
     * @return
     */
    @Mappings({
        @Mapping(target = "id", ignore = true),
        @Mapping(expression = "java(configureState())", target = "state"),
        @Mapping(expression = "java(passwordEncoder.encode(user.getPasswordClear()))", target = "password"),
        @Mapping(expression = "java(createAuthProviderEntity(user))", target = "authProvider"),
        @Mapping(expression = "java(getDefaultRole())", target = "authority")
    })
    @Named("signUpUserSocialDTOToUserEntity")
    public abstract UserEntity signUpUserSocialDTOToUserEntity(SignUpSocialUserDTO user);

    /**
     * Sign Up User Social DTO List to User Entity List
     *
     * @param userList
     * @return
     */
    @IterableMapping(qualifiedByName = "signUpUserSocialDTOToUserEntity")
    public abstract List<UserEntity> signUpUserSocialDTOListToUserEntityList(List<SignUpSocialUserDTO> userList);

    /**
     * Create Auth Provider Entity
     *
     * @param user
     * @return
     */
    protected AuthenticationProviderEntity createAuthProviderEntity(final SignUpSocialUserDTO user) {
        return AuthenticationProviderEntity.builder()
                .key(user.getId())
                .token(user.getAccessToken())
                .type(AuthenticationProviderTypeEnum.valueOf(user.getProvider()))
                .build();
    }

    /**
     * Configure State
     *
     * @return
     */
    protected UserStateEnum configureState() {
        return UserStateEnum.PENDING_VALIDATE;
    }

    /**
     * Get Default Role
     *
     * @return
     */
    protected AuthorityEntity getDefaultRole() {
        return authorityRepository.findOneByType(AuthorityEnum.ROLE_STUDENT)
                .orElse(null);
    }
}
