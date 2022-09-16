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
public class ICertificationCourseContract extends Contract {
    public static final String BINARY = "";

    public static final String FUNC_addCertificationCourse = "addCertificationCourse";

    public static final String FUNC_CANBEISSUED = "canBeIssued";

    public static final String FUNC_CANBERENEWED = "canBeRenewed";

    public static final String FUNC_DISABLECERTIFICATIONCOURSE = "disableCertificationCourse";

    public static final String FUNC_ENABLECERTIFICATIONCOURSE = "enableCertificationCourse";

    public static final String FUNC_GETALLCERTIFICATIONCOURSES = "getAllCertificationCourses";

    public static final String FUNC_GETCERTIFICATEAUTHORITYFORCOURSE = "getCertificateAuthorityForCourse";

    public static final String FUNC_GETCERTIFICATECOURSEDETAIL = "getCertificateCourseDetail";

    public static final String FUNC_GETCOSTOFISSUINGCERTIFICATE = "getCostOfIssuingCertificate";

    public static final String FUNC_GETCOSTOFRENEWINGCERTIFICATE = "getCostOfRenewingCertificate";

    public static final String FUNC_GETDURATIONINHOURS = "getDurationInHours";

    public static final String FUNC_GETEXPIRATIONDATE = "getExpirationDate";

    public static final String FUNC_GETMYCERTIFICATIONCOURSES = "getMyCertificationCourses";

    public static final String FUNC_ISCERTIFICATIONCOURSEEXISTS = "isCertificationCourseExists";

    public static final String FUNC_ISYOUROWNER = "isYourOwner";

    public static final String FUNC_REMOVECERTIFICATIONCOURSE = "removeCertificationCourse";

    public static final String FUNC_UPDATECERTIFICATIONCOURSE = "updateCertificationCourse";

    public static final Event ONCERTIFICATIONCOURSEDISABLED_EVENT = new Event("OnCertificationCourseDisabled", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}));
    ;

    public static final Event ONCERTIFICATIONCOURSEENABLED_EVENT = new Event("OnCertificationCourseEnabled", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}));
    ;

    public static final Event ONCERTIFICATIONCOURSEREMOVED_EVENT = new Event("OnCertificationCourseRemoved", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}));
    ;

    public static final Event ONCERTIFICATIONCOURSEUPDATED_EVENT = new Event("OnCertificationCourseUpdated", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}));
    ;

    public static final Event ONNEWCERTIFICATIONCOURSECREATED_EVENT = new Event("OnNewCertificationCourseCreated", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}));
    ;

    @Deprecated
    protected ICertificationCourseContract(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected ICertificationCourseContract(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, credentials, contractGasProvider);
    }

    @Deprecated
    protected ICertificationCourseContract(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    protected ICertificationCourseContract(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public List<OnCertificationCourseDisabledEventResponse> getOnCertificationCourseDisabledEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(ONCERTIFICATIONCOURSEDISABLED_EVENT, transactionReceipt);
        ArrayList<OnCertificationCourseDisabledEventResponse> responses = new ArrayList<OnCertificationCourseDisabledEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            OnCertificationCourseDisabledEventResponse typedResponse = new OnCertificationCourseDisabledEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse._id = (String) eventValues.getNonIndexedValues().get(0).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<OnCertificationCourseDisabledEventResponse> onCertificationCourseDisabledEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new Function<Log, OnCertificationCourseDisabledEventResponse>() {
            @Override
            public OnCertificationCourseDisabledEventResponse apply(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(ONCERTIFICATIONCOURSEDISABLED_EVENT, log);
                OnCertificationCourseDisabledEventResponse typedResponse = new OnCertificationCourseDisabledEventResponse();
                typedResponse.log = log;
                typedResponse._id = (String) eventValues.getNonIndexedValues().get(0).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<OnCertificationCourseDisabledEventResponse> onCertificationCourseDisabledEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(ONCERTIFICATIONCOURSEDISABLED_EVENT));
        return onCertificationCourseDisabledEventFlowable(filter);
    }

    public List<OnCertificationCourseEnabledEventResponse> getOnCertificationCourseEnabledEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(ONCERTIFICATIONCOURSEENABLED_EVENT, transactionReceipt);
        ArrayList<OnCertificationCourseEnabledEventResponse> responses = new ArrayList<OnCertificationCourseEnabledEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            OnCertificationCourseEnabledEventResponse typedResponse = new OnCertificationCourseEnabledEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse._id = (String) eventValues.getNonIndexedValues().get(0).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<OnCertificationCourseEnabledEventResponse> onCertificationCourseEnabledEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new Function<Log, OnCertificationCourseEnabledEventResponse>() {
            @Override
            public OnCertificationCourseEnabledEventResponse apply(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(ONCERTIFICATIONCOURSEENABLED_EVENT, log);
                OnCertificationCourseEnabledEventResponse typedResponse = new OnCertificationCourseEnabledEventResponse();
                typedResponse.log = log;
                typedResponse._id = (String) eventValues.getNonIndexedValues().get(0).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<OnCertificationCourseEnabledEventResponse> onCertificationCourseEnabledEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(ONCERTIFICATIONCOURSEENABLED_EVENT));
        return onCertificationCourseEnabledEventFlowable(filter);
    }

    public List<OnCertificationCourseRemovedEventResponse> getOnCertificationCourseRemovedEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(ONCERTIFICATIONCOURSEREMOVED_EVENT, transactionReceipt);
        ArrayList<OnCertificationCourseRemovedEventResponse> responses = new ArrayList<OnCertificationCourseRemovedEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            OnCertificationCourseRemovedEventResponse typedResponse = new OnCertificationCourseRemovedEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse._id = (String) eventValues.getNonIndexedValues().get(0).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<OnCertificationCourseRemovedEventResponse> onCertificationCourseRemovedEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new Function<Log, OnCertificationCourseRemovedEventResponse>() {
            @Override
            public OnCertificationCourseRemovedEventResponse apply(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(ONCERTIFICATIONCOURSEREMOVED_EVENT, log);
                OnCertificationCourseRemovedEventResponse typedResponse = new OnCertificationCourseRemovedEventResponse();
                typedResponse.log = log;
                typedResponse._id = (String) eventValues.getNonIndexedValues().get(0).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<OnCertificationCourseRemovedEventResponse> onCertificationCourseRemovedEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(ONCERTIFICATIONCOURSEREMOVED_EVENT));
        return onCertificationCourseRemovedEventFlowable(filter);
    }

    public List<OnCertificationCourseUpdatedEventResponse> getOnCertificationCourseUpdatedEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(ONCERTIFICATIONCOURSEUPDATED_EVENT, transactionReceipt);
        ArrayList<OnCertificationCourseUpdatedEventResponse> responses = new ArrayList<OnCertificationCourseUpdatedEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            OnCertificationCourseUpdatedEventResponse typedResponse = new OnCertificationCourseUpdatedEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse._id = (String) eventValues.getNonIndexedValues().get(0).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<OnCertificationCourseUpdatedEventResponse> onCertificationCourseUpdatedEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new Function<Log, OnCertificationCourseUpdatedEventResponse>() {
            @Override
            public OnCertificationCourseUpdatedEventResponse apply(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(ONCERTIFICATIONCOURSEUPDATED_EVENT, log);
                OnCertificationCourseUpdatedEventResponse typedResponse = new OnCertificationCourseUpdatedEventResponse();
                typedResponse.log = log;
                typedResponse._id = (String) eventValues.getNonIndexedValues().get(0).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<OnCertificationCourseUpdatedEventResponse> onCertificationCourseUpdatedEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(ONCERTIFICATIONCOURSEUPDATED_EVENT));
        return onCertificationCourseUpdatedEventFlowable(filter);
    }

    public List<OnNewCertificationCourseCreatedEventResponse> getOnNewCertificationCourseCreatedEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(ONNEWCERTIFICATIONCOURSECREATED_EVENT, transactionReceipt);
        ArrayList<OnNewCertificationCourseCreatedEventResponse> responses = new ArrayList<OnNewCertificationCourseCreatedEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            OnNewCertificationCourseCreatedEventResponse typedResponse = new OnNewCertificationCourseCreatedEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse._id = (String) eventValues.getNonIndexedValues().get(0).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<OnNewCertificationCourseCreatedEventResponse> onNewCertificationCourseCreatedEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new Function<Log, OnNewCertificationCourseCreatedEventResponse>() {
            @Override
            public OnNewCertificationCourseCreatedEventResponse apply(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(ONNEWCERTIFICATIONCOURSECREATED_EVENT, log);
                OnNewCertificationCourseCreatedEventResponse typedResponse = new OnNewCertificationCourseCreatedEventResponse();
                typedResponse.log = log;
                typedResponse._id = (String) eventValues.getNonIndexedValues().get(0).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<OnNewCertificationCourseCreatedEventResponse> onNewCertificationCourseCreatedEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(ONNEWCERTIFICATIONCOURSECREATED_EVENT));
        return onNewCertificationCourseCreatedEventFlowable(filter);
    }

    public RemoteFunctionCall<TransactionReceipt> addCertificationCourse(String _name, BigInteger _costOfIssuingCertificate, BigInteger _durationInHours) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_addCertificationCourse, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Utf8String(_name), 
                new org.web3j.abi.datatypes.generated.Uint256(_costOfIssuingCertificate), 
                new org.web3j.abi.datatypes.generated.Uint256(_durationInHours)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> addCertificationCourse(String _name, BigInteger _costOfIssuingCertificate, BigInteger _durationInHours, BigInteger _expirationInDays, Boolean _canBeRenewed, BigInteger _costOfRenewingCertificate) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_addCertificationCourse, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Utf8String(_name), 
                new org.web3j.abi.datatypes.generated.Uint256(_costOfIssuingCertificate), 
                new org.web3j.abi.datatypes.generated.Uint256(_durationInHours), 
                new org.web3j.abi.datatypes.generated.Uint256(_expirationInDays), 
                new org.web3j.abi.datatypes.Bool(_canBeRenewed), 
                new org.web3j.abi.datatypes.generated.Uint256(_costOfRenewingCertificate)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<Boolean> canBeIssued(String _id) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_CANBEISSUED, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Utf8String(_id)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {}));
        return executeRemoteCallSingleValueReturn(function, Boolean.class);
    }

    public RemoteFunctionCall<Boolean> canBeRenewed(String _id) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_CANBERENEWED, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Utf8String(_id)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {}));
        return executeRemoteCallSingleValueReturn(function, Boolean.class);
    }

    public RemoteFunctionCall<TransactionReceipt> disableCertificationCourse(String _id) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_DISABLECERTIFICATIONCOURSE, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Utf8String(_id)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> enableCertificationCourse(String _id) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_ENABLECERTIFICATIONCOURSE, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Utf8String(_id)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<List> getAllCertificationCourses() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_GETALLCERTIFICATIONCOURSES, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<DynamicArray<CertificationCourseRecord>>() {}));
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

    public RemoteFunctionCall<String> getCertificateAuthorityForCourse(String _id) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_GETCERTIFICATEAUTHORITYFORCOURSE, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Utf8String(_id)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteFunctionCall<CertificationCourseRecord> getCertificateCourseDetail(String _id) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_GETCERTIFICATECOURSEDETAIL, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Utf8String(_id)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<CertificationCourseRecord>() {}));
        return executeRemoteCallSingleValueReturn(function, CertificationCourseRecord.class);
    }

    public RemoteFunctionCall<BigInteger> getCostOfIssuingCertificate(String _id) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_GETCOSTOFISSUINGCERTIFICATE, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Utf8String(_id)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<BigInteger> getCostOfRenewingCertificate(String _id) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_GETCOSTOFRENEWINGCERTIFICATE, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Utf8String(_id)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<BigInteger> getDurationInHours(String _id) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_GETDURATIONINHOURS, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Utf8String(_id)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<BigInteger> getExpirationDate(String _id) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_GETEXPIRATIONDATE, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Utf8String(_id)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<List> getMyCertificationCourses() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_GETMYCERTIFICATIONCOURSES, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<DynamicArray<CertificationCourseRecord>>() {}));
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

    public RemoteFunctionCall<Boolean> isCertificationCourseExists(String _id) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_ISCERTIFICATIONCOURSEEXISTS, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Utf8String(_id)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {}));
        return executeRemoteCallSingleValueReturn(function, Boolean.class);
    }

    public RemoteFunctionCall<Boolean> isYourOwner(String _id, String _certificationAuthority) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_ISYOUROWNER, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Utf8String(_id), 
                new org.web3j.abi.datatypes.Address(160, _certificationAuthority)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {}));
        return executeRemoteCallSingleValueReturn(function, Boolean.class);
    }

    public RemoteFunctionCall<TransactionReceipt> removeCertificationCourse(String _id) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_REMOVECERTIFICATIONCOURSE, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Utf8String(_id)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> updateCertificationCourse(String _id, String _name, BigInteger _costOfIssuingCertificate, BigInteger _durationInHours, BigInteger _expirationInDays, Boolean _canBeRenewed, BigInteger _costOfRenewingCertificate) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_UPDATECERTIFICATIONCOURSE, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Utf8String(_id), 
                new org.web3j.abi.datatypes.Utf8String(_name), 
                new org.web3j.abi.datatypes.generated.Uint256(_costOfIssuingCertificate), 
                new org.web3j.abi.datatypes.generated.Uint256(_durationInHours), 
                new org.web3j.abi.datatypes.generated.Uint256(_expirationInDays), 
                new org.web3j.abi.datatypes.Bool(_canBeRenewed), 
                new org.web3j.abi.datatypes.generated.Uint256(_costOfRenewingCertificate)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    @Deprecated
    public static ICertificationCourseContract load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new ICertificationCourseContract(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    @Deprecated
    public static ICertificationCourseContract load(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new ICertificationCourseContract(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public static ICertificationCourseContract load(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return new ICertificationCourseContract(contractAddress, web3j, credentials, contractGasProvider);
    }

    public static ICertificationCourseContract load(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return new ICertificationCourseContract(contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static RemoteCall<ICertificationCourseContract> deploy(Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return deployRemoteCall(ICertificationCourseContract.class, web3j, credentials, contractGasProvider, BINARY, "");
    }

    @Deprecated
    public static RemoteCall<ICertificationCourseContract> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(ICertificationCourseContract.class, web3j, credentials, gasPrice, gasLimit, BINARY, "");
    }

    public static RemoteCall<ICertificationCourseContract> deploy(Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return deployRemoteCall(ICertificationCourseContract.class, web3j, transactionManager, contractGasProvider, BINARY, "");
    }

    @Deprecated
    public static RemoteCall<ICertificationCourseContract> deploy(Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(ICertificationCourseContract.class, web3j, transactionManager, gasPrice, gasLimit, BINARY, "");
    }

    public static class CertificationCourseRecord extends DynamicStruct {
        public String name;

        public BigInteger costOfIssuingCertificate;

        public BigInteger costOfRenewingCertificate;

        public String certificationAuthority;

        public BigInteger durationInHours;

        public BigInteger expirationInDays;

        public Boolean canBeRenewed;

        public Boolean isEnabled;

        public Boolean isExist;

        public CertificationCourseRecord(String name, BigInteger costOfIssuingCertificate, BigInteger costOfRenewingCertificate, String certificationAuthority, BigInteger durationInHours, BigInteger expirationInDays, Boolean canBeRenewed, Boolean isEnabled, Boolean isExist) {
            super(new org.web3j.abi.datatypes.Utf8String(name),new org.web3j.abi.datatypes.generated.Uint256(costOfIssuingCertificate),new org.web3j.abi.datatypes.generated.Uint256(costOfRenewingCertificate),new org.web3j.abi.datatypes.Address(certificationAuthority),new org.web3j.abi.datatypes.generated.Uint256(durationInHours),new org.web3j.abi.datatypes.generated.Uint256(expirationInDays),new org.web3j.abi.datatypes.Bool(canBeRenewed),new org.web3j.abi.datatypes.Bool(isEnabled),new org.web3j.abi.datatypes.Bool(isExist));
            this.name = name;
            this.costOfIssuingCertificate = costOfIssuingCertificate;
            this.costOfRenewingCertificate = costOfRenewingCertificate;
            this.certificationAuthority = certificationAuthority;
            this.durationInHours = durationInHours;
            this.expirationInDays = expirationInDays;
            this.canBeRenewed = canBeRenewed;
            this.isEnabled = isEnabled;
            this.isExist = isExist;
        }

        public CertificationCourseRecord(Utf8String name, Uint256 costOfIssuingCertificate, Uint256 costOfRenewingCertificate, Address certificationAuthority, Uint256 durationInHours, Uint256 expirationInDays, Bool canBeRenewed, Bool isEnabled, Bool isExist) {
            super(name,costOfIssuingCertificate,costOfRenewingCertificate,certificationAuthority,durationInHours,expirationInDays,canBeRenewed,isEnabled,isExist);
            this.name = name.getValue();
            this.costOfIssuingCertificate = costOfIssuingCertificate.getValue();
            this.costOfRenewingCertificate = costOfRenewingCertificate.getValue();
            this.certificationAuthority = certificationAuthority.getValue();
            this.durationInHours = durationInHours.getValue();
            this.expirationInDays = expirationInDays.getValue();
            this.canBeRenewed = canBeRenewed.getValue();
            this.isEnabled = isEnabled.getValue();
            this.isExist = isExist.getValue();
        }
    }

    public static class OnCertificationCourseDisabledEventResponse extends BaseEventResponse {
        public String _id;
    }

    public static class OnCertificationCourseEnabledEventResponse extends BaseEventResponse {
        public String _id;
    }

    public static class OnCertificationCourseRemovedEventResponse extends BaseEventResponse {
        public String _id;
    }

    public static class OnCertificationCourseUpdatedEventResponse extends BaseEventResponse {
        public String _id;
    }

    public static class OnNewCertificationCourseCreatedEventResponse extends BaseEventResponse {
        public String _id;
    }
}
