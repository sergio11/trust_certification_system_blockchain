package com.dreamsoftware.tcs.mapper;

import com.dreamsoftware.tcs.web.dto.response.SignUpSocialUserDTO;
import com.dreamsoftware.tcs.web.dto.response.SimpleSocialUserDTO;
import java.util.List;
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
public abstract class SimpleSocialUserMapper {

    /**
     * @param user
     * @return DTO
     */
    @Mappings({
        @Mapping(source = "user.id", target = "passwordClear")
    })
    @Named("simpleSocialUserDTOToSignUpSocialUserDTO")
    public abstract SignUpSocialUserDTO simpleSocialUserDTOToSignUpSocialUserDTO(SimpleSocialUserDTO user);

    /**
     * @param userList
     * @return dto list
     */
    @IterableMapping(qualifiedByName = "simpleSocialUserDTOToSignUpSocialUserDTO")
    public abstract List<SignUpSocialUserDTO> simpleSocialUserDTOToSignUpSocialUserDTO(List<SimpleSocialUserDTO> userList);
}
