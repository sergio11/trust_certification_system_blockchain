package com.dreamsoftware.tcs.services;

import com.dreamsoftware.tcs.web.dto.request.AddCaMemberDTO;
import com.dreamsoftware.tcs.web.dto.response.CertificationAuthorityDetailDTO;
import com.dreamsoftware.tcs.web.dto.response.SimpleCertificationAuthorityDetailDTO;
import com.dreamsoftware.tcs.web.dto.response.UpdateCertificationAuthorityDTO;
import java.util.Optional;

/**
 *
 * @author ssanchez
 */
public interface ICertificationAuthorityService {

    /**
     * Get All
     *
     * @return
     * @throws java.lang.Throwable
     */
    Iterable<SimpleCertificationAuthorityDetailDTO> getAll() throws Throwable;

    /**
     * Get Detail
     *
     * @param id
     * @return
     * @throws java.lang.Throwable
     */
    CertificationAuthorityDetailDTO getDetail(final String id) throws Throwable;

    /**
     * Enable Certification Authority
     *
     * @param id
     * @return
     * @throws Throwable
     */
    void enable(final String id) throws Throwable;

    /**
     * Enable Certification Authority Member
     *
     * @param caId
     * @param memberId
     * @param adminId
     * @return
     * @throws Throwable
     */
    void enableMember(final String caId, final String memberId, final String adminId) throws Throwable;

    /**
     * Disable Certification Authority
     *
     * @param id
     * @return
     * @throws Throwable
     */
    void disable(final String id) throws Throwable;

    /**
     * Disable Certification Authority Member
     *
     * @param caId
     * @param memberId
     * @param adminId
     * @return
     * @throws Throwable
     */
    void disableMember(final String caId, final String memberId, final String adminId) throws Throwable;

    /**
     * Add Certification Authority Member
     *
     * @param id
     * @param member
     * @return
     * @throws Throwable
     */
    CertificationAuthorityDetailDTO addMember(final String id, final AddCaMemberDTO member) throws Throwable;

    /**
     * Remove Certification Authority Member
     * @param caId
     * @param memberId
     * @param adminId
     * @return
     * @throws Throwable
     */
    void removeMember(final String caId, final String memberId, final String adminId) throws Throwable;

    /**
     *
     * @param id
     * @return
     */
    Optional<UpdateCertificationAuthorityDTO> editById(final String id);

    /**
     *
     * @param id
     * @param model
     * @return
     */
    Optional<SimpleCertificationAuthorityDetailDTO> update(final String id, final UpdateCertificationAuthorityDTO model);

    /**
     *
     * @param caId
     */
    void remove(final String caId);

}
