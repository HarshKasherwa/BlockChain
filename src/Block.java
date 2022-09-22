import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.Instant;
import java.util.ArrayList;

import static java.nio.charset.StandardCharsets.UTF_8;

public class Block {

    private final int index;

    private String hash;
    private final String minedByAddress;
    private final String previousHash;
    private final MerkleTree merkleTree;
    private String timestamp;
    private int nonce;
    public Block(int index, String previousHash, ArrayList<Transaction> txn_list, String address) {

        this.index = index;
        this.previousHash = previousHash;
        this.merkleTree = new MerkleTree(txn_list);
        this.minedByAddress = address;
        this.nonce = 0;
        this.hash = calculateHash();
    }

    public void mineBlock(int prefix) {

        String prefixString = new String(new char[prefix]).replace('\0', '0');
        while (!this.hash.substring(0,prefix).equals(prefixString))  {
            nonce++;
            this.hash = calculateHash();
            Instant time = Instant.now();
            this.timestamp = time.toString();
        }
    }

    private String calculateHash() {

        String messageForHash = previousHash + merkleTree.getMerkle_root().getHash() + nonce;

        MessageDigest data;
        byte[] bytes_arr = null;
        try {
            data = MessageDigest.getInstance("SHA-256");
            bytes_arr = data.digest(messageForHash.getBytes(UTF_8));
        } catch (NoSuchAlgorithmException e)    {
            System.out.println("Sorry");
        }

        StringBuilder new_hash = new StringBuilder();
        assert bytes_arr != null;
        for (byte b : bytes_arr)
            new_hash.append(String.format("%02x", b));

        return new_hash.toString();
    }

    public String getMinedByAddress() {
        return minedByAddress;
    }

    public int getIndex() {
        return index;
    }

    public String getHash() {
        return hash;
    }

    public String getPreviousHash() {
        return previousHash;
    }

    public MerkleTree getMerkleTree() {
        return merkleTree;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public int getNonce() {
        return nonce;
    }

}
