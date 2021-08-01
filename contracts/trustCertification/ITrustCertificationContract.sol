// SPDX-License-Identifier: MIT
pragma solidity >=0.4.4 <0.7.4;
pragma experimental ABIEncoderV2;

interface ITrustCertificationContract { 
    
    function issueCertificate(address _recipientAddress, string memory _id, string memory _certificateCourseId, uint _qualification) external;
    function enableCertificate(string memory _id) external;
    function disableCertificate(string memory _id) external;
    function isCertificateValid(string memory _id) external view returns (bool);
    
    // Data Structure
    struct CertificateRecord {
        address issuerAddress;
        address recipientAddress;
        string certificateCourseId;
        uint256 expirationDate;
        uint qualification;
        uint durationInHours;
        uint256 expeditionDate;
        bool isEnabled;
        bool isExist;
    }

    // Events
    event OnNewCertificateGenerated(string _id);
    event OnCertificateDeleted(string _id);
    event OnCertificateEnabled(string _id);
    event OnCertificateDisabled(string _id);
    
}