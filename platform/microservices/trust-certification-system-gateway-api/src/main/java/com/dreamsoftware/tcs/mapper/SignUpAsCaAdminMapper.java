package com.dreamsoftware.tcs.mapper;

import com.dreamsoftware.tcs.persistence.nosql.entity.*;
import com.dreamsoftware.tcs.persistence.nosql.repository.AuthorityRepository;
import com.dreamsoftware.tcs.service.ISecurityTokenGeneratorService;
import com.dreamsoftware.tcs.web.dto.request.SignUpAsCaAdminDTO;
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
public abstract class SignUpAsCaAdminMapper {

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
     * @param ca
     * @return
     */
    @Mappings({
            @Mapping(source = "caName", target = "name"),
            @Mapping(source = "caLocation", target = "location"),
            @Mapping(source = "caExecutiveDirector", target = "executiveDirector"),
            @Mapping(source = "caSupportMail", target = "supportMail"),
            @Mapping(expression = "java(mapToCaAdminMember(ca))", target = "admin")
    })
    @Named("dtoToEntity")
    public abstract CertificationAuthorityEntity dtoToEntity(final SignUpAsCaAdminDTO ca);


    /**
     *
     * @param ca
     * @return
     */
    protected UserEntity mapToCaAdminMember(final SignUpAsCaAdminDTO ca) {
        Assert.notNull(ca, "dto can not be null");
        final UserEntity userEntity = new UserEntity();
        userEntity.setFullName(ca.getCaAdminFullName());
        userEntity.setEmail(ca.getCaAdminEmail());
        userEntity.setPassword(passwordEncoder.encode(ca.getCaAdminPasswordClear()));
        userEntity.setLanguage(ca.getLanguage());
        userEntity.setType(UserTypeEnum.CA_ADMIN);
        userEntity.setConfirmationToken(tokenGeneratorService.generateToken(ca.getCaAdminFullName()));
        userEntity.setAuthority(getCaAdminAuthority());
        return userEntity;
    }

    /**
     * Get CA Admin Authority
     */
    protected AuthorityEntity getCaAdminAuthority() {
        return authorityRepository.findOneByType(AuthorityEnum.ROLE_CA_ADMIN).orElse(null);
    }

}
