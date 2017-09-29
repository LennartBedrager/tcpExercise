package server;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class EchoMultiServer {

    private ServerSocket serverSocket;

    public static void main(String[] args) throws IOException {
        EchoMultiServer server = new EchoMultiServer();
        server.start(1236);
    }

    public void start(int port) throws IOException {
        serverSocket = new ServerSocket(port);
        while (true) {
            new EchoClientHandler(serverSocket.accept()).start();
        }
    }

    public void stop() throws IOException {
        serverSocket.close();
    }

    private static class EchoClientHandler extends Thread {

        private Socket clientSocket;
        private PrintWriter out;
        private Scanner in;

        private EchoClientHandler(Socket socket) {
            this.clientSocket = socket;
        }

        @Override
        public void run() {
            try {
                out = new PrintWriter(clientSocket.getOutputStream(), true);
                in = new Scanner(clientSocket.getInputStream());

                String inputLine;

                //DO STUFF HERE
                handleClient(clientSocket);

                in.close();
                out.close();
                clientSocket.close();
            } catch (IOException ex) {
                Logger.getLogger(EchoMultiServer.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        private static void handleClient(Socket socket) throws IOException {
            Scanner scan = new Scanner(socket.getInputStream());
            PrintWriter pw = new PrintWriter(socket.getOutputStream(), true); //DONT FORGET AUTOFLUSH
            //IMPORTANT: BLOCKING

            String input;
            String[] split;
            String cmd;
            String msg;
            while (true) {
                pw.println("Usage: command#message");
                input = scan.nextLine();
                if (input.toLowerCase().equals("exit") || input.toLowerCase().equals("quit")) {
                    pw.println("Disconnected!");
                    break;
                }
                split = input.split("#");
                cmd = split[0];
                msg = split[1];
                pw.println(parseCommand(cmd, msg));
            }

        }

        private static String parseCommand(String cmd, String msg) {
            HashMap<String, String> translations = new HashMap<>();
            translations.put("hund", "dog");
            translations.put("kat", "cat");
            translations.put("dog", "hund");
            translations.put("cat", "kat");
            switch (cmd.toUpperCase()) {
                case "UPPER":
                    return msg.toUpperCase();
                case "LOWER":
                    return msg.toLowerCase();
                case "REVERSE":
                    return new StringBuilder(msg).reverse().toString();
                case "TRANSLATE":
                    return translations.get(msg);
                default:
                    break;
            }
            return null;
        }

    }

}