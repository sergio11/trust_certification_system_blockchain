package com.dreamsoftware.tcs.mapper;

import com.dreamsoftware.tcs.i18n.service.I18NService;
import com.dreamsoftware.tcs.web.dto.response.SimpleSocialUserDTO;
import com.google.api.services.oauth2.model.Userinfo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author ssanchez
 */
@Mapper(unmappedTargetPolicy = org.mapstruct.ReportingPolicy.IGNORE)
public abstract class UserGoogleMapper {

    @Autowired
    protected I18NService i18nService;

    /**
     *
     * @param entity
     * @return
     */
    @Mappings({
        @Mapping(source = "entity.givenName", target = "name"),
        @Mapping(source = "entity.familyName", target = "surname"),
        @Mapping(source = "entity.email", target = "email"),
        @Mapping(source = "entity.id", target = "id"),
        @Mapping(source = "entity.picture", target = "avatarUrl"),
        @Mapping(constant = "GOOGLE", target = "provider"),
        @Mapping(expression = "java(i18nService.parseLocaleOrDefault(entity.getLocale()).toString())", target = "locale")
    })
    @Named("entityToDto")
    public abstract SimpleSocialUserDTO entityToDto(final Userinfo entity);

}
