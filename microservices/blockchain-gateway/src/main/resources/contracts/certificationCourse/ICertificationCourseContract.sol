// SPDX-License-Identifier: MIT
pragma solidity ^0.7.1;
pragma experimental ABIEncoderV2;


interface ICertificationCourseContract { 
    
    function addCertificationCourse(string memory _name, uint _costOfIssuingCertificate, uint _durationInHours) external returns (uint);
    function addCertificationCourse(string memory _name, uint _costOfIssuingCertificate, uint _durationInHours, uint _expirationInDays, bool _canBeRenewed, uint _costOfRenewingCertificate) external returns (uint);
    function removeCertificationCourse(uint _id) external;
    function enableCertificationCourse(uint _id) external;
    function disableCertificationCourse(uint _id) external;
    function canBeIssued(uint _id) external view returns (bool);
    function canBeRenewed(uint _id) external view returns (bool);
    function isCertificationCourseExists(uint _id) external view returns (bool);
    function getCostOfIssuingCertificate(uint _id) external view returns (uint);
    function getCostOfRenewingCertificate(uint _id) external view returns (uint);
    function getCertificateAuthorityForCourse(uint _id) external view returns (address);
    function getDurationInHours(uint _id) external view returns (uint);
    function getExpirationDate(uint _id) external view returns (uint);
    function isYourOwner(uint _id, address _certificationAuthority) external view returns (bool);
    
    // Data Structure
    struct CertificationCourseRecord {
        string name;
        uint costOfIssuingCertificate;
        uint costOfRenewingCertificate;
        address certificationAuthority;
        uint durationInHours;
        uint expirationInDays;
        bool canBeRenewed;
        bool isEnabled;
        bool isExist;
    }
    
    // Events Definitions
    event OnNewCertificationCourseCreated(uint _id);
    event OnCertificationCourseRemoved(uint _id);
    event OnCertificationCourseEnabled(uint _id);
    event OnCertificationCourseDisabled(uint _id);
}


