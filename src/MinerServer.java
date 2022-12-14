import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class MinerServer {

    public void acceptConnection(int listening_port) {

        try(ServerSocket serverSocket = new ServerSocket(listening_port)) {

            while (true)    {

                Socket socket = serverSocket.accept();
                System.out.println("New miner connected: " + socket);
                BufferedReader input = new BufferedReader(
                        new InputStreamReader(socket.getInputStream()));
//                PrintWriter output = new PrintWriter(socket.getOutputStream(), true);

                String messageFromUserServer = input.readLine();
                if (messageFromUserServer.equals("exit"))   {
                    System.out.println("Connection Closed");
                    socket.close();
                    break;
                }
                System.out.println("Message from UserServer: " + messageFromUserServer);
            }
        }catch (IOException e)  {
            System.out.println("Server Exception: " + e.getMessage());
        }
    }
}
