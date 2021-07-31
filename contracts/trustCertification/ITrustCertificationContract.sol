// SPDX-License-Identifier: MIT
pragma solidity >=0.4.4 <0.7.4;
pragma experimental ABIEncoderV2;


interface ITrustCertificationContract { 
    
    
    // Data Structure
    struct CertificateRecord {
        string certificateCourseId;
        uint256 expirationDate;
        uint qualification;
        uint durationInHours;
        uint256 expeditionDate;
        bool isAvailable;
        bool isExist;
    }

    
    // Events
    event OnNewCertificateGenerated(string _id);
    event OnCertificateDeleted(string _id);
    
}