package com.gxuwz.medical.domain.user;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import com.gxuwz.medical.database.DbUtil;
import com.gxuwz.medical.domain.role.Role;
import com.gxuwz.medical.exception.UserNotFoundException;

/**
 * 用户信息实体类
 * 
 * @author 演示
 * 
 */
public class User {
	/**
	 * 工号
	 */
	private String userid;
	/**
	 * 密码
	 */
	private String pwd;
	/**
	 * 姓名
	 */
	private String fullname;
	
	private String status;
	/**
	 * 所在农合机构编码
	 */
	private String agencode;
	/**
	 * 用户对应角色列表
	 */
	private Set<Role> roles;
	
	private Connection conn;
	private PreparedStatement ptmt;
	private ResultSet rs;

	public User() {

	}
	
	public User(String userid, String pwd) throws UserNotFoundException {
		try {
			conn = DbUtil.getConnection();
			ptmt = conn.prepareStatement("select * from t_user where userid=? and pwd=?");
			ptmt.setString(1, userid);
			ptmt.setString(2, pwd);
			rs = ptmt.executeQuery();
			if (rs != null && rs.next()) {
				this.fullname = rs.getString("fullname");
				this.pwd = rs.getString("pwd");
				this.userid = rs.getString("userid");
			} else {
				throw new UserNotFoundException("User with id " + userid
						+ " could not be loaded from the database.");
			}
		} catch (SQLException e) {

			throw new UserNotFoundException("User with id " + userid
					+ " could not be loaded from the database.");

		} finally {
			DbUtil.close(rs, ptmt, conn);
		}
	}
	
	public User(String userid, String pwd, String fullname, String status,String agencode) {
		this.userid = userid;
		this.pwd = pwd;
		this.fullname = fullname;
		this.status = status;
		this.agencode = agencode;
	}

	
	
	public void load(String userid)throws UserNotFoundException {
		try {
		    
			conn = DbUtil.getConnection();
			ptmt = conn.prepareStatement("select * from t_user where userid=?");
			ptmt.setString(1, userid);
			rs = ptmt.executeQuery();
			
			if(rs !=null && rs.next()){
				this.userid = rs.getString("userid");
				this.pwd = rs.getString("pwd");
				this.fullname = rs.getString("fullname");
				this.agencode = rs.getString("agencode");
				this.status = rs.getString("status");
			}
			
		} catch (SQLException e) {
			throw new UserNotFoundException("User with id " + userid
					+ " could not be loaded from the database.");
		}finally{
			DbUtil.close(rs, ptmt, conn);
		}

	}
	
	
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public String getPwd() {
		return pwd;
	}
	public void setPwd(String pwd) {
		this.pwd = pwd;
	}
	public String getFullname() {
		return fullname;
	}
	public void setFullname(String fullname) {
		this.fullname = fullname;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getAgencode() {
		return agencode;
	}
	public void setAgencode(String agencode) {
		this.agencode = agencode;
	}
	
	private List<String> getRoleids(){
		List<String> roleids = new ArrayList<String>();
		
		try {
			conn = DbUtil.getConnection();
			ptmt = conn.prepareStatement("select roleid from t_user_role where userid=?");
			ptmt.setString(1, userid);
			
			rs = ptmt.executeQuery();
			while(rs.next()){
				roleids.add(rs.getString(1));
			}
			
		} catch (SQLException e) {
			
			e.printStackTrace();
		}finally {
			DbUtil.close(rs, ptmt, conn);
		}
		return roleids;
	}
	
	public Set<Role> getRoles() {
		try{
			if(roles == null){
				roles = new HashSet<Role>();
				List<String> roleids = getRoleids();
				for(String roleid : roleids){
					Role role = new Role(roleid);
					roles.add(role);
				}
			}
		}catch (Exception e) {
			e.printStackTrace();
		}	
			return roles;
		}
	
	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}

	//添加用户
	public void addUser(String[] roleids) throws Exception {
		try {
			conn = DbUtil.getConnection();
			conn.setAutoCommit(false);
			//保存用户信息
			toSave(); 
			//建立角色关联
			if(roleids!=null){
				for(String roleid:roleids){
					bindRole(roleid);
				}
			}
			conn.commit();
		} catch (Exception e) {
			conn.rollback();
    		throw e;
		}finally{
			DbUtil.close(conn);
		}
		
	}

	private void bindRole(String roleid) throws SQLException {
		
		try {
			StringBuilder sb=new StringBuilder("insert into t_user_role(userid,roleid)");
			sb.append("values(?,?)");
			ptmt = conn.prepareStatement(sb.toString());
			ptmt.setString(1, this.userid);
			ptmt.setString(2, roleid);
			ptmt.executeUpdate(); 
		} catch (SQLException e) {
			throw new SQLException("Failed to bind to Role!", e);
		}finally{
			DbUtil.close(ptmt);
		}
	}

	private void toSave() throws Exception {
		try{
			  StringBuilder sb=new StringBuilder();
			  sb.append("insert into t_user");
			  sb.append("(userid,fullname,pwd,agencode,status)");
			  sb.append("values(?,?,?,?,?)");
			  ptmt=conn.prepareStatement(sb.toString());
			  ptmt.setString(1, this.userid);
			  ptmt.setString(2, this.fullname);
			  ptmt.setString(3, this.pwd);
			  ptmt.setString(4, this.agencode);
			  ptmt.setString(5, this.status);
			  ptmt.executeUpdate(); 
		}catch (Exception e) {
			 throw e;
		  }finally{
			  DbUtil.close(ptmt);
		  }
	}


	public void editUser(String[] roleids) throws Exception {

		try {
			conn = DbUtil.getConnection();
			conn.setAutoCommit(false);
			//更新用户信息到数据库
			this.editToDB();
			//删除原有关联菜单信息
			this.unbindRole();
			for (String roleid : roleids) {
				//再次绑定关联用户、角色
				this.bindRole(roleid);
			}
			conn.commit();
		} catch (Exception e) {
			conn.rollback();
			throw e;
		}finally{
			DbUtil.close(conn);
		}
		
	}
	/**
	   * 解除User与Role关联
	   * @param conn
	   * @throws SQLException
	   */
	private void unbindRole() throws SQLException {
		 
		  try {
			StringBuffer sb=new StringBuffer("delete from t_user_role where userid=?");
			ptmt = conn.prepareStatement(sb.toString());
			ptmt.setString(1, this.userid);
			ptmt.executeUpdate();
		} catch (SQLException e) {
			throw new SQLException("Failed to unbind to Role !", e);
		}finally{
			  DbUtil.close(ptmt);
		  }
		
	}

	private void editToDB() throws SQLException {
		
		try {
			StringBuffer sb=new StringBuffer("update t_user t set t.fullname=?,t.pwd=?,agencode=?,status=?  where t.userid=?");
			ptmt = conn.prepareStatement(sb.toString());
			ptmt.setString(1, this.fullname);
			ptmt.setString(2, this.pwd);
			ptmt.setString(3, this.agencode);
			ptmt.setString(4, this.status);
			ptmt.setString(5, this.userid);
			ptmt.executeUpdate();
		} catch (SQLException e) {
			throw new SQLException("Failed to update t_user  !", e);
		}finally{
			DbUtil.close(ptmt);
		}
	}

	public void delUser(String userid) throws Exception {
		this.userid =userid;
		try{
			   conn =DbUtil.getConnection();
			   //1：开启手动提交事务
			   conn.setAutoCommit(false);
			   //2:删除用户信息
			   deleteFromDB();
			   //3:删除关联角色信息
			   unbindRole();
			   //4：提交事务
			   conn.commit();
		   }catch(SQLException e){
			   conn.rollback();
			   throw e;
		   }finally{
			   DbUtil.close(conn);
		   }
	  }


	private void deleteFromDB() throws Exception {
		StringBuffer sb=new StringBuffer("delete from t_user where userid=?");
	    try {
	    	ptmt=conn.prepareStatement(sb.toString());
			ptmt.setString(1, this.userid);
			ptmt.executeUpdate(); 
		} catch(SQLException e){
			  throw new SQLException("Failed to delete record from table !", e);
		  }finally{
			  DbUtil.close(ptmt);
		  }
		
	}
	
	
}
