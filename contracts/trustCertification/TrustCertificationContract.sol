// SPDX-License-Identifier: MIT
pragma solidity >=0.4.4 <0.7.4;
pragma experimental ABIEncoderV2;
import "../ownable/Ownable.sol";
import "./ITrustCertificationContract.sol";
import "../tokenManagement/ITokenManagementContract.sol";
import "../certificationCourse/ICertificationCourseContract.sol";

contract TrustCertificationContract is Ownable, ITrustCertificationContract {
    
    address private tokenManagementAddr;
    address private certificationCourseAddr;
    
    mapping(string => CertificateRecord) private certificates;
    mapping(address => string) certificatesByIssuer;
    mapping(address => string) certificatesByRecipient;
    
    function setTokenManagementAddr(address _tokenManagementAddr) public payable onlyOwner() {
       tokenManagementAddr = _tokenManagementAddr;
    }
    
    function setCertificationCourseAddr(address _certificationCourseAddr) public payable onlyOwner() {
       certificationCourseAddr = _certificationCourseAddr;
    }
    
    function issueCertificate(address _recipientAddress, string memory _id, string memory _certificateCourseId, uint _qualification) external override CertificateMustNotExist(_id) IssuerMustBeOwnerOfTheCourse(_certificateCourseId, msg.sender) {
        require(ICertificationCourseContract(certificationCourseAddr).isCertificationCourseExists(_certificateCourseId), "Certification Course with given id don't exists");
        require(ICertificationCourseContract(certificationCourseAddr).canBeIssued(_certificateCourseId), "Certification Course with given id can not be issued");
        uint _costOfIssuingCertificate = ICertificationCourseContract(certificationCourseAddr).getCostOfIssuingCertificate(_certificateCourseId);
        uint _durationInHours =  ICertificationCourseContract(certificationCourseAddr).getDurationInHours(_certificateCourseId);
        uint _recipientAddressTokens = ITokenManagementContract(tokenManagementAddr).getTokens(_recipientAddress);
        require(_costOfIssuingCertificate <= _recipientAddressTokens, "You do not have enough tokens to issue the certificate");
        require(ITokenManagementContract(tokenManagementAddr).transfer(_recipientAddress, msg.sender, _costOfIssuingCertificate), "The transfer could not be made");
        certificates[_id] = CertificateRecord(msg.sender, _recipientAddress, _certificateCourseId, ICertificationCourseContract(certificationCourseAddr).getExpirationDate(_certificateCourseId) , _qualification, _durationInHours, block.timestamp, true, true);
        certificatesByIssuer[msg.sender] = _id;
        certificatesByRecipient[_recipientAddress] = _id;
        emit OnNewCertificateGenerated(_id);
    }
    
    function enableCertificate(string memory _id) external override CertificateMustExist(_id) { 
        certificates[_id].isEnabled = true;
        emit OnCertificateEnabled(_id);
    }
    
    function disableCertificate(string memory _id) external override onlyOwner() CertificateMustExist(_id) { 
        certificates[_id].isEnabled = false;
        emit OnCertificateDisabled(_id);
    }
    
    function isCertificateValid(string memory _id) external view override CertificateMustExist(_id) returns (bool) {
        return certificates[_id].isExist && certificates[_id].isEnabled && (certificates[_id].expirationDate == 0 ||
         certificates[_id].expirationDate > 0 &&  block.timestamp < certificates[_id].expirationDate);
    }

    
     // Modifiers

    modifier CertificateMustExist(string memory _id) {
        require(certificates[_id].isExist, "Certification with given id don't exists");
        _;
    }
    
    modifier CertificateMustNotExist(string memory _id) {
        require(!certificates[_id].isExist, "Certification with given id already exists");
        _;
    }
    
    modifier IssuerMustBeOwnerOfTheCourse(string memory _courseId, address _certificationAuthorityAddress) {
        require(ICertificationCourseContract(certificationCourseAddr).isYourOwner(_courseId, _certificationAuthorityAddress), "Certification Authority must be the owner of this course");
        _;
    }
    
}