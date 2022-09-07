package com.dreamsoftware.tcs.mapper;

import com.dreamsoftware.tcs.i18n.service.I18NService;
import com.dreamsoftware.tcs.web.dto.response.SimpleSocialUserDTO;
import com.restfb.types.User;
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
public abstract class UserFacebookMapper {

    @Autowired
    protected I18NService i18nService;

    /**
     *
     * @param entity
     * @return
     */
    @Mappings({
        @Mapping(source = "entity.firstName", target = "name"),
        @Mapping(source = "entity.lastName", target = "surname"),
        @Mapping(source = "entity.birthdayAsDate", target = "birthday"),
        @Mapping(source = "entity.email", target = "email"),
        @Mapping(source = "entity.id", target = "id"),
        @Mapping(constant = "FACEBOOK", target = "provider"),
        @Mapping(expression = "java(i18nService.parseLocaleOrDefault(entity.getLocale()).toString())", target = "locale")
    })
    @Named("entityToDto")
    public abstract SimpleSocialUserDTO entityToDto(User entity);

}
