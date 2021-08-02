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
    
    mapping(uint => CertificateRecord) private certificates;
    mapping(address => uint[]) private certificatesByIssuer;
    mapping(address => uint[]) private certificatesByRecipient;
    uint private lastID;
    
    function setTokenManagementAddr(address _tokenManagementAddr) public payable onlyOwner() {
       tokenManagementAddr = _tokenManagementAddr;
    }
    
    function setCertificationCourseAddr(address _certificationCourseAddr) public payable onlyOwner() {
       certificationCourseAddr = _certificationCourseAddr;
    }
    
    function issueCertificate(address _recipientAddress, uint _certificateCourseId, uint _qualification) external override IssuerMustBeOwnerOfTheCourse(_certificateCourseId, msg.sender) returns(uint) {
        require(ICertificationCourseContract(certificationCourseAddr).isCertificationCourseExists(_certificateCourseId), "Certification Course with given id don't exists");
        require(ICertificationCourseContract(certificationCourseAddr).canBeIssued(_certificateCourseId), "Certification Course with given id can not be issued");
        uint _costOfIssuingCertificate = ICertificationCourseContract(certificationCourseAddr).getCostOfIssuingCertificate(_certificateCourseId);
        uint _durationInHours =  ICertificationCourseContract(certificationCourseAddr).getDurationInHours(_certificateCourseId);
        uint _recipientAddressTokens = ITokenManagementContract(tokenManagementAddr).getTokens(_recipientAddress);
        require(_costOfIssuingCertificate <= _recipientAddressTokens, "You do not have enough tokens to issue the certificate");
        require(ITokenManagementContract(tokenManagementAddr).transfer(_recipientAddress, msg.sender, _costOfIssuingCertificate), "The transfer could not be made");
        lastID = lastID + 1;
        certificates[lastID] = CertificateRecord(msg.sender, _recipientAddress, _certificateCourseId, ICertificationCourseContract(certificationCourseAddr).getExpirationDate(_certificateCourseId) , _qualification, _durationInHours, block.timestamp, true, true, true);
        certificatesByIssuer[msg.sender].push(lastID);
        certificatesByRecipient[_recipientAddress].push(lastID);
        emit OnNewCertificateGenerated(lastID);
        return lastID;
    }
    
    function renewCertificate(uint _id) external override CertificateMustExist(_id)  MustBeOwnerOfTheCertificate(msg.sender, _id) CertificateMustBeExpired(_id) {
        CertificateRecord memory certificate = certificates[_id];
        require(ICertificationCourseContract(certificationCourseAddr).isCertificationCourseExists(certificate.certificateCourseId), "Certification Course for given certificate id don't exists");
        require(ICertificationCourseContract(certificationCourseAddr).canBeRenewed(certificate.certificateCourseId), "Certification Course for given certificate id can not be renewed");
        uint _costOfRenewingCertificate = ICertificationCourseContract(certificationCourseAddr).getCostOfRenewingCertificate(certificate.certificateCourseId);
        uint _recipientAddressTokens = ITokenManagementContract(tokenManagementAddr).getTokens(msg.sender);
        require(_costOfRenewingCertificate <= _recipientAddressTokens, "You do not have enough tokens to renew the certificate");
        require(ITokenManagementContract(tokenManagementAddr).transfer(msg.sender, ICertificationCourseContract(certificationCourseAddr).getCertificateAuthorityForCourse(certificate.certificateCourseId), _costOfRenewingCertificate), "The transfer could not be made");
        certificate.expirationDate = ICertificationCourseContract(certificationCourseAddr).getExpirationDate(certificate.certificateCourseId);
        emit OnCertificateRenewed(_id);
    }
    
    function enableCertificate(uint _id) external override CertificateMustExist(_id) MustBeOwnerOfTheCertificate(msg.sender, _id) { 
        certificates[_id].isEnabled = true;
        emit OnCertificateEnabled(_id);
    }
    
    function disableCertificate(uint _id) external override CertificateMustExist(_id) MustBeOwnerOfTheCertificate(msg.sender, _id) { 
        certificates[_id].isEnabled = false;
        emit OnCertificateDisabled(_id);
    }
    
    function updateCertificateVisibility(uint _id, bool _isVisible) external override CertificateMustExist(_id) MustBeOwnerOfTheCertificate(msg.sender, _id) { 
        certificates[_id].isVisible = _isVisible;
        emit OnCertificateVisibilityUpdated(_id, _isVisible);
    }
    
    function isCertificateValid(uint _id) external view override CertificateMustExist(_id) CertificateMustVisible(_id) returns (bool) {
        return certificates[_id].isExist && certificates[_id].isEnabled && (certificates[_id].expirationDate == 0 ||
         certificates[_id].expirationDate > 0 &&  block.timestamp < certificates[_id].expirationDate);
    }
    
     function getMyCertificatesAsRecipient() public view override returns (CertificateRecord[] memory) {
        CertificateRecord[] memory  myCertificates = new CertificateRecord[](certificatesByRecipient[msg.sender].length);
        for (uint i=0; i < certificatesByRecipient[msg.sender].length; i++) { 
            myCertificates[i] = certificates[certificatesByRecipient[msg.sender][i]];
        }
        return myCertificates;
    }
    
    function getMyCertificatesAsIssuer() external view override returns (CertificateRecord[] memory) { 
         CertificateRecord[] memory  myCertificates = new CertificateRecord[](certificatesByIssuer[msg.sender].length);
        for (uint i=0; i < certificatesByIssuer[msg.sender].length; i++) { 
            myCertificates[i] = certificates[certificatesByIssuer[msg.sender][i]];
        }
        return myCertificates;
    }
    
     // Modifiers

    modifier CertificateMustExist(uint _id) {
        require(certificates[_id].isExist, "Certification with given id don't exists");
        _;
    }
    
    modifier CertificateMustBeExpired(uint _id) {
        require(certificates[_id].isExist, "Certification with given id has not expired");
        _;
    }
    
    modifier CertificateMustNotExist(uint _id) {
        require(!certificates[_id].isExist, "Certification with given id already exists");
        _;
    }
    
    modifier CertificateMustVisible(uint _id) {
        require(certificates[_id].isVisible, "Certification with given id is not visible");
        _;
    }
    
    modifier MustBeOwnerOfTheCertificate(address _ownerAddress, uint _id) {
        require(certificates[_id].recipientAddress == _ownerAddress, "Must be the owner of this certificate");
        _;
    }
    
    modifier IssuerMustBeOwnerOfTheCourse(uint _courseId, address _certificationAuthorityAddress) {
        require(ICertificationCourseContract(certificationCourseAddr).isYourOwner(_courseId, _certificationAuthorityAddress), "Certification Authority must be the owner of this course");
        _;
    }
    
}