package com.gxuwz.medical.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.gxuwz.medical.database.DbUtil;
import com.gxuwz.medical.domain.illCard.IllCard;

public class IllCardDao extends GenericDao<IllCard>{

	@Override
	protected IllCard handle(ResultSet rs) throws SQLException {
		try{
			IllCard model = new IllCard();
			 model.setId(rs.getInt("id"));
			 model.setIllCard(rs.getString("illCard"));
			 model.setNongheCard(rs.getString("nongheCard"));
			 model.setIdCard(rs.getString("idCard"));
			 model.setIllCode(rs.getString("illCode"));
			 model.setAttachment(rs.getString("attachment"));
			 model.setStartTime(rs.getDate("startTime"));
			 model.setEndTime(rs.getDate("endTime"));
			 model.setRealName(rs.getString("realName"));
			return model;
		}catch(SQLException e){
			
			throw new SQLException("结果集转为实例失败!",e);
		}
	}

	/**
	 * 查询总记录数
	 * @param illCard
	 * @param idCard
	 * @param illCode
	 * @return
	 */
	public int queryCount(String illCard, String idCard, String illCode) {
		Connection conn = null;
	    PreparedStatement ptmt= null;
		ResultSet rs = null;
		
		StringBuffer sb=new StringBuffer("select count(*) from t_ill_card where 1=1 ");
		if(illCard!=null && !illCard.trim().equals("")){
			sb.append(" and illCard like '%"+illCard+"%'");
		}
		if(idCard!=null && !idCard.trim().equals("")){
			sb.append(" and idCard like '%"+idCard+"%'");
		}
		if(illCode!=null && !illCode.trim().equals("")){
			sb.append(" and illCode like '%"+illCode+"%'");
		}
		int rowCount = 0;
		
		 try {
			 conn = DbUtil.getConnection();
			 ptmt = conn.prepareStatement(sb.toString());
			 rs = ptmt.executeQuery();
			 while(rs.next()){
				 rowCount = rs.getInt(1);
			 }
		 }catch (Exception e) {
				e.printStackTrace();
				throw new RuntimeException();
			}finally {
				DbUtil.close(rs, ptmt, conn);
			}
		 return rowCount;
	}
		
	public List<IllCard> queryAndSearchPages(String illCard, String idCard, String illCode, Integer currentPage,
			Integer pageSize) {
		Connection conn = null;
	    PreparedStatement ptmt= null;
		ResultSet rs = null;
		 List<IllCard> result = new ArrayList<IllCard>();
		 StringBuilder sb=new StringBuilder();
		 int startNo = (currentPage-1)*pageSize;
		 sb.append("select * from t_ill_card where 1=1 ");
			if(illCard!=null && !illCard.trim().equals("")){
				sb.append(" and illCard like '%"+illCard+"%'");
			}
			if(idCard!=null && !idCard.trim().equals("")){
				sb.append(" and idCard like '%"+idCard+"%'");
			}
			if(illCode!=null && !illCode.trim().equals("")){
				sb.append(" and illCode like '%"+illCode+"%'");
			}
		 sb.append("order by illCard asc limit ?,?");
		 
		 try {
			 conn = DbUtil.getConnection();
			 ptmt = conn.prepareStatement(sb.toString());
			 ptmt.setInt(1, startNo);
			 ptmt.setInt(2, pageSize);
			 rs = ptmt.executeQuery();
			 IllCard model = null;
			 while(rs.next()) {
				 model = new IllCard();
				 model.setId(rs.getInt("id"));
				 model.setIllCard(rs.getString("illCard"));
				 model.setNongheCard(rs.getString("nongheCard"));
				 model.setIdCard(rs.getString("idCard"));
				 model.setIllCode(rs.getString("illCode"));
				 model.setAttachment(rs.getString("attachment"));
				 model.setStartTime(rs.getDate("startTime"));
				 model.setEndTime(rs.getDate("endTime"));
				 model.setRealName(rs.getString("realName"));
				 result.add(model);
			 }
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException();
		}finally {
			DbUtil.close(rs, ptmt, conn);
		}
		
		
		return result;
	}

	public IllCard querybyid(String id) {
			Connection conn =null;
			PreparedStatement ptmt =null;
			ResultSet rs =null;
			StringBuffer sb=new StringBuffer();
			
			 sb.append("select * from t_ill_card where id = ?");
			 try {
					conn = DbUtil.getConnection();
					ptmt = conn.prepareStatement(sb.toString());
					int index=1;
					ptmt.setInt(index, Integer.valueOf(id));
					rs = ptmt.executeQuery();
					IllCard entity = null;
					if (rs != null && rs.next()) {
						entity = new IllCard();
						entity.setId(rs.getInt("id"));
						entity.setIllCard(rs.getString("illCard"));
						entity.setNongheCard(rs.getString("nongheCard"));
						entity.setIdCard(rs.getString("idCard"));
						entity.setIllCode(rs.getString("illCode"));
						entity.setAttachment(rs.getString("attachment"));
						entity.setStartTime(rs.getDate("startTime"));
						entity.setEndTime(rs.getDate("endTime"));
						entity.setRealName(rs.getString("realName"));
						return entity;
					}
			 }catch (Exception e) {
					e.printStackTrace();
					throw new RuntimeException();
				}finally {
					DbUtil.close(rs, ptmt, conn);
				}
				return null;
			}

	public List<String> queryIllCodes(String selectyear, String idCard,String illCodez) {
		Connection conn =null;
		PreparedStatement ptmt =null;
		ResultSet rs =null;
		List<String> result = new ArrayList<String>();
		StringBuilder sb=new StringBuilder();
		sb.append("select illCode from t_ill_card where startTime like '"+selectyear+"%' and idCard = '"+idCard+"' and illCode ='"+illCodez+"'");
		
		try {
			 conn = DbUtil.getConnection();
			 ptmt = conn.prepareStatement(sb.toString());
			 rs = ptmt.executeQuery();
			 while(rs.next()) {
				 String illCode=rs.getString("illCode");
				 result.add(illCode);
			 }
		 }catch (Exception e) {
				e.printStackTrace();
			}finally {
				DbUtil.close(rs, ptmt, conn);
			}
			return result;
		
	}

}
