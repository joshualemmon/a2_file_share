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
        this.serverDir = new File("serverFolder"); //Destination server folder
        this.clientSocket = socket;
        String title;
        String message;
        try
        {
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            message = in.readLine();
            title = in.readLine();
            if(message.equals("UPLOAD"))
            {
                serverDir = new File("serverFolder/" + title);         //Create path to new file
                serverDir.createNewFile();                                                      //Create a new file
                fos = new FileOutputStream(serverDir);
                writer = new PrintWriter(fos);                                                  //Write to file
            }
            else if(message.equals("DOWNLOAD"))
            {

                File localDir = new File(Main.folderPath + "/" + title);
                localDir.createNewFile();

                fos = new FileOutputStream(localDir);
                writer = new PrintWriter(fos);
            }
         }catch(IOException ioe)
         {
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
            while ((line = in.readLine()) != null) {
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
