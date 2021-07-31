// SPDX-License-Identifier: MIT
pragma solidity >=0.4.4 <0.7.4;
pragma experimental ABIEncoderV2;
import "./ITrustCertificationContract.sol";
import "../tokenManagement/ITokenManagementContract.sol";
import "../certificationCourse/ICertificationCourseContract.sol";

contract TrustCertificationContract is ITrustCertificationContract {
    
    address tokenManagementAddr;
    address certificationCourseAddr;
    // Owner Direction
    address payable public ownerAddress;
    
    mapping(string => CertificateRecord) private certificates;
    
    constructor () {
       ownerAddress = msg.sender;
    }
    
    function setTokenManagementAddr(address _tokenManagementAddr) public payable restricted() {
       tokenManagementAddr = _tokenManagementAddr;
    }
    
    function setCertificationCourseAddr(address _certificationCourseAddr) public payable restricted() {
       certificationCourseAddr = _certificationCourseAddr;
    }
    
    function addCertificate(string memory _id, string memory _certificateCourseId, uint _qualification,  uint _durationInHours) external override CertificateMustNotExist(_id) {
        require(ICertificationCourseContract(certificationCourseAddr).isCertificationCourseExists(_certificateCourseId), "Certification Course with given id don't exists");
        require(ICertificationCourseContract(certificationCourseAddr).canBeIssued(_certificateCourseId), "Certification Course with given id can not be issued");
        uint costOfIssuingCertificate = ICertificationCourseContract(certificationCourseAddr).getCostOfIssuingCertificate(_certificateCourseId);
        uint myTokens = ITokenManagementContract(tokenManagementAddr).getTokens(msg.sender);
        require(costOfIssuingCertificate <= myTokens, "You do not have enough tokens to issue the certificate");
        require(ITokenManagementContract(tokenManagementAddr).transfer(msg.sender, address(this), costOfIssuingCertificate), "The transfer could not be made");
        certificates[_id] = CertificateRecord(_certificateCourseId, ICertificationCourseContract(certificationCourseAddr).getExpirationDate(_certificateCourseId) , _qualification, _durationInHours, block.timestamp, true, true);
        emit OnNewCertificateGenerated(_id);
    }
    
    function enableCertificate(string memory _id) external override restricted() CertificateMustExist(_id) { 
        certificates[_id].isEnabled = true;
        emit OnCertificateEnabled(_id);
    }
    
    function disableCertificate(string memory _id) external override restricted() CertificateMustExist(_id) { 
        certificates[_id].isEnabled = false;
        emit OnCertificateDisabled(_id);
    }
    
    function isCertificateValid(string memory _id) external view override CertificateMustExist(_id) returns (bool) {
        return certificates[_id].isExist && certificates[_id].isEnabled && (certificates[_id].expirationDate == 0 ||
         certificates[_id].expirationDate > 0 &&  block.timestamp < certificates[_id].expirationDate);
    }

    
     // Modifiers
    modifier restricted() {
        require(msg.sender == ownerAddress, "You don't have enought permissions to execute this operation");
        _;
    }

    modifier CertificateMustExist(string memory _id) {
        require(certificates[_id].isExist, "Certification with given id don't exists");
        _;
    }
    
    modifier CertificateMustNotExist(string memory _id) {
        require(!certificates[_id].isExist, "Certification with given id already exists");
        _;
    }
}