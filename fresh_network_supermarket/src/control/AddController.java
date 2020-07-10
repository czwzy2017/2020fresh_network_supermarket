package control;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import main.MainApp;
import manager.CookbookManager;
import manager.FreshManager;
import manager.ProcurementManager;
import util.BusinessException;

import java.io.IOException;

public class AddController {

    @FXML
    private TextField text_procurement_status;

    @FXML
    private TextField text_procurement_goods;

    @FXML
    private TextField text_procurement_count;

    @FXML
    private TextField text_goods_size;

    @FXML
    private TextField text_goods_category;

    @FXML
    private TextField text_goods_detail;

    @FXML
    private TextField text_goods_name;

    @FXML
    private TextField text_goods_count;

    @FXML
    private TextField text_goods_price;

    @FXML
    private TextField text_goods_vip;

    @FXML
    private TextField text_cookbook_name;

    @FXML
    private TextArea text_cookbook_ingredient;

    @FXML
    private TextArea text_cookbook_step;

    public void addProcurement() throws IOException {
        if ("".equals(text_procurement_goods.getText())) throw new BusinessException("商品编号不能为空");
        if ("".equals(text_procurement_count.getText())) throw new BusinessException("商品数量不能为空");
        int goods, count;
        try {
            goods = Integer.valueOf(text_procurement_goods.getText().trim());
            count = Integer.valueOf(text_procurement_count.getText().trim());
        } catch (NumberFormatException e) {
            throw new BusinessException("商品编号和商品数量必须为数字");
        }
        String status = text_procurement_status.getText().replaceAll(" ", "");
        new ProcurementManager().add(goods, count, status);
        Stage primaryStage = (Stage) text_procurement_goods.getScene().getWindow();
        primaryStage.close();
        MainApp mainApp = new MainApp();
        mainApp.showProcurement();
    }

    public void addGoods() throws IOException {
        double price;
        int count=0, category;
        if ("".equals(text_goods_category.getText().trim())) throw new BusinessException("分类编号不能为空");
        if ("".equals(text_goods_price.getText().trim())) throw new BusinessException("商品价格不能为空");
        try {
            price = Double.valueOf(text_goods_price.getText().trim());
        } catch (NumberFormatException e) {
            throw new BusinessException("商品价格必须为实数");
        }
        try {
            if ("".equals(text_goods_count.getText().trim())) count = 0;
            else count = Integer.valueOf(text_goods_count.getText().trim());
            category = Integer.valueOf(text_goods_category.getText().trim());
        } catch (NumberFormatException e) {
            throw new BusinessException("分类编号和商品数量必须为正整数");
        }
        String name = text_goods_name.getText().trim();
        String vip = text_goods_vip.getText().trim();
        String size = text_goods_size.getText().trim();
        String detail = text_goods_detail.getText().trim();
        new FreshManager().addGoods(category, name, price, count, vip, size, detail);
        Stage primaryStage = (Stage) text_goods_name.getScene().getWindow();
        primaryStage.close();
        new MainApp().showFresh();
    }

    public void addCookbook() throws IOException{
        String name = text_cookbook_name.getText().trim();
        String ingredient = text_cookbook_ingredient.getText().trim();
        String step = text_cookbook_step.getText().trim();
        new CookbookManager().addCookbook(name,ingredient,step);
        Stage primaryStage = (Stage) text_cookbook_name.getScene().getWindow();
        primaryStage.close();
        new MainApp().showCookbook();
    }
}
