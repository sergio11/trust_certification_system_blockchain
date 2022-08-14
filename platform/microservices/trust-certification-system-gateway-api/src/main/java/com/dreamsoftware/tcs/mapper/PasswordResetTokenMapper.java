package com.dreamsoftware.tcs.mapper;

import com.dreamsoftware.tcs.persistence.bc.repository.entity.CertificateIssuedEntity;
import com.dreamsoftware.tcs.persistence.nosql.entity.PasswordResetTokenEntity;
import com.dreamsoftware.tcs.web.dto.internal.PasswordResetTokenDTO;
import com.dreamsoftware.tcs.web.dto.response.CertificateIssuedDTO;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.Named;

/**
 *
 * @author ssanchez
 */
@Mapper(unmappedTargetPolicy = org.mapstruct.ReportingPolicy.IGNORE)
public abstract class PasswordResetTokenMapper {

    @Mappings({
        @Mapping(expression = "java(entity.getUser().getId().toString())", target = "id"),
        @Mapping(source = "entity.user.name", target = "name"),
        @Mapping(source = "entity.user.language", target = "locale"),
        @Mapping(source = "entity.user.email", target = "email"),
        @Mapping(source = "expiryDate", target = "expiryDate", dateFormat = "yyyy/MM/dd HH:mm:ss")
    })
    @Named("passwordResetTokenEntityToPasswordResetTokenDTO")
    public abstract PasswordResetTokenDTO passwordResetTokenEntityToPasswordResetTokenDTO(PasswordResetTokenEntity entity);

    @IterableMapping(qualifiedByName = "passwordResetTokenEntityToPasswordResetTokenDTO")
    public abstract Iterable<PasswordResetTokenDTO> passwordResetTokenEntityToPasswordResetTokenDTOs(Iterable<PasswordResetTokenEntity> entityList);
}
