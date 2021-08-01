// SPDX-License-Identifier: MIT
pragma solidity >=0.4.4 <0.7.4;
pragma experimental ABIEncoderV2;

interface ICertificationCourseContract { 
    
    function addCertificationCourse(string memory _name, uint _costOfIssuingCertificate, uint _durationInHours, uint _expirationInDays) external returns (uint);
    function removeCertificationCourse(uint _id) external;
    function enableCertificationCourse(uint _id) external;
    function disableCertificationCourse(uint _id) external;
    function canBeIssued(uint _id) external view returns (bool);
    function isCertificationCourseExists(uint _id) external view returns (bool);
    function getCostOfIssuingCertificate(uint _id) external view returns (uint);
    function getDurationInHours(uint _id) external view returns (uint);
    function getExpirationDate(uint _id) external view returns (uint);
    function isYourOwner(uint _id, address _certificationAuthority) external view returns (bool);
    function getMyCertificationCourses() external view returns (CertificationCourseRecord[] memory);
    
    // Data Structure
    struct CertificationCourseRecord {
        string name;
        uint costOfIssuingCertificate;
        address certificationAuthority;
        uint durationInHours;
        uint expirationInDays;
        bool isEnabled;
        bool isExist;
    }
    
    // Events Definitions
    event OnNewCertificationCourseCreated(uint _id);
    event OnCertificationCourseRemoved(uint _id);
    event OnCertificationCourseEnabled(uint _id);
    event OnCertificationCourseDisabled(uint _id);
}