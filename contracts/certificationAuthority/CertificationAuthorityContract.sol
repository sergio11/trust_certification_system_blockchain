// SPDX-License-Identifier: MIT
pragma solidity >=0.4.4 <0.7.4;
pragma experimental ABIEncoderV2;
import "./ICertificationAuthorityContract.sol";

contract CertificationAuthorityContract is ICertificationAuthorityContract {
    
    // Owner Direction
    address payable public ownerAddress;
    
    
    mapping(string => CertificationAuthorityRecord) public certificationAuthorities;
    
    constructor () public {
       ownerAddress = msg.sender;
    }
    
    function addCertificationAuthority(string memory _id, string memory _name, uint _costOfIssuingCertificate) external override restricted() CertificationAuthorityMustNotExist(_id) {
        certificationAuthorities[_id] = CertificationAuthorityRecord(_name, _costOfIssuingCertificate, true, true);
        emit OnNewCertificationAuthorityCreated(_id);
    }
    
    
    function removeCertificationAuthority(string memory _id) external override restricted() CertificationAuthorityMustExist(_id) { 
        delete certificationAuthorities[_id];
        emit OnCertificationAuthorityRemoved(_id);
    }
    
    function enableCertificationAuthority(string memory _id) external override restricted() CertificationAuthorityMustExist(_id) {
        certificationAuthorities[_id].isAvailable = true;
        emit OnCertificationAuthorityEnabled(_id);
    }
    
    function disableCertificationAuthority(string memory _id) external override restricted() CertificationAuthorityMustExist(_id) {
       certificationAuthorities[_id].isAvailable = false;
       emit OnCertificationAuthorityDisabled(_id);
    }
    
    function isCertificationAuthorityEnabled(string memory _id) external view override restricted() CertificationAuthorityMustExist(_id) returns (bool)  {
        return certificationAuthorities[_id].isAvailable;
    }
    
    // Modifiers
    modifier restricted() {
        if (msg.sender == ownerAddress) _;
    }
    
    // Modifiers
    modifier CertificationAuthorityMustExist(string memory _id) {
        require(certificationAuthorities[_id].isExist, "Certification Authority with given id don't exists");
        _;
    }
    
    modifier CertificationAuthorityMustNotExist(string memory _id) {
        require(!certificationAuthorities[_id].isExist, "Certification Authority with given id already exists");
        _;
    }
    
    
}