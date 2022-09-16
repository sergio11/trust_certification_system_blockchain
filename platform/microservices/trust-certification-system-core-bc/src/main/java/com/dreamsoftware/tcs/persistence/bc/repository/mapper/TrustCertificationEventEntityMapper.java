package com.dreamsoftware.tcs.persistence.bc.repository.mapper;

import com.dreamsoftware.tcs.contracts.TrustCertificationContract.OnCertificateDeletedEventResponse;
import com.dreamsoftware.tcs.contracts.TrustCertificationContract.OnCertificateDisabledEventResponse;
import com.dreamsoftware.tcs.contracts.TrustCertificationContract.OnCertificateEnabledEventResponse;
import com.dreamsoftware.tcs.contracts.TrustCertificationContract.OnCertificateRenewedEventResponse;
import com.dreamsoftware.tcs.contracts.TrustCertificationContract.OnCertificateVisibilityUpdatedEventResponse;
import com.dreamsoftware.tcs.contracts.TrustCertificationContract.OnNewCertificateGeneratedEventResponse;
import com.dreamsoftware.tcs.persistence.bc.repository.entity.TrustCertificationEventEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.Named;

/**
 *
 * @author ssanchez
 */
@Mapper(unmappedTargetPolicy = org.mapstruct.ReportingPolicy.IGNORE)
public abstract class TrustCertificationEventEntityMapper {

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
        @Mapping(expression = "java(com.dreamsoftware.tcs.persistence.bc.repository.entity.TrustCertificationEventTypeEnum.CERTIFICATE_DELETED)", target = "type"),
        @Mapping(source = "event._id", target = "certificateId")
    })
    @Named("mapEventToEntity")
    public abstract TrustCertificationEventEntity mapEventToEntity(OnCertificateDeletedEventResponse event);

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
        @Mapping(expression = "java(com.dreamsoftware.tcs.persistence.bc.repository.entity.TrustCertificationEventTypeEnum.CERTIFICATE_DISABLED)", target = "type"),
        @Mapping(source = "event._id", target = "certificateId")
    })
    @Named("mapEventToEntity")
    public abstract TrustCertificationEventEntity mapEventToEntity(OnCertificateDisabledEventResponse event);

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
        @Mapping(expression = "java(com.dreamsoftware.tcs.persistence.bc.repository.entity.TrustCertificationEventTypeEnum.CERTIFICATE_ENABLED)", target = "type"),
        @Mapping(source = "event._id", target = "certificateId")
    })
    @Named("mapEventToEntity")
    public abstract TrustCertificationEventEntity mapEventToEntity(OnCertificateEnabledEventResponse event);

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
        @Mapping(expression = "java(com.dreamsoftware.tcs.persistence.bc.repository.entity.TrustCertificationEventTypeEnum.CERTIFICATE_RENEWED)", target = "type"),
        @Mapping(source = "event._id", target = "certificateId")
    })
    @Named("mapEventToEntity")
    public abstract TrustCertificationEventEntity mapEventToEntity(OnCertificateRenewedEventResponse event);

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
        @Mapping(expression = "java(com.dreamsoftware.tcs.persistence.bc.repository.entity.TrustCertificationEventTypeEnum.CERTIFICATE_VISIBILITY_UPDATED)", target = "type"),
        @Mapping(source = "event._id", target = "certificateId")
    })
    @Named("mapEventToEntity")
    public abstract TrustCertificationEventEntity mapEventToEntity(OnCertificateVisibilityUpdatedEventResponse event);

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
        @Mapping(expression = "java(com.dreamsoftware.tcs.persistence.bc.repository.entity.TrustCertificationEventTypeEnum.CERTIFICATE_GENERATED)", target = "type"),
        @Mapping(source = "event._id", target = "certificateId")
    })
    @Named("mapEventToEntity")
    public abstract TrustCertificationEventEntity mapEventToEntity(OnNewCertificateGeneratedEventResponse event);

}
