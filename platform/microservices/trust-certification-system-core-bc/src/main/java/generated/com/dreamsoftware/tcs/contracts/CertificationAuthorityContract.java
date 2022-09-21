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
    public static final String BINARY = "608060405234801561001057600080fd5b50600080546001600160a01b03191633179055611666806100326000396000f3fe6080604052600436106101145760003560e01c80638a765457116100a0578063dc580b3d11610064578063dc580b3d146102f9578063e52253811461030e578063eebbaf5814610323578063f2fde38b14610343578063f90a7a161461036357610114565b80638a7654571461024c5780638c3522951461026c5780639146014c14610299578063a3307132146102b9578063a56593d6146102d957610114565b8063567193f9116100e7578063567193f9146101aa5780635fd94b68146101ca5780636aeea31e146101dd5780636c2ab70d1461020a57806384f085261461022a57610114565b80631074cea31461011957806312065fe01461013b578063178d318b146101665780631b85318214610188575b600080fd5b34801561012557600080fd5b5061013961013436600461115a565b610378565b005b34801561014757600080fd5b5061015061044e565b60405161015d9190611604565b60405180910390f35b34801561017257600080fd5b5061017b61047e565b60405161015d9190611353565b34801561019457600080fd5b5061019d61062f565b60405161015d91906112c1565b3480156101b657600080fd5b506101396101c536600461115a565b61063e565b6101396101d836600461115a565b610742565b3480156101e957600080fd5b506101fd6101f836600461115a565b61078e565b60405161015d91906113b3565b34801561021657600080fd5b5061013961022536600461115a565b6107b5565b34801561023657600080fd5b5061023f6108a8565b60405161015d919061160d565b34801561025857600080fd5b5061013961026736600461119d565b6108ad565b34801561027857600080fd5b5061028c61028736600461115a565b6108f5565b60405161015d91906115f1565b3480156102a557600080fd5b506101fd6102b436600461115a565b610a5f565b3480156102c557600080fd5b506101396102d43660046111d8565b610ac5565b3480156102e557600080fd5b506101396102f43660046111d8565b610b0d565b34801561030557600080fd5b5061023f610bad565b34801561031a57600080fd5b50610139610bb2565b34801561032f57600080fd5b5061015061033e36600461115a565b610c19565b34801561034f57600080fd5b5061013961035e36600461115a565b610c37565b34801561036f57600080fd5b5061028c610ccf565b6000546001600160a01b031633146103ab5760405162461bcd60e51b81526004016103a290611594565b60405180910390fd5b6001600160a01b038116600090815260026020819052604090912001548190610100900460ff166103ee5760405162461bcd60e51b81526004016103a2906114b7565b6001600160a01b03821660009081526002602081905260409182902001805461ff0019169055517f14a1355acd9909e848e2fcc10757bee6a3dac4719dbc121aa3a339595e15e2e2906104429084906112c1565b60405180910390a15050565b600080546001600160a01b031633146104795760405162461bcd60e51b81526004016103a290611594565b504790565b6000546060906001600160a01b031633146104ab5760405162461bcd60e51b81526004016103a290611594565b60035460609067ffffffffffffffff811180156104c757600080fd5b5060405190808252806020026020018201604052801561050157816020015b6104ee611026565b8152602001906001900390816104e65790505b50905060005b60035481101561062957600260006003838154811061052257fe5b6000918252602080832091909101546001600160a01b0316835282810193909352604091820190208151815460026101006001831615026000190190911604601f8101859004909402810160a09081019093526080810184815290939192849284918401828280156105d55780601f106105aa576101008083540402835291602001916105d5565b820191906000526020600020905b8154815290600101906020018083116105b857829003601f168201915b50505091835250506001820154602082015260029091015460ff80821615156040840152610100909104161515606090910152825183908390811061061657fe5b6020908102919091010152600101610507565b50905090565b6000546001600160a01b031690565b6000546001600160a01b031633146106685760405162461bcd60e51b81526004016103a290611594565b336000818152600260208190526040909120015460ff161561069c5760405162461bcd60e51b81526004016103a29061146f565b6001600160a01b038216600090815260026020819052604090912001548290610100900460ff166106df5760405162461bcd60e51b81526004016103a2906114b7565b6001600160a01b03831660009081526002602081905260409182902001805460ff19166001179055517f70181c3b508c5e190ae6ba2c5c8659ce2b8c05dc712f95787bbc5effd6ea7677906107359085906112c1565b60405180910390a1505050565b6000546001600160a01b0316331461076c5760405162461bcd60e51b81526004016103a290611594565b600180546001600160a01b0319166001600160a01b0392909216919091179055565b6001600160a01b031660009081526002602081905260409091200154610100900460ff1690565b6000546001600160a01b031633146107df5760405162461bcd60e51b81526004016103a290611594565b336000818152600260208190526040909120015460ff166108125760405162461bcd60e51b81526004016103a2906113be565b6001600160a01b038216600090815260026020819052604090912001548290610100900460ff166108555760405162461bcd60e51b81526004016103a2906114b7565b6001600160a01b03831660009081526002602081905260409182902001805460ff19169055517f19f5ee6faef1186424c03768eabd96d0e61384a9bef9e8903088acf6f93d0298906107359085906112c1565b600481565b3360008181526002602081905260409091200154610100900460ff16156108e65760405162461bcd60e51b81526004016103a290611509565b6108f1826004610de6565b5050565b6108fd611026565b6001600160a01b038216600090815260026020819052604090912001548290610100900460ff166109405760405162461bcd60e51b81526004016103a2906114b7565b6001600160a01b03831660009081526002602081905260409091200154839060ff1661097e5760405162461bcd60e51b81526004016103a2906113be565b6001600160a01b0384166000908152600260208181526040928390208351815460a0601f60001961010060018516150201909216959095049081018490049093028101840190945260808401828152909284928491840182828015610a245780601f106109f957610100808354040283529160200191610a24565b820191906000526020600020905b815481529060010190602001808311610a0757829003601f168201915b50505091835250506001820154602082015260029091015460ff80821615156040840152610100909104161515606090910152949350505050565b6001600160a01b0381166000908152600260208190526040822001548290610100900460ff16610aa15760405162461bcd60e51b81526004016103a2906114b7565b50506001600160a01b03166000908152600260208190526040909120015460ff1690565b3360008181526002602081905260409091200154610100900460ff1615610afe5760405162461bcd60e51b81526004016103a290611509565b610b088383610de6565b505050565b3360008181526002602081905260409091200154610100900460ff16610b455760405162461bcd60e51b81526004016103a2906114b7565b3360009081526002602090815260409091208451610b6592860190611052565b503360008181526002602052604090819020600101849055517f712bd455f1a1f65f2b3ea94fcfe1728a77defaaa92dd601031b4422e6b42f9e191610735918690869061131f565b600581565b6000546001600160a01b03163314610bdc5760405162461bcd60e51b81526004016103a290611594565b600080546040516001600160a01b03909116914780156108fc02929091818181858888f19350505050158015610c16573d6000803e3d6000fd5b50565b6001600160a01b031660009081526002602052604090206001015490565b6000546001600160a01b03163314610c615760405162461bcd60e51b81526004016103a290611594565b6001600160a01b038116610c7457600080fd5b600080546040516001600160a01b03808516939216917ff77c6eb92f5003da08a86ab833733c2f7f05480f4cc11bf57bf9fecb10873ad791a3600080546001600160a01b0319166001600160a01b0392909216919091179055565b610cd7611026565b3360008181526002602081905260409091200154610100900460ff16610d0f5760405162461bcd60e51b81526004016103a2906114b7565b336000908152600260208181526040928390208351815460a0601f60001961010060018516150201909216959095049081018490049093028101840190945260808401828152909284928491840182828015610dac5780601f10610d8157610100808354040283529160200191610dac565b820191906000526020600020905b815481529060010190602001808311610d8f57829003601f168201915b50505091835250506001820154602082015260029091015460ff8082161515604084015261010090910416151560609091015291505b5090565b60015460405163450efe2160e01b81526000916001600160a01b03169063450efe2190610e179033906004016112c1565b60206040518083038186803b158015610e2f57600080fd5b505afa158015610e43573d6000803e3d6000fd5b505050506040513d601f19601f82011682018060405250810190610e67919061121b565b90506005811015610e8a5760405162461bcd60e51b81526004016103a290611405565b6001546040516317d5759960e31b81526001600160a01b039091169063beabacc890610ebf90339030906005906004016112d5565b602060405180830381600087803b158015610ed957600080fd5b505af1158015610eed573d6000803e3d6000fd5b505050506040513d601f19601f82011682018060405250810190610f11919061117d565b610f2d5760405162461bcd60e51b81526004016103a29061155d565b6040805160808101825284815260208082018590526001828401819052606083015233600090815260028252929092208151805192939192610f729284920190611052565b5060208201516001808301919091556040808401516002909301805460609095015115156101000261ff001994151560ff199096169590951793909316939093179091556003805491820181556000527fc2575a0e9e593c00f959f8c92f12db2869c3395a3b0502d05e2516446f71f85b0180546001600160a01b0319163390811790915590517ffcff7c819063c1e2e1d479afc40b32d8c0868ae1b9af6ead96979b538cecc213916107359186906112fb565b604051806080016040528060608152602001600081526020016000151581526020016000151581525090565b828054600181600116156101000203166002900490600052602060002090601f016020900481019282601f1061109357805160ff19168380011785556110c0565b828001600101855582156110c0579182015b828111156110c05782518255916020019190600101906110a5565b50610de29291505b80821115610de257600081556001016110c8565b600082601f8301126110ec578081fd5b813567ffffffffffffffff80821115611103578283fd5b604051601f8301601f191681016020018281118282101715611123578485fd5b60405282815292508284830160200186101561113e57600080fd5b8260208601602083013760006020848301015250505092915050565b60006020828403121561116b578081fd5b81356111768161161b565b9392505050565b60006020828403121561118e578081fd5b81518015158114611176578182fd5b6000602082840312156111ae578081fd5b813567ffffffffffffffff8111156111c4578182fd5b6111d0848285016110dc565b949350505050565b600080604083850312156111ea578081fd5b823567ffffffffffffffff811115611200578182fd5b61120c858286016110dc565b95602094909401359450505050565b60006020828403121561122c578081fd5b5051919050565b60008151808452815b818110156112585760208185018101518683018201520161123c565b818111156112695782602083870101525b50601f01601f19169290920160200192915050565b60008151608084526112936080850182611233565b9050602083015160208501526040830151151560408501526060830151151560608501528091505092915050565b6001600160a01b0391909116815260200190565b6001600160a01b03938416815291909216602082015260ff909116604082015260600190565b6001600160a01b03831681526040602082018190526000906111d090830184611233565b6001600160a01b038416815260606020820181905260009061134390830185611233565b9050826040830152949350505050565b6000602080830181845280855180835260408601915060408482028701019250838701855b828110156113a657603f1988860301845261139485835161127e565b94509285019290850190600101611378565b5092979650505050505050565b901515815260200190565b60208082526027908201527f43657274696669636174696f6e20417574686f72697479206d75737420626520604082015266195b98589b195960ca1b606082015260800190565b60208082526044908201527f596f7520646f206e6f74206861766520656e6f75676820746f6b656e7320746f60408201527f2072656769737465722061732043657274696669636174696f6e20417574686f6060820152637269747960e01b608082015260a00190565b60208082526028908201527f43657274696669636174696f6e20417574686f72697479206d75737420626520604082015267191a5cd8589b195960c21b606082015260800190565b60208082526032908201527f43657274696669636174696f6e20417574686f72697479207769746820676976604082015271656e20696420646f6e27742065786973747360701b606082015260800190565b60208082526034908201527f43657274696669636174696f6e20417574686f72697479207769746820676976604082015273656e20696420616c72656164792065786973747360601b606082015260800190565b6020808252601e908201527f546865207472616e7366657220636f756c64206e6f74206265206d6164650000604082015260600190565b6020808252603c908201527f596f7520646f6e2774206861766520656e6f75676874207065726d697373696f60408201527f6e7320746f20657865637574652074686973206f7065726174696f6e00000000606082015260800190565b600060208252611176602083018461127e565b90815260200190565b60ff91909116815260200190565b6001600160a01b0381168114610c1657600080fdfea2646970667358221220e238cd47d66d05db279a352779245ee783c22ce781c837ade345334881c3e7a264736f6c63430007010033";

    public static final String FUNC_ADD_CERTIFICATION_AUTHORITY_COST_IN_TCS_TOKENS = "ADD_CERTIFICATION_AUTHORITY_COST_IN_TCS_TOKENS";

    public static final String FUNC_DEFAULT_COST_OF_ISSUING_CERTIFICATE = "DEFAULT_COST_OF_ISSUING_CERTIFICATE";

    public static final String FUNC_addCertificationAuthority = "addCertificationAuthority";

    public static final String FUNC_COLLECT = "collect";

    public static final String FUNC_DISABLECERTIFICATIONAUTHORITY = "disableCertificationAuthority";

    public static final String FUNC_ENABLECERTIFICATIONAUTHORITY = "enableCertificationAuthority";

    public static final String FUNC_GETALLCERTIFICATIONAUTHORITIES = "getAllCertificationAuthorities";

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
