package demo.wing;

import com.github.ontio.common.Address;
import com.github.ontio.common.Helper;
import com.github.ontio.common.UInt256;
import com.github.ontio.io.BinaryReader;
import com.github.ontio.io.BinaryWriter;
import com.github.ontio.io.Serializable;

import java.io.IOException;
import java.math.BigInteger;

public class Market implements Serializable {
    public Address addr;
    public Address insuranceAddr;
    public boolean isListed;
    /// @notice Whether or not this market receives WING
    public boolean receiveWing;

    /// @notice weight of WING distribution at each market
    public long wingWeight;

    /**
     * @notice Multiplier representing the most one can borrow against their collateral in this market.
     *  For instance, 0.9 to allow borrowing 90% of collateral value.
     *  Must be between 0 and 1, and stored as a mantissa.
     */
    public long collateralFactorMantissa;

    public int underlyingDecimals;

    @Override
    public void deserialize(BinaryReader reader) throws IOException {
        try {
            this.addr = reader.readSerializable(Address.class);
            this.insuranceAddr = reader.readSerializable(Address.class);
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        this.isListed = reader.readBoolean();
        this.receiveWing = reader.readBoolean();
        this.wingWeight = Utils.parseU128(reader);
        this.collateralFactorMantissa = Utils.parseU128(reader);
        this.underlyingDecimals = reader.readInt();
    }

    @Override
    public void serialize(BinaryWriter writer) throws IOException {

    }
}
