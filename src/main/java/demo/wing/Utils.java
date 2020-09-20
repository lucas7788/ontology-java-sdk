package demo.wing;

import com.github.ontio.common.Address;
import com.github.ontio.common.Helper;
import com.github.ontio.io.BinaryReader;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.math.BigInteger;

public class Utils {

    public static Address[] parseAddressArray(String data) throws IOException {
//        String data = "06ee009854c16f81a89121204ce799378ded21934c011bca853eaebbeb2ff26e4bc0b8f2b54d516b053e11e4ea490ff79911b06f875cc574a30ac56b4cb2111f3f3e6cca2329a2575f18052b068ec0152fb9fbdece97ae89b4a37b97eaa86b8bc128e6e62a25649922f7c3ebf74a720d0cb627ed8033da0783";
        byte[] d = Helper.hexToBytes(data);
        ByteArrayInputStream stream = new ByteArrayInputStream(d);
        BinaryReader reader = new BinaryReader(stream);
        long l = reader.readVarInt();
        Address[] addrs = new Address[(int)l];
        for (int i =0;i<l;i++) {
            Address addr = new Address();
            addr.deserialize(reader);
            addrs[i] = addr;
        }
        System.out.println(addrs);
        return addrs;
    }


    public static long parseU128(BinaryReader reader) throws IOException {
        byte[] weight = reader.readBytes(16);
        BigInteger b = Helper.BigIntFromNeoBytes(weight);
        return b.longValue();
    }

    public static Market parseMarket(String data) throws IOException {
        byte[] d = Helper.hexToBytes(data);
        ByteArrayInputStream stream = new ByteArrayInputStream(d);
        BinaryReader reader = new BinaryReader(stream);
        Market m = new Market();
        m.deserialize(reader);
        return m;
    }
}
