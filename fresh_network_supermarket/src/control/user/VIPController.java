package control.user;

import javafx.fxml.FXML;
import javafx.scene.text.Text;
import manager.UserManager;
import model.BeanUser;

public class VIPController {
    @FXML
    private Text text_time;

    public void vip_1(){
        new UserManager().vip(1);
        initData();
    }

    public void vip_3(){
        new UserManager().vip(3);
        initData();
    }

    public void vip_12(){
        new UserManager().vip(12);
        initData();
    }

    private void initData(){
        text_time.setText(BeanUser.currentLoginUser.getUser_vip_deadline().toString());
    }

    @FXML
    public void initialize(){
        initData();
    }
}
