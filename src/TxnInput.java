import java.util.ArrayList;

public class TxnInput {

    public int count;
    public ArrayList<String> prev_txn;
    public ArrayList<Integer> index;

    public ArrayList<Double> value;

    public TxnInput() {
        this.count = 0;
        this.prev_txn = new ArrayList<>();
        this.index = new ArrayList<>();
    }

    public void add_txn(String txnHash, int index)  {
        prev_txn.add(count,txnHash);
        this.index.add(count, index);
        this.count++;
    }
    public ArrayList<String> getPrev_txn() {
        return prev_txn;
    }

    public ArrayList<Integer> getIndex() {
        return index;
    }
}
