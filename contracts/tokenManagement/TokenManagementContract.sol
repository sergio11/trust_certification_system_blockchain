// SPDX-License-Identifier: MIT
pragma solidity >=0.4.4 <0.7.4;
pragma experimental ABIEncoderV2;
import "./ITokenManagementContract.sol";
import "../ERC20/ERC20.sol";

contract TokenManagementContract is ITokenManagementContract {
    
    // Token contract instance
    ERC20Basic private token;
    
    // Owner Direction
    address payable public ownerAddress;
    
    mapping(address => ClientRecord) public clients;
    
    constructor () {
        // create ERC20 token with initial supply
        token = new ERC20Basic(20000);
        ownerAddress = msg.sender;
    }
    
    function getTokenPriceInWeis(uint _tokenCount) public override pure returns (uint) {
        return _tokenCount * (25 wei);
    }
    
    function balanceOf() public override view returns (uint) {
        return token.balanceOf(address(this));
    }
    
    function getMyTokens() public override view returns (uint) {
        return token.balanceOf(msg.sender);
    }
    
    function generateTokens(uint _tokenCount) external override restricted() {
        token.increaseTotalSupply(_tokenCount);
    }
    
    function buyTokens(uint _tokenCount)  public override payable {
        require (_tokenCount <= balanceOf(), "The transaction cannot be completed the requested amount of tokens cannot be satisfied");
        uint tokenCost = getTokenPriceInWeis(_tokenCount);
        require(msg.value >= tokenCost, "Insufficient amount to buy tokens");
        msg.sender.transfer(msg.value - tokenCost);
        token.transfer(msg.sender, _tokenCount);
        clients[msg.sender].tokensPurchasedCount += _tokenCount;
        clients[msg.sender].tokensAvailables += _tokenCount;
    }
    
     // Modifiers
    modifier restricted() {
        if (msg.sender == ownerAddress) _;
    }
    
    
}