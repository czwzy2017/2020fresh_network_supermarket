package manager;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.BeanFreshCategory;
import model.BeanGoods;
import util.BusinessException;
import util.DBUtil;
import util.DbException;

import java.sql.Connection;
import java.sql.SQLException;

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

    public BeanFreshCategory selectCategory(int id){
        Connection conn = null;
        BeanFreshCategory r = new BeanFreshCategory();
        try {
            conn = DBUtil.getConnection();
            String sql = "select * from fresh_category where category_id=?";
            java.sql.PreparedStatement pst = conn.prepareStatement(sql);
            pst.setInt(1,id);
            java.sql.ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                r.setCategory_id(rs.getInt(1));
                r.setCategory_name(rs.getString(2));
                r.setCategory_description(rs.getString(3));
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
        String name=r.getCategory_name();
        if ("".equals(name)) throw new BusinessException("类别名称不能为空");
        String description=r.getCategory_description();
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

    public ObservableList<BeanGoods> loadGoods(int id) {
        ObservableList<BeanGoods> result = FXCollections.observableArrayList();
        Connection conn = null;
        try {
            conn = DBUtil.getConnection();
            String sql = "select * from goods where category_id=?";
            java.sql.PreparedStatement pst = conn.prepareStatement(sql);
            pst.setInt(1,id);
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

    public BeanGoods selectGoods(int id){
        Connection conn = null;
        BeanGoods r = new BeanGoods();
        try {
            conn = DBUtil.getConnection();
            String sql = "select * from goods where goods_id=?";
            java.sql.PreparedStatement pst = conn.prepareStatement(sql);
            pst.setInt(1,id);
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
            }
            pst.close();
            sql="select category_name from fresh_category where category_id=?";
            pst = conn.prepareStatement(sql);
            pst.setInt(1,r.getCategory_id());
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

    public void modifyGoods(BeanGoods r) {
        Connection conn = null;
        if ("".equals(r.getGoods_name())) throw new BusinessException("商品名称不能为空");
        if (r.getGoods_price()<=0 || r.getGoods_vip_price()<=0) throw new BusinessException("商品价格必须大于0");
        if ("".equals(r.getGoods_size())) throw new BusinessException("商品规格不能为空");
        if (r.getGoods_count()<0) throw new BusinessException("商品数量不能小于0");
        try {
            conn = DBUtil.getConnection();
            String sql = "update goods set goods_name=?,goods_price=?,goods_vip_price=?,goods_count=?,goods_size=?,goods_details=? where goods_id=?";
            java.sql.PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1, r.getGoods_name());
            pst.setDouble(2, r.getGoods_price());
            pst.setDouble(3, r.getGoods_vip_price());
            pst.setInt(4,r.getGoods_count());
            pst.setString(5,r.getGoods_size());
            pst.setString(6,r.getGoods_detail());
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

    public void addGoods(int category_id,String name, Double price,int count,Double vip,String size,String detail) {
        Connection conn = null;
        if ("".equals(name)) throw new BusinessException("商品名称不能为空");
        if (price<=0 || vip<=0) throw new BusinessException("商品价格必须大于0");
        if ("".equals(size)) throw new BusinessException("商品规格不能为空");
        if (count<0) throw new BusinessException("商品数量不能小于0");
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
            pst.setDouble(3,price);
            pst.setDouble(4,vip);
            pst.setInt(5,count);
            pst.setString(6,size);
            pst.setString(7,detail);
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
