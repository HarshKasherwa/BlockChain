import java.security.*;
import java.security.spec.ECGenParameterSpec;
import java.security.spec.ECParameterSpec;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import static java.nio.charset.StandardCharsets.UTF_8;

public class Node {

    public PublicKey public_key;
    private PrivateKey private_key;
    public String address;
    private Signature sign;

    public Node()   {

        try {
            KeyPairGenerator g = KeyPairGenerator.getInstance("EC", "SunEC");
            ECGenParameterSpec ecsp = new ECGenParameterSpec("secp224r1");
            try {
                g.initialize(ecsp);
            }catch (InvalidAlgorithmParameterException e)   {
                System.out.println("Invalid Algorithm Parameter");
            }

            KeyPair kp = g.genKeyPair();
            this.private_key = kp.getPrivate();
            this. public_key = kp.getPublic();

            sign = Signature.getInstance("SHA256withECDSA", "SunEC");
            sign.initSign(this.private_key);

        }catch (NoSuchAlgorithmException e) {
            System.out.println("No such Algorithm");
        }catch (NoSuchProviderException | InvalidKeyException e) {
            throw new RuntimeException(e);
        }

        MessageDigest data;
        byte[] bytes_arr = null;

        assert public_key != null;
        String publicKeyStr = public_key.toString();
        try {
                data = MessageDigest.getInstance("SHA-256");
                bytes_arr = data.digest(publicKeyStr.getBytes(UTF_8));
        }catch (NoSuchAlgorithmException e) {
            System.out.println("No Such Algorithm");
        }

        StringBuilder address_hash = new StringBuilder();
        assert bytes_arr != null;
        for (byte b : bytes_arr)
            address_hash.append(String.format("%02x", b));

        this.address = address_hash.toString();
    }

    public PublicKey getPublic_key() {
        return public_key;
    }

    public PrivateKey getPrivate_key() {
        return private_key;
    }

    public String getAddress() {
        return address;
    }

    public Signature getSign() {
        return sign;
    }
}
