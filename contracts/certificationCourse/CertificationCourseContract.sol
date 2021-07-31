// SPDX-License-Identifier: MIT
pragma solidity >=0.4.4 <0.7.4;
pragma experimental ABIEncoderV2;
import "./ICertificationCourseContract.sol";
import "../certificationAuthority/ICertificationAuthorityContract.sol";

contract CertificationCourseContract is ICertificationCourseContract {
    
    // Owner Direction
    address payable public ownerAddress;
    address certificationAuthorityAddr;
    
    mapping(string => CertificationCourseRecord) private certificationCourse;
    
    constructor () {
       ownerAddress = msg.sender;
    }
    
    function setCertificationAuthorityAddr(address _certificationAuthorityAddr) public payable restricted() {
       certificationAuthorityAddr = _certificationAuthorityAddr;
    }

    function addCertificationCourse(string memory _id, string memory _name, uint _costOfIssuingCertificate, uint _durationInHours, uint _expirationInDays, string memory _certificationAuthorityId) external override restricted() CertificationCourseMustNotExist(_id) {
        require(ICertificationAuthorityContract(certificationAuthorityAddr).isCertificationAuthorityExists(_certificationAuthorityId), "Certification Authority with given id don't exists");
        require(ICertificationAuthorityContract(certificationAuthorityAddr).isCertificationAuthorityEnabled(_certificationAuthorityId), "Certification Authority with given id don't enabled");
        
        if(_costOfIssuingCertificate == 0)
            _costOfIssuingCertificate = ICertificationAuthorityContract(certificationAuthorityAddr).getDefaultCostOfIssuingCertificate(_certificationAuthorityId);
        
        certificationCourse[_id] = CertificationCourseRecord(_name, _costOfIssuingCertificate, _certificationAuthorityId, _durationInHours, _expirationInDays, true, true);
        emit OnNewCertificationCourseCreated(_id);
    }
    
    function removeCertificationCourse(string memory _id) external override restricted() CertificationCourseMustExist(_id) { 
        delete certificationCourse[_id];
        emit OnCertificationCourseRemoved(_id);
    }
    
    function enableCertificationCourse(string memory _id) external override restricted() CertificationCourseMustExist(_id) {
        certificationCourse[_id].isAvailable = true;
        emit OnCertificationCourseEnabled(_id);
    }
    
    function disableCertificationCourse(string memory _id) external override restricted() CertificationCourseMustExist(_id) {
       certificationCourse[_id].isAvailable = false;
       emit OnCertificationCourseDisabled(_id);
    }
    
    function canBeIssued(string memory _id) external view override CertificationCourseMustExist(_id) returns (bool)  {
        return certificationCourse[_id].isAvailable 
        && ICertificationAuthorityContract(certificationAuthorityAddr).isCertificationAuthorityExists(certificationCourse[_id].certificationAuthorityId)
        && ICertificationAuthorityContract(certificationAuthorityAddr).isCertificationAuthorityEnabled(certificationCourse[_id].certificationAuthorityId);
    }
    
    function isCertificationCourseExists(string memory _id) external view override returns (bool) {
        return certificationCourse[_id].isExist;
    }
    
    function getCostOfIssuingCertificate(string memory _id) public view override returns (uint) {
        return certificationCourse[_id].costOfIssuingCertificate;
    }
    
    function getExpirationDate(string memory _id) external view override CertificationCourseMustExist(_id) returns (uint) {
        uint _expirationDateInSeconds;
        if(certificationCourse[_id].expirationInDays == 0)
            _expirationDateInSeconds = 0;
        else
          _expirationDateInSeconds = block.timestamp + (certificationCourse[_id].expirationInDays * 24 * 60 * 60 * 60);
        return _expirationDateInSeconds;
    }
    
    // modifiers    
    modifier restricted() {
        require(msg.sender == ownerAddress, "You don't have enought permissions to execute this operation");
        _;
    }

    modifier CertificationCourseMustExist(string memory _id) {
        require(certificationCourse[_id].isExist, "Certification Course with given id don't exists");
        _;
    }
    
    modifier CertificationCourseMustNotExist(string memory _id) {
        require(!certificationCourse[_id].isExist, "Certification Course with given id already exists");
        _;
    }
    
}