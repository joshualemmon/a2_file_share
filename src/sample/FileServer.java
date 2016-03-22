package sample;

import java.io.*;
import java.net.*;
import java.util.Vector;

public class FileServer {
    protected Socket clientSocket           = null;
    protected ServerSocket serverSocket     = null;
    protected FileServerThread[] threads    = null;
    protected int numClients                = 0;
    protected Vector messages               = new Vector();

    public static int SERVER_PORT = 16789;
    public static int MAX_CLIENTS = 5;

    public FileServer() {
        try {
            serverSocket = new ServerSocket(SERVER_PORT);
            threads = new FileServerThread[MAX_CLIENTS];
            while(true) {
                clientSocket = serverSocket.accept();
                System.out.println("Client #"+(numClients+1)+" connected.");
                threads[numClients] = new FileServerThread(clientSocket, messages);
                threads[numClients].start();
                numClients++;
            }
        } catch (IOException e) {
            System.err.println("IOEXception while creating server connection");
        }
    }

    public static void main(String[] args) {
        FileServer app = new FileServer();
    }
}
