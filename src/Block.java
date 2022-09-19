import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import static java.nio.charset.StandardCharsets.UTF_8;

public class Block {

    private String hash;
    private final String previousHash;
    private final String transaction_data;
    private final String timestamp;
    private int nonce;

    public Block(String previousHash, String transaction_data, String timestamp) {

        this.previousHash = previousHash;
        this.transaction_data = transaction_data;
        this.timestamp = timestamp;
        this.nonce = 0;
        this.hash = calculateHash();
    }

    public String mineBlock(int prefix) {

        String prefixString = new String(new char[prefix]).replace('\0', '0');
        while (!hash.substring(0,prefix).equals(prefixString))  {
            nonce++;
            hash = calculateHash();
        }

        return hash;
    }

    private String calculateHash() {

        String messageForHash = previousHash + timestamp + nonce + transaction_data;

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

    public String getHash() {
        return hash;
    }

    public String getPreviousHash() {
        return previousHash;
    }

    public String getTransaction_data() {
        return transaction_data;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public int getNonce() {
        return nonce;
    }

}
