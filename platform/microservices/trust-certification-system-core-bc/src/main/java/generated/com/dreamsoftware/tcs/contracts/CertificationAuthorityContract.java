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
    public static final String BINARY = "608060405234801561001057600080fd5b50600080546001600160a01b03191633179055611f21806100326000396000f3fe6080604052600436106101145760003560e01c80638c352295116100a0578063e522538111610064578063e5225381146102ee578063eebbaf5814610303578063f2fde38b14610323578063f6a8b4eb14610343578063f90a7a161461036357610114565b80638c3522951461024c5780639146014c14610279578063942fcdd814610299578063a3e6194e146102b9578063dc580b3d146102d957610114565b8063567193f9116100e7578063567193f9146101aa5780635fd94b68146101ca5780636aeea31e146101dd5780636c2ab70d1461020a57806384f085261461022a57610114565b80631074cea31461011957806312065fe01461013b578063178d318b146101665780631b85318214610188575b600080fd5b34801561012557600080fd5b50610139610134366004611824565b610378565b005b34801561014757600080fd5b5061015061044d565b60405161015d9190611c02565b60405180910390f35b34801561017257600080fd5b5061017b61047d565b60405161015d9190611b97565b34801561019457600080fd5b5061019d6107f4565b60405161015d9190611adc565b3480156101b657600080fd5b506101396101c5366004611824565b610803565b6101396101d8366004611824565b610905565b3480156101e957600080fd5b506101fd6101f8366004611824565b610951565b60405161015d9190611bf7565b34801561021657600080fd5b50610139610225366004611824565b610977565b34801561023657600080fd5b5061023f610a68565b60405161015d9190611e51565b34801561025857600080fd5b5061026c610267366004611824565b610a6d565b60405161015d9190611e3e565b34801561028557600080fd5b506101fd610294366004611824565b610d9c565b3480156102a557600080fd5b506101396102b436600461195e565b610e01565b3480156102c557600080fd5b506101396102d436600461195e565b610f01565b3480156102e557600080fd5b5061023f610f4c565b3480156102fa57600080fd5b50610139610f51565b34801561030f57600080fd5b5061015061031e366004611824565b610fb8565b34801561032f57600080fd5b5061013961033e366004611824565b610fd6565b34801561034f57600080fd5b5061013961035e3660046118da565b61106e565b34801561036f57600080fd5b5061026c6110b9565b6000546001600160a01b031633146103ab5760405162461bcd60e51b81526004016103a290611de1565b60405180910390fd5b6001600160a01b0381166000908152600260205260409020600501548190610100900460ff166103ed5760405162461bcd60e51b81526004016103a290611d04565b6001600160a01b03821660009081526002602052604090819020600501805461ff0019169055517f14a1355acd9909e848e2fcc10757bee6a3dac4719dbc121aa3a339595e15e2e290610441908490611adc565b60405180910390a15050565b600080546001600160a01b031633146104785760405162461bcd60e51b81526004016103a290611de1565b504790565b6000546060906001600160a01b031633146104aa5760405162461bcd60e51b81526004016103a290611de1565b60035460609067ffffffffffffffff811180156104c657600080fd5b5060405190808252806020026020018201604052801561050057816020015b6104ed611706565b8152602001906001900390816104e55790505b50905060005b6003548110156107ee57600260006003838154811061052157fe5b6000918252602080832091909101546001600160a01b031683528281019390935260409182019020815181546002610100600183161581026000190190921604601f81018690049095028201810190935260e0810184815290939192849284918401828280156105d25780601f106105a7576101008083540402835291602001916105d2565b820191906000526020600020905b8154815290600101906020018083116105b557829003601f168201915b50505050508152602001600182018054600181600116156101000203166002900480601f0160208091040260200160405190810160405280929190818152602001828054600181600116156101000203166002900480156106745780601f1061064957610100808354040283529160200191610674565b820191906000526020600020905b81548152906001019060200180831161065757829003601f168201915b5050509183525050600282810180546040805160206001841615610100026000190190931694909404601f810183900483028501830190915280845293810193908301828280156107065780601f106106db57610100808354040283529160200191610706565b820191906000526020600020905b8154815290600101906020018083116106e957829003601f168201915b505050918352505060038201805460408051602060026001851615610100026000190190941693909304601f810184900484028201840190925281815293820193929183018282801561079a5780601f1061076f5761010080835404028352916020019161079a565b820191906000526020600020905b81548152906001019060200180831161077d57829003601f168201915b50505091835250506004820154602082015260059091015460ff8082161515604084015261010090910416151560609091015282518390839081106107db57fe5b6020908102919091010152600101610506565b50905090565b6000546001600160a01b031690565b6000546001600160a01b0316331461082d5760405162461bcd60e51b81526004016103a290611de1565b3360008181526002602052604090206005015460ff16156108605760405162461bcd60e51b81526004016103a290611cbc565b6001600160a01b0382166000908152600260205260409020600501548290610100900460ff166108a25760405162461bcd60e51b81526004016103a290611d04565b6001600160a01b03831660009081526002602052604090819020600501805460ff19166001179055517f70181c3b508c5e190ae6ba2c5c8659ce2b8c05dc712f95787bbc5effd6ea7677906108f8908590611adc565b60405180910390a1505050565b6000546001600160a01b0316331461092f5760405162461bcd60e51b81526004016103a290611de1565b600180546001600160a01b0319166001600160a01b0392909216919091179055565b6001600160a01b0316600090815260026020526040902060050154610100900460ff1690565b6000546001600160a01b031633146109a15760405162461bcd60e51b81526004016103a290611de1565b3360008181526002602052604090206005015460ff166109d35760405162461bcd60e51b81526004016103a290611c0b565b6001600160a01b0382166000908152600260205260409020600501548290610100900460ff16610a155760405162461bcd60e51b81526004016103a290611d04565b6001600160a01b03831660009081526002602052604090819020600501805460ff19169055517f19f5ee6faef1186424c03768eabd96d0e61384a9bef9e8903088acf6f93d0298906108f8908590611adc565b600481565b610a75611706565b6001600160a01b0382166000908152600260205260409020600501548290610100900460ff16610ab75760405162461bcd60e51b81526004016103a290611d04565b6001600160a01b038316600090815260026020526040902060050154839060ff16610af45760405162461bcd60e51b81526004016103a290611c0b565b6001600160a01b0384166000908152600260208181526040928390208351815460018116156101009081026000190190911694909404601f81018490049093028101840190945260e08401828152909284928491840182828015610b995780601f10610b6e57610100808354040283529160200191610b99565b820191906000526020600020905b815481529060010190602001808311610b7c57829003601f168201915b50505050508152602001600182018054600181600116156101000203166002900480601f016020809104026020016040519081016040528092919081815260200182805460018160011615610100020316600290048015610c3b5780601f10610c1057610100808354040283529160200191610c3b565b820191906000526020600020905b815481529060010190602001808311610c1e57829003601f168201915b5050509183525050600282810180546040805160206001841615610100026000190190931694909404601f81018390048302850183019091528084529381019390830182828015610ccd5780601f10610ca257610100808354040283529160200191610ccd565b820191906000526020600020905b815481529060010190602001808311610cb057829003601f168201915b505050918352505060038201805460408051602060026001851615610100026000190190941693909304601f8101849004840282018401909252818152938201939291830182828015610d615780601f10610d3657610100808354040283529160200191610d61565b820191906000526020600020905b815481529060010190602001808311610d4457829003601f168201915b50505091835250506004820154602082015260059091015460ff80821615156040840152610100909104161515606090910152949350505050565b6001600160a01b0381166000908152600260205260408120600501548290610100900460ff16610dde5760405162461bcd60e51b81526004016103a290611d04565b50506001600160a01b031660009081526002602052604090206005015460ff1690565b33600081815260026020526040902060050154610100900460ff16610e385760405162461bcd60e51b81526004016103a290611d04565b3360009081526002602090815260409091208651610e5e92600190920191880190611747565b503360009081526002602081815260409092208651610e8593919092019190870190611747565b503360009081526002602090815260409091208451610eac92600390920191860190611747565b503360008181526002602052604090819020600401849055517fba079ed6b1c523b9559ee64df8f1867c2ae70d16fa226f7d0a708e66b20a66ef91610ef2918890611b16565b60405180910390a15050505050565b33600081815260026020526040902060050154610100900460ff1615610f395760405162461bcd60e51b81526004016103a290611d56565b610f4585858585611396565b5050505050565b600581565b6000546001600160a01b03163314610f7b5760405162461bcd60e51b81526004016103a290611de1565b600080546040516001600160a01b03909116914780156108fc02929091818181858888f19350505050158015610fb5573d6000803e3d6000fd5b50565b6001600160a01b031660009081526002602052604090206004015490565b6000546001600160a01b031633146110005760405162461bcd60e51b81526004016103a290611de1565b6001600160a01b03811661101357600080fd5b600080546040516001600160a01b03808516939216917ff77c6eb92f5003da08a86ab833733c2f7f05480f4cc11bf57bf9fecb10873ad791a3600080546001600160a01b0319166001600160a01b0392909216919091179055565b33600081815260026020526040902060050154610100900460ff16156110a65760405162461bcd60e51b81526004016103a290611d56565b6110b38484846004611396565b50505050565b6110c1611706565b33600081815260026020526040902060050154610100900460ff166110f85760405162461bcd60e51b81526004016103a290611d04565b336000908152600260208181526040928390208351815460018116156101009081026000190190911694909404601f81018490049093028101840190945260e084018281529092849284918401828280156111945780601f1061116957610100808354040283529160200191611194565b820191906000526020600020905b81548152906001019060200180831161117757829003601f168201915b50505050508152602001600182018054600181600116156101000203166002900480601f0160208091040260200160405190810160405280929190818152602001828054600181600116156101000203166002900480156112365780601f1061120b57610100808354040283529160200191611236565b820191906000526020600020905b81548152906001019060200180831161121957829003601f168201915b5050509183525050600282810180546040805160206001841615610100026000190190931694909404601f810183900483028501830190915280845293810193908301828280156112c85780601f1061129d576101008083540402835291602001916112c8565b820191906000526020600020905b8154815290600101906020018083116112ab57829003601f168201915b505050918352505060038201805460408051602060026001851615610100026000190190941693909304601f810184900484028201840190925281815293820193929183018282801561135c5780601f106113315761010080835404028352916020019161135c565b820191906000526020600020905b81548152906001019060200180831161133f57829003601f168201915b50505091835250506004820154602082015260059091015460ff8082161515604084015261010090910416151560609091015291505b5090565b60015460405163450efe2160e01b81526000916001600160a01b03169063450efe21906113c7903390600401611adc565b60206040518083038186803b1580156113df57600080fd5b505afa1580156113f3573d6000803e3d6000fd5b505050506040513d601f19601f8201168201806040525081019061141791906119ea565b9050600581101561143a5760405162461bcd60e51b81526004016103a290611c52565b6001546040516317d5759960e31b81526001600160a01b039091169063beabacc89061146f9033903090600590600401611af0565b602060405180830381600087803b15801561148957600080fd5b505af115801561149d573d6000803e3d6000fd5b505050506040513d601f19601f820116820180604052508101906114c19190611847565b6114dd5760405162461bcd60e51b81526004016103a290611daa565b606073__$d0bc08d74c675cf72d6ed4f0104203ec25$__639201de553360405160200161150a9190611abf565b604051602081830303815290604052805190602001206040518263ffffffff1660e01b815260040161153c9190611c02565b60006040518083038186803b15801561155457600080fd5b505af4158015611568573d6000803e3d6000fd5b505050506040513d6000823e601f3d908101601f191682016040526115909190810190611867565b6040805160e08101825282815260208082018a90528183018990526060820188905260808201879052600160a0830181905260c0830152336000908152600282529290922081518051949550919390926115ee928492910190611747565b5060208281015180516116079260018501920190611747565b5060408201518051611623916002840191602090910190611747565b506060820151805161163f916003840191602090910190611747565b506080820151600482015560a08201516005909101805460c09093015115156101000261ff001992151560ff199094169390931791909116919091179055600380546001810182556000919091527fc2575a0e9e593c00f959f8c92f12db2869c3395a3b0502d05e2516446f71f85b0180546001600160a01b031916339081179091556040517f630687cebe5f7bad4e6895640fae5b9806e7eaaf69625ea48b29bdfaa706eab5916116f691899089908990611b42565b60405180910390a1505050505050565b6040518060e0016040528060608152602001606081526020016060815260200160608152602001600081526020016000151581526020016000151581525090565b828054600181600116156101000203166002900490600052602060002090601f016020900481019282601f1061178857805160ff19168380011785556117b5565b828001600101855582156117b5579182015b828111156117b557825182559160200191906001019061179a565b506113929291505b8082111561139257600081556001016117bd565b600082601f8301126117e1578081fd5b81356117f46117ef82611e86565b611e5f565b915080825283602082850101111561180b57600080fd5b8060208401602084013760009082016020015292915050565b600060208284031215611835578081fd5b813561184081611ed6565b9392505050565b600060208284031215611858578081fd5b81518015158114611840578182fd5b600060208284031215611878578081fd5b815167ffffffffffffffff81111561188e578182fd5b8201601f8101841361189e578182fd5b80516118ac6117ef82611e86565b8181528560208385010111156118c0578384fd5b6118d1826020830160208601611eaa565b95945050505050565b6000806000606084860312156118ee578182fd5b833567ffffffffffffffff80821115611905578384fd5b611911878388016117d1565b94506020860135915080821115611926578384fd5b611932878388016117d1565b93506040860135915080821115611947578283fd5b50611954868287016117d1565b9150509250925092565b60008060008060808587031215611973578081fd5b843567ffffffffffffffff8082111561198a578283fd5b611996888389016117d1565b955060208701359150808211156119ab578283fd5b6119b7888389016117d1565b945060408701359150808211156119cc578283fd5b506119d9878288016117d1565b949793965093946060013593505050565b6000602082840312156119fb578081fd5b5051919050565b60008151808452611a1a816020860160208601611eaa565b601f01601f19169290920160200192915050565b6000815160e08452611a4360e0850182611a02565b905060208301518482036020860152611a5c8282611a02565b91505060408301518482036040860152611a768282611a02565b91505060608301518482036060860152611a908282611a02565b9150506080830151608085015260a0830151151560a085015260c0830151151560c08501528091505092915050565b60609190911b6bffffffffffffffffffffffff1916815260140190565b6001600160a01b0391909116815260200190565b6001600160a01b03938416815291909216602082015260ff909116604082015260600190565b6001600160a01b0383168152604060208201819052600090611b3a90830184611a02565b949350505050565b6001600160a01b0385168152608060208201819052600090611b6690830186611a02565b8281036040840152611b788186611a02565b90508281036060840152611b8c8185611a02565b979650505050505050565b6000602080830181845280855180835260408601915060408482028701019250838701855b82811015611bea57603f19888603018452611bd8858351611a2e565b94509285019290850190600101611bbc565b5092979650505050505050565b901515815260200190565b90815260200190565b60208082526027908201527f43657274696669636174696f6e20417574686f72697479206d75737420626520604082015266195b98589b195960ca1b606082015260800190565b60208082526044908201527f596f7520646f206e6f74206861766520656e6f75676820746f6b656e7320746f60408201527f2072656769737465722061732043657274696669636174696f6e20417574686f6060820152637269747960e01b608082015260a00190565b60208082526028908201527f43657274696669636174696f6e20417574686f72697479206d75737420626520604082015267191a5cd8589b195960c21b606082015260800190565b60208082526032908201527f43657274696669636174696f6e20417574686f72697479207769746820676976604082015271656e20696420646f6e27742065786973747360701b606082015260800190565b60208082526034908201527f43657274696669636174696f6e20417574686f72697479207769746820676976604082015273656e20696420616c72656164792065786973747360601b606082015260800190565b6020808252601e908201527f546865207472616e7366657220636f756c64206e6f74206265206d6164650000604082015260600190565b6020808252603c908201527f596f7520646f6e2774206861766520656e6f75676874207065726d697373696f60408201527f6e7320746f20657865637574652074686973206f7065726174696f6e00000000606082015260800190565b6000602082526118406020830184611a2e565b60ff91909116815260200190565b60405181810167ffffffffffffffff81118282101715611e7e57600080fd5b604052919050565b600067ffffffffffffffff821115611e9c578081fd5b50601f01601f191660200190565b60005b83811015611ec5578181015183820152602001611ead565b838111156110b35750506000910152565b6001600160a01b0381168114610fb557600080fdfea264697066735822122089eac21729e8626434591ba92fc864f73abe9f755ff767717a37474baef541dc64736f6c63430007010033";

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
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}, new TypeReference<Utf8String>() {}));
    ;

    public static final Event ONNEWCERTIFICATIONAUTHORITYCREATED_EVENT = new Event("OnNewCertificationAuthorityCreated", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}, new TypeReference<Utf8String>() {}, new TypeReference<Utf8String>() {}, new TypeReference<Utf8String>() {}));
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

    public static class OnOwnershipTransferredEventResponse extends BaseEventResponse {
        public String previousOwner;

        public String newOwner;
    }
}
