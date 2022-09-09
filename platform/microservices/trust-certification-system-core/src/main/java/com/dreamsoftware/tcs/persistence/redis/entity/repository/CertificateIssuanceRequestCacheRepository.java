package com.dreamsoftware.tcs.persistence.redis.entity.repository;

import com.dreamsoftware.tcs.persistence.redis.entity.CertificateIssuanceRequestCacheEntity;
import com.redis.om.spring.repository.RedisDocumentRepository;

/**
 *
 * @author ssanchez
 */
public interface CertificateIssuanceRequestCacheRepository extends RedisDocumentRepository<CertificateIssuanceRequestCacheEntity, String> {

}
