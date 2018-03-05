package kr.og.dgit.erp_setting.service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

import kr.og.dgit.erp_setting.dao.ExecuteSql;
import kr.or.dgit.db_connection.jdbc.LoadProperties;

public class BackUpService implements DaoService {
	private static final BackUpService instance = new BackUpService();
	
	public static BackUpService getInstance() {
		return instance;
	}
	
	private BackUpService() {}

	@Override
	public void service() {
		Properties properties = LoadProperties.getInstance().getProperties();
		
		ExecuteSql.getInstance().execSQL("use " + properties.getProperty("dbname"));
		
		checkBackupDir();
		
		String[] tables = properties.getProperty("tables").toString().split(",");
		for (String tblName : tables) {
			String sql = String.format("select * from %s;", tblName);
			exportData(sql, tblName);
		}
	}

	private void exportData(String sql, String tblName) {
		try {
			ResultSet rs = ExecuteSql.getInstance().execQuerySQL(sql);
			int columnCnt = rs.getMetaData().getColumnCount();
			StringBuilder sb = new StringBuilder();
			while(rs.next()) {
				for (int i = 1; i <= columnCnt; i++) {
					sb.append(rs.getObject(i) + ",");
				}
				sb.replace(sb.length()-1, sb.length(), "");
				sb.append("\r\n");
			}
			writeBackupFile(sb.toString(), tblName);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private void writeBackupFile(String result, String tblName) {
		String resPath = System.getProperty("user.dir") + "\\BackupFiles\\" + tblName + ".csv";
		resPath = resPath.replace("\\", "/");
		
		try (OutputStreamWriter osw = new OutputStreamWriter(new FileOutputStream(resPath), "euc-kr");) {
			osw.write(result);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void checkBackupDir() {
		File backupDir = new File(System.getProperty("user.dir") + "\\BackupFiles");
		if (backupDir.exists()) {
			for (File file : backupDir.listFiles()) {
				file.delete();
				System.out.printf("%s Delete 성공!%n", file.getName());
			}
		} else {
			backupDir.mkdir();
			System.out.printf("%s Create 성공!%n", backupDir.getName());
		}
	}

}
