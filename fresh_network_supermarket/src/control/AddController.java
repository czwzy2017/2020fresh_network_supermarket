package control;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import main.MainApp;
import manager.ProcurementManager;
import util.BusinessException;

public class AddController {

    @FXML
    private TextField text_procurement_status;

    @FXML
    private TextField text_procurement_goods;

    @FXML
    private TextField text_procurement_count;

    public void addProcurement() throws Exception {
        if ("".equals(text_procurement_goods.getText())) throw new BusinessException("商品编号不能为空");
        if ("".equals(text_procurement_count.getText())) throw new BusinessException("商品数量不能为空");
        int goods, count;
        try {
            goods = Integer.valueOf(text_procurement_goods.getText().trim());
            count = Integer.valueOf(text_procurement_count.getText().trim());
        } catch (NumberFormatException e) {
            throw new BusinessException("商品编号和商品数量必须为数字");
        }
        String status = text_procurement_status.getText();
        new ProcurementManager().add(goods, count, status);
        Stage primaryStage = (Stage) text_procurement_goods.getScene().getWindow();
        primaryStage.close();
        MainApp mainApp = new MainApp();
        mainApp.showProcurement();
    }

}
