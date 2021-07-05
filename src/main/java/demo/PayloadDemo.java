package demo;

import com.github.ontio.OntSdk;
import com.github.ontio.common.Address;
import com.github.ontio.common.Helper;
import com.github.ontio.core.asset.State;
import com.github.ontio.core.payload.InvokeCode;
import com.github.ontio.core.transaction.Transaction;
import com.github.ontio.core.transaction.TransactionType;
import com.github.ontio.io.BinaryReader;
import com.github.ontio.smartcontract.neovm.Oep4;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.math.BigInteger;

import static com.github.ontio.common.Helper.BigIntFromNeoBytes;
import static com.github.ontio.core.scripts.ScriptOp.*;
import static com.github.ontio.smartcontract.Vm.NATIVE_INVOKE_NAME;

public class PayloadDemo {

    static class TransferInfo {
        public String assetAddress;
        public String from;
        public String to;
        public long value;

        public TransferInfo(String assetAddress, String from, String to, long value) {
            this.assetAddress = assetAddress;
            this.from = from;
            this.to = to;
            this.value = value;
        }
    }


    public static String TRANSFER = "transfer";
    public static String ontContract = "0100000000000000000000000000000000000000";
    public static String ongContract = "0200000000000000000000000000000000000000";
    public static byte[] nativeInvokeNameBytes = NATIVE_INVOKE_NAME.getBytes();
    public static byte[] nativeInvoke = new byte[nativeInvokeNameBytes.length];

    public static void main(String[] args) throws Exception {
        OntSdk ontSdk = getOntSdk();
        Oep4 oo = ontSdk.neovm().oep4();
        oo.setContractAddress("5e0aebb3dcc7af619e019a8f2195151d4d59644d");
        String from = "AFqw9nGHiskTsrfqyMQr3Y3hZ759VpH5j1";
        String to = "ANtmXW56adweZkdYmxoqayH8ELbVvXYKu4";

        System.out.println("from:" + Address.decodeBase58(from).toHexString());
        System.out.println("to:" + Address.decodeBase58(to).toHexString());

        State state;
        //parse native
        for (long amount = 100000; amount < 100000; amount++) {
            System.out.println("amount:" + amount);
            Transaction tx = ontSdk.nativevm().ont().makeTransfer(from, to, amount, from, 20000, 500);
            TransferInfo info = parseTxPayload(tx);
            if (!info.from.equals(from)) {
                throw new Exception("invalid from");
            }
            if (!info.to.equals(to)) {
                throw new Exception("invalid to");
            }
            if (info.value != amount) {
                throw new Exception("invalid amount" + info.value + ":" + amount);
            }
        }

        //parse oep4
        for (long amount = 100000; amount < 1000000; amount++) {
            Transaction tx = oo.makeTransfer(from, to, amount, from, 20000, 500);
            TransferInfo info = parseTxPayload(tx);
            if (!info.from.equals(from)) {
                throw new Exception("invalid from");
            }
            if (!info.to.equals(to)) {
                throw new Exception("invalid to");
            }
            if (info.value != amount) {
                throw new Exception("invalid to");
            }
        }

//        System.out.println("info:" + JSON.toJSONString(info));
        System.out.println("from:" + Address.decodeBase58(from).toHexString());
        System.out.println("to:" + Address.decodeBase58(to).toHexString());
        System.out.println(Helper.toHexString(TRANSFER.getBytes()));
    }

    public static TransferInfo parseTxPayload(Transaction tx) throws IOException {
        if (tx.txType == TransactionType.InvokeCode) {
            InvokeCode invokeTx = (InvokeCode) tx;
            byte[] code = invokeTx.code;
            int len = code.length;
            //22 + 1 + 1 + 1 + 20 = 45
            if (len > 45) {
                System.arraycopy(code, len - 22, nativeInvoke, 0, nativeInvokeNameBytes.length);
                if (new String(nativeInvoke).equals(NATIVE_INVOKE_NAME)) {
                    return parseNativePayload(code);
                } else {
                    return parseOEP4Payload(code);
                }
            }
        }
        return null;
    }

    //00
    // c6
    // 6b
    // 14
    // 00c496ee298fedab9179ea753ba41ccb02798a15     from
    // 6a7cc8
    // 14
    // 4e16aed7a3b1fd8a22a1ffa3fc880157398bfab7     to
    // 6a
    // 7c
    // c8
    // 51   amount
    // 6a7cc86c51c1
    // 08
    // 7472616e73666572
    // 14
    // 0000000000000000000000000000000000000001
    // 00
    // 68
    // 16
    // 4f6e746f6c6f67792e4e61746976652e496e766f6b65
    //ont ong
    public static TransferInfo parseNativePayload(byte[] payload) throws IOException {
        //22 + 1 + 1 + 1 + 20 = 45
        if (payload.length > 45) {
            byte[] contractAddrBytes = new byte[20];
            System.arraycopy(payload, payload.length - 45, contractAddrBytes, 0, 20);
            String contractAddr = new Address(contractAddrBytes).toHexString();
            if (contractAddr.equals(ontContract) || contractAddr.equals(ongContract)) {
                // 45 + 1 + 8 = 54
                if (payload.length > 54) {
                    byte[] transferCode = new byte[TRANSFER.getBytes().length];
                    System.arraycopy(payload, payload.length - 46 - 8, transferCode, 0, transferCode.length);
                    if (new String(transferCode).equals(TRANSFER)) {
                        ByteArrayInputStream ms = new ByteArrayInputStream(payload);
                        BinaryReader reader = new BinaryReader(ms);
                        skipOpCode(reader, 3);
                        Address from = readAddress(reader);
                        skipOpCode(reader, 3);
                        Address to = readAddress(reader);
                        skipOpCode(reader, 3);
                        long val = readValue(reader, payload.length);
                        return new TransferInfo(contractAddr, from.toBase58(), to.toBase58(), val);
                    }
                }
            }
        }
        return null;
    }


    // 02
    // 8000
    // 14
    // 4e16aed7a3b1fd8a22a1ffa3fc880157398bfab7   to
    // 14
    // 00c496ee298fedab9179ea753ba41ccb02798a15    from
    // 53c1
    // 087472616e73666572
    // 67
    // 4d64594d1d1595218f9a019e61afc7dcb3eb0a5e
    public static TransferInfo parseOEP4Payload(byte[] code) throws IOException {
        byte[] contractAddrBytes = new byte[20];
        System.arraycopy(code, code.length - 20, contractAddrBytes, 0, 20);
        Address contractAddr = new Address(contractAddrBytes);
        byte[] transferCode = new byte[TRANSFER.getBytes().length];
        System.arraycopy(code, code.length - 20 - 1 - transferCode.length, transferCode, 0, transferCode.length);
        if (new String(transferCode).equals(TRANSFER)) {
            ByteArrayInputStream ms = new ByteArrayInputStream(code);
            BinaryReader reader = new BinaryReader(ms);
            long val = readValue(reader, code.length);
            Address to = readAddress(reader);
            Address from = readAddress(reader);
            return new TransferInfo(contractAddr.toHexString(), from.toBase58(), to.toBase58(), val);
        }
        return null;
    }

    public static void skipOpCode(BinaryReader reader, int num) throws IOException {
        for (int i = 0; i < num; i++) {
            reader.readByte();
        }
    }

    public static long readValue(BinaryReader reader, int length) throws IOException {
        byte by = reader.readByte();
        long amount = 0;
        if (by == 0) {
            return amount;
        } else if (by >= OP_PUSH1.getByte() && by <= OP_PUSH16.getByte()) {
            BigInteger b = BigIntFromNeoBytes(new byte[]{by});
            amount = b.longValue() - 0x50;
            return amount;
        } else {
            int remain = reader.available();
            reader.Seek(length - remain - 1);
            byte[] bs = reader.readVarBytes();
            BigInteger b = BigIntFromNeoBytes(bs);
            return b.longValue();
        }
    }

    public static Address readAddress(BinaryReader reader) throws IOException {
        byte[] bs = reader.readVarBytes();
        return new Address(bs);
    }

    public static boolean isOpCode(byte by) {
        if ((by >= 0x00 && by <= 0x01) || (by >= OP_PUSHBYTES75.getByte() && by <= OP_PUSHM1.getByte()) ||
                (by >= OP_NOP.getByte() && by <= OP_SUBSTR.getByte()) ||
                (by > OP_LEFT.getByte() && by < OP_MINITEM.getByte())) {
            return true;
        } else {
            return false;
        }
    }

    public static OntSdk getOntSdk() throws Exception {
//        String ip = "http://139.219.108.204";
        String ip = "http://127.0.0.1";
//        String ip = "http://101.132.193.149";
        String restUrl = ip + ":" + "20334";
        String rpcUrl = ip + ":" + "20336";
        String wsUrl = ip + ":" + "20335";

        OntSdk wm = OntSdk.getInstance();
        wm.setRpc(rpcUrl);
        wm.setRestful(restUrl);
        wm.setDefaultConnect(wm.getRestful());

        wm.openWalletFile("Demo3.json");

        return wm;
    }
}
