package kr.og.dgit.erp_setting.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import kr.or.dgit.db_connection.jdbc.DBConn;

public class ExecuteSql {
	private static final ExecuteSql instance = new ExecuteSql();
	
	public static ExecuteSql getInstance() {
		return instance;
	}

	private ExecuteSql() {}

	public void execSQL(String sql) {
		Connection conn = DBConn.getInstance().getConnection();
		try (PreparedStatement pstmt = conn.prepareStatement(sql);) {
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			System.err.printf("%s - %s%n", e.getErrorCode(), e.getMessage());
		}
	}
	
	public ResultSet execQuerySQL(String sql) throws SQLException {
		Connection conn = DBConn.getInstance().getConnection();
		PreparedStatement pstmt = null;
		pstmt = conn.prepareStatement(sql);
		return pstmt.executeQuery();
	}
}
