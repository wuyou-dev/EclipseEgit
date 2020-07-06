package henu.utils;
import java.sql.*;
//22222 Hello 
//bbb+aaa
public class DbUtil {
	/**
	 * 閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓鎹峰尅鎷烽敓鏂ゆ嫹閿熻緝顫嫹閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷疯彉閿熺祵RL閿熸枻鎷烽敓鐭紮鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷�
	 */
	private static final String URL = "jdbc:mysql://localhost:3306/userdb?useUnicode=true&characterEncoding=UTF-8&useSSL=false&serverTimezone=Asia/Shanghai&zeroDateTimeBehavior=CONVERT_TO_NULL";
	private static final String USER = "root";
	private static final String PASSWORD = "654321";
	/**
	 * 閿熸枻鎷烽敓鏂ゆ嫹JDBC閿熸枻鎷峰洘閿熸枻鎷烽敓锟�
	 */
	protected static Statement s=null;
	protected static ResultSet rs = null;
	protected static Connection conn = null;
	/**
	 * 閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓鎹峰尅鎷烽敓鏂ゆ嫹閿熸枻鎷�
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
	 * 鎵ч敓鏂ゆ嫹INSERT/UPDATE/DELETE SQL閿熸枻鎷烽敓锟�
	 * @param sql SQL閿熸枻鎷锋磳顒婃嫹鍧�閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓锟�
	 * @return 鎵ч敓鍙枻鎷烽敓鏂ゆ嫹閿熺禒nt閿熸枻鎷烽敓鏂ゆ嫹
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
	 * 鎵ч敓鏂ゆ嫹SELECT SQL閿熸枻鎷烽敓锟�
	 * @param sql SQL閿熸枻鎷锋磳顒婃嫹鍧�閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓锟�
	 * @return ResultSet閿熸枻鎷烽敓鏂ゆ嫹閿燂拷
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
	 * 鎵ч敓鍙鎷锋�丼QL閿熸枻鎷烽敓锟�
	 * @param sql 閿熸枻鎷烽敓鍙鎷烽敓鏂ゆ嫹閿熶茎璁规嫹鎬丼QL閿熸枻鎷锋垂锟� 
	 * @return 閿熸枻鎷烽敓鏂ゆ嫹PreparedStatement閿熸枻鎷烽敓鏂ゆ嫹
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
	 * 閿熸枻鎷烽敓鏂ゆ嫹姣撻敓锟�
	 */
	public static void rollback() {
		try {
			getConnection().rollback();
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		
	}
	/**
	 * 閿熸埅鎲嬫嫹閿熸枻鎷烽敓鎹峰尅鎷烽敓鏂ゆ嫹閿熸帴璁规嫹閿熸枻鎷�
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
		"'閿熸枻鎷烽敓鏂ゆ嫹鍛�','濂�','1999-10-22','allen@henu.edu.cn')";
		executeUpdate(sql);
		close();
		*/
		//閿熸枻鎷峰啓SQL閿熸枻鎷烽敓锟�
		String sql = "INSERT INTO tb_users(fd_username,fd_password,fd_usertype,fd_gender,fd_email," +
				"fd_birthdate, fd_introduction,fd_hobby) VALUES (?,?,?,?,?,?,?,?)";
		
		//鎵ч敓鏂ゆ嫹SQL
		PreparedStatement ps = DbUtil.executePreparedStatement(sql);
		try {
			ps.setString(1, "username");
			ps.setString(2, "password");
			ps.setString(3, "1");
			ps.setString(4, "閿熸枻鎷�");
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
