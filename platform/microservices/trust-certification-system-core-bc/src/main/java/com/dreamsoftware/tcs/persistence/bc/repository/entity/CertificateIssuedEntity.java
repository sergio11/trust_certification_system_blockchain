package com.dreamsoftware.tcs.persistence.bc.repository.entity;

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

    /**
     * Certificate Id
     */
    private String id;

    /**
     * Issuer Address
     */
    private String issuerAddress;

    /**
     * Recipient Address
     */
    private String recipientAddress;

    /**
     * Course
     */
    private String course;

    /**
     * Expiration Date
     */
    private Long expirationDate;

    /**
     * Qualification
     */
    private Long qualification;

    /**
     * Duration in hours
     */
    private Long durationInHours;

    /**
     * Expedition Date
     */
    private Long expeditionDate;

    /**
     * is visible
     */
    private Boolean isVisible;

    /**
     * is enabled
     */
    private Boolean isEnabled;

    /**
     * is Exists
     */
    private Boolean isExist;

    /**
     * Certificate File CID
     */
    private String fileCid;

    /**
     * Certificate File Hash
     */
    private String fileCertificateHash;

    /**
     * Certificate Image CID
     */
    private String imageCid;

    /**
     * Certificate Image Hash
     */
    private String imageCertificateHash;

}


