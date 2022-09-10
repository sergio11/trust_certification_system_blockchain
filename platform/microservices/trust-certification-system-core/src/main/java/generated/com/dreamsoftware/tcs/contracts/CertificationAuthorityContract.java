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
import org.web3j.abi.datatypes.Bool;
import org.web3j.abi.datatypes.DynamicStruct;
import org.web3j.abi.datatypes.Event;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.Utf8String;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.abi.datatypes.generated.Uint8;
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
public class CertificationAuthorityContract extends Contract {
    public static final String BINARY = "608060405234801561001057600080fd5b50600080546001600160a01b0319163317905561149f806100326000396000f3fe6080604052600436106101095760003560e01c80638c35229511610095578063dc580b3d11610064578063dc580b3d146102cc578063e5225381146102e1578063eebbaf58146102f6578063f2fde38b14610316578063f90a7a161461033657610109565b80638c3522951461023f5780639146014c1461026c578063a33071321461028c578063a56593d6146102ac57610109565b80635fd94b68116100dc5780635fd94b681461019d5780636aeea31e146101b05780636c2ab70d146101dd57806384f08526146101fd5780638a7654571461021f57610109565b80631074cea31461010e57806312065fe0146101305780631b8531821461015b578063567193f91461017d575b600080fd5b34801561011a57600080fd5b5061012e610129366004610fda565b61034b565b005b34801561013c57600080fd5b50610145610434565b604051610152919061143d565b60405180910390f35b34801561016757600080fd5b50610170610464565b60405161015291906110fe565b34801561018957600080fd5b5061012e610198366004610fda565b610473565b61012e6101ab366004610fda565b610577565b3480156101bc57600080fd5b506101d06101cb366004610fda565b6105c3565b6040516101529190611190565b3480156101e957600080fd5b5061012e6101f8366004610fda565b6105ea565b34801561020957600080fd5b506102126106dd565b6040516101529190611446565b34801561022b57600080fd5b5061012e61023a36600461101d565b6106e2565b34801561024b57600080fd5b5061025f61025a366004610fda565b610776565b60405161015291906113f3565b34801561027857600080fd5b506101d0610287366004610fda565b6108e0565b34801561029857600080fd5b5061012e6102a7366004611058565b610946565b3480156102b857600080fd5b5061012e6102c7366004611058565b610b88565b3480156102d857600080fd5b50610212610c28565b3480156102ed57600080fd5b5061012e610c2d565b34801561030257600080fd5b50610145610311366004610fda565b610c94565b34801561032257600080fd5b5061012e610331366004610fda565b610cb2565b34801561034257600080fd5b5061025f610d4a565b6000546001600160a01b0316331461037e5760405162461bcd60e51b815260040161037590611396565b60405180910390fd5b6001600160a01b038116600090815260026020819052604090912001548190610100900460ff166103c15760405162461bcd60e51b8152600401610375906112b9565b6001600160a01b0382166000908152600260205260408120906103e48282610e61565b5060006001820155600201805461ffff191690556040517f14a1355acd9909e848e2fcc10757bee6a3dac4719dbc121aa3a339595e15e2e2906104289084906110fe565b60405180910390a15050565b600080546001600160a01b0316331461045f5760405162461bcd60e51b815260040161037590611396565b504790565b6000546001600160a01b031690565b6000546001600160a01b0316331461049d5760405162461bcd60e51b815260040161037590611396565b336000818152600260208190526040909120015460ff16156104d15760405162461bcd60e51b815260040161037590611271565b6001600160a01b038216600090815260026020819052604090912001548290610100900460ff166105145760405162461bcd60e51b8152600401610375906112b9565b6001600160a01b03831660009081526002602081905260409182902001805460ff19166001179055517f70181c3b508c5e190ae6ba2c5c8659ce2b8c05dc712f95787bbc5effd6ea76779061056a9085906110fe565b60405180910390a1505050565b6000546001600160a01b031633146105a15760405162461bcd60e51b815260040161037590611396565b600180546001600160a01b0319166001600160a01b0392909216919091179055565b6001600160a01b031660009081526002602081905260409091200154610100900460ff1690565b6000546001600160a01b031633146106145760405162461bcd60e51b815260040161037590611396565b336000818152600260208190526040909120015460ff166106475760405162461bcd60e51b8152600401610375906111c0565b6001600160a01b038216600090815260026020819052604090912001548290610100900460ff1661068a5760405162461bcd60e51b8152600401610375906112b9565b6001600160a01b03831660009081526002602081905260409182902001805460ff19169055517f19f5ee6faef1186424c03768eabd96d0e61384a9bef9e8903088acf6f93d02989061056a9085906110fe565b600481565b3360008181526002602081905260409091200154610100900460ff161561071b5760405162461bcd60e51b81526004016103759061130b565b604051635198389960e11b8152309063a330713290610740908590600490810161119b565b600060405180830381600087803b15801561075a57600080fd5b505af115801561076e573d6000803e3d6000fd5b505050505050565b61077e610ea5565b6001600160a01b038216600090815260026020819052604090912001548290610100900460ff166107c15760405162461bcd60e51b8152600401610375906112b9565b6001600160a01b03831660009081526002602081905260409091200154839060ff166107ff5760405162461bcd60e51b8152600401610375906111c0565b6001600160a01b0384166000908152600260208181526040928390208351815460a0601f600019610100600185161502019092169590950490810184900490930281018401909452608084018281529092849284918401828280156108a55780601f1061087a576101008083540402835291602001916108a5565b820191906000526020600020905b81548152906001019060200180831161088857829003601f168201915b50505091835250506001820154602082015260029091015460ff80821615156040840152610100909104161515606090910152949350505050565b6001600160a01b0381166000908152600260208190526040822001548290610100900460ff166109225760405162461bcd60e51b8152600401610375906112b9565b50506001600160a01b03166000908152600260208190526040909120015460ff1690565b3360008181526002602081905260409091200154610100900460ff161561097f5760405162461bcd60e51b81526004016103759061130b565b60015460405163450efe2160e01b81526000916001600160a01b03169063450efe21906109b09033906004016110fe565b60206040518083038186803b1580156109c857600080fd5b505afa1580156109dc573d6000803e3d6000fd5b505050506040513d601f19601f82011682018060405250810190610a00919061109b565b90506005811015610a235760405162461bcd60e51b815260040161037590611207565b6001546040516317d5759960e31b81526001600160a01b039091169063beabacc890610a589033903090600590600401611112565b602060405180830381600087803b158015610a7257600080fd5b505af1158015610a86573d6000803e3d6000fd5b505050506040513d601f19601f82011682018060405250810190610aaa9190610ffd565b610ac65760405162461bcd60e51b81526004016103759061135f565b6040805160808101825285815260208082018690526001828401819052606083015233600090815260028252929092208151805192939192610b0b9284920190610ed1565b50602082015160018201556040808301516002909201805460609094015115156101000261ff001993151560ff199095169490941792909216929092179055517ffcff7c819063c1e2e1d479afc40b32d8c0868ae1b9af6ead96979b538cecc21390610b7a9033908790611138565b60405180910390a150505050565b3360008181526002602081905260409091200154610100900460ff16610bc05760405162461bcd60e51b8152600401610375906112b9565b3360009081526002602090815260409091208451610be092860190610ed1565b503360008181526002602052604090819020600101849055517f712bd455f1a1f65f2b3ea94fcfe1728a77defaaa92dd601031b4422e6b42f9e19161056a918690869061115c565b600581565b6000546001600160a01b03163314610c575760405162461bcd60e51b815260040161037590611396565b600080546040516001600160a01b03909116914780156108fc02929091818181858888f19350505050158015610c91573d6000803e3d6000fd5b50565b6001600160a01b031660009081526002602052604090206001015490565b6000546001600160a01b03163314610cdc5760405162461bcd60e51b815260040161037590611396565b6001600160a01b038116610cef57600080fd5b600080546040516001600160a01b03808516939216917ff77c6eb92f5003da08a86ab833733c2f7f05480f4cc11bf57bf9fecb10873ad791a3600080546001600160a01b0319166001600160a01b0392909216919091179055565b610d52610ea5565b3360008181526002602081905260409091200154610100900460ff16610d8a5760405162461bcd60e51b8152600401610375906112b9565b336000908152600260208181526040928390208351815460a0601f60001961010060018516150201909216959095049081018490049093028101840190945260808401828152909284928491840182828015610e275780601f10610dfc57610100808354040283529160200191610e27565b820191906000526020600020905b815481529060010190602001808311610e0a57829003601f168201915b50505091835250506001820154602082015260029091015460ff8082161515604084015261010090910416151560609091015291505b5090565b50805460018160011615610100020316600290046000825580601f10610e875750610c91565b601f016020900490600052602060002090810190610c919190610f47565b604051806080016040528060608152602001600081526020016000151581526020016000151581525090565b828054600181600116156101000203166002900490600052602060002090601f016020900481019282601f10610f1257805160ff1916838001178555610f3f565b82800160010185558215610f3f579182015b82811115610f3f578251825591602001919060010190610f24565b50610e5d9291505b5b80821115610e5d5760008155600101610f48565b600082601f830112610f6c578081fd5b813567ffffffffffffffff80821115610f83578283fd5b604051601f8301601f191681016020018281118282101715610fa3578485fd5b604052828152925082848301602001861015610fbe57600080fd5b8260208601602083013760006020848301015250505092915050565b600060208284031215610feb578081fd5b8135610ff681611454565b9392505050565b60006020828403121561100e578081fd5b81518015158114610ff6578182fd5b60006020828403121561102e578081fd5b813567ffffffffffffffff811115611044578182fd5b61105084828501610f5c565b949350505050565b6000806040838503121561106a578081fd5b823567ffffffffffffffff811115611080578182fd5b61108c85828601610f5c565b95602094909401359450505050565b6000602082840312156110ac578081fd5b5051919050565b60008151808452815b818110156110d8576020818501810151868301820152016110bc565b818111156110e95782602083870101525b50601f01601f19169290920160200192915050565b6001600160a01b0391909116815260200190565b6001600160a01b03938416815291909216602082015260ff909116604082015260600190565b6001600160a01b0383168152604060208201819052600090611050908301846110b3565b6001600160a01b0384168152606060208201819052600090611180908301856110b3565b9050826040830152949350505050565b901515815260200190565b6000604082526111ae60408301856110b3565b905060ff831660208301529392505050565b60208082526027908201527f43657274696669636174696f6e20417574686f72697479206d75737420626520604082015266195b98589b195960ca1b606082015260800190565b60208082526044908201527f596f7520646f206e6f74206861766520656e6f75676820746f6b656e7320746f60408201527f2072656769737465722061732043657274696669636174696f6e20417574686f6060820152637269747960e01b608082015260a00190565b60208082526028908201527f43657274696669636174696f6e20417574686f72697479206d75737420626520604082015267191a5cd8589b195960c21b606082015260800190565b60208082526032908201527f43657274696669636174696f6e20417574686f72697479207769746820676976604082015271656e20696420646f6e27742065786973747360701b606082015260800190565b60208082526034908201527f43657274696669636174696f6e20417574686f72697479207769746820676976604082015273656e20696420616c72656164792065786973747360601b606082015260800190565b6020808252601e908201527f546865207472616e7366657220636f756c64206e6f74206265206d6164650000604082015260600190565b6020808252603c908201527f596f7520646f6e2774206861766520656e6f75676874207065726d697373696f60408201527f6e7320746f20657865637574652074686973206f7065726174696f6e00000000606082015260800190565b60006020825282516080602084015261140f60a08401826110b3565b9050602084015160408401526040840151151560608401526060840151151560808401528091505092915050565b90815260200190565b60ff91909116815260200190565b6001600160a01b0381168114610c9157600080fdfea26469706673582212209244995672c8f7aad85b3dce88403c66bbf8e306b5536d75a61727e8a5927ea864736f6c63430007010033";

    public static final String FUNC_ADD_CERTIFICATION_AUTHORITY_COST_IN_TCS_TOKENS = "ADD_CERTIFICATION_AUTHORITY_COST_IN_TCS_TOKENS";

    public static final String FUNC_DEFAULT_COST_OF_ISSUING_CERTIFICATE = "DEFAULT_COST_OF_ISSUING_CERTIFICATE";

    public static final String FUNC_addCertificationAuthority = "addCertificationAuthority";

    public static final String FUNC_COLLECT = "collect";

    public static final String FUNC_DISABLECERTIFICATIONAUTHORITY = "disableCertificationAuthority";

    public static final String FUNC_ENABLECERTIFICATIONAUTHORITY = "enableCertificationAuthority";

    public static final String FUNC_GETBALANCE = "getBalance";

    public static final String FUNC_getCertificateAuthorityDetail = "getCertificateAuthorityDetail";

    public static final String FUNC_GETDEFAULTCOSTOFISSUINGCERTIFICATE = "getDefaultCostOfIssuingCertificate";

    public static final String FUNC_ISCERTIFICATIONAUTHORITYENABLED = "isCertificationAuthorityEnabled";

    public static final String FUNC_ISCERTIFICATIONAUTHORITYEXISTS = "isCertificationAuthorityExists";

    public static final String FUNC_OWNINGAUTHORITY = "owningAuthority";

    public static final String FUNC_REMOVECERTIFICATIONAUTHORITY = "removeCertificationAuthority";

    public static final String FUNC_SETTOKENMANAGEMENTADDR = "setTokenManagementAddr";

    public static final String FUNC_TRANSFEROWNERSHIP = "transferOwnership";

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
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}, new TypeReference<Utf8String>() {}, new TypeReference<Uint256>() {}));
    ;

    public static final Event ONNEWCERTIFICATIONAUTHORITYCREATED_EVENT = new Event("OnNewCertificationAuthorityCreated", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}, new TypeReference<Utf8String>() {}));
    ;

    public static final Event ONOWNERSHIPTRANSFERRED_EVENT = new Event("OnOwnershipTransferred", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>(true) {}, new TypeReference<Address>(true) {}));
    ;

    @Deprecated
    protected CertificationAuthorityContract(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected CertificationAuthorityContract(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, credentials, contractGasProvider);
    }

    @Deprecated
    protected CertificationAuthorityContract(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    protected CertificationAuthorityContract(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
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
            typedResponse._defaultCostOfIssuingCertificate = (BigInteger) eventValues.getNonIndexedValues().get(2).getValue();
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
                typedResponse._defaultCostOfIssuingCertificate = (BigInteger) eventValues.getNonIndexedValues().get(2).getValue();
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
            typedResponse.name = (String) eventValues.getNonIndexedValues().get(1).getValue();
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
                typedResponse.name = (String) eventValues.getNonIndexedValues().get(1).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<OnNewCertificationAuthorityCreatedEventResponse> onNewCertificationAuthorityCreatedEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(ONNEWCERTIFICATIONAUTHORITYCREATED_EVENT));
        return onNewCertificationAuthorityCreatedEventFlowable(filter);
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

    public RemoteFunctionCall<BigInteger> ADD_CERTIFICATION_AUTHORITY_COST_IN_TCS_TOKENS() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_ADD_CERTIFICATION_AUTHORITY_COST_IN_TCS_TOKENS, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint8>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<BigInteger> DEFAULT_COST_OF_ISSUING_CERTIFICATE() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_DEFAULT_COST_OF_ISSUING_CERTIFICATE, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint8>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<TransactionReceipt> addCertificationAuthority(String _name) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_addCertificationAuthority, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Utf8String(_name)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> addCertificationAuthority(String _name, BigInteger _defaultCostOfIssuingCertificate) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_addCertificationAuthority, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Utf8String(_name), 
                new org.web3j.abi.datatypes.generated.Uint256(_defaultCostOfIssuingCertificate)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> collect() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_COLLECT, 
                Arrays.<Type>asList(), 
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

    public RemoteFunctionCall<BigInteger> getBalance() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_GETBALANCE, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
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

    public RemoteFunctionCall<String> owningAuthority() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_OWNINGAUTHORITY, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteFunctionCall<TransactionReceipt> removeCertificationAuthority(String _address) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_REMOVECERTIFICATIONAUTHORITY, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, _address)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> setTokenManagementAddr(String _tokenManagementAddr) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_SETTOKENMANAGEMENTADDR, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, _tokenManagementAddr)), 
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

    public RemoteFunctionCall<TransactionReceipt> updateCertificationAuthority(String _name, BigInteger _defaultCostOfIssuingCertificate) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_UPDATECERTIFICATIONAUTHORITY, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Utf8String(_name), 
                new org.web3j.abi.datatypes.generated.Uint256(_defaultCostOfIssuingCertificate)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    @Deprecated
    public static CertificationAuthorityContract load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new CertificationAuthorityContract(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    @Deprecated
    public static CertificationAuthorityContract load(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new CertificationAuthorityContract(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public static CertificationAuthorityContract load(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return new CertificationAuthorityContract(contractAddress, web3j, credentials, contractGasProvider);
    }

    public static CertificationAuthorityContract load(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return new CertificationAuthorityContract(contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static RemoteCall<CertificationAuthorityContract> deploy(Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return deployRemoteCall(CertificationAuthorityContract.class, web3j, credentials, contractGasProvider, BINARY, "");
    }

    @Deprecated
    public static RemoteCall<CertificationAuthorityContract> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(CertificationAuthorityContract.class, web3j, credentials, gasPrice, gasLimit, BINARY, "");
    }

    public static RemoteCall<CertificationAuthorityContract> deploy(Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return deployRemoteCall(CertificationAuthorityContract.class, web3j, transactionManager, contractGasProvider, BINARY, "");
    }

    @Deprecated
    public static RemoteCall<CertificationAuthorityContract> deploy(Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(CertificationAuthorityContract.class, web3j, transactionManager, gasPrice, gasLimit, BINARY, "");
    }

    public static class CertificationAuthorityRecord extends DynamicStruct {
        public String name;

        public BigInteger defaultCostOfIssuingCertificate;

        public Boolean isEnabled;

        public Boolean isExist;

        public CertificationAuthorityRecord(String name, BigInteger defaultCostOfIssuingCertificate, Boolean isEnabled, Boolean isExist) {
            super(new org.web3j.abi.datatypes.Utf8String(name),new org.web3j.abi.datatypes.generated.Uint256(defaultCostOfIssuingCertificate),new org.web3j.abi.datatypes.Bool(isEnabled),new org.web3j.abi.datatypes.Bool(isExist));
            this.name = name;
            this.defaultCostOfIssuingCertificate = defaultCostOfIssuingCertificate;
            this.isEnabled = isEnabled;
            this.isExist = isExist;
        }

        public CertificationAuthorityRecord(Utf8String name, Uint256 defaultCostOfIssuingCertificate, Bool isEnabled, Bool isExist) {
            super(name,defaultCostOfIssuingCertificate,isEnabled,isExist);
            this.name = name.getValue();
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

        public BigInteger _defaultCostOfIssuingCertificate;
    }

    public static class OnNewCertificationAuthorityCreatedEventResponse extends BaseEventResponse {
        public String _address;

        public String name;
    }

    public static class OnOwnershipTransferredEventResponse extends BaseEventResponse {
        public String previousOwner;

        public String newOwner;
    }
}
