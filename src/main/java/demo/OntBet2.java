package demo;

import com.alibaba.fastjson.JSON;
import com.github.ontio.OntSdk;
import com.github.ontio.account.Account;
import com.github.ontio.common.Address;
import com.github.ontio.common.Helper;
import com.github.ontio.core.transaction.Transaction;
import com.github.ontio.crypto.SignatureScheme;
import com.github.ontio.smartcontract.neovm.abi.AbiFunction;
import com.github.ontio.smartcontract.neovm.abi.AbiInfo;

public class OntBet2 {
    public static void main(String[] args) throws Exception {
        OntSdk sdk = OntSdk.getInstance();
        String localRpc = "http://polaris1.ont.io:20336";

        sdk.setRpc(localRpc);
        sdk.openWalletFile("cynowallet.dat");
        Account account = sdk.getWalletMgr().getAccount("AbPRaepcpBAFHz9zCj4619qch4Aq5hJARA","LUlu@665211");
        Account account1 = new Account(Helper.hexToBytes("274b0b664d9c1e993c1d62a42f78ba84c379e332aa1d050ce9c1840820acee8b"), SignatureScheme.SHA256WITHECDSA);

        boolean deploy = false;
        boolean rollANumber = false;
        boolean bet = false;
        boolean bankerInvest = true;

        if(false){

//            Account account1 = new Account(Helper.hexToBytes("274b0b664d9c1e993c1d62a42f78ba84c379e332aa1d050ce9c1840820acee8b"), SignatureScheme.SHA256WITHECDSA);
            sdk.nativevm().ong().sendTransfer(account1, Address.parse("ef8ef940eb9b8273ff178e9d4c1f15cc06f7accc").toBase58(),10000000000L,account1,20000,0);

            return;
        }
        if(deploy){
            String codeStr = "58c56b6a00527ac46a51527ac46a00c30b726f6c6c414e756d6265729c64090065d1006c7566616a00c3036265749c640900655e006c756661006c756653c56b09f4f4f3f3f2f2f1f100f0006c756656c56b6a00527ac46a00c363070065e0ff7561516c756657c56b6a00527ac46a51527ac46a00c36a51c3936a52527ac46a52c36a00c3a265c9ff756a52c36c756655c56b0c5f726f6c6c414e756d62657200c176c97c67ccacf706cc151f4c9d8e17ff73829beb40f98eef6a00527ac45a6a00c3527c65a1ff52c176c96a51527ac4036265746a51c37c67ccacf706cc151f4c9d8e17ff73829beb40f98eef6c756654c56b0c5f726f6c6c414e756d62657200c176c97c67ccacf706cc151f4c9d8e17ff73829beb40f98eef6a00527ac46a00c36c7566";
            Transaction tx = sdk.vm().makeDeployCodeTransaction(codeStr, true,"1","1","sss","1","1",account.getAddressU160().toBase58(), 22600000,0);
            sdk.signTx(tx, new Account[][]{{account}});
            Object res = sdk.getConnect().syncSendRawTransaction(Helper.toHexString(tx.toArray()));
            System.out.println("res:" + res);
            return;
        }
        String contractAddr = "ef8ef940eb9b8273ff178e9d4c1f15cc06f7accc";
        String abiStr = "{\"functions\":[{\"name\":\"Main\",\"parameters\":[{\"name\":\"operation\",\"type\":\"\"},{\"name\":\"args\",\"type\":\"\"}],\"returntype\":\"\"},{\"name\":\"rollANumber\",\"parameters\":[],\"returntype\":\"\"},{\"name\":\"bet\",\"parameters\":[],\"returntype\":\"\"},{\"name\":\"Add\",\"parameters\":[{\"name\":\"a\",\"type\":\"\"},{\"name\":\"b\",\"type\":\"\"}],\"returntype\":\"\"},{\"name\":\"Require\",\"parameters\":[{\"name\":\"condition\",\"type\":\"\"}],\"returntype\":\"\"},{\"name\":\"Revert\",\"parameters\":[{\"name\":\"\",\"type\":\"\"}],\"returntype\":\"\"}]}";
        AbiInfo abiInfo = JSON.parseObject(abiStr, AbiInfo.class);
        if(rollANumber) {
            AbiFunction func = abiInfo.getFunction("rollANumber");
            Object res = sdk.neovm().sendTransaction(Helper.reverse(contractAddr),account,account,2000000,0,func,true);
            System.out.println(res);
            return;
        }

        if(bet) {
            AbiFunction func = abiInfo.getFunction("bet");
            func.setParamsValue(account.getAddressU160().toArray(),2L,1L);
            String txhash = (String) sdk.neovm().sendTransaction(Helper.reverse(contractAddr),account,account,2000000,0,func,false);
            Thread.sleep(3000);
            Object obj = sdk.getConnect().getSmartCodeEvent(txhash);
            System.out.println(obj);
            return;
        }

        if(bankerInvest){
            AbiFunction func = abiInfo.getFunction("bankerInvest");
            func.setParamsValue(account1.getAddressU160().toArray(),2001*1000000000L);
            String txhash = (String) sdk.neovm().sendTransaction(Helper.reverse(contractAddr),account1,account1,2000000,0,func,false);
            Thread.sleep(6000);
            Object obj = sdk.getConnect().getSmartCodeEvent(txhash);
            System.out.println(obj);
            return;
        }
    }
}
