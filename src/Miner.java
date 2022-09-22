import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.security.*;
import java.util.ArrayList;
import java.util.Scanner;

public class Miner extends Wallet {

    public Miner() {
//        super();
//        System.out.println("This: " + this.getAddress());
//        System.out.println("Wallet: " + wallet.getAddress());
    }

    public void connectionWithMinerServer(int connecting_port)    {

        try(Socket socket = new Socket("localhost", connecting_port))  {

            PrintWriter output = new PrintWriter(socket.getOutputStream(), true);
            Scanner sc = new Scanner(System.in);

            while (true)    {

                System.out.print("Enter message: ");
                String message = sc.nextLine();
                System.out.println();
                output.println(message);
                if (message.equals("exit")) {
                    socket.close();
                    System.out.println("Connection closed");
                    break;
                }
            }
            sc.close();
        }catch (IOException e)  {
            System.out.println("Connection Error: " + e.getMessage());
        }
    }

    public Transaction generateCoinBase(BlockChain blockChain, Miner miner)   {

        Transaction coinbase = null;
        ArrayList<UTXO> output_list = new ArrayList<>();
        UTXO UTXO = new UTXO(0, miner.getAddress(), blockChain.reward);
        output_list.add(UTXO);
        coinbase = new Transaction("CoinBase", 0,
                1, output_list, false );
        coinbase.calculateTxID();
        UTXO.setTxn(coinbase);
//        System.out.println("Output list: " + output_list);
        miner.addUTXO(output_list.get(0));
        return coinbase;
    }

    public Block generateBlock(Miner miner, String prevBlockHash, BlockChain blockChain)  {

        ArrayList<Transaction> txn_list = new ArrayList<>();
        Transaction coinbase = generateCoinBase(blockChain, miner);
        txn_list.add(coinbase);
        Block new_block = new Block(blockChain.blockChain.size(), prevBlockHash, txn_list, miner.getAddress());
        new_block.mineBlock(blockChain.prefix);
        System.out.println("New Block Mined");
        return new_block;
    }

    public Block generateBlock(Miner miner, String prevBlockHash, BlockChain blockChain, Transaction txn)  {

        ArrayList<Transaction> txn_list = new ArrayList<>();
        Transaction coinbase = generateCoinBase(blockChain, miner);
        txn_list.add(coinbase);
        txn_list.add(txn);
        Block new_block = new Block(blockChain.blockChain.size(), prevBlockHash, txn_list, miner.getAddress());
        new_block.mineBlock(blockChain.prefix);
        System.out.println("New Block Mined");
        return new_block;
    }

    public void confirm_txn(Block block, BlockChain blockChain)   {

        ArrayList<Transaction> txn_list = block.getTxnData();
        for (Transaction txn: txn_list) {
            txn.setConfirmation(true);
            txn.setBlockNumber(blockChain.blockChain.size()-1);
        }
    }

    public boolean verifyTxn(Transaction tx, PublicKey publicKey) throws SignatureException {

        boolean result = false;
        String txnID = tx.getTxnID();
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
        byte[] sig = txnID.getBytes();
        result = sign.verify(sig);
        return result;
    }
}
