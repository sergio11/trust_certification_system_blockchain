// SPDX-License-Identifier: MIT
pragma solidity ^0.7.1;
pragma experimental ABIEncoderV2;

contract EtherFaucetContract {
    
    uint private topupAmountInEthers = 5 ether;

    function getSeedFunds() public payable{
        msg.sender.transfer(topupAmountInEthers);
    }

    function sendSeedFundsTo(address payable account) public  {
        account.transfer(topupAmountInEthers);
    }
    
    function sendFunds(address payable account, uint amount) public  {
        account.transfer(amount);
    }
}