package com.dreamsoftware.tcs.services.impl;

import com.dreamsoftware.tcs.config.properties.StreamChannelsProperties;
import com.dreamsoftware.tcs.mapper.CertificationAuthorityDetailMapper;
import com.dreamsoftware.tcs.mapper.SimpleCertificationAuthorityDetailMapper;
import com.dreamsoftware.tcs.mapper.UpdateCertificationAuthorityMapper;
import com.dreamsoftware.tcs.persistence.bc.repository.ICertificationAuthorityBlockchainRepository;
import com.dreamsoftware.tcs.persistence.nosql.entity.CertificationAuthorityEntity;
import com.dreamsoftware.tcs.persistence.nosql.repository.CertificationAuthorityRepository;
import com.dreamsoftware.tcs.services.ICertificationAuthorityService;
import com.dreamsoftware.tcs.web.dto.request.AddCaMemberDTO;
import com.dreamsoftware.tcs.web.dto.response.CertificationAuthorityDetailDTO;
import com.dreamsoftware.tcs.web.dto.response.SimpleCertificationAuthorityDetailDTO;
import com.dreamsoftware.tcs.web.dto.response.UpdateCertificationAuthorityDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 *
 * @author ssanchez
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class CertificationAuthorityServiceImpl implements ICertificationAuthorityService {

    private final CertificationAuthorityRepository certificationAuthorityRepository;
    private final ICertificationAuthorityBlockchainRepository caBlockchainRepository;
    private final SimpleCertificationAuthorityDetailMapper simpleCertificationAuthorityDetailMapper;
    private final CertificationAuthorityDetailMapper certificationAuthorityDetailMapper;
    private final UpdateCertificationAuthorityMapper updateCertificationAuthorityMapper;
    private final StreamChannelsProperties streamChannelsProperties;
    private final StreamBridge streamBridge;

    /**
     *
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
        return certificationAuthorityDetailMapper.entityToDTO(caEntity);
    }

    /**
     *
     * @param caId
     * @param memberId
     * @param adminId
     * @return
     * @throws Throwable
     */
    @Override
    public CertificationAuthorityDetailDTO enableMember(final String caId, final String memberId, final String adminId) throws Throwable {
        Assert.notNull(caId, "Ca ID can not be null");
        Assert.notNull(memberId, "Id can not be null");
        Assert.notNull(adminId, "Id can not be null");

        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     *
     * @param caId
     * @param memberId
     * @param adminId
     * @return
     * @throws Throwable
     */
    @Override
    public CertificationAuthorityDetailDTO disableMember(final String caId, final String memberId, final String adminId) throws Throwable {
        Assert.notNull(caId, "Ca ID can not be null");
        Assert.notNull(memberId, "Id can not be null");
        Assert.notNull(adminId, "Id can not be null");
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     *
     * @param id
     * @param member
     * @return
     * @throws Throwable
     */
    @Override
    public CertificationAuthorityDetailDTO addMember(final String id, final AddCaMemberDTO member) throws Throwable {
        Assert.notNull(id, "Id can not be null");
        Assert.notNull(member, "member can not be null");
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     *
     * @param id
     * @param memberId
     * @return
     * @throws Throwable
     */
    @Override
    public CertificationAuthorityDetailDTO removeMember(String id, String memberId) throws Throwable {
        Assert.notNull(id, "Id can not be null");
        Assert.notNull(memberId, "member can not be null");
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     *
     * @param id
     * @return
     * @throws Throwable
     */
    @Override
    public CertificationAuthorityDetailDTO enable(String id) throws Throwable {
        Assert.notNull(id, "Id can not be null");
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     *
     * @param id
     * @return
     * @throws Throwable
     */
    @Override
    public CertificationAuthorityDetailDTO disable(String id) throws Throwable {
        Assert.notNull(id, "Id can not be null");
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
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
     *
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

    @Override
    public void remove(String caId) {

    }

}
