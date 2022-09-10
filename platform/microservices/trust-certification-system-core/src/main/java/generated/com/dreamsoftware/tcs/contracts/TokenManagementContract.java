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
import org.web3j.abi.datatypes.Event;
import org.web3j.abi.datatypes.Type;
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
import org.web3j.tuples.generated.Tuple3;
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
public class TokenManagementContract extends Contract {
    public static final String BINARY = "6080604052601e600255600a600355600360045534801561001f57600080fd5b50600080546001600160a01b0319163317905560405163773594009061004490610090565b61004e919061009d565b604051809103906000f08015801561006a573d6000803e3d6000fd5b50600180546001600160a01b0319166001600160a01b03929092169190911790556100a6565b6106a880610dda83390190565b90815260200190565b610d25806100b56000396000f3fe6080604052600436106100f35760003560e01c806361eed2a91161008a578063cdccedf911610059578063cdccedf91461026a578063dbb8a6081461028a578063e52253811461029f578063f2fde38b146102b4576100f3565b806361eed2a9146101d9578063722713f714610208578063beabacc81461021d578063ca01ba391461024a576100f3565b806341c16ea5116100c657806341c16ea51461017a578063450efe211461018f578063598823b0146101af5780635b3b136a146101c4576100f3565b806312065fe0146100f85780631b853182146101235780633610724e146101455780633fe03b171461015a575b600080fd5b34801561010457600080fd5b5061010d6102d4565b60405161011a9190610cab565b60405180910390f35b34801561012f57600080fd5b5061013861030d565b60405161011a9190610ac7565b610158610153366004610a97565b61031c565b005b34801561016657600080fd5b506101586101753660046109fc565b610447565b34801561018657600080fd5b5061010d610617565b34801561019b57600080fd5b5061010d6101aa3660046109d9565b61061d565b3480156101bb57600080fd5b5061010d6106a4565b3480156101d057600080fd5b5061010d6106aa565b3480156101e557600080fd5b506101f96101f43660046109d9565b610730565b60405161011a93929190610cb4565b34801561021457600080fd5b5061010d610754565b34801561022957600080fd5b5061023d610238366004610a37565b610785565b60405161011a9190610b18565b34801561025657600080fd5b50610158610265366004610a97565b610839565b34801561027657600080fd5b5061010d610285366004610a97565b6108c8565b34801561029657600080fd5b5061010d6108d4565b3480156102ab57600080fd5b506101586108da565b3480156102c057600080fd5b506101586102cf3660046109d9565b610941565b600080546001600160a01b031633146103085760405162461bcd60e51b81526004016102ff90610c0d565b60405180910390fd5b504790565b6000546001600160a01b031690565b610324610754565b8111156103435760405162461bcd60e51b81526004016102ff90610b5a565b600061034e826108c8565b9050803410156103705760405162461bcd60e51b81526004016102ff90610c6a565b60405133903483900380156108fc02916000818181858888f1935050505015801561039f573d6000803e3d6000fd5b5060015460405163a9059cbb60e01b81526001600160a01b039091169063a9059cbb906103d29033908690600401610adb565b602060405180830381600087803b1580156103ec57600080fd5b505af1158015610400573d6000803e3d6000fd5b505050506040513d601f19601f820116820180604052508101906104249190610a77565b505033600090815260066020526040902080548201815560010180549091019055565b6000546001600160a01b031633146104715760405162461bcd60e51b81526004016102ff90610c0d565b6001600160a01b038216600090815260056020526040902054829060ff16156104ac5760405162461bcd60e51b81526004016102ff90610b23565b600060028360028111156104bc57fe5b14156104cb57506002546104ed565b60008360028111156104d957fe5b14156104e857506003546104ed565b506004545b6104f5610754565b8111156105145760405162461bcd60e51b81526004016102ff90610b5a565b60015460405163a9059cbb60e01b81526001600160a01b039091169063a9059cbb906105469087908590600401610adb565b602060405180830381600087803b15801561056057600080fd5b505af1158015610574573d6000803e3d6000fd5b505050506040513d601f19601f820116820180604052508101906105989190610a77565b6105b45760405162461bcd60e51b81526004016102ff90610bd6565b6001600160a01b03841660009081526005602090815260408083208054600160ff1991821681179092556006909352922080548401815580830180548501905560029081018054879491931691849081111561060c57fe5b021790555050505050565b60025481565b6001546040516370a0823160e01b81526000916001600160a01b0316906370a082319061064e908590600401610ac7565b60206040518083038186803b15801561066657600080fd5b505afa15801561067a573d6000803e3d6000fd5b505050506040513d601f19601f8201168201806040525081019061069e9190610aaf565b92915050565b60045481565b6001546040516370a0823160e01b81526000916001600160a01b0316906370a08231906106db903390600401610ac7565b60206040518083038186803b1580156106f357600080fd5b505afa158015610707573d6000803e3d6000fd5b505050506040513d601f19601f8201168201806040525081019061072b9190610aaf565b905090565b60066020526000908152604090208054600182015460029092015490919060ff1683565b6001546040516370a0823160e01b81526000916001600160a01b0316906370a08231906106db903090600401610ac7565b6001546040516317d5759960e31b81526000916001600160a01b03169063beabacc8906107ba90879087908790600401610af4565b602060405180830381600087803b1580156107d457600080fd5b505af11580156107e8573d6000803e3d6000fd5b505050506040513d601f19601f8201168201806040525081019061080c9190610a77565b50506001600160a01b03831660009081526006602052604090206001908101805483900390559392505050565b6000546001600160a01b031633146108635760405162461bcd60e51b81526004016102ff90610c0d565b6001546040516303a8799360e31b81526001600160a01b0390911690631d43cc9890610893908490600401610cab565b600060405180830381600087803b1580156108ad57600080fd5b505af11580156108c1573d6000803e3d6000fd5b5050505050565b662386f26fc100000290565b60035481565b6000546001600160a01b031633146109045760405162461bcd60e51b81526004016102ff90610c0d565b600080546040516001600160a01b03909116914780156108fc02929091818181858888f1935050505015801561093e573d6000803e3d6000fd5b50565b6000546001600160a01b0316331461096b5760405162461bcd60e51b81526004016102ff90610c0d565b6001600160a01b03811661097e57600080fd5b600080546040516001600160a01b03808516939216917ff77c6eb92f5003da08a86ab833733c2f7f05480f4cc11bf57bf9fecb10873ad791a3600080546001600160a01b0319166001600160a01b0392909216919091179055565b6000602082840312156109ea578081fd5b81356109f581610cda565b9392505050565b60008060408385031215610a0e578081fd5b8235610a1981610cda565b9150602083013560038110610a2c578182fd5b809150509250929050565b600080600060608486031215610a4b578081fd5b8335610a5681610cda565b92506020840135610a6681610cda565b929592945050506040919091013590565b600060208284031215610a88578081fd5b815180151581146109f5578182fd5b600060208284031215610aa8578081fd5b5035919050565b600060208284031215610ac0578081fd5b5051919050565b6001600160a01b0391909116815260200190565b6001600160a01b03929092168252602082015260400190565b6001600160a01b039384168152919092166020820152604081019190915260600190565b901515815260200190565b6020808252601c908201527f4163636f756e742048617320416c72656164792046696e616e63656400000000604082015260600190565b60208082526056908201527f546865207472616e73616374696f6e2063616e6e6f7420626520636f6d706c6560408201527f746564207468652072657175657374656420616d6f756e74206f6620746f6b656060820152751b9cc818d85b9b9bdd081899481cd85d1a5cd99a595960521b608082015260a00190565b6020808252601e908201527f546865207472616e7366657220636f756c64206e6f74206265206d6164650000604082015260600190565b6020808252603c908201527f596f7520646f6e2774206861766520656e6f75676874207065726d697373696f60408201527f6e7320746f20657865637574652074686973206f7065726174696f6e00000000606082015260800190565b60208082526021908201527f496e73756666696369656e7420616d6f756e7420746f2062757920746f6b656e6040820152607360f81b606082015260800190565b90815260200190565b838152602081018390526060810160038310610ccc57fe5b826040830152949350505050565b6001600160a01b038116811461093e57600080fdfea2646970667358221220b8f6480144e496ce45eef068674aa8807ec94c85c8a0ddc4b67f65534b1633be64736f6c63430007010033608060405234801561001057600080fd5b506040516106a83803806106a883398101604081905261002f91610049565b600281905533600090815260208190526040902055610061565b60006020828403121561005a578081fd5b5051919050565b610638806100706000396000f3fe608060405234801561001057600080fd5b50600436106100a95760003560e01c8063313ce56711610071578063313ce5671461012957806370a082311461013e57806395d89b4114610151578063a9059cbb14610159578063beabacc81461016c578063dd62ed3e1461017f576100a9565b806306fdde03146100ae578063095ea7b3146100cc57806318160ddd146100ec5780631d43cc981461010157806323b872dd14610116575b600080fd5b6100b6610192565b6040516100c39190610598565b60405180910390f35b6100df6100da36600461054b565b6101b3565b6040516100c3919061058d565b6100f461021e565b6040516100c391906105eb565b61011461010f366004610575565b610224565b005b6100df61012436600461050e565b610244565b610131610392565b6040516100c391906105f4565b6100f461014c3660046104bf565b610397565b6100b66103b2565b6100df61016736600461054b565b6103d1565b6100df61017a36600461050e565b6103e5565b6100f461018d3660046104da565b61045c565b60405180604001604052806005815260200164054435332360dc1b81525081565b3360008181526001602090815260408083206001600160a01b038716808552925280832085905551919290917f8c5be1e5ebec7d5bd14f71427d1e84f3dd0314c0f7b2291e5b200ac8c7c3b9259061020c9086906105eb565b60405180910390a35060015b92915050565b60025490565b600280548201905533600090815260208190526040902080549091019055565b6001600160a01b03831660009081526020819052604081205482111561026957600080fd5b6001600160a01b038416600090815260016020908152604080832033845290915290205482111561029957600080fd5b6001600160a01b0384166000908152602081905260409020546102bc9083610487565b6001600160a01b0385166000908152602081815260408083209390935560018152828220338352905220546102f19083610487565b6001600160a01b038086166000908152600160209081526040808320338452825280832094909455918616815290819052205461032e9083610499565b6001600160a01b0380851660008181526020819052604090819020939093559151908616907fddf252ad1be2c89b69c2b068fc378daa952ba7f163c4a11628f55a4df523b3ef906103809086906105eb565b60405180910390a35060019392505050565b601281565b6001600160a01b031660009081526020819052604090205490565b6040518060400160405280600381526020016254435360e81b81525081565b60006103de3384846103e5565b9392505050565b6001600160a01b03831660009081526020819052604081205482111561040a57600080fd5b6001600160a01b03841660009081526020819052604090205461042d9083610487565b6001600160a01b03808616600090815260208190526040808220939093559085168152205461032e9083610499565b6001600160a01b03918216600090815260016020908152604080832093909416825291909152205490565b60008282111561049357fe5b50900390565b6000828201838110156103de57fe5b80356001600160a01b038116811461021857600080fd5b6000602082840312156104d0578081fd5b6103de83836104a8565b600080604083850312156104ec578081fd5b6104f684846104a8565b915061050584602085016104a8565b90509250929050565b600080600060608486031215610522578081fd5b61052c85856104a8565b925061053b85602086016104a8565b9150604084013590509250925092565b6000806040838503121561055d578182fd5b61056784846104a8565b946020939093013593505050565b600060208284031215610586578081fd5b5035919050565b901515815260200190565b6000602080835283518082850152825b818110156105c4578581018301518582016040015282016105a8565b818111156105d55783604083870101525b50601f01601f1916929092016040019392505050565b90815260200190565b60ff9190911681526020019056fea264697066735822122011613b56bb5d23e4958b295763a59ed552b8ee2132776be18a27d7cc7130e13b64736f6c63430007010033";

    public static final String FUNC_DEFAULT_ADMIN_TOKENS = "DEFAULT_ADMIN_TOKENS";

    public static final String FUNC_DEFAULT_CA_TOKENS = "DEFAULT_CA_TOKENS";

    public static final String FUNC_DEFAULT_STUDENTS_TOKENS = "DEFAULT_STUDENTS_TOKENS";

    public static final String FUNC_BALANCEOF = "balanceOf";

    public static final String FUNC_BUYTOKENS = "buyTokens";

    public static final String FUNC_CLIENTS = "clients";

    public static final String FUNC_COLLECT = "collect";

    public static final String FUNC_GENERATETOKENS = "generateTokens";

    public static final String FUNC_GETBALANCE = "getBalance";

    public static final String FUNC_GETMYTOKENS = "getMyTokens";

    public static final String FUNC_GETTOKENPRICEINWEI = "getTokenPriceInWei";

    public static final String FUNC_GETTOKENS = "getTokens";

    public static final String FUNC_OWNINGAUTHORITY = "owningAuthority";

    public static final String FUNC_SENDINITIALTOKENFUNDSTO = "sendInitialTokenFundsTo";

    public static final String FUNC_TRANSFER = "transfer";

    public static final String FUNC_TRANSFEROWNERSHIP = "transferOwnership";

    public static final Event ONOWNERSHIPTRANSFERRED_EVENT = new Event("OnOwnershipTransferred", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>(true) {}, new TypeReference<Address>(true) {}));
    ;

    @Deprecated
    protected TokenManagementContract(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected TokenManagementContract(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, credentials, contractGasProvider);
    }

    @Deprecated
    protected TokenManagementContract(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    protected TokenManagementContract(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, transactionManager, contractGasProvider);
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

    public RemoteFunctionCall<BigInteger> DEFAULT_ADMIN_TOKENS() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_DEFAULT_ADMIN_TOKENS, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<BigInteger> DEFAULT_CA_TOKENS() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_DEFAULT_CA_TOKENS, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<BigInteger> DEFAULT_STUDENTS_TOKENS() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_DEFAULT_STUDENTS_TOKENS, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<BigInteger> balanceOf() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_BALANCEOF, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<TransactionReceipt> buyTokens(BigInteger _tokenCount) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_BUYTOKENS, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_tokenCount)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<Tuple3<BigInteger, BigInteger, BigInteger>> clients(String param0) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_CLIENTS, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, param0)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}, new TypeReference<Uint256>() {}, new TypeReference<Uint8>() {}));
        return new RemoteFunctionCall<Tuple3<BigInteger, BigInteger, BigInteger>>(function,
                new Callable<Tuple3<BigInteger, BigInteger, BigInteger>>() {
                    @Override
                    public Tuple3<BigInteger, BigInteger, BigInteger> call() throws Exception {
                        List<Type> results = executeCallMultipleValueReturn(function);
                        return new Tuple3<BigInteger, BigInteger, BigInteger>(
                                (BigInteger) results.get(0).getValue(), 
                                (BigInteger) results.get(1).getValue(), 
                                (BigInteger) results.get(2).getValue());
                    }
                });
    }

    public RemoteFunctionCall<TransactionReceipt> collect() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_COLLECT, 
                Arrays.<Type>asList(), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> generateTokens(BigInteger _tokenCount) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_GENERATETOKENS, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_tokenCount)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<BigInteger> getBalance() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_GETBALANCE, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<BigInteger> getMyTokens() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_GETMYTOKENS, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<BigInteger> getTokenPriceInWei(BigInteger _tokenCount) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_GETTOKENPRICEINWEI, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_tokenCount)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<BigInteger> getTokens(String client) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_GETTOKENS, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, client)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<String> owningAuthority() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_OWNINGAUTHORITY, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteFunctionCall<TransactionReceipt> sendInitialTokenFundsTo(String account, BigInteger clientType) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_SENDINITIALTOKENFUNDSTO, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, account), 
                new org.web3j.abi.datatypes.generated.Uint8(clientType)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> transfer(String client, String recipient, BigInteger amount) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_TRANSFER, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, client), 
                new org.web3j.abi.datatypes.Address(160, recipient), 
                new org.web3j.abi.datatypes.generated.Uint256(amount)), 
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
    public static TokenManagementContract load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new TokenManagementContract(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    @Deprecated
    public static TokenManagementContract load(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new TokenManagementContract(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public static TokenManagementContract load(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return new TokenManagementContract(contractAddress, web3j, credentials, contractGasProvider);
    }

    public static TokenManagementContract load(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return new TokenManagementContract(contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static RemoteCall<TokenManagementContract> deploy(Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return deployRemoteCall(TokenManagementContract.class, web3j, credentials, contractGasProvider, BINARY, "");
    }

    public static RemoteCall<TokenManagementContract> deploy(Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return deployRemoteCall(TokenManagementContract.class, web3j, transactionManager, contractGasProvider, BINARY, "");
    }

    @Deprecated
    public static RemoteCall<TokenManagementContract> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(TokenManagementContract.class, web3j, credentials, gasPrice, gasLimit, BINARY, "");
    }

    @Deprecated
    public static RemoteCall<TokenManagementContract> deploy(Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(TokenManagementContract.class, web3j, transactionManager, gasPrice, gasLimit, BINARY, "");
    }

    public static class OnOwnershipTransferredEventResponse extends BaseEventResponse {
        public String previousOwner;

        public String newOwner;
    }
}
