package com.dreamsoftware.tcs.mapper;

import com.dreamsoftware.tcs.persistence.nosql.entity.UserEntity;
import com.dreamsoftware.tcs.web.dto.response.UserDetailDTO;
import org.mapstruct.*;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author ssanchez
 */
@Mapper(unmappedTargetPolicy = org.mapstruct.ReportingPolicy.IGNORE)
public abstract class UserDetailMapper {

    @Autowired
    protected SimpleAuthProviderMapper authProviderMapper;

    @Autowired
    protected SimpleCertificationAuthorityDetailMapper certificationAuthorityDetailMapper;

    /**
     * @param entity
     * @return
     */
    @Mappings({
            @Mapping(expression = "java(entity.getId().toString())", target = "identity"),
            @Mapping(expression = "java(entity.getType().name())", target = "type"),
            @Mapping(expression = "java(entity.getState().name())", target = "state"),
            @Mapping(expression = "java(entity.getCa() != null ? certificationAuthorityDetailMapper.entityToDTO(entity.getCa()) : null)", target = "ca"),
            @Mapping(expression = "java(entity.getAuthProvider() != null ? authProviderMapper.entityToDTO(entity.getAuthProvider()) : null)", target = "provider")
    })
    @Named("entityToDTO")
    public abstract UserDetailDTO entityToDTO(final UserEntity entity);

    /**
     * @param entity
     * @return
     */
    @IterableMapping(qualifiedByName = "entityToDTO")
    public abstract Iterable<UserDetailDTO> entityToDTO(final Iterable<UserEntity> entity);
}
