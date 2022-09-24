package com.dreamsoftware.tcs.contracts;

import io.reactivex.Flowable;
import io.reactivex.functions.Function;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import org.web3j.abi.EventEncoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.Bool;
import org.web3j.abi.datatypes.DynamicArray;
import org.web3j.abi.datatypes.DynamicStruct;
import org.web3j.abi.datatypes.Event;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.Utf8String;
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
public class ICertificationAuthorityContract extends Contract {
    public static final String BINARY = "";

    public static final String FUNC_addCertificationAuthority = "addCertificationAuthority";

    public static final String FUNC_DISABLECERTIFICATIONAUTHORITY = "disableCertificationAuthority";

    public static final String FUNC_ENABLECERTIFICATIONAUTHORITY = "enableCertificationAuthority";

    public static final String FUNC_GETALLCERTIFICATIONAUTHORITIES = "getAllCertificationAuthorities";

    public static final String FUNC_getCertificateAuthorityDetail = "getCertificateAuthorityDetail";

    public static final String FUNC_GETDEFAULTCOSTOFISSUINGCERTIFICATE = "getDefaultCostOfIssuingCertificate";

    public static final String FUNC_ISCERTIFICATIONAUTHORITYENABLED = "isCertificationAuthorityEnabled";

    public static final String FUNC_ISCERTIFICATIONAUTHORITYEXISTS = "isCertificationAuthorityExists";

    public static final String FUNC_REMOVECERTIFICATIONAUTHORITY = "removeCertificationAuthority";

    public static final String FUNC_UPDATECERTIFICATIONAUTHORITY = "updateCertificationAuthority";

    public static final Event ONCERTIFICATIONAUTHORITYDISABLED_EVENT = new Event("OnCertificationAuthorityDisabled", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
    ;

    public static final Event ONCERTIFICATIONAUTHORITYENABLED_EVENT = new Event("OnCertificationAuthorityEnabled", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
    ;

    public static final Event ONCERTIFICATIONAUTHORITYREMOVED_EVENT = new Event("OnCertificationAuthorityRemoved", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
    ;

    public static final Event ONCERTIFICATIONAUTHORITYUPDATED_EVENT = new Event("OnCertificationAuthorityUpdated", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}, new TypeReference<Utf8String>() {}));
    ;

    public static final Event ONNEWCERTIFICATIONAUTHORITYCREATED_EVENT = new Event("OnNewCertificationAuthorityCreated", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}, new TypeReference<Utf8String>() {}, new TypeReference<Utf8String>() {}, new TypeReference<Utf8String>() {}));
    ;

    @Deprecated
    protected ICertificationAuthorityContract(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected ICertificationAuthorityContract(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, credentials, contractGasProvider);
    }

    @Deprecated
    protected ICertificationAuthorityContract(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    protected ICertificationAuthorityContract(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public List<OnCertificationAuthorityDisabledEventResponse> getOnCertificationAuthorityDisabledEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(ONCERTIFICATIONAUTHORITYDISABLED_EVENT, transactionReceipt);
        ArrayList<OnCertificationAuthorityDisabledEventResponse> responses = new ArrayList<OnCertificationAuthorityDisabledEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            OnCertificationAuthorityDisabledEventResponse typedResponse = new OnCertificationAuthorityDisabledEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse._address = (String) eventValues.getNonIndexedValues().get(0).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<OnCertificationAuthorityDisabledEventResponse> onCertificationAuthorityDisabledEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new Function<Log, OnCertificationAuthorityDisabledEventResponse>() {
            @Override
            public OnCertificationAuthorityDisabledEventResponse apply(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(ONCERTIFICATIONAUTHORITYDISABLED_EVENT, log);
                OnCertificationAuthorityDisabledEventResponse typedResponse = new OnCertificationAuthorityDisabledEventResponse();
                typedResponse.log = log;
                typedResponse._address = (String) eventValues.getNonIndexedValues().get(0).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<OnCertificationAuthorityDisabledEventResponse> onCertificationAuthorityDisabledEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(ONCERTIFICATIONAUTHORITYDISABLED_EVENT));
        return onCertificationAuthorityDisabledEventFlowable(filter);
    }

    public List<OnCertificationAuthorityEnabledEventResponse> getOnCertificationAuthorityEnabledEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(ONCERTIFICATIONAUTHORITYENABLED_EVENT, transactionReceipt);
        ArrayList<OnCertificationAuthorityEnabledEventResponse> responses = new ArrayList<OnCertificationAuthorityEnabledEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            OnCertificationAuthorityEnabledEventResponse typedResponse = new OnCertificationAuthorityEnabledEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse._address = (String) eventValues.getNonIndexedValues().get(0).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<OnCertificationAuthorityEnabledEventResponse> onCertificationAuthorityEnabledEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new Function<Log, OnCertificationAuthorityEnabledEventResponse>() {
            @Override
            public OnCertificationAuthorityEnabledEventResponse apply(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(ONCERTIFICATIONAUTHORITYENABLED_EVENT, log);
                OnCertificationAuthorityEnabledEventResponse typedResponse = new OnCertificationAuthorityEnabledEventResponse();
                typedResponse.log = log;
                typedResponse._address = (String) eventValues.getNonIndexedValues().get(0).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<OnCertificationAuthorityEnabledEventResponse> onCertificationAuthorityEnabledEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(ONCERTIFICATIONAUTHORITYENABLED_EVENT));
        return onCertificationAuthorityEnabledEventFlowable(filter);
    }

    public List<OnCertificationAuthorityRemovedEventResponse> getOnCertificationAuthorityRemovedEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(ONCERTIFICATIONAUTHORITYREMOVED_EVENT, transactionReceipt);
        ArrayList<OnCertificationAuthorityRemovedEventResponse> responses = new ArrayList<OnCertificationAuthorityRemovedEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            OnCertificationAuthorityRemovedEventResponse typedResponse = new OnCertificationAuthorityRemovedEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse._address = (String) eventValues.getNonIndexedValues().get(0).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<OnCertificationAuthorityRemovedEventResponse> onCertificationAuthorityRemovedEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new Function<Log, OnCertificationAuthorityRemovedEventResponse>() {
            @Override
            public OnCertificationAuthorityRemovedEventResponse apply(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(ONCERTIFICATIONAUTHORITYREMOVED_EVENT, log);
                OnCertificationAuthorityRemovedEventResponse typedResponse = new OnCertificationAuthorityRemovedEventResponse();
                typedResponse.log = log;
                typedResponse._address = (String) eventValues.getNonIndexedValues().get(0).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<OnCertificationAuthorityRemovedEventResponse> onCertificationAuthorityRemovedEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(ONCERTIFICATIONAUTHORITYREMOVED_EVENT));
        return onCertificationAuthorityRemovedEventFlowable(filter);
    }

    public List<OnCertificationAuthorityUpdatedEventResponse> getOnCertificationAuthorityUpdatedEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(ONCERTIFICATIONAUTHORITYUPDATED_EVENT, transactionReceipt);
        ArrayList<OnCertificationAuthorityUpdatedEventResponse> responses = new ArrayList<OnCertificationAuthorityUpdatedEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            OnCertificationAuthorityUpdatedEventResponse typedResponse = new OnCertificationAuthorityUpdatedEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse._address = (String) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse._name = (String) eventValues.getNonIndexedValues().get(1).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<OnCertificationAuthorityUpdatedEventResponse> onCertificationAuthorityUpdatedEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new Function<Log, OnCertificationAuthorityUpdatedEventResponse>() {
            @Override
            public OnCertificationAuthorityUpdatedEventResponse apply(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(ONCERTIFICATIONAUTHORITYUPDATED_EVENT, log);
                OnCertificationAuthorityUpdatedEventResponse typedResponse = new OnCertificationAuthorityUpdatedEventResponse();
                typedResponse.log = log;
                typedResponse._address = (String) eventValues.getNonIndexedValues().get(0).getValue();
                typedResponse._name = (String) eventValues.getNonIndexedValues().get(1).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<OnCertificationAuthorityUpdatedEventResponse> onCertificationAuthorityUpdatedEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(ONCERTIFICATIONAUTHORITYUPDATED_EVENT));
        return onCertificationAuthorityUpdatedEventFlowable(filter);
    }

    public List<OnNewCertificationAuthorityCreatedEventResponse> getOnNewCertificationAuthorityCreatedEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(ONNEWCERTIFICATIONAUTHORITYCREATED_EVENT, transactionReceipt);
        ArrayList<OnNewCertificationAuthorityCreatedEventResponse> responses = new ArrayList<OnNewCertificationAuthorityCreatedEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            OnNewCertificationAuthorityCreatedEventResponse typedResponse = new OnNewCertificationAuthorityCreatedEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse._address = (String) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse._name = (String) eventValues.getNonIndexedValues().get(1).getValue();
            typedResponse._location = (String) eventValues.getNonIndexedValues().get(2).getValue();
            typedResponse._executiveDirector = (String) eventValues.getNonIndexedValues().get(3).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<OnNewCertificationAuthorityCreatedEventResponse> onNewCertificationAuthorityCreatedEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new Function<Log, OnNewCertificationAuthorityCreatedEventResponse>() {
            @Override
            public OnNewCertificationAuthorityCreatedEventResponse apply(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(ONNEWCERTIFICATIONAUTHORITYCREATED_EVENT, log);
                OnNewCertificationAuthorityCreatedEventResponse typedResponse = new OnNewCertificationAuthorityCreatedEventResponse();
                typedResponse.log = log;
                typedResponse._address = (String) eventValues.getNonIndexedValues().get(0).getValue();
                typedResponse._name = (String) eventValues.getNonIndexedValues().get(1).getValue();
                typedResponse._location = (String) eventValues.getNonIndexedValues().get(2).getValue();
                typedResponse._executiveDirector = (String) eventValues.getNonIndexedValues().get(3).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<OnNewCertificationAuthorityCreatedEventResponse> onNewCertificationAuthorityCreatedEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(ONNEWCERTIFICATIONAUTHORITYCREATED_EVENT));
        return onNewCertificationAuthorityCreatedEventFlowable(filter);
    }

    public RemoteFunctionCall<TransactionReceipt> addCertificationAuthority(String _name, String _location, String _executiveDirector, BigInteger _defaultCostOfIssuingCertificate) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_addCertificationAuthority, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Utf8String(_name), 
                new org.web3j.abi.datatypes.Utf8String(_location), 
                new org.web3j.abi.datatypes.Utf8String(_executiveDirector), 
                new org.web3j.abi.datatypes.generated.Uint256(_defaultCostOfIssuingCertificate)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> addCertificationAuthority(String _name, String _location, String _executiveDirector) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_addCertificationAuthority, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Utf8String(_name), 
                new org.web3j.abi.datatypes.Utf8String(_location), 
                new org.web3j.abi.datatypes.Utf8String(_executiveDirector)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> disableCertificationAuthority(String _address) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_DISABLECERTIFICATIONAUTHORITY, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, _address)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> enableCertificationAuthority(String _address) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_ENABLECERTIFICATIONAUTHORITY, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, _address)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<List> getAllCertificationAuthorities() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_GETALLCERTIFICATIONAUTHORITIES, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<DynamicArray<CertificationAuthorityRecord>>() {}));
        return new RemoteFunctionCall<List>(function,
                new Callable<List>() {
                    @Override
                    @SuppressWarnings("unchecked")
                    public List call() throws Exception {
                        List<Type> result = (List<Type>) executeCallSingleValueReturn(function, List.class);
                        return convertToNative(result);
                    }
                });
    }

    public RemoteFunctionCall<CertificationAuthorityRecord> getCertificateAuthorityDetail(String _address) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_getCertificateAuthorityDetail, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, _address)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<CertificationAuthorityRecord>() {}));
        return executeRemoteCallSingleValueReturn(function, CertificationAuthorityRecord.class);
    }

    public RemoteFunctionCall<CertificationAuthorityRecord> getCertificateAuthorityDetail() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_getCertificateAuthorityDetail, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<CertificationAuthorityRecord>() {}));
        return executeRemoteCallSingleValueReturn(function, CertificationAuthorityRecord.class);
    }

    public RemoteFunctionCall<BigInteger> getDefaultCostOfIssuingCertificate(String _address) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_GETDEFAULTCOSTOFISSUINGCERTIFICATE, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, _address)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<Boolean> isCertificationAuthorityEnabled(String _address) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_ISCERTIFICATIONAUTHORITYENABLED, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, _address)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {}));
        return executeRemoteCallSingleValueReturn(function, Boolean.class);
    }

    public RemoteFunctionCall<Boolean> isCertificationAuthorityExists(String _address) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_ISCERTIFICATIONAUTHORITYEXISTS, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, _address)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {}));
        return executeRemoteCallSingleValueReturn(function, Boolean.class);
    }

    public RemoteFunctionCall<TransactionReceipt> removeCertificationAuthority(String _address) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_REMOVECERTIFICATIONAUTHORITY, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, _address)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> updateCertificationAuthority(String _name, String _location, String _executiveDirector, BigInteger _defaultCostOfIssuingCertificate) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_UPDATECERTIFICATIONAUTHORITY, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Utf8String(_name), 
                new org.web3j.abi.datatypes.Utf8String(_location), 
                new org.web3j.abi.datatypes.Utf8String(_executiveDirector), 
                new org.web3j.abi.datatypes.generated.Uint256(_defaultCostOfIssuingCertificate)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    @Deprecated
    public static ICertificationAuthorityContract load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new ICertificationAuthorityContract(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    @Deprecated
    public static ICertificationAuthorityContract load(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new ICertificationAuthorityContract(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public static ICertificationAuthorityContract load(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return new ICertificationAuthorityContract(contractAddress, web3j, credentials, contractGasProvider);
    }

    public static ICertificationAuthorityContract load(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return new ICertificationAuthorityContract(contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static RemoteCall<ICertificationAuthorityContract> deploy(Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return deployRemoteCall(ICertificationAuthorityContract.class, web3j, credentials, contractGasProvider, BINARY, "");
    }

    @Deprecated
    public static RemoteCall<ICertificationAuthorityContract> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(ICertificationAuthorityContract.class, web3j, credentials, gasPrice, gasLimit, BINARY, "");
    }

    public static RemoteCall<ICertificationAuthorityContract> deploy(Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return deployRemoteCall(ICertificationAuthorityContract.class, web3j, transactionManager, contractGasProvider, BINARY, "");
    }

    @Deprecated
    public static RemoteCall<ICertificationAuthorityContract> deploy(Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(ICertificationAuthorityContract.class, web3j, transactionManager, gasPrice, gasLimit, BINARY, "");
    }

    public static class CertificationAuthorityRecord extends DynamicStruct {
        public String id;

        public String name;

        public String location;

        public String executiveDirector;

        public BigInteger defaultCostOfIssuingCertificate;

        public Boolean isEnabled;

        public Boolean isExist;

        public CertificationAuthorityRecord(String id, String name, String location, String executiveDirector, BigInteger defaultCostOfIssuingCertificate, Boolean isEnabled, Boolean isExist) {
            super(new org.web3j.abi.datatypes.Utf8String(id),new org.web3j.abi.datatypes.Utf8String(name),new org.web3j.abi.datatypes.Utf8String(location),new org.web3j.abi.datatypes.Utf8String(executiveDirector),new org.web3j.abi.datatypes.generated.Uint256(defaultCostOfIssuingCertificate),new org.web3j.abi.datatypes.Bool(isEnabled),new org.web3j.abi.datatypes.Bool(isExist));
            this.id = id;
            this.name = name;
            this.location = location;
            this.executiveDirector = executiveDirector;
            this.defaultCostOfIssuingCertificate = defaultCostOfIssuingCertificate;
            this.isEnabled = isEnabled;
            this.isExist = isExist;
        }

        public CertificationAuthorityRecord(Utf8String id, Utf8String name, Utf8String location, Utf8String executiveDirector, Uint256 defaultCostOfIssuingCertificate, Bool isEnabled, Bool isExist) {
            super(id,name,location,executiveDirector,defaultCostOfIssuingCertificate,isEnabled,isExist);
            this.id = id.getValue();
            this.name = name.getValue();
            this.location = location.getValue();
            this.executiveDirector = executiveDirector.getValue();
            this.defaultCostOfIssuingCertificate = defaultCostOfIssuingCertificate.getValue();
            this.isEnabled = isEnabled.getValue();
            this.isExist = isExist.getValue();
        }
    }

    public static class OnCertificationAuthorityDisabledEventResponse extends BaseEventResponse {
        public String _address;
    }

    public static class OnCertificationAuthorityEnabledEventResponse extends BaseEventResponse {
        public String _address;
    }

    public static class OnCertificationAuthorityRemovedEventResponse extends BaseEventResponse {
        public String _address;
    }

    public static class OnCertificationAuthorityUpdatedEventResponse extends BaseEventResponse {
        public String _address;

        public String _name;
    }

    public static class OnNewCertificationAuthorityCreatedEventResponse extends BaseEventResponse {
        public String _address;

        public String _name;

        public String _location;

        public String _executiveDirector;
    }
}
