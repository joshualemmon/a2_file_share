/*package sample;

import java.io.*;
import java.net.*;

public class Client {
    protected BufferedInputStream bis   = null;
    protected BufferedOutputStream bos  = null;

    protected Socket clientSocket   = null;
    protected String localHost      = "127.0.0.1";
    protected int port              = 8080;

    protected File uploadFile       = null;

    public Client(String localHost, int port) {
        this.localHost = localHost;
        this.port = port;

        try {
            clientSocket = new Socket(localHost, port);
        } catch (IOException e) {
            System.err.println("IOEXception while creating server connection");
        }
    }

    public void uploading(File uploadFile)
    {
        this.uploadFile = uploadFile;

        try
        {
            long lengthOfFile = uploadFile.length();    //Counts the length of the file
            byte[] bytesInFile = new byte[(int)(lengthOfFile)]; //Stores the bytes in the file, I don't know how though

            bis = new BufferedInputStream(new FileInputStream(uploadFile)); //Stream for file
            bis.read(bytesInFile, 0, bytesInFile.length);                   //Read in the file to stream
            bos = new BufferedOutputStream(clientSocket.getOutputStream()); //Stream to socket
            bos.write(bytesInFile, 0, bytesInFile.length);                  //Write file data to socket
            bos.flush(); //Finalize

            bis.close();
            bos.close();

        } catch (IOException ioe)
        {
            ioe.printStackTrace();
        }
    }
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


