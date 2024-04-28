package Client.GUI;

import Client.LibraryClient;
import Server.LibraryServer;
import database.Item;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import Server.User;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

public class LoginController {

    @FXML
    private Button LoginButton;

    @FXML
    private TextField Username;

    @FXML
    private TextField Password;

    @FXML
    private Button CreateAccount;

    public void switchToLibrary(ActionEvent event) throws IOException, ClassNotFoundException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("LibraryDisplay.fxml"));
        Parent root = loader.load();
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
        ArrayList<Item> items=new ArrayList<>();
        items.add(new Item("Book","Harry Potter","Me",1.5));
        User user = new User(Username.getText(), Password.getText(),items);
        LibraryClient.ClientLoginRequest(user);
        LibraryClient.getBooks();
    }

    public void switchToSignupPage(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Signup.fxml"));
        Parent root = loader.load();
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
