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
public class ITrustCertificationContract extends Contract {
    public static final String BINARY = "";

    public static final String FUNC_DISABLECERTIFICATE = "disableCertificate";

    public static final String FUNC_ENABLECERTIFICATE = "enableCertificate";

    public static final String FUNC_GETCERTIFICATEDETAIL = "getCertificateDetail";

    public static final String FUNC_GETMYCERTIFICATESASISSUER = "getMyCertificatesAsIssuer";

    public static final String FUNC_GETMYCERTIFICATESASRECIPIENT = "getMyCertificatesAsRecipient";

    public static final String FUNC_ISCERTIFICATEVALID = "isCertificateValid";

    public static final String FUNC_ISSUECERTIFICATE = "issueCertificate";

    public static final String FUNC_RENEWCERTIFICATE = "renewCertificate";

    public static final String FUNC_UPDATECERTIFICATEVISIBILITY = "updateCertificateVisibility";

    public static final String FUNC_VALIDATECERTIFICATEINTEGRITY = "validateCertificateIntegrity";

    public static final Event ONCERTIFICATEDELETED_EVENT = new Event("OnCertificateDeleted", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}, new TypeReference<Bool>() {}));
    ;

    public static final Event ONCERTIFICATEDISABLED_EVENT = new Event("OnCertificateDisabled", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}, new TypeReference<Bool>() {}));
    ;

    public static final Event ONCERTIFICATEENABLED_EVENT = new Event("OnCertificateEnabled", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}, new TypeReference<Bool>() {}));
    ;

    public static final Event ONCERTIFICATERENEWED_EVENT = new Event("OnCertificateRenewed", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}, new TypeReference<Bool>() {}));
    ;

    public static final Event ONCERTIFICATEVISIBILITYUPDATED_EVENT = new Event("OnCertificateVisibilityUpdated", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}, new TypeReference<Bool>() {}));
    ;

    public static final Event ONNEWCERTIFICATEGENERATED_EVENT = new Event("OnNewCertificateGenerated", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}, new TypeReference<Bool>() {}));
    ;

    @Deprecated
    protected ITrustCertificationContract(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected ITrustCertificationContract(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, credentials, contractGasProvider);
    }

    @Deprecated
    protected ITrustCertificationContract(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    protected ITrustCertificationContract(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public List<OnCertificateDeletedEventResponse> getOnCertificateDeletedEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(ONCERTIFICATEDELETED_EVENT, transactionReceipt);
        ArrayList<OnCertificateDeletedEventResponse> responses = new ArrayList<OnCertificateDeletedEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            OnCertificateDeletedEventResponse typedResponse = new OnCertificateDeletedEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse._id = (String) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse._isVisible = (Boolean) eventValues.getNonIndexedValues().get(1).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<OnCertificateDeletedEventResponse> onCertificateDeletedEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new Function<Log, OnCertificateDeletedEventResponse>() {
            @Override
            public OnCertificateDeletedEventResponse apply(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(ONCERTIFICATEDELETED_EVENT, log);
                OnCertificateDeletedEventResponse typedResponse = new OnCertificateDeletedEventResponse();
                typedResponse.log = log;
                typedResponse._id = (String) eventValues.getNonIndexedValues().get(0).getValue();
                typedResponse._isVisible = (Boolean) eventValues.getNonIndexedValues().get(1).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<OnCertificateDeletedEventResponse> onCertificateDeletedEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(ONCERTIFICATEDELETED_EVENT));
        return onCertificateDeletedEventFlowable(filter);
    }

    public List<OnCertificateDisabledEventResponse> getOnCertificateDisabledEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(ONCERTIFICATEDISABLED_EVENT, transactionReceipt);
        ArrayList<OnCertificateDisabledEventResponse> responses = new ArrayList<OnCertificateDisabledEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            OnCertificateDisabledEventResponse typedResponse = new OnCertificateDisabledEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse._id = (String) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse._isVisible = (Boolean) eventValues.getNonIndexedValues().get(1).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<OnCertificateDisabledEventResponse> onCertificateDisabledEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new Function<Log, OnCertificateDisabledEventResponse>() {
            @Override
            public OnCertificateDisabledEventResponse apply(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(ONCERTIFICATEDISABLED_EVENT, log);
                OnCertificateDisabledEventResponse typedResponse = new OnCertificateDisabledEventResponse();
                typedResponse.log = log;
                typedResponse._id = (String) eventValues.getNonIndexedValues().get(0).getValue();
                typedResponse._isVisible = (Boolean) eventValues.getNonIndexedValues().get(1).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<OnCertificateDisabledEventResponse> onCertificateDisabledEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(ONCERTIFICATEDISABLED_EVENT));
        return onCertificateDisabledEventFlowable(filter);
    }

    public List<OnCertificateEnabledEventResponse> getOnCertificateEnabledEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(ONCERTIFICATEENABLED_EVENT, transactionReceipt);
        ArrayList<OnCertificateEnabledEventResponse> responses = new ArrayList<OnCertificateEnabledEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            OnCertificateEnabledEventResponse typedResponse = new OnCertificateEnabledEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse._id = (String) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse._isVisible = (Boolean) eventValues.getNonIndexedValues().get(1).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<OnCertificateEnabledEventResponse> onCertificateEnabledEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new Function<Log, OnCertificateEnabledEventResponse>() {
            @Override
            public OnCertificateEnabledEventResponse apply(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(ONCERTIFICATEENABLED_EVENT, log);
                OnCertificateEnabledEventResponse typedResponse = new OnCertificateEnabledEventResponse();
                typedResponse.log = log;
                typedResponse._id = (String) eventValues.getNonIndexedValues().get(0).getValue();
                typedResponse._isVisible = (Boolean) eventValues.getNonIndexedValues().get(1).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<OnCertificateEnabledEventResponse> onCertificateEnabledEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(ONCERTIFICATEENABLED_EVENT));
        return onCertificateEnabledEventFlowable(filter);
    }

    public List<OnCertificateRenewedEventResponse> getOnCertificateRenewedEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(ONCERTIFICATERENEWED_EVENT, transactionReceipt);
        ArrayList<OnCertificateRenewedEventResponse> responses = new ArrayList<OnCertificateRenewedEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            OnCertificateRenewedEventResponse typedResponse = new OnCertificateRenewedEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse._id = (String) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse._isVisible = (Boolean) eventValues.getNonIndexedValues().get(1).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<OnCertificateRenewedEventResponse> onCertificateRenewedEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new Function<Log, OnCertificateRenewedEventResponse>() {
            @Override
            public OnCertificateRenewedEventResponse apply(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(ONCERTIFICATERENEWED_EVENT, log);
                OnCertificateRenewedEventResponse typedResponse = new OnCertificateRenewedEventResponse();
                typedResponse.log = log;
                typedResponse._id = (String) eventValues.getNonIndexedValues().get(0).getValue();
                typedResponse._isVisible = (Boolean) eventValues.getNonIndexedValues().get(1).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<OnCertificateRenewedEventResponse> onCertificateRenewedEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(ONCERTIFICATERENEWED_EVENT));
        return onCertificateRenewedEventFlowable(filter);
    }

    public List<OnCertificateVisibilityUpdatedEventResponse> getOnCertificateVisibilityUpdatedEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(ONCERTIFICATEVISIBILITYUPDATED_EVENT, transactionReceipt);
        ArrayList<OnCertificateVisibilityUpdatedEventResponse> responses = new ArrayList<OnCertificateVisibilityUpdatedEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            OnCertificateVisibilityUpdatedEventResponse typedResponse = new OnCertificateVisibilityUpdatedEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse._id = (String) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse._isVisible = (Boolean) eventValues.getNonIndexedValues().get(1).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<OnCertificateVisibilityUpdatedEventResponse> onCertificateVisibilityUpdatedEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new Function<Log, OnCertificateVisibilityUpdatedEventResponse>() {
            @Override
            public OnCertificateVisibilityUpdatedEventResponse apply(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(ONCERTIFICATEVISIBILITYUPDATED_EVENT, log);
                OnCertificateVisibilityUpdatedEventResponse typedResponse = new OnCertificateVisibilityUpdatedEventResponse();
                typedResponse.log = log;
                typedResponse._id = (String) eventValues.getNonIndexedValues().get(0).getValue();
                typedResponse._isVisible = (Boolean) eventValues.getNonIndexedValues().get(1).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<OnCertificateVisibilityUpdatedEventResponse> onCertificateVisibilityUpdatedEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(ONCERTIFICATEVISIBILITYUPDATED_EVENT));
        return onCertificateVisibilityUpdatedEventFlowable(filter);
    }

    public List<OnNewCertificateGeneratedEventResponse> getOnNewCertificateGeneratedEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(ONNEWCERTIFICATEGENERATED_EVENT, transactionReceipt);
        ArrayList<OnNewCertificateGeneratedEventResponse> responses = new ArrayList<OnNewCertificateGeneratedEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            OnNewCertificateGeneratedEventResponse typedResponse = new OnNewCertificateGeneratedEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse._id = (String) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse._isVisible = (Boolean) eventValues.getNonIndexedValues().get(1).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<OnNewCertificateGeneratedEventResponse> onNewCertificateGeneratedEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new Function<Log, OnNewCertificateGeneratedEventResponse>() {
            @Override
            public OnNewCertificateGeneratedEventResponse apply(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(ONNEWCERTIFICATEGENERATED_EVENT, log);
                OnNewCertificateGeneratedEventResponse typedResponse = new OnNewCertificateGeneratedEventResponse();
                typedResponse.log = log;
                typedResponse._id = (String) eventValues.getNonIndexedValues().get(0).getValue();
                typedResponse._isVisible = (Boolean) eventValues.getNonIndexedValues().get(1).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<OnNewCertificateGeneratedEventResponse> onNewCertificateGeneratedEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(ONNEWCERTIFICATEGENERATED_EVENT));
        return onNewCertificateGeneratedEventFlowable(filter);
    }

    public RemoteFunctionCall<TransactionReceipt> disableCertificate(String _id) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_DISABLECERTIFICATE, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Utf8String(_id)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> enableCertificate(String _id) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_ENABLECERTIFICATE, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Utf8String(_id)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<CertificateRecord> getCertificateDetail(String _id) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_GETCERTIFICATEDETAIL, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Utf8String(_id)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<CertificateRecord>() {}));
        return executeRemoteCallSingleValueReturn(function, CertificateRecord.class);
    }

    public RemoteFunctionCall<List> getMyCertificatesAsIssuer() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_GETMYCERTIFICATESASISSUER, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<DynamicArray<Utf8String>>() {}));
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

    public RemoteFunctionCall<List> getMyCertificatesAsRecipient() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_GETMYCERTIFICATESASRECIPIENT, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<DynamicArray<Utf8String>>() {}));
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

    public RemoteFunctionCall<Boolean> isCertificateValid(String _id) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_ISCERTIFICATEVALID, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Utf8String(_id)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {}));
        return executeRemoteCallSingleValueReturn(function, Boolean.class);
    }

    public RemoteFunctionCall<TransactionReceipt> issueCertificate(IssueCertificateRequest _request) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_ISSUECERTIFICATE, 
                Arrays.<Type>asList(_request), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> renewCertificate(String _id) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_RENEWCERTIFICATE, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Utf8String(_id)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> updateCertificateVisibility(String _id, Boolean isVisible) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_UPDATECERTIFICATEVISIBILITY, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Utf8String(_id), 
                new org.web3j.abi.datatypes.Bool(isVisible)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<Boolean> validateCertificateIntegrity(String _id, String _fileCertificateHash, String recipientAddress) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_VALIDATECERTIFICATEINTEGRITY, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Utf8String(_id), 
                new org.web3j.abi.datatypes.Utf8String(_fileCertificateHash), 
                new org.web3j.abi.datatypes.Address(160, recipientAddress)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {}));
        return executeRemoteCallSingleValueReturn(function, Boolean.class);
    }

    @Deprecated
    public static ITrustCertificationContract load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new ITrustCertificationContract(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    @Deprecated
    public static ITrustCertificationContract load(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new ITrustCertificationContract(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public static ITrustCertificationContract load(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return new ITrustCertificationContract(contractAddress, web3j, credentials, contractGasProvider);
    }

    public static ITrustCertificationContract load(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return new ITrustCertificationContract(contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static RemoteCall<ITrustCertificationContract> deploy(Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return deployRemoteCall(ITrustCertificationContract.class, web3j, credentials, contractGasProvider, BINARY, "");
    }

    @Deprecated
    public static RemoteCall<ITrustCertificationContract> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(ITrustCertificationContract.class, web3j, credentials, gasPrice, gasLimit, BINARY, "");
    }

    public static RemoteCall<ITrustCertificationContract> deploy(Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return deployRemoteCall(ITrustCertificationContract.class, web3j, transactionManager, contractGasProvider, BINARY, "");
    }

    @Deprecated
    public static RemoteCall<ITrustCertificationContract> deploy(Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(ITrustCertificationContract.class, web3j, transactionManager, gasPrice, gasLimit, BINARY, "");
    }

    public static class CertificateRecord extends DynamicStruct {
        public String id;

        public String issuerAddress;

        public String recipientAddress;

        public String course;

        public BigInteger expirationDate;

        public BigInteger qualification;

        public BigInteger durationInHours;

        public String fileCid;

        public String fileCertificateHash;

        public String imageCid;

        public String imageCertificateHash;

        public BigInteger expeditionDate;

        public Boolean isVisible;

        public Boolean isEnabled;

        public Boolean isExist;

        public CertificateRecord(String id, String issuerAddress, String recipientAddress, String course, BigInteger expirationDate, BigInteger qualification, BigInteger durationInHours, String fileCid, String fileCertificateHash, String imageCid, String imageCertificateHash, BigInteger expeditionDate, Boolean isVisible, Boolean isEnabled, Boolean isExist) {
            super(new org.web3j.abi.datatypes.Utf8String(id),new org.web3j.abi.datatypes.Address(issuerAddress),new org.web3j.abi.datatypes.Address(recipientAddress),new org.web3j.abi.datatypes.Utf8String(course),new org.web3j.abi.datatypes.generated.Uint256(expirationDate),new org.web3j.abi.datatypes.generated.Uint256(qualification),new org.web3j.abi.datatypes.generated.Uint256(durationInHours),new org.web3j.abi.datatypes.Utf8String(fileCid),new org.web3j.abi.datatypes.Utf8String(fileCertificateHash),new org.web3j.abi.datatypes.Utf8String(imageCid),new org.web3j.abi.datatypes.Utf8String(imageCertificateHash),new org.web3j.abi.datatypes.generated.Uint256(expeditionDate),new org.web3j.abi.datatypes.Bool(isVisible),new org.web3j.abi.datatypes.Bool(isEnabled),new org.web3j.abi.datatypes.Bool(isExist));
            this.id = id;
            this.issuerAddress = issuerAddress;
            this.recipientAddress = recipientAddress;
            this.course = course;
            this.expirationDate = expirationDate;
            this.qualification = qualification;
            this.durationInHours = durationInHours;
            this.fileCid = fileCid;
            this.fileCertificateHash = fileCertificateHash;
            this.imageCid = imageCid;
            this.imageCertificateHash = imageCertificateHash;
            this.expeditionDate = expeditionDate;
            this.isVisible = isVisible;
            this.isEnabled = isEnabled;
            this.isExist = isExist;
        }

        public CertificateRecord(Utf8String id, Address issuerAddress, Address recipientAddress, Utf8String course, Uint256 expirationDate, Uint256 qualification, Uint256 durationInHours, Utf8String fileCid, Utf8String fileCertificateHash, Utf8String imageCid, Utf8String imageCertificateHash, Uint256 expeditionDate, Bool isVisible, Bool isEnabled, Bool isExist) {
            super(id,issuerAddress,recipientAddress,course,expirationDate,qualification,durationInHours,fileCid,fileCertificateHash,imageCid,imageCertificateHash,expeditionDate,isVisible,isEnabled,isExist);
            this.id = id.getValue();
            this.issuerAddress = issuerAddress.getValue();
            this.recipientAddress = recipientAddress.getValue();
            this.course = course.getValue();
            this.expirationDate = expirationDate.getValue();
            this.qualification = qualification.getValue();
            this.durationInHours = durationInHours.getValue();
            this.fileCid = fileCid.getValue();
            this.fileCertificateHash = fileCertificateHash.getValue();
            this.imageCid = imageCid.getValue();
            this.imageCertificateHash = imageCertificateHash.getValue();
            this.expeditionDate = expeditionDate.getValue();
            this.isVisible = isVisible.getValue();
            this.isEnabled = isEnabled.getValue();
            this.isExist = isExist.getValue();
        }
    }

    public static class IssueCertificateRequest extends DynamicStruct {
        public String id;

        public String recipientAddress;

        public String certificateCourseId;

        public BigInteger qualification;

        public String fileCid;

        public String fileCertificateHash;

        public String imageCid;

        public String imageCertificateHash;

        public IssueCertificateRequest(String id, String recipientAddress, String certificateCourseId, BigInteger qualification, String fileCid, String fileCertificateHash, String imageCid, String imageCertificateHash) {
            super(new org.web3j.abi.datatypes.Utf8String(id),new org.web3j.abi.datatypes.Address(recipientAddress),new org.web3j.abi.datatypes.Utf8String(certificateCourseId),new org.web3j.abi.datatypes.generated.Uint256(qualification),new org.web3j.abi.datatypes.Utf8String(fileCid),new org.web3j.abi.datatypes.Utf8String(fileCertificateHash),new org.web3j.abi.datatypes.Utf8String(imageCid),new org.web3j.abi.datatypes.Utf8String(imageCertificateHash));
            this.id = id;
            this.recipientAddress = recipientAddress;
            this.certificateCourseId = certificateCourseId;
            this.qualification = qualification;
            this.fileCid = fileCid;
            this.fileCertificateHash = fileCertificateHash;
            this.imageCid = imageCid;
            this.imageCertificateHash = imageCertificateHash;
        }

        public IssueCertificateRequest(Utf8String id, Address recipientAddress, Utf8String certificateCourseId, Uint256 qualification, Utf8String fileCid, Utf8String fileCertificateHash, Utf8String imageCid, Utf8String imageCertificateHash) {
            super(id,recipientAddress,certificateCourseId,qualification,fileCid,fileCertificateHash,imageCid,imageCertificateHash);
            this.id = id.getValue();
            this.recipientAddress = recipientAddress.getValue();
            this.certificateCourseId = certificateCourseId.getValue();
            this.qualification = qualification.getValue();
            this.fileCid = fileCid.getValue();
            this.fileCertificateHash = fileCertificateHash.getValue();
            this.imageCid = imageCid.getValue();
            this.imageCertificateHash = imageCertificateHash.getValue();
        }
    }

    public static class OnCertificateDeletedEventResponse extends BaseEventResponse {
        public String _id;

        public Boolean _isVisible;
    }

    public static class OnCertificateDisabledEventResponse extends BaseEventResponse {
        public String _id;

        public Boolean _isVisible;
    }

    public static class OnCertificateEnabledEventResponse extends BaseEventResponse {
        public String _id;

        public Boolean _isVisible;
    }

    public static class OnCertificateRenewedEventResponse extends BaseEventResponse {
        public String _id;

        public Boolean _isVisible;
    }

    public static class OnCertificateVisibilityUpdatedEventResponse extends BaseEventResponse {
        public String _id;

        public Boolean _isVisible;
    }

    public static class OnNewCertificateGeneratedEventResponse extends BaseEventResponse {
        public String _id;

        public Boolean _isVisible;
    }
}
