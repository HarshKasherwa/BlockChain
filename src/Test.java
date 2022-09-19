import javax.security.sasl.SaslClient;
import java.time.Instant;
import java.util.Scanner;

public class Test {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        String message = sc.next();
        System.out.println("Message: " + message);
        Instant time;
        int prefix  = 3;
        String prefixString = new String(new char[prefix]).replace('\0', '0');
        System.out.println("PrefixString: " + prefixString);
        time = Instant.now();
        System.out.println("Time: " + time);
        Block obj = new Block(prefixString, message, time.toString());
        String newHash = obj.mineBlock(prefix);
        System.out.println(newHash);
        sc.close();
    }
}
