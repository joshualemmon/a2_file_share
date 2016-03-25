package sample;

import java.io.*;
import java.net.*;

public final class ClientHandler implements Runnable {
    protected FileOutputStream fos  = null;
    protected BufferedReader in     = null;
    protected PrintWriter writer    = null;

    protected Socket clientSocket   = null;
    protected String localHost      = "127.0.0.8";
    protected int port              = 8080;

    protected File serverDir        = null;


    public ClientHandler(Socket socket) {
        this.serverDir = new File("serverFolder/plain.txt"); //Destination server folder
        this.clientSocket = socket;

        try {
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream())); //Get the streamed data
            fos = new FileOutputStream(serverDir);
            writer = new PrintWriter(fos); //setup writing file to directory functionality
        } catch (IOException ioe) {
            ioe.printStackTrace();
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
        String line = null;

        try
        {
            /*
            byte[] fileLengthInBytes = new byte[(int) uploadFile.length()];

            bis.read(fileLengthInBytes, 0, fileLengthInBytes.length);
            bos.write(fileLengthInBytes, 0, fileLengthInBytes.length);
            bos.flush();

            bis.close();
            bos.close();
            */

            while ((line = in.readLine()) != null) {
                //System.out.println();
                writer.println(line);
            }

            in.close();
            writer.close();

        } catch(IOException ioe) {
            ioe.printStackTrace();
        }
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
