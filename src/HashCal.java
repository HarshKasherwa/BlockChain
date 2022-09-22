import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import static java.nio.charset.StandardCharsets.UTF_8;

public class HashCal {

    public String calculateHash(String messageForHash)    {

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
}
