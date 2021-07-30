pragma solidity >=0.4.4 <0.7.4;
pragma experimental ABIEncoderV2;
import "./ERC20/ERC20.sol";
import "./ITokenManagementContract.sol";


interface ITrustCertificationContract {
    
    
    // Events Definitions
    event OnNewCertificateGenerated(bytes32 _certificateId);
    
}


contract TrustCertificationContract is ITrustCertificationContract, ITokenManagementContract {
    
    // Token contract instance
    ERC20Basic private token;
    
    // Owner Direction
    address payable public ownerAddress;
    
    constructor () public {
        // create ERC20 token with initial supply
        token = new ERC20Basic(20000);
        ownerAddress = msg.sender;
    }
    
    struct StudentRecord {
        uint tokensPurchasedCount;
        uint tokensAvailables;
    }
    
    mapping(address => StudentRecord) public students;
    
    
    // Token management
    function getTokenPriceInWeis(uint _tokenCount) public override pure returns (uint) {
        return _tokenCount * (25 wei);
    }
    
    function balanceOf() public override view returns (uint) {
        return token.balanceOf(address(this));
    }
    
    function buyTokens(uint _tokenCount)  public override payable {
        require (_tokenCount <= balanceOf(), "The transaction cannot be completed the requested amount of tokens cannot be satisfied");
        uint tokenCost = getTokenPriceInWeis(_tokenCount);
        require(msg.value >= tokenCost, "Insufficient amount to buy tokens");
        msg.sender.transfer(msg.value - tokenCost);
        token.transfer(msg.sender, _tokenCount);
        students[msg.sender].tokensPurchasedCount += _tokenCount;
        students[msg.sender].tokensAvailables += _tokenCount;
    }
    
    
    
}