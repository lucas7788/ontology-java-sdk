package demo;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.ontio.OntSdk;
import com.github.ontio.account.Account;
import com.github.ontio.common.Helper;
import com.github.ontio.smartcontract.neovm.abi.AbiFunction;
import com.github.ontio.smartcontract.neovm.abi.AbiInfo;

public class OntBetZhongYao {
    public static void main(String[] args) throws Exception {
        OntSdk sdk = OntSdk.getInstance();
        String localRpc = "http://polaris1.ont.io:20336";
        String zhuwangRpc = "http://dappnode1.ont.io:20336";
        sdk.setRpc(zhuwangRpc);
        sdk.openWalletFile("cynowallet.dat");
        Account account = sdk.getWalletMgr().getAccount("AbPRaepcpBAFHz9zCj4619qch4Aq5hJARA","LUlu@665211");

        String contractAddr = "3ea96606811260613d01127b340d4d8e0bd03340";
        String abiStr = "{\"functions\":[{\"name\":\"Main\",\"parameters\":[{\"name\":\"operation\",\"type\":\"\"},{\"name\":\"args\",\"type\":\"\"}],\"returntype\":\"\"},{\"name\":\"init\",\"parameters\":[],\"returntype\":\"\"},{\"name\":\"setParameters\",\"parameters\":[{\"name\":\"dividendForBankersPercentage\",\"type\":\"\"},{\"name\":\"runningVaultPercentage\",\"type\":\"\"}],\"returntype\":\"\"},{\"name\":\"withdrawCommission\",\"parameters\":[{\"name\":\"\",\"type\":\"\"}],\"returntype\":\"\"},{\"name\":\"migrateContract\",\"parameters\":[{\"name\":\"code\",\"type\":\"\"},{\"name\":\"needStorage\",\"type\":\"\"},{\"name\":\"name\",\"type\":\"\"},{\"name\":\"version\",\"type\":\"\"},{\"name\":\"author\",\"type\":\"\"},{\"name\":\"email\",\"type\":\"\"},{\"name\":\"description\",\"type\":\"\"},{\"name\":\"newReversedContractHash\",\"type\":\"\"}],\"returntype\":\"\"},{\"name\":\"bankerInvest\",\"parameters\":[{\"name\":\"account\",\"type\":\"ByteArray\"},{\"name\":\"ongAmount\",\"type\":\"Integer\"}],\"returntype\":\"\"},{\"name\":\"_bankerInvest\",\"parameters\":[{\"name\":\"account\",\"type\":\"\"},{\"name\":\"ongAmount\",\"type\":\"\"}],\"returntype\":\"\"},{\"name\":\"bankerWithdrawDividend\",\"parameters\":[{\"name\":\"account\",\"type\":\"\"}],\"returntype\":\"\"},{\"name\":\"bankerWithdrawEarning\",\"parameters\":[{\"name\":\"account\",\"type\":\"\"}],\"returntype\":\"\"},{\"name\":\"bankerWithdraw\",\"parameters\":[{\"name\":\"account\",\"type\":\"\"}],\"returntype\":\"\"},{\"name\":\"bankerWithdrawBeforeExit\",\"parameters\":[{\"name\":\"account\",\"type\":\"\"}],\"returntype\":\"\"},{\"name\":\"bankerExit\",\"parameters\":[{\"name\":\"account\",\"type\":\"\"}],\"returntype\":\"\"},{\"name\":\"bet\",\"parameters\":[{\"name\":\"account\",\"type\":\"ByteArray\"},{\"name\":\"ongAmount\",\"type\":\"Integer\"},{\"name\":\"number\",\"type\":\"Integer\"}],\"returntype\":\"\"},{\"name\":\"getCurrentRound\",\"parameters\":[{\"name\":\"\",\"type\":\"\"}],\"returntype\":\"\"},{\"name\":\"getDividendForBankersPercentage\",\"parameters\":[{\"name\":\"roundNumber\",\"type\":\"\"}],\"returntype\":\"\"},{\"name\":\"getRunningVaultPercentage\",\"parameters\":[{\"name\":\"roundNumber\",\"type\":\"\"}],\"returntype\":\"\"},{\"name\":\"getTotalONG\",\"parameters\":[{\"name\":\"\",\"type\":\"\"}],\"returntype\":\"\"},{\"name\":\"getCommission\",\"parameters\":[{\"name\":\"\",\"type\":\"\"}],\"returntype\":\"\"},{\"name\":\"getRoundGameStatus\",\"parameters\":[{\"name\":\"roundNumber\",\"type\":\"Integer\"}],\"returntype\":\"\"},{\"name\":\"getBankersInvestment\",\"parameters\":[{\"name\":\"roundNumber\",\"type\":\"\"}],\"returntype\":\"\"},{\"name\":\"getIncreasingRunnVault\",\"parameters\":[{\"name\":\"roundNumber\",\"type\":\"Integer\"}],\"returntype\":\"\"},{\"name\":\"getRunningVault\",\"parameters\":[{\"name\":\"roundNumber\",\"type\":\"\"}],\"returntype\":\"\"},{\"name\":\"getRealTimeRunningVault\",\"parameters\":[{\"name\":\"roundNumber\",\"type\":\"Integer\"}],\"returntype\":\"\"},{\"name\":\"getBankersList\",\"parameters\":[{\"name\":\"roundNumber\",\"type\":\"\"},{\"name\":\"includeExitBankers\",\"type\":\"\"}],\"returntype\":\"\"},{\"name\":\"getBankerInvestment\",\"parameters\":[{\"name\":\"roundNumber\",\"type\":\"\"},{\"name\":\"account\",\"type\":\"\"}],\"returntype\":\"\"},{\"name\":\"getBankerBalanceInRunVault\",\"parameters\":[{\"name\":\"roundNumber\",\"type\":\"\"},{\"name\":\"account\",\"type\":\"\"}],\"returntype\":\"\"},{\"name\":\"getProfitPerInvestmentForBankers\",\"parameters\":[{\"name\":\"roundNumber\",\"type\":\"\"}],\"returntype\":\"\"},{\"name\":\"getProfitPerRunningVaultShare\",\"parameters\":[{\"name\":\"roundNumber\",\"type\":\"\"}],\"returntype\":\"\"},{\"name\":\"getBankersLastTimeUpdateDividendRound\",\"parameters\":[{\"name\":\"account\",\"type\":\"\"}],\"returntype\":\"\"},{\"name\":\"getBankersLastTimeUpdateEarnRound\",\"parameters\":[{\"name\":\"account\",\"type\":\"\"}],\"returntype\":\"\"},{\"name\":\"getRunVaultShare\",\"parameters\":[{\"name\":\"account\",\"type\":\"\"}],\"returntype\":\"\"},{\"name\":\"getBankerEarning\",\"parameters\":[{\"name\":\"account\",\"type\":\"\"}],\"returntype\":\"\"},{\"name\":\"getBankerDividend\",\"parameters\":[{\"name\":\"account\",\"type\":\"\"}],\"returntype\":\"\"},{\"name\":\"checkInBankerList\",\"parameters\":[{\"name\":\"account\",\"type\":\"\"},{\"name\":\"bankersList\",\"type\":\"\"}],\"returntype\":\"\"},{\"name\":\"updateBankerDividend\",\"parameters\":[{\"name\":\"account\",\"type\":\"\"}],\"returntype\":\"\"},{\"name\":\"updateBankerEarning\",\"parameters\":[{\"name\":\"account\",\"type\":\"\"}],\"returntype\":\"\"},{\"name\":\"_calculatePayOutToWin\",\"parameters\":[{\"name\":\"ongAmount\",\"type\":\"\"},{\"name\":\"number\",\"type\":\"\"}],\"returntype\":\"\"},{\"name\":\"_rollANumber\",\"parameters\":[],\"returntype\":\"\"},{\"name\":\"_transferONG\",\"parameters\":[{\"name\":\"fromAcct\",\"type\":\"\"},{\"name\":\"toAcct\",\"type\":\"\"},{\"name\":\"amount\",\"type\":\"\"}],\"returntype\":\"\"},{\"name\":\"_transferONGFromContact\",\"parameters\":[{\"name\":\"toAcct\",\"type\":\"\"},{\"name\":\"amount\",\"type\":\"\"}],\"returntype\":\"\"},{\"name\":\"concatKey\",\"parameters\":[{\"name\":\"str1\",\"type\":\"\"},{\"name\":\"str2\",\"type\":\"\"}],\"returntype\":\"\"},{\"name\":\"Revert\",\"parameters\":[{\"name\":\"\",\"type\":\"\"}],\"returntype\":\"\"},{\"name\":\"Require\",\"parameters\":[{\"name\":\"condition\",\"type\":\"\"}],\"returntype\":\"\"},{\"name\":\"RequireScriptHash\",\"parameters\":[{\"name\":\"key\",\"type\":\"\"}],\"returntype\":\"\"},{\"name\":\"RequireWitness\",\"parameters\":[{\"name\":\"witness\",\"type\":\"\"}],\"returntype\":\"\"},{\"name\":\"Add\",\"parameters\":[{\"name\":\"a\",\"type\":\"\"},{\"name\":\"b\",\"type\":\"\"}],\"returntype\":\"\"},{\"name\":\"Sub\",\"parameters\":[{\"name\":\"a\",\"type\":\"\"},{\"name\":\"b\",\"type\":\"\"}],\"returntype\":\"\"},{\"name\":\"ASub\",\"parameters\":[{\"name\":\"a\",\"type\":\"\"},{\"name\":\"b\",\"type\":\"\"}],\"returntype\":\"\"},{\"name\":\"Mul\",\"parameters\":[{\"name\":\"a\",\"type\":\"\"},{\"name\":\"b\",\"type\":\"\"}],\"returntype\":\"\"},{\"name\":\"Div\",\"parameters\":[{\"name\":\"a\",\"type\":\"\"},{\"name\":\"b\",\"type\":\"\"}],\"returntype\":\"\"},{\"name\":\"Pwr\",\"parameters\":[{\"name\":\"a\",\"type\":\"\"},{\"name\":\"b\",\"type\":\"\"}],\"returntype\":\"\"},{\"name\":\"Sqrt\",\"parameters\":[{\"name\":\"a\",\"type\":\"\"}],\"returntype\":\"\"}]}";
        AbiInfo abiInfo = JSON.parseObject(abiStr, AbiInfo.class);

        final long currentRound = 5;
        final String STATUS_ON = "RUNNING";
        final String STATUS_OFF = "END";

        while (true) {

            //查询状态
            AbiFunction getRoundGameStatusFunc = abiInfo.getFunction("getRoundGameStatus");
            getRoundGameStatusFunc.setParamsValue(currentRound);
            Object result = sdk.neovm().sendTransaction(Helper.reverse(contractAddr),account,account,2000000,500,getRoundGameStatusFunc,true);
            String status = new String(Helper.hexToBytes(((JSONObject)result).getString("Result")));
            if (status.equals(STATUS_ON)) {

                System.out.println(STATUS_ON);

            } else if(status.equals(STATUS_OFF)) {

                //如果是STATUS_OFF，就冲进去
                AbiFunction bankerInvestFunc = abiInfo.getFunction("bankerInvest");
                bankerInvestFunc.setParamsValue(account.getAddressU160().toArray(),1001*1000000000L);
                String txHash = (String) sdk.neovm().sendTransaction(Helper.reverse(contractAddr),account,account,2100000,500,bankerInvestFunc,false);
                Thread.sleep(6000);
                Object obj = sdk.getConnect().getSmartCodeEvent(txHash);
                System.out.println(obj);
                break;
            }
            Thread.sleep(100);
        }
    }
}
