package control.user;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Slider;
import javafx.scene.control.TextArea;
import javafx.scene.text.Text;
import manager.FreshManager;
import manager.OrderManager;
import model.BeanGoods;
import model.BeanGoodsOrders;
import model.BeanUser;
import model.GoodsComment;
import util.BusinessException;
import util.DBUtil;
import util.DbException;

import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Timestamp;

public class GoodsController {
    @FXML
    private UserMenuController menuController;

    @FXML
    private Text text_name;

    @FXML
    private Text text_price;

    @FXML
    private Text text_vip;

    @FXML
    private Text text_size;

    @FXML
    private Text text_detail;

    @FXML
    private TextArea text_comment;

    @FXML
    private Slider slider_level;

    BeanGoods goods;

    public void show(int id) {
        goods = new FreshManager().selectGoods(id);
        text_name.setText(goods.getGoods_name());
        text_price.setText(String.valueOf(goods.getGoods_price()));
        text_vip.setText(goods.getVip_price_string());
        text_size.setText(goods.getGoods_size());
        text_detail.setText(goods.getGoods_detail());
        GoodsComment goodsComment = loadComment();
        text_comment.setText(goodsComment.getComment_detail());
        slider_level.setValue(goodsComment.getComment_level());
    }

    public void shopping() {
        if (BeanGoodsOrders.currentOrders == null) {
            int orderId = new OrderManager().makeOrder();
            BeanGoodsOrders r = new BeanGoodsOrders();
            r.setOrders_id(orderId);
            r.setUser_id(BeanUser.currentLoginUser.getUser_id());
            r.setOrder_status("未下单");
            BeanGoodsOrders.currentOrders = r;
        }
        new OrderManager().addGoods(goods.getGoods_id());
    }

    public void comment() {
        GoodsComment goodsComment = new GoodsComment();
        goodsComment.setUser_id(BeanUser.currentLoginUser.getUser_id());
        goodsComment.setGoods_id(goods.getGoods_id());
        goodsComment.setComment_level((int) slider_level.getValue());
        goodsComment.setComment_detail(text_comment.getText());
        goodsComment.setComment_date(new Date(System.currentTimeMillis()));
        addComment(goodsComment);
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("成功");
        alert.setHeaderText(null);
        alert.setContentText("评价成功");
        alert.showAndWait();
    }

    @FXML
    public void initialize() {
        menuController.text_name.setText("欢迎您，" + BeanUser.currentLoginUser.getUser_name());
    }

    private void addComment(GoodsComment r) {
        Connection conn = null;
        try {
            conn = DBUtil.getConnection();
            String sql = "select * from goods_orders,orders_detail where goods_id=? and goods_orders.orders_id=orders_detail.orders_id and orders_status='已完成' and user_id=?";
            java.sql.PreparedStatement pst = conn.prepareStatement(sql);
            pst.setInt(1, r.getGoods_id());
            pst.setInt(2,r.getUser_id());
            java.sql.ResultSet rs = pst.executeQuery();
            if (!rs.next()) throw new BusinessException("您还未购买过此商品或还未确认收货，不可评价");
            pst.close();
            sql = "select * from goods_comment where goods_id=? and user_id=?";
            pst = conn.prepareStatement(sql);
            pst.setInt(1, r.getGoods_id());
            pst.setInt(2, r.getUser_id());
            rs = pst.executeQuery();
            if (rs.next()) throw new BusinessException("您已评价过");
            rs.close();
            pst.close();
            sql = "insert into goods_comment(goods_id, user_id, comment_detail, comment_date, comment_level) VALUE (?,?,?,?,?)";
            pst = conn.prepareStatement(sql);
            pst.setInt(1, r.getGoods_id());
            pst.setInt(2, r.getUser_id());
            pst.setString(3, r.getComment_detail());
            pst.setTimestamp(4, new Timestamp(r.getComment_date().getTime()));
            pst.setInt(5, r.getComment_level());
            pst.execute();
            pst.close();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DbException(e);
        } finally {
            if (conn != null)
                try {
                    conn.close();
                } catch (SQLException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
        }
    }

    public GoodsComment loadComment() {
        GoodsComment result = new GoodsComment();
        Connection conn = null;
        try {
            conn = DBUtil.getConnection();
            String sql = "select * from goods_comment where goods_id=? and user_id=?";
            java.sql.PreparedStatement pst = conn.prepareStatement(sql);
            pst.setInt(1, goods.getGoods_id());
            pst.setInt(2, BeanUser.currentLoginUser.getUser_id());
            java.sql.ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                result.setComment_detail(rs.getString(3));
                result.setComment_level(rs.getInt(5));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DbException(e);
        } finally {
            if (conn != null)
                try {
                    conn.close();
                } catch (SQLException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
        }
        return result;
    }
}
