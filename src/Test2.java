import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Scanner;

import static java.nio.charset.StandardCharsets.UTF_8;

public class Test2 {

    public static String cal_hash(String message)  {

        MessageDigest data;
        byte[] bytes_arr = null;
        try {
            data = MessageDigest.getInstance("SHA-256");
            bytes_arr = data.digest(message.getBytes(UTF_8));
        } catch (NoSuchAlgorithmException e)    {
            System.out.println("Sorry");
        }

        StringBuilder new_hash = new StringBuilder();
        assert bytes_arr != null;
        for (byte b : bytes_arr)
            new_hash.append(String.format("%02x", b));

        return new_hash.toString();
    }

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        String s = sc.next();
        int prefix = 3;
        int nonce = 0;
        String prefixString = new String(new char[prefix]).replace('\0', '0');
        String new_hash;
        while (true)    {
            String hash = cal_hash(s+nonce);
            System.out.println("Nonce: " + nonce + " Hash: " + hash);
            if (hash.substring(0,prefix).equals(prefixString))  {
                new_hash = hash;
                break;
            }
            else    {
                nonce++;
            }

        }
        System.out.println("New Hash: " + new_hash);

        sc.close();
    }
}
