package manager;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.BeanCookbook;
import model.Commend;
import util.BusinessException;
import util.DBUtil;
import util.DbException;

import java.sql.Connection;
import java.sql.SQLException;

public class CookbookManager {
    public ObservableList<BeanCookbook> loadCookbook() {
        ObservableList<BeanCookbook> result = FXCollections.observableArrayList();
        Connection conn = null;
        try {
            conn = DBUtil.getConnection();
            String sql = "select * from cookbook";
            java.sql.Statement st = conn.createStatement();
            java.sql.ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                BeanCookbook r = new BeanCookbook();
                r.setCookbook_id(rs.getInt(1));
                r.setCookbook_name(rs.getString(2));
                r.setCookbook_ingredient(rs.getString(3));
                r.setCookbook_step(rs.getString(4));
                r.setCookbook_image(rs.getString(5));
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

    public BeanCookbook selectCookbook(int id) {
        Connection conn = null;
        BeanCookbook r = new BeanCookbook();
        try {
            conn = DBUtil.getConnection();
            String sql = "select * from cookbook where cookbook_id=?";
            java.sql.PreparedStatement pst = conn.prepareStatement(sql);
            pst.setInt(1, id);
            java.sql.ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                r.setCookbook_id(rs.getInt(1));
                r.setCookbook_name(rs.getString(2));
                r.setCookbook_ingredient(rs.getString(3));
                r.setCookbook_step(rs.getString(4));
//                r.setCookbook_image(rs.getString(5));
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

    public void modifyCookbooks(BeanCookbook r) {
        if ("".equals(r.getCookbook_name())) throw new BusinessException("菜谱名称不能为空");
        Connection conn = null;
        try {
            conn = DBUtil.getConnection();
            String sql = "update cookbook set cookbook_name=?,cookbook_ingredient=?,cookbook_step=? where cookbook_id=?";
            java.sql.PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1, r.getCookbook_name());
            pst.setString(2, r.getCookbook_ingredient());
            pst.setString(3, r.getCookbook_step());
            pst.setInt(4,r.getCookbook_id());
//            pst.setString(4,r.getCookbook_image());
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

    public void addCookbook(String name, String ingredient, String step) {
        Connection conn = null;
        if ("".equals(name)) throw new BusinessException("菜谱名称不能为空");
        try {
            conn = DBUtil.getConnection();
            String sql = "insert into cookbook(cookbook_name, cookbook_ingredient, cookbook_step)  VALUE (?,?,?)";
            java.sql.PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1, name);
            pst.setString(2, ingredient);
            pst.setString(3, step);
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

    public void deleteCookbook(BeanCookbook r) {
        Connection conn = null;
        try {
            conn = DBUtil.getConnection();
            String sql = "delete from cookbook where cookbook_id=?";
            java.sql.PreparedStatement pst = conn.prepareStatement(sql);
            pst.setInt(1, r.getCookbook_id());
            pst.execute();
            pst.close();
        } catch (SQLException e) {
            throw new BusinessException("该菜谱下存在推荐商品，不可删除");
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

    public ObservableList<Commend> loadGoods(int id) {
        ObservableList<Commend> result = FXCollections.observableArrayList();
        Connection conn = null;
        try {
            conn = DBUtil.getConnection();
            String sql = "select goods.goods_id,goods_name,commend_description from commend,goods where cookbook_id=? and goods.goods_id=commend.goods_id";
            java.sql.PreparedStatement pst = conn.prepareStatement(sql);
            pst.setInt(1, id);
            java.sql.ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                Commend r = new Commend();
                r.setGoods_id(rs.getInt(1));
                r.setGoods_name(rs.getString(2));
                r.setCommend_description(rs.getString(3));
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

    public void modifyCommend(Commend r) {
        Connection conn = null;
        if ("".equals(r.getGoods_name())) throw new BusinessException("商品名称不能为空");
        try {
            conn = DBUtil.getConnection();
            String sql = "select * from goods where goods_id=?";
            java.sql.PreparedStatement pst = conn.prepareStatement(sql);
            pst.setInt(1, r.getGoods_id());
            java.sql.ResultSet rs = pst.executeQuery();
            if (!rs.next()) throw new BusinessException("商品不存在");
            rs.close();
            pst.close();
            sql = "update commend set commend_description=? where goods_id=? and cookbook_id=?";
            pst = conn.prepareStatement(sql);
            pst.setString(1, r.getCommend_description());
            pst.setInt(2, r.getGoods_id());
            pst.setInt(3, r.getCookbook_id());
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

    public void addGoods(int goods_id, int cookbook_id) {
        Connection conn = null;
        try {
            conn = DBUtil.getConnection();
            String sql = "select * from goods where goods_id=?";
            java.sql.PreparedStatement pst = conn.prepareStatement(sql);
            pst.setInt(1, goods_id);
            java.sql.ResultSet rs = pst.executeQuery();
            if (!rs.next()) throw new BusinessException("商品不存在");
            pst.close();
            sql = "select * from commend where goods_id=? and cookbook_id=?";
            pst = conn.prepareStatement(sql);
            pst.setInt(1, goods_id);
            pst.setInt(2, cookbook_id);
            rs=pst.executeQuery();
            if (rs.next()) throw new BusinessException("商品已存在菜谱推荐中");
            sql = "insert into commend(goods_id, cookbook_id) VALUE (?,?)";
            pst = conn.prepareStatement(sql);
            pst.setInt(1, goods_id);
            pst.setInt(2, cookbook_id);
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

    public void deleteGoods(Commend r) {
        Connection conn = null;
        try {
            conn = DBUtil.getConnection();
            String sql = "delete from commend where goods_id=? and cookbook_id=?";
            java.sql.PreparedStatement pst = conn.prepareStatement(sql);
            pst.setInt(1, r.getGoods_id());
            pst.setInt(2, r.getCookbook_id());
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
