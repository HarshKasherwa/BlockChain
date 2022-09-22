import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class UserServer {

    public void sendMessageToMiner(String message)  {

        try (Socket socket = new Socket("localhost", 5000))  {

            PrintWriter output = new PrintWriter(socket.getOutputStream(), true);
            output.println(message);
        }catch(IOException e)   {
            System.out.println("Connection Error: " + e.getMessage());
        }
    }

    public void acceptConnection(int listening_port)   {
        try (ServerSocket serverSocket = new ServerSocket(listening_port))    {

            Socket socket = serverSocket.accept();
            System.out.println("New user connected: " + socket);

            BufferedReader input = new BufferedReader(
                    new InputStreamReader(socket.getInputStream()));
//            PrintWriter output = new PrintWriter(socket.getOutputStream(), true);

            while (true)    {

                String messageFromUser = input.readLine();
                System.out.println("Message Received from User: " + messageFromUser);
                if (messageFromUser.equals("exit")) {
                    socket.close();
                    System.out.println("Connection with user closed");
                    sendMessageToMiner(messageFromUser);
                    break;
                }
            }

        }catch (IOException e)  {
            System.out.println("ServerException: " + e.getMessage());
        }
    }

    public void start_server() {

        int listening_port = 6000;
        UserServer obj = new UserServer();
        Thread listen = new Thread(new Runnable() {
            @Override
            public void run() {
                obj.acceptConnection(listening_port);
            }
        });

        listen.start();

    }
}
