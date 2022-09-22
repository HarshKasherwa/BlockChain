public class UTXO {
    private Transaction txn;

    private final int index;

    private final String address;

    private final double amount;
    public UTXO(int index, String address, double amount) {
        this.address = address;
        this.amount = amount;
        this.index = index;
    }
    public void setTxn(Transaction txn) {
        this.txn = txn;
    }

    public Transaction getTxn() {
        return txn;
    }

    public String getAddress() {
        return address;
    }

    public double getAmount() {
        return amount;
    }

    public int getIndex() {
        return index;
    }
}
