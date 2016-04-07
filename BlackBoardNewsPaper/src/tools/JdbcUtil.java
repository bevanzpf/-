package tools;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.junit.Test;


public class JdbcUtil {
	@Test
	public static Connection getConnection() throws SQLException, ClassNotFoundException
	{
		String url = "jdbc:mysql://localhost:3306/�ڰ屨";
		String user = "root";
		String password = "ygjjzh147569";
		String driver = "com.mysql.jdbc.Driver";
		Class.forName(driver);
		
		return DriverManager.getConnection(url, user, password);
	}
	@Test
	public static void testgetConnection() throws ClassNotFoundException, SQLException
	{
		System.out.println(getConnection());
	}
	@Test
	public void testStatement() throws Exception{
		Connection conn = null;
		Statement statement = null;
		try{
			conn = getConnection();
			String sql = null;
			sql = "Update users SET name='tom' where id =1";
			statement = conn.createStatement();
			statement.executeUpdate(sql);
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			try{
				if(statement!=null)
					statement.close();
			}catch(Exception e){
				e.printStackTrace();
			}finally{
				if(conn != null)
					conn.close();
			}
		}
	}
//	public static void main(String[] args) throws ClassNotFoundException, SQLException {
//		String url = "jdbc:mysql://localhost:3306/�ڰ屨";
//		String user = "root";
//		String password = "ygjjzh147569";
//		String driver = "com.mysql.jdbc.Driver";
//		Connection conn = null;
//		Statement statement = null;
//		Class.forName(driver);
//		try{
//			conn = getConnection();
//			String sql = null;
//			sql = "Update users SET name='tom2' where id =1";
//			statement = conn.createStatement();
//			statement.executeUpdate(sql);
//		}catch(Exception e){
//			e.printStackTrace();
//		}finally{
//			try{
//				if(statement!=null)
//					statement.close();
//			}catch(Exception e){
//				e.printStackTrace();
//			}finally{
//				if(conn != null)
//					conn.close();
//			}
//		}
//		
//	}
	public static void releaseDB(ResultSet resultSet, Statement statement,Connection connection ) {
		if(resultSet != null){
			try{
				resultSet.close();
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		if(statement != null)
		{
			try{
				statement.close();
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		if(connection != null)
		{
			try{
				connection.close();
			}catch(Exception e){
				e.printStackTrace();
			}
		}
	}
}
