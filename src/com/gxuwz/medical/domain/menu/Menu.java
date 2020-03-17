package com.gxuwz.medical.domain.menu;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import com.gxuwz.medical.database.DbUtil;
import com.gxuwz.medical.exception.MenuException;

/**
 * 权限实体类
 * @author 演示
 * 
 */
public class Menu {

	/**
	 * 权限编号
	 */
	private String menuid;
	/**
	 * 权限上一级编号
	 */
	private String menupid;
	/**
	 * 上一级菜单对象
	 */
	private Menu parent;
	
	private int level;//�˵�����
	
	/**
	 * 权限编号
	 */
	private String menuname;
	
	private String url;
	
	private Connection conn;
	
	private PreparedStatement ptmt;
	
	private ResultSet rs;
	
	public Menu(){
		
	}

	public Menu getParent() {
		try{
			if(this.parent==null){
				this.parent=new Menu(this.menupid);
			}
		}catch(MenuException e){
			e.printStackTrace();
		}
		return this.parent;
	}
	
	/**
	 * 构造菜单节点实例
	 * @param menuid
	 * @throws MenuException
	 */
	public Menu(String menuid) throws MenuException {
		this.menuid=menuid;
		this.menuname="";
		if(!"0".equals(menuid)){
		    loadDB(menuid);
		}
	}

	public Menu(String menuname, String url) {
		this.menuname=menuname;
		this.url =url;
	}

	private void loadDB(String menuid) throws MenuException {
		try {
			conn=DbUtil.getConnection();
			ptmt=conn.prepareStatement("select * from t_menu where menuid=? order by menuid asc");
			ptmt.setString(1, menuid);
			rs = ptmt.executeQuery();
			if(rs.next()){
				this.menuid=rs.getString("menuid");
				this.menuname=rs.getString("menuname");
				this.menupid =rs.getString("menupid");
				this.url =rs.getString("url");
				this.level=rs.getInt("level");
				
			}
		} catch (SQLException e) {
			throw new MenuException("Failed to load id"+menuid+" from database!",e);
		}finally{
			DbUtil.close(rs, ptmt, conn);
		}
		
	}

	public String getMenuid() {
		return menuid;
	}

	public void setMenuid(String menuid) {
		this.menuid = menuid;
	}

	public String getMenupid() {
		return menupid;
	}

	public void setMenupid(String menupid) {
		this.menupid = menupid;
	}


	public void setParent(Menu parent) {
		this.parent = parent;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public String getMenuname() {
		return menuname;
	}

	public void setMenuname(String menuname) {
		this.menuname = menuname;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
	// 添加到父节点+级别
	public void addMenu(String menupid, int plevel) throws SQLException {
		this.menupid=menupid;
		this.level=plevel+1;
		try{
			conn =DbUtil.getConnection();
			conn.setAutoCommit(false);
			//生成菜单编号
			this.menuid=createMenuid();
		    //保存到数据库
			saveToDB();
			conn.commit();
		}catch(SQLException e){
			conn.rollback();
			throw e;
		}finally{
			DbUtil.close(conn);
		}
	}

	private void saveToDB() throws SQLException {
		 try{
			  StringBuffer sqlBuff=new StringBuffer("insert into t_menu(menuid,menuname,menupid,url,level)");
			  sqlBuff.append("values(?,?,?,?,?)");
			  ptmt=conn.prepareStatement(sqlBuff.toString());
			  ptmt.setString(1, this.menuid);
			  ptmt.setString(2, this.menuname);
			  ptmt.setString(3, this.menupid);
			  ptmt.setString(4, this.url);
			  ptmt.setInt(5, this.level);
			  ptmt.executeUpdate(); 
		  }catch(SQLException e){
			  throw e;
		  }finally{
			  DbUtil.close(ptmt);
		  } 
		
	}

	private String createMenuid() throws SQLException {
		String sql="select max(menuid) from t_menu where menupid=?";
		try{
			ptmt=conn.prepareStatement(sql);
			int index=1;
			ptmt.setString(index, this.menupid);
			rs=ptmt.executeQuery();
			String maxcode="";
			if(rs.next()){
			   maxcode=rs.getString(1);
			}
			if(maxcode!=null){
			  int beginIndex=maxcode.length()-2;
			  String no=maxcode.substring(beginIndex);
			  Integer number=Integer.parseInt(no);
			  ++number;
			  //使用0补足位数
			  no=String.format("%02d", number);
			  maxcode=this.menupid+no;
			}else{
				maxcode=this.menupid+"01";
			}
			return maxcode;
		}catch(SQLException e){
			
			throw new SQLException("创建父节点ID"+this.menuid+"失败!",e);
		}finally{
			DbUtil.close(ptmt);
		}
	}

	public void delMenu(String menuid) throws Exception {
		 this.menuid =menuid;
		   try{
			   conn =DbUtil.getConnection();
			   //1：开启手动提交事务
			   conn.setAutoCommit(false);
			   //2:删除菜单信息
			   deleteFromDB();
			 //3:删除关联角色
			    unbindRole();
			   //4：提交事务
			   conn.commit();
		}catch (Exception e) {
			conn.rollback();
			throw e;
		}finally{
			DbUtil.close(conn);
		}


}

	private void unbindRole() throws SQLException {
		try{
			  StringBuffer sb=new StringBuffer("delete from t_role_menu where menuid=?");
			  ptmt=conn.prepareStatement(sb.toString());
			  ptmt.setString(1, this.menuid);
			  ptmt.executeUpdate(); 
		  }catch(SQLException e){
			  throw new SQLException("Failed to unbind to Role !", e);
		  }finally{
			  DbUtil.close(ptmt);
		  }
		
	}

	private void deleteFromDB() throws SQLException {
		 try{
			  StringBuffer sb=new StringBuffer("delete from t_menu where menuid=?");
			  ptmt=conn.prepareStatement(sb.toString());
			  ptmt.setString(1, this.menuid);

			  ptmt.executeUpdate(); 
		  }catch(SQLException e){
			  throw new SQLException("Failed to delete record from table !", e);
		  }finally{
			  DbUtil.close(ptmt);
		  }
	}

	public void editMenu(String menuname, String url) throws SQLException {
		
		this.menuname =menuname;
		this.url =url;
		try{
			conn =DbUtil.getConnection();
			conn.setAutoCommit(false);
		    //保存到数据库
			editToDB();
			conn.commit();
		}catch(SQLException e){
			conn.rollback();
			throw e;
		}finally{
			DbUtil.close(conn);
		}
		
	}

	private void editToDB() throws SQLException {
		try{
			  StringBuffer sqlBuff=new StringBuffer("update t_menu t set t.menuname=?,t.url=? where t.menuid=?");
			  ptmt=conn.prepareStatement(sqlBuff.toString());

			  ptmt.setString(1, this.menuname);
			  ptmt.setString(2, this.url);
			  ptmt.setString(3, this.menuid);
			  ptmt.executeUpdate(); 
		  }catch(SQLException e){
			  throw new SQLException("Failed to update t_menu !", e);
		  }finally{
			  DbUtil.close(ptmt);
		  } 
	}
}
