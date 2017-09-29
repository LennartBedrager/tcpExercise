
package client;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;


public class TimeClient {
  
    Socket socket;
    Scanner scan;
    PrintWriter pw;
    
    public void connect(String ip, int port) throws IOException{
        socket = new Socket(ip,port);
        pw = new PrintWriter(socket.getOutputStream(),true);
    }
    
    
  
    
    public static void main(String[] args) throws IOException {
        TimeClient client = new TimeClient();
        client.connect("localhost", 1234);
        System.out.println("A client requested info about time");
        System.out.println("Done");
    }
    
}
