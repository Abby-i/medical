package com.gxuwz.medical.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.gxuwz.medical.database.DbUtil;
import com.gxuwz.medical.domain.illExpense.IllExpense;
import com.gxuwz.medical.domain.payRecord.PayRecord;


public class IllExpenseDao extends GenericDao<IllExpense>{
	Connection conn = null;
    PreparedStatement ptmt= null;
	ResultSet rs = null;

	@Override
	protected IllExpense handle(ResultSet rs) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

		public PayRecord queryIsCanhe(String nongheCard,String idCard,String selectyear) {
		Connection conn =null;
		PreparedStatement ptmt=null;
		ResultSet rs=null;
		try {
			conn = DbUtil.getConnection();
			StringBuffer sb=new StringBuffer("SELECT * from t_pay_record where 1=1 ");
			if(nongheCard!=null && !nongheCard.trim().equals("")){
				sb.append(" and joinNum = '"+nongheCard+"'"+"and payTime like '"+selectyear+"%'");
			}
			if(idCard!=null && !idCard.trim().equals("")){
				sb.append(" and cardid = '"+idCard+"'"+"and payTime like '"+selectyear+"%'" );
			}
			ptmt=conn.prepareStatement(sb.toString());
			rs=ptmt.executeQuery();
			 PayRecord model = new PayRecord();
			if(rs.next()){
				model.setHomeid(rs.getString("homeid"));
				model.setHousehold(rs.getString("household"));
				model.setCardid(rs.getString("cardid"));
				model.setJoinNum(rs.getString("joinNum"));
				model.setInvoiceNum(rs.getString("invoiceNum"));
				model.setName(rs.getString("name"));
				model.setPayAccount(rs.getDouble("payAccount"));
				model.setPayTime(rs.getDate("payTime"));
				model.setOperator(rs.getString("operator"));
				return model;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			
		}finally{
			DbUtil.close(rs, ptmt, conn);
		}
		return null;
	}

		public int queryCount(String town, String village, String group, String name, String auditStatus) {
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
			if(auditStatus!=null && !auditStatus.trim().equals("")){
				sb.append(" and auditStatus = '"+auditStatus+"'");
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

		public List<IllExpense> queryExpense(String town, String village, String group, String name,
				String auditStatus, Integer currentPage, Integer pageSize) {
			
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
			 if(auditStatus!=null && !auditStatus.trim().equals("") && !auditStatus.trim().equals("1")){
					sb.append(" and auditStatus = '"+auditStatus+"'");
			 }
			 if(auditStatus!=null && !auditStatus.trim().equals("") && auditStatus.trim().equals("1")){
					sb.append(" and auditStatus != '"+0+"'");
			 }
			 sb.append("order by auditStatus and idCard asc limit ?,?");
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

		public IllExpense queryById(Integer id) {
			try {
				conn = DbUtil.getConnection();
				StringBuffer sb=new StringBuffer("select * from t_ill_expense where id = ?");
				ptmt=conn.prepareStatement(sb.toString());
				ptmt.setInt(1, id);
				rs=ptmt.executeQuery();
				
				IllExpense model = null;
				while(rs.next()) {
					model = new IllExpense();
					model.setId(rs.getInt("id"));
					model.setIllCard(rs.getString("illCard"));
					model.setIllcode(rs.getString("illCode"));
					model.setIllCard(rs.getString("illCard"));
					model.setHospitalName(rs.getString("hospitalName"));
					model.setMedicalCost(rs.getDouble("medicalCost"));
					model.setInvoiceNum(rs.getString("invoiceNum"));
					model.setClinicTime(rs.getDate("clinicTime"));
				 	model.setNative(rs.getBoolean("isNative"));
				 	model.setExpenseAccount(rs.getDouble("expenseAccount"));
				 	model.setExpenseTime(rs.getDate("expenseTime"));
				 	model.setOrganization(rs.getString("organization"));
				 	model.setAuditStatus(rs.getString("auditStatus"));
				 	model.setRemittanceStatus(rs.getString("remittanceStatus"));
				 	model.setIdCard(rs.getString("idCard"));
				 	model.setNongheCard(rs.getString("nongheCard"));
				 	model.setOperator(rs.getString("operator"));
				 	model.setName(rs.getString("name"));
				 	model.setDetails(rs.getString("details"));
					return model;
				}
			}catch(Exception e){
				e.printStackTrace();
			}finally{
				DbUtil.close(rs, ptmt, conn);
			}
			return null;
		}

	
}
