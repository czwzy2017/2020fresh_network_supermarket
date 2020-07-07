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
import java.util.ArrayList;
import java.util.List;

public class ProcurementManager {
    public ObservableList<BeanGoodsProcurement> loadProcurement() throws BaseException {
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
                r.setProcurement_id(rs.getString(1));
                r.setAdmin_id(rs.getString(2));
                r.setGoods_id(rs.getString(3));
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

    public void modify(BeanGoodsProcurement r) throws BaseException {
        String id = r.getProcurement_id();
        String goods = r.getGoods_id();
        int count = r.getProcurement_count();
        String status = r.getProcurement_status();
        if (!"下单".equals(status) && !"在途".equals(status) && !"入库".equals(status))
            throw new BusinessException("采购状态请输入“下单/在途/入库”");
        if ("".equals(goods)) throw new BusinessException("商品编号不能为空");
        if ("".equals(count)) throw new BusinessException("商品数量不能为空");
        Connection conn = null;
        try {
            conn = DBUtil.getConnection();
            String sql = "select * from goods where goods_id=?";
            java.sql.PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1, goods);
            java.sql.ResultSet rs = pst.executeQuery();
            if (!rs.next()) throw new BusinessException("商品不存在");
            rs.close();
            pst.close();
            sql = "update goods_procurement set goods_id=?,procurement_count=?,procurement_status=?where procurement_id=?";
            pst = conn.prepareStatement(sql);
            pst.setString(1, goods);
            pst.setInt(2, count);
            pst.setString(3, status);
            pst.setString(4, id);
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

    public void add(String goods,int count,String status) throws BaseException {
        if (!"下单".equals(status) && !"在途".equals(status) && !"入库".equals(status))
            throw new BusinessException("采购状态请输入“下单/在途/入库”");
        if ("".equals(goods)) throw new BusinessException("商品编号不能为空");
        if ("".equals(count)) throw new BusinessException("商品数量不能为空");
        Connection conn = null;
        try {
            conn = DBUtil.getConnection();
            String sql = "select * from goods where goods_id=?";
            java.sql.PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1, goods);
            java.sql.ResultSet rs = pst.executeQuery();
            if (!rs.next()) throw new BusinessException("商品不存在");
            pst.close();
            sql = "select count(*) from goods_procurement";
            java.sql.Statement st = conn.createStatement();
            rs=st.executeQuery(sql);
            int i=0;
            if (rs.next()) i=rs.getInt(1);
            sql = "insert into goods_procurement(procurement_id, admin_id, goods_id, procurement_count, procurement_status)  VALUE (?,?,?,?,?)";
            pst = conn.prepareStatement(sql);
            pst.setString(1, String.valueOf(i)+1);
            pst.setString(2, BeanAdmin.currentLoginAdmin.getAdmin_id());
            pst.setString(3,goods);
            pst.setInt(4,count);
            pst.setString(5,status);
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

    public void delete(BeanGoodsProcurement r) throws BaseException {
        Connection conn = null;
        try {
            conn = DBUtil.getConnection();
            String sql = "delete from goods_procurement where procurement_id=?";
            java.sql.PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1, r.getProcurement_id());
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
