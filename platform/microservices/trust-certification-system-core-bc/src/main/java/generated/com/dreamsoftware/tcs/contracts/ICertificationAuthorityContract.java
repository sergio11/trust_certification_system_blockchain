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

    public static final String FUNC_ADDCERTIFICATIONAUTHORITY = "addCertificationAuthority";

    public static final String FUNC_ADDMEMBER = "addMember";

    public static final String FUNC_DISABLECERTIFICATIONAUTHORITY = "disableCertificationAuthority";

    public static final String FUNC_DISABLEMEMBER = "disableMember";

    public static final String FUNC_ENABLECERTIFICATIONAUTHORITY = "enableCertificationAuthority";

    public static final String FUNC_ENABLEMEMBER = "enableMember";

    public static final String FUNC_GETALLCERTIFICATIONAUTHORITIES = "getAllCertificationAuthorities";

    public static final String FUNC_GETCERTIFICATEAUTHORITYADMINMEMBER = "getCertificateAuthorityAdminMember";

    public static final String FUNC_GETCERTIFICATEAUTHORITYDETAIL = "getCertificateAuthorityDetail";

    public static final String FUNC_GETCERTIFICATIONAUTHORITYBYMEMBER = "getCertificationAuthorityByMember";

    public static final String FUNC_ISCERTIFICATIONAUTHORITYENABLED = "isCertificationAuthorityEnabled";

    public static final String FUNC_ISCERTIFICATIONAUTHORITYEXISTS = "isCertificationAuthorityExists";

    public static final String FUNC_ISCERTIFICATIONAUTHORITYMEMBERENABLED = "isCertificationAuthorityMemberEnabled";

    public static final String FUNC_ISCERTIFICATIONAUTHORITYMEMBEREXISTS = "isCertificationAuthorityMemberExists";

    public static final String FUNC_REMOVECERTIFICATIONAUTHORITY = "removeCertificationAuthority";

    public static final String FUNC_REMOVEMEMBER = "removeMember";

    public static final Event ONCERTIFICATIONAUTHORITYDISABLED_EVENT = new Event("OnCertificationAuthorityDisabled", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}));
    ;

    public static final Event ONCERTIFICATIONAUTHORITYENABLED_EVENT = new Event("OnCertificationAuthorityEnabled", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}));
    ;

    public static final Event ONCERTIFICATIONAUTHORITYMEMBERDISABLED_EVENT = new Event("OnCertificationAuthorityMemberDisabled", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}, new TypeReference<Address>() {}));
    ;

    public static final Event ONCERTIFICATIONAUTHORITYMEMBERENABLED_EVENT = new Event("OnCertificationAuthorityMemberEnabled", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}, new TypeReference<Address>() {}));
    ;

    public static final Event ONCERTIFICATIONAUTHORITYMEMBERREMOVED_EVENT = new Event("OnCertificationAuthorityMemberRemoved", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}, new TypeReference<Address>() {}));
    ;

    public static final Event ONCERTIFICATIONAUTHORITYREMOVED_EVENT = new Event("OnCertificationAuthorityRemoved", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}));
    ;

    public static final Event ONNEWCERTIFICATIONAUTHORITYCREATED_EVENT = new Event("OnNewCertificationAuthorityCreated", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}, new TypeReference<Address>() {}));
    ;

    public static final Event ONNEWCERTIFICATIONAUTHORITYMEMBERADDED_EVENT = new Event("OnNewCertificationAuthorityMemberAdded", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}, new TypeReference<Address>() {}));
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
            typedResponse._id = (String) eventValues.getNonIndexedValues().get(0).getValue();
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
                typedResponse._id = (String) eventValues.getNonIndexedValues().get(0).getValue();
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
            typedResponse._id = (String) eventValues.getNonIndexedValues().get(0).getValue();
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
                typedResponse._id = (String) eventValues.getNonIndexedValues().get(0).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<OnCertificationAuthorityEnabledEventResponse> onCertificationAuthorityEnabledEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(ONCERTIFICATIONAUTHORITYENABLED_EVENT));
        return onCertificationAuthorityEnabledEventFlowable(filter);
    }

    public List<OnCertificationAuthorityMemberDisabledEventResponse> getOnCertificationAuthorityMemberDisabledEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(ONCERTIFICATIONAUTHORITYMEMBERDISABLED_EVENT, transactionReceipt);
        ArrayList<OnCertificationAuthorityMemberDisabledEventResponse> responses = new ArrayList<OnCertificationAuthorityMemberDisabledEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            OnCertificationAuthorityMemberDisabledEventResponse typedResponse = new OnCertificationAuthorityMemberDisabledEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse._id = (String) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse._address = (String) eventValues.getNonIndexedValues().get(1).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<OnCertificationAuthorityMemberDisabledEventResponse> onCertificationAuthorityMemberDisabledEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new Function<Log, OnCertificationAuthorityMemberDisabledEventResponse>() {
            @Override
            public OnCertificationAuthorityMemberDisabledEventResponse apply(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(ONCERTIFICATIONAUTHORITYMEMBERDISABLED_EVENT, log);
                OnCertificationAuthorityMemberDisabledEventResponse typedResponse = new OnCertificationAuthorityMemberDisabledEventResponse();
                typedResponse.log = log;
                typedResponse._id = (String) eventValues.getNonIndexedValues().get(0).getValue();
                typedResponse._address = (String) eventValues.getNonIndexedValues().get(1).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<OnCertificationAuthorityMemberDisabledEventResponse> onCertificationAuthorityMemberDisabledEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(ONCERTIFICATIONAUTHORITYMEMBERDISABLED_EVENT));
        return onCertificationAuthorityMemberDisabledEventFlowable(filter);
    }

    public List<OnCertificationAuthorityMemberEnabledEventResponse> getOnCertificationAuthorityMemberEnabledEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(ONCERTIFICATIONAUTHORITYMEMBERENABLED_EVENT, transactionReceipt);
        ArrayList<OnCertificationAuthorityMemberEnabledEventResponse> responses = new ArrayList<OnCertificationAuthorityMemberEnabledEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            OnCertificationAuthorityMemberEnabledEventResponse typedResponse = new OnCertificationAuthorityMemberEnabledEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse._id = (String) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse._address = (String) eventValues.getNonIndexedValues().get(1).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<OnCertificationAuthorityMemberEnabledEventResponse> onCertificationAuthorityMemberEnabledEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new Function<Log, OnCertificationAuthorityMemberEnabledEventResponse>() {
            @Override
            public OnCertificationAuthorityMemberEnabledEventResponse apply(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(ONCERTIFICATIONAUTHORITYMEMBERENABLED_EVENT, log);
                OnCertificationAuthorityMemberEnabledEventResponse typedResponse = new OnCertificationAuthorityMemberEnabledEventResponse();
                typedResponse.log = log;
                typedResponse._id = (String) eventValues.getNonIndexedValues().get(0).getValue();
                typedResponse._address = (String) eventValues.getNonIndexedValues().get(1).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<OnCertificationAuthorityMemberEnabledEventResponse> onCertificationAuthorityMemberEnabledEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(ONCERTIFICATIONAUTHORITYMEMBERENABLED_EVENT));
        return onCertificationAuthorityMemberEnabledEventFlowable(filter);
    }

    public List<OnCertificationAuthorityMemberRemovedEventResponse> getOnCertificationAuthorityMemberRemovedEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(ONCERTIFICATIONAUTHORITYMEMBERREMOVED_EVENT, transactionReceipt);
        ArrayList<OnCertificationAuthorityMemberRemovedEventResponse> responses = new ArrayList<OnCertificationAuthorityMemberRemovedEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            OnCertificationAuthorityMemberRemovedEventResponse typedResponse = new OnCertificationAuthorityMemberRemovedEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse._id = (String) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse._address = (String) eventValues.getNonIndexedValues().get(1).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<OnCertificationAuthorityMemberRemovedEventResponse> onCertificationAuthorityMemberRemovedEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new Function<Log, OnCertificationAuthorityMemberRemovedEventResponse>() {
            @Override
            public OnCertificationAuthorityMemberRemovedEventResponse apply(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(ONCERTIFICATIONAUTHORITYMEMBERREMOVED_EVENT, log);
                OnCertificationAuthorityMemberRemovedEventResponse typedResponse = new OnCertificationAuthorityMemberRemovedEventResponse();
                typedResponse.log = log;
                typedResponse._id = (String) eventValues.getNonIndexedValues().get(0).getValue();
                typedResponse._address = (String) eventValues.getNonIndexedValues().get(1).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<OnCertificationAuthorityMemberRemovedEventResponse> onCertificationAuthorityMemberRemovedEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(ONCERTIFICATIONAUTHORITYMEMBERREMOVED_EVENT));
        return onCertificationAuthorityMemberRemovedEventFlowable(filter);
    }

    public List<OnCertificationAuthorityRemovedEventResponse> getOnCertificationAuthorityRemovedEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(ONCERTIFICATIONAUTHORITYREMOVED_EVENT, transactionReceipt);
        ArrayList<OnCertificationAuthorityRemovedEventResponse> responses = new ArrayList<OnCertificationAuthorityRemovedEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            OnCertificationAuthorityRemovedEventResponse typedResponse = new OnCertificationAuthorityRemovedEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse._id = (String) eventValues.getNonIndexedValues().get(0).getValue();
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
                typedResponse._id = (String) eventValues.getNonIndexedValues().get(0).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<OnCertificationAuthorityRemovedEventResponse> onCertificationAuthorityRemovedEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(ONCERTIFICATIONAUTHORITYREMOVED_EVENT));
        return onCertificationAuthorityRemovedEventFlowable(filter);
    }

    public List<OnNewCertificationAuthorityCreatedEventResponse> getOnNewCertificationAuthorityCreatedEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(ONNEWCERTIFICATIONAUTHORITYCREATED_EVENT, transactionReceipt);
        ArrayList<OnNewCertificationAuthorityCreatedEventResponse> responses = new ArrayList<OnNewCertificationAuthorityCreatedEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            OnNewCertificationAuthorityCreatedEventResponse typedResponse = new OnNewCertificationAuthorityCreatedEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse._id = (String) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse._address = (String) eventValues.getNonIndexedValues().get(1).getValue();
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
                typedResponse._id = (String) eventValues.getNonIndexedValues().get(0).getValue();
                typedResponse._address = (String) eventValues.getNonIndexedValues().get(1).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<OnNewCertificationAuthorityCreatedEventResponse> onNewCertificationAuthorityCreatedEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(ONNEWCERTIFICATIONAUTHORITYCREATED_EVENT));
        return onNewCertificationAuthorityCreatedEventFlowable(filter);
    }

    public List<OnNewCertificationAuthorityMemberAddedEventResponse> getOnNewCertificationAuthorityMemberAddedEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(ONNEWCERTIFICATIONAUTHORITYMEMBERADDED_EVENT, transactionReceipt);
        ArrayList<OnNewCertificationAuthorityMemberAddedEventResponse> responses = new ArrayList<OnNewCertificationAuthorityMemberAddedEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            OnNewCertificationAuthorityMemberAddedEventResponse typedResponse = new OnNewCertificationAuthorityMemberAddedEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse._id = (String) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse._address = (String) eventValues.getNonIndexedValues().get(1).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<OnNewCertificationAuthorityMemberAddedEventResponse> onNewCertificationAuthorityMemberAddedEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new Function<Log, OnNewCertificationAuthorityMemberAddedEventResponse>() {
            @Override
            public OnNewCertificationAuthorityMemberAddedEventResponse apply(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(ONNEWCERTIFICATIONAUTHORITYMEMBERADDED_EVENT, log);
                OnNewCertificationAuthorityMemberAddedEventResponse typedResponse = new OnNewCertificationAuthorityMemberAddedEventResponse();
                typedResponse.log = log;
                typedResponse._id = (String) eventValues.getNonIndexedValues().get(0).getValue();
                typedResponse._address = (String) eventValues.getNonIndexedValues().get(1).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<OnNewCertificationAuthorityMemberAddedEventResponse> onNewCertificationAuthorityMemberAddedEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(ONNEWCERTIFICATIONAUTHORITYMEMBERADDED_EVENT));
        return onNewCertificationAuthorityMemberAddedEventFlowable(filter);
    }

    public RemoteFunctionCall<TransactionReceipt> addCertificationAuthority(String _id) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_ADDCERTIFICATIONAUTHORITY, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Utf8String(_id)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> addMember(String _id, String _member) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_ADDMEMBER, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Utf8String(_id), 
                new org.web3j.abi.datatypes.Address(160, _member)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> disableCertificationAuthority(String _id) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_DISABLECERTIFICATIONAUTHORITY, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Utf8String(_id)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> disableMember(String _id, String _member) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_DISABLEMEMBER, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Utf8String(_id), 
                new org.web3j.abi.datatypes.Address(160, _member)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> enableCertificationAuthority(String _id) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_ENABLECERTIFICATIONAUTHORITY, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Utf8String(_id)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> enableMember(String _id, String _member) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_ENABLEMEMBER, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Utf8String(_id), 
                new org.web3j.abi.datatypes.Address(160, _member)), 
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

    public RemoteFunctionCall<String> getCertificateAuthorityAdminMember(String _id) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_GETCERTIFICATEAUTHORITYADMINMEMBER, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Utf8String(_id)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteFunctionCall<CertificationAuthorityRecord> getCertificateAuthorityDetail(String _id) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_GETCERTIFICATEAUTHORITYDETAIL, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Utf8String(_id)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<CertificationAuthorityRecord>() {}));
        return executeRemoteCallSingleValueReturn(function, CertificationAuthorityRecord.class);
    }

    public RemoteFunctionCall<String> getCertificationAuthorityByMember(String _member) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_GETCERTIFICATIONAUTHORITYBYMEMBER, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, _member)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteFunctionCall<Boolean> isCertificationAuthorityEnabled(String _id) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_ISCERTIFICATIONAUTHORITYENABLED, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Utf8String(_id)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {}));
        return executeRemoteCallSingleValueReturn(function, Boolean.class);
    }

    public RemoteFunctionCall<Boolean> isCertificationAuthorityExists(String _id) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_ISCERTIFICATIONAUTHORITYEXISTS, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Utf8String(_id)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {}));
        return executeRemoteCallSingleValueReturn(function, Boolean.class);
    }

    public RemoteFunctionCall<Boolean> isCertificationAuthorityMemberEnabled(String _id, String _member) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_ISCERTIFICATIONAUTHORITYMEMBERENABLED, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Utf8String(_id), 
                new org.web3j.abi.datatypes.Address(160, _member)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {}));
        return executeRemoteCallSingleValueReturn(function, Boolean.class);
    }

    public RemoteFunctionCall<Boolean> isCertificationAuthorityMemberExists(String _id, String _member) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_ISCERTIFICATIONAUTHORITYMEMBEREXISTS, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Utf8String(_id), 
                new org.web3j.abi.datatypes.Address(160, _member)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {}));
        return executeRemoteCallSingleValueReturn(function, Boolean.class);
    }

    public RemoteFunctionCall<TransactionReceipt> removeCertificationAuthority(String _id) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_REMOVECERTIFICATIONAUTHORITY, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Utf8String(_id)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> removeMember(String _id, String _member) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_REMOVEMEMBER, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Utf8String(_id), 
                new org.web3j.abi.datatypes.Address(160, _member)), 
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

        public String admin;

        public Boolean isEnabled;

        public Boolean isExist;

        public CertificationAuthorityRecord(String id, String admin, Boolean isEnabled, Boolean isExist) {
            super(new org.web3j.abi.datatypes.Utf8String(id),new org.web3j.abi.datatypes.Address(admin),new org.web3j.abi.datatypes.Bool(isEnabled),new org.web3j.abi.datatypes.Bool(isExist));
            this.id = id;
            this.admin = admin;
            this.isEnabled = isEnabled;
            this.isExist = isExist;
        }

        public CertificationAuthorityRecord(Utf8String id, Address admin, Bool isEnabled, Bool isExist) {
            super(id,admin,isEnabled,isExist);
            this.id = id.getValue();
            this.admin = admin.getValue();
            this.isEnabled = isEnabled.getValue();
            this.isExist = isExist.getValue();
        }
    }

    public static class OnCertificationAuthorityDisabledEventResponse extends BaseEventResponse {
        public String _id;
    }

    public static class OnCertificationAuthorityEnabledEventResponse extends BaseEventResponse {
        public String _id;
    }

    public static class OnCertificationAuthorityMemberDisabledEventResponse extends BaseEventResponse {
        public String _id;

        public String _address;
    }

    public static class OnCertificationAuthorityMemberEnabledEventResponse extends BaseEventResponse {
        public String _id;

        public String _address;
    }

    public static class OnCertificationAuthorityMemberRemovedEventResponse extends BaseEventResponse {
        public String _id;

        public String _address;
    }

    public static class OnCertificationAuthorityRemovedEventResponse extends BaseEventResponse {
        public String _id;
    }

    public static class OnNewCertificationAuthorityCreatedEventResponse extends BaseEventResponse {
        public String _id;

        public String _address;
    }

    public static class OnNewCertificationAuthorityMemberAddedEventResponse extends BaseEventResponse {
        public String _id;

        public String _address;
    }
}
