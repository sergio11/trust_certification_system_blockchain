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
    
    function setCertificationAuthorityAddr(address _certificationAuthorityAddr) public payable {
       certificationAuthorityAddr = _certificationAuthorityAddr;
    }

    function addCertificationCourse(string memory _id, string memory _name, uint _costOfIssuingCertificate, uint _durationInHours, string memory _certificationAuthorityId) external override restricted() CertificationCourseMustNotExist(_id) {
        require(ICertificationAuthorityContract(certificationAuthorityAddr).isCertificationAuthorityExists(_certificationAuthorityId), "Certification Authority with given id don't exists");
        require(ICertificationAuthorityContract(certificationAuthorityAddr).isCertificationAuthorityEnabled(_certificationAuthorityId), "Certification Authority with given id don't enabled");
        certificationCourse[_id] = CertificationCourseRecord(_name, _costOfIssuingCertificate, _certificationAuthorityId, _durationInHours, true, true);
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
    
    function isCertificationCourseEnabled(string memory _id) external view override CertificationCourseMustExist(_id) returns (bool)  {
        return certificationCourse[_id].isAvailable;
    }
    
    function isCertificationCourseExists(string memory _id) external view override returns (bool) {
        return certificationCourse[_id].isExist;
    }
    
    function getCertificationCourse(string memory _id) public view override returns (CertificationCourseRecord memory) {
        return certificationCourse[_id];
    }
    
    // modifier    
    modifier restricted() {
        if (msg.sender == ownerAddress) _;
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