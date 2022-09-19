import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class BlockChain {

    public static void main(String[] args) {

        List<Block> blockchain = new ArrayList<>();

        int prefix = 3;
        Instant time;
        String prefixString = new String(new char[prefix]).replace('\0', '0');

        for (int i = 0; i < 4; i++) {


            if (blockchain.size() == 0) {
                System.out.println("New BlockChain");
                time = Instant.now();
                Block obj = new Block(prefixString, "Genesis_Block", time.toString());
                String new_block_hash = obj.mineBlock(prefix);
                blockchain.add(obj);
                System.out.println("New Block Mined");
                System.out.println("Block " + blockchain.size() + " Hash: " + new_block_hash);
            }
            else {
                time = Instant.now();
                int blockchain_size = blockchain.size();
                String prevBlockHash = blockchain.get(blockchain_size - 1).getHash();
                String message = "Block: " + blockchain_size;
                Block obj = new Block(prevBlockHash, message, time.toString());
                String newBlockHash = obj.mineBlock(prefix);
                blockchain.add(obj);
                System.out.println("New Block Mined");
                System.out.println("Block " + blockchain.size() + " Hash: " + newBlockHash);
            }
        }
    }
}
