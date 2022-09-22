import java.security.PrivateKey;
import java.util.ArrayList;

public class TxnOutput {

    private final int index;
    private final String address;
    private final double amount;

    public TxnOutput(int index, String address, double amount) {
        this.address = address;
        this.amount = amount;
        this.index = index;
    }

    public String getAddress() {
        return address;
    }

    public double getAmount() {
        return amount;
    }

    public  int getIndex() {
        return index;
    }
}
