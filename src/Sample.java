import java.security.*;
import java.security.spec.ECGenParameterSpec;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Sample {

    public static PrivateKey privateKey;
    public static PublicKey publicKey;
    public static Signature signature;
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        try {
            KeyPairGenerator g = KeyPairGenerator.getInstance("EC", "SunEC");
            ECGenParameterSpec ecGenSP = new ECGenParameterSpec("secp256r1");
            try {
                g.initialize(ecGenSP);
            }catch (InvalidAlgorithmParameterException e)   {
                System.out.println("Invalid Algorithm Parameter");
            }

            KeyPair kp = g.genKeyPair();
            privateKey = kp.getPrivate();
            publicKey = kp.getPublic();

            signature = Signature.getInstance("SHA256withECDSA", "SunEC");
            signature.initSign(privateKey);

        }catch (NoSuchAlgorithmException e) {
            System.out.println("No such Algorithm");
        }catch (NoSuchProviderException | InvalidKeyException e) {
            throw new RuntimeException(e);
        }

        System.out.println("Pr. Key: " + privateKey);
        System.out.println("Pu. Key: " + publicKey);
        System.out.println("Sign: " + signature);
        sc.close();
    }
}
