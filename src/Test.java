import javax.security.sasl.SaslClient;
import java.security.*;
import java.time.Instant;
import java.util.Arrays;
import java.util.Scanner;

public class Test {

    public static void main(String[] args) throws SignatureException {

        Wallet wallet = new Wallet();
        System.out.println("Public Key: " + wallet.getPublicKey());
        System.out.println("Private Key: " + wallet.getPrivateKey());
        System.out.println("Address: " + wallet.getAddress());
        System.out.println("Signature: " + wallet.getSignature());

        Scanner sc = new Scanner(System.in);
        String msg = sc.nextLine();
        byte[] message = msg.getBytes();
        byte[] sig = null;
        Signature signature = wallet.getSignature();
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
            sign.initVerify(wallet.getPublicKey());
        }catch (InvalidKeyException e)  {
            System.out.println("Invalid Key");
        }
        boolean validSign = sign.verify(sig);
        System.out.println("verify: " + validSign);
        sc.close();
    }
}
