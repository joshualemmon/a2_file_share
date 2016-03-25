package sample;

import java.io.*;
import java.net.*;
import java.util.*;

public class FileServerThread extends Thread {
    protected Socket socket             = null;
    //protected PrintWriter out     = null;
    //protected BufferedReader in   = null;
    //protected Vector files        = new Vector(20, 5);
    protected File[] myFilenames        = null;
    protected FileInputStream fis       = null;
    protected BufferedInputStream bis   = null;
    protected OutputStream os           = null;

    public FileServerThread(Socket socket, File[] filenames)
    {
        super();                        //Access thread class
        this.socket = socket;           //Configure socket
        this.myFilenames = filenames;   //Take in certain certain files

        /*
        try {
            out = new PrintWriter(socket.getOutputStream(), true);
            in = new BufferedInputStream(new FileInputStreamReader(socket.getInputStream()));
        } catch (IOException e) {
            System.err.println("IOEXception while opening a read/write connection");
        }
        */
    }

    @Override
    public void run()
    {
        //out.println("File Sharer Server V1.0");

        boolean endOfSession = false;
        while(!endOfSession)
        {
            endOfSession = processCommand();
        }
        try
        {
            socket.close();
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    protected boolean processCommand() {
        File file = null;

        // Sending files to the server
        for(File myFile : myFilenames)
        {
            try
            {
                //Can copy characters and strings
                byte[] fileLengthInBytes = new byte[(int) myFile.length()];
                fis = new FileInputStream(myFile);
                bis = new BufferedInputStream(new FileInputStream(myFile));
                bis.read(fileLengthInBytes, 0, fileLengthInBytes.length);   //How does this part work?
                os = socket.getOutputStream();
                os.write(fileLengthInBytes, 0, fileLengthInBytes.length);
                System.out.println("Sending " + myFile.getName() + "(" + fileLengthInBytes.length + " bytes)");

            } catch(IOException ioe) {
                ioe.printStackTrace();
            }
        }

        try
        {
            os.flush();
            bis.close();
            fis.close();
            System.out.println("Done Sending Files");
        } catch(IOException ioe)
        {
            ioe.printStackTrace();
        }

        /*
        try
        {
            file = fis.readFile();
        } catch (IOException e) {
            System.err.println("Error reading command from socket.");
            return true;
        }
        if (file == null) {
            return true;
        }
        */

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
}