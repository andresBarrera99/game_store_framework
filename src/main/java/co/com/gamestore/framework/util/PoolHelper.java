package co.com.gamestore.framework.util;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.sql.DataSource;

import org.apache.commons.dbcp2.BasicDataSource;

public class PoolHelper {
	
	public static Map<String, DataSource> dataSources = new HashMap<String, DataSource>();
	
	private static DataSource createDataSource(String poolName, Properties poolProperties) {
		BasicDataSource ds = null;
		Properties prop = null == poolProperties ? PropertiesHelper.getProperties(Constants.DB_PROPERTIES) : poolProperties;
		if (prop.get(poolName+".driverClass") != null) {
			ds = new BasicDataSource();
			ds.setDriverClassName(prop.getProperty(poolName+".driverClass"));
			ds.setUrl(prop.getProperty(poolName+".url"));
			ds.setUsername(prop.getProperty(poolName+".username"));
			ds.setPassword(prop.getProperty(poolName+".password"));
			ds.setInitialSize(Integer.parseInt(prop.getProperty(poolName+".initial")));
			ds.setMaxIdle(Integer.parseInt(prop.getProperty(poolName+".maxActive")));
			ds.setMaxConnLifetimeMillis(Long.parseLong(prop.getProperty(poolName+".connectionTimeout")));
			ds.setTestOnBorrow(Boolean.valueOf(prop.getProperty(poolName+".testOnBorrow")).booleanValue());
			ds.setTestWhileIdle(Boolean.valueOf(prop.getProperty(poolName+".testWhileIdle")).booleanValue());
			ds.setValidationQuery(prop.getProperty(poolName+".validate.query"));
		}
		return ds;
	}
	
	
	public static DataSource getPool(String poolName) {
		synchronized (dataSources) {
			DataSource dataSource = null;
			try {
				dataSource = dataSources.get(poolName);
				if (null == dataSource) {
					try {
						dataSource =  createDataSource(poolName, null);
					} catch (Exception e) {
						e.printStackTrace();
					}
					
					if (null == dataSource) {
						throw new SQLException("Could not create datasource");
					}else {
						dataSources.put(poolName,dataSource);
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			return dataSource;
		}
	}
	
	
	public static void closeConnection(Connection c) {
		if (null != c) {
			try {
				c.setAutoCommit(true);
			} catch (Exception e) {
				e.printStackTrace();
			}
			try {
				c.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

}
