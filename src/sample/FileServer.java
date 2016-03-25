package sample;

import com.sun.jndi.ldap.Connection;

import java.io.*;
import java.net.*;
import java.util.Vector;

public class FileServer {
    protected Socket clientSocket           = null; //The socket for one client
    protected ServerSocket serverSocket     = null; //The Server current socket that will be actively listening to requests
    protected FileServerThread fsT          = null; //Needs to handle multiple client threads, therefore there must be multiple
                                                    //file server threads
    //protected Thread t                      = null;
    protected int numClients                = 0;    //Total number of clientsf
    protected File uploadFile = null;

    public static int SERVER_PORT = 8080;

    public FileServer()
    {
        try
        {
            serverSocket = new ServerSocket(SERVER_PORT);
            while(true)
            {
                System.out.println("Waiting...");
                try
                {
                    clientSocket = serverSocket.accept();
                    System.out.println("Client #" + (numClients + 1) + " connected.");
                    fsT = new FileServerThread(clientSocket);
                    fsT.t.start();


                } catch (IOException ioe) {
                    ioe.printStackTrace();
                }


                //serverSocket.close();

            }

        } catch (IOException e) {
            System.err.println("IOEXception while creating server connection");
        }

    }

    public static void main(String[] args)
    {
        FileServer server = new FileServer();
    }
}
