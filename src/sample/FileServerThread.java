package sample;

import java.io.*;
import java.net.*;
import java.util.*;

public class FileServerThread extends Thread {
    protected Socket clientSocket       = null;
    protected Thread t                  = null;

    public FileServerThread(Socket socket)
    {
        super();
        this.clientSocket = socket;                  //Configure socket
        this.t = new Thread(new ClientHandler());    //Creates client thread
    }
}