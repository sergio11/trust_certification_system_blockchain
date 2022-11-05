package com.dreamsoftware.tcs.mapper;

import com.dreamsoftware.tcs.persistence.nosql.entity.AuthorityEntity;
import com.dreamsoftware.tcs.persistence.nosql.entity.AuthorityEnum;
import com.dreamsoftware.tcs.persistence.nosql.entity.UserEntity;
import com.dreamsoftware.tcs.persistence.nosql.repository.AuthorityRepository;
import com.dreamsoftware.tcs.web.dto.request.AddCaMemberDTO;
import org.mapstruct.*;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author ssanchez
 */
@Mapper(unmappedTargetPolicy = org.mapstruct.ReportingPolicy.IGNORE)
public abstract class AddCaMemberMapper {

    /**
     * Authority Repository
     */
    @Autowired
    protected AuthorityRepository authorityRepository;

    /**
     *
     * @param dto
     * @return
     */
    @Mappings({
            @Mapping(expression = "java(getUserAuthority())", target = "authority"),
            @Mapping(expression = "java(com.dreamsoftware.tcs.persistence.nosql.entity.UserTypeEnum.CA_MEMBER)", target = "type")
    })
    @Named("dtoToEntity")
    public abstract UserEntity dtoToEntity(final AddCaMemberDTO dto);

    /**
     *
     * @param dtoList
     * @return
     */
    @IterableMapping(qualifiedByName = "dtoToEntity")
    public abstract Iterable<UserEntity> dtoToEntity(final Iterable<AddCaMemberDTO> dtoList);

    protected AuthorityEntity getUserAuthority() {
        return authorityRepository.findOneByType(AuthorityEnum.ROLE_CA_MEMBER).orElse(null);
    }
}
