package henu.utils;
import java.sql.*;
import javax.naming.*;
import org.apache.tomcat.jdbc.pool.DataSource;
public class WebDbPool {
	/**
	 * ����JDBC��ض���
	 */
	protected static Statement s=null;
	protected static ResultSet rs = null;
	protected static Connection conn = null;
	
	public static synchronized Connection getConnection()
	{
		try
		{
			//Context��javax.name���е�һ���ӿڣ����ڲ������ݿ����ӳص������ļ�
			Context ctx = new InitialContext();  
			ctx = (Context) ctx.lookup("java:comp/env");
             //dbpoolΪcontext.xml�ļ���<Resource>Ԫ��name����ֵ
			DataSource ds = (DataSource) ctx.lookup("dbpool");
			conn = ds.getConnection();
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		return conn;
	}
	
	/**
	 * ִ��INSERT/UPDATE/DELETE SQL���
	 * @param sql SQL��䣬�ַ�������
	 * @return ִ�н����int����
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
	 * ִ��SELECT SQL���
	 * @param sql SQL��䣬�ַ�������
	 * @return ResultSet�����
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
	 * ִ�ж�̬SQL���
	 * @param sql ���в����Ķ�̬SQL��䡣 
	 * @return ����PreparedStatement����
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
	 * ����ع�
	 */
	public static void rollback() {
		try {
			getConnection().rollback();
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		
	}
	/**
	 * �ر����ݿ����Ӷ���
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
		"'����Ա','Ů','1999-10-22','allen@henu.edu.cn')";
		executeUpdate(sql);
		close();
		*/
		//��дSQL���
		String sql = "INSERT INTO tb_users(fd_username,fd_password,fd_usertype,fd_gender,fd_email," +
				"fd_birthdate, fd_introduction,fd_hobby) VALUES (?,?,?,?,?,?,?,?)";
		
		//ִ��SQL
		PreparedStatement ps = DbUtil.executePreparedStatement(sql);
		try {
			ps.setString(1, "username");
			ps.setString(2, "password");
			ps.setString(3, "1");
			ps.setString(4, "��");
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
