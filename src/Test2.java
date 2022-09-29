import java.security.*;
import java.util.Arrays;
import java.util.Scanner;

import static java.nio.charset.StandardCharsets.UTF_8;

public class Test2 {

    public static void main(String[] args) throws SignatureException {

        Miner miner = new Miner();
        int height = 0;
        int prefix = 3;
        BlockChain bc = new BlockChain(prefix);
        String prevBlockHash = new String(new char[prefix]).replace('\0', '0');
        Block new_block = miner.generateBlock(miner, prevBlockHash,bc);
        bc.blockChain.add(new_block);
        miner.confirm_txn(new_block, bc);
        System.out.println("TxnID: " + new_block.getTxnData().get(0).getTxnID());
        Transaction txn = new_block.getTxnData().get(0);
        Signature signature = miner.getSignature();
        System.out.println("Signature before: " + signature);
        byte[] message = txn.getTxnID().getBytes();
        byte[] sig = null;
        assert signature != null;
        try {
            signature.update(message);
            sig = signature.sign();
        }catch (SignatureException e)   {
            System.out.println("Error: " + e.getMessage());
        }
        System.out.println("Signature After: " + sig);

        Signature sign = null;
        try{
            sign = Signature.getInstance("SHA256withECDSA", "SunEC");
        }catch (NoSuchAlgorithmException | NoSuchProviderException e)   {
            System.out.println("Error: " + e.getMessage());
        }
        try {
            assert sign != null;
            sign.initVerify(miner.getPublicKey());
        }catch (InvalidKeyException e)  {
            System.out.println("Invalid Key");
        }
        assert sig != null;
        sign.update(message);
        boolean validSign = sign.verify(sig);
        System.out.println("verify: " + validSign);
    }
}
