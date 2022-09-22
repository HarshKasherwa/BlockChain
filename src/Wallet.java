import java.security.*;
import java.security.spec.ECGenParameterSpec;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

import static java.nio.charset.StandardCharsets.UTF_8;

public class Wallet {

    public PublicKey publicKey;
    private PrivateKey privateKey;
    public String address;
    private Signature signature;
    private double balance;
    private ArrayList<UTXO> UTXOs;

    public Wallet()   {

        this.balance = 0;
        this.UTXOs = new ArrayList<>();

        try {
            KeyPairGenerator g = KeyPairGenerator.getInstance("EC", "SunEC");
            ECGenParameterSpec ecGenSP = new ECGenParameterSpec("secp224r1");
            try {
                g.initialize(ecGenSP);
            }catch (InvalidAlgorithmParameterException e)   {
                System.out.println("Invalid Algorithm Parameter");
            }

            KeyPair kp = g.genKeyPair();
            this.privateKey = kp.getPrivate();
            this.publicKey = kp.getPublic();

            this.signature = Signature.getInstance("SHA256withECDSA", "SunEC");
            this.signature.initSign(this.privateKey);

        }catch (NoSuchAlgorithmException e) {
            System.out.println("No such Algorithm");
        }catch (NoSuchProviderException | InvalidKeyException e) {
            throw new RuntimeException(e);
        }

        MessageDigest data;
        byte[] bytes_arr = null;

        assert publicKey != null;
        String publicKeyStr = publicKey.toString();
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

    public double getBalance() {

        double bal = 0;
        for (UTXO utxo: UTXOs) {
            bal+=utxo.getAmount();
        }
        this.balance = bal;
        return balance;
    }

    public ArrayList<UTXO> getUTXOs() {
        return UTXOs;
    }

    public PublicKey getPublicKey() {
        return publicKey;
    }

    public PrivateKey getPrivateKey() {
        return privateKey;
    }

    public String getAddress() {
        return address;
    }

    public Signature getSignature() {
        return signature;
    }

    public void addUTXO(UTXO utxo)  {

        this.UTXOs.add(utxo);
    }

    public void removeUTXO(UTXO utxo)   {

        this.UTXOs.remove(utxo);
        if (UTXOs.size() == 0)
            UTXOs = new ArrayList<>();
    }
}
