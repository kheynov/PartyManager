package server;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketAddress;
import java.util.Scanner;

public class ClientTest {
    private static String hostname = "127.0.0.1";
    private static int port = 14669;
    private static Scanner inStream;
    private static PrintWriter outStream;
    private static Socket clientSocket;

    private static String firstRequest = "{\"action\":\"GET_USER\",\"tagID\":\"1414\"}";
    public static void main(String[] args){
        try {
            clientSocket = new Socket(hostname, port);
            inStream = new Scanner(clientSocket.getInputStream());
            outStream = new PrintWriter(clientSocket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        new Thread(() -> {
            if (inStream.hasNext()){
                System.out.println(inStream.nextLine());
            }
        }).start();
        sendMessage(firstRequest);
    }
    private static void sendMessage(String message) {
        outStream.println(message);
        outStream.flush();
    }
}
