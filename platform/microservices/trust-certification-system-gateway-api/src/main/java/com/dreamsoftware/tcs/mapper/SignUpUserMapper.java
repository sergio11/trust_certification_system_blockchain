package com.dreamsoftware.tcs.mapper;

import com.dreamsoftware.tcs.persistence.nosql.entity.AuthorityEntity;
import com.dreamsoftware.tcs.persistence.nosql.entity.AuthorityEnum;
import com.dreamsoftware.tcs.persistence.nosql.entity.UserEntity;
import com.dreamsoftware.tcs.persistence.nosql.entity.UserTypeEnum;
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
     *
     * @param user
     * @return
     */
    @Mappings({
            @Mapping(expression = "java(passwordEncoder.encode(user.getPasswordClear()))", target = "password"),
            @Mapping(expression = "java(getStudentAuthority())", target = "authority"),
            @Mapping(expression = "java(com.dreamsoftware.tcs.persistence.nosql.entity.UserTypeEnum.STUDENT)", target = "type"),
            @Mapping(expression = "java(tokenGeneratorService.generateToken(user.getFullName()))", target = "confirmationToken")
    })
    @Named("signUpUserDTOToStudentUserEntity")
    public abstract UserEntity signUpUserDTOToStudentUserEntity(final SignUpUserDTO user);

    /**
     *
     * @param user
     * @return
     */
    @Mappings({
            @Mapping(expression = "java(passwordEncoder.encode(user.getPasswordClear()))", target = "password"),
            @Mapping(expression = "java(getCaAdminAuthority())", target = "authority"),
            @Mapping(expression = "java(com.dreamsoftware.tcs.persistence.nosql.entity.UserTypeEnum.CA_ADMIN)", target = "type"),
            @Mapping(expression = "java(tokenGeneratorService.generateToken(user.getFullName()))", target = "confirmationToken")
    })
    @Named("signUpUserDTOToStudentUserEntity")
    public abstract UserEntity signUpUserDTOToCaAdminUserEntity(final SignUpUserDTO user);

    /**
     *
     * @param user
     * @return
     */
    @Mappings({
            @Mapping(expression = "java(passwordEncoder.encode(user.getPasswordClear()))", target = "password"),
            @Mapping(expression = "java(getCheckerAuthority())", target = "authority"),
            @Mapping(expression = "java(com.dreamsoftware.tcs.persistence.nosql.entity.UserTypeEnum.CHECKER)", target = "type"),
            @Mapping(expression = "java(tokenGeneratorService.generateToken(user.getFullName()))", target = "confirmationToken")
    })
    @Named("signUpUserDTOToStudentUserEntity")
    public abstract UserEntity signUpUserDTOToCheckerUserEntity(final SignUpUserDTO user);

    /**
     * Get Checker Authority
     * @return
     */
    protected AuthorityEntity getCheckerAuthority() {
        return getUserAuthority(AuthorityEnum.ROLE_CHECKER);
    }

    /**
     * Get CA Admin Authority
     * @return
     */
    protected AuthorityEntity getCaAdminAuthority() {
        return getUserAuthority(AuthorityEnum.ROLE_CA_ADMIN);
    }

    /**
     * Get Student Authority
     * @return
     */
    protected AuthorityEntity getStudentAuthority() {
        return getUserAuthority(AuthorityEnum.ROLE_STUDENT);
    }

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
