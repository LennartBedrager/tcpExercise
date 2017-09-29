
package server;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;


public class EchoServer {
    
    private static int PORT = 1234;
    private static String IP = "localhost";  
    private static ServerSocket serverSocket;

    public static void handleClient(Socket s) throws IOException{
        Scanner scan = new Scanner(s.getInputStream());
        PrintWriter pw = new PrintWriter(s.getOutputStream(),true); // MORE IMPORTANT REMEMBER TRUE FOR AUTOFLUSH!!!!!!
        //IMPORTANT BLOKING 
        String messageFromClient = scan.nextLine();
        System.out.println("Received: " + messageFromClient);
        while(!messageFromClient.equals("STOP")){
            pw.println(messageFromClient.toUpperCase());
            messageFromClient = scan.nextLine(); // IMPORATNT BLOCKING
        }
    }
    
    public static void main(String[] args) throws IOException {
        if(args.length == 2){
            PORT = Integer.parseInt(args[0]);
            IP = args[1];
        }
        
        serverSocket = new ServerSocket(); // Remember to bind
        serverSocket.bind(new InetSocketAddress(IP,PORT));
        System.out.println("Wating for a client!");
        
        while(true){
        Socket socket = serverSocket.accept(); // IMPORTANT BLOCKING CALL
        System.out.println("Client found!");
        handleClient(socket);
        }
        
    }
    
    
}
