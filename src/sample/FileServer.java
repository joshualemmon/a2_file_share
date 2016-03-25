package sample;

import java.io.*;
import java.net.*;
import java.util.Vector;

public class FileServer {
    protected Socket clientSocket           = null;
    protected ServerSocket serverSocket     = null;
    protected FileServerThread threads      = null;
    protected int numClients                = 0;
    protected File[] filenames              = null;
    private static String serverFolder = ".serverfiles";

    public static int SERVER_PORT = 8080;

    public FileServer(File[] filenames) {
        try {
            serverSocket = new ServerSocket(SERVER_PORT);
            while(true) {
                clientSocket = serverSocket.accept();
                System.out.println("Client #"+(numClients+1)+" connected.");
                threads = new FileServerThread(clientSocket, filenames);
                threads.start();

                serverSocket.close();
            }


        } catch (IOException e) {
            System.err.println("IOEXception while creating server connection");
        }

    }
    public static File[] getServerFiles()
    {
        File localDir = new File(serverFolder);
        File[] files =  localDir.listFiles();
        return files;
    }

    public static void main(String[] args) {
        File[] test = getServerFiles();
        FileServer app = new FileServer(test);
    }
}
