package Server;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import static database.LibraryItemDB.*;
import static database.UserDB.*;

public class LibraryServer {
    public static void main(String[] args) throws IOException {
        initializeLibraryItemDB();
        //initializeItemsInDB();
        initializeUserDB();
        new LibraryServer().setupNetworking();
    }

    public static void ServerLoginRequest(User user){
        addUser(user);
    }

    List<Socket> sockets = new ArrayList<>();
    private void setupNetworking() throws IOException {
        try{
            ServerSocket server = new ServerSocket(1025);
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
        ObjectOutputStream objectOutputStream;
        ObjectInputStream objectInputStream;

        public ClientHandler(Socket clientSocket) throws IOException {
            this.clientSocket = clientSocket;
            objectOutputStream= new ObjectOutputStream(clientSocket.getOutputStream());
            objectInputStream= new ObjectInputStream(clientSocket.getInputStream());
        }

        public void sendLibraryItems() throws IOException {
            List<LibraryItem> items = getAllLibraryItems();
            objectOutputStream.writeObject(items);
            objectOutputStream.flush();
        }

        @Override
        public void run() {
            try {
                //BufferedReader reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                Object object;
                while ((object = objectInputStream.readObject()) != null) {
                    if(object instanceof String){
                        String message=(String) object;
                        System.out.println("received: "+ message);
                        if(message.equals("getLibraryItems")){
                            sendLibraryItems();
                        }
                    }
                    if(object instanceof User){
                        User user=(User) object;
                        System.out.println("received: "+ user);
                        ServerLoginRequest(user);
                    }

                }
            }
            catch (IOException e){}
            catch (ClassNotFoundException e){}
        }
    }
}