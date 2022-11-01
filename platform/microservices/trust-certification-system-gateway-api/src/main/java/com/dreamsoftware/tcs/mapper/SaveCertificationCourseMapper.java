package com.dreamsoftware.tcs.mapper;

import com.dreamsoftware.tcs.persistence.nosql.entity.CertificationAuthorityEntity;
import com.dreamsoftware.tcs.persistence.nosql.entity.CertificationCourseEntity;
import com.dreamsoftware.tcs.persistence.nosql.entity.CertificationCourseStateEnum;
import com.dreamsoftware.tcs.persistence.nosql.entity.UserEntity;
import com.dreamsoftware.tcs.persistence.nosql.repository.UserRepository;
import com.dreamsoftware.tcs.web.dto.request.SaveCertificationCourseDTO;
import io.jsonwebtoken.lang.Assert;
import org.mapstruct.*;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Save Certification Course Mapper
 *
 * @author ssanchez
 */
@Mapper(unmappedTargetPolicy = org.mapstruct.ReportingPolicy.IGNORE)
public abstract class SaveCertificationCourseMapper {

    @Autowired
    protected UserRepository userRepository;

    /**
     *
     * @param dto
     * @return
     */
    @Mappings({
            @Mapping(expression = "java(getCertificationAuthority(dto.getCaWalletHash()))", target = "ca"),
            @Mapping(expression = "java(getCertificationCourseInitialState())", target = "status")
    })
    @Named("dtoToEntity")
    public abstract CertificationCourseEntity dtoToEntity(SaveCertificationCourseDTO dto);

    /**
     *
     * @param dtoList
     * @return
     */
    @IterableMapping(qualifiedByName = "dtoToEntity")
    public abstract Iterable<CertificationCourseEntity> dtoToEntity(Iterable<SaveCertificationCourseDTO> dtoList);

    /**
     * Get Certification Course Initial State
     * @return
     */
    protected CertificationCourseStateEnum getCertificationCourseInitialState() {
        return CertificationCourseStateEnum.ENABLED;
    }

    /**
     * Get Certification Authority
     * @param caMemberWalletHash
     * @return
     */
    protected CertificationAuthorityEntity getCertificationAuthority(final String caMemberWalletHash) {
        Assert.notNull(caMemberWalletHash, "CA Wallet hash can not be null");
        return userRepository.findOneByWalletHash(caMemberWalletHash).map(UserEntity::getCa)
                .orElseThrow(IllegalStateException::new);
    }
}
