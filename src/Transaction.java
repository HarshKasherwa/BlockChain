import java.time.Instant;
import java.util.ArrayList;
import java.util.List;


public class Transaction {

    private String txnID;
    private final String txnType;
    public int blockNumber;
    private final int input_count;
    private ArrayList<TxnInput> input;
    private final int output_count;
    private final ArrayList<TxnOutput> output;
    private String timeStamp;
    private boolean confirmation;

    //coinbase transaction constructor
    public Transaction(String txnType, int blockNumber,int input_count,
                       int output_count, ArrayList<TxnOutput> output,
                       boolean confirmation) {

        this.txnID = null;
        this.txnType = txnType;
        this.blockNumber = blockNumber;
        this.input_count = input_count;
        this.output_count = output_count;
        this.output = output;
        this.confirmation = confirmation;
    }

    //Normal txn constructor
    public Transaction(String txnType, int blockNumber, int input_count,
                       ArrayList<TxnInput> input, int output_count,
                       ArrayList<TxnOutput> output, boolean confirmation) {

        this.txnID = null;
        this.txnType = txnType;
        this.blockNumber = blockNumber;
        this.input_count = input_count;
        this.input = input;
        this.output_count = output_count;
        this.output = output;
        this.confirmation = confirmation;
    }

    public void calculateTxID(){

        Instant time = Instant.now();
        this.timeStamp = time.toString();
        String txnData = "";
        if (this.input_count == 0)    {
            txnData = txnData + txnType + timeStamp + blockNumber + input_count +
                    output_count + output;
        }
        else {
            txnData = txnData + txnType + timeStamp + blockNumber +
                    input_count + input +
                    output_count + output;
        }

        HashCal hash = new HashCal();
        this.txnID = hash.calculateHash(txnData);
    }

    public String getTxnID() {
        return txnID;
    }

    public String getTxnType() {
        return txnType;
    }

    public int getBlockNumber() {
        return blockNumber;
    }

    public int getInput_count() {
        return input_count;
    }

    public ArrayList<TxnInput> getInput() {
        return input;
    }

    public int getOutput_count() {
        return output_count;
    }

    public ArrayList<TxnOutput> getOutput() {
        return output;
    }
}
