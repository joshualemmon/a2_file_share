/*
package sample;

import java.io.*;
import java.net.*;

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

        try {
            clientSocket = new Socket(localHost, port);

        } catch (IOException e) {
            System.err.println("IOEXception while creating server connection");
        }
    }

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

