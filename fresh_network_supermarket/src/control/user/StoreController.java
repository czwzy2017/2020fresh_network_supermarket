package control.user;

import javafx.fxml.FXML;
import model.BeanUser;

public class StoreController {
    @FXML
    private UserMenuController menuController;

    @FXML
    public void initialize() {
        menuController.text_name.setText("欢迎您，" + BeanUser.currentLoginUser.getUser_name());
    }
}
