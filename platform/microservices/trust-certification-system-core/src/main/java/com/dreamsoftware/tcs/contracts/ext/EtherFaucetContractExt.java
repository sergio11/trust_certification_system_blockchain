package com.dreamsoftware.tcs.contracts.ext;

import com.dreamsoftware.tcs.contracts.EtherFaucetContract;
import java.math.BigInteger;
import org.web3j.crypto.Credentials;
import java.util.Arrays;
import java.util.Collections;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.RemoteFunctionCall;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tx.TransactionManager;
import org.web3j.tx.gas.ContractGasProvider;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Type;

/**
 *
 * @author ssanchez
 */
public final class EtherFaucetContractExt extends EtherFaucetContract {

    public EtherFaucetContractExt(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected EtherFaucetContractExt(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        super(contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public RemoteFunctionCall<TransactionReceipt> deposit(final BigInteger weiValue) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_DEPOSIT,
                Arrays.<Type>asList(),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function, weiValue);
    }

    public static EtherFaucetContractExt load(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return new EtherFaucetContractExt(contractAddress, web3j, transactionManager, contractGasProvider);
    }

}
