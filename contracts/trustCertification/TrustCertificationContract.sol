// SPDX-License-Identifier: MIT
pragma solidity >=0.4.4 <0.7.4;
pragma experimental ABIEncoderV2;
import "./ITrustCertificationContract.sol";

contract TrustCertificationContract is ITrustCertificationContract {
    
    address tokenManagementAddr;
    address certificationCourseAddr;
    
    function setTokenManagementAddr(address _tokenManagementAddr) public payable {
       tokenManagementAddr = _tokenManagementAddr;
    }
    
    function setCertificationCourseAddr(address _certificationCourseAddr) public payable {
       certificationCourseAddr = _certificationCourseAddr;
    }
    
}