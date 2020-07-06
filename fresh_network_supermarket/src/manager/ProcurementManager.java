package manager;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.BeanAdmin;
import model.BeanGoodsProcurement;
import util.BaseException;
import util.DBUtil;
import util.DbException;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProcurementManager {
    public ObservableList<BeanGoodsProcurement> loadProcurement() throws BaseException {
        ObservableList<BeanGoodsProcurement> result =  FXCollections.observableArrayList();
        Connection conn = null;
        try {
            conn = DBUtil.getConnection();
            String sql = "select * from goods_procurement where admin_id=?";
            java.sql.PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1, BeanAdmin.currentLoginAdmin.getAdmin_id());
            java.sql.ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                BeanGoodsProcurement r=new BeanGoodsProcurement();
                r.setProcurement_id(rs.getString(1));
                r.setAdmin_id(rs.getString(2));
                r.setGoods_id(rs.getString(3));
                r.setProcurement_count(rs.getInt(4));
                r.setProcurement_status(rs.getString(5));
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
}
