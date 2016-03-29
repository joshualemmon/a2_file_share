package sample;

import java.io.*;
import java.net.*;

public class FileServer
{
    protected Socket clientSocket           = null; //The socket for one client
    protected ServerSocket serverSocket     = null; //The Server current socket that will be actively listening to requests
                                                    //Needs to handle multiple client threads, therefore there must be multiple
                                                    //file server threads
    public static int SERVER_PORT           = 8080;

    public FileServer()
    {
        try
        {
            serverSocket = new ServerSocket(SERVER_PORT);
            while(true) //Server is waiting for connections
            {
                System.out.println("Waiting...");
                try
                {
                    clientSocket = serverSocket.accept();
                    System.out.println("Client connected.");
                    Thread handlerThread = new Thread(new ClientHandler(clientSocket));
                    handlerThread.start();
                } catch (IOException ioe)
                {
                    ioe.printStackTrace();
                }
            }

        } catch (IOException e) {
            System.err.println("IOEXception while creating server connection");
            System.exit(0);
        }

    }

    public static String[] getServerFiles()
    {
        File servDir = new File(".serverFolder");
        File[] serverFiles = servDir.listFiles();

        String[] s = new String[serverFiles.length];
        int i = 0;
        for(File f : serverFiles)
        {
            s[i++] = f.getName();
        }
        return s;
    }

    public static void main(String[] args)
    {
        FileServer server = new FileServer();
    }

    class ClientHandler implements Runnable {
        protected FileOutputStream fos  = null;
        protected BufferedReader in     = null;
        protected PrintWriter writer    = null;

        protected Socket clientSocket   = null;
        protected boolean skip          = false;

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
                    writer = new PrintWriter(fos); //Write to file
                    skip = false;
                } else if (message.equals("DOWNLOAD"))
                {
                    File localDir = new File(path + "/" + title);
                    localDir.createNewFile();
                    fos = new FileOutputStream(localDir);
                    writer = new PrintWriter(fos);
                    skip = false;
                } else if (message.equals("DELETE"))
                {
                    File deleteFile = new File(path + "/" + title);
                    deleteFile.delete();
                    skip = true;
                }

            } catch (IOException ioe)
            {
                System.err.println("Problem with reading in constructor file.");

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

            while (!endOfSession && !skip) {
                endOfSession = processUpload();
            }

            try {
                clientSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

            skip = false;
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

                in.close();         //properly close streams
                writer.close();
            } catch(IOException ioe)
            {
                System.err.println("Problem with reading processUpload file.");
            }
            return true;
        }
    }
}
