package com.dreamsoftware.blockchaingateway.persistence.bc.repository.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 *
 * @author ssanchez
 */
@Data
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
@NoArgsConstructor
public class CertificateIssuedEntity {

    private String id;
    private String issuerAddress;
    private String recipientAddress;
    private String course;
    private Long expirationDate;
    private Long qualification;
    private Long durationInHours;
    private Long expeditionDate;
    private Boolean isVisible;
    private Boolean isEnabled;
    private Boolean isExist;
}
