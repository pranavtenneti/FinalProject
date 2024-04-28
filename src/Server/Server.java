package Server;
import java.io.*;
import java.util.*;

import java.net.ServerSocket;
import java.net.Socket;

public class Server {
  public static void main(String[] args) throws IOException {
    new Server().setupNetworking();
  }
  List<Socket> sockets = new ArrayList<>();
  private void setupNetworking() throws IOException {
    try{
      ServerSocket server = new ServerSocket(1024);
      while(true){
        Socket clientSocket=server.accept();
        System.out.println("incoming transmission");
        sockets.add(clientSocket);
        Thread t = new Thread(new ClientHandler(clientSocket));
        t.start();
      }
    } catch (IOException e) {}
  }
  class ClientHandler implements Runnable{
    private Socket clientSocket;

    public ClientHandler(Socket clientSocket) {
      this.clientSocket = clientSocket;
    }

    @Override
    public void run() {
      try {
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(clientSocket.getOutputStream());
        ObjectInputStream objectInputStream = new ObjectInputStream(clientSocket.getInputStream());
        while (true) {
          Object ob=objectInputStream.readObject();
          if(ob instanceof User){
            User user=(User) ob;
            System.out.println("received: "+ user);
          }
          else if(ob instanceof LibraryItem){
            LibraryItem item=(LibraryItem) ob;
            System.out.println("received: "+ item);
          }
        }
      }
      catch (IOException | ClassNotFoundException e){}
    }
  }
}


//PrintWriter writer = new PrintWriter(clientSocket.getOutputStream());
//        BufferedReader reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
//        String message;
//        while((message=reader.readLine())!=null){
//          System.out.println("received: "+message);
//          for(Socket socket:sockets){
//            PrintWriter writer = new PrintWriter(socket.getOutputStream());
//            writer.println(message);
//            writer.flush();
//          }
//        }