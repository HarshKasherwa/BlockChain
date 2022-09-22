import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.security.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class Miner extends Wallet {

    Wallet wallet = new Wallet();

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

    public Transaction generateCoinBase(BlockChain blockChain)   {

        ArrayList<TxnOutput> output_list = new ArrayList<>();
        TxnOutput txnOutput = new TxnOutput(0, wallet.getAddress(), blockChain.reward);
        output_list.add(txnOutput);
        Transaction coinbase = new Transaction("CoinBase", blockChain.blockChain.size(),
                0, 1, output_list, false );
        coinbase.calculateTxID();
        return coinbase;
    }

    public void generateBlock(String prevBlockHash, BlockChain blockChain)  {

        ArrayList<Transaction> txn_list = new ArrayList<>();
        Transaction coinbase = generateCoinBase(blockChain);
        txn_list.add(coinbase);
        Block new_block = new Block(blockChain.blockChain.size(), prevBlockHash, txn_list, address);
        new_block.mineBlock(blockChain.prefix);
        System.out.println("New Block Mined");
    }
}
