package com.dreamsoftware.blockchaingateway.persistence.bc.repository.mapper;

import com.dreamsoftware.blockchaingateway.contracts.CertificationCourseContract;
import com.dreamsoftware.blockchaingateway.persistence.bc.repository.entity.CertificationCourseEventEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.Named;

/**
 *
 * @author ssanchez
 */
@Mapper(unmappedTargetPolicy = org.mapstruct.ReportingPolicy.IGNORE)
public abstract class CertificationCourseEventEntityMapper {

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
        @Mapping(expression = "java(com.dreamsoftware.blockchaingateway.persistence.bc.repository.entity.CertificationCourseEventTypeEnum.COURSE_DISABLED)", target = "type"),
        @Mapping(source = "event._id", target = "courseId")
    })
    @Named("mapEventToEntity")
    public abstract CertificationCourseEventEntity mapEventToEntity(CertificationCourseContract.OnCertificationCourseDisabledEventResponse event);

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
        @Mapping(expression = "java(com.dreamsoftware.blockchaingateway.persistence.bc.repository.entity.CertificationCourseEventTypeEnum.COURSE_ENABLED)", target = "type"),
        @Mapping(source = "event._id", target = "courseId")
    })
    @Named("mapEventToEntity")
    public abstract CertificationCourseEventEntity mapEventToEntity(CertificationCourseContract.OnCertificationCourseEnabledEventResponse event);

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
        @Mapping(expression = "java(com.dreamsoftware.blockchaingateway.persistence.bc.repository.entity.CertificationCourseEventTypeEnum.COURSE_REMOVED)", target = "type"),
        @Mapping(source = "event._id", target = "courseId")
    })
    @Named("mapEventToEntity")
    public abstract CertificationCourseEventEntity mapEventToEntity(CertificationCourseContract.OnCertificationCourseRemovedEventResponse event);

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
        @Mapping(expression = "java(com.dreamsoftware.blockchaingateway.persistence.bc.repository.entity.CertificationCourseEventTypeEnum.COURSE_CREATED)", target = "type"),
        @Mapping(source = "event._id", target = "courseId")
    })
    @Named("mapEventToEntity")
    public abstract CertificationCourseEventEntity mapEventToEntity(CertificationCourseContract.OnNewCertificationCourseCreatedEventResponse event);
}
