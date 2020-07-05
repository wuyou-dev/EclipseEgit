package henu.utils;

import java.sql.*;
import java.util.Properties;
import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.commons.dbcp2.BasicDataSourceFactory;
public class DbcpPool { 
		/**
		 * 声明JDBC相关对象
		 */
		protected static Statement s=null;
		protected static ResultSet rs = null;
		protected static Connection conn = null;
		private static BasicDataSource dataSource = null;

	    //初始化数据库连接池
	    public static void init() 
	    {
	        if (dataSource != null)
	        {
	            try
	            {
	                dataSource.close();
	            } catch (Exception e)
	            {
	                e.printStackTrace();
	            }
	            dataSource = null;
	        }
	        //使用Properties对象定义数据库连接池信息
	        try {
	            Properties p = new Properties();
	            p.setProperty("driverClassName", "com.mysql.jdbc.Driver");
	            p.setProperty("url", "jdbc:mysql://localhost:3306/userdb");
	            p.setProperty("username", "root");
	            p.setProperty("password", "123");
	            p.setProperty("maxActive", "30");
	            p.setProperty("maxIdle", "10");
	            p.setProperty("maxWait", "1000");
	            p.setProperty("removeAbandoned", "false");
	            p.setProperty("removeAbandonedTimeout", "120");
	            p.setProperty("testOnBorrow", "true");
	            p.setProperty("logAbandoned", "true");       
	            //以指定信息创建数据源
	            dataSource = (BasicDataSource) BasicDataSourceFactory.createDataSource(p);
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	    }
	    //从连接池中获取连接
	    public static synchronized Connection getConnection() throws  SQLException {
	        if (dataSource == null) {
	            init();
	        }
	        Connection conn = null;
	        if (dataSource != null) {
	            conn = dataSource.getConnection();
	        }
	        return conn;
	    }
		/**
		 * 执行INSERT/UPDATE/DELETE SQL语句
		 * @param sql SQL语句，字符串类型
		 * @return 执行结果，int类型
		 */
		public static int executeUpdate(String sql)
		{
			int result = 0;
			try {
				s = getConnection().createStatement();
				result = s.executeUpdate(sql);
			} catch (SQLException e) {
				
				e.printStackTrace();
			}
			return result;
		}
		/**
		 * 执行SELECT SQL语句
		 * @param sql SQL语句，字符串类型
		 * @return ResultSet结果集
		 */
		public static ResultSet executeQuery(String sql)
		{
			
			try {
				s = getConnection().createStatement();
				rs = s.executeQuery(sql);
			} catch (SQLException e) {
				
				e.printStackTrace();
			}
			return rs;
		}
		/**
		 * 执行动态SQL语句
		 * @param sql 含有参数的动态SQL语句。 
		 * @return 返回PreparedStatement对象
		 */
		public static PreparedStatement executePreparedStatement(String sql)
		{
			PreparedStatement ps = null;
			try
			{
				ps = getConnection().prepareStatement(sql);
			}catch(Exception e)
			{
				e.printStackTrace();
			}
			return ps;
		}
		/**
		 * 事务回滚
		 */
		public static void rollback() {
			try {
				getConnection().rollback();
			} catch (SQLException e) {
				
				e.printStackTrace();
			}
			
		}
		/**
		 * 关闭数据库连接对象
		 */
		public static void close()
		{
			try
			{
				if(rs!=null)
					rs.close();
				if(s!= null)
					s.close();
				if(conn!=null)
					conn.close();
				
			}catch(SQLException e)
			{
				e.printStackTrace();
			}
		}
		public static void main(String[] args) {
			/*
			String sql = "INSERT INTO tb_users(fd_username,fd_password,fd_usertype,fd_gender,fd_birthdate,fd_email) VALUES ('Wangli','aWeY92,zeP', " +
			"'管理员','女','1999-10-22','allen@henu.edu.cn')";
			executeUpdate(sql);
			close();
			*/
			//编写SQL语句
			String sql = "INSERT INTO tb_users(fd_username,fd_password,fd_usertype,fd_gender,fd_email," +
					"fd_birthdate, fd_introduction,fd_hobby) VALUES (?,?,?,?,?,?,?,?)";
			
			//执行SQL
			PreparedStatement ps = DbUtil.executePreparedStatement(sql);
			try {
				ps.setString(1, "liang");
				ps.setString(2, "password");
				ps.setString(3, "1");
				ps.setString(4, "男");
				ps.setString(5, "email");
				ps.setString(6, "birthdate");
				ps.setString(7, "introduction");
				ps.setString(8, "hobby");
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			
			int result = 0;
			try {
				result = ps.executeUpdate();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				ps.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
}
