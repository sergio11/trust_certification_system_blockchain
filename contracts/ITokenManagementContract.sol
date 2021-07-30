pragma solidity >=0.4.4 <0.7.4;
pragma experimental ABIEncoderV2;

interface ITokenManagementContract {
    
    function getTokenPriceInWeis(uint _tokenCount) external pure returns (uint);
    function buyTokens(uint _tokenCount) external payable;
    function balanceOf() external view returns (uint);
}