package com.dreamsoftware.blockchaingateway.mapper;

import com.dreamsoftware.blockchaingateway.persistence.nosql.entity.UserEntity;
import com.dreamsoftware.blockchaingateway.web.dto.request.SignUpUserDTO;
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
     * Sign Up User DTO to User Entity
     *
     * @param user
     * @return
     */
    @Mappings({
        @Mapping(expression = "java(passwordEncoder.encode(user.getPasswordClear()))", target = "password"),
        @Mapping(expression = "java(com.dreamsoftware.blockchaingateway.persistence.nosql.entity.UserTypeEnum.valueOf(user.getType()))", target = "type")
    })
    @Named("signUpUserDTOToUserEntity")
    public abstract UserEntity signUpUserDTOToUserEntity(SignUpUserDTO user);
}
