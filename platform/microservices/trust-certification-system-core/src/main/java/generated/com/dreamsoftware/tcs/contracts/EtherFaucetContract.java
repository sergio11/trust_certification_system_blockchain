package com.dreamsoftware.tcs.contracts;

import io.reactivex.Flowable;
import io.reactivex.functions.Function;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.web3j.abi.EventEncoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.Event;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameter;
import org.web3j.protocol.core.RemoteCall;
import org.web3j.protocol.core.RemoteFunctionCall;
import org.web3j.protocol.core.methods.request.EthFilter;
import org.web3j.protocol.core.methods.response.BaseEventResponse;
import org.web3j.protocol.core.methods.response.Log;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
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
public class EtherFaucetContract extends Contract {
    public static final String BINARY = "60806040526729a2241af62c000060015534801561001c57600080fd5b50600080546001600160a01b031916331790556106978061003e6000396000f3fe6080604052600436106100705760003560e01c80636f64234e1161004e5780636f64234e146100e4578063d0e30db014610104578063e522538114610119578063f2fde38b1461012e57610070565b806312065fe0146100755780631b853182146100a057806323111a0d146100c2575b600080fd5b34801561008157600080fd5b5061008a61014e565b6040516100979190610658565b60405180910390f35b3480156100ac57600080fd5b506100b5610187565b604051610097919061053d565b3480156100ce57600080fd5b506100e26100dd3660046104f1565b610196565b005b3480156100f057600080fd5b506100e26100ff366004610513565b6102b9565b61010c61036b565b604051610097919061058e565b34801561012557600080fd5b506100e26103d5565b34801561013a57600080fd5b506100e26101493660046104f1565b61043c565b600080546001600160a01b031633146101825760405162461bcd60e51b8152600401610179906105fb565b60405180910390fd5b504790565b6000546001600160a01b031690565b6000546001600160a01b031633146101c05760405162461bcd60e51b8152600401610179906105fb565b600047116101e05760405162461bcd60e51b8152600401610179906105d0565b6001600160a01b038116600090815260026020526040902054819060ff161561021b5760405162461bcd60e51b815260040161017990610599565b6001546040516001600160a01b0384169180156108fc02916000818181858888f19350505050158015610252573d6000803e3d6000fd5b506001600160a01b03821660009081526002602052604090819020805460ff191660019081179091555490517fdfcd43c08ef05f3daafa26ff085ec3bed7a53cbe2c3800282ba4f3f8cb751349916102ad9133918691610551565b60405180910390a15050565b6000546001600160a01b031633146102e35760405162461bcd60e51b8152600401610179906105fb565b600047116103035760405162461bcd60e51b8152600401610179906105d0565b6040516001600160a01b0383169082156108fc029083906000818181858888f19350505050158015610339573d6000803e3d6000fd5b507f47f48bd52d3f8f7dc0a3c88ebc824a2f3d439b5d7a04fa1a278ef0de587369d082826040516102ad929190610575565b600080546001600160a01b031633146103965760405162461bcd60e51b8152600401610179906105fb565b7fa9376856550cfe9f0d25894ccc2b7caa4aeffd8b24c8bc99159a8d3de8fe6aaa33346040516103c7929190610575565b60405180910390a150600190565b6000546001600160a01b031633146103ff5760405162461bcd60e51b8152600401610179906105fb565b600080546040516001600160a01b03909116914780156108fc02929091818181858888f19350505050158015610439573d6000803e3d6000fd5b50565b6000546001600160a01b031633146104665760405162461bcd60e51b8152600401610179906105fb565b6001600160a01b03811661047957600080fd5b600080546040516001600160a01b03808516939216917ff77c6eb92f5003da08a86ab833733c2f7f05480f4cc11bf57bf9fecb10873ad791a3600080546001600160a01b0319166001600160a01b0392909216919091179055565b80356001600160a01b03811681146104eb57600080fd5b92915050565b600060208284031215610502578081fd5b61050c83836104d4565b9392505050565b60008060408385031215610525578081fd5b61052f84846104d4565b946020939093013593505050565b6001600160a01b0391909116815260200190565b6001600160a01b039384168152919092166020820152604081019190915260600190565b6001600160a01b03929092168252602082015260400190565b901515815260200190565b6020808252601a908201527f4163636f756e742048617320416c72656164792046756e646564000000000000604082015260600190565b602080825260119082015270496e737566696369656e742046756e647360781b604082015260600190565b6020808252603c908201527f596f7520646f6e2774206861766520656e6f75676874207065726d697373696f60408201527f6e7320746f20657865637574652074686973206f7065726174696f6e00000000606082015260800190565b9081526020019056fea264697066735822122067e738e0e2c0f3f33c26fc0e47b294d6a94cd03635ab299f204b3a4594f56cda64736f6c63430007010033";

    public static final String FUNC_COLLECT = "collect";

    public static final String FUNC_DEPOSIT = "deposit";

    public static final String FUNC_GETBALANCE = "getBalance";

    public static final String FUNC_OWNINGAUTHORITY = "owningAuthority";

    public static final String FUNC_SENDFUNDS = "sendFunds";

    public static final String FUNC_SENDSEEDFUNDSTO = "sendSeedFundsTo";

    public static final String FUNC_TRANSFEROWNERSHIP = "transferOwnership";

    public static final Event ONDEPOSIT_EVENT = new Event("OnDeposit", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}, new TypeReference<Uint256>() {}));
    ;

    public static final Event ONOWNERSHIPTRANSFERRED_EVENT = new Event("OnOwnershipTransferred", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>(true) {}, new TypeReference<Address>(true) {}));
    ;

    public static final Event ONSENDFUNDS_EVENT = new Event("OnSendFunds", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}, new TypeReference<Uint256>() {}));
    ;

    public static final Event ONSENDSEEDFUNDS_EVENT = new Event("OnSendSeedFunds", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}, new TypeReference<Address>() {}, new TypeReference<Uint256>() {}));
    ;

    @Deprecated
    protected EtherFaucetContract(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected EtherFaucetContract(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, credentials, contractGasProvider);
    }

    @Deprecated
    protected EtherFaucetContract(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    protected EtherFaucetContract(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public List<OnDepositEventResponse> getOnDepositEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(ONDEPOSIT_EVENT, transactionReceipt);
        ArrayList<OnDepositEventResponse> responses = new ArrayList<OnDepositEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            OnDepositEventResponse typedResponse = new OnDepositEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.sender = (String) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse.amount = (BigInteger) eventValues.getNonIndexedValues().get(1).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<OnDepositEventResponse> onDepositEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new Function<Log, OnDepositEventResponse>() {
            @Override
            public OnDepositEventResponse apply(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(ONDEPOSIT_EVENT, log);
                OnDepositEventResponse typedResponse = new OnDepositEventResponse();
                typedResponse.log = log;
                typedResponse.sender = (String) eventValues.getNonIndexedValues().get(0).getValue();
                typedResponse.amount = (BigInteger) eventValues.getNonIndexedValues().get(1).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<OnDepositEventResponse> onDepositEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(ONDEPOSIT_EVENT));
        return onDepositEventFlowable(filter);
    }

    public List<OnOwnershipTransferredEventResponse> getOnOwnershipTransferredEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(ONOWNERSHIPTRANSFERRED_EVENT, transactionReceipt);
        ArrayList<OnOwnershipTransferredEventResponse> responses = new ArrayList<OnOwnershipTransferredEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            OnOwnershipTransferredEventResponse typedResponse = new OnOwnershipTransferredEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.previousOwner = (String) eventValues.getIndexedValues().get(0).getValue();
            typedResponse.newOwner = (String) eventValues.getIndexedValues().get(1).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<OnOwnershipTransferredEventResponse> onOwnershipTransferredEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new Function<Log, OnOwnershipTransferredEventResponse>() {
            @Override
            public OnOwnershipTransferredEventResponse apply(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(ONOWNERSHIPTRANSFERRED_EVENT, log);
                OnOwnershipTransferredEventResponse typedResponse = new OnOwnershipTransferredEventResponse();
                typedResponse.log = log;
                typedResponse.previousOwner = (String) eventValues.getIndexedValues().get(0).getValue();
                typedResponse.newOwner = (String) eventValues.getIndexedValues().get(1).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<OnOwnershipTransferredEventResponse> onOwnershipTransferredEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(ONOWNERSHIPTRANSFERRED_EVENT));
        return onOwnershipTransferredEventFlowable(filter);
    }

    public List<OnSendFundsEventResponse> getOnSendFundsEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(ONSENDFUNDS_EVENT, transactionReceipt);
        ArrayList<OnSendFundsEventResponse> responses = new ArrayList<OnSendFundsEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            OnSendFundsEventResponse typedResponse = new OnSendFundsEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.account = (String) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse.amount = (BigInteger) eventValues.getNonIndexedValues().get(1).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<OnSendFundsEventResponse> onSendFundsEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new Function<Log, OnSendFundsEventResponse>() {
            @Override
            public OnSendFundsEventResponse apply(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(ONSENDFUNDS_EVENT, log);
                OnSendFundsEventResponse typedResponse = new OnSendFundsEventResponse();
                typedResponse.log = log;
                typedResponse.account = (String) eventValues.getNonIndexedValues().get(0).getValue();
                typedResponse.amount = (BigInteger) eventValues.getNonIndexedValues().get(1).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<OnSendFundsEventResponse> onSendFundsEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(ONSENDFUNDS_EVENT));
        return onSendFundsEventFlowable(filter);
    }

    public List<OnSendSeedFundsEventResponse> getOnSendSeedFundsEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(ONSENDSEEDFUNDS_EVENT, transactionReceipt);
        ArrayList<OnSendSeedFundsEventResponse> responses = new ArrayList<OnSendSeedFundsEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            OnSendSeedFundsEventResponse typedResponse = new OnSendSeedFundsEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.sender = (String) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse.account = (String) eventValues.getNonIndexedValues().get(1).getValue();
            typedResponse.amount = (BigInteger) eventValues.getNonIndexedValues().get(2).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<OnSendSeedFundsEventResponse> onSendSeedFundsEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new Function<Log, OnSendSeedFundsEventResponse>() {
            @Override
            public OnSendSeedFundsEventResponse apply(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(ONSENDSEEDFUNDS_EVENT, log);
                OnSendSeedFundsEventResponse typedResponse = new OnSendSeedFundsEventResponse();
                typedResponse.log = log;
                typedResponse.sender = (String) eventValues.getNonIndexedValues().get(0).getValue();
                typedResponse.account = (String) eventValues.getNonIndexedValues().get(1).getValue();
                typedResponse.amount = (BigInteger) eventValues.getNonIndexedValues().get(2).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<OnSendSeedFundsEventResponse> onSendSeedFundsEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(ONSENDSEEDFUNDS_EVENT));
        return onSendSeedFundsEventFlowable(filter);
    }

    public RemoteFunctionCall<TransactionReceipt> collect() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_COLLECT, 
                Arrays.<Type>asList(), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> deposit() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_DEPOSIT, 
                Arrays.<Type>asList(), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<BigInteger> getBalance() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_GETBALANCE, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<String> owningAuthority() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_OWNINGAUTHORITY, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteFunctionCall<TransactionReceipt> sendFunds(String account, BigInteger amount) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_SENDFUNDS, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, account), 
                new org.web3j.abi.datatypes.generated.Uint256(amount)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> sendSeedFundsTo(String account) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_SENDSEEDFUNDSTO, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, account)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> transferOwnership(String newOwner) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_TRANSFEROWNERSHIP, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, newOwner)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    @Deprecated
    public static EtherFaucetContract load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new EtherFaucetContract(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    @Deprecated
    public static EtherFaucetContract load(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new EtherFaucetContract(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public static EtherFaucetContract load(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return new EtherFaucetContract(contractAddress, web3j, credentials, contractGasProvider);
    }

    public static EtherFaucetContract load(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return new EtherFaucetContract(contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static RemoteCall<EtherFaucetContract> deploy(Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return deployRemoteCall(EtherFaucetContract.class, web3j, credentials, contractGasProvider, BINARY, "");
    }

    @Deprecated
    public static RemoteCall<EtherFaucetContract> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(EtherFaucetContract.class, web3j, credentials, gasPrice, gasLimit, BINARY, "");
    }

    public static RemoteCall<EtherFaucetContract> deploy(Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return deployRemoteCall(EtherFaucetContract.class, web3j, transactionManager, contractGasProvider, BINARY, "");
    }

    @Deprecated
    public static RemoteCall<EtherFaucetContract> deploy(Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(EtherFaucetContract.class, web3j, transactionManager, gasPrice, gasLimit, BINARY, "");
    }

    public static class OnDepositEventResponse extends BaseEventResponse {
        public String sender;

        public BigInteger amount;
    }

    public static class OnOwnershipTransferredEventResponse extends BaseEventResponse {
        public String previousOwner;

        public String newOwner;
    }

    public static class OnSendFundsEventResponse extends BaseEventResponse {
        public String account;

        public BigInteger amount;
    }

    public static class OnSendSeedFundsEventResponse extends BaseEventResponse {
        public String sender;

        public String account;

        public BigInteger amount;
    }
}
