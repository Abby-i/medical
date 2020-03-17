package com.gxuwz.medical.domain.medical;

import java.sql.SQLException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Date;

import com.gxuwz.medical.database.DbUtil;
import com.gxuwz.medical.domain.farmer.Farmer;

/**
 * 医疗机构管理类
 * @author 演示
 *
 */
public class Medical {
	/**
	 * 机构编码
	 */
	private String jgbm;
	/**
	 * 组织机构编码
	 */
	private String zzjgbm;
	/**
	 * 机构名称
	 */
	private String jgmc;
	/**
	 * 地区编码
	 */
	private String dqbm;
	/**
	 * 行政区域编码
	 */
	private String areacode;
	/**
	 * 隶属关系
	 */
	private String lsgx;
	
	 public String getLsgxName(){
	       String lsgxName="";
		   if(this.lsgx!=null){
				 try{
			      S201 s201=new S201(this.lsgx, "02");
			      lsgxName= s201.getItemname();
				 }catch(Exception e){
					 ;
				 }
		   }
		 return lsgxName;
	 }
	/**
	 * 机构级别
	 */
	private String jgjb;
	/**
	 * 申报定点类型
	 */
	private String sbddlx;
	/**
	 * 批准定点类型
	 */
	private String pzddlx;
	/**
	 * 所属经济类型
	 */
	private String ssjjlx;
	/**
	 * 卫生机构大类
	 */
	private String wsjgdl;
	/**
	 * 卫生机构小类
	 */
	private String wsjgxl;
	
	/**
	 * 主管单位
	 */
	private String zgdw;
	/**
	 * 开业时间
	 */
	private Date kysj;
	/**
	 * 法人代表
	 */
	private String frdb;
	/**
	 * 注册资金
	 */
	private double zczj;
	
	public Medical(){
		
	}
	/**
	 * 添加医疗机构
	 * @throws SQLException
	 */
	public void add()throws SQLException{
		Connection conn =null;
		try{
			//1:获得连接
			 conn=DbUtil.getConnection();
			//2;保存到数据库
			conn.setAutoCommit(false);
			saveToDB(conn);
			conn.commit();
		}catch(SQLException e){
			conn.rollback();
			e.printStackTrace();
			throw e;
		}finally{
			DbUtil.close(conn);
		}
	}
	/**
	 * 全参构造函数
	 * @param jgbm 机构编码
	 * @param zzjgbm 组织机构编码
	 * @param jgmc 机构名称
	 * @param dqbm 地区编码
	 * @param areacode 行政区域编码
	 * @param lsgx 隶属关系
	 * @param jgjb 机构级别
	 * @param sbddlx 申报定点类型
	 * @param pzddlx 批准定点类型
	 * @param ssjjlx 所属经济类型
	 * @param wsjgdl 卫生机构大类
	 * @param wsjgxl 卫生机构小类
	 * @param zgdw 主管单位
	 * @param kysj 开业时间
	 * @param frdb 法人代表
	 * @param zczj 注册资金
	 */
	public Medical(String jgbm, String zzjgbm, String jgmc, String dqbm,
			String areacode, String lsgx, String jgjb, String sbddlx,
			String pzddlx, String ssjjlx, String wsjgdl, String wsjgxl,
			String zgdw, Date kysj, String frdb, double zczj) {
		super();
		this.jgbm = jgbm;
		this.zzjgbm = zzjgbm;
		this.jgmc = jgmc;
		this.dqbm = dqbm;
		this.areacode = areacode;
		this.lsgx = lsgx;
		this.jgjb = jgjb;
		this.sbddlx = sbddlx;
		this.pzddlx = pzddlx;
		this.ssjjlx = ssjjlx;
		this.wsjgdl = wsjgdl;
		this.wsjgxl = wsjgxl;
		this.zgdw = zgdw;
		this.kysj = kysj;
		this.frdb = frdb;
		this.zczj = zczj;
	}
	
	
	
	public Date getKysj() {
		return kysj;
	}
	public void setKysj(Date kysj) {
		this.kysj = kysj;
	}
	private void saveToDB(Connection conn)throws SQLException{
		PreparedStatement pstmt=null;
		try{
			
			StringBuffer sqlBuff=new StringBuffer();
			sqlBuff.append("insert into t_medical( jgbm, zzjgbm, jgmc, dqbm, areacode, lsgx, jgjb, "
					+ "sbddlx, pzddlx, ssjjlx, wsjgdl, wsjgxl, zgdw, kysj , frdb,zczj)");
			sqlBuff.append("values");
			sqlBuff.append("(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ? , ?,?)");
			String sql=sqlBuff.toString();
			pstmt=conn.prepareStatement(sql);
			//设置动态参数的值
			int index=1;
			pstmt.setString(index++, this.jgbm);
			pstmt.setString(index++, this.zzjgbm);
			pstmt.setString(index++, this.jgmc);
			pstmt.setString(index++, this.dqbm);
			pstmt.setString(index++, this.areacode);
			pstmt.setString(index++, this.lsgx);
			pstmt.setString(index++, this.jgjb);
			pstmt.setString(index++, this.sbddlx);
			pstmt.setString(index++, this.pzddlx);
			pstmt.setString(index++, this.ssjjlx);
			pstmt.setString(index++, this.wsjgdl);
			pstmt.setString(index++, this.wsjgxl);
			pstmt.setString(index++, this.zgdw);
			pstmt.setDate(index++, new java.sql.Date(this.kysj.getTime()));
			pstmt.setString(index++, this.frdb);
			pstmt.setDouble(index++, this.zczj);
			
			int count=pstmt.executeUpdate();
		}catch(SQLException e){
			e.printStackTrace();
			throw e;
		}finally{
			DbUtil.close(pstmt);
		}
		
	}
	
	
	
	public String getJgbm() {
		return jgbm;
	}

	public void setJgbm(String jgbm) {
		this.jgbm = jgbm;
	}

	public String getZzjgbm() {
		return zzjgbm;
	}

	public void setZzjgbm(String zzjgbm) {
		this.zzjgbm = zzjgbm;
	}

	public String getJgmc() {
		return jgmc;
	}

	public void setJgmc(String jgmc) {
		this.jgmc = jgmc;
	}

	public String getDqbm() {
		return dqbm;
	}

	public void setDqbm(String dqbm) {
		this.dqbm = dqbm;
	}

	public String getAreacode() {
		return areacode;
	}

	public void setAreacode(String areacode) {
		this.areacode = areacode;
	}

	public String getLsgx() {
		return lsgx;
	}

	public void setLsgx(String lsgx) {
		this.lsgx = lsgx;
	}

	public String getJgjb() {
		return jgjb;
	}

	public void setJgjb(String jgjb) {
		this.jgjb = jgjb;
	}

	public String getSbddlx() {
		return sbddlx;
	}

	public void setSbddlx(String sbddlx) {
		this.sbddlx = sbddlx;
	}

	public String getPzddlx() {
		return pzddlx;
	}

	public void setPzddlx(String pzddlx) {
		this.pzddlx = pzddlx;
	}

	public String getSsjjlx() {
		return ssjjlx;
	}

	public void setSsjjlx(String ssjjlx) {
		this.ssjjlx = ssjjlx;
	}

	public String getWsjgdl() {
		return wsjgdl;
	}

	public void setWsjgdl(String wsjgdl) {
		this.wsjgdl = wsjgdl;
	}

	public String getWsjgxl() {
		return wsjgxl;
	}

	public void setWsjgxl(String wsjgxl) {
		this.wsjgxl = wsjgxl;
	}

	public String getZgdw() {
		return zgdw;
	}

	public void setZgdw(String zgdw) {
		this.zgdw = zgdw;
	}



	public String getFrdb() {
		return frdb;
	}

	public void setFrdb(String frdb) {
		this.frdb = frdb;
	}

	public double getZczj() {
		return zczj;
	}

	public void setZczj(double zczj) {
		this.zczj = zczj;
	}
	public void delToDB(String jgbm) throws Exception {
		Connection conn = null;
		
		this.jgbm =jgbm;
		   try{
			   conn =DbUtil.getConnection();
			   //1：开启手动提交事务
			   conn.setAutoCommit(false);
			   //2:删除信息
			   deleteFromDB(conn);
			   //4：提交事务
			   conn.commit();
		}catch (Exception e) {
			conn.rollback();
			throw e;
		}finally{
			DbUtil.close(conn);
		}
		
	}
	private void deleteFromDB(Connection conn) {
		PreparedStatement ptmt=null;
		StringBuffer sqlBuff=new StringBuffer("delete from t_medical where jgbm=?");
		try {
			ptmt=conn.prepareStatement(sqlBuff.toString());
			ptmt.setString(1, this.jgbm); 
			ptmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			DbUtil.close(ptmt);
		}
		
	}
	
	public void editToDB(String jgbm, String zzjgbm, String jgmc, String dqbm,
			String areacode, String lsgx, String jgjb, String sbddlx,
			String pzddlx, String ssjjlx, String wsjgdl, String wsjgxl,
			String zgdw, Date kysj, String frdb, double zczj) throws Exception {
		Connection conn = null;
		PreparedStatement ptmt=null;
		StringBuffer sqlBuff=new StringBuffer("update t_medical t set t.jgbm=?,t.zzjgbm=?,t.jgmc=?,t.dqbm=?,t.areacode=?,"
				+ "t.lsgx=?,t.jgjb=?,t.sbddlx=?,t.pzddlx=?,t.ssjjlx=?,t.wsjgdl=?,t.wsjgxl=?,t.zgdw=?,t.frdb=?,t.zczj=? "
				+ "where jgbm=?");
		System.out.println("--------------");
		System.out.println(jgbm);
		System.out.println(zzjgbm);
		System.out.println(jgmc);
		System.out.println(dqbm);
		System.out.println(kysj);
		   try{
			   conn =DbUtil.getConnection();
			   //1：开启手动提交事务
			   conn.setAutoCommit(false);
			   //2:修改信息
				ptmt=conn.prepareStatement(sqlBuff.toString());
				int index=1;
				ptmt.setString(index++, jgbm);
				ptmt.setString(index++, zzjgbm);
				ptmt.setString(index++, jgmc);
				ptmt.setString(index++, dqbm);
				ptmt.setString(index++, areacode);
				ptmt.setString(index++, lsgx);
				ptmt.setString(index++, jgjb);
				ptmt.setString(index++, sbddlx);
				ptmt.setString(index++, pzddlx);
				ptmt.setString(index++, ssjjlx);
				ptmt.setString(index++, wsjgdl);
				ptmt.setString(index++, wsjgxl);
				ptmt.setString(index++, zgdw);
				/*ptmt.setDate(index++,   new java.sql.Date(this.kysj.getTime()) );*/
				ptmt.setString(index++, frdb);
				ptmt.setDouble(index++, zczj);
				ptmt.setString(index++, jgbm);
				ptmt.executeUpdate();
			   //4：提交事务
			   conn.commit();
		}catch (Exception e) {
			e.printStackTrace();
			conn.rollback();
			throw e;
		}finally{
			DbUtil.close(ptmt, conn);
		}
		
	}
	
	
	@Override
	public String toString() {
		return "Medical [jgbm=" + jgbm + ", zzjgbm=" + zzjgbm + ", jgmc=" + jgmc + ", dqbm=" + dqbm + ", areacode="
				+ areacode + ", lsgx=" + lsgx + ", jgjb=" + jgjb + ", sbddlx=" + sbddlx + ", pzddlx=" + pzddlx
				+ ", ssjjlx=" + ssjjlx + ", wsjgdl=" + wsjgdl + ", wsjgxl=" + wsjgxl + ", zgdw=" + zgdw + ", kysj="
				+ kysj + ", frdb=" + frdb + ", zczj=" + zczj + "]";
	}
	

	

}
