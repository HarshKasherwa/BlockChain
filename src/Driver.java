import java.security.SignatureException;
import java.util.*;

public class Driver {

    public static void main(String[] args) throws SignatureException {

        int prefix = 3;
        BlockChain block_chain = new BlockChain(prefix);
        List<Miner> miner_list = new ArrayList<>();
//        int miner_port = 5000;

        Scanner sc = new Scanner(System.in);
        while (true)    {
            System.out.println("Enter 1 to mine block.");
            System.out.println("Enter 2 to print blockchain data.");
            System.out.println("Enter 3 to print miner's wallet information");
            System.out.println("Enter 4 to initiate a transaction.");
            System.out.println("Enter 0 to exit.");
            short input = sc.nextShort();
            if (input == 0)
                break;
            else if (input == 1)    {

                System.out.println("Enter no. of blocks to mine.");
                short x = sc.nextShort();
                boolean val = false;
                int miner_number = 0;
                if (x == 1) {
                    System.out.println("Enter the miner number for which you want mine block");
                    miner_number = sc.nextInt();
                    val = true;
                }
                for (short i = 0; i < x; i++) {

                    Miner miner;
                    if (x == 1) {
                        miner = miner_list.get(miner_number-1);
                    }
                    else    {

                        miner = new Miner();
                        miner_list.add(miner);
                    }
//                    System.out.println("Miner Address: " + miner.getAddress());
                    int height = block_chain.blockChain.size();
                    String prevBlockHash;
                    if (height == 0)  {
                        prevBlockHash = new String(new char[prefix]).replace('\0', '0');
                    }
                    else {
                        prevBlockHash = block_chain.blockChain.get(height - 1).getHash();
                    }
                    Block newBlock = miner.generateBlock(miner, prevBlockHash, block_chain);
                    block_chain.blockChain.add(newBlock);
                    miner.confirm_txn(newBlock, block_chain);
//                    System.out.println("NB: " + newBlock);
//                    System.out.println(newBlock.getPreviousHash());
//                    System.out.println(newBlock.getHash());
//                    System.out.println(newBlock.getTimestamp());
//                    System.out.println("With BC obj: " + block_chain.blockChain.get(0));
                }
                System.out.println();
            }
            if (input == 2) {

                System.out.println("~".repeat(100));
                for (Block obj : block_chain.blockChain)   {
                    System.out.println("Block Index: " + obj.getIndex());
                    System.out.println("Previous Block Hash: " +obj.getPreviousHash());
                    System.out.println("Block Hash: " + obj.getHash());
//                    System.out.println("Merkle Root Hash: " + obj.getMerkleTree().getMerkle_root().getHash());
//                    System.out.println("Timestamp: " + obj.getTimestamp());
                    System.out.println("Nonce: " + obj.getNonce());
                    System.out.println("Mined by: " + obj.getMinedByAddress());
                    System.out.println();
                }
            }

            if (input == 3) {

                System.out.println("~".repeat(100));
                System.out.println("-".repeat(10) + " All Miners Wallet Information " + "-".repeat(10));
                for (Miner miner: miner_list)  {
                    System.out.println("Miner Address: " + miner.getAddress());
//                    System.out.println("Miner Public_Key: " + miner.getPublicKey());
                    System.out.println("Miner Wallet Balance: " + miner.getBalance());
                    System.out.println(".".repeat(7) + "UTXOs List" + ".".repeat(7));
                    for (UTXO utxo: miner.getUTXOs())   {
                        System.out.println("Txn: " + utxo.getTxn().getTxnID() +" ".repeat(10) +
                                "Confirmed: " + utxo.getTxn().isConfirmation());
                        System.out.println(utxo.getIndex() + " ".repeat(5) + utxo.getAddress() +
                                " ".repeat(5) + "Amount: " + utxo.getAmount());
                    }
                    System.out.println();
                }
            }

            if (input == 4) {
                Set<Integer> choose_miner = new HashSet<>();
                for (int i = 0; i < miner_list.size(); i++) {
                    choose_miner.add(i+1);
                }
                System.out.println("Total miner available: " + miner_list.size());
                System.out.print("Enter the sender miner number: ");
                int sender_n = sc.nextInt();
                choose_miner.remove(sender_n);
                System.out.print("\nEnter the receiver miner number: ");
                int receiver_n = sc.nextInt();
                choose_miner.remove(receiver_n);

                Random rnd = new Random();
                int rnd_num = rnd.nextInt(choose_miner.size());
                Object[] arr = choose_miner.toArray();
                int s_miner = (int) arr[rnd_num];

                System.out.print("\nEnter the amount to send: ");
                double amount = sc.nextDouble();
                Miner sender = miner_list.get(sender_n-1);
                Miner receiver = miner_list.get(receiver_n-1);

                User obj = new User();
                Transaction new_txn = obj.initiateTxn(sender, receiver, amount);
                Miner selected_miner = miner_list.get(s_miner-1);
//                boolean verification = selected_miner.verifyTxn(new_txn, sender.publicKey);
//                System.out.println("Transaction Verification: " + verification);
                int height = block_chain.blockChain.size();
                String prevBlockHash = block_chain.blockChain.get(height-1).getHash();
                Block new_block =  selected_miner.generateBlock(selected_miner, prevBlockHash, block_chain, new_txn);
                block_chain.blockChain.add(new_block);
                selected_miner.confirm_txn(new_block, block_chain);
                for (UTXO t: new_txn.getInput())    {
                    sender.removeUTXO(t);
                }
                for (UTXO t: new_txn.getOutput())   {
                    String address = t.getAddress();
                    Miner m_obj = null;
                    for (Miner m: miner_list)   {
                        if (m.getAddress().equals(address)) {
                            m_obj = m;
                            break;
                        }
                    }
                    assert m_obj != null;
                    m_obj.addUTXO(t);
//                    System.out.println("UTXO added");
                }
            }
            if (input == 5) {
                for (Miner m: miner_list)   {
                    System.out.println("M.get: " + m.getAddress());
                    System.out.println("M.add: " + m.address);
                }
            }
        }
        sc.close();
    }
}