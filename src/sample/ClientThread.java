/*package sample;

import java.io.*;
import java.net.*;
import java.util.*;

public class ClientThread extends Thread {
    protected Socket socket       = null;
    protected PrintWriter out     = null;
    protected BufferedReader in   = null;
    protected Vector files        = new Vector(20, 5);
    protected File[] myFilenames  = null;
    protected FileInputStream fis = null;
    protected BufferedInputStream bis = null;
    protected OutputStream os     = null;

    public ClientThread(Socket socket, File[] filenames)
    {
        super(); //Access thread class
        this.socket = socket; //Configure socket
        this.myFilenames = filenames; //Take in certain certain files

        // send file
        for(int i = 0; i < myFilenames.length; i++)
        {
            try
            {
                //Can copy characters and strings
                byte[] fileLengthInBytes = new byte[(int) myFilenames[i].length()];
                fis = new FileInputStream(myFilenames[i]);
                bis = new BufferedInputStream(new FileInputStream(myFilenames[i]));
                bis.read(fileLengthInBytes, 0, fileLengthInBytes.length);
                os = socket.getOutputStream();

            } catch(IOException ioe) {
                ioe.printStackTrace();
            }
        }

        System.out.println("Sending " + FILE_TO_SEND + "(" + mybytearray.length + " bytes)");
        os.write(mybytearray,0,mybytearray.length);
        os.flush();
        System.out.println("Done.");

        for(Vector myFilename : files)
            try {
                out = new PrintWriter(socket.getOutputStream(), true);
                in = new BufferedInputStream(new FileInputStreamReader(socket.getInputStream()));
            } catch (IOException e) {
                System.err.println("IOEXception while opening a read/write connection");
            }
    }

    @Override
    public void run() {
        out.println("File Sharer Server V1.0");

        boolean endOfSession = false;

        while(!endOfSession) {
            endOfSession = processCommand();
        }

        try {
            socket.close();
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    protected boolean processCommand() {
        File file = null;

        try {
            file = in.read();
        } catch (IOException e) {
            System.err.println("Error reading command from socket.");
            return true;
        }
        if (file == null) {
            return true;
        }

        return processCommand(command, args);
    }

    protected boolean processCommand(String command, String filename) {
        if (command.equalsIgnoreCase("DIR")) {
            return false;
        }
        // these are the other possible commands
        else if (command.equalsIgnoreCase("UPLOAD")) {
            return false;
        }
        else if (command.equalsIgnoreCase("DOWNLOAD"))
        {
            return false;
        }
        else
        {
            return false;
        }

    }
}*/