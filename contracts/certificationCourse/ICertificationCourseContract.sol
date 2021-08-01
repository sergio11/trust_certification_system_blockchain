// SPDX-License-Identifier: MIT
pragma solidity >=0.4.4 <0.7.4;
pragma experimental ABIEncoderV2;

interface ICertificationCourseContract { 
    
    function addCertificationCourse(string memory _id, string memory _name, uint _costOfIssuingCertificate, uint _durationInHours, uint _expirationInDays) external;
    function removeCertificationCourse(string memory _id) external;
    function enableCertificationCourse(string memory _id) external;
    function disableCertificationCourse(string memory _id) external;
    function canBeIssued(string memory _id) external view returns (bool);
    function isCertificationCourseExists(string memory _id) external view returns (bool);
    function getCostOfIssuingCertificate(string memory _id) external view returns (uint);
    function getDurationInHours(string memory _id) external view returns (uint);
    function getExpirationDate(string memory _id) external view returns (uint);
    function isYourOwner(string memory _id, address _certificationAuthority) external view returns (bool);
    
    // Data Structure
    struct CertificationCourseRecord {
        string name;
        uint costOfIssuingCertificate;
        address certificationAuthority;
        uint durationInHours;
        uint expirationInDays;
        bool isAvailable;
        bool isExist;
    }
    
    // Events Definitions
    event OnNewCertificationCourseCreated(string _id);
    event OnCertificationCourseRemoved(string _id);
    event OnCertificationCourseEnabled(string _id);
    event OnCertificationCourseDisabled(string _id);
}