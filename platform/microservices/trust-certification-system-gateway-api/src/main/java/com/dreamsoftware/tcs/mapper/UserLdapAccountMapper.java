package com.dreamsoftware.tcs.mapper;

import com.dreamsoftware.tcs.persistence.ldap.entity.UserLdapEntity;
import com.dreamsoftware.tcs.persistence.nosql.entity.AuthorityEnum;
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
 * User Ldap Account
 */
@Mapper(unmappedTargetPolicy = org.mapstruct.ReportingPolicy.IGNORE)
public abstract class UserLdapAccountMapper {

    /**
     * @param entity
     * @return DTO
     */
    @Mappings({
        @Mapping(expression = "java(getPermissions())", target = "grantedAuthorities"),
        @Mapping(expression = "java(entity.getUid())", target = "id")
    })
    @Named("entityToDTO")
    public abstract UserDetailsImpl<String> entityToDTO(final UserLdapEntity entity);

    /**
     *
     * @param entity list
     * @return dto list
     */
    @IterableMapping(qualifiedByName = "entityToDTO")
    public abstract List<UserDetailsImpl<String>> entityToDTO(final List<UserLdapEntity> entity);

    /**
     * Get Permissions
     *
     * @return
     */
    protected Set<GrantedAuthority> getPermissions() {
        Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
        grantedAuthorities.add(new SimpleGrantedAuthority(AuthorityEnum.ROLE_ADMIN.name()));
        return grantedAuthorities;
    }
}
