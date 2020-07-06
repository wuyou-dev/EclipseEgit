package henu.utils;
import java.sql.*;
//22222 Hello 
//aaa
public class DbUtil {
	/**
	 * 锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷锟捷匡拷锟斤拷锟较拷锟斤拷锟斤拷锟斤拷菘锟経RL锟斤拷锟矫伙拷锟斤拷锟斤拷锟斤拷锟斤拷
	 */
	private static final String URL = "jdbc:mysql://localhost:3306/userdb?useUnicode=true&characterEncoding=UTF-8&useSSL=false&serverTimezone=Asia/Shanghai&zeroDateTimeBehavior=CONVERT_TO_NULL";
	private static final String USER = "root";
	private static final String PASSWORD = "654321";
	/**
	 * 锟斤拷锟斤拷JDBC锟斤拷囟锟斤拷锟�
	 */
	protected static Statement s=null;
	protected static ResultSet rs = null;
	protected static Connection conn = null;
	/**
	 * 锟斤拷锟斤拷锟斤拷锟捷匡拷锟斤拷锟斤拷
	 * @return conn
	 */
	public static synchronized Connection getConnection()
	{
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			conn = DriverManager.getConnection(URL, USER, PASSWORD);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return conn;
	}
	/**
	 * 执锟斤拷INSERT/UPDATE/DELETE SQL锟斤拷锟�
	 * @param sql SQL锟斤拷洌拷址锟斤拷锟斤拷锟斤拷锟�
	 * @return 执锟叫斤拷锟斤拷锟絠nt锟斤拷锟斤拷
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
	 * 执锟斤拷SELECT SQL锟斤拷锟�
	 * @param sql SQL锟斤拷洌拷址锟斤拷锟斤拷锟斤拷锟�
	 * @return ResultSet锟斤拷锟斤拷锟�
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
	 * 执锟叫讹拷态SQL锟斤拷锟�
	 * @param sql 锟斤拷锟叫诧拷锟斤拷锟侥讹拷态SQL锟斤拷洹� 
	 * @return 锟斤拷锟斤拷PreparedStatement锟斤拷锟斤拷
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
	 * 锟斤拷锟斤拷毓锟�
	 */
	public static void rollback() {
		try {
			getConnection().rollback();
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		
	}
	/**
	 * 锟截憋拷锟斤拷锟捷匡拷锟斤拷锟接讹拷锟斤拷
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
		"'锟斤拷锟斤拷员','女','1999-10-22','allen@henu.edu.cn')";
		executeUpdate(sql);
		close();
		*/
		//锟斤拷写SQL锟斤拷锟�
		String sql = "INSERT INTO tb_users(fd_username,fd_password,fd_usertype,fd_gender,fd_email," +
				"fd_birthdate, fd_introduction,fd_hobby) VALUES (?,?,?,?,?,?,?,?)";
		
		//执锟斤拷SQL
		PreparedStatement ps = DbUtil.executePreparedStatement(sql);
		try {
			ps.setString(1, "username");
			ps.setString(2, "password");
			ps.setString(3, "1");
			ps.setString(4, "锟斤拷");
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
