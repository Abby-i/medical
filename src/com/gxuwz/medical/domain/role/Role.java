package com.gxuwz.medical.domain.role;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.gxuwz.medical.database.DbUtil;
import com.gxuwz.medical.domain.menu.Menu;
import com.gxuwz.medical.exception.RoleNotFoundException;

public class Role {
	/**
	 * 权限编号
	 */
	private String roleid;
	/**
	 * 权限编号
	 */
	private String roleName;
	/**
	 * 权限编号
	 */
	private Set<Menu> menus;
	
	private Connection conn;
	
	private PreparedStatement ptmt;
	
	private ResultSet rs;
	
	public Role(){
		
	}
	
	public Role(String roleid,String rolename){
		
		this.roleid =roleid;
		this.roleName =rolename;
	}
	
	public void addRole(String rolename,String[]menuids) throws Exception {
		
		this.roleName = rolename;
		try{
			 conn =DbUtil.getConnection();
			 	//1：开启手动提交事务
			   conn.setAutoCommit(false);
			   //2:保存角色信息到数据库
			   saveToDB();
			 //3:循环关联菜单信息
			   if(menuids!=null){
				   for (String m : menuids) {
					  bindMenu(m);
				}
			   }
			   conn.commit();
		}catch (Exception e) {
			 conn.rollback();
			   throw e;
		}finally{
			DbUtil.close(ptmt);
		}
	}
	
	private void bindMenu(String menuid) throws SQLException {
		  try{
			  StringBuffer sqlBuff=new StringBuffer("insert into t_role_menu(roleid,menuid)");
			  sqlBuff.append("values(?,?)");
			  ptmt=conn.prepareStatement(sqlBuff.toString());
			  ptmt.setString(1, this.roleid);
			  ptmt.setString(2, menuid);
			  ptmt.executeUpdate(); 
		  }catch(SQLException e){
			  throw new SQLException("Failed to bind to menu!", e);
		  }finally{
			  DbUtil.close(ptmt);
		  }
	}

	private void saveToDB() throws SQLException {
		try{
		 StringBuffer sb=new StringBuffer("insert into t_role(roleid,rolename)");
		  sb.append("values(?,?)");
		  ptmt=conn.prepareStatement(sb.toString());
		  ptmt.setString(1, this.roleid);
		  ptmt.setString(2, this.roleName);
		  ptmt.executeUpdate(); 
	  }catch(SQLException e){
		  throw new SQLException("Failed to insert into table !", e);
	  }finally{
		  DbUtil.close(ptmt);
	  } 
		
	}

	public Role(String roleid) throws RoleNotFoundException{
		load(roleid);
	}

	private void load(String roleid) throws RoleNotFoundException {
		conn = DbUtil.getConnection();
		try {
			ptmt = conn.prepareStatement("select * from t_role where roleid=?");
			ptmt.setString(1, roleid);
			rs = ptmt.executeQuery();
			if(rs.next()){
				this.roleid = rs.getString("roleid");
				this.roleName = rs.getString("rolename");
			}else{
				throw new RoleNotFoundException("Role with id "
	                    + roleid + " could not be loaded from the database.");
			}
		} catch (SQLException e) {
			throw new RoleNotFoundException("Role with id "
                    + roleid + " could not be loaded from the database.",e);
		}finally{
			DbUtil.close(rs, ptmt, conn);
		}
		
	}

	public String getRoleid() {
		return roleid;
	}
	public void setRoleid(String roleid) {
		this.roleid = roleid;
	}
	public String getRoleName() {
		return roleName;
	}
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	
	private List<String> getMenuids() {
		List<String> menuids = new ArrayList<String>();
		
		try {
			conn = DbUtil.getConnection();
			ptmt = conn.prepareStatement("select menuid from t_role_menu where roleid=?");
			ptmt.setString(1, roleid);
			rs = ptmt.executeQuery();
			while(rs.next()){
				menuids.add(rs.getString(1));
			}
		} catch (SQLException e) {
			
			e.printStackTrace();
			
		} finally {
			DbUtil.close(rs, ptmt, conn);
		}
		return menuids;
	}

	public Set<Menu> getMenus() {
		try {
			if (menus == null) {
				menus= new HashSet<Menu>();
				List<String> menuids = getMenuids();
				for (String menuid : menuids) {
					Menu menu = new Menu(menuid);
					menus.add(menu);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return menus;
	}

	public void setMenus(Set<Menu> menus) {
		this.menus = menus;
	}

	public void editRole(String[] menuids) throws SQLException {
		 try{
			conn =DbUtil.getConnection();
		   //1：开启手动提交事务
		   conn.setAutoCommit(false);
		   //2:更新角色信息到数据库
		   eidtToDB();
		   //3：删除原来角色关联
		   unbindMenu();
		   //4:循环关联菜单信息
		   for(String menuid:menuids){
			   bindMenu(menuid);
		   }
		   //4：提交事务
		   conn.commit();
		 }catch(SQLException e){
			   conn.rollback();
			   throw e;
		   }finally{
			   DbUtil.close(conn);
		   }
		
	}


	private void unbindMenu() throws SQLException {
		 try {
			StringBuffer sb=new StringBuffer("delete from t_role_menu where roleid=?");
			  ptmt=conn.prepareStatement(sb.toString());
			  ptmt.setString(1, this.roleid);
			  ptmt.executeUpdate();
		} catch (SQLException e) {
			 throw new SQLException("Failed to unbind to menu!", e);
		  }finally{
			  DbUtil.close(ptmt);
		  }
		
	}

	private void eidtToDB() throws SQLException {
		 try{
			  StringBuffer sb = new StringBuffer("update t_role t set t.rolename=?  where t.roleid=?");
			  ptmt=conn.prepareStatement(sb.toString());
			  ptmt.setString(1, this.roleName);
			  ptmt.setString(2, this.roleid);
			  ptmt.executeUpdate(); 
		  }catch(SQLException e){
			  throw new SQLException("Failed to update t_role  !", e);
		  }finally{
			  DbUtil.close(ptmt);
		  }
		
	}

	public void delRole(String roleid) throws SQLException {
		 this.roleid =roleid;
		 try{
			   conn =DbUtil.getConnection();
			   //1：开启手动提交事务
			   conn.setAutoCommit(false);
			   //2:删除角色信息
			   deleteFromDB();
			   //3:删除关联菜单信息
			   unbindMenu();
			   //4：提交事务
			   conn.commit();
		   }catch(SQLException e){
			   conn.rollback();
			   throw e;
		   }finally{
			   DbUtil.close(conn);
		   }
		
	}

	private void deleteFromDB() throws SQLException {
		try{
			  StringBuffer sb=new StringBuffer("delete from t_role where roleid=?");
			  ptmt=conn.prepareStatement(sb.toString());
			  ptmt.setString(1, this.roleid);
			  ptmt.executeUpdate(); 
		  }catch(SQLException e){
			  throw new SQLException("Failed to delete record from table !", e);
		  }finally{
			  DbUtil.close(ptmt);
		  }
		
	}

	
	
}
