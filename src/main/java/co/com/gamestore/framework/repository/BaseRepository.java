package co.com.gamestore.framework.repository;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.sql.DataSource;

import co.com.gamestore.framework.util.Constants;
import co.com.gamestore.framework.util.PoolHelper;


public class BaseRepository {	
	
	SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
	
	public static final String DB_SOURCE_NAME = "GAME_STORE";
	
	/**
	 * @author Jonathan.Barrera
	 * @param dataSourceName
	 * @return a specific data source
	 */
	public static DataSource getDataSource( String dataSourceName ) {
		return PoolHelper.getPool(dataSourceName);
	}
	
	
	/**
	 * @author Jonathan.Barrera
	 * @param autoCommit
	 * @return the connection to the default data source with custom auto commit
	 * @throws SQLException
	 */
	public static Connection connect( Boolean autoCommit ) throws SQLException{
		return connect(DB_SOURCE_NAME,autoCommit);
	}
	
	/**
	 * @author Jonathan.Barrera
	 * @param dataSource
	 * @param autoCommit
	 * @return The connection to specific data source with custom auto commit
	 * @throws SQLException
	 */
	public static Connection connect( String dataSource, Boolean autoCommit ) throws SQLException{
		Connection c = null;
		DataSource pool = PoolHelper.getPool(dataSource);
		if (null == pool) {
			throw new SQLException("Could not find pool "+ dataSource);
		}
		
		c = pool.getConnection();
		if (null == c) {
			throw new SQLException("Without connections to dataBase");
		}
		c.setAutoCommit(autoCommit);
		return c;
	}
	/**
	 * Close a ResultSet that has been used previously
	 * @author Jonathan.Barrera
	 * @param rs
	 */
	public void closeRS( ResultSet rs ) {
		try {
			if (null != rs) {
				rs.close();
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Close a Statement that has been used previously
	 * @author Jonathan.Barrera
	 * @param stmt
	 */
	public void closeSTMT( Statement stmt ) {
		try {
			if (null != stmt) {
				stmt.close();
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Close a connection that has been used previously
	 * @author Jonathan.Barrera
	 * @param stmt
	 */
	public void closeC( Connection c ) {
		try {
			PoolHelper.closeConnection(c);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * close a result set, statement and connection in the correct order
	 * @author Jonathan.Barrera
	 * @param rs
	 * @param stmt
	 * @param c
	 */
	public void closeAll( ResultSet rs, Statement stmt, Connection c ) {
		closeRS(rs);
		closeSTMT(stmt);
		closeC(c);
	}
	
	/**
	 * Do a roll back transaction in Data base
	 * @author Jonathan.Barrera
	 * @param c
	 */
	public void rollback(Connection c) {
		try {
			if ( null != c ) {
				c.rollback();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 
	 * @author Jonathan.Barrera
	 * @param fileName
	 * @return a SQL QUERY Sentence
	 * @throws IOException
	 */
	protected String getQuery(String fileName) throws IOException{
		return getSQL("query/"+fileName);
	}
	
	/**
	 * @author Jonathan.Barrera
	 * @param fileName
	 * @return a SQL UPDATE Sentence
	 * @throws IOException
	 */
	protected String getUpdate(String fileName) throws IOException{
		return getSQL("update/"+fileName);
	}
	
	/**
	 * 
	 * @author Jonathan.Barrera
	 * @param fileName
	 * @return a SQL INSERT Sentence
	 * @throws IOException
	 */
	protected String getInsert(String fileName) throws IOException{
		return getSQL("insert/"+fileName);
	}
	
	/**
	 * 
	 * @author Jonathan.Barrera
	 * @param fileName
	 * @return a SQL sentence
	 * @throws IOException
	 */
	protected String getSQL( String fileName) throws IOException {
		try {
			return new String(Files.readAllBytes(Paths.get(System.getProperty("user.dir") + Constants.BASE_DB_FILES_PATH + fileName)));
		}catch (IOException e) {
			throw new IOException(String.format("An error has ocurred while reading the SQL file %s", fileName),e);
		}
	}
	
	
	/**
	 * Method to get like parameter from an object 
	 * @param param
	 * @return
	 */
	protected String getLikeParameter(Object param) {
		return "" + '%' + param + '%';
	}
	/**
	 * Method to get a string date with custom format;
	 * @param dateToFormat
	 * @return a string with custom format
	 */
	protected String getDateFormated(Date dateToFormat) {
		return formatter.format(dateToFormat);
	}
	
	/**
	 * Method to get Date from a string with custom format
	 * @param dateToFormat
	 * @return
	 * @throws ParseException
	 */
	protected Date getDateFormatedFromString(String dateToFormat) throws ParseException {
		return formatter.parse(dateToFormat);
	}
	
	/**
	 * @author Jonathan.Barrera
	 * @param sql
	 * @param params
	 * @return SQL Sentences with replaced parameters
	 */
	protected String replaceParameters(String sql, Object... params) {
		StringBuilder sb = new StringBuilder(sql);
		Boolean isFirst = true;
		Integer lastIndex = 0;
		Integer index = 0;
		for (Object param : params) {
			if (isFirst) {
				index = sb.indexOf("?");
			}else {
				index = sb.indexOf("?", lastIndex+1);
			}
			isFirst =false;
			if (index > 0) {
				String p = param instanceof String ? "\'"+ param + "\'" : (String) param;
				sb.replace(index, index+1,p);
			}
			lastIndex = index;
		}
		
		return sb.toString();
	}

}
