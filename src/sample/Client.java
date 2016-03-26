<<<<<<< HEAD
=======
/*
>>>>>>> rework
package sample;

import java.io.*;
import java.net.*;
<<<<<<< HEAD
import java.util.*;

public class Client {
    private Socket socket = null;
    private BufferedReader in = null;
    private PrintWriter networkOut = null;
    private BufferedReader networkIn = null;

    public  static String SERVER_ADDRESS = "localhost";
    public  static int    SERVER_PORT = 8080;
=======

public class Client {
    protected BufferedInputStream bis   = null;
    protected BufferedOutputStream bos  = null;
    protected FileOutputStream fos      = null;

    //protected DataOutputStream dos  = null;
    //protected BufferedReader in     = null;

    protected Socket clientSocket   = null;
    protected String localHost      = "127.0.0.1";
    protected int port              = 8080;

    protected File uploadFile       = null;
    protected File serverDir        = null;


    public Client(File uploadFile) {
        this.uploadFile = uploadFile;
        this.serverDir = new File("/home/wuwoot/Documents/b/serverFolder/" + uploadFile.getName());
>>>>>>> rework

        try {
<<<<<<< HEAD
            socket = new Socket(SERVER_ADDRESS, SERVER_PORT);
        } catch (UnknownHostException e) {
            System.err.println("Unknown host: "+SERVER_ADDRESS);
=======
            clientSocket = new Socket(localHost, port);

>>>>>>> rework
        } catch (IOException e) {
            System.err.println("IOEXception while connecting to server: "+SERVER_ADDRESS);
        }
        if (socket == null) {
            System.err.println("socket is null");
        }
        try {
            networkOut = new PrintWriter(socket.getOutputStream(), true);
            networkIn = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        } catch (IOException e) {
            System.err.println("IOEXception while opening a read/write connection");
        }
        in = new BufferedReader(new InputStreamReader(System.in));

<<<<<<< HEAD
        try {
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void main(String[] args)
    {
        Client client = new Client();
    }
}
=======
    protected boolean setUpload(File uploadFile) {
        return true;
    }

/*
    protected boolean processCommand(String command, String filename) {
        if (command.equalsIgnoreCase("DIR"))
        {
            return false;
        } else if (command.equalsIgnoreCase("UPLOAD"))
        {
            return false;
        }
        else if (command.equalsIgnoreCase("DOWNLOAD"))
        {
            return false;
        }
        else
        {
            return true;
        }
    }
*/
/*
    public static void main(String[] args) {

    }
}*/

>>>>>>> rework
