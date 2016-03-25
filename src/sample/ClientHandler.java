package sample;

import java.io.*;
import java.net.*;

public final class ClientHandler implements Runnable {
    protected FileOutputStream fos  = null;
    protected BufferedReader in     = null;
    protected PrintWriter writer    = null;

    protected Socket clientSocket   = null;
    protected String localHost      = "127.0.0.1";
    protected int port              = 8080;

    protected File serverDir        = null;


    public ClientHandler(Socket socket) {
        this.clientSocket = socket;
        String title = null;    //Place holder for the title of the file

        try
        {
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));  //Get the streamed file data

            title = in.readLine();                                                          //Get the title of the file
            serverDir = new File("/home/wuwoot/Documents/b/serverFolder/" + title);         //Create path to new file -- Tony
            //serverDir = new File("/home/wuwoot/Documents/b/serverFolder/" + title);         //Create path to new file -- Josh
            serverDir.createNewFile();                                                      //Create a new file

            fos = new FileOutputStream(serverDir);
            writer = new PrintWriter(fos);                                                  //Write to file
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
