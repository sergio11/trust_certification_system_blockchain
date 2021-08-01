// SPDX-License-Identifier: MIT
pragma solidity >=0.4.4 <0.7.4;
pragma experimental ABIEncoderV2;

interface ICertificationAuthorityContract {
    
    function addCertificationAuthority(string memory _name, uint _defaultCostOfIssuingCertificate) external;
    function removeCertificationAuthority(address _address) external;
    function enableCertificationAuthority(address _address) external;
    function disableCertificationAuthority(address _address) external;
    function isCertificationAuthorityEnabled(address _address) external view returns (bool);
    function isCertificationAuthorityExists(address _address) external view returns (bool);
    function getDefaultCostOfIssuingCertificate(address _address) external view returns (uint);
    
    
    // Data Structure
    struct CertificationAuthorityRecord {
        string name;
        uint defaultCostOfIssuingCertificate;
        bool isEnabled;
        bool isExist;
    }
    
    // Events Definitions
    event OnNewCertificationAuthorityCreated(address _address, string name);
    event OnCertificationAuthorityRemoved(address _address);
    event OnCertificationAuthorityEnabled(address _address);
    event OnCertificationAuthorityDisabled(address _address);
    
}