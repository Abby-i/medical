package com.gxuwz.medical.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import com.gxuwz.medical.database.DbUtil;
import com.gxuwz.medical.domain.role.Role;
import com.gxuwz.medical.domain.user.User;

public class RoleDao{
	
	private Connection conn;
	private PreparedStatement ptmt;
	private ResultSet rs;

	/**
	 * 查询角色表的所有信息
	 * @return
	 */
	public List<Role> queryRoles() {
		List<Role> result = new ArrayList<Role>();
		StringBuilder sb=new StringBuilder();
		sb.append("select roleid,rolename from t_role");
		
		try {
			 conn = DbUtil.getConnection();
			 ptmt = conn.prepareStatement(sb.toString());
			 rs = ptmt.executeQuery();
			 
			 Role r =null;
			 while(rs.next()) {
				 r = new Role();
				 r.setRoleid(rs.getString("roleid"));
				 r.setRoleName(rs.getString("rolename"));
				 result.add(r);
			 }
		 }catch (Exception e) {
				e.printStackTrace();
			}finally {
				DbUtil.close(rs, ptmt, conn);
			}
			return result;
		}
	
	/**
	 * 
	 * @param sql
	 * @param params
	 * @return
	 * @throws SQLException
	 */
	public List<Role> queryOjects(String sql,Object[]params)throws SQLException{
		Connection conn =null;
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		List<Role> results=new ArrayList<Role>();
		try{
			conn=DbUtil.getConnection();
			pstmt=conn.prepareStatement(sql);
			int index=1;
			if(params!=null){
			  for(Object param:params){
				  if(param instanceof String){
					  pstmt.setString(index++, (String)param);
				  }
				  if(param instanceof Integer){
					  pstmt.setInt(index++, (Integer)param);
				  }
				  if(param instanceof Float){
					  pstmt.setFloat(index++, (Float)param);
				  }
			  }
			}
			rs=pstmt.executeQuery();
			while(rs.next()){

				Role role=handle(rs);
				results.add(role);
			
			}
			return results;
		}catch(SQLException e){
			throw new SQLException("执行SQL["+sql+"]失败",e);
		}finally{
			DbUtil.close(rs, pstmt, conn);
		}
		
	}
	
	protected Role handle(ResultSet rs) throws SQLException {
		try{
			Role role=new Role();
			role.setRoleid(rs.getString("roleid"));
			role.setRoleName(rs.getString("rolename"));
			return role;
		}catch(SQLException e){
			
			throw new SQLException("结果集转为实例失败!",e);
		}
	}

	public List<String> queryhasChecked(String userid) {
		List<String> result = new ArrayList<String>();
		StringBuilder sb=new StringBuilder();
		sb.append("select roleid from t_user_role where userid = ?");
		
		try {
			 conn = DbUtil.getConnection();
			 ptmt = conn.prepareStatement(sb.toString());
			 ptmt.setString(1, userid);
			 rs = ptmt.executeQuery();
			 
			 while(rs.next()) {
				 String roleid=rs.getString("roleid");
				 result.add(roleid);
			 }
		 }catch (Exception e) {
				e.printStackTrace();
			}finally {
				DbUtil.close(rs, ptmt, conn);
			}
			return result;
		}

	public List<Role> queryRole(Integer currentPage, Integer pageSize) {
		List<Role> result = new ArrayList<Role>();
		 StringBuilder sb=new StringBuilder();
		 int startNo = (currentPage-1)*pageSize;
		 sb.append("select * from t_role limit ?,?");
		 
		 try {
			 conn = DbUtil.getConnection();
			 ptmt = conn.prepareStatement(sb.toString());
			 ptmt.setInt(1, startNo);
			 ptmt.setInt(2, pageSize);
			 rs = ptmt.executeQuery();
			 
			 Role r =null;
			 while(rs.next()) {
				 r = new Role();
				 r.setRoleid(rs.getString("roleid"));
				 r.setRoleName(rs.getString("rolename"));
				 result.add(r);
			 }
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException();
		}finally {
			DbUtil.close(rs, ptmt, conn);
		}
		return result;
	}

	public Role querybyId(String roleid) {
		StringBuffer sb=new StringBuffer();
		 sb.append("select * from t_role where roleid = ?");
		
		 try {
			conn = DbUtil.getConnection();
			ptmt = conn.prepareStatement(sb.toString());
			int index=1;
			ptmt.setString(index, roleid);
			rs = ptmt.executeQuery();
			Role role=new Role();
			if (rs != null && rs.next()) {
				role.setRoleid(rs.getString("roleid"));
				role.setRoleName(rs.getString("rolename"));
			}
			return role;
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException();
		}finally {
			DbUtil.close(rs, ptmt, conn);
		}
	 }
}
