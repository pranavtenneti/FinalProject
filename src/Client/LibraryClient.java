package Client;

import java.net.InetAddress;
import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import Server.LibraryItem;
import Server.User;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class LibraryClient extends Application {
    public static ObjectOutputStream oos;
    public static ObjectInputStream ois;

    public static User currentUser;

    public static void getBooks() throws IOException, ClassNotFoundException {
        oos.writeObject("getLibraryItems");
        oos.flush();
        Object object=ois.readObject();
        List<LibraryItem> libraryItems = (List<LibraryItem>) object;
        System.out.println(libraryItems);

    }

    @Override
    public void start(Stage applicationStage) throws Exception {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("GUI/Login.fxml"));
            applicationStage.setTitle("hello");
            applicationStage.setScene(new Scene(root));
            applicationStage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void setupNetworking(){
        try {
            Socket socket = new Socket(InetAddress.getLocalHost().getHostAddress(),1025);
            oos = new ObjectOutputStream(socket.getOutputStream());
            ois = new ObjectInputStream(socket.getInputStream());
            System.out.println("networking established");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void ClientLoginRequest(User user) throws IOException {
        oos.writeObject(user);
        oos.flush();
        currentUser=user;
    }


    public static void main(String[] args) {
        new LibraryClient().setupNetworking();
        launch(args);
    }
}
