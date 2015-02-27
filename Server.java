import java.net.*;
import java.io.*;

public class Server extends Thread{
        private ServerSocket serverSocket;
        public Server(int port) throws IOException{
                serverSocket = new ServerSocket(port);
                serverSocket.setSoTimeout(50000);
        }
        public void run(){
                while(true){
                        try{
                                System.out.println("Waiting for client on port "+ serverSocket.getLocalPort()+"...");
                                Socket server = serverSocket.accept();
                                System.out.println("Just connected to " + server.getRemoteSocketAddress());
                                DataInputStream in = new DataInputStream(server.getInputStream());
                                System.out.println(in.readUTF());
                                DataOutputStream out = new DataOutputStream(server.getOutputStream());
                                out.writeUTF("Thank You for connecting to " + server.getLocalSocketAddress() + "\nGoodBye");
                                server.close();
                        }
                        catch(Exception e){break;}
                }
        }
        public static void main(String args[]){
                int port = Integer.parseInt(args[0]);
                try{
                        Thread t = new Server(port);
                        t.start();
                }catch(Exception e){}
        }
}
