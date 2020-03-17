package com.gxuwz.medical.database;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
/**
 * 数据库访问操作管理类
 * @author 演示
 *
 */
public class DbUtil {
	
	private static String url = null;
	
	private static String user = null;
	
	private static String password = null;
	
	private static String driverClass = null;
	
	/**
	 * 只注册一次
	 */
	static{
		try {
			/**
			 * 读取jdbc.properties
			 */
			//创建Properties对象
			Properties prop = new Properties();
			//构造输入流
			Class clazz = DbUtil.class;
			InputStream in = clazz.getResourceAsStream("/jdbc.properties");
			//加载文件
			prop.load(in);
			//读取
			url = prop.getProperty("url");
			user = prop.getProperty("user");
			password = prop.getProperty("password");
			driverClass = prop.getProperty("driverClass");
			Class.forName("com.mysql.jdbc.Driver");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	/**
	 * 获取连接方法
	 */
	public static Connection getConnection(){
		try {
			Connection conn = DriverManager.getConnection(url,user,password);
			return conn;
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException();
		}
	}
	
	/**
	 * 释放资源方法
	 */
	public static void close(ResultSet rs,PreparedStatement ptmt,Connection conn){
		
		if(rs!=null){
			try {
				rs.close();
			} catch (Exception e) {
				e.printStackTrace();
				throw new RuntimeException();
			}
		}
		
		if(ptmt!=null){
			try {
				ptmt.close();
			} catch (Exception e) {
				e.printStackTrace();
				throw new RuntimeException();
			}
		}
		
		if(conn!=null){
			try {
				conn.close();
			} catch (Exception e) {
				e.printStackTrace();
				throw new RuntimeException();
			}
		}
	}
	
public static void close(PreparedStatement ptmt,Connection conn){
		
		if(ptmt!=null){
			try {
				ptmt.close();
			} catch (Exception e) {
				e.printStackTrace();
				throw new RuntimeException();
			}
		}
		
		if(conn!=null){
			try {
				conn.close();
			} catch (Exception e) {
				e.printStackTrace();
				throw new RuntimeException();
			}
		}
	}

	public static void close(PreparedStatement ptmt) {
		if(ptmt!=null){
			try {
				ptmt.close();
			} catch (Exception e) {
				e.printStackTrace();
				throw new RuntimeException();
			}
		}
	}
	

	public static void close(Connection conn) {
		if(conn!=null){
			try {
				conn.close();
			} catch (Exception e) {
				e.printStackTrace();
				throw new RuntimeException();
			}
		}
	}

	
	/**
	 * 封装增加删除修改的通用工具方法
	 * @param sql SQL语句
	 * @param objs	SQL语句占位符实参，如果没有参数则传入null
	 * @return 返回增删改的结果，返回类型为int
	 */
	public static int executeDML(String sql,Object...objs){
		// 声明jdbc变量
				Connection conn = null;
				PreparedStatement ptmt = null;
				int i = -1;
				try {
					// 获取连接对象
					conn = DbUtil.getConnection();
					// 开启事务管理
					conn.setAutoCommit(false);
					// 创建SQL命令对象
					ptmt = conn.prepareStatement(sql);
					// 给占位符赋值
					if(objs!=null){
						for(int j=0;j<objs.length;j++){
							ptmt.setObject(j+1,objs[j]);
						}
					}
					// 执行SQL
					i = ptmt.executeUpdate();
					conn.commit();
				} catch (Exception e) {
					try {
						conn.rollback();
					} catch (SQLException e1) {
						e1.printStackTrace();
						throw new RuntimeException();
					}
					e.printStackTrace();
				} finally {
					DbUtil.close(null, ptmt, conn);
				}
				return i;
	}
	
	/**
	 * 查询方法
	 * @param sql
	 * @param args
	 * @return
	 */
	public List executeQuery(String sql,Object[] args,Class clazz){
		
		List list=new ArrayList();
		Connection conn = null;
		PreparedStatement ptmt = null;
		ResultSet rs = null;
		try {
			conn = this.getConnection();
			ptmt = conn.prepareStatement(sql);
			if(args != null){
				for (int i = 0; i < args.length; i++) {
					ptmt.setObject(i+1, args[i]);
				}
			}
			rs=ptmt.executeQuery();
			while(rs.next()){
			//通过反射得到一个对象
				list.add(this.getObj(clazz, rs));
			}
			
			
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException();
		}finally{
			try {
				close(rs, ptmt, conn);;
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				throw new RuntimeException();
			}
		}
		return list;
	}
 
	
	public static Object getObj(Class clazz,ResultSet rs)throws Exception{
		//所有的属性
		Field[] field= clazz.getDeclaredFields();
		Object info= clazz.newInstance();
		for (int i = 0; i < field.length; i++) {
			String name=field[i].getName().toUpperCase();
			//得到方法名
				name="set"+name.charAt(0)+name.substring(1).toLowerCase();
			//得到类型
			Class  c=field[i].getType();
			//得到方法
		    Method method= clazz.getMethod(name, c);
		    //实现方法
		    method.invoke(info, rs.getObject(i+1));
		}
		return info;
	}
	

}