package manager;

import model.BeanAdmin;
import model.BeanUserInfo;
import util.BaseException;
import util.BusinessException;
import util.DBUtil;
import util.DbException;

import java.sql.Connection;
import java.sql.SQLException;

public class adminManager {
    public BeanAdmin login(String userId, String pwd) throws BaseException {
        BeanAdmin r = new BeanAdmin();
        Connection conn = null;
        try {
            conn = DBUtil.getConnection();
            String sql = "select * from admin where user_id=?";
            java.sql.PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1, userId);
            java.sql.ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                if (!rs.getString(3).equals(pwd)) throw new BusinessException("密码错误");
            } else throw new BusinessException("用户不存在");
            r.setAdmin_id(userId);
            r.setAdmin_name(rs.getString(2));
            r.setAdmin_pwd(pwd);
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
}
