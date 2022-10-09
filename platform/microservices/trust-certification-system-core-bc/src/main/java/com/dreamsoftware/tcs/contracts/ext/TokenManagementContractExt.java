package com.dreamsoftware.tcs.contracts.ext;

import com.dreamsoftware.tcs.contracts.TokenManagementContract;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.RemoteFunctionCall;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tx.TransactionManager;
import org.web3j.tx.gas.ContractGasProvider;

import java.math.BigInteger;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author ssanchez
 */
public class TokenManagementContractExt extends TokenManagementContract {
    protected TokenManagementContractExt(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        super(contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public RemoteFunctionCall<TransactionReceipt> buyTokens(BigInteger _tokenCount, BigInteger weiValue) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_BUYTOKENS,
                List.of(new Uint256(_tokenCount)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function, weiValue);
    }

    public static TokenManagementContractExt load(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return new TokenManagementContractExt(contractAddress, web3j, transactionManager, contractGasProvider);
    }
}
