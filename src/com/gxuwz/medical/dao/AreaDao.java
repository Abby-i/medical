package com.gxuwz.medical.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import com.gxuwz.medical.database.DbUtil;
import com.gxuwz.medical.domain.area.Area;
import com.gxuwz.medical.exception.DbException;

public class AreaDao extends GenericDao<Area>{

	@Override
	protected Area handle(ResultSet rs) throws SQLException {
		try{
			Area model=new Area();
			model.setAreacode(rs.getString("areacode"));
			model.setAreaname(rs.getString("areaname"));
			model.setGrade(rs.getInt("grade"));
			model.setAreaupcode(rs.getString("areaupcode"));
			return model;
		}catch(SQLException e){
			
			throw new SQLException("结果集转为实例失败!",e);
		}
	}
	
	public List<Area> queryAll(){
		Connection conn = null;
		PreparedStatement ptmt = null;
		ResultSet rs =null;
		List<Area> result = new ArrayList<Area>();
		StringBuilder sb=new StringBuilder();
		//查询出所属行政区域是否在镇级以上的区域
		sb.append("SELECT * FROM t_area order by areacode asc");
		try{
			conn = DbUtil.getConnection();
			ptmt = conn.prepareStatement(sb.toString());
			rs = ptmt.executeQuery();
			
			Area a = null;
			while(rs.next()){
				a = new Area();
				a.setAreacode(rs.getString("areacode"));
				a.setAreaname(rs.getString("areaname"));
				a.setGrade(rs.getInt("grade"));
				a.setAreaupcode(rs.getString("areaupcode"));
				result.add(a);
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			DbUtil.close(rs, ptmt, conn);
		}
		return result;
	}

	public List<Area> queryAreas() {
		Connection conn = null;
		PreparedStatement ptmt = null;
		ResultSet rs =null;
		List<Area> result = new ArrayList<Area>();
		StringBuilder sb=new StringBuilder();
		//查询出所属行政区域是否在镇级以上的区域
		sb.append("SELECT * FROM t_area WHERE  LENGTH(areacode)<=8 order by areacode asc");
		try{
			conn = DbUtil.getConnection();
			ptmt = conn.prepareStatement(sb.toString());
			rs = ptmt.executeQuery();
			
			Area a = null;
			while(rs.next()){
				a = new Area();
				a.setAreacode(rs.getString("areacode"));
				a.setAreaname(rs.getString("areaname"));
				a.setGrade(rs.getInt("grade"));
				a.setAreaupcode(rs.getString("areaupcode"));
				result.add(a);
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			DbUtil.close(rs, ptmt, conn);
		}
		return result;
	}

	public Area querySelected(String areacode) {
		Connection conn = null;
		PreparedStatement ptmt = null;
		ResultSet rs =null;
		try {
			conn = DbUtil.getConnection();
			StringBuffer sb = new StringBuffer("select a.areacode,a.areaname,grade,a.areaupcode FROM t_farmer f,t_area a WHERE f.areacode=a.areacode and a.areacode = ?");
			ptmt = conn.prepareStatement(sb.toString());
			ptmt.setString(1, areacode);
			rs = ptmt.executeQuery();
			Area a = null;
			while(rs.next()){
				a = new Area();
				a.setAreacode(rs.getString("areacode"));
				a.setAreaname(rs.getString("areaname"));
				a.setGrade(rs.getInt("grade"));
				a.setAreaupcode(rs.getString("areaupcode"));
				return a ;
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			DbUtil.close(rs, ptmt, conn);
		}
		return null;
	}

	/**
	 * 通过id查询Area
	 * @param homeid
	 * @return
	 */
	public List<Area> queryByupId(String areacode) {
		Connection conn = null;
		PreparedStatement ptmt = null;
		ResultSet rs =null;
		List<Area> result = new ArrayList<Area>();
		StringBuffer sb=new StringBuffer();
		sb.append("select areacode,areaname,grade,areaupcode from t_area where areaupcode = ? order by areacode asc");
		try{
			 conn = DbUtil.getConnection();
			 ptmt = conn.prepareStatement(sb.toString());
			 ptmt.setString(1, areacode);
			 rs = ptmt.executeQuery();
			 Area a = null;
			 while(rs.next()) {
				 a = new Area();
				 a.setAreacode(rs.getString("areacode"));
				 a.setAreaname(rs.getString("areaname"));
				 a.setGrade(rs.getInt("grade"));
				 a.setAreaupcode(rs.getString("areaupcode"));
				 result.add(a);
			 }
		}catch (Exception e) {
			e.printStackTrace();
		}finally {
			DbUtil.close(rs, ptmt, conn);
		}
	return result;
}
	
	public List<Area>findChildArea(String areacode,int grade)throws DbException{
		try{
	    String sql="select * from t_area where areacode like'"+areacode+"%' and grade="+grade;
			 
		  Object[]params={};
		  return super.queryObject(sql, params);
		}catch(SQLException e){
			
			throw new DbException("查找所有行政区域失败",e);
		}
	}
	
	//查询所有的镇
	public List<Area> findtown(){
		Connection conn = null;
		PreparedStatement ptmt = null;
		ResultSet rs =null;
		List<Area> result = new ArrayList<Area>();
		StringBuffer sb=new StringBuffer();
		sb.append("select areacode,areaname,grade,areaupcode from t_area where grade=2 order by areacode asc");
		try{
			 conn = DbUtil.getConnection();
			 ptmt = conn.prepareStatement(sb.toString());
			 rs = ptmt.executeQuery();
			 Area a = null;
			 while(rs.next()) {
				 a = new Area();
				 a.setAreacode(rs.getString("areacode"));
				 a.setAreaname(rs.getString("areaname"));
				 a.setGrade(rs.getInt("grade"));
				 a.setAreaupcode(rs.getString("areaupcode"));
				 result.add(a);
			 }
		}catch (Exception e) {
			e.printStackTrace();
		}finally {
			DbUtil.close(rs, ptmt, conn);
		}
	return result;
	}
	//查询镇所属的村（areacode长度为10）
	
	//查询所有的村（areacode长度为12）
	
	
	
	public Area querytownname(String townid) {
		Connection conn =null;
		PreparedStatement ptmt =null;
		ResultSet rs =null;
		StringBuffer sb=new StringBuffer("SELECT b.areacode,b.areaname from t_homearchives a,t_area b  where a.townid = b.areacode and a.townid = ? ");
		try {
			conn = DbUtil.getConnection();
			ptmt = conn.prepareStatement(sb.toString());
			int index=1;
			ptmt.setString(index, townid);
			rs = ptmt.executeQuery();
			Area area = new Area();
			if (rs != null && rs.next()) {
				area.setAreacode(rs.getString("areacode"));
				area.setAreaname(rs.getString("areaname"));
				return area;
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException();
		}finally {
			DbUtil.close(rs, ptmt, conn);
		}
		return null;
		
	}
	
	public Area queryVillagename(String villageid) {
		Connection conn =null;
		PreparedStatement ptmt =null;
		ResultSet rs =null;
		StringBuffer sb=new StringBuffer("SELECT b.areacode,b.areaname from t_homearchives a,t_area b  where a.villageid = b.areacode and a.villageid = ? ");
		try {
			conn = DbUtil.getConnection();
			ptmt = conn.prepareStatement(sb.toString());
			int index=1;
			ptmt.setString(index, villageid);
			rs = ptmt.executeQuery();
			Area area = new Area();
			if (rs != null && rs.next()) {
				area.setAreacode(rs.getString("areacode"));
				area.setAreaname(rs.getString("areaname"));
				return area;
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException();
		}finally {
			DbUtil.close(rs, ptmt, conn);
		}
		return null;
		
	}
	
	public Area queryGroupname(String groupid) {
		Connection conn =null;
		PreparedStatement ptmt =null;
		ResultSet rs =null;
		StringBuffer sb=new StringBuffer("SELECT b.areacode,b.areaname from t_homearchives a,t_area b  where a.groupid = b.areacode and a.groupid = ? ");
		try {
			conn = DbUtil.getConnection();
			ptmt = conn.prepareStatement(sb.toString());
			int index=1;
			ptmt.setString(index, groupid);
			rs = ptmt.executeQuery();
			Area area = new Area();
			if (rs != null && rs.next()) {
				area.setAreacode(rs.getString("areacode"));
				area.setAreaname(rs.getString("areaname"));
				return area;
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException();
		}finally {
			DbUtil.close(rs, ptmt, conn);
		}
		return null;
		
	}
	
}
