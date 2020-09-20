package demo.wing;

import com.alibaba.fastjson.JSONObject;
import com.github.ontio.OntSdk;
import com.github.ontio.account.Account;
import com.github.ontio.common.Address;
import com.github.ontio.common.Helper;
import com.github.ontio.core.transaction.Transaction;
import com.github.ontio.crypto.SignatureScheme;
import com.github.ontio.io.BinaryReader;
import com.github.ontio.ontid.Util;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

public class WingDemo {

    public static void main(String[] args) throws IOException {
        // 1. sdk初始化
        String ip = "http://polaris3.ont.io";
        String rpcUrl = ip + ":" + "20336";
        OntSdk sdk = OntSdk.getInstance();
        sdk.setRpc(rpcUrl);

        long gasPrice = 500;
        long gasLimit = 20000;

        if (false){

        }


        try {
//            String privatekey0 = "c19f16785b8f3543bbaf5e1dbb5d398dfa6c85aaad54fc9d71203ce83e505c07";
            String privatekey1 = "2ab720ff80fcdd31a769925476c26120a879e235182594fbb57b67c0743558d7";
            com.github.ontio.account.Account userAccount = new com.github.ontio.account.Account(Helper.hexToBytes(privatekey1), SignatureScheme.SHA256WITHECDSA);

            System.out.println("userAccount address: " + userAccount.getAddressU160().toBase58());

            // 2. 调用合约中的方法
            String wingContractAddr = "791d42c167d19f05a6b1936896d989f442f0ff58";

            // 列出所有市场合约地址
            String method = "allMarkets";
            List param = new ArrayList<>();
            Transaction tx = sdk.wasmvm().makeInvokeCodeTransaction(wingContractAddr,method,param,userAccount.getAddressU160(),gasLimit, gasPrice);
            Object res = sdk.getConnect().sendRawTransactionPreExec(tx.toHexString());
            JSONObject obj = ((JSONObject)res);
            Address[] markets = Utils.parseAddressArray(obj.getString("Result"));

            // 3. 查询market信息
            method = "marketMeta";
            param = new ArrayList<>();
            param.add(markets[0]);
            tx = sdk.wasmvm().makeInvokeCodeTransaction(wingContractAddr,method,param,userAccount.getAddressU160(),gasLimit, gasPrice);
            res = sdk.getConnect().sendRawTransactionPreExec(tx.toHexString());
            obj = ((JSONObject)res);
            Market m = Utils.parseMarket(obj.getString("Result"));
            System.out.println(m);

            // 4. 查询ftoken的 exchange_rate
            method = "exchangeRateCurrent";
            param = new ArrayList<>();
            tx = sdk.wasmvm().makeInvokeCodeTransaction(markets[0].toHexString(),method,param,userAccount.getAddressU160(),gasLimit, gasPrice);
            res = sdk.getConnect().sendRawTransactionPreExec(tx.toHexString());
            obj = ((JSONObject)res);
            byte[] d = Helper.hexToBytes(obj.getString("Result"));
            ByteArrayInputStream stream = new ByteArrayInputStream(d);
            BinaryReader reader = new BinaryReader(stream);
            long exchange_rate= Utils.parseU128(reader);

            // 5. 查询ftoken的 total_borrows
            method = "totalBorrows";
            param = new ArrayList<>();
            tx = sdk.wasmvm().makeInvokeCodeTransaction(markets[0].toHexString(),method,param,userAccount.getAddressU160(),gasLimit, gasPrice);
            res = sdk.getConnect().sendRawTransactionPreExec(tx.toHexString());
            obj = ((JSONObject)res);
            d = Helper.hexToBytes(obj.getString("Result"));
            stream = new ByteArrayInputStream(d);
            reader = new BinaryReader(stream);
            long totalBorrows= Utils.parseU128(reader);


            // 6. 查询ftoken的 total_supply
            method = "totalSupply";
            param = new ArrayList<>();
            tx = sdk.wasmvm().makeInvokeCodeTransaction(markets[0].toHexString(),method,param,userAccount.getAddressU160(),gasLimit, gasPrice);
            res = sdk.getConnect().sendRawTransactionPreExec(tx.toHexString());
            obj = ((JSONObject)res);
            d = Helper.hexToBytes(obj.getString("Result"));
            stream = new ByteArrayInputStream(d);
            reader = new BinaryReader(stream);
            long totalSupply= Utils.parseU128(reader);

            // 7. 查询ftoken的 underlyingName
            method = "underlyingName";
            param = new ArrayList<>();
            tx = sdk.wasmvm().makeInvokeCodeTransaction(markets[0].toHexString(),method,param,userAccount.getAddressU160(),gasLimit, gasPrice);
            res = sdk.getConnect().sendRawTransactionPreExec(tx.toHexString());
            obj = ((JSONObject)res);
            d = Helper.hexToBytes(obj.getString("Result"));
            stream = new ByteArrayInputStream(d);
            reader = new BinaryReader(stream);
            String underlyingName = reader.readVarString();

            // 8. 查询underlying_price 要从oralce合约中查询
            String oracleContractAddr = "";
            method = "getUnderlyingPrice";
            param = new ArrayList<>();
            tx = sdk.wasmvm().makeInvokeCodeTransaction(oracleContractAddr,method,param,userAccount.getAddressU160(),gasLimit, gasPrice);
            res = sdk.getConnect().sendRawTransactionPreExec(tx.toHexString());
            obj = ((JSONObject)res);
            d = Helper.hexToBytes(obj.getString("Result"));
            stream = new ByteArrayInputStream(d);
            reader = new BinaryReader(stream);
            long underlyingPrice = Utils.parseU128(reader);


            // 9. 查询ftoken 的 symbol
            method = "symbol";
            param = new ArrayList<>();
            tx = sdk.wasmvm().makeInvokeCodeTransaction(markets[0].toHexString(),method,param,userAccount.getAddressU160(),gasLimit, gasPrice);
            res = sdk.getConnect().sendRawTransactionPreExec(tx.toHexString());
            obj = ((JSONObject)res);
            d = Helper.hexToBytes(obj.getString("Result"));
            stream = new ByteArrayInputStream(d);
            reader = new BinaryReader(stream);
            String symbol = reader.readVarString();


            // 9. 查询wing_distributed
            // flash合约的wingDistributedNum方法，传入ftoken address
            String flashContractAddr = "";
            method = "wingDistributedNum";
            param = new ArrayList<>();
            param.add(markets[0]);
            tx = sdk.wasmvm().makeInvokeCodeTransaction(flashContractAddr,method,param,userAccount.getAddressU160(),gasLimit, gasPrice);
            res = sdk.getConnect().sendRawTransactionPreExec(tx.toHexString());
            obj = ((JSONObject)res);
            d = Helper.hexToBytes(obj.getString("Result"));
            stream = new ByteArrayInputStream(d);
            reader = new BinaryReader(stream);
            long wingDistributedNum = Utils.parseU128(reader);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

