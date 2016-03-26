package sample;

import java.io.*;
import java.net.*;

public final class ClientHandler implements Runnable {
    protected FileOutputStream fos  = null;
    protected BufferedReader in     = null;
    protected PrintWriter writer    = null;

    protected Socket clientSocket   = null;

    public ClientHandler(Socket socket) {
        this.clientSocket = socket;
        String message = null;
        String title = null;
        String path = null;

        try
        {
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            message = in.readLine();
            title = in.readLine();
            path = in.readLine();

            if (message.equals("UPLOAD"))
            {
                File serverDir = new File(path + "/" + title);                                  //Create path to new file
                serverDir.createNewFile();                                                      //Create a new file
                fos = new FileOutputStream(serverDir);
                writer = new PrintWriter(fos);                                                  //Write to file
            }
            else if (message.equals("DOWNLOAD"))
            {
                File localDir = new File(path + "/" + title);
                localDir.createNewFile();
                fos = new FileOutputStream(localDir);
                writer = new PrintWriter(fos);
            }
            else if(message.equals("DELETE"))
            {
                File deleteFile = new File(path + "/" + title);
                deleteFile.delete();
            }
        } catch (IOException ioe)
        {
             System.err.println("Problem with reading file.");

        } catch (NullPointerException npe)
        {
            System.err.println("There is no message, client shutting down.");
            System.exit(0);
        }
    }

    @Override
    public void run()
    {
        boolean endOfSession = false;

        while (!endOfSession) {
            endOfSession = processUpload();
        }

        try {
            clientSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected boolean processUpload()
    {
        String line = null;

        try
        {
            while ((line = in.readLine()) != null)
            {
                writer.println(line);
            }

            in.close();
            writer.close();
        } catch(IOException ioe)
        {
            System.err.println("Problem with reading file.");
        }
        return true;
    }

    public static void main(String[] args) {

    }
}
