// SPDX-License-Identifier: MIT
pragma solidity >=0.4.4 <0.7.4;
pragma experimental ABIEncoderV2;

interface ICertificationAuthorityContract {
    
    function addCertificationAuthority(string memory _id, string memory _name, uint _defaultCostOfIssuingCertificate) external;
    function removeCertificationAuthority(string memory _id) external;
    function enableCertificationAuthority(string memory _id) external;
    function disableCertificationAuthority(string memory _id) external;
    function isCertificationAuthorityEnabled(string memory _id) external view returns (bool);
    function isCertificationAuthorityExists(string memory _id) external view returns (bool);
    function getCertificationAuthority(string memory _id) external view returns (CertificationAuthorityRecord memory);
    
    // Data Structure
    struct CertificationAuthorityRecord {
        string name;
        uint defaultCostOfIssuingCertificate;
        bool isAvailable;
        bool isExist;
    }
    
    // Events Definitions
    event OnNewCertificationAuthorityCreated(string _id);
    event OnCertificationAuthorityRemoved(string _id);
    event OnCertificationAuthorityEnabled(string _id);
    event OnCertificationAuthorityDisabled(string _id);
    
}