import org.w3c.dom.ls.LSException;

import java.time.Instant;
import java.util.*;

public class BlockChain {

    public List<Block> blockChain;
    public double reward;
    public Set<Transaction> txn_ledger;
    public int prefix;

    public BlockChain(int prefix) {
        this.blockChain = new List<Block>() {
            @Override
            public int size() {
                return 0;
            }

            @Override
            public boolean isEmpty() {
                return false;
            }

            @Override
            public boolean contains(Object o) {
                return false;
            }

            @Override
            public Iterator<Block> iterator() {
                return null;
            }

            @Override
            public Object[] toArray() {
                return new Object[0];
            }

            @Override
            public <T> T[] toArray(T[] a) {
                return null;
            }

            @Override
            public boolean add(Block block) {
                return false;
            }

            @Override
            public boolean remove(Object o) {
                return false;
            }

            @Override
            public boolean containsAll(Collection<?> c) {
                return false;
            }

            @Override
            public boolean addAll(Collection<? extends Block> c) {
                return false;
            }

            @Override
            public boolean addAll(int index, Collection<? extends Block> c) {
                return false;
            }

            @Override
            public boolean removeAll(Collection<?> c) {
                return false;
            }

            @Override
            public boolean retainAll(Collection<?> c) {
                return false;
            }

            @Override
            public void clear() {

            }

            @Override
            public Block get(int index) {
                return null;
            }

            @Override
            public Block set(int index, Block element) {
                return null;
            }

            @Override
            public void add(int index, Block element) {

            }

            @Override
            public Block remove(int index) {
                return null;
            }

            @Override
            public int indexOf(Object o) {
                return 0;
            }

            @Override
            public int lastIndexOf(Object o) {
                return 0;
            }

            @Override
            public ListIterator<Block> listIterator() {
                return null;
            }

            @Override
            public ListIterator<Block> listIterator(int index) {
                return null;
            }

            @Override
            public List<Block> subList(int fromIndex, int toIndex) {
                return null;
            }
        };
        this.prefix = prefix;
        this.reward = 100;
    }


    //    public static void main(String[] args) {
//
//        List<Block> blockchain = new ArrayList<>();
//
//        int prefix = 3;
//        Instant time;
//        String prefixString = new String(new char[prefix]).replace('\0', '0');
//
//        for (int i = 0; i < 4; i++) {
//
//
//            if (blockchain.size() == 0) {
//                System.out.println("New BlockChain");
//                time = Instant.now();
//                Block obj = new Block(prefixString, "Genesis_Block", time.toString());
//                String new_block_hash = obj.mineBlock(prefix);
//                blockchain.add(obj);
//                System.out.println("New Block Mined");
//                System.out.println("Block " + blockchain.size() + " Hash: " + new_block_hash);
//            }
//            else {
//                time = Instant.now();
//                int blockchain_size = blockchain.size();
//                String prevBlockHash = blockchain.get(blockchain_size - 1).getHash();
//                String message = "Block: " + blockchain_size;
//                Block obj = new Block(prevBlockHash, message, time.toString());
//                String newBlockHash = obj.mineBlock(prefix);
//                blockchain.add(obj);
//                System.out.println("New Block Mined");
//                System.out.println("Block " + blockchain.size() + " Hash: " + newBlockHash);
//            }
//        }
//    }
}
