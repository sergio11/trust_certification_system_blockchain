// SPDX-License-Identifier: MIT
pragma solidity >=0.4.4 <0.7.4;
pragma experimental ABIEncoderV2;

interface ICertificationCourseContract { 
    
    function addCertificationCourse(string memory _id, string memory _name, uint _costOfIssuingCertificate, uint _durationInHours, string memory _certificationAuthorityId) external;
    function removeCertificationCourse(string memory _id) external;
    function enableCertificationCourse(string memory _id) external;
    function disableCertificationCourse(string memory _id) external;
    function isCertificationCourseEnabled(string memory _id) external view returns (bool);
    function isCertificationCourseExists(string memory _id) external view returns (bool);
    function getCertificationCourse(string memory _id) external view returns (CertificationCourseRecord memory);
    
    // Data Structure
    struct CertificationCourseRecord {
        string name;
        uint costOfIssuingCertificate;
        string certificationAuthorityId;
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