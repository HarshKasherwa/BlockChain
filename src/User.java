import javax.swing.text.Utilities;
import java.security.Signature;
import java.security.SignatureException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

public class User extends Wallet{

    public ArrayList<UTXO> select_coins(Wallet sender, double amount)   {

        ArrayList<UTXO> selected_Txn = new ArrayList<>();
        ArrayList<UTXO> utxo_list = sender.getUTXOs();
        UTXO[] arr = new UTXO[utxo_list.size()];
        int a = 0;
        for (UTXO txn: utxo_list)   {
            arr[a++] = txn;
        }

        Arrays.sort(arr, new Comparator<UTXO>(){

            @Override
            public int compare(UTXO o1, UTXO o2) {
                int diff = o2.getAmount() >= o1.getAmount() ? 1 : -1;
                return diff;
            }
        });

        utxo_list = new ArrayList<UTXO>(Arrays.asList(arr));
        double sum = 0;
        for (UTXO txn: utxo_list)  {
            if ((sum+txn.getAmount()) >= amount) {
                selected_Txn.add(txn);
                break;
            }
            else {
                selected_Txn.add(txn);
                sum+= txn.getAmount();
            }
        }
        return selected_Txn;
    }

    public double cal_amount(ArrayList<UTXO> list)  {
        double sum = 0;
        for (UTXO txn: list)
            sum+=txn.getAmount();

        return sum;
    }
    public Transaction initiateTxn(Miner sender, Miner receiver, double amount)  {

        ArrayList<UTXO> input_utxo = new ArrayList<>();
        ArrayList<UTXO> ouput_utxo = new ArrayList<>();
        input_utxo = select_coins(sender, amount);
        double txn_amount = cal_amount(input_utxo);
        if (txn_amount > amount)    {
            UTXO receiver_utxo = new UTXO(0, receiver.getAddress(), amount);
            UTXO change = new UTXO(1, sender.getAddress(), txn_amount - amount);
            ouput_utxo.add(receiver_utxo);
            ouput_utxo.add(change);
        }
        else {
            UTXO receiver_utxo = new UTXO(0, receiver.getAddress(), amount);
        }
        Transaction new_Txn = new Transaction("Normal", input_utxo.size(), input_utxo,
                ouput_utxo.size(), ouput_utxo, false);
        new_Txn.calculateTxID();
        for (UTXO t: new_Txn.getOutput()){
            t.setTxn(new_Txn);
        }
        Signature signature = sender.getSignature();
        byte[] message = new_Txn.getTxnID().getBytes();
        byte[] sig = null;
        assert signature != null;
        try {
            signature.update(message);
            sig = signature.sign();
        }catch (SignatureException e)   {
            System.out.println("Error: " + e.getMessage());
        }
        assert sig != null;
        String signed_Txn_ID = Arrays.toString(sig);
//        new_Txn.setTxnID(new_Txn);
        return new_Txn;
    }
}
