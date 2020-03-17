package com.gxuwz.medical.domain.chronicdis;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.PreparedStatement;

import javax.naming.spi.DirStateFactory.Result;

import com.gxuwz.medical.database.DbUtil;
import com.gxuwz.medical.exception.DbException;

/**
 * 慢性病分类管理类
 * @author 演示
 *
 */
public class Chronicdis {
	/**
	 * 疾病编码
	 */
	private String illcode;
	/**
	 * 疾病名称
	 */
	private String illname;
	/**
	 * 拼音码
	 */
	private String pycode;
	/**
	 * 五笔码
	 */
	private String wbcode;
	
	public Chronicdis(){
		
	}
	
	public Chronicdis(String illcode,String illname,String pycode,String wbcode){
		
		this.illcode =illcode;
		this.illname =illname;
		this.pycode =pycode;
		this.wbcode =wbcode;
		
	}
	
	
	public void edit()throws SQLException{
		Connection conn =DbUtil.getConnection();
		try{
			 conn.setAutoCommit(false);
			 this.updateToDB(conn);
			 conn.commit();
		}catch(SQLException e){
			conn.rollback();
			throw e;
		}finally{
			DbUtil.close(conn);
		}
	}
	private void updateToDB(Connection conn)throws SQLException{
		PreparedStatement pstmt=null;
		try{
			//定义构造update SQL语句字符串变量sql
			String  sql="update t_chronicdis set illname=?,pycode=?,wbcode=? where illcode=?";
			 //创建执行带动态参数的SQL的PreparedStatement pstmt
			 pstmt=conn.prepareStatement(sql);
			 //设置动态参数对应的值
			 int index=1;
			 pstmt.setString(index++, this.illname);
			 pstmt.setString(index++, this.pycode);
			 pstmt.setString(index++, this.wbcode);
			 pstmt.setString(index++, this.illcode);
			int count=pstmt.executeUpdate();
			
		}catch(SQLException e){
			throw e;
		}finally{
			DbUtil.close(pstmt);
		}
		
	}
	public Chronicdis(String id)throws Exception{
		this.illcode =id;
		try{
			load();
		}catch(Exception e){
			e.printStackTrace();
			throw new DbException("无法找到ID="+this.illcode+"对应信息");
		}
	}
	
	private void load()throws SQLException{
		Connection conn=DbUtil.getConnection();
		 PreparedStatement pstmt=null;
		 ResultSet rs=null;
		try{
			
		 String sql="select * from t_chronicdis where illcode=?";
		 pstmt=conn.prepareStatement(sql);
		 pstmt.setString(1, this.illcode);
		 rs=pstmt.executeQuery();
		 if(rs.next()){
			 this.illcode=rs.getString("illcode");
			 this.illname =rs.getString("illname");
			 this.pycode =rs.getString("pycode");
			 this.wbcode =rs.getString("wbcode");
		 }
		}catch(SQLException e){
			throw e;
		}
		
	}
	
	public void del(String illcode)throws SQLException{

		Connection conn=null;
       try{
    	   //1:对象属性赋值
    	    this.illcode =illcode;
    	    conn=DbUtil.getConnection();
    	   //2；调用delFromDB方法
    	    delFromDB(conn);
       }catch(SQLException e){
    	   throw e;
       }finally{
    	   DbUtil.close(conn);
       }
		
	}
	private void delFromDB(Connection conn)throws SQLException{
		PreparedStatement pstmt=null;
		try{
			//定义构造插入SQL语句字符串变量sql
			String  sql="delete from t_chronicdis where illcode=?";
			 //创建执行带动态参数的SQL的PreparedStatement pstmt
			 pstmt=conn.prepareStatement(sql);
			 //设置动态参数对应的值
			 int index=1;
			 pstmt.setString(index++, this.illcode);
		
			int count=pstmt.executeUpdate();
			
		}catch(SQLException e){
			throw e;
		}finally{
			DbUtil.close(pstmt);
			
		}
		
		
	}

	
	/**
	 * 添加慢性病
	 */
	public void add()throws SQLException{
		Connection conn=null;
       try{
    	   //1:对象属性赋值,无
    	    conn=DbUtil.getConnection();
    	   //2；调用saveToDB方法保存到数据库
    	    saveToDB(conn);
       }catch(SQLException e){
    	   throw e;
       }finally{
    	   DbUtil.close(conn);
       }
		
	}
	
	private void saveToDB(Connection conn)throws SQLException{
		PreparedStatement pstmt=null;
		try{
			//定义构造插入SQL语句字符串变量sql
			String  sql="insert into t_chronicdis(illcode,illname,pycode,wbcode)values(?,?,?,?)";
			 //创建执行带动态参数的SQL的PreparedStatement pstmt
			 pstmt=conn.prepareStatement(sql);
			 //设置动态参数对应的值
			 int index=1;
			 pstmt.setString(index++, this.illcode);
			 pstmt.setString(index++, this.illname);
			 pstmt.setString(index++, this.pycode);
			 pstmt.setString(index++, this.wbcode);
			int count=pstmt.executeUpdate();
			
		}catch(SQLException e){
			throw e;
		}finally{
			DbUtil.close(pstmt);
			
		}
		
		
	}

	public String getIllcode() {
		return illcode;
	}

	public void setIllcode(String illcode) {
		this.illcode = illcode;
	}

	public String getIllname() {
		return illname;
	}

	public void setIllname(String illname) {
		this.illname = illname;
	}

	public String getPycode() {
		return pycode;
	}

	public void setPycode(String pycode) {
		this.pycode = pycode;
	}

	public String getWbcode() {
		return wbcode;
	}

	public void setWbcode(String wbcode) {
		this.wbcode = wbcode;
	}
}
