package com.gxuwz.medical.domain.illExpense;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Date;

import com.gxuwz.medical.database.DbUtil;
import com.gxuwz.medical.domain.chronicdis.Chronicdis;
import com.gxuwz.medical.domain.util.AgeUtil;

public class IllExpense {
	private Integer id;
	private String illCard;//慢性病证号
	private String illcode;//疾病编码
	private String hospitalCode;//医院编号
	private String hospitalName;//医院名称
	private double medicalCost;//医疗总费用
	private String invoiceNum;//医院发票号
	private Date clinicTime;//就诊时间
	private boolean isNative;//是否本地
	private double expenseAccount;//报销金额
	private Date expenseTime;//报销时间
	private String organization;//报销机构
	private String auditStatus;//审核状态
	private String remittanceStatus;//汇款状态
	private String operator;//操作员
	private String name;//姓名
	private String idCard;
	private String nongheCard;
	private String details;
	private String agreetor;
	 Connection conn=null;
	 PreparedStatement ptmt=null;
	
	
	@Override
	public String toString() {
		return "IllExpense [illCard=" + illCard + ", illcode=" + illcode + ", hospitalCode=" + hospitalCode
				+ ", hospitalName=" + hospitalName + ", medicalCost=" + medicalCost + ", invoiceNum=" + invoiceNum
				+ ", clinicTime=" + clinicTime + ", isNative=" + isNative + ", expenseAccount=" + expenseAccount
				+ ", expenseTime=" + expenseTime + ", organization=" + organization + ", auditStatus=" + auditStatus
				+ ", remittanceStatus=" + remittanceStatus + ", operator=" + operator + ", idCard=" + idCard
				+ ", nongheCard=" + nongheCard + "]";
	}
	
	
	public String getDetails() {
		return details;
	}


	public void setDetails(String details) {
		this.details = details;
	}


	public String getAgreetor() {
		return agreetor;
	}

	public void setAgreetor(String agreetor) {
		this.agreetor = agreetor;
	}


	public IllExpense() {}
	
	
	public Integer getId() {
		return id;
	}


	public void setId(Integer id) {
		this.id = id;
	}


	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}


	public String getIdCard() {
		return idCard;
	}
	
	public String getNongheCard() {
		return nongheCard;
	}

	public void setNongheCard(String nongheCard) {
		this.nongheCard = nongheCard;
	}



	public void setIdCard(String idCard) {
		this.idCard = idCard;
	}

	public String getIllCard() {
		return illCard;
	}
	public void setIllCard(String illCard) {
		this.illCard = illCard;
	}
	
	public String getIllcode() {
		return illcode;
	}

	public void setIllcode(String illcode) {
		this.illcode = illcode;
	}

	public String getHospitalName() {
		return hospitalName;
	}
	public void setHospitalName(String hospitalName) {
		this.hospitalName = hospitalName;
	}
	public double getMedicalCost() {
		return medicalCost;
	}
	public void setMedicalCost(double medicalCost) {
		this.medicalCost = medicalCost;
	}
	public String getInvoiceNum() {
		return invoiceNum;
	}
	public void setInvoiceNum(String invoiceNum) {
		this.invoiceNum = invoiceNum;
	}
	public Date getClinicTime() {
		return clinicTime;
	}
	public void setClinicTime(Date clinicTime) {
		this.clinicTime = clinicTime;
	}
	public boolean isNative() {
		return isNative;
	}
	public void setNative(boolean isNative) {
		this.isNative = isNative;
	}
	public double getExpenseAccount() {
		return expenseAccount;
	}
	public void setExpenseAccount(double expenseAccount) {
		this.expenseAccount = expenseAccount;
	}
	public Date getExpenseTime() {
		return expenseTime;
	}
	public void setExpenseTime(Date expenseTime) {
		this.expenseTime = expenseTime;
	}
	public String getOrganization() {
		return organization;
	}
	public void setOrganization(String organization) {
		this.organization = organization;
	}
	public String getAuditStatus() {
		return auditStatus;
	}
	public void setAuditStatus(String auditStatus) {
		this.auditStatus = auditStatus;
	}
	public String getRemittanceStatus() {
		return remittanceStatus;
	}
	public void setRemittanceStatus(String remittanceStatus) {
		this.remittanceStatus = remittanceStatus;
	}
	public String getOperator() {
		return operator;
	}
	public void setOperator(String operator) {
		this.operator = operator;
	}

	public String getHospitalCode() {
		return hospitalCode;
	}

	public void setHospitalCode(String hospitalCode) {
		this.hospitalCode = hospitalCode;
	}



	public void add(String idCard, String illCode, String nongheCard, String medicalCost, double expenseAccount,
			String invoiceNum, String hospitalCode, Date clinicTime, String isNative, String remittanceStatus,
			String auditStatus, String operator,String name,String hospitalName) throws Exception {
		expenseTime = new Date();
		try{
			//加载父节点ID的对象
			//自动编号
			conn =DbUtil.getConnection();
			//开启手动提交事务
			conn.setAutoCommit(false);
			
			  StringBuffer sqlBuff=new StringBuffer("insert into t_ill_expense(idCard,illCode,nongheCard,medicalCost,expenseAccount,invoiceNum,hospitalCode,clinicTime,isNative,remittanceStatus,auditStatus,operator,expenseTime,name,hospitalName)");
			  sqlBuff.append("values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
			  ptmt=conn.prepareStatement(sqlBuff.toString());
			  ptmt.setString(1, idCard);
			  ptmt.setString(2, illCode);
			  ptmt.setString(3, nongheCard);
			  ptmt.setString(4, medicalCost);
			  ptmt.setDouble(5, expenseAccount);
			  //,invoiceNum,hospitalName,clinicTime,isNative,remittanceStatus,auditStatus,operator
			  ptmt.setString(6, invoiceNum);
			  ptmt.setString(7, hospitalCode);
			  ptmt.setDate(8, new java.sql.Date(clinicTime.getTime()));
			  ptmt.setString(9, isNative);
			  ptmt.setString(10, remittanceStatus);
			  ptmt.setString(11, auditStatus);
			  ptmt.setString(12, operator);
			  ptmt.setDate(13, new java.sql.Date(expenseTime.getTime()));
			  ptmt.setString(14, name);
			  ptmt.setString(15, hospitalName);
			  ptmt.executeUpdate(); 
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


	public void update(String auditStatus,String details,String agreetor, String id) throws SQLException {
		 try {
			  conn = DbUtil.getConnection();
			  conn.setAutoCommit(false);
			  StringBuffer sqlBuff=new StringBuffer("update t_ill_expense set auditStatus=?,details=?,agreetor=? where id=?");
			  ptmt=conn.prepareStatement(sqlBuff.toString());
			  if(auditStatus.equals("已审核")){
				  auditStatus = "1";
			  }else if(auditStatus.equals("未审核")){
				  auditStatus = "0";
			  }else if(auditStatus.equals("同意")){
				  auditStatus = "2";
			  }else if(auditStatus.equals("不同意")){
				  auditStatus = "3";
			  }
			  System.out.println("auditStatus:"+auditStatus);
			  ptmt.setString(1, auditStatus);
			  ptmt.setString(2, details);
			  ptmt.setString(3, agreetor);
			  ptmt.setInt(4, Integer.valueOf(id));
			  ptmt.executeUpdate(); 
			  conn.commit();
		 }catch (SQLException e) {
				if(conn!=null){
					conn.rollback();
				}
				throw new SQLException("Failed to update t_area  !", e);
			}finally{
				 DbUtil.close(ptmt, conn);
			}
		
	}


	public void del(String id) throws SQLException {
		try{
			conn = DbUtil.getConnection();
			conn.setAutoCommit(false);
			
			StringBuffer sqlBuff=new StringBuffer("delete from t_ill_expense where id=?");
			ptmt=conn.prepareStatement(sqlBuff.toString());
			ptmt.setInt(1, Integer.valueOf(id));
			ptmt.executeUpdate(); 
			
			 conn.commit();
		}catch(SQLException e){
			conn.rollback();
			throw new SQLException("Failed to delete record from table !", e);
		}finally {
			DbUtil.close(conn);
		}
		
	}


	public void update(String remittanceStatus, String agreetor, String id) throws SQLException {
		
		 try {
			  conn = DbUtil.getConnection();
			  conn.setAutoCommit(false);
			  StringBuffer sqlBuff=new StringBuffer("update t_ill_expense set remittanceStatus=?,agreetor=? where id=?");
			  ptmt=conn.prepareStatement(sqlBuff.toString());
			 if(remittanceStatus.equals("已汇款")){
				 remittanceStatus = "1";
			 }else if(remittanceStatus.equals("未汇款")){
				 remittanceStatus = "0";
			 }
			 System.out.println(remittanceStatus+"草拟吗");
			  ptmt.setString(1, remittanceStatus);
			  ptmt.setString(2, agreetor);
			  ptmt.setInt(3, Integer.valueOf(id));
			  ptmt.executeUpdate(); 
			  conn.commit();
		 }catch (SQLException e) {
				if(conn!=null){
					conn.rollback();
				}
				throw new SQLException("Failed to update t_ill_expense  !", e);
			}finally{
				 DbUtil.close(ptmt, conn);
			}
		
	}
	
}
