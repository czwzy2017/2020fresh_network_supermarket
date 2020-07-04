package control;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import main.registerMain;

public class loginController {

    @FXML
    private TextField text_id;

    @FXML
    private Button button_register;

    @FXML
    private Label label_pwd;

    @FXML
    private TextField text_pwd;

    @FXML
    private Label label_id;

    public void eventRegister() throws Exception {
        registerMain re=new registerMain();
        Stage stage=new Stage();
        re.start(stage);
    }

}
