package unittest.scenes;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class dController {
    @FXML
    public TextField Username;
    @FXML
    TextField Password;
    @FXML
    Button LoginButton;

    public void Login(ActionEvent e){
        TextField username = Username;

    }

}
