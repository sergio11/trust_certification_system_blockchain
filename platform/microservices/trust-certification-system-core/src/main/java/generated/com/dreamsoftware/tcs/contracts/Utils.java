package com.dreamsoftware.tcs.contracts;

import java.math.BigInteger;
import java.util.Arrays;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.Utf8String;
import org.web3j.abi.datatypes.generated.Bytes1;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.RemoteCall;
import org.web3j.protocol.core.RemoteFunctionCall;
import org.web3j.tx.Contract;
import org.web3j.tx.TransactionManager;
import org.web3j.tx.gas.ContractGasProvider;

/**
 * <p>Auto generated code.
 * <p><strong>Do not modify!</strong>
 * <p>Please use the <a href="https://docs.web3j.io/command_line.html">web3j command line tools</a>,
 * or the org.web3j.codegen.SolidityFunctionWrapperGenerator in the 
 * <a href="https://github.com/web3j/web3j/tree/master/codegen">codegen module</a> to update.
 *
 * <p>Generated with web3j version 4.8.4.
 */
@SuppressWarnings("rawtypes")
public class Utils extends Contract {
    public static final String BINARY = "61028d610026600b82828239805160001a60731461001957fe5b30600052607381538281f3fe73000000000000000000000000000000000000000030146080604052600436106100405760003560e01c80639201de5514610045578063a86b73f01461006e575b600080fd5b6100586100533660046101af565b61008e565b6040516100659190610204565b60405180910390f35b61008161007c3660046101c7565b610187565b60405161006591906101ef565b6040805181815260608181018352916000918391602082018180368337019050509050600091505b80518260ff16101561017e57600084600260ff85160460ff16602081106100d957fe5b1a600f1690506000600486600260ff87160460ff16602081106100f857fe5b1a60f81b6001600160f81b031916901c60f81c905061011682610187565b838560ff168151811061012557fe5b60200101906001600160f81b031916908160001a90535083600101935061014b81610187565b838560ff168151811061015a57fe5b60200101906001600160f81b031916908160001a90535050600190920191506100b6565b9150505b919050565b6000600a8260ff1610156101a257506030810160f81b610182565b506057810160f81b610182565b6000602082840312156101c0578081fd5b5035919050565b6000602082840312156101d8578081fd5b813560ff811681146101e8578182fd5b9392505050565b6001600160f81b031991909116815260200190565b6000602080835283518082850152825b8181101561023057858101830151858201604001528201610214565b818111156102415783604083870101525b50601f01601f191692909201604001939250505056fea26469706673582212204a345ff76a9552f504432e7aa41ab4648257e92c7c8d267820fa2ccc681c35ac64736f6c63430007010033";

    public static final String FUNC_BYTES32TOSTRING = "bytes32ToString";

    public static final String FUNC_TOBYTE = "toByte";

    @Deprecated
    protected Utils(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected Utils(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, credentials, contractGasProvider);
    }

    @Deprecated
    protected Utils(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    protected Utils(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public RemoteFunctionCall<String> bytes32ToString(byte[] _bytes32) {
        final Function function = new Function(FUNC_BYTES32TOSTRING, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Bytes32(_bytes32)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteFunctionCall<byte[]> toByte(BigInteger _uint8) {
        final Function function = new Function(FUNC_TOBYTE, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint8(_uint8)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Bytes1>() {}));
        return executeRemoteCallSingleValueReturn(function, byte[].class);
    }

    @Deprecated
    public static Utils load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new Utils(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    @Deprecated
    public static Utils load(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new Utils(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public static Utils load(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return new Utils(contractAddress, web3j, credentials, contractGasProvider);
    }

    public static Utils load(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return new Utils(contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static RemoteCall<Utils> deploy(Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return deployRemoteCall(Utils.class, web3j, credentials, contractGasProvider, BINARY, "");
    }

    @Deprecated
    public static RemoteCall<Utils> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(Utils.class, web3j, credentials, gasPrice, gasLimit, BINARY, "");
    }

    public static RemoteCall<Utils> deploy(Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return deployRemoteCall(Utils.class, web3j, transactionManager, contractGasProvider, BINARY, "");
    }

    @Deprecated
    public static RemoteCall<Utils> deploy(Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(Utils.class, web3j, transactionManager, gasPrice, gasLimit, BINARY, "");
    }
}
