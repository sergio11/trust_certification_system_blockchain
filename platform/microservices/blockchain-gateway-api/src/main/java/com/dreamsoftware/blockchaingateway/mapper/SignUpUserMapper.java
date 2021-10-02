package com.dreamsoftware.blockchaingateway.mapper;

import com.dreamsoftware.blockchaingateway.persistence.nosql.entity.AuthorityEntity;
import com.dreamsoftware.blockchaingateway.persistence.nosql.entity.AuthorityEnum;
import com.dreamsoftware.blockchaingateway.persistence.nosql.entity.UserEntity;
import com.dreamsoftware.blockchaingateway.persistence.nosql.entity.UserTypeEnum;
import com.dreamsoftware.blockchaingateway.persistence.nosql.repository.AuthorityRepository;
import com.dreamsoftware.blockchaingateway.web.dto.request.SignUpUserDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.Assert;

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
        @Mapping(expression = "java(getUserAuthority(user.getType()))", target = "authority"),
        @Mapping(expression = "java(com.dreamsoftware.blockchaingateway.persistence.nosql.entity.UserTypeEnum.valueOf(user.getType()))", target = "type")
    })
    @Named("signUpUserDTOToUserEntity")
    public abstract UserEntity signUpUserDTOToUserEntity(SignUpUserDTO user);

    /**
     * Get User Authority
     *
     * @param userType
     * @return
     */
    protected AuthorityEntity getUserAuthority(final String userType) {
        Assert.notNull(userType, "User Type can not be null");
        final UserTypeEnum userTypeEnum = UserTypeEnum.valueOf(userType);
        final AuthorityEnum authorityTypeEnum;
        switch (userTypeEnum) {
            case CA:
                authorityTypeEnum = AuthorityEnum.ROLE_CA;
                break;
            case STUDENT:
                authorityTypeEnum = AuthorityEnum.ROLE_STUDENT;
                break;
            case ADMIN:
                authorityTypeEnum = AuthorityEnum.ROLE_ADMIN;
                break;
            default:
                authorityTypeEnum = null;
                break;
        }
        return authorityRepository.findOneByType(authorityTypeEnum).orElse(null);
    }
}
