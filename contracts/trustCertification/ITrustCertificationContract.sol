// SPDX-License-Identifier: MIT
pragma solidity >=0.4.4 <0.7.4;
pragma experimental ABIEncoderV2;

interface ITrustCertificationContract { 
    
    function issueCertificate(address _recipientAddress, uint _certificateCourseId, uint _qualification) external returns(uint);
    function renewCertificate(uint _id) external;
    function enableCertificate(uint _id) external;
    function disableCertificate(uint _id) external;
    function updateCertificateVisibility(uint _id, bool isVisible) external;
    function isCertificateValid(uint _id) external view returns (bool);
    function getMyCertificatesAsRecipient() external view returns (CertificateRecord[] memory);
    function getMyCertificatesAsIssuer() external view returns (CertificateRecord[] memory);
    
    // Data Structure
    struct CertificateRecord {
        address issuerAddress;
        address recipientAddress;
        uint certificateCourseId;
        uint256 expirationDate;
        uint qualification;
        uint durationInHours;
        uint256 expeditionDate;
        bool isVisible;
        bool isEnabled;
        bool isExist;
    }

    // Events
    event OnNewCertificateGenerated(uint _id);
    event OnCertificateRenewed(uint _id);
    event OnCertificateDeleted(uint _id);
    event OnCertificateEnabled(uint _id);
    event OnCertificateDisabled(uint _id);
    event OnCertificateVisibilityUpdated(uint _id, bool _isVisible);
}