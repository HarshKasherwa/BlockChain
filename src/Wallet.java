import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.ArrayList;
import java.util.List;

public class Wallet extends Node{

    public static void main(String[] args) {

        List<Node> obj = new ArrayList<>();

        for (int i = 0; i < 10; i++)    {
            Node ob = new Node();
            obj.add(ob);
        }

        for (Node ob: obj)
            System.out.println("Address : " + ob.getAddress());
    }
}
