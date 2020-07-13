package manager;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.BeanGoodsOrders;
import model.BeanUser;
import model.OrderDetail;
import util.DBUtil;
import util.DbException;

import java.sql.Connection;
import java.sql.SQLException;

public class OrderManager {
    public ObservableList<BeanGoodsOrders> loadOrder() {
        ObservableList<BeanGoodsOrders> result = FXCollections.observableArrayList();
        Connection conn = null;
        try {
            conn = DBUtil.getConnection();
            String sql = "select * from goods_orders where user_id=?";
            java.sql.PreparedStatement pst = conn.prepareStatement(sql);
            pst.setInt(1,BeanUser.currentLoginUser.getUser_id());
            java.sql.ResultSet rs = pst.executeQuery(sql);
            while (rs.next()) {
                BeanGoodsOrders r=new BeanGoodsOrders();
                r.setOrders_id(rs.getInt(1));
                r.setCoupon_id(rs.getInt(2));
                r.setUser_id(rs.getInt(3));
                r.setOrder_original_price(rs.getDouble(4));
                r.setOder_final_price(rs.getDouble(5));
                r.setOrder_address(rs.getString(6));
                r.setOrder_time(rs.getTimestamp(7));
                r.setOrder_status(rs.getString(8));
                result.add(r);
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

    public ObservableList<OrderDetail> loadOrderDetail(int id) {
        ObservableList<OrderDetail> result = FXCollections.observableArrayList();
        Connection conn = null;
        try {
            conn = DBUtil.getConnection();
            String sql = "select * from orders_detail where orders_id=?";
            java.sql.PreparedStatement pst = conn.prepareStatement(sql);
            pst.setInt(1,id);
            java.sql.ResultSet rs = pst.executeQuery(sql);
            while (rs.next()) {
                OrderDetail r=new OrderDetail();
                r.setOrders_id(rs.getInt(1));
                r.setDiscount_id(rs.getInt(2));
                r.setDiscount_id_string();
                r.setGoods_id(rs.getInt(3));
                r.setDetail_count(rs.getInt(4));
                r.setGoods_price(rs.getDouble(5));
                r.setDiscount(rs.getDouble(6));
                sql = "select discount_goods_count from full_discount where discount_id=?";
                pst =conn.prepareStatement(sql);
                pst.setInt(1,r.getDiscount_id());
                java.sql.ResultSet rst=pst.executeQuery();
                if (rs.next()) r.setDiscount_goods_count(rst.getInt(1));
                r.setDiscount_detail();
                result.add(r);
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

    public int makeOrder(){
        Connection conn = null;
        int id=0;
        try{
            conn = DBUtil.getConnection();
            String sql = "insert into goods_orders(user_id, orders_original_price, orders_final_price, orders_address,orders_status)  value (?,0,0,' ','未下单')";
            java.sql.PreparedStatement pst=conn.prepareStatement(sql);
            pst.setInt(1,BeanUser.currentLoginUser.getUser_id());
            pst.execute();
            sql = "select max(orders_id) from goods_orders";
            java.sql.Statement st=conn.createStatement();
            java.sql.ResultSet rs=st.executeQuery(sql);
            if (rs.next()) id=rs.getInt(1);
        }catch (SQLException e) {
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
        return id;
    }

    public void addGoods(int goodId){
        Connection conn = null;
        OrderDetail r=new OrderDetail();
        r.setOrders_id(BeanGoodsOrders.currentOrders.getOrders_id());
        try {
            conn = DBUtil.getConnection();
            String sql="select discount_id,discount from full_discount,discount_goods where goods_id=? and full_discount.discount_id=discount_goods.discount_id";
            java.sql.PreparedStatement pst = conn.prepareStatement(sql);
            pst.setInt(1,goodId);
            java.sql.ResultSet rs=pst.executeQuery();
            if (rs.next()){
                r.setDiscount_id(rs.getInt(1));
                r.setDiscount(rs.getDouble(2));
            }
            sql ="select detail_count from orders_detail where orders_id=? and goods_id=?";
            pst = conn.prepareStatement(sql);
            pst.setInt(1,r.getOrders_id());

        }catch (SQLException e) {
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
}
