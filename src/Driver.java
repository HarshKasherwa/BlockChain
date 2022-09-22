import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Driver {

    public static void main(String[] args) {

        int prefix = 3;
        BlockChain block_chain = new BlockChain(prefix);
        List<Miner> miner_list = new ArrayList<>();
//        int miner_port = 5000;

        Scanner sc = new Scanner(System.in);
        while (true)    {
            System.out.println("Enter 1 to mine block.");
            System.out.println("Enter 2 to print blockchain data.");
            System.out.println("Enter 3 to print miner's wallet information");
//            System.out.println("Enter 4 to print last transaction.");
            System.out.println("Enter 0 to exit.");
            short input = sc.nextShort();
            if (input == 0)
                break;
            else if (input == 1)    {

                System.out.println("Enter no. of blocks to mine.");
                short x = sc.nextShort();
                for (short i = 0; i < x; i++) {

                    Miner miner = new Miner();
                    miner_list.add(miner);
                    int height = block_chain.blockChain.size();
                    String prevBlockHash;
                    if (height == 0)  {
                        prevBlockHash = new String(new char[prefix]).replace('\0', '0');
                    }
                    else {
                        prevBlockHash = block_chain.blockChain.get(height - 1).getHash();
                    }
                    Block newBlock = miner.generateBlock(prevBlockHash, block_chain);
                    block_chain.blockChain.add(newBlock);
//                    System.out.println("NB: " + newBlock);
//                    System.out.println(newBlock.getPreviousHash());
//                    System.out.println(newBlock.getHash());
//                    System.out.println(newBlock.getTimestamp());
//                    System.out.println("With BC obj: " + block_chain.blockChain.get(0));
                }
                System.out.println();
            }
            if (input == 2) {

                System.out.println("~".repeat(30));
                for (Block obj : block_chain.blockChain)   {
                    System.out.println("Block Index: " + obj.getIndex());
                    System.out.println("Previous Block Hash: " +obj.getPreviousHash());
                    System.out.println("Block Hash: " + obj.getHash());
                    System.out.println("Merkle Root Hash: " + obj.getMerkleTree().getMerkle_root().getHash());
                    System.out.println("Timestamp: " + obj.getTimestamp());
                    System.out.println("Nonce: " + obj.getNonce());
                    System.out.println("Mined by: " + obj.getMinedByAddress());
                    System.out.println();
                }
            }

            if (input == 3) {

                System.out.println("~".repeat(30));
                System.out.println("-".repeat(10) + " All Miners Wallet Information " + "-".repeat(10));
                for (Miner miner: miner_list)  {
                    System.out.println("Miner Address: " + miner.getAddress());
                    System.out.println("Miner Public_Key: " + miner.getPublicKey());
                    System.out.println("Miner Wallet Balance: " + miner.getBalance());
                    System.out.println(".".repeat(7) + "UTXOs List" + ".".repeat(7));
                    for (TxnOutput utxo: miner.getUTXOs())   {
                        System.out.println(utxo.getIndex() + " ".repeat(5) + utxo.getAddress() +
                                " ".repeat(5) + "Amount: " + utxo.getAmount());
                    }
                    System.out.println();
                }
            }
        }
        sc.close();
    }
}