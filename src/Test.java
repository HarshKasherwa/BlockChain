import javax.security.sasl.SaslClient;
import java.security.*;
import java.security.spec.ECGenParameterSpec;
import java.time.Instant;
import java.util.Arrays;
import java.util.Scanner;

public class Test {

    public static void main(String[] args) throws SignatureException {


        PublicKey publicKey = null;
        PrivateKey privateKey;
        Signature signature = null;

        try {
            KeyPairGenerator g = KeyPairGenerator.getInstance("EC", "SunEC");
            ECGenParameterSpec ecGenSP = new ECGenParameterSpec("secp224r1");
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

        Scanner sc = new Scanner(System.in);
        String msg = sc.nextLine();
        byte[] message = msg.getBytes();
        byte[] sig = null;
        assert signature != null;
        try {
            signature.update(message);
            sig = signature.sign();
        }catch (SignatureException e)   {
            System.out.println("Error: " + e.getMessage());
        }
        System.out.println("Signed Msg: " + Arrays.toString(sig));
        System.out.println("~~~~~~~~~~~");

        Signature sign = null;
        try{
            sign = Signature.getInstance("SHA256withECDSA", "SunEC");
        }catch (NoSuchAlgorithmException | NoSuchProviderException e)   {
            System.out.println("Error: " + e.getMessage());
        }
        try {
            assert sign != null;
            sign.initVerify(publicKey);
        }catch (InvalidKeyException e)  {
            System.out.println("Invalid Key");
        }
        assert sig != null;
        sign.update(message);
        boolean validSign = sign.verify(message);
        System.out.println("verify: " + validSign);
        sc.close();
    }
}
