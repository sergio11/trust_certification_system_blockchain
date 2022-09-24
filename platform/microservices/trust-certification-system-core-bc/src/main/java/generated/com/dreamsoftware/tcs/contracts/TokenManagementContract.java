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
import org.web3j.tuples.generated.Tuple4;
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
    public static final String BINARY = "6080604052601e600255600a600355600360045534801561001f57600080fd5b50600080546001600160a01b0319163317905560405163773594009061004490610090565b61004e919061009d565b604051809103906000f08015801561006a573d6000803e3d6000fd5b50600180546001600160a01b0319166001600160a01b03929092169190911790556100a6565b6106a880610fd183390190565b90815260200190565b610f1c806100b56000396000f3fe6080604052600436106100fe5760003560e01c80636039fbdb11610095578063ca01ba3911610064578063ca01ba3914610276578063cdccedf914610296578063dbb8a608146102b6578063e5225381146102cb578063f2fde38b146102e0576100fe565b80636039fbdb146101e457806361eed2a914610211578063722713f714610241578063beabacc814610256576100fe565b806341c16ea5116100d157806341c16ea514610185578063450efe211461019a578063598823b0146101ba5780635b3b136a146101cf576100fe565b806312065fe0146101035780631b8531821461012e5780633610724e146101505780633fe03b1714610165575b600080fd5b34801561010f57600080fd5b50610118610300565b6040516101259190610e99565b60405180910390f35b34801561013a57600080fd5b50610143610339565b6040516101259190610c88565b61016361015e366004610c58565b610348565b005b34801561017157600080fd5b50610163610180366004610b92565b610473565b34801561019157600080fd5b50610118610643565b3480156101a657600080fd5b506101186101b5366004610b6f565b610649565b3480156101c657600080fd5b506101186106d0565b3480156101db57600080fd5b506101186106d6565b3480156101f057600080fd5b506102046101ff366004610c0d565b61075c565b6040516101259190610cd9565b34801561021d57600080fd5b5061023161022c366004610b6f565b6108bd565b6040516101259493929190610ea2565b34801561024d57600080fd5b506101186108ea565b34801561026257600080fd5b50610204610271366004610bcd565b61091b565b34801561028257600080fd5b50610163610291366004610c58565b6109cf565b3480156102a257600080fd5b506101186102b1366004610c58565b610a5e565b3480156102c257600080fd5b50610118610a6a565b3480156102d757600080fd5b50610163610a70565b3480156102ec57600080fd5b506101636102fb366004610b6f565b610ad7565b600080546001600160a01b031633146103345760405162461bcd60e51b815260040161032b90610dfb565b60405180910390fd5b504790565b6000546001600160a01b031690565b6103506108ea565b81111561036f5760405162461bcd60e51b815260040161032b90610d48565b600061037a82610a5e565b90508034101561039c5760405162461bcd60e51b815260040161032b90610e58565b60405133903483900380156108fc02916000818181858888f193505050501580156103cb573d6000803e3d6000fd5b5060015460405163a9059cbb60e01b81526001600160a01b039091169063a9059cbb906103fe9033908690600401610c9c565b602060405180830381600087803b15801561041857600080fd5b505af115801561042c573d6000803e3d6000fd5b505050506040513d601f19601f820116820180604052508101906104509190610c38565b505033600090815260066020526040902080548201815560010180549091019055565b6000546001600160a01b0316331461049d5760405162461bcd60e51b815260040161032b90610dfb565b6001600160a01b038216600090815260056020526040902054829060ff16156104d85760405162461bcd60e51b815260040161032b90610d11565b600060028360028111156104e857fe5b14156104f75750600254610519565b600083600281111561050557fe5b14156105145750600354610519565b506004545b6105216108ea565b8111156105405760405162461bcd60e51b815260040161032b90610d48565b60015460405163a9059cbb60e01b81526001600160a01b039091169063a9059cbb906105729087908590600401610c9c565b602060405180830381600087803b15801561058c57600080fd5b505af11580156105a0573d6000803e3d6000fd5b505050506040513d601f19601f820116820180604052508101906105c49190610c38565b6105e05760405162461bcd60e51b815260040161032b90610dc4565b6001600160a01b03841660009081526005602090815260408083208054600160ff1991821681179092556006909352922080548401815580830180548501905560029081018054879491931691849081111561063857fe5b021790555050505050565b60025481565b6001546040516370a0823160e01b81526000916001600160a01b0316906370a082319061067a908590600401610c88565b60206040518083038186803b15801561069257600080fd5b505afa1580156106a6573d6000803e3d6000fd5b505050506040513d601f19601f820116820180604052508101906106ca9190610c70565b92915050565b60045481565b6001546040516370a0823160e01b81526000916001600160a01b0316906370a0823190610707903390600401610c88565b60206040518083038186803b15801561071f57600080fd5b505afa158015610733573d6000803e3d6000fd5b505050506040513d601f19601f820116820180604052508101906107579190610c70565b905090565b600080546001600160a01b031633146107875760405162461bcd60e51b815260040161032b90610dfb565b6001600160a01b0383166000908152600660205260409020600201548390610100900460ff166107c95760405162461bcd60e51b815260040161032b90610ce4565b6107d16108ea565b8311156107f05760405162461bcd60e51b815260040161032b90610d48565b60015460405163a9059cbb60e01b81526001600160a01b039091169063a9059cbb906108229087908790600401610c9c565b602060405180830381600087803b15801561083c57600080fd5b505af1158015610850573d6000803e3d6000fd5b505050506040513d601f19601f820116820180604052508101906108749190610c38565b6108905760405162461bcd60e51b815260040161032b90610dc4565b506001600160a01b0390921660009081526006602052604090208054820181556001018054909101905590565b60066020526000908152604090208054600182015460029092015490919060ff8082169161010090041684565b6001546040516370a0823160e01b81526000916001600160a01b0316906370a0823190610707903090600401610c88565b6001546040516317d5759960e31b81526000916001600160a01b03169063beabacc89061095090879087908790600401610cb5565b602060405180830381600087803b15801561096a57600080fd5b505af115801561097e573d6000803e3d6000fd5b505050506040513d601f19601f820116820180604052508101906109a29190610c38565b50506001600160a01b03831660009081526006602052604090206001908101805483900390559392505050565b6000546001600160a01b031633146109f95760405162461bcd60e51b815260040161032b90610dfb565b6001546040516303a8799360e31b81526001600160a01b0390911690631d43cc9890610a29908490600401610e99565b600060405180830381600087803b158015610a4357600080fd5b505af1158015610a57573d6000803e3d6000fd5b5050505050565b662386f26fc100000290565b60035481565b6000546001600160a01b03163314610a9a5760405162461bcd60e51b815260040161032b90610dfb565b600080546040516001600160a01b03909116914780156108fc02929091818181858888f19350505050158015610ad4573d6000803e3d6000fd5b50565b6000546001600160a01b03163314610b015760405162461bcd60e51b815260040161032b90610dfb565b6001600160a01b038116610b1457600080fd5b600080546040516001600160a01b03808516939216917ff77c6eb92f5003da08a86ab833733c2f7f05480f4cc11bf57bf9fecb10873ad791a3600080546001600160a01b0319166001600160a01b0392909216919091179055565b600060208284031215610b80578081fd5b8135610b8b81610ed1565b9392505050565b60008060408385031215610ba4578081fd5b8235610baf81610ed1565b9150602083013560038110610bc2578182fd5b809150509250929050565b600080600060608486031215610be1578081fd5b8335610bec81610ed1565b92506020840135610bfc81610ed1565b929592945050506040919091013590565b60008060408385031215610c1f578182fd5b8235610c2a81610ed1565b946020939093013593505050565b600060208284031215610c49578081fd5b81518015158114610b8b578182fd5b600060208284031215610c69578081fd5b5035919050565b600060208284031215610c81578081fd5b5051919050565b6001600160a01b0391909116815260200190565b6001600160a01b03929092168252602082015260400190565b6001600160a01b039384168152919092166020820152604081019190915260600190565b901515815260200190565b602080825260139082015272436c69656e7420646f6e27742065786973747360681b604082015260600190565b6020808252601c908201527f4163636f756e742048617320416c72656164792046696e616e63656400000000604082015260600190565b60208082526056908201527f546865207472616e73616374696f6e2063616e6e6f7420626520636f6d706c6560408201527f746564207468652072657175657374656420616d6f756e74206f6620746f6b656060820152751b9cc818d85b9b9bdd081899481cd85d1a5cd99a595960521b608082015260a00190565b6020808252601e908201527f546865207472616e7366657220636f756c64206e6f74206265206d6164650000604082015260600190565b6020808252603c908201527f596f7520646f6e2774206861766520656e6f75676874207065726d697373696f60408201527f6e7320746f20657865637574652074686973206f7065726174696f6e00000000606082015260800190565b60208082526021908201527f496e73756666696369656e7420616d6f756e7420746f2062757920746f6b656e6040820152607360f81b606082015260800190565b90815260200190565b848152602081018490526080810160038410610eba57fe5b836040830152821515606083015295945050505050565b6001600160a01b0381168114610ad457600080fdfea2646970667358221220be08d1292275769bb15c8804b12312c16ab3ba1fad8886af232d1a92d395678464736f6c63430007010033608060405234801561001057600080fd5b506040516106a83803806106a883398101604081905261002f91610049565b600281905533600090815260208190526040902055610061565b60006020828403121561005a578081fd5b5051919050565b610638806100706000396000f3fe608060405234801561001057600080fd5b50600436106100a95760003560e01c8063313ce56711610071578063313ce5671461012957806370a082311461013e57806395d89b4114610151578063a9059cbb14610159578063beabacc81461016c578063dd62ed3e1461017f576100a9565b806306fdde03146100ae578063095ea7b3146100cc57806318160ddd146100ec5780631d43cc981461010157806323b872dd14610116575b600080fd5b6100b6610192565b6040516100c39190610598565b60405180910390f35b6100df6100da36600461054b565b6101b3565b6040516100c3919061058d565b6100f461021e565b6040516100c391906105eb565b61011461010f366004610575565b610224565b005b6100df61012436600461050e565b610244565b610131610392565b6040516100c391906105f4565b6100f461014c3660046104bf565b610397565b6100b66103b2565b6100df61016736600461054b565b6103d1565b6100df61017a36600461050e565b6103e5565b6100f461018d3660046104da565b61045c565b60405180604001604052806005815260200164054435332360dc1b81525081565b3360008181526001602090815260408083206001600160a01b038716808552925280832085905551919290917f8c5be1e5ebec7d5bd14f71427d1e84f3dd0314c0f7b2291e5b200ac8c7c3b9259061020c9086906105eb565b60405180910390a35060015b92915050565b60025490565b600280548201905533600090815260208190526040902080549091019055565b6001600160a01b03831660009081526020819052604081205482111561026957600080fd5b6001600160a01b038416600090815260016020908152604080832033845290915290205482111561029957600080fd5b6001600160a01b0384166000908152602081905260409020546102bc9083610487565b6001600160a01b0385166000908152602081815260408083209390935560018152828220338352905220546102f19083610487565b6001600160a01b038086166000908152600160209081526040808320338452825280832094909455918616815290819052205461032e9083610499565b6001600160a01b0380851660008181526020819052604090819020939093559151908616907fddf252ad1be2c89b69c2b068fc378daa952ba7f163c4a11628f55a4df523b3ef906103809086906105eb565b60405180910390a35060019392505050565b601281565b6001600160a01b031660009081526020819052604090205490565b6040518060400160405280600381526020016254435360e81b81525081565b60006103de3384846103e5565b9392505050565b6001600160a01b03831660009081526020819052604081205482111561040a57600080fd5b6001600160a01b03841660009081526020819052604090205461042d9083610487565b6001600160a01b03808616600090815260208190526040808220939093559085168152205461032e9083610499565b6001600160a01b03918216600090815260016020908152604080832093909416825291909152205490565b60008282111561049357fe5b50900390565b6000828201838110156103de57fe5b80356001600160a01b038116811461021857600080fd5b6000602082840312156104d0578081fd5b6103de83836104a8565b600080604083850312156104ec578081fd5b6104f684846104a8565b915061050584602085016104a8565b90509250929050565b600080600060608486031215610522578081fd5b61052c85856104a8565b925061053b85602086016104a8565b9150604084013590509250925092565b6000806040838503121561055d578182fd5b61056784846104a8565b946020939093013593505050565b600060208284031215610586578081fd5b5035919050565b901515815260200190565b6000602080835283518082850152825b818110156105c4578581018301518582016040015282016105a8565b818111156105d55783604083870101525b50601f01601f1916929092016040019392505050565b90815260200190565b60ff9190911681526020019056fea264697066735822122063bf17986e07a6eba4b0c2474dcf01ffe5b262c608a8b7dd4198277230fad3f464736f6c63430007010033";

    public static final String FUNC_DEFAULT_ADMIN_TOKENS = "DEFAULT_ADMIN_TOKENS";

    public static final String FUNC_DEFAULT_CA_TOKENS = "DEFAULT_CA_TOKENS";

    public static final String FUNC_DEFAULT_STUDENTS_TOKENS = "DEFAULT_STUDENTS_TOKENS";

    public static final String FUNC_ADDTOKENS = "addTokens";

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

    public RemoteFunctionCall<TransactionReceipt> addTokens(String _recipient, BigInteger _amount) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_ADDTOKENS, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, _recipient), 
                new org.web3j.abi.datatypes.generated.Uint256(_amount)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
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

    public RemoteFunctionCall<Tuple4<BigInteger, BigInteger, BigInteger, Boolean>> clients(String param0) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_CLIENTS, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, param0)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}, new TypeReference<Uint256>() {}, new TypeReference<Uint8>() {}, new TypeReference<Bool>() {}));
        return new RemoteFunctionCall<Tuple4<BigInteger, BigInteger, BigInteger, Boolean>>(function,
                new Callable<Tuple4<BigInteger, BigInteger, BigInteger, Boolean>>() {
                    @Override
                    public Tuple4<BigInteger, BigInteger, BigInteger, Boolean> call() throws Exception {
                        List<Type> results = executeCallMultipleValueReturn(function);
                        return new Tuple4<BigInteger, BigInteger, BigInteger, Boolean>(
                                (BigInteger) results.get(0).getValue(), 
                                (BigInteger) results.get(1).getValue(), 
                                (BigInteger) results.get(2).getValue(), 
                                (Boolean) results.get(3).getValue());
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

    public RemoteFunctionCall<BigInteger> getTokens(String _client) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_GETTOKENS, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, _client)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<String> owningAuthority() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_OWNINGAUTHORITY, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteFunctionCall<TransactionReceipt> sendInitialTokenFundsTo(String _account, BigInteger _clientType) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_SENDINITIALTOKENFUNDSTO, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, _account), 
                new org.web3j.abi.datatypes.generated.Uint8(_clientType)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> transfer(String _client, String _recipient, BigInteger _amount) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_TRANSFER, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, _client), 
                new org.web3j.abi.datatypes.Address(160, _recipient), 
                new org.web3j.abi.datatypes.generated.Uint256(_amount)), 
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
