package com.dreamsoftware.blockchaingateway.persistence.bc.repository.mapper;

import com.dreamsoftware.blockchaingateway.contracts.CertificationAuthorityContract;
import com.dreamsoftware.blockchaingateway.persistence.bc.repository.entity.CertificationAuthorityEventEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.Named;

/**
 *
 * @author ssanchez
 */
@Mapper(unmappedTargetPolicy = org.mapstruct.ReportingPolicy.IGNORE)
public abstract class CertificationAuthorityEventEntityMapper {

    /**
     *
     * @param event
     * @return
     */
    @Mappings({
        @Mapping(source = "event.log.address", target = "address"),
        @Mapping(source = "event.log.blockHash", target = "blockHash"),
        @Mapping(source = "event.log.blockNumber", target = "blockNumber"),
        @Mapping(source = "event.log.blockNumberRaw", target = "blockNumberRaw"),
        @Mapping(source = "event.log.data", target = "data"),
        @Mapping(source = "event.log.logIndex", target = "logIndex"),
        @Mapping(source = "event.log.logIndexRaw", target = "logIndexRaw"),
        @Mapping(source = "event.log.transactionHash", target = "transactionHash"),
        @Mapping(source = "event.log.transactionIndex", target = "transactionIndex"),
        @Mapping(source = "event.log.transactionIndexRaw", target = "transactionIndexRaw"),
        @Mapping(expression = "java(com.dreamsoftware.blockchaingateway.persistence.bc.repository.entity.CertificationAuthorityEventTypeEnum.CA_CREATED)", target = "type")
    })
    @Named("mapEventToEntity")
    public abstract CertificationAuthorityEventEntity mapEventToEntity(CertificationAuthorityContract.OnNewCertificationAuthorityCreatedEventResponse event);

    /**
     *
     * @param event
     * @return
     */
    @Mappings({
        @Mapping(source = "event.log.address", target = "address"),
        @Mapping(source = "event.log.blockHash", target = "blockHash"),
        @Mapping(source = "event.log.blockNumber", target = "blockNumber"),
        @Mapping(source = "event.log.blockNumberRaw", target = "blockNumberRaw"),
        @Mapping(source = "event.log.data", target = "data"),
        @Mapping(source = "event.log.logIndex", target = "logIndex"),
        @Mapping(source = "event.log.logIndexRaw", target = "logIndexRaw"),
        @Mapping(source = "event.log.transactionHash", target = "transactionHash"),
        @Mapping(source = "event.log.transactionIndex", target = "transactionIndex"),
        @Mapping(source = "event.log.transactionIndexRaw", target = "transactionIndexRaw"),
        @Mapping(expression = "java(com.dreamsoftware.blockchaingateway.persistence.bc.repository.entity.CertificationAuthorityEventTypeEnum.CA_REMOVED)", target = "type")
    })
    @Named("mapEventToEntity")
    public abstract CertificationAuthorityEventEntity mapEventToEntity(CertificationAuthorityContract.OnCertificationAuthorityRemovedEventResponse event);

    /**
     *
     * @param event
     * @return
     */
    @Mappings({
        @Mapping(source = "event.log.address", target = "address"),
        @Mapping(source = "event.log.blockHash", target = "blockHash"),
        @Mapping(source = "event.log.blockNumber", target = "blockNumber"),
        @Mapping(source = "event.log.blockNumberRaw", target = "blockNumberRaw"),
        @Mapping(source = "event.log.data", target = "data"),
        @Mapping(source = "event.log.logIndex", target = "logIndex"),
        @Mapping(source = "event.log.logIndexRaw", target = "logIndexRaw"),
        @Mapping(source = "event.log.transactionHash", target = "transactionHash"),
        @Mapping(source = "event.log.transactionIndex", target = "transactionIndex"),
        @Mapping(source = "event.log.transactionIndexRaw", target = "transactionIndexRaw"),
        @Mapping(expression = "java(com.dreamsoftware.blockchaingateway.persistence.bc.repository.entity.CertificationAuthorityEventTypeEnum.CA_ENABLED)", target = "type")
    })
    @Named("mapEventToEntity")
    public abstract CertificationAuthorityEventEntity mapEventToEntity(CertificationAuthorityContract.OnCertificationAuthorityEnabledEventResponse event);

    /**
     *
     * @param event
     * @return
     */
    @Mappings({
        @Mapping(source = "event.log.address", target = "address"),
        @Mapping(source = "event.log.blockHash", target = "blockHash"),
        @Mapping(source = "event.log.blockNumber", target = "blockNumber"),
        @Mapping(source = "event.log.blockNumberRaw", target = "blockNumberRaw"),
        @Mapping(source = "event.log.data", target = "data"),
        @Mapping(source = "event.log.logIndex", target = "logIndex"),
        @Mapping(source = "event.log.logIndexRaw", target = "logIndexRaw"),
        @Mapping(source = "event.log.transactionHash", target = "transactionHash"),
        @Mapping(source = "event.log.transactionIndex", target = "transactionIndex"),
        @Mapping(source = "event.log.transactionIndexRaw", target = "transactionIndexRaw"),
        @Mapping(expression = "java(com.dreamsoftware.blockchaingateway.persistence.bc.repository.entity.CertificationAuthorityEventTypeEnum.CA_DISABLED)", target = "type")
    })
    @Named("mapEventToEntity")
    public abstract CertificationAuthorityEventEntity mapEventToEntity(CertificationAuthorityContract.OnCertificationAuthorityDisabledEventResponse event);
}
