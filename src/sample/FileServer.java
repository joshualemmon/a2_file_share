package sample;

import com.sun.jndi.ldap.Connection;

import java.io.*;
import java.net.*;
import java.util.Vector;

public class FileServer
{
    protected Socket clientSocket           = null; //The socket for one client
    protected ServerSocket serverSocket     = null; //The Server current socket that will be actively listening to requests
                                                    //Needs to handle multiple client threads, therefore there must be multiple
                                                    //file server threads
    protected int numClients                = 0;    //Total number of clientsf
    public static int SERVER_PORT = 8080;

    public FileServer()
    {
        try
        {
            serverSocket = new ServerSocket(SERVER_PORT);
            while(true) //Server is waiting for connections
            {
                System.out.println("Waiting...");
                try
                {
                    clientSocket = serverSocket.accept();
                    System.out.println("Client #" + (numClients + 1) + " connected.");
                    Thread handlerThread = new Thread(new ClientHandler(clientSocket));
                    handlerThread.start();
                } catch (IOException ioe)
                {
                    ioe.printStackTrace();
                }
            }

        } catch (IOException e) {
            System.err.println("IOEXception while creating server connection");
        }

    }

    public static String[] getServerFiles()
    {
        File servDir = new File("serverFolder");
        File[] serverFiles = servDir.listFiles();

        String[] s = new String[serverFiles.length];
        int i = 0;
        for(File f : serverFiles)
        {
            s[i++] = f.getName();
        }
        return s;
    }

    public static void main(String[] args)
    {
        FileServer server = new FileServer();
    }
}
