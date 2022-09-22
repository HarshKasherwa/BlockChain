
import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class Client {

    public static void main(String[] args) {

        try(Socket socket = new Socket("localhost", 5000))  {

            BufferedReader echoes = new BufferedReader(
                    new InputStreamReader(socket.getInputStream()));
            PrintWriter stringToEcho = new PrintWriter(socket.getOutputStream(), true);

            Scanner sc = new Scanner(System.in);
            String echoString;
            String response;

            do {
                System.out.println("Enter String to be echoed: ");
                echoString = sc.nextLine();

                stringToEcho.println(echoString);
                if (!echoString.equals("exit")) {
                    response = echoes.readLine();
                    System.out.println("Response: " + response);
                }
            }while (!echoString.equals("exit"));
            sc.close();
        }catch (IOException e)  {
            e.printStackTrace();
        }

    }
}
