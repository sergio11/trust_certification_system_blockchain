// SPDX-License-Identifier: MIT
pragma solidity >=0.4.4 <0.7.4;
pragma experimental ABIEncoderV2;

interface ITokenManagementContract {
    
    function getTokenPriceInWeis(uint _tokenCount) external pure returns (uint);
    function buyTokens(uint _tokenCount) external payable;
    function balanceOf() external view returns (uint);
    function getMyTokens() external view returns (uint);
    function generateTokens(uint _tokenCount) external;
    
    // Data Structure
    struct ClientRecord {
        uint tokensPurchasedCount;
        uint tokensAvailables;
    }
    
}