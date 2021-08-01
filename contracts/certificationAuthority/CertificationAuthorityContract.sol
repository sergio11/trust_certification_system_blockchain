// SPDX-License-Identifier: MIT
pragma solidity >=0.4.4 <0.7.4;
pragma experimental ABIEncoderV2;
import "../ownable/Ownable.sol";
import "../tokenManagement/ITokenManagementContract.sol";
import "./ICertificationAuthorityContract.sol";

contract CertificationAuthorityContract is Ownable, ICertificationAuthorityContract {
    
    address private tokenManagementAddr;
    uint8 public constant costOfAddCertificationAuthority = 20;
    
    mapping(address => CertificationAuthorityRecord) private certificationAuthorities;
    
    function setTokenManagementAddr(address _tokenManagementAddr) public payable onlyOwner() {
       tokenManagementAddr = _tokenManagementAddr;
    }
    
    function addCertificationAuthority(string memory _name, uint _defaultCostOfIssuingCertificate) external override CertificationAuthorityMustNotExist(msg.sender) {
        uint _senderTokens = ITokenManagementContract(tokenManagementAddr).getTokens(msg.sender);
        require(_senderTokens >= costOfAddCertificationAuthority, "You do not have enough tokens to register as Certification Authority");
        require(ITokenManagementContract(tokenManagementAddr).transfer(msg.sender, address(this), costOfAddCertificationAuthority), "The transfer could not be made");
        certificationAuthorities[msg.sender] = CertificationAuthorityRecord(_name, _defaultCostOfIssuingCertificate, true, true);
        emit OnNewCertificationAuthorityCreated(msg.sender, _name);
    }
    
    function removeCertificationAuthority(address _address) external override onlyOwner() CertificationAuthorityMustExist(_address) { 
        delete certificationAuthorities[_address];
        emit OnCertificationAuthorityRemoved(_address);
    }
    
    function enableCertificationAuthority(address _address) external override onlyOwner() CertificationAuthorityMustExist(_address) {
        certificationAuthorities[_address].isAvailable = true;
        emit OnCertificationAuthorityEnabled(_address);
    }
    
    function disableCertificationAuthority(address _address) external override onlyOwner() CertificationAuthorityMustExist(_address) {
       certificationAuthorities[_address].isAvailable = false;
       emit OnCertificationAuthorityDisabled(_address);
    }
    
    function isCertificationAuthorityEnabled(address _address) external view override CertificationAuthorityMustExist(_address) returns (bool)  {
        return certificationAuthorities[_address].isAvailable;
    }
    
    function isCertificationAuthorityExists(address _address) external view override returns (bool) {
        return certificationAuthorities[_address].isExist;
    }
    
    function getDefaultCostOfIssuingCertificate(address _address) public view override returns (uint) {
        return certificationAuthorities[_address].defaultCostOfIssuingCertificate;
    }
     
    // Modifiers

    modifier CertificationAuthorityMustExist(address _address) {
        require(certificationAuthorities[_address].isExist, "Certification Authority with given id don't exists");
        _;
    }
    
    modifier CertificationAuthorityMustNotExist(address _address) {
        require(!certificationAuthorities[_address].isExist, "Certification Authority with given id already exists");
        _;
    }
    
    
}