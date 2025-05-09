package com.dreamsoftware.tcs.mapper;

import com.dreamsoftware.tcs.persistence.nosql.entity.AuthorityEntity;
import com.dreamsoftware.tcs.persistence.nosql.entity.UserEntity;
import com.dreamsoftware.tcs.web.security.userdetails.impl.UserDetailsImpl;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.Named;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

/**
 * User Details Mapper
 */
@Mapper(unmappedTargetPolicy = org.mapstruct.ReportingPolicy.IGNORE)
public abstract class UserDetailsMapper {

    /**
     * User Entity to User Details mapping
     *
     * @param entity
     * @return DTO
     */
    @Mappings({
        @Mapping(expression = "java(getPermissions(entity.getAuthority()))", target = "grantedAuthorities"),
        @Mapping(expression = "java(entity.getId().toString())", target = "id")
    })
    @Named("entityToDTO")
    public abstract UserDetailsImpl<String> entityToDTO(UserEntity entity);

    /**
     * User Entity list to User Details list
     *
     * @param entity list
     * @return dto list
     */
    @IterableMapping(qualifiedByName = "entityToDTO")
    public abstract List<UserDetailsImpl<String>> entityToDTO(List<UserEntity> entity);

    /**
     * Get Permissions
     *
     * @param authorityEntity
     * @return
     */
    protected Set<GrantedAuthority> getPermissions(final AuthorityEntity authorityEntity) {
        Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
        grantedAuthorities.add(new SimpleGrantedAuthority(authorityEntity.getType().name()));
        return grantedAuthorities;
    }
}
