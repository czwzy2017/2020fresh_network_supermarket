package manager;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.BeanPromotion;
import util.BusinessException;
import util.DBUtil;
import util.DbException;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;

public class PromotionManager {
    public ObservableList<BeanPromotion> loadPromotion() {
        ObservableList<BeanPromotion> result = FXCollections.observableArrayList();
        Connection conn = null;
        try {
            conn = DBUtil.getConnection();
            String sql = "select promotion_id, goods.goods_id, promotion_price, promotion_count, promotion_begin_date, promotion_end_date,goods_name from promotion,goods where promotion.goods_id=goods.goods_id ";
            java.sql.Statement st = conn.createStatement();
            java.sql.ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                BeanPromotion r = new BeanPromotion();
                r.setPromotion_id(rs.getInt(1));
                r.setGoods_id(rs.getInt(2));
                r.setPromotion_price(rs.getDouble(3));
                r.setPromotion_count(rs.getInt(4));
                r.setPromotion_begin_date(rs.getTimestamp(5));
                r.setPromotion_end_date(rs.getTimestamp(6));
                r.setGoods_name(rs.getString(7));
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

    public ObservableList<BeanPromotion> selectPromotion(int id) {
        Connection conn = null;
        ObservableList<BeanPromotion> result = FXCollections.observableArrayList();
        try {
            conn = DBUtil.getConnection();
            String sql = "select promotion_id, goods.goods_id, promotion_price, promotion_count, promotion_begin_date, promotion_end_date,goods_name from promotion,goods where promotion.goods_id=goods.goods_id and goods.goods_id=?";
            java.sql.PreparedStatement pst = conn.prepareStatement(sql);
            pst.setInt(1, id);
            java.sql.ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                BeanPromotion r = new BeanPromotion();
                r.setPromotion_id(rs.getInt(1));
                r.setGoods_id(rs.getInt(2));
                r.setPromotion_price(rs.getDouble(3));
                r.setPromotion_count(rs.getInt(4));
                r.setPromotion_begin_date(rs.getTimestamp(5));
                r.setPromotion_end_date(rs.getTimestamp(6));
                r.setGoods_name(rs.getString(7));
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

    public void add(BeanPromotion r) {
        if (r.getPromotion_price() <= 0) throw new BusinessException("促销价格不能小于0");
        if (r.getPromotion_count() <= 0) throw new BusinessException("促销数量必须大于0");
        if (r.getPromotion_end_date().before(r.getPromotion_begin_date())) throw new BusinessException("结束日期不能早于起始日期");
        Connection conn = null;
        try {
            conn = DBUtil.getConnection();
            String sql = "select goods_price,goods_count from goods where goods_id=?";
            java.sql.PreparedStatement pst = conn.prepareStatement(sql);
            pst.setInt(1, r.getGoods_id());
            java.sql.ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                if (rs.getDouble(1) <= r.getPromotion_price()) throw new BusinessException("促销金额必须小于商品原价");
                if (rs.getInt(2)<r.getPromotion_count()) throw new BusinessException("促销数量不得大于库存");
            } else {
                throw new BusinessException("商品不存在");
            }
            pst.close();
            sql="select * from promotion where (discount_begin_date<? and discount_end_date>=? or discount_end_date>? and discount_begin_date<=?) and goods_id=?";
            pst = conn.prepareStatement(sql);
            pst.setTimestamp(1,new Timestamp(r.getPromotion_end_date().getTime()));
            pst.setTimestamp(2,new Timestamp(r.getPromotion_end_date().getTime()));
            pst.setTimestamp(3,new Timestamp(r.getPromotion_begin_date().getTime()));
            pst.setTimestamp(4,new Timestamp(r.getPromotion_begin_date().getTime()));
            pst.setInt(5,r.getGoods_id());
            rs =pst.executeQuery();
            if (rs.next()) throw new BusinessException("该商品在该时间段已有促销");
            pst.close();
            sql = "insert into promotion(goods_id, promotion_price, promotion_count, promotion_begin_date, promotion_end_date)  VALUE (?,?,?,?,?)";
            pst = conn.prepareStatement(sql);
            pst.setInt(1, r.getGoods_id());
            pst.setDouble(2, r.getPromotion_price());
            pst.setInt(3, r.getPromotion_count());
            pst.setTimestamp(4, new Timestamp(r.getPromotion_begin_date().getTime()));
            pst.setTimestamp(5, new Timestamp(r.getPromotion_end_date().getTime()));
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

    public void delete(BeanPromotion r) {
        Connection conn = null;
        try {
            conn = DBUtil.getConnection();
            String sql = "delete from promotion where promotion_id=?";
            java.sql.PreparedStatement pst = conn.prepareStatement(sql);
            pst.setInt(1, r.getPromotion_id());
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
}
