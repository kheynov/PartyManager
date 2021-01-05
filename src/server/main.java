package server;

import socket.ClientManager;

public class main {
    public static void main(String[] args) {
        ClientManager clientManager = new ClientManager();
        new Thread(clientManager).start();
    }
}
