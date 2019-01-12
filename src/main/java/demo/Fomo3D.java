package demo;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.ontio.OntSdk;
import com.github.ontio.account.Account;
import com.github.ontio.common.Address;
import com.github.ontio.common.Helper;
import com.github.ontio.core.transaction.Transaction;
import com.github.ontio.crypto.SignatureScheme;
import com.github.ontio.network.exception.ConnectorException;
import com.github.ontio.sdk.exception.SDKException;
import com.github.ontio.smartcontract.neovm.abi.AbiFunction;
import com.github.ontio.smartcontract.neovm.abi.AbiInfo;
import com.github.ontio.smartcontract.neovm.abi.BuildParams;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

public class Fomo3D {

    public static OntSdk sdk = OntSdk.getInstance();
    public static String zhuwangRpc = "http://dappnode1.ont.io:20336";
    public static String contractAddr = "95adaa30262e21cefe07bb99624d6249ce0b170d";
    public static String password = "LUlu@665211";


    public static Account adminAccount;


    public static Account myAccount;



    public static AbiInfo abiInfo;

    public static void main(String[] args) throws Exception {

        String abi = "{\"functions\":[{\"name\":\"Main\",\"parameters\":[{\"name\":\"operation\",\"type\":\"\"},{\"name\":\"args\",\"type\":\"\"}],\"returntype\":\"\"}," +
                "{\"name\":\"init\",\"parameters\":[],\"returntype\":\"\"}," +
                "{\"name\":\"setParameters\",\"parameters\":[{\"name\":\"keyHolderPercen\",\"type\":\"Integer\"},{\"name\":\"bonusPercen\",\"type\":\"Integer\"},{\"name\":\"shareHolderPercen\",\"type\":\"Integer\"},{\"name\":\"invitorPercen\",\"type\":\"Integer\"}],\"returntype\":\"\"}," +
                "{\"name\":\"setDistributeParameters\",\"parameters\":[{\"name\":\"lastBuyerPercen\",\"type\":\"\"},{\"name\":\"nextBonusPercen\",\"type\":\"\"},{\"name\":\"operationPercen\",\"type\":\"\"}],\"returntype\":\"\"}," +
                "{\"name\":\"startNewRound\",\"parameters\":[],\"returntype\":\"\"}," +
                "{\"name\":\"endCurrentRound\",\"parameters\":[],\"returntype\":\"\"}," +
                "{\"name\":\"withdrawOperation\",\"parameters\":[{\"name\":\"account\",\"type\":\"\"}],\"returntype\":\"\"}," +
                "{\"name\":\"withdrawAdmin\",\"parameters\":[{\"name\":\"account\",\"type\":\"ByteArray\"}, {\"name\":\"ongAmount\",\"type\":\"Integer\"}],\"returntype\":\"\"}," +
                "{\"name\":\"withdrawShareHolder\",\"parameters\":[{\"name\":\"account\",\"type\":\"\"}],\"returntype\":\"\"}," +
                "{\"name\":\"withdrawFee\",\"parameters\":[{\"name\":\"account\",\"type\":\"ByteArray\"}],\"returntype\":\"\"}," +
                "{\"name\":\"buyKey\",\"parameters\":[{\"name\":\"account\",\"type\":\"ByteArray\"},{\"name\":\"invitor\",\"type\":\"ByteArray\"}],\"returntype\":\"\"}," +
                "{\"name\":\"invitorPlayer\",\"parameters\":[{\"name\":\"account\",\"type\":\"\"},{\"name\":\"newaccount\",\"type\":\"\"}],\"returntype\":\"\"}," +
                "{\"name\":\"withdrawDividend\",\"parameters\":[{\"name\":\"account\",\"type\":\"ByteArray\"}],\"returntype\":\"\"}," +
                "{\"name\":\"updateDividendBalance\",\"parameters\":[{\"name\":\"account\",\"type\":\"\"},{\"name\":\"currentRound\",\"type\":\"\"}],\"returntype\":\"\"}," +
                "{\"name\":\"getBuyerDistribute\",\"parameters\":[],\"returntype\":\"\"}," +
                "{\"name\":\"getFeeBalance\",\"parameters\":[],\"returntype\":\"\"}," +
                "{\"name\":\"getShareHolderBalance\",\"parameters\":[],\"returntype\":\"\"}," +
                "{\"name\":\"getOperationBalance\",\"parameters\":[],\"returntype\":\"\"}," +
                "{\"name\":\"getBonusDistributePercen\",\"parameters\":[{\"name\":\"\",\"type\":\"\"}],\"returntype\":\"\"}," +
                "{\"name\":\"getLastBuyer\",\"parameters\":[{\"name\":\"currentRound\",\"type\":\"Integer\"}],\"returntype\":\"\"}," +
                "{\"name\":\"getBonusBalance\",\"parameters\":[],\"returntype\":\"\"}," +
                "{\"name\":\"getKeyBalance\",\"parameters\":[{\"name\":\"account\",\"type\":\"\"},{\"name\":\"currentRound\",\"type\":\"\"}],\"returntype\":\"\"}," +
                "{\"name\":\"getDividendBalance\",\"parameters\":[{\"name\":\"account\",\"type\":\"ByteArray\"}],\"returntype\":\"\"}," +
                "{\"name\":\"getInvitor\",\"parameters\":[{\"name\":\"account\",\"type\":\"\"}],\"returntype\":\"\"}," +
                "{\"name\":\"getCurrentRound\",\"parameters\":[],\"returntype\":\"\"}," +
                "{\"name\":\"getCurrentRemainingTime\",\"parameters\":[{\"name\":\"roundNumber\",\"type\":\"Integer\"}],\"returntype\":\"\"}," +
                "{\"name\":\"getRoundGameStatus\",\"parameters\":[{\"name\":\"roundNumber\",\"type\":\"Integer\"}],\"returntype\":\"\"}," +
                "{\"name\":\"getKeyPrice\",\"parameters\":[],\"returntype\":\"\"}," +
                "{\"name\":\"getToTalKey\",\"parameters\":[{\"name\":\"roundNumber\",\"type\":\"\"}],\"returntype\":\"\"}," +
                "{\"name\":\"getProfitPerKey\",\"parameters\":[{\"name\":\"roundNumber\",\"type\":\"\"}],\"returntype\":\"\"}," +
                "{\"name\":\"concatKey\",\"parameters\":[{\"name\":\"str1\",\"type\":\"\"},{\"name\":\"str2\",\"type\":\"\"}],\"returntype\":\"\"}," +
                "{\"name\":\"_transferONG\",\"parameters\":[{\"name\":\"fromAcct\",\"type\":\"\"},{\"name\":\"toAcct\",\"type\":\"\"},{\"name\":\"amount\",\"type\":\"\"}],\"returntype\":\"\"}," +
                "{\"name\":\"_transferONGFromContact\",\"parameters\":[{\"name\":\"toAcct\",\"type\":\"\"},{\"name\":\"amount\",\"type\":\"\"}],\"returntype\":\"\"}," +
                "{\"name\":\"Revert\",\"parameters\":[{\"name\":\"\",\"type\":\"\"}],\"returntype\":\"\"}," +
                "{\"name\":\"Require\",\"parameters\":[{\"name\":\"condition\",\"type\":\"\"}],\"returntype\":\"\"},{\"name\":\"RequireScriptHash\",\"parameters\":[{\"name\":\"key\",\"type\":\"\"}],\"returntype\":\"\"},{\"name\":\"RequireWitness\",\"parameters\":[{\"name\":\"witness\",\"type\":\"\"}],\"returntype\":\"\"},{\"name\":\"Add\",\"parameters\":[{\"name\":\"a\",\"type\":\"\"},{\"name\":\"b\",\"type\":\"\"}],\"returntype\":\"\"},{\"name\":\"Sub\",\"parameters\":[{\"name\":\"a\",\"type\":\"\"},{\"name\":\"b\",\"type\":\"\"}],\"returntype\":\"\"},{\"name\":\"Mul\",\"parameters\":[{\"name\":\"a\",\"type\":\"\"},{\"name\":\"b\",\"type\":\"\"}],\"returntype\":\"\"},{\"name\":\"Div\",\"parameters\":[{\"name\":\"a\",\"type\":\"\"},{\"name\":\"b\",\"type\":\"\"}],\"returntype\":\"\"}]}";


        sdk.setRpc(zhuwangRpc);
        sdk.openWalletFile("cynowallet.dat");
        abiInfo = JSON.parseObject(abi, AbiInfo.class);
        adminAccount = sdk.getWalletMgr().getAccount("AbPRaepcpBAFHz9zCj4619qch4Aq5hJARA","LUlu@665211");

        myAccount = new Account(Account.getPrivateKeyFromWIF("L3FsqSSX3waPoWhT2gs6X9uiGc5XNKPszniuF8Z7J6U6spF6fNC7"), SignatureScheme.SHA256WITHECDSA);

        int num =0;
        while (true) {

            if(!adminAccount.getAddressU160().toBase58().equals(getLastBuyer()) || getKeyPrice() < 40) {
                int remainingTime = getCurrentRemainingTime();

                if(remainingTime < 300){
                    System.out.println("**************buyKey*************************");
                    buyKey();
                } else {
                    int sleepTime = (remainingTime-600)*1000;
                    System.out.println("*******************sleep********************" + sleepTime);
                    Thread.sleep(sleepTime);
                }
            }

        }
    }


    public static void buyKey() throws Exception {
        AbiFunction func = abiInfo.getFunction("buyKey");
        func.setParamsValue(adminAccount.getAddressU160().toArray(), adminAccount.getAddressU160().toArray());
        String txhash = (String) sdk.neovm().sendTransaction(Helper.reverse(contractAddr),adminAccount,adminAccount,20000000,500,func,false);
        System.out.println(txhash);
        Thread.sleep(6000);
        Object obj = sdk.getConnect().getSmartCodeEvent(txhash);
        System.out.println(obj);
    }

    public static int getCurrentRemainingTime() throws Exception {
        long round = getCurrentRound();
        AbiFunction func = abiInfo.getFunction("getCurrentRemainingTime");
        func.setParamsValue(round);
        Object obj = sdk.neovm().sendTransaction(Helper.reverse(contractAddr),adminAccount,adminAccount,2000000,0,func,true);
        System.out.println("getCurrentRemainingTime:" + Long.valueOf(Helper.reverse(((JSONObject)obj).getString("Result")), 16));
        return Long.valueOf(Helper.reverse(((JSONObject)obj).getString("Result")), 16).intValue();
    }


    public static String getLastBuyer() throws Exception {
        long round = getCurrentRound();
        AbiFunction func = abiInfo.getFunction("getLastBuyer");
        func.setParamsValue(round);
        Object obj = sdk.neovm().sendTransaction(Helper.reverse(contractAddr),adminAccount,adminAccount,2000000,0,func,true);
        System.out.println("lastBuyer:" + Address.parse(((JSONObject)obj).getString("Result")).toBase58());
        return Address.parse(((JSONObject)obj).getString("Result")).toBase58();
    }

    public static Account getAccount(String enpri,String password,String address,String salt) throws Exception {
        String privateKey = Account.getGcmDecodedPrivateKey(enpri,password,address,Base64.getDecoder().decode(salt),16384,SignatureScheme.SHA256WITHECDSA);
        Account account = new Account(Helper.hexToBytes(privateKey),SignatureScheme.SHA256WITHECDSA);
//        System.out.println(Helper.toHexString(account.serializePublicKey()));
        return account;
    }


    public static long getCurrentRound() throws Exception {
        AbiFunction func = abiInfo.getFunction("getCurrentRound");
        Object obj = sdk.neovm().sendTransaction(Helper.reverse(contractAddr),adminAccount,adminAccount,2000000,0,func,true);
        System.out.println(obj);
        return Long.valueOf(Helper.reverse(((JSONObject)obj).getString("Result")), 16);
    }

    public static double getKeyPrice() throws Exception {
        AbiFunction func = abiInfo.getFunction("getKeyPrice");
        Object obj = sdk.neovm().sendTransaction(Helper.reverse(contractAddr),adminAccount,adminAccount,2000000,0,func,true);
        System.out.println(Long.valueOf(Helper.reverse(((JSONObject)obj).getString("Result")), 16).doubleValue()/1000000000.0000);
        return Long.valueOf(Helper.reverse(((JSONObject)obj).getString("Result")), 16).doubleValue()/1000000000.0000;
    }
}
