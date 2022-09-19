import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Sample {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        int prefix = 3;
        List<Block> chain = new ArrayList<>();
        String prefixString = new String(new char[prefix]).replace('\0', '0');
        Instant time;
        String message = sc.next();
        time = Instant.now();
        Block obj = new Block(prefixString, message, time.toString());
        System.out.println("Prev_Hash: " + obj.getPreviousHash());
        System.out.println("Data: " + obj.getTransaction_data());
        System.out.println("Timestamp: " + obj.getTimestamp());

        sc.close();
    }
}
