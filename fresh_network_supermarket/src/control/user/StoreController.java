package control.user;

import control.admin.AdminMenuController;
import javafx.fxml.FXML;
import model.BeanUserInfo;

public class StoreController {
    @FXML
    private AdminMenuController menuController;
    @FXML
    public void initialize() {
        menuController.text_name.setText("欢迎您，" + BeanUserInfo.getCurrentLoginUser().getUser_name());
    }
}
