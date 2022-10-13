package com.dreamsoftware.tcs.services.impl;

import com.dreamsoftware.tcs.config.properties.StreamChannelsProperties;
import com.dreamsoftware.tcs.mapper.*;
import com.dreamsoftware.tcs.persistence.nosql.entity.CertificationAuthorityEntity;
import com.dreamsoftware.tcs.persistence.nosql.entity.UserEntity;
import com.dreamsoftware.tcs.persistence.nosql.entity.UserStateEnum;
import com.dreamsoftware.tcs.persistence.nosql.repository.CertificationAuthorityRepository;
import com.dreamsoftware.tcs.persistence.nosql.repository.UserRepository;
import com.dreamsoftware.tcs.service.IWalletService;
import com.dreamsoftware.tcs.services.ICertificationAuthorityService;
import com.dreamsoftware.tcs.stream.events.user.*;
import com.dreamsoftware.tcs.web.dto.request.AddCaMemberDTO;
import com.dreamsoftware.tcs.web.dto.response.CertificationAuthorityDetailDTO;
import com.dreamsoftware.tcs.web.dto.response.SimpleCertificationAuthorityDetailDTO;
import com.dreamsoftware.tcs.web.dto.response.SimpleUserDTO;
import com.dreamsoftware.tcs.web.dto.response.UpdateCertificationAuthorityDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author ssanchez
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class CertificationAuthorityServiceImpl implements ICertificationAuthorityService {

    private final CertificationAuthorityRepository certificationAuthorityRepository;
    private final SimpleCertificationAuthorityDetailMapper simpleCertificationAuthorityDetailMapper;
    private final CertificationAuthorityDetailMapper certificationAuthorityDetailMapper;
    private final UpdateCertificationAuthorityMapper updateCertificationAuthorityMapper;
    private final StreamChannelsProperties streamChannelsProperties;
    private final StreamBridge streamBridge;
    private final AddCaMemberMapper addCaMemberMapper;
    private final UserRepository userRepository;
    private final SimpleUserMapper simpleUserMapper;
    private final IWalletService walletService;

    /**
     * @return @throws Throwable
     */
    @Override
    public Iterable<SimpleCertificationAuthorityDetailDTO> getAll() throws Throwable {
        return certificationAuthorityRepository.findAll()
                .stream()
                .map(simpleCertificationAuthorityDetailMapper::entityToDTO)
                .collect(Collectors.toList());
    }

    /**
     * Get Detail
     *
     * @param id
     * @return
     */
    @Override
    public CertificationAuthorityDetailDTO getDetail(final String id) throws Throwable {
        Assert.notNull(id, "Id can not be null");
        log.debug("Get Certification Authority Detail, id: " + id);
        final CertificationAuthorityEntity caEntity = certificationAuthorityRepository.findById(new ObjectId(id))
                .orElseThrow(() -> {
                    throw new IllegalStateException("Certification Authority Not Found");
                });
        final List<UserEntity> caMembers = userRepository.findAllByCaId(new ObjectId(id));
        return certificationAuthorityDetailMapper.entityToDTO(caEntity, caMembers);
    }

    /**
     * @param caId
     * @param memberId
     * @param adminId
     * @return
     * @throws Throwable
     */
    @Override
    public void enableMember(final String caId, final String memberId, final String adminId) throws Throwable {
        Assert.notNull(caId, "Ca ID can not be null");
        Assert.notNull(memberId, "Id can not be null");
        Assert.notNull(adminId, "Id can not be null");
        streamBridge.send(streamChannelsProperties.getUserManagement(), EnableCertificationAuthorityMemberEvent.builder()
                .adminId(adminId)
                .memberId(memberId)
                .caId(caId)
                .build());
    }

    /**
     * @param caId
     * @param memberId
     * @param adminId
     * @return
     * @throws Throwable
     */
    @Override
    public void disableMember(final String caId, final String memberId, final String adminId) throws Throwable {
        Assert.notNull(caId, "Ca ID can not be null");
        Assert.notNull(memberId, "Id can not be null");
        Assert.notNull(adminId, "Id can not be null");
        streamBridge.send(streamChannelsProperties.getUserManagement(), DisableCertificationAuthorityMemberEvent.builder()
                .adminId(adminId)
                .memberId(memberId)
                .caId(caId)
                .build());
    }

    /**
     * Add Certification Authority Member
     *
     * @param caId
     * @param member
     * @param adminWalletHash
     * @return
     * @throws Throwable
     */
    @Override
    public SimpleUserDTO addMember(final String caId, final AddCaMemberDTO member, final String adminWalletHash) throws Throwable {
        Assert.notNull(caId, "caId can not be null");
        Assert.notNull(member, "member can not be null");
        Assert.notNull(adminWalletHash, "adminWalletHash can not be null");
        final UserEntity caMemberEntity = addCaMemberMapper.dtoToEntity(member);
        // Generate Wallet
        final String walletHash = walletService.generateWallet();
        log.debug("Wallet created with hash: " + walletHash);
        caMemberEntity.setState(UserStateEnum.PENDING_VALIDATE);
        caMemberEntity.setWalletHash(walletHash);
        caMemberEntity.setHasCredentialsExpired(true);
        caMemberEntity.setLastPasswordReset(new Date());
        final UserEntity userEntitySaved = userRepository.save(caMemberEntity);
        streamBridge.send(streamChannelsProperties.getUserManagement(), NewCertificationAuthorityMemberEvent.builder()
                .caId(caId)
                .adminWalletHash(adminWalletHash)
                .memberWalletHash(userEntitySaved.getWalletHash())
                .build());
        return simpleUserMapper.entityToDTO(userEntitySaved);
    }

    /**
     * @param caId
     * @param memberId
     * @param adminId
     * @throws Throwable
     */
    @Override
    public void removeMember(final String caId, final String memberId, final String adminId) throws Throwable {
        Assert.notNull(caId, "caId can not be null");
        Assert.notNull(memberId, "member can not be null");
        Assert.notNull(adminId, "adminId can not be null");
        streamBridge.send(streamChannelsProperties.getUserManagement(), RemoveCertificationAuthorityMemberEvent.builder()
                .caId(caId)
                .memberId(memberId)
                .adminId(adminId)
                .build());
    }

    /**
     * @param id
     * @return
     * @throws Throwable
     */
    @Override
    public void enable(final String id) throws Throwable {
        Assert.notNull(id, "Id can not be null");
        streamBridge.send(streamChannelsProperties.getUserManagement(), EnableCertificationAuthorityEvent.builder()
                .caId(id)
                .build());
    }

    /**
     * @param id
     * @return
     * @throws Throwable
     */
    @Override
    public void disable(final String id) throws Throwable {
        Assert.notNull(id, "Id can not be null");
        streamBridge.send(streamChannelsProperties.getUserManagement(), DisableCertificationAuthorityEvent.builder()
                .caId(id)
                .build());
    }

    /**
     * Edit by wallet hash
     *
     * @param id
     * @return
     */
    @Override
    public Optional<UpdateCertificationAuthorityDTO> editById(final String id) {
        Assert.notNull(id, "id can not be null");
        Assert.isTrue(ObjectId.isValid(id), "id must be valid Object Id");
        log.debug("editById, id: " + id);
        return certificationAuthorityRepository.findById(new ObjectId(id))
                .map(updateCertificationAuthorityMapper::entityToDTO);
    }

    /**
     * @param id
     * @param model
     * @return
     */
    @Override
    public Optional<SimpleCertificationAuthorityDetailDTO> update(final String id, final UpdateCertificationAuthorityDTO model) {
        Assert.notNull(id, "walletHash can not be null");
        Assert.notNull(model, "model can not be null");
        return certificationAuthorityRepository.findById(new ObjectId(id))
                .map(certificationAuthorityEntity -> updateCertificationAuthorityMapper.update(certificationAuthorityEntity, model))
                .map(certificationAuthorityEntity -> certificationAuthorityRepository.update(id, certificationAuthorityEntity))
                .map(simpleCertificationAuthorityDetailMapper::entityToDTO);
    }

    /**
     * @param caId
     */
    @Override
    public void remove(final String caId) {
        Assert.notNull(caId, "caId can not be null");
        streamBridge.send(streamChannelsProperties.getUserManagement(), RemoveCertificationAuthorityEvent.builder()
                .caId(caId)
                .build());
    }

}
