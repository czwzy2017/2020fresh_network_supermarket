package manager;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.BeanAdmin;
import model.BeanGoodsProcurement;
import util.BaseException;
import util.BusinessException;
import util.DBUtil;
import util.DbException;

import java.sql.Connection;
import java.sql.SQLException;

public class ProcurementManager {
    public ObservableList<BeanGoodsProcurement> loadProcurement() {
        ObservableList<BeanGoodsProcurement> result = FXCollections.observableArrayList();
        Connection conn = null;
        try {
            conn = DBUtil.getConnection();
            String sql = "select procurement_id,admin_id,goods.goods_id,goods_name,procurement_count,procurement_status from goods_procurement,goods where admin_id=? and goods_procurement.goods_id=goods.goods_id";
            java.sql.PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1, BeanAdmin.currentLoginAdmin.getAdmin_id());
            java.sql.ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                BeanGoodsProcurement r = new BeanGoodsProcurement();
                r.setProcurement_id(rs.getInt(1));
                r.setAdmin_id(rs.getString(2));
                r.setGoods_id(rs.getInt(3));
                r.setGoods_name(rs.getString(4));
                r.setProcurement_count(rs.getInt(5));
                r.setProcurement_status(rs.getString(6));
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

    public void modify(BeanGoodsProcurement r, int flag) {
        int id = r.getProcurement_id();
        int goods = r.getGoods_id();
        int count = r.getProcurement_count();
        String status = r.getProcurement_status();
        if (count <= 0) throw new BusinessException("商品数量不能低于0");
        if (!"下单".equals(status) && !"在途".equals(status) && !"入库".equals(status))
            throw new BusinessException("采购状态请输入“下单/在途/入库”");
        Connection conn = null;
        try {
            conn = DBUtil.getConnection();
            String sql = "select * from goods where goods_id=?";
            java.sql.PreparedStatement pst = conn.prepareStatement(sql);
            pst.setInt(1, goods);
            java.sql.ResultSet rs = pst.executeQuery();
            if (!rs.next()) throw new BusinessException("商品不存在");
            rs.close();
            pst.close();
            sql = "update goods_procurement set goods_id=?,procurement_count=?,procurement_status=?where procurement_id=?";
            pst = conn.prepareStatement(sql);
            pst.setInt(1, goods);
            pst.setInt(2, count);
            pst.setString(3, status);
            pst.setInt(4, id);
            pst.execute();
            pst.close();
            if ("入库".equals(status) && flag == 1) {
                sql = "update goods set goods_count=goods_count+? where goods_id=?";
                pst = conn.prepareStatement(sql);
                pst.setInt(1, count);
                pst.setInt(2, goods);
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

    public void add(int goods, int count, String status) {
        if (!"下单".equals(status) && !"在途".equals(status) && !"入库".equals(status))
            throw new BusinessException("采购状态请输入“下单/在途/入库”");
        if (count <= 0) throw new BusinessException("商品数量必须大于零");
        Connection conn = null;
        try {
            conn = DBUtil.getConnection();
            String sql = "select * from goods where goods_id=?";
            java.sql.PreparedStatement pst = conn.prepareStatement(sql);
            pst.setInt(1, goods);
            java.sql.ResultSet rs = pst.executeQuery();
            if (!rs.next()) throw new BusinessException("商品不存在");
            rs.close();
            pst.close();
            sql = "insert into goods_procurement(admin_id, goods_id, procurement_count, procurement_status)  VALUE (?,?,?,?)";
            pst = conn.prepareStatement(sql);
            pst.setString(1, BeanAdmin.currentLoginAdmin.getAdmin_id());
            pst.setInt(2, goods);
            pst.setInt(3, count);
            pst.setString(4, status);
            pst.execute();
            pst.close();
            if ("入库".equals(status)) {
                sql = "update goods set goods_count=goods_count+? where goods_id=?";
                pst = conn.prepareStatement(sql);
                pst.setInt(1, count);
                pst.setInt(2, goods);
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

    public void delete(BeanGoodsProcurement r) {
        Connection conn = null;
        try {
            conn = DBUtil.getConnection();
            String sql = "delete from goods_procurement where procurement_id=?";
            java.sql.PreparedStatement pst = conn.prepareStatement(sql);
            pst.setInt(1, r.getProcurement_id());
            pst.execute();
            pst.close();
            if ("入库".equals(r.getProcurement_status())){
                sql="update goods set goods_count=goods_count-? where goods_id=?";
                pst=conn.prepareStatement(sql);
                pst.setInt(1,r.getProcurement_count());
                pst.setInt(2,r.getGoods_id());
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
