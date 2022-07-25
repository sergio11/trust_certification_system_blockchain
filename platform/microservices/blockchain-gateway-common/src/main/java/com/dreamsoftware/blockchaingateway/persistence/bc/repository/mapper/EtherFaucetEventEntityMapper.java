package com.dreamsoftware.blockchaingateway.persistence.bc.repository.mapper;

import com.dreamsoftware.blockchaingateway.contracts.EtherFaucetContract;
import com.dreamsoftware.blockchaingateway.persistence.bc.repository.entity.EtherFaucetEventEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.Named;

/**
 *
 * @author ssanchez
 */
@Mapper(unmappedTargetPolicy = org.mapstruct.ReportingPolicy.IGNORE)
public abstract class EtherFaucetEventEntityMapper {

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
        @Mapping(expression = "java(com.dreamsoftware.blockchaingateway.persistence.bc.repository.entity.EtherFaucetEventTypeEnum.DEPOSIT)", target = "type")
    })
    @Named("mapEventToEntity")
    public abstract EtherFaucetEventEntity mapEventToEntity(EtherFaucetContract.OnDepositEventResponse event);

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
        @Mapping(expression = "java(com.dreamsoftware.blockchaingateway.persistence.bc.repository.entity.EtherFaucetEventTypeEnum.GET_SEED_FUNDS)", target = "type")
    })
    @Named("mapEventToEntity")
    public abstract EtherFaucetEventEntity mapEventToEntity(EtherFaucetContract.OnGetSeedFundsEventResponse event);

}
