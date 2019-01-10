package demo;

import com.alibaba.fastjson.JSON;
import com.github.ontio.OntSdk;
import com.github.ontio.account.Account;
import com.github.ontio.common.Helper;
import com.github.ontio.smartcontract.neovm.abi.AbiFunction;
import com.github.ontio.smartcontract.neovm.abi.AbiInfo;

public class NBAContractDemo {

    public static void main(String[] args) throws Exception {
        OntSdk sdk = OntSdk.getInstance();
        String localRpc = "http://127.0.0.1:20336";

//        String zhuwangRpc = "http://dappnode1.ont.io:20336";

        String contractAddress = "453f45570f84921c9a6f2d4409d0161f3f640007";
        sdk.setRpc(localRpc);
        sdk.openWalletFile("cynowallet.dat");

        Account adminAccount = sdk.getWalletMgr().getAccount("AbPRaepcpBAFHz9zCj4619qch4Aq5hJARA","LUlu@665211");

        String abi = "{\"functions\":[{\"name\":\"Main\",\"parameters\":[{\"name\":\"operation\",\"type\":\"\"},{\"name\":\"args\",\"type\":\"\"}],\"returntype\":\"\"}," +
                "{\"name\":\"getGameCount\",\"parameters\":[{\"name\":\"date\",\"type\":\"\"}],\"returntype\":\"\"}," +
                "{\"name\":\"getOracleReq\",\"parameters\":[{\"name\":\"date\",\"type\":\"\"}],\"returntype\":\"\"}," +
                "{\"name\":\"name\",\"parameters\":[{\"name\":\"\",\"type\":\"\"}],\"returntype\":\"\"}," +
                "{\"name\":\"getMatchByDate\",\"parameters\":[{\"name\":\"date\",\"type\":\"\"}],\"returntype\":\"\"}," +
                "{\"name\":\"placeBet\",\"parameters\":[{\"name\":\"address\",\"type\":\"\"},{\"name\":\"gameID\",\"type\":\"\"},{\"name\":\"HorV\",\"type\":\"\"},{\"name\":\"amount\",\"type\":\"\"}],\"returntype\":\"\"}," +
                "{\"name\":\"endBet\",\"parameters\":[{\"name\":\"date\",\"type\":\"\"}],\"returntype\":\"\"}," +
                "{\"name\":\"inputMatch\",\"parameters\":[{\"name\":\"date\",\"type\":\"String\"},{\"name\":\"gameID\",\"type\":\"String\"},{\"name\":\"hTeamID\",\"type\":\"String\"},{\"name\":\"vTeamID\",\"type\":\"String\"}],\"returntype\":\"\"}," +
                "{\"name\":\"callOracle\",\"parameters\":[{\"name\":\"date\",\"type\":\"String\"}],\"returntype\":\"\"}," +
                "{\"name\":\"setResult\",\"parameters\":[{\"name\":\"date\",\"type\":\"\"}],\"returntype\":\"\"}," +
                "{\"name\":\"manualSetResult\",\"parameters\":[{\"name\":\"date\",\"type\":\"\"},{\"name\":\"index\",\"type\":\"\"},{\"name\":\"gameid\",\"type\":\"\"},{\"name\":\"hscore\",\"type\":\"\"},{\"name\":\"vscore\",\"type\":\"\"}],\"returntype\":\"\"}," +
                "{\"name\":\"queryAccountBalance\",\"parameters\":[{\"name\":\"address\",\"type\":\"\"}],\"returntype\":\"\"},{\"name\":\"withdraw\",\"parameters\":[{\"name\":\"address\",\"type\":\"\"},{\"name\":\"amount\",\"type\":\"\"}],\"returntype\":\"\"}," +
                "{\"name\":\"adminWithdraw\",\"parameters\":[{\"name\":\"\",\"type\":\"\"}],\"returntype\":\"\"}," +
                "{\"name\":\"_require\",\"parameters\":[{\"name\":\"regx\",\"type\":\"\"}],\"returntype\":\"\"}," +
                "{\"name\":\"_concatKey\",\"parameters\":[{\"name\":\"str1\",\"type\":\"\"},{\"name\":\"str2\",\"type\":\"\"}],\"returntype\":\"\"}," +
                "{\"name\":\"_concatStrs\",\"parameters\":[{\"name\":\"strarr\",\"type\":\"\"},{\"name\":\"spliter\",\"type\":\"\"}],\"returntype\":\"\"}," +
                "{\"name\":\"_transferONG\",\"parameters\":[{\"name\":\"fromacct\",\"type\":\"\"},{\"name\":\"toacct\",\"type\":\"\"},{\"name\":\"amount\",\"type\":\"\"}],\"returntype\":\"\"}," +
                "{\"name\":\"_transferONGFromContract\",\"parameters\":[{\"name\":\"toacct\",\"type\":\"\"},{\"name\":\"amount\",\"type\":\"\"}],\"returntype\":\"\"},{\"name\":\"_distributeRewards\",\"parameters\":[{\"name\":\"totalBets\",\"type\":\"\"},{\"name\":\"winnerBets\",\"type\":\"\"},{\"name\":\"betinfos\",\"type\":\"\"}],\"returntype\":\"\"},{\"name\":\"_itoa\",\"parameters\":[{\"name\":\"i\",\"type\":\"\"}],\"returntype\":\"\"}]}";

        AbiInfo abiInfo = JSON.parseObject(abi, AbiInfo.class);



        boolean inputMatch = false;
        boolean callOracle = true;
        if(inputMatch) {
            AbiFunction func = abiInfo.getFunction("inputMatch");
            func.setParamsValue("20181221","sss","sss","sss");
            String txhash = (String) sdk.neovm().sendTransaction(Helper.reverse(contractAddress),adminAccount,adminAccount,2000000,0,func,false);

            Thread.sleep(6000);
            System.out.println(sdk.getConnect().getSmartCodeEvent(txhash));
            return;
        }
        if(callOracle) {
            AbiFunction func = abiInfo.getFunction("callOracle");
            func.setParamsValue("20181220");
            String txhash = (String) sdk.neovm().sendTransaction(Helper.reverse(contractAddress),adminAccount,adminAccount,2000000,0,func,false);

            Thread.sleep(6000);
            System.out.println(sdk.getConnect().getSmartCodeEvent(txhash));
            return;
        }
    }
}
