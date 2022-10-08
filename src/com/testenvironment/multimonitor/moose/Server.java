package com.testenvironment.multimonitor.moose;

import com.testenvironment.multimonitor.logging.Logger;

import java.awt.*;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {
    private final static String NAME = "Server/";
    //----------------------------------------------------------------------------------------
    private static Server instance; // Singelton

    private final int PORT = 8000; // always the same
    private final int CONNECTION_TIMEOUT = 60 * 1000; // 1 min

    private ServerSocket serverSocket;
    private Socket socket;
    private PrintWriter outPW;
    private BufferedReader inBR;
    private final Logger logger;

    private ExecutorService executor;

    //----------------------------------------------------------------------------------------

    //-- Runnable for waiting for incoming connections
    private class ConnWaitRunnable implements Runnable {
        String TAG = NAME + "ConnWaitRunnable";

        @Override
        public void run() {
            try {
                System.out.println("Server: Waiting");
                if (serverSocket == null) serverSocket = new ServerSocket(PORT);
                socket = serverSocket.accept();

                // When reached here, Moose is connected
                System.out.println("Connected");

                // Create streams
                inBR = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                outPW = new PrintWriter(new BufferedWriter(
                        new OutputStreamWriter(socket.getOutputStream())), true);

                // Start receiving
                executor.execute(new InRunnable());

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    //-- Runnable for sending messages to Moose
    private class OutRunnable implements Runnable {
        String TAG = NAME + "OutRunnable";
        Memo message;

        public OutRunnable(Memo mssg) {
            message = mssg;
        }

        @Override
        public void run() {
            if (message != null) {
                outPW.println(message);
                outPW.flush();
            }
        }
    }

    //-- Runnable for receiving messages from Moose
    private class InRunnable implements Runnable {
        String TAG = NAME + "InRunnable";

        @Override
        public void run() {
            while (!Thread.currentThread().isInterrupted() && inBR != null) {
                try {
                    String message = inBR.readLine();
                    if (message != null) {
                        Memo memo = Memo.valueOf(message);

                        // If it was scrolling, send to Controller to perform
                        if (memo.getAction().equals("SCROLL")) {
                            System.out.println(memo);
                            Robot.getRobot().moveRobot(memo.getMode());
                            if(!memo.getMode().equals("tap"))
                                logger.setJumpMode(memo.getMode());
                            if(memo.getMode().equals("tapLeft") || memo.getMode().equals("tapRight")) {
                                logger.setTapJumpX(memo.getValue1Int());
                                logger.setTapJumpY(memo.getValue2Int());
                            }
                        }

                    } else {
                        start();
                        return;
                    }
                } catch (IOException | AWTException e) {
                    System.out.println("Error in reading from Moose");
                    e.printStackTrace();
                }
            }
        }
    }

    //----------------------------------------------------------------------------------------

    /**
     * Get the instance
     * @return single instance
     */
    public static Server get() {
        if (instance == null) instance = new Server();
        return instance;
    }

    /**
     * Constructor
     */
    public Server() {
        String TAG = NAME;
        logger = Logger.getLogger();
        // Init executerService for running threads
        executor = Executors.newCachedThreadPool();
    }

    /**
     * Start the server
     */
    public void start() {
        String TAG = NAME + "start";
        executor.execute(new ConnWaitRunnable());
    }

    public void send(Memo mssg) {
        executor.execute(new OutRunnable(mssg));
    }
}
