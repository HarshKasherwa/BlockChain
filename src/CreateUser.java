import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.spec.ECGenParameterSpec;
import java.util.Arrays;

public class CreateUser {

//    public CreateUser() {
//
//        try {
//            KeyPairGenerator g = KeyPairGenerator.getInstance("EC", "SunEC");
//            ECGenParameterSpec ecsp = new ECGenParameterSpec("spec224r1");
//            g.initialize(ecsp);
//
//            KeyPair kp = g.genKeyPair();
//            PrivateKey private_key = kp.getPrivate();
//            PublicKey public_key = kp.getPublic();
//
//            Signature s = Signature.getInstance("SHA256withECDSA", "SunEC");
//            s.initSign(private_key);
//
//        } catch (NoSuchAlgorithmException e)    {
//            System.out.println("Sorry, No such Algorithm found.");
//        } catch (NoSuchProviderException | InvalidAlgorithmParameterException | InvalidKeyException e) {
//            throw new RuntimeException(e);
//        }
//    }

    public static void main(String[] args) throws SignatureException, NoSuchAlgorithmException, NoSuchProviderException {

        byte[] msg = "Hello World!".getBytes(StandardCharsets.UTF_8);
        byte[] sig;
        Signature s = null;
        PrivateKey private_key = null;
        PublicKey public_key = null;

        try {
            KeyPairGenerator g = KeyPairGenerator.getInstance("EC", "SunEC");
            ECGenParameterSpec ecsp = new ECGenParameterSpec("secp224r1");
            g.initialize(ecsp);

            KeyPair kp = g.genKeyPair();
            private_key = kp.getPrivate();
            public_key = kp.getPublic();

            s = Signature.getInstance("SHA256withECDSA", "SunEC");
            s.initSign(private_key);

        } catch (NoSuchAlgorithmException e)    {
            System.out.println("Sorry, No such Algorithm found.");
        } catch (NoSuchProviderException | InvalidAlgorithmParameterException | InvalidKeyException e) {
            throw new RuntimeException(e);
        }

        assert s != null;
        System.out.println("Sign: " + s);
        s.update(msg);
        sig = s.sign();
        System.out.println("Pri: " + private_key.toString());
        System.out.println("Pub: " + public_key.toString());
        System.out.println(Arrays.toString(sig));

        Signature sg = Signature.getInstance("SHA256withECDSA", "SunEC");
        try {
            sg.initVerify(public_key);
        }catch (InvalidKeyException e)  {
            System.out.println("Invalid Key");
        }
        sg.update(msg);
        boolean validSign = sg.verify(sig);
        System.out.println("verify: " + validSign);

    }
}
