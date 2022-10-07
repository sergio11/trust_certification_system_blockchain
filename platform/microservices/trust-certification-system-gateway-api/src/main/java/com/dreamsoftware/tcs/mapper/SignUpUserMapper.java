package com.dreamsoftware.tcs.mapper;

import com.dreamsoftware.tcs.persistence.nosql.entity.AuthorityEntity;
import com.dreamsoftware.tcs.persistence.nosql.entity.AuthorityEnum;
import com.dreamsoftware.tcs.persistence.nosql.entity.UserEntity;
import com.dreamsoftware.tcs.persistence.nosql.repository.AuthorityRepository;
import com.dreamsoftware.tcs.service.ISecurityTokenGeneratorService;
import com.dreamsoftware.tcs.web.dto.request.SignUpUserDTO;
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
     * Token Generator Service
     */
    @Autowired
    protected ISecurityTokenGeneratorService tokenGeneratorService;

    /**
     * Sign Up User DTO to User Entity
     *
     * @param user
     * @param authorityType
     * @return
     */
    @Mappings({
        @Mapping(expression = "java(passwordEncoder.encode(user.getPasswordClear()))", target = "password"),
        @Mapping(expression = "java(getUserAuthority(authorityType))", target = "authority"),
        @Mapping(expression = "java(tokenGeneratorService.generateToken(user.getFullName()))", target = "confirmationToken")
    })
    @Named("signUpUserDTOToUserEntity")
    public abstract UserEntity signUpUserDTOToUserEntity(final SignUpUserDTO user, final AuthorityEnum authorityType);

    /**
     * Get User Authority
     *
     * @param authorityType
     * @return
     */
    protected AuthorityEntity getUserAuthority(final AuthorityEnum authorityType) {
        Assert.notNull(authorityType, "Authority Type can not be null");
        return authorityRepository.findOneByType(authorityType).orElse(null);
    }

}
