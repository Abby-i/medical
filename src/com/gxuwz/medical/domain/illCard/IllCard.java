package com.gxuwz.medical.domain.illCard;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Date;

import com.gxuwz.medical.database.DbUtil;

public class IllCard {
	private int id;
	private String illCard;//慢性病证号
	private String nongheCard;//农合证号
	private String idCard;//身份证号*
	private String illCode;//疾病编码*
	private String realName;//文件名*
	private String attachment;//证明附件（存到数据库的名字）*
	private Date startTime;//起始时间*
	private Date endTime;//终止时间*
	
	public IllCard() {}

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	



	public IllCard(int id, String illCard, String nongheCard, String idCard, String illCode, String realName,
			String attachment, Date startTime, Date endTime) {
		super();
		this.id = id;
		this.illCard = illCard;
		this.nongheCard = nongheCard;
		this.idCard = idCard;
		this.illCode = illCode;
		this.realName = realName;
		this.attachment = attachment;
		this.startTime = startTime;
		this.endTime = endTime;
	}

	public IllCard(String illCard, String nongheCard, String idCard, String illCode, String realName, String attachment,
			Date startTime, Date endTime) {
		super();
		this.illCard = illCard;
		this.nongheCard = nongheCard;
		this.idCard = idCard;
		this.illCode = illCode;
		this.realName = realName;
		this.attachment = attachment;
		this.startTime = startTime;
		this.endTime = endTime;
	}
	
	@Override
	public String toString() {
		return "IllCard [id=" + id + ", illCard=" + illCard + ", nongheCard=" + nongheCard + ", idCard=" + idCard
				+ ", illCode=" + illCode + ", realName=" + realName + ", attachment=" + attachment + ", startTime="
				+ startTime + ", endTime=" + endTime + "]";
	}

	public String getIllCard() {
		return illCard;
	}

	public void setIllCard(String illCard) {
		this.illCard = illCard;
	}

	public String getNongheCard() {
		return nongheCard;
	}

	public void setNongheCard(String nongheCard) {
		this.nongheCard = nongheCard;
	}

	public String getIdCard() {
		return idCard;
	}

	public void setIdCard(String idCard) {
		this.idCard = idCard;
	}

	public String getIllCode() {
		return illCode;
	}

	public void setIllCode(String illCode) {
		this.illCode = illCode;
	}

	public String getAttachment() {
		return attachment;
	}

	public void setAttachment(String attachment) {
		this.attachment = attachment;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void add() throws Exception {
		Connection conn=null;
		try{
			conn =DbUtil.getConnection();
			//开启手动提交事务
			conn.setAutoCommit(false);
			//保存到数据库
			saveToDB(conn);
			//提交事务
			conn.commit();
		}catch (Exception e) {
			if(conn!=null){
				conn.rollback();
			}
			throw e;
		}finally{
			DbUtil.close(conn);
		}
	}

	private void saveToDB(Connection conn) throws SQLException {
		  PreparedStatement ptmt=null;
		  try{
			  StringBuffer sqlBuff=new StringBuffer("insert into t_ill_card("
			  		+ "illCard,nongheCard,idCard,illCode,realName,attachment,startTime,endTime)");
			  sqlBuff.append("values(?,?,?,?,?,?,?,?)");
			  ptmt=conn.prepareStatement(sqlBuff.toString());
			  ptmt.setString(1, this.illCard);
			  ptmt.setString(2, this.nongheCard);
			  ptmt.setString(3, this.idCard);
			  ptmt.setString(4, this.illCode);
			  ptmt.setString(5, this.realName);
			  ptmt.setString(6, this.attachment);
			  ptmt.setDate(7, new java.sql.Date(this.startTime.getTime()));
			  ptmt.setDate(8, new java.sql.Date(this.endTime.getTime()));
			  ptmt.executeUpdate(); 
		  }catch(SQLException e){
			  throw e;
		  }finally{
			  DbUtil.close(ptmt);
		  } 
		
	}

	public void del(String id2) throws Exception {
		Connection conn=null;
		PreparedStatement ptmt=null;
		try{
			conn = DbUtil.getConnection();
			conn.setAutoCommit(false);
			
			StringBuffer sqlBuff=new StringBuffer("delete from t_ill_card where id=?");
			ptmt=conn.prepareStatement(sqlBuff.toString());
			int id = Integer.valueOf(id2);
			ptmt.setInt(1, id);
			ptmt.executeUpdate(); 
			conn.commit();
		}catch(SQLException e){
			conn.rollback();
			throw new SQLException("Failed to delete record from table !", e);
		}finally {
			DbUtil.close(conn);
		}
		
		
	}

	public void edit() throws Exception {
		Connection conn = null;
		try {
			conn = DbUtil.getConnection();
			// 1：开启手动提交事务
			conn.setAutoCommit(false);
			eidtToDB(conn);
			// 4：提交事务
			conn.commit();
		} catch (SQLException e) {
			conn.rollback();
			throw e;
		} finally {
			DbUtil.close(conn);
		}
	}

	private void eidtToDB(Connection conn) throws Exception {
		PreparedStatement ptmt = null;
		try{
			  StringBuffer sb = new StringBuffer("update t_ill_card t set "
			  		+ "illCard=?,nongheCard=?,idCard=?,illCode=?,realName=?,attachment=?,startTime=?,endTime=? "
			  		+ " where id=?");
			  ptmt=conn.prepareStatement(sb.toString());
			  ptmt.setString(1, this.illCard);
			  ptmt.setString(2, this.nongheCard);
			  ptmt.setString(3, this.idCard);
			  ptmt.setString(4, this.illCode);
			  ptmt.setString(5, this.realName);
			  ptmt.setString(6, this.attachment);
			  ptmt.setDate(7, new java.sql.Date(this.startTime.getTime()));
			  ptmt.setDate(8, new java.sql.Date(this.endTime.getTime()));
			  ptmt.setInt(9, this.id);
			  ptmt.executeUpdate(); 
		  }catch(SQLException e){
			  throw new SQLException("Failed to update t_ill_card  !", e);
		  }finally{
			  DbUtil.close(ptmt);
		  }
	}

	
	
}
