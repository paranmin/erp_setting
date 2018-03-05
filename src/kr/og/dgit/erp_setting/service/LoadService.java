package kr.og.dgit.erp_setting.service;

import java.util.Properties;

import kr.og.dgit.erp_setting.dao.ExecuteSql;
import kr.or.dgit.db_connection.jdbc.LoadProperties;

public class LoadService implements DaoService {
	private static final LoadService instance = new LoadService();
	
	public static LoadService getInstance() {
		return instance;
	}
	
	private LoadService() {}

	@Override
	public void service() {
		Properties properties = LoadProperties.getInstance().getProperties();
		
		ExecuteSql.getInstance().execSQL("use " + properties.getProperty("dbname"));
		ExecuteSql.getInstance().execSQL("set foreign_key_checks=0");
		
		
		String[] tables = properties.getProperty("tables").split(",");
		for (String tblName : tables) {
			String path = String.format("%s\\DataFiles\\%s.csv", System.getProperty("user.dir"), tblName);
			String sql = String.format("load data local infile '%s' into table %s character set 'euckr' fields terminated by ',';", path, tblName);
			sql = sql.replace("\\", "/");
			ExecuteSql.getInstance().execSQL(sql);
		}
		ExecuteSql.getInstance().execSQL("set foreign_key_checks=1");
	}

}
