package com.gxuwz.medical.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.gxuwz.medical.database.DbUtil;
import com.gxuwz.medical.domain.area.Area;
import com.gxuwz.medical.domain.homearchives.Homearchives;
import com.gxuwz.medical.domain.role.Role;
import com.gxuwz.medical.domain.user.User;

public class HomearchivesDao extends GenericDao<Homearchives>{

	@Override
	protected Homearchives handle(ResultSet rs) throws SQLException {
		try{
			Homearchives entity=new Homearchives();
			entity.setId(rs.getString("id"));
			entity.setCountyid(rs.getString("countyid"));
			entity.setTownid(rs.getString("townid"));
			entity.setVillageid(rs.getString("villageid"));
			entity.setGroupid(rs.getString("groupid"));
			entity.setHomeid(rs.getString("homeid"));
			entity.setProperty(rs.getString("property"));
			entity.setHousehold(rs.getString("household"));
			entity.setFamilysize(rs.getInt("familysize"));
			entity.setFarmersize(rs.getInt("farmersize"));
			entity.setAddress(rs.getString("address"));
			entity.setCreatetime(rs.getDate("createtime"));
			entity.setRegistrar(rs.getString("registrar"));
			
			return entity;
		}catch(SQLException e){
			throw new SQLException("结果集转为实例失败!",e);
		}
	}

	/**
	 * 查询出最大id
	 * @return
	 */
	public String getMaxid(){
		Connection conn =null;
		PreparedStatement ptmt =null;
		ResultSet rs =null;
		StringBuilder sb=new StringBuilder();
		sb.append("select MAX(homeid) from t_homearchives");
		try {
			 conn = DbUtil.getConnection();
			 ptmt = conn.prepareStatement(sb.toString());
			 rs = ptmt.executeQuery();
			 while(rs.next()) {
				 return rs.getString("MAX(homeid)");
			 }
		 } catch (Exception e) {
				e.printStackTrace();
			}finally {
				DbUtil.close(rs, ptmt, conn);
			}
		return null;
	}
	
	public Homearchives querybyId(String homeid){
		Connection conn =null;
		PreparedStatement ptmt =null;
		ResultSet rs =null;
		StringBuffer sb=new StringBuffer();
		 sb.append("select * from t_homearchives where homeid = ?");
		
		 try {
			conn = DbUtil.getConnection();
			ptmt = conn.prepareStatement(sb.toString());
			int index=1;
			ptmt.setString(index, homeid);
			rs = ptmt.executeQuery();
			Homearchives homearchives = new Homearchives();
			if (rs != null && rs.next()) {
				homearchives.setCountyid(rs.getString("countyid"));
				homearchives.setTownid(rs.getString("townid"));
				homearchives.setVillageid(rs.getString("villageid"));
				homearchives.setGroupid(rs.getString("groupid"));
				homearchives.setHomeid(rs.getString("homeid"));
				homearchives.setProperty(rs.getString("property"));
				homearchives.setHousehold(rs.getString("household"));
				homearchives.setFamilysize(rs.getInt("familysize"));
				homearchives.setFarmersize(rs.getInt("farmersize"));
				homearchives.setAddress(rs.getString("address"));
				homearchives.setCreatetime(rs.getDate("createtime"));
				homearchives.setRegistrar(rs.getString("registrar"));
				return homearchives;
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException();
		}finally {
			DbUtil.close(rs, ptmt, conn);
		}
		return null;
		
	}
	
	//根据组编号生成家庭编号
	public String Tohomeid(String groupid){
		Connection conn =null;
		PreparedStatement ptmt =null;
		ResultSet rs =null;
		StringBuilder sb=new StringBuilder();
		sb.append("select MAX(homeid) from t_homearchives where homeid like '"+groupid+"%'");
		try {
			 conn = DbUtil.getConnection();
			 ptmt = conn.prepareStatement(sb.toString());
			 rs = ptmt.executeQuery();
			 while(rs.next()) {
				 return rs.getString("MAX(homeid)");
			 }
		 } catch (Exception e) {
				e.printStackTrace();
			}finally {
				DbUtil.close(rs, ptmt, conn);
			}
		return null;
	}
	

	public static void main(String[] args) {
		HomearchivesDao dao =new HomearchivesDao();
		System.out.println(dao.getMaxid());
	}
	
	
	public int queryCount(String household) {
		Connection conn = null;
	    PreparedStatement ptmt= null;
		ResultSet rs = null;
		
		StringBuffer sb=new StringBuffer("select count(*) from t_homearchives where 1=1 ");
		if(household!=null && !household.trim().equals("")){
			sb.append(" and household like '%"+household+"%'");
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

	public List<Homearchives> queryHomearchives(String household,Integer currentPage, Integer pageSize) {
		Connection conn = null;
	    PreparedStatement ptmt= null;
		ResultSet rs = null;
		 List<Homearchives> result = new ArrayList<Homearchives>();
		 StringBuilder sb=new StringBuilder();
		 int startNo = (currentPage-1)*pageSize;
		 sb.append("select * from t_homearchives where 1=1 ");
		 if(household!=null && !household.trim().equals("")){
				sb.append(" and household like '%"+household+"%'");
			}
		 sb.append("order by homeid asc limit ?,?");
		 try {
			 conn = DbUtil.getConnection();
			 ptmt = conn.prepareStatement(sb.toString());
			 ptmt.setInt(1, startNo);
			 ptmt.setInt(2, pageSize);
			 rs = ptmt.executeQuery();
			 
			 Homearchives homearchives = null;
			 while(rs.next()) {
				 	homearchives = new Homearchives();
				 	homearchives.setCountyid(rs.getString("countyid"));
					homearchives.setTownid(rs.getString("townid"));
					homearchives.setVillageid(rs.getString("villageid"));
					homearchives.setGroupid(rs.getString("groupid"));
					homearchives.setHomeid(rs.getString("homeid"));
					homearchives.setProperty(rs.getString("property"));
					homearchives.setHousehold(rs.getString("household"));
					homearchives.setFamilysize(rs.getInt("familysize"));
					homearchives.setFarmersize(rs.getInt("farmersize"));
					homearchives.setAddress(rs.getString("address"));
					homearchives.setCreatetime(rs.getDate("createtime"));
					homearchives.setRegistrar(rs.getString("registrar"));
				 result.add(homearchives);
			 }
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException();
		}finally {
			DbUtil.close(rs, ptmt, conn);
		}
		return result;
	}

}
