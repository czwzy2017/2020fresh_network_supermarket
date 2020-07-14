package manager;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.BeanGoodsOrders;
import model.BeanUser;
import model.OrderDetail;
import util.BusinessException;
import util.DBUtil;
import util.DbException;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;

public class OrderManager {
    public void setCurrentOrder() {
        Connection conn = null;
        try {
            conn = DBUtil.getConnection();
            String sql = "select * from goods_orders where user_id=? and orders_status='未下单'";
            java.sql.PreparedStatement pst = conn.prepareStatement(sql);
            pst.setInt(1, BeanUser.currentLoginUser.getUser_id());
            java.sql.ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                BeanGoodsOrders r = new BeanGoodsOrders();
                r.setOrders_id(rs.getInt(1));
                r.setCoupon_id(rs.getInt(2));
                r.setUser_id(rs.getInt(3));
                r.setOrder_original_price(rs.getDouble(4));
                r.setOrder_final_price(rs.getDouble(5));
                r.setOrder_address(rs.getString(6));
                r.setOrder_time(rs.getTimestamp(7));
                r.setOrder_real_time(rs.getTimestamp(8));
                r.setOrder_status(rs.getString(9));
                BeanGoodsOrders.currentOrders = r;
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
    }

    public ObservableList<BeanGoodsOrders> loadOrder() {
        ObservableList<BeanGoodsOrders> result = FXCollections.observableArrayList();
        Connection conn = null;
        try {
            conn = DBUtil.getConnection();
            String sql = "select * from goods_orders where user_id = ?";
            java.sql.PreparedStatement pst = conn.prepareStatement(sql);
            pst.setInt(1, BeanUser.currentLoginUser.getUser_id());
            java.sql.ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                BeanGoodsOrders r = new BeanGoodsOrders();
                r.setOrders_id(rs.getInt(1));
                r.setCoupon_id(rs.getInt(2));
                r.setUser_id(rs.getInt(3));
                r.setOrder_original_price(rs.getDouble(4));
                r.setOrder_final_price(rs.getDouble(5));
                r.setOrder_address(rs.getString(6));
                r.setOrder_time(rs.getTimestamp(7));
                r.setOrder_real_time(rs.getTimestamp(8));
                r.setOrder_status(rs.getString(9));
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
            pst.setInt(1, id);
            java.sql.ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                OrderDetail r = new OrderDetail();
                r.setOrders_id(rs.getInt(1));
                r.setDiscount_id(rs.getInt(2));
                r.setDiscount_id_string();
                r.setGoods_id(rs.getInt(3));
                sql = "select goods_name from goods where goods_id=?";
                pst = conn.prepareStatement(sql);
                pst.setInt(1, r.getGoods_id());
                java.sql.ResultSet rst = pst.executeQuery();
                if (rst.next()) r.setGoods_name(rst.getString(1));
                r.setDetail_count(rs.getInt(4));
                r.setPromotion_count(rs.getInt(5));
                r.setGoods_price(rs.getDouble(6));
                r.setDiscount(rs.getDouble(7));
                sql = "select discount_goods_count from full_discount where discount_id=?";
                pst = conn.prepareStatement(sql);
                pst.setInt(1, r.getDiscount_id());
                rst = pst.executeQuery();
                if (rst.next()) r.setDiscount_goods_count(rst.getInt(1));
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

    public int countOrder(){
        Connection conn = null;
        int count=0;
        try {
            conn = DBUtil.getConnection();
            String sql = "select count(*) from goods_orders where user_id = ?";
            java.sql.PreparedStatement pst = conn.prepareStatement(sql);
            pst.setInt(1, BeanUser.currentLoginUser.getUser_id());
            java.sql.ResultSet rs = pst.executeQuery();
            if (rs.next()) count=rs.getInt(1);
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
        return count;
    }

    public double sumPrice(){
        Connection conn = null;
       double price=0;
        try {
            conn = DBUtil.getConnection();
            String sql = "select sum(orders_original_price) from goods_orders where user_id = ?";
            java.sql.PreparedStatement pst = conn.prepareStatement(sql);
            pst.setInt(1, BeanUser.currentLoginUser.getUser_id());
            java.sql.ResultSet rs = pst.executeQuery();
            if (rs.next()) price=rs.getDouble(1);
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
        return ((int)(price*100+0.5))/100.0;
    }

    public double sumSale(){
        Connection conn = null;
        double price=0;
        try {
            conn = DBUtil.getConnection();
            String sql = "select sum(orders_original_price),sum(orders_final_price) from goods_orders where user_id = ?";
            java.sql.PreparedStatement pst = conn.prepareStatement(sql);
            pst.setInt(1, BeanUser.currentLoginUser.getUser_id());
            java.sql.ResultSet rs = pst.executeQuery();
            if (rs.next()) price=rs.getDouble(1)-rs.getDouble(2);
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
        return ((int)(price*100+0.5))/100.0;
    }

    public int makeOrder() {
        Connection conn = null;
        int id = 0;
        try {
            conn = DBUtil.getConnection();
            String sql = "insert into goods_orders(user_id, orders_original_price, orders_final_price, orders_address,orders_time,orders_real_time,orders_status)  value (?,0,0,' ',null,null,'未下单')";
            java.sql.PreparedStatement pst = conn.prepareStatement(sql);
            pst.setInt(1, BeanUser.currentLoginUser.getUser_id());
            pst.execute();
            sql = "select max(orders_id) from goods_orders";
            java.sql.Statement st = conn.createStatement();
            java.sql.ResultSet rs = st.executeQuery(sql);
            if (rs.next()) id = rs.getInt(1);
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
        return id;
    }

    public void addGoods(int goodId) {
        Connection conn = null;
        OrderDetail r = new OrderDetail();
        r.setOrders_id(BeanGoodsOrders.currentOrders.getOrders_id());
        r.setGoods_id(goodId);
        try {
            conn = DBUtil.getConnection();
            String sql = "select full_discount.discount_id,discount from full_discount,discount_goods where goods_id=? and full_discount.discount_id=discount_goods.discount_id and( ? between full_discount.discount_begin_date and full_discount.discount_end_date)";
            java.sql.PreparedStatement pst = conn.prepareStatement(sql);
            pst.setInt(1, r.getGoods_id());
            pst.setTimestamp(2, new Timestamp(System.currentTimeMillis()));
            java.sql.ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                r.setDiscount_id(rs.getInt(1));
                r.setDiscount(rs.getDouble(2));
            } else {
                r.setDiscount_id(0);
            }
            sql = "select goods_name from goods where goods_id=?";
            pst = conn.prepareStatement(sql);
            pst.setInt(1, r.getGoods_id());
            rs = pst.executeQuery();
            if (rs.next()) r.setGoods_name(rs.getString(1));
            int count = 0;
            sql = "select goods_count from goods where goods_id=?";
            pst = conn.prepareStatement(sql);
            pst.setInt(1, r.getGoods_id());
            rs = pst.executeQuery();
            if (rs.next()) count = rs.getInt(1);
            sql = "select detail_count from orders_detail where orders_id=? and goods_id=?";
            pst = conn.prepareStatement(sql);
            pst.setInt(1, r.getOrders_id());
            pst.setInt(2, r.getGoods_id());
            rs = pst.executeQuery();
            if (rs.next()) {
                if (count > rs.getInt(1)) r.setDetail_count(rs.getInt(1) + 1);
                else throw new BusinessException("库存不足");
            } else r.setDetail_count(1);
            double promotion = 0;
            sql = "select promotion_count,promotion_price from promotion where goods_id=? and (? between promotion_begin_date and promotion_end_date)";
            pst = conn.prepareStatement(sql);
            pst.setInt(1, r.getGoods_id());
            pst.setTimestamp(2, new Timestamp(System.currentTimeMillis()));
            rs = pst.executeQuery();
            if (rs.next()) {
                if (rs.getInt(1) > r.getDetail_count()) r.setPromotion_count(r.getDetail_count());
                else r.setPromotion_count(rs.getInt(1));
                promotion = rs.getDouble(2);
            } else {
                r.setPromotion_count(0);
            }
            double price = 0, vip = 0;
            sql = "select goods_price,goods_vip_price from goods where goods_id=?";
            pst = conn.prepareStatement(sql);
            pst.setInt(1, r.getGoods_id());
            rs = pst.executeQuery();
            if (rs.next()) {
                price = rs.getDouble(1);
                vip = rs.getDouble(2);
            }
            if (vip != 0 && BeanUser.currentLoginUser.isUser_vip()) {
                if (vip <= promotion) {
                    r.setPromotion_count(0);
                }
                r.setGoods_price((vip * (r.getDetail_count() - r.getPromotion_count()) + promotion * r.getPromotion_count()) / r.getDetail_count());
            } else {
                r.setGoods_price((price * (r.getDetail_count() - r.getPromotion_count()) + promotion * r.getPromotion_count()) / r.getDetail_count());
            }
            if (r.getDetail_count() == 1) {
                if (r.getDiscount_id() == 0) {
                    sql = "insert into orders_detail(orders_id, goods_id, detail_count, promotion_count,goods_price) VALUE (?,?,?,?,?)";
                    pst = conn.prepareStatement(sql);
                    pst.setInt(1, r.getOrders_id());
                    pst.setInt(2, r.getGoods_id());
                    pst.setInt(3, r.getDetail_count());
                    pst.setInt(4, r.getPromotion_count());
                    pst.setDouble(5, r.getGoods_price());
                } else {
                    sql = "insert into orders_detail(orders_id, discount_id, goods_id, detail_count, promotion_count,goods_price, discount) VALUE (?,?,?,?,?,?,?)";
                    pst = conn.prepareStatement(sql);
                    pst.setInt(1, r.getOrders_id());
                    pst.setInt(2, r.getDiscount_id());
                    pst.setInt(3, r.getGoods_id());
                    pst.setInt(4, r.getDetail_count());
                    pst.setInt(5, r.getPromotion_count());
                    pst.setDouble(6, r.getGoods_price());
                    pst.setDouble(7, r.getDiscount());
                }

            } else {
                if (r.getDiscount_id() == 0) {
                    sql = "update orders_detail set discount_id=?,detail_count=?, promotion_count=?,goods_price=?, discount=? where orders_id=? and goods_id=?";
                    pst = conn.prepareStatement(sql);
                    pst.setString(1, null);
                    pst.setInt(2, r.getDetail_count());
                    pst.setInt(3, r.getPromotion_count());
                    pst.setDouble(4, r.getGoods_price());
                    pst.setString(5, null);
                    pst.setInt(6, r.getOrders_id());
                    pst.setInt(7, r.getGoods_id());
                } else {
                    sql = "update orders_detail set discount_id=?,detail_count=?, promotion_count=?,goods_price=?, discount=? where orders_id=? and goods_id=?";
                    pst = conn.prepareStatement(sql);
                    pst.setInt(1, r.getDiscount_id());
                    pst.setInt(2, r.getDetail_count());
                    pst.setInt(3, r.getPromotion_count());
                    pst.setDouble(4, r.getGoods_price());
                    pst.setDouble(5, r.getDiscount());
                    pst.setInt(6, r.getOrders_id());
                    pst.setInt(7, r.getGoods_id());
                }
            }
            pst.execute();
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

    public void delete(OrderDetail r) {
        Connection conn = null;
        try {
            conn = DBUtil.getConnection();
            String sql = "delete from orders_detail where goods_id=? and orders_id=?";
            java.sql.PreparedStatement pst = conn.prepareStatement(sql);
            pst.setInt(1, r.getGoods_id());
            pst.setInt(2, r.getOrders_id());
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

    public void modify(OrderDetail r) {
        Connection conn = null;
        try {
            conn = DBUtil.getConnection();
            String sql = "select full_discount.discount_id,discount from full_discount,discount_goods where goods_id=? and full_discount.discount_id=discount_goods.discount_id and( ? between full_discount.discount_begin_date and full_discount.discount_end_date)";
            java.sql.PreparedStatement pst = conn.prepareStatement(sql);
            pst.setInt(1, r.getGoods_id());
            pst.setTimestamp(2, new Timestamp(System.currentTimeMillis()));
            java.sql.ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                r.setDiscount_id(rs.getInt(1));
                r.setDiscount(rs.getDouble(2));
            } else {
                r.setDiscount_id(0);
            }
            double promotion = 0;
            sql = "select promotion_count,promotion_price from promotion where goods_id=? and (? between promotion_begin_date and promotion_end_date)";
            pst = conn.prepareStatement(sql);
            pst.setInt(1, r.getGoods_id());
            pst.setTimestamp(2, new Timestamp(System.currentTimeMillis()));
            rs = pst.executeQuery();
            if (rs.next()) {
                if (rs.getInt(1) > r.getDetail_count()) r.setPromotion_count(r.getDetail_count());
                else r.setPromotion_count(rs.getInt(1));
                promotion = rs.getDouble(2);
            } else {
                r.setPromotion_count(0);
            }
            double price = 0, vip = 0;
            sql = "select goods_price,goods_vip_price from goods where goods_id=?";
            pst = conn.prepareStatement(sql);
            pst.setInt(1, r.getGoods_id());
            rs = pst.executeQuery();
            if (rs.next()) {
                price = rs.getDouble(1);
                vip = rs.getDouble(2);
            }
            if (vip != 0 && BeanUser.currentLoginUser.isUser_vip()) {
                if (vip <= promotion) {
                    r.setPromotion_count(0);
                }
                r.setGoods_price((vip * (r.getDetail_count() - r.getPromotion_count()) + promotion * r.getPromotion_count()) / r.getDetail_count());
            } else {
                r.setGoods_price((price * (r.getDetail_count() - r.getPromotion_count()) + promotion * r.getPromotion_count()) / r.getDetail_count());
            }
            if (r.getDiscount_id() == 0) {
                sql = "update orders_detail set discount_id=?,detail_count=?, promotion_count=?,goods_price=?, discount=? where orders_id=? and goods_id=?";
                pst = conn.prepareStatement(sql);
                pst.setString(1, null);
                pst.setInt(2, r.getDetail_count());
                pst.setInt(3, r.getPromotion_count());
                pst.setDouble(4, r.getGoods_price());
                pst.setString(5, null);
                pst.setInt(6, r.getOrders_id());
                pst.setInt(7, r.getGoods_id());
            } else {
                sql = "update orders_detail set discount_id=?,detail_count=?, promotion_count=?,goods_price=?, discount=? where orders_id=? and goods_id=?";
                pst = conn.prepareStatement(sql);
                pst.setInt(1, r.getDiscount_id());
                pst.setInt(2, r.getDetail_count());
                pst.setInt(3, r.getPromotion_count());
                pst.setDouble(4, r.getGoods_price());
                pst.setDouble(5, r.getDiscount());
                pst.setInt(6, r.getOrders_id());
                pst.setInt(7, r.getGoods_id());
            }
            pst.execute();
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

    public void buy() {
        Connection conn = null;
        BeanGoodsOrders r = BeanGoodsOrders.currentOrders;
        try {
            conn = DBUtil.getConnection();
            String sql = "update goods_orders set coupon_id=?,orders_original_price=?,orders_final_price=?,orders_address=?,orders_time=?,orders_status='配送中' where orders_id=?";
            java.sql.PreparedStatement pst = conn.prepareStatement(sql);
            if (r.getCoupon_id() != 0) pst.setInt(1, r.getCoupon_id());
            else pst.setString(1, null);
            pst.setDouble(2, r.getOrder_original_price());
            pst.setDouble(3, r.getOrder_final_price());
            pst.setString(4, r.getOrder_address());
            pst.setTimestamp(5, new Timestamp(System.currentTimeMillis() + 30 * 60 * 1000));
            pst.setInt(6, r.getOrders_id());
            pst.execute();
            pst.close();
            if (r.getCoupon_id() != 0) {
                sql = "update user_coupon set user_coupon_count=user_coupon_count-1 where user_id=? and coupon_id=?";
                pst = conn.prepareStatement(sql);
                pst.setInt(1, r.getUser_id());
                pst.setInt(2, r.getCoupon_id());
                pst.execute();
                pst.close();
            }
            sql = "select * from orders_detail where orders_id=?";
            pst = conn.prepareStatement(sql);
            pst.setInt(1, r.getOrders_id());
            java.sql.ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                sql = "update goods set goods_count=goods_count-? where goods_id=?";
                pst = conn.prepareStatement(sql);
                pst.setInt(1, rs.getInt(4));
                pst.setInt(2, rs.getInt(3));
                pst.execute();
                sql = "update promotion set promotion_count=promotion_count-? where goods_id=? and (? between promotion_begin_date and promotion_end_date)";
                pst = conn.prepareStatement(sql);
                pst.setInt(1, rs.getInt(5));
                pst.setInt(2, rs.getInt(3));
                pst.setTimestamp(3, new Timestamp(System.currentTimeMillis()));
                pst.execute();
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
    }

    public void setPrice() {
        Connection conn = null;
        BeanGoodsOrders r = BeanGoodsOrders.currentOrders;
        double finalPrice = 0, originalPrice = 0;
        try {
            conn = DBUtil.getConnection();
            String sql = "select detail_count,goods_price,discount_id,discount,goods_id from orders_detail where orders_id=?";
            java.sql.PreparedStatement pst = conn.prepareStatement(sql);
            pst.setInt(1, BeanGoodsOrders.currentOrders.getOrders_id());
            java.sql.ResultSet rs = pst.executeQuery();
            r.setDiscount("");
            while (rs.next()) {
                originalPrice += rs.getInt(1) * rs.getDouble(2);
                if (rs.getInt(3) != 0) {
                    int count = 0;
                    sql = "select sum(detail_count) from orders_detail where orders_id=? and discount_id=?";
                    pst = conn.prepareStatement(sql);
                    pst.setInt(1, r.getOrders_id());
                    pst.setInt(2, rs.getInt(3));
                    java.sql.ResultSet rst = pst.executeQuery();
                    if (rst.next()) count = rst.getInt(1);
                    sql = "select discount_goods_count from full_discount where discount_id=?";
                    pst = conn.prepareStatement(sql);
                    pst.setInt(1, rs.getInt(3));
                    rst = pst.executeQuery();
                    if (rst.next()) {
                        if (count >= rst.getInt(1)) {
                            finalPrice += rs.getInt(1) * rs.getDouble(2) * rs.getDouble(4);
                            sql = "select goods_name from goods where goods_id=?";
                            pst = conn.prepareStatement(sql);
                            pst.setInt(1, rs.getInt(5));
                            java.sql.ResultSet resultSet = pst.executeQuery();
                            if (resultSet.next()) r.setDiscount(r.getDiscount() + resultSet.getString(1) + " ");
                        } else {
                            finalPrice += rs.getInt(1) * rs.getDouble(2);
                        }
                    }
                } else {
                    finalPrice += rs.getInt(1) * rs.getDouble(2);
                }
            }
            r.setOrder_original_price(originalPrice);
            r.setOrder_final_price(finalPrice);
            sql = "select coupon_credit_price,coupon_minimum_price,coupon.coupon_id from coupon,user_coupon where coupon.coupon_id=user_coupon.coupon_id and coupon_credit_price in ( select max(coupon_credit_price) from coupon,user_coupon where coupon.coupon_id=user_coupon.coupon_id and ?>coupon_minimum_price and (? between coupon_begin_date and coupon_end_date))";
            pst = conn.prepareStatement(sql);
            pst.setDouble(1, r.getOrder_original_price());
            pst.setTimestamp(2,new Timestamp(System.currentTimeMillis()));
            rs = pst.executeQuery();
            if (rs.next()) {
                r.setOrder_final_price(r.getOrder_final_price() - rs.getDouble(1));
                r.setCoupon_id(rs.getInt(3));
                r.setCoupon("满" + rs.getDouble(2) + "减" + rs.getDouble(1));
            }
            BeanGoodsOrders.currentOrders = r;
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

    public void receipt(int id) {
        Connection conn = null;
        try {
            conn = DBUtil.getConnection();
            String sql = "update goods_orders set orders_real_time=?,orders_status='已完成' where orders_id=?";
            java.sql.PreparedStatement pst = conn.prepareStatement(sql);
            pst.setTimestamp(1, new Timestamp(System.currentTimeMillis()));
            pst.setInt(2, id);
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

    public void refund(BeanGoodsOrders r) {
        Connection conn = null;
        try {
            conn = DBUtil.getConnection();
            String sql = "update goods_orders set orders_status='退货' where orders_id=?";
            java.sql.PreparedStatement pst = conn.prepareStatement(sql);
            pst.setInt(1, r.getOrders_id());
            pst.execute();
            pst.close();
            sql = "update user_coupon set user_coupon_count=user_coupon_count+1 where coupon_id=? and user_id=?";
            pst = conn.prepareStatement(sql);
            pst.setInt(1, r.getCoupon_id());
            pst.setInt(2, r.getUser_id());
            pst.execute();
            sql = "select goods_id,detail_count,promotion_count from orders_detail where orders_id=?";
            pst = conn.prepareStatement(sql);
            pst.setInt(1, r.getOrders_id());
            java.sql.ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                sql = "update goods set goods_count=goods_count+? where goods_id=?";
                pst = conn.prepareStatement(sql);
                pst.setInt(1, rs.getInt(2));
                pst.setInt(2, rs.getInt(1));
                pst.execute();
                pst.close();
                sql = "update promotion set promotion_count=promotion_count+? where goods_id=?";
                pst = conn.prepareStatement(sql);
                pst.setInt(1, rs.getInt(3));
                pst.setInt(2, rs.getInt(1));
                pst.execute();
                pst.close();
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
    }
}
