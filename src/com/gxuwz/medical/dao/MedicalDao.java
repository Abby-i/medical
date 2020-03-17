package com.gxuwz.medical.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.gxuwz.medical.database.DbUtil;
import com.gxuwz.medical.domain.medical.Medical;

public class MedicalDao extends GenericDao<Medical>{

	@Override
	protected Medical handle(ResultSet rs) throws SQLException {
		
		Medical entity =new Medical();
		entity.setJgbm(rs.getString("jgbm"));
		entity.setZzjgbm(rs.getString("zzjgbm"));
		entity.setJgmc(rs.getString("jgmc"));
		entity.setDqbm(rs.getString("dqbm"));
		entity.setAreacode(rs.getString("areacode"));
		entity.setLsgx(rs.getString("lsgx"));
		entity.setJgjb(rs.getString("jgjb"));
		entity.setSbddlx(rs.getString("sbddlx"));
		entity.setPzddlx(rs.getString("pzddlx"));
		entity.setSsjjlx(rs.getString("ssjjlx"));
		entity.setWsjgdl(rs.getString("wsjgdl"));
		entity.setWsjgxl(rs.getString("wsjgxl"));
		entity.setZgdw(rs.getString("zgdw"));
		entity.setKysj(rs.getDate("kysj"));
		entity.setFrdb(rs.getString("frdb"));
		entity.setZczj(rs.getDouble("zczj"));
		
		return entity;
	}

	public Medical queryById(String jgbm) {
		Connection conn =null;
		PreparedStatement ptmt=null;
		ResultSet rs=null;
		try {
			conn = DbUtil.getConnection();
			StringBuffer sb=new StringBuffer("select * from t_medical where jgbm = ?");
			ptmt=conn.prepareStatement(sb.toString());
			ptmt.setString(1, jgbm);
			rs=ptmt.executeQuery();
			
			Medical m = null;
			while(rs.next()) {
				m = new Medical();
				m.setJgbm(rs.getString("jgbm"));
				m.setZzjgbm(rs.getString("zzjgbm"));
				m.setJgmc(rs.getString("jgmc"));
				m.setDqbm(rs.getString("dqbm"));
				m.setAreacode(rs.getString("areacode"));
				m.setLsgx(rs.getString("lsgx"));
				m.setJgjb(rs.getString("jgjb"));
				m.setSbddlx(rs.getString("sbddlx"));
				m.setPzddlx(rs.getString("pzddlx"));
				m.setSsjjlx(rs.getString("ssjjlx"));
				m.setWsjgdl(rs.getString("wsjgdl"));
				m.setWsjgxl(rs.getString("wsjgxl"));
				m.setZgdw(rs.getString("zgdw"));
				m.setKysj(rs.getDate("kysj"));
				m.setFrdb(rs.getString("frdb"));
				m.setZczj(rs.getDouble("zczj"));
				return m;
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			DbUtil.close(rs, ptmt, conn);
		}
		return null;
		
	}

}
