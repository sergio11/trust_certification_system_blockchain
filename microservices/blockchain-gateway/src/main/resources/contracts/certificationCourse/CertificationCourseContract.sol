// SPDX-License-Identifier: MIT
pragma solidity ^0.7.1;
pragma experimental ABIEncoderV2;
import "../ownable/Ownable.sol";
import "./ICertificationCourseContract.sol";
import "../tokenManagement/ITokenManagementContract.sol";
import "../certificationAuthority/ICertificationAuthorityContract.sol";

contract CertificationCourseContract is Ownable, ICertificationCourseContract {
    
    uint8 private constant COST_OF_ADD_CERTIFICATION_COURSE = 10;
    
    address private tokenManagementContractAddr;
    address private certificationAuthorityContractAddr;
    mapping(uint => CertificationCourseRecord) private certificationCourse;
    mapping(address => uint[]) private certificationAuthorityCourses;
    uint private lastID;
    
    function setCertificationAuthorityContractAddr(address _certificationAuthorityContractAddr) public payable onlyOwner() {
       certificationAuthorityContractAddr = _certificationAuthorityContractAddr;
    }
    
    function setTokenManagementAddr(address _tokenManagementContractAddr) public payable onlyOwner() {
       tokenManagementContractAddr = _tokenManagementContractAddr;
    }
    
    function addCertificationCourse(string memory _name, uint _costOfIssuingCertificate, uint _durationInHours, uint _expirationInDays, bool _canBeRenewed, uint _costOfRenewingCertificate) external override MustBeAValidCertificationAuthority(msg.sender) returns(uint){
        uint _senderTokens = ITokenManagementContract(tokenManagementContractAddr).getTokens(msg.sender);
        require(_senderTokens >= COST_OF_ADD_CERTIFICATION_COURSE, "You do not have enough tokens to register as Certification Course");
        require(ITokenManagementContract(tokenManagementContractAddr).transfer(msg.sender, address(this), COST_OF_ADD_CERTIFICATION_COURSE), "The transfer could not be made");
        
        if(_costOfIssuingCertificate == 0)
            _costOfIssuingCertificate = ICertificationAuthorityContract(certificationAuthorityContractAddr).getDefaultCostOfIssuingCertificate(msg.sender);
        
        lastID = lastID + 1;
        certificationCourse[lastID] = CertificationCourseRecord(_name, _costOfIssuingCertificate, _costOfRenewingCertificate,
            msg.sender, _durationInHours, _expirationInDays, _canBeRenewed,  true, true);
        certificationAuthorityCourses[msg.sender].push(lastID);
        emit OnNewCertificationCourseCreated(lastID);
        return lastID;
    }

    function addCertificationCourse(string memory _name, uint _costOfIssuingCertificate, uint _durationInHours) external override MustBeAValidCertificationAuthority(msg.sender) returns(uint){
        return this.addCertificationCourse(_name, _costOfIssuingCertificate, _durationInHours, 0, false, 0);
    }
    
    function removeCertificationCourse(uint _id) external override CertificationCourseMustExist(_id) MustBeOwnerOfTheCourse(_id, msg.sender) { 
        delete certificationCourse[_id];
        emit OnCertificationCourseRemoved(_id);
    }
    
    function enableCertificationCourse(uint _id) external override CertificationCourseMustBeDisabled(_id) CertificationCourseMustExist(_id) MustBeOwnerOfTheCourse(_id, msg.sender) {
        certificationCourse[_id].isEnabled = true;
        emit OnCertificationCourseEnabled(_id);
    }
    
    function disableCertificationCourse(uint _id) external override CertificationCourseMustBeEnabled(_id) CertificationCourseMustExist(_id) MustBeOwnerOfTheCourse(_id, msg.sender) {
       certificationCourse[_id].isEnabled = false;
       emit OnCertificationCourseDisabled(_id);
    }
    
    function canBeIssued(uint _id) external view override CertificationCourseMustExist(_id) returns (bool)  {
        return certificationCourse[_id].isEnabled 
        && ICertificationAuthorityContract(certificationAuthorityContractAddr).isCertificationAuthorityExists(certificationCourse[_id].certificationAuthority)
        && ICertificationAuthorityContract(certificationAuthorityContractAddr).isCertificationAuthorityEnabled(certificationCourse[_id].certificationAuthority);
    }
    
    function canBeRenewed(uint _id) external view override CertificationCourseMustExist(_id) returns (bool)  { 
        return certificationCourse[_id].isEnabled 
        && certificationCourse[_id].canBeRenewed 
        && ICertificationAuthorityContract(certificationAuthorityContractAddr).isCertificationAuthorityExists(certificationCourse[_id].certificationAuthority)
        && ICertificationAuthorityContract(certificationAuthorityContractAddr).isCertificationAuthorityEnabled(certificationCourse[_id].certificationAuthority);
    }
    
    function isCertificationCourseExists(uint _id) external view override CertificationCourseMustExist(_id) returns (bool) {
        return certificationCourse[_id].isExist;
    }
    
    function getCostOfIssuingCertificate(uint _id) public view override CertificationCourseMustExist(_id) returns (uint) {
        return certificationCourse[_id].costOfIssuingCertificate;
    }
    
    function getCostOfRenewingCertificate(uint _id) external view override CertificationCourseMustExist(_id) returns (uint) {
        return certificationCourse[_id].costOfRenewingCertificate;
    }
    
    function getCertificateAuthorityForCourse(uint _id) external view override CertificationCourseMustExist(_id) returns (address) {
        return certificationCourse[_id].certificationAuthority;
    }
    
    function getDurationInHours(uint _id) public view override CertificationCourseMustExist(_id) returns (uint) {
        return certificationCourse[_id].durationInHours;
    }
    
    function getExpirationDate(uint _id) external view override CertificationCourseMustExist(_id) returns (uint) {
        uint _expirationDateInSeconds;
        if(certificationCourse[_id].expirationInDays == 0)
            _expirationDateInSeconds = 0;
        else
          _expirationDateInSeconds = block.timestamp + (certificationCourse[_id].expirationInDays * 24 * 60 * 60);
        return _expirationDateInSeconds;
    }
    
    function isYourOwner(uint _id, address _certificationAuthority) public view override CertificationCourseMustExist(_id) returns (bool) {
        return certificationCourse[_id].certificationAuthority == _certificationAuthority;
    }
    
    // modifiers    

    modifier CertificationCourseMustExist(uint _id) {
        require(certificationCourse[_id].isExist, "Certification Course with given id don't exists");
        _;
    }
    
    
    modifier MustBeAValidCertificationAuthority(address _certificationAuthorityAddress) {
        require(ICertificationAuthorityContract(certificationAuthorityContractAddr).isCertificationAuthorityExists(_certificationAuthorityAddress)
         && ICertificationAuthorityContract(certificationAuthorityContractAddr).isCertificationAuthorityEnabled(_certificationAuthorityAddress), "Must be a valid certification Authority");
        _;
    }
    
    modifier MustBeOwnerOfTheCourse(uint _courseId, address _certificationAuthorityAddress) {
        require(isYourOwner(_courseId, _certificationAuthorityAddress), "Certification Authority must be the owner of this course");
        _;
    }
    
    modifier CertificationCourseMustBeEnabled(uint _id) {
        require(certificationCourse[_id].isEnabled, "Certification Course must be enabled");
        _;
    }
    
    modifier CertificationCourseMustBeDisabled(uint _id) {
        require(!certificationCourse[_id].isEnabled, "Certification Course must be disabled");
        _;
    }
    
}