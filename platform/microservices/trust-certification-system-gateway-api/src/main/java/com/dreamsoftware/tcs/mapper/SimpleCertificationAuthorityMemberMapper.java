package com.dreamsoftware.tcs.mapper;

import com.dreamsoftware.tcs.persistence.nosql.entity.UserEntity;
import com.dreamsoftware.tcs.web.dto.response.SimpleCertificationAuthorityMemberDTO;
import org.mapstruct.*;

/**
 *
 * @author ssanchez
 */
@Mapper(unmappedTargetPolicy = org.mapstruct.ReportingPolicy.IGNORE)
public abstract class SimpleCertificationAuthorityMemberMapper {

    /**
     *
     * @param entity
     * @return
     */
    @Mappings({
        @Mapping(expression = "java(entity.getId().toString())", target = "identity"),
        @Mapping(expression = "java(entity.getType().name())", target = "type"),
        @Mapping(expression = "java(entity.getState().name())", target = "state")
    })
    @Named("entityToDTO")
    public abstract SimpleCertificationAuthorityMemberDTO entityToDTO(final UserEntity entity);

    /**
     *
     * @param entity
     * @return
     */
    @IterableMapping(qualifiedByName = "entityToDTO")
    public abstract Iterable<SimpleCertificationAuthorityMemberDTO> entityToDTO(final Iterable<UserEntity> entity);
}
