package util;
import com.mchange.v2.c3p0.ComboPooledDataSource;

import java.beans.PropertyVetoException;
import java.sql.Connection;
import java.sql.SQLException;

public class DBUtil {
	private static final String jdbcUrl = "jdbc:mysql://localhost:3306/fns?serverTimezone=Asia/Shanghai&useUnicode=true&characterEncoding=utf8";
	private static final String dbUser = "root";
	private static final String dbPwd = "12345678";
	private static ComboPooledDataSource dataSource = null;

	static {
		dataSource = new ComboPooledDataSource();
		dataSource.setUser(dbUser);
		dataSource.setPassword(dbPwd);
		dataSource.setJdbcUrl(jdbcUrl);
		try {
			dataSource.setDriverClass("com.mysql.cj.jdbc.Driver");
		} catch (PropertyVetoException e) {
			e.printStackTrace();
		}
		dataSource.setInitialPoolSize(2);
		dataSource.setMinPoolSize(1);
		dataSource.setMaxPoolSize(10);
		dataSource.setMaxStatements(50);
		dataSource.setMaxIdleTime(60);
	}

	public static synchronized Connection getConnection() {
		try {
			return dataSource.getConnection();
		} catch (SQLException e) {
			throw new RuntimeException("数据库连接失败", e);
		}
	}
}
