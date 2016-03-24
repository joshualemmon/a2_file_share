/*package sample;

import java.io.*;
import java.net.*;
import java.util.Vector;

public class Client {
    protected Socket clientSocket           = null;
    protected ServerSocket serverSocket     = null;
    protected FileServerThread threads      = null;
    protected int numClients                = 0;
    protected File[] filenames              = null;

    public static int SERVER_PORT = 8080;

    public Client() {
        try {
            serverSocket = new ServerSocket(SERVER_PORT);
            while(true) {
                clientSocket = serverSocket.accept();
                System.out.println("Client #"+(numClients+1)+" connected.");
                threads = new FileServerThread(clientSocket, filenames);
                threads.start();
            }
        } catch (IOException e) {
            System.err.println("IOEXception while creating server connection");
        }
    }

    public static void main(String[] args) {
        FileServer app = new FileServer();
    }
}*/
