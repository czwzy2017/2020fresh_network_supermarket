package manager;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.BeanFreshCategory;
import model.BeanGoods;
import model.BeanUser;
import util.BaseException;
import util.BusinessException;
import util.DBUtil;
import util.DbException;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;

public class FreshManager {
    public ObservableList<BeanFreshCategory> loadCategory() {
        ObservableList<BeanFreshCategory> result = FXCollections.observableArrayList();
        Connection conn = null;
        try {
            conn = DBUtil.getConnection();
            String sql = "select * from fresh_category";
            java.sql.Statement st = conn.createStatement();
            java.sql.ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                BeanFreshCategory r = new BeanFreshCategory();
                r.setCategory_id(rs.getInt(1));
                r.setCategory_name(rs.getString(2));
                r.setCategory_description(rs.getString(3));
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

    public ObservableList<String> loadCategoryName() {
        ObservableList<String> result = FXCollections.observableArrayList();
        Connection conn = null;
        try {
            conn = DBUtil.getConnection();
            String sql = "select category_name from fresh_category";
            java.sql.Statement st = conn.createStatement();
            java.sql.ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                result.add(rs.getString(1));
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

    public BeanFreshCategory selectCategory(int id) {
        Connection conn = null;
        BeanFreshCategory r = new BeanFreshCategory();
        try {
            conn = DBUtil.getConnection();
            String sql = "select * from fresh_category where category_id=?";
            java.sql.PreparedStatement pst = conn.prepareStatement(sql);
            pst.setInt(1, id);
            java.sql.ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                r.setCategory_id(rs.getInt(1));
                r.setCategory_name(rs.getString(2));
                r.setCategory_description(rs.getString(3));
            } else {
                throw new BusinessException("生鲜类别不存在");
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
        return r;
    }

    public void modifyCategory(BeanFreshCategory r) {
        int id = r.getCategory_id();
        String name = r.getCategory_name().trim();
        if ("".equals(name)) throw new BusinessException("类别名称不能为空");
        String description = r.getCategory_description();
        Connection conn = null;
        try {
            conn = DBUtil.getConnection();
            String sql = "update fresh_category set category_name=?,category_description=? where category_id=?";
            java.sql.PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1, name);
            pst.setString(2, description);
            pst.setInt(3, id);
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

    public void addCategory(String name, String description) {
        Connection conn = null;
        if ("".equals(name)) throw new BusinessException("类别名称不能为空");
        try {
            conn = DBUtil.getConnection();
            String sql = "insert into fresh_category(category_name,category_description)  VALUE (?,?)";
            java.sql.PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1, name);
            pst.setString(2, description);
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

    public void deleteCategory(BeanFreshCategory r) {
        Connection conn = null;
        try {
            conn = DBUtil.getConnection();
            String sql = "delete from fresh_category where category_id=?";
            java.sql.PreparedStatement pst = conn.prepareStatement(sql);
            pst.setInt(1, r.getCategory_id());
            pst.execute();
            pst.close();
        } catch (SQLException e) {
            throw new BusinessException("该分类下存在商品，不可删除");
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

    public ObservableList<BeanGoods> loadGoods(int id) {
        ObservableList<BeanGoods> result = FXCollections.observableArrayList();
        Connection conn = null;
        try {
            conn = DBUtil.getConnection();
            String sql = "select * from goods where category_id=?";
            java.sql.PreparedStatement pst = conn.prepareStatement(sql);
            pst.setInt(1, id);
            java.sql.ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                BeanGoods r = new BeanGoods();
                r.setGoods_id(rs.getInt(1));
                r.setCategory_id(rs.getInt(2));
                r.setGoods_name(rs.getString(3));
                r.setGoods_price(rs.getDouble(4));
                r.setGoods_vip_price(rs.getDouble(5));
                r.setGoods_count(rs.getInt(6));
                r.setGoods_size(rs.getString(7));
                r.setGoods_detail(rs.getString(8));
                r.setVip_price_string();
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

    public ObservableList<BeanGoods> loadStoreGoods(int id) {
        ObservableList<BeanGoods> result = FXCollections.observableArrayList();
        Connection conn = null;
        try {
            conn = DBUtil.getConnection();
            String sql = "select * from goods where category_id=?";
            java.sql.PreparedStatement pst = conn.prepareStatement(sql);
            pst.setInt(1, id);
            java.sql.ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                BeanGoods r = new BeanGoods();
                r.setGoods_id(rs.getInt(1));
                r.setCategory_id(rs.getInt(2));
                r.setGoods_name(rs.getString(3));
                r.setGoods_price(rs.getDouble(4));
                r.setGoods_vip_price(rs.getDouble(5));
                r.setGoods_count(rs.getInt(6));
                r.setGoods_size(rs.getString(7));
                r.setGoods_detail(rs.getString(8));
                r.setVip_price_string();
                sql = "select promotion_price,promotion_count from promotion where goods_id=? and (? between promotion_begin_date and promotion_end_date)";
                pst = conn.prepareStatement(sql);
                pst.setInt(1, r.getGoods_id());
                pst.setTimestamp(2, new Timestamp(System.currentTimeMillis()));
                java.sql.ResultSet rst = pst.executeQuery();
                if (rst.next()) {
                    r.setGoods_promotion(String.valueOf(rst.getDouble(1)));
                    r.setPromotion_count(rst.getInt(2));
                }
                if (r.getGoods_count()>0) result.add(r);
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

    public ObservableList<BeanGoods> loadAllGoods() {
        ObservableList<BeanGoods> result = FXCollections.observableArrayList();
        Connection conn = null;
        try {
            conn = DBUtil.getConnection();
            String sql = "select * from goods";
            java.sql.Statement st = conn.createStatement();
            java.sql.ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                BeanGoods r = new BeanGoods();
                r.setGoods_id(rs.getInt(1));
                r.setCategory_id(rs.getInt(2));
                r.setGoods_name(rs.getString(3));
                r.setGoods_price(rs.getDouble(4));
                r.setGoods_vip_price(rs.getDouble(5));
                r.setGoods_count(rs.getInt(6));
                r.setGoods_size(rs.getString(7));
                r.setGoods_detail(rs.getString(8));
                r.setVip_price_string();
                sql = "select promotion_price,promotion_count from promotion where goods_id=? and (? between promotion_begin_date and promotion_end_date)";
                java.sql.PreparedStatement pst = conn.prepareStatement(sql);
                pst.setInt(1, r.getGoods_id());
                pst.setTimestamp(2, new Timestamp(System.currentTimeMillis()));
                java.sql.ResultSet rst = pst.executeQuery();
                if (rst.next()) {
                    r.setGoods_promotion(String.valueOf(rst.getDouble(1)));
                    r.setPromotion_count(rst.getInt(2));
                }
                if (r.getGoods_count()>0) result.add(r);
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

    public ObservableList<BeanGoods> loadCommend() {
        ObservableList<BeanGoods> result = FXCollections.observableArrayList();
        Connection conn = null;
        try {
            conn = DBUtil.getConnection();
            String sql = "select distinct goods.goods_id,  goods.category_id,  goods.goods_name,  goods.goods_price,  goods.goods_vip_price,  goods.goods_count,  goods.goods_size,  goods.goods_details from goods,orders_detail,goods_orders where goods.goods_id=orders_detail.goods_id and goods_orders.orders_id=orders_detail.orders_id and user_id=?";
            java.sql.PreparedStatement pst = conn.prepareStatement(sql);
            pst.setInt(1, BeanUser.currentLoginUser.getUser_id());
            java.sql.ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                BeanGoods r = new BeanGoods();
                r.setGoods_id(rs.getInt(1));
                r.setCategory_id(rs.getInt(2));
                r.setGoods_name(rs.getString(3));
                r.setGoods_price(rs.getDouble(4));
                r.setGoods_vip_price(rs.getDouble(5));
                r.setGoods_count(rs.getInt(6));
                r.setGoods_size(rs.getString(7));
                r.setGoods_detail(rs.getString(8));
                r.setVip_price_string();
                sql = "select promotion_price,promotion_count from promotion where goods_id=? and (? between promotion_begin_date and promotion_end_date)";
                pst = conn.prepareStatement(sql);
                pst.setInt(1, r.getGoods_id());
                pst.setTimestamp(2, new Timestamp(System.currentTimeMillis()));
                java.sql.ResultSet rst = pst.executeQuery();
                if (rst.next()) {
                    r.setGoods_promotion(String.valueOf(rst.getDouble(1)));
                    r.setPromotion_count(rst.getInt(2));
                }
                if (r.getGoods_count()>0) result.add(r);
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

    public BeanGoods selectGoods(int id) {
        Connection conn = null;
        BeanGoods r = new BeanGoods();
        try {
            conn = DBUtil.getConnection();
            String sql = "select * from goods where goods_id=?";
            java.sql.PreparedStatement pst = conn.prepareStatement(sql);
            pst.setInt(1, id);
            java.sql.ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                r.setGoods_id(rs.getInt(1));
                r.setCategory_id(rs.getInt(2));
                r.setGoods_name(rs.getString(3));
                r.setGoods_price(rs.getDouble(4));
                r.setGoods_vip_price(rs.getDouble(5));
                r.setGoods_count(rs.getInt(6));
                r.setGoods_size(rs.getString(7));
                r.setGoods_detail(rs.getString(8));
                r.setVip_price_string();
            } else {
                throw new BusinessException("商品不存在");
            }
            pst.close();
            sql = "select category_name from fresh_category where category_id=?";
            pst = conn.prepareStatement(sql);
            pst.setInt(1, r.getCategory_id());
            rs = pst.executeQuery();
            if (rs.next()) r.setCategory_name(rs.getString(1));
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
        return r;
    }

    public  ObservableList<BeanGoods> selectGoods(String name) {
        ObservableList<BeanGoods> result = FXCollections.observableArrayList();
        Connection conn = null;
        try {
            conn = DBUtil.getConnection();
            String sql = "select * from goods where goods_name like ?";
            java.sql.PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1, "%"+name+"%");
            java.sql.ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                BeanGoods r = new BeanGoods();
                r.setGoods_id(rs.getInt(1));
                r.setCategory_id(rs.getInt(2));
                r.setGoods_name(rs.getString(3));
                r.setGoods_price(rs.getDouble(4));
                r.setGoods_vip_price(rs.getDouble(5));
                r.setGoods_count(rs.getInt(6));
                r.setGoods_size(rs.getString(7));
                r.setGoods_detail(rs.getString(8));
                r.setVip_price_string();
                sql = "select promotion_price,promotion_count from promotion where goods_id=? and (? between promotion_begin_date and promotion_end_date)";
                pst = conn.prepareStatement(sql);
                pst.setInt(1, r.getGoods_id());
                pst.setTimestamp(2, new Timestamp(System.currentTimeMillis()));
                java.sql.ResultSet rst = pst.executeQuery();
                if (rst.next()) {
                    r.setGoods_promotion(String.valueOf(rst.getDouble(1)));
                    r.setPromotion_count(rst.getInt(2));
                }
                if (r.getGoods_count()>0) result.add(r);
            }
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
        return result;
    }

    public void modifyGoods(BeanGoods r) {
        Connection conn = null;
        double vip = 0;
        if ("".equals(r.getVip_price_string())) vip = -1;
        else {
            try {
                vip = Double.valueOf(r.getVip_price_string().trim());
            } catch (Exception e) {
                throw new BusinessException("会员价必须为实数");
            }
            if (vip <= 0) throw new BusinessException("商品价格必须大于0");
            if (r.getGoods_price() < vip) throw new BusinessException("会员价不得高于非会员价");
        }
        if ("".equals(r.getGoods_name().trim())) throw new BusinessException("商品名称不能为空");
        if (r.getGoods_price() <= 0) throw new BusinessException("商品价格必须大于0");
        if (r.getGoods_count() < 0) throw new BusinessException("商品数量不能小于0");
        try {
            conn = DBUtil.getConnection();
            String sql = "update goods set goods_name=?,goods_price=?,goods_vip_price=?,goods_count=?,goods_size=?,goods_details=? where goods_id=?";
            java.sql.PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1, r.getGoods_name());
            pst.setDouble(2, r.getGoods_price());
            if (vip == -1) pst.setString(3, null);
            else pst.setDouble(3, vip);
            pst.setInt(4, r.getGoods_count());
            pst.setString(5, r.getGoods_size());
            pst.setString(6, r.getGoods_detail());
            pst.setInt(7, r.getGoods_id());
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

    public void addGoods(int category_id, String name, Double price, int count, String vip_price, String size, String detail) {
        Connection conn = null;
        double vip = 0;
        if ("".equals(vip_price)) vip = -1;
        else {
            try {
                vip = Double.valueOf(vip_price.trim());
            } catch (Exception e) {
                throw new BusinessException("会员价必须为实数");
            }
            if (vip <= 0) throw new BusinessException("商品价格必须大于0");
            if (price < vip) throw new BusinessException("会员价不得高于非会员价");
        }
        if ("".equals(name)) throw new BusinessException("商品名称不能为空");
        if (price <= 0) throw new BusinessException("商品价格必须大于0");
        if (count < 0) throw new BusinessException("商品数量不能小于0");
        try {
            conn = DBUtil.getConnection();
            String sql = "select * from fresh_category where category_id=?";
            java.sql.PreparedStatement pst = conn.prepareStatement(sql);
            pst.setInt(1, category_id);
            java.sql.ResultSet rs = pst.executeQuery();
            if (!rs.next()) throw new BusinessException("生鲜类别不存在");
            rs.close();
            pst.close();
            sql = "insert into goods(category_id, goods_name, goods_price, goods_vip_price, goods_count, goods_size, goods_details)   VALUE (?,?,?,?,?,?,?)";
            pst = conn.prepareStatement(sql);
            pst.setInt(1, category_id);
            pst.setString(2, name);
            pst.setDouble(3, price);
            if (vip == -1) pst.setString(4, null);
            else pst.setDouble(4, vip);
            pst.setInt(5, count);
            pst.setString(6, size);
            pst.setString(7, detail);
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

    public void deleteGoods(BeanGoods r) {
        Connection conn = null;
        try {
            conn = DBUtil.getConnection();
            String sql = "delete from goods where goods_id=?";
            java.sql.PreparedStatement pst = conn.prepareStatement(sql);
            pst.setInt(1, r.getGoods_id());
            pst.execute();
            pst.close();
        } catch (SQLException e) {
            throw new BusinessException("该商品曾被引用，不可删除");
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
