package com.dreamsoftware.tcs.mapper;

import com.dreamsoftware.tcs.persistence.nosql.entity.CertificationAuthorityEntity;
import com.dreamsoftware.tcs.persistence.nosql.entity.UserEntity;
import com.dreamsoftware.tcs.web.dto.response.CertificationAuthorityDetailDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @author ssanchez
 */
@Mapper(unmappedTargetPolicy = org.mapstruct.ReportingPolicy.IGNORE)
public abstract class CertificationAuthorityDetailMapper {

    @Autowired
    protected SimpleCertificationAuthorityMemberMapper simpleCertificationAuthorityMemberMapper;

    /**
     * Certification Authority Entity to Certification Authority Detail
     *
     * @param caEntity
     * @return
     */
    @Mappings({
            @Mapping(expression = "java(caEntity.getId().toString())", target = "id"),
            @Mapping(expression = "java(simpleCertificationAuthorityMemberMapper.entityToDTO(caMembers))", target = "members")
    })
    @Named("caEntityToCaDetail")
    public abstract CertificationAuthorityDetailDTO entityToDTO(final CertificationAuthorityEntity caEntity, final List<UserEntity> caMembers);


}
