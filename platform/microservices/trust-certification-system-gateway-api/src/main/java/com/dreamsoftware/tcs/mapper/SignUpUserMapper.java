package com.dreamsoftware.tcs.mapper;

import com.dreamsoftware.tcs.persistence.nosql.entity.AuthorityEntity;
import com.dreamsoftware.tcs.persistence.nosql.entity.AuthorityEnum;
import com.dreamsoftware.tcs.persistence.nosql.entity.UserEntity;
import com.dreamsoftware.tcs.persistence.nosql.entity.UserTypeEnum;
import com.dreamsoftware.tcs.persistence.nosql.repository.AuthorityRepository;
import com.dreamsoftware.tcs.web.dto.request.SignUpUserDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 *
 * @author ssanchez
 */
@Mapper(unmappedTargetPolicy = org.mapstruct.ReportingPolicy.IGNORE)
public abstract class SignUpUserMapper {

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
     * Sign Up User DTO to User Entity
     *
     * @param user
     * @return
     */
    @Mappings({
        @Mapping(expression = "java(passwordEncoder.encode(user.getPasswordClear()))", target = "password"),
        @Mapping(expression = "java(getUserAuthority(user.isRegisterAsCA()))", target = "authority"),
        @Mapping(expression = "java(getUserType(user.isRegisterAsCA()))", target = "type")
    })
    @Named("signUpUserDTOToUserEntity")
    public abstract UserEntity signUpUserDTOToUserEntity(SignUpUserDTO user);

    /**
     * Get User Authority
     *
     * @param registerAsCA
     * @return
     */
    protected AuthorityEntity getUserAuthority(final Boolean registerAsCA) {
        final AuthorityEnum authorityTypeEnum = registerAsCA ? AuthorityEnum.ROLE_CA : AuthorityEnum.ROLE_STUDENT;
        return authorityRepository.findOneByType(authorityTypeEnum).orElse(null);
    }

    /**
     *
     * @param registerAsCA
     * @return
     */
    protected UserTypeEnum getUserType(final Boolean registerAsCA) {
        return registerAsCA ? UserTypeEnum.CA : UserTypeEnum.STUDENT;
    }

}
