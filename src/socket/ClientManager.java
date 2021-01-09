package socket;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ClientManager implements Runnable {
    private static final int port = 14669;
    private ServerSocket serverSocket;
    private Socket clientSocket;

    @Override
    public void run() {
        try {
            serverSocket = new ServerSocket(port);
            System.out.println("Server started");
            while (true) {
                System.out.println("Waiting for client");
                clientSocket = serverSocket.accept();
                System.out.println("Client connected");
                ClientHandler clientHandler = new ClientHandler(clientSocket);
                new Thread(clientHandler).start();
//                ParkHandler parkHandler = new ParkHandler(clientSocket);
//                new Thread(parkHandler).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (clientSocket != null) {
                    clientSocket.close();
                }
                if (serverSocket != null) {
                    serverSocket.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
