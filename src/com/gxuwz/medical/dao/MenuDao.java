package com.gxuwz.medical.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.gxuwz.medical.database.DbUtil;
import com.gxuwz.medical.domain.menu.Menu;
import com.gxuwz.medical.domain.user.User;

public class MenuDao {
	
	private Connection conn;
	private PreparedStatement ptmt;
	private ResultSet rs;
	
	public List<Menu> queryMenus(){
		List<Menu> result = new ArrayList<Menu>();
		StringBuilder sb=new StringBuilder();
		sb.append("SELECT menuid,menuname,menupid,url,level FROM t_menu  order by menuid asc");
		try {
			 conn = DbUtil.getConnection();
			 ptmt = conn.prepareStatement(sb.toString());
			 rs = ptmt.executeQuery();
			 
			 Menu m = null;
			 while(rs.next()) {
				 m = new Menu();
				 m.setMenuid(rs.getString("menuid"));
				 m.setMenuname(rs.getString("menuname"));
				 m.setMenupid(rs.getString("menupid"));
				 m.setUrl(rs.getString("url"));
				 m.setLevel(rs.getInt("level"));
				 result.add(m);
			 }
		 } catch (Exception e) {
				e.printStackTrace();
			}finally {
				DbUtil.close(rs, ptmt, conn);
			}
		return result;
		
	}
	
	public List<String> queryMenuid(String roleid){
		List<String> result =new ArrayList<String>();
		StringBuilder sb=new StringBuilder();
		sb.append("SELECT menuid from t_menu order by menuid asc");
		try {
			 conn = DbUtil.getConnection();
			 ptmt = conn.prepareStatement(sb.toString());
			 rs = ptmt.executeQuery();
			 
			 while(rs.next()) {
				 String menuid=rs.getString("menuid");
				 result.add(menuid);
			 }
		 } catch (Exception e) {
				e.printStackTrace();
			}finally {
				DbUtil.close(rs, ptmt, conn);
			}
		return result;
		
	}

	public List<Menu> queryMenus(String roleid) {
		List<Menu> result = new ArrayList<Menu>();
		StringBuilder sb=new StringBuilder();
		sb.append("SELECT a.menuid,menuname,menupid,url,level from  t_menu a,t_role_menu b  WHERE a.menuid = b.menuid AND roleid = ? order by a.menuid asc");
		try {
			 conn = DbUtil.getConnection();
			 ptmt = conn.prepareStatement(sb.toString());
			 ptmt.setString(1, roleid);
			 rs = ptmt.executeQuery();
			 
			 Menu m = null;
			 while(rs.next()) {
				 m = new Menu();
				 m.setMenuid(rs.getString("menuid"));
				 m.setMenuname(rs.getString("menuname"));
				 m.setMenupid(rs.getString("menupid"));
				 m.setUrl(rs.getString("url"));
				 m.setLevel(rs.getInt("level"));
				 result.add(m);
			 }
		 } catch (Exception e) {
				e.printStackTrace();
			}finally {
				DbUtil.close(rs, ptmt, conn);
			}
		return result;
	}

	public List<Menu> queryMenu(Integer currentPage, Integer pageSize) {
		 List<Menu> result = new ArrayList<Menu>();
		 StringBuilder sb=new StringBuilder();
		 int startNo = (currentPage-1)*pageSize;
		 sb.append("select * from t_menu limit ?,?");
		 
		 try {
			 conn = DbUtil.getConnection();
			 ptmt = conn.prepareStatement(sb.toString());
			 ptmt.setInt(1, startNo);
			 ptmt.setInt(2, pageSize);
			 rs = ptmt.executeQuery();
			 
			 Menu m =null;
			 while(rs.next()) {
				 m = new Menu();
				 m.setMenuid(rs.getString("menuid"));
				 m.setMenuname(rs.getString("menuname"));
				 m.setMenupid(rs.getString("menupid"));
				 m.setUrl(rs.getString("url"));
				 m.setLevel(rs.getInt("level"));
				 result.add(m);
			 }
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException();
		}finally {
			DbUtil.close(rs, ptmt, conn);
		}
		return result;
	}
	
	public List<Menu> queryMenu(){
		 List<Menu> result = new ArrayList<Menu>();
		 StringBuilder sb=new StringBuilder();
		 sb.append("select * from t_menu ");
		 try {
			 conn = DbUtil.getConnection();
			 ptmt = conn.prepareStatement(sb.toString());
			 rs = ptmt.executeQuery();
			 
			 Menu m =null;
			 while(rs.next()) {
				 m = new Menu();
				 m.setMenuid(rs.getString("menuid"));
				 m.setMenuname(rs.getString("menuname"));
				 m.setMenupid(rs.getString("menupid"));
				 m.setUrl(rs.getString("url"));
				 m.setLevel(rs.getInt("level"));
				 result.add(m);
			 }
		 }catch (Exception e) {
				e.printStackTrace();
				throw new RuntimeException();
			}finally {
				DbUtil.close(rs, ptmt, conn);
			}
			return result;
		}

	
	
}
