// SPDX-License-Identifier: MIT
pragma solidity >=0.4.4 <0.7.4;
pragma experimental ABIEncoderV2;
import "../ownable/Ownable.sol";
import "./ICertificationCourseContract.sol";
import "../tokenManagement/ITokenManagementContract.sol";
import "../certificationAuthority/ICertificationAuthorityContract.sol";

contract CertificationCourseContract is Ownable, ICertificationCourseContract {
    
    address private tokenManagementContractAddr;
    address private certificationAuthorityContractAddr;
    
    uint8 public constant costOfAddCertificationCourse = 10;
    
    mapping(string => CertificationCourseRecord) private certificationCourse;
    mapping(address => string) certificationAuthorityCourses;
    
    function setCertificationAuthorityContractAddr(address _certificationAuthorityContractAddr) public payable onlyOwner() {
       certificationAuthorityContractAddr = _certificationAuthorityContractAddr;
    }
    
    function setTokenManagementAddr(address _tokenManagementContractAddr) public payable onlyOwner() {
       tokenManagementContractAddr = _tokenManagementContractAddr;
    }

    function addCertificationCourse(string memory _id, string memory _name, uint _costOfIssuingCertificate, uint _durationInHours, uint _expirationInDays) external override MustBeAValidCertificationAuthority(msg.sender) CertificationCourseMustNotExist(_id) {
        uint _senderTokens = ITokenManagementContract(tokenManagementContractAddr).getTokens(msg.sender);
        require(_senderTokens >= costOfAddCertificationCourse, "You do not have enough tokens to register as Certification Course");
        require(ITokenManagementContract(tokenManagementContractAddr).transfer(msg.sender, address(this), costOfAddCertificationCourse), "The transfer could not be made");
        
        if(_costOfIssuingCertificate == 0)
            _costOfIssuingCertificate = ICertificationAuthorityContract(certificationAuthorityContractAddr).getDefaultCostOfIssuingCertificate(msg.sender);
        
        certificationCourse[_id] = CertificationCourseRecord(_name, _costOfIssuingCertificate, msg.sender, _durationInHours, _expirationInDays, true, true);
        certificationAuthorityCourses[msg.sender] = _id;
        emit OnNewCertificationCourseCreated(_id);
    }
    
    function removeCertificationCourse(string memory _id) external override CertificationCourseMustExist(_id) MustBeOwnerOfTheCourse(_id, msg.sender) { 
        delete certificationCourse[_id];
        emit OnCertificationCourseRemoved(_id);
    }
    
    function enableCertificationCourse(string memory _id) external override CertificationCourseMustExist(_id) MustBeOwnerOfTheCourse(_id, msg.sender) {
        certificationCourse[_id].isAvailable = true;
        emit OnCertificationCourseEnabled(_id);
    }
    
    function disableCertificationCourse(string memory _id) external override CertificationCourseMustExist(_id) MustBeOwnerOfTheCourse(_id, msg.sender) {
       certificationCourse[_id].isAvailable = false;
       emit OnCertificationCourseDisabled(_id);
    }
    
    function canBeIssued(string memory _id) external view override CertificationCourseMustExist(_id) returns (bool)  {
        return certificationCourse[_id].isAvailable 
        && ICertificationAuthorityContract(certificationAuthorityContractAddr).isCertificationAuthorityExists(certificationCourse[_id].certificationAuthority)
        && ICertificationAuthorityContract(certificationAuthorityContractAddr).isCertificationAuthorityEnabled(certificationCourse[_id].certificationAuthority);
    }
    
    function isCertificationCourseExists(string memory _id) external view override returns (bool) {
        return certificationCourse[_id].isExist;
    }
    
    function getCostOfIssuingCertificate(string memory _id) public view override returns (uint) {
        return certificationCourse[_id].costOfIssuingCertificate;
    }
    
    function getDurationInHours(string memory _id) public view override returns (uint) {
        return certificationCourse[_id].durationInHours;
    }
    
    function getExpirationDate(string memory _id) external view override CertificationCourseMustExist(_id) returns (uint) {
        uint _expirationDateInSeconds;
        if(certificationCourse[_id].expirationInDays == 0)
            _expirationDateInSeconds = 0;
        else
          _expirationDateInSeconds = block.timestamp + (certificationCourse[_id].expirationInDays * 24 * 60 * 60 * 60);
        return _expirationDateInSeconds;
    }
    
    function isYourOwner(string memory _id, address _certificationAuthority) public view override returns (bool) {
        return certificationCourse[_id].certificationAuthority == _certificationAuthority;
    }
    
    // modifiers    

    modifier CertificationCourseMustExist(string memory _id) {
        require(certificationCourse[_id].isExist, "Certification Course with given id don't exists");
        _;
    }
    
    modifier CertificationCourseMustNotExist(string memory _id) {
        require(!certificationCourse[_id].isExist, "Certification Course with given id already exists");
        _;
    }
    
    modifier MustBeAValidCertificationAuthority(address _certificationAuthorityAddress) {
        require(ICertificationAuthorityContract(certificationAuthorityContractAddr).isCertificationAuthorityExists(_certificationAuthorityAddress)
         && ICertificationAuthorityContract(certificationAuthorityContractAddr).isCertificationAuthorityEnabled(_certificationAuthorityAddress), "Must be a valid certification Authority");
        _;
    }
    
    modifier MustBeOwnerOfTheCourse(string memory _courseId, address _certificationAuthorityAddress) {
        require(isYourOwner(_courseId, _certificationAuthorityAddress), "Certification Authority must be the owner of this course");
        _;
    }
    
}