package sample;

import java.io.*;
import java.net.*;

public final class ClientHandler implements Runnable {
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


    public ClientHandler() {
        this.uploadFile = uploadFile;
        this.serverDir = new File("/home/wuwoot/Documents/b/serverFolder/" + uploadFile.getName());

        try {
            clientSocket = new Socket(localHost, port);

        } catch (IOException e) {
            System.err.println("IOEXception while creating server connection");
        }
    }



    @Override
    public void run()
    {
        try {
            clientSocket = new Socket(localHost,port);

            boolean endOfSession = false;
            while (!endOfSession) {
                endOfSession = processUpload();
            }
            try {
                clientSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    protected boolean processUpload() {

        try
        {
            byte[] fileLengthInBytes = new byte[(int) uploadFile.length()];

            bis = new BufferedInputStream(clientSocket.getInputStream());
            fos = new FileOutputStream(serverDir);
            bos = new BufferedOutputStream(fos);


            bis.read(fileLengthInBytes, 0, fileLengthInBytes.length);
            bos.write(fileLengthInBytes, 0, fileLengthInBytes.length);
            bos.flush();

            bis.close();
            bos.close();

        } catch(IOException ioe) {
            ioe.printStackTrace();
        }
        return true;
    }

    protected boolean processStart(File uploadFile) {
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

    public static void main(String[] args) {

    }
}
