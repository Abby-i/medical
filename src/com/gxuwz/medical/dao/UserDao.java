package com.gxuwz.medical.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.gxuwz.medical.database.DbUtil;
import com.gxuwz.medical.domain.user.User;

public class UserDao {
	
	private Connection conn;
	private PreparedStatement ptmt;
	private ResultSet rs;
	
	public List<User> queryUser(Integer currentPage, Integer pageSize) {
		 List<User> result = new ArrayList<User>();
		 StringBuilder sb=new StringBuilder();
		 int startNo = (currentPage-1)*pageSize;
		 sb.append("select * from t_user limit ?,?");
		 
		 try {
			 conn = DbUtil.getConnection();
			 ptmt = conn.prepareStatement(sb.toString());
			 ptmt.setInt(1, startNo);
			 ptmt.setInt(2, pageSize);
			 rs = ptmt.executeQuery();
			 
			 User u =null;
			 while(rs.next()) {
				 u = new User();
				 u.setUserid(rs.getString("userid"));
				 u.setPwd(rs.getString("pwd"));
				 u.setFullname(rs.getString("fullname"));
				 u.setAgencode(rs.getString("agencode"));
				 u.setStatus(rs.getString("status"));
				 result.add(u);
			 }
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException();
		}finally {
			DbUtil.close(rs, ptmt, conn);
		}
		return result;
	}

	public User querybyId(String userid) {
		
		 StringBuffer sb=new StringBuffer();
		 sb.append("select * from t_user where userid = ?");
		
		 try {
			conn = DbUtil.getConnection();
			ptmt = conn.prepareStatement(sb.toString());
			int index=1;
			ptmt.setString(index, userid);
			rs = ptmt.executeQuery();
			User user=new User();
			if (rs != null && rs.next()) {
				user.setFullname(rs.getString("fullname"));
				user.setPwd(rs.getString("pwd"));
				user.setUserid(rs.getString("userid"));
				user.setAgencode(rs.getString("agencode"));
				user.setStatus(rs.getString("status"));
			}
			return user;
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException();
		}finally {
			DbUtil.close(rs, ptmt, conn);
		}
	 }
		 
}
