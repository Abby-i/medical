package com.gxuwz.medical.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.gxuwz.medical.database.DbUtil;
import com.gxuwz.medical.domain.illExpense.IllExpense;

public class RemittanceExpenseDao extends GenericDao<IllExpense>{
	
	Connection conn =null;
	PreparedStatement ptmt =null;
	ResultSet rs =null;

	@Override
	protected IllExpense handle(ResultSet rs) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	public int queryCount(String town, String village, String group, String name, String remittanceStatus) {
		StringBuffer sb=new StringBuffer("select count(*) from t_ill_expense where 1=1 ");
		if(town!=null && !town.trim().equals("")){
			sb.append(" and nongheCard like '"+town+"%'");
		}
		if(village!=null && !village.trim().equals("")){
			sb.append(" and nongheCard like '"+village+"%'");
		}
		if(group!=null && !group.trim().equals("")){
			sb.append(" and nongheCard like '"+group+"%'");
		}
		if(name!=null && !name.trim().equals("")){
			sb.append(" and name like '%"+name+"%'");
		}
		if(remittanceStatus!=null && !remittanceStatus.trim().equals("") && remittanceStatus.trim().equals("1")){
				sb.append(" and remittanceStatus = '"+1+"'");
		 }
		 if(remittanceStatus!=null && !remittanceStatus.trim().equals("") && remittanceStatus.trim().equals("0")){
				sb.append(" and remittanceStatus = '"+0+"'");
		 }
		 sb.append(" and auditStatus = '"+2+"'");
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

	public List<IllExpense> queryExpense(String town, String village, String group, String name,
			String remittanceStatus, Integer currentPage, Integer pageSize) {
		 List<IllExpense> result = new ArrayList<IllExpense>();
		 StringBuilder sb=new StringBuilder();
		 int startNo = (currentPage-1)*pageSize;
		 sb.append("select * from t_ill_expense where 1=1 ");
		 if(town!=null && !town.trim().equals("")){
				sb.append(" and nongheCard like '"+town+"%'");
		 }
		 if(village!=null && !village.trim().equals("")){
				sb.append(" and nongheCard like '"+village+"%'");
		 }
		 if(group!=null && !group.trim().equals("")){
				sb.append(" and nongheCard like '"+group+"%'");
		 }
		 if(name!=null && !name.trim().equals("")){
				sb.append(" and name = '"+name+"'");
		 }
		 if(remittanceStatus!=null && !remittanceStatus.trim().equals("") && remittanceStatus.trim().equals("1")){
				sb.append(" and remittanceStatus = '"+1+"'");
		 }
		 if(remittanceStatus!=null && !remittanceStatus.trim().equals("") && remittanceStatus.trim().equals("0")){
				sb.append(" and remittanceStatus = '"+0+"'");
		 }
		 sb.append(" and auditStatus = '"+2+"'");
		 sb.append("order by remittanceStatus and idCard asc limit ?,?");
		 try {
			 conn = DbUtil.getConnection();
			 ptmt = conn.prepareStatement(sb.toString());
			 ptmt.setInt(1, startNo);
			 ptmt.setInt(2, pageSize);
			 rs = ptmt.executeQuery();
			 
			 IllExpense illExpense = null;
			 while(rs.next()) {
				 	illExpense = new IllExpense();
				 	illExpense.setId(rs.getInt("id"));
				 	illExpense.setIllCard(rs.getString("illCard"));
				 	illExpense.setIllcode(rs.getString("illCode"));
				 	illExpense.setIllCard(rs.getString("illCard"));
				 	illExpense.setHospitalName(rs.getString("hospitalName"));
				 	illExpense.setMedicalCost(rs.getDouble("medicalCost"));
				 	illExpense.setInvoiceNum(rs.getString("invoiceNum"));
				 	illExpense.setClinicTime(rs.getDate("clinicTime"));
				 	illExpense.setNative(rs.getBoolean("isNative"));
				 	illExpense.setExpenseAccount(rs.getDouble("expenseAccount"));
				 	illExpense.setExpenseTime(rs.getDate("expenseTime"));
				 	illExpense.setOrganization(rs.getString("organization"));
				 	illExpense.setAuditStatus(rs.getString("auditStatus"));
				 	illExpense.setRemittanceStatus(rs.getString("remittanceStatus"));
				 	illExpense.setIdCard(rs.getString("idCard"));
				 	illExpense.setNongheCard(rs.getString("nongheCard"));
				 	illExpense.setOperator(rs.getString("operator"));
				 	illExpense.setName(rs.getString("name"));
				 	illExpense.setHospitalCode(rs.getString("hospitalCode"));
				 	illExpense.setDetails(rs.getString("details"));
				 	illExpense.setAgreetor(rs.getString("agreetor"));
					result.add(illExpense);
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
