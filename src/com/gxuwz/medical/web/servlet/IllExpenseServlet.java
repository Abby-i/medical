package com.gxuwz.medical.web.servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.gxuwz.medical.dao.ChronicdisDao;
import com.gxuwz.medical.dao.IllCardDao;
import com.gxuwz.medical.dao.IllExpenseDao;
import com.gxuwz.medical.dao.PolicyDao;
import com.gxuwz.medical.dao.S201Dao;
import com.gxuwz.medical.domain.chronicdis.Chronicdis;
import com.gxuwz.medical.domain.illExpense.IllExpense;
import com.gxuwz.medical.domain.medical.S201;
import com.gxuwz.medical.domain.payRecord.PayRecord;
import com.gxuwz.medical.domain.policy.Policy;
import com.gxuwz.medical.domain.util.AgeUtil;

/**
 * Servlet implementation class IllExpenseServlet
 */
public class IllExpenseServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;
	PolicyDao policyDao = new PolicyDao();
	IllCardDao illCardDao = new IllCardDao();
	IllExpense illExpense = new IllExpense();
	ChronicdisDao chronicdisDao = new ChronicdisDao();
	S201Dao s201Dao = new S201Dao();
       
   
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String m =request.getParameter("m");
		if("list".equals(m)){
			list(request,response);
		}else if("input".equals(m)){
			input(request,response);
		}else if("add".equals(m)){
			add(request,response);
		}else if("del".equals(m)){
			
		} else if("edit".equals(m)){
			
		}else if("search".equals(m)){
			search(request,response);
		}
	}

	private void add(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String idCard =request.getParameter("idCard");//身份证号
		String illCode =request.getParameter("illCode");//疾病编码
		String nongheCard =request.getParameter("nongheCard");//农合证号
		String medicalCost =request.getParameter("medicalCost");//医疗总费用
		String invoiceNum =request.getParameter("invoiceNum");//发票号
		String hospitalCode =request.getParameter("hospitalCode");//医院编号
		String clinicTimestr =request.getParameter("clinicTime");//就诊时间
		String isNative =request.getParameter("isNative");//是否本地
		String remittanceStatus =request.getParameter("remittanceStatus");//汇款状态
		String auditStatus =request.getParameter("auditStatus");//审核状态
		String operator =request.getParameter("operator");//
		String selectyear =request.getParameter("selectyear");
		String name =request.getParameter("name");
		System.out.println("hospitalCode:"+hospitalCode);
		
		S201 s201 = s201Dao.queryById(hospitalCode);
		String hospitalName = s201.getItemname();
		System.out.println("hospitalName:"+hospitalName);
		
		System.out.println("idCard:"+idCard);
		Date clinicTime = null;
		try {
			clinicTime = AgeUtil.toDateFormat(clinicTimestr);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		//如果illcode存在不为空。则符合条件select illCode from t_ill_card where selectyear like '"+selectyear+"%' and idCard = ?
		List<String> illCard_illCodes =  illCardDao.queryIllCodes(selectyear,idCard,illCode);
		System.out.println(illCard_illCodes.toString());
		if(illCard_illCodes.size()==0){
			prompt(request, response, "报销的慢性病与慢性病上的疾病不一致或慢性病证已过期");
			return;
		}
		
		//系统查询当前年度慢性病封顶线及报销比例
		Policy policy = policyDao.queryCeiling(selectyear);
		double ceiling = policy.getCeiling();//封顶线
		double ratio = policy.getRatio();//报销比例
		
		//系统预计算本次报销金额；
		double cost = Double.valueOf(medicalCost);
		System.out.println("cost:"+cost);
		double expense =cost*ratio;
		System.out.println("expense:"+expense);
		
		//系统计算当前年度慢性病已报销总金额；
		double totalExpense = policyDao.CountExpense(selectyear,idCard);
		System.out.println("totalExpense:"+totalExpense);
		
		//系统计算本次报销金额；
		if((ceiling-totalExpense)>=expense){
			//存预报销金额：
			double expenseAccount = expense;
			try {
				illExpense.add(idCard,illCode,nongheCard,medicalCost,expenseAccount,invoiceNum,hospitalCode,clinicTime,isNative,remittanceStatus,auditStatus,operator,name,hospitalName);
				theScope(request, idCard, illCode, nongheCard, medicalCost, invoiceNum, isNative, operator, name,
						hospitalName, clinicTimestr,expense);
				process(request, response, "/page/illExpense/illExpense_print.jsp");
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}else if( (ceiling-totalExpense)>0 && (ceiling-totalExpense)<expense ){
			//存(ceiling-totalExpense)  封顶线-年度已报销金额
			double expenseAccount = ceiling-totalExpense;
			try {
				illExpense.add(idCard,illCode,nongheCard,medicalCost,expenseAccount,invoiceNum,hospitalCode,clinicTime,isNative,remittanceStatus,auditStatus,operator,name,hospitalName);
				
				theScope(request, idCard, illCode, nongheCard, medicalCost, invoiceNum, isNative, operator, name,
						hospitalName, clinicTimestr,expense);
				process(request, response, "/page/illExpense/illExpense_print.jsp");
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			/**
			 * 打印或者跳转页面
			 */
		
			
			
			
			
		}else{
			//今年已经不能报销了 
			prompt(request, response, "当前年度报销额度已用完");
			return;
		}
	}

	private void theScope(HttpServletRequest request, String idCard, String illCode, String nongheCard,
			String medicalCost, String invoiceNum, String isNative, String operator, String name, String hospitalName,
			String clinicTimestr,Double expense) {
		request.setAttribute("idCard", idCard);
		request.setAttribute("name", name);
		request.setAttribute("illCode", illCode);
		request.setAttribute("nongheCard", nongheCard);
		request.setAttribute("medicalCost", medicalCost);
		request.setAttribute("invoiceNum", invoiceNum);
		request.setAttribute("hospitalName", hospitalName);
		request.setAttribute("medicalCost", medicalCost);
		request.setAttribute("invoiceNum", invoiceNum);
		request.setAttribute("clinicTime", clinicTimestr);
		request.setAttribute("isNative", isNative);
		request.setAttribute("operator", operator);
		request.setAttribute("expense", expense);
	}

	private void input(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String nongheCard = request.getParameter("nongheCard");
		String idCard = request.getParameter("idCard");
		String selectyear = request.getParameter("selectyear");
		if((nongheCard.equals("")||nongheCard=="")&&(idCard.equals("")||idCard=="")){
			prompt(request, response, "至少输入农合证号或者身份证号");
			return;
		}
		
		IllExpenseDao illExpenseDao = new IllExpenseDao();
		/**
		 * 判断身份证号是否参合
		 */
		PayRecord payrecode =illExpenseDao.queryIsCanhe(nongheCard, idCard, selectyear);
		
		if(payrecode==null){
			prompt(request, response, "该身份证号未参合或未交费");
			return;
		}else{
			
			try{
				Policy policy = policyDao.queryCeiling(selectyear);//查询当前年度的慢病缴费政策
				request.setAttribute("payrecode",payrecode );//
				request.setAttribute("selectyear",selectyear );
				request.setAttribute("policy", policy );
			}catch(Exception e){
				prompt(request, response, "当前年度慢性病政策未设置，请于管理可与管");
				return;
			}
			
			
			
			process(request, response, "/page/illExpense/illExpense_add.jsp");
		}
	}

	private void search(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		process(request, response, "/page/illExpense/illExpense_search.jsp");
	}

	private void list(HttpServletRequest request, HttpServletResponse response) {
		
		
	}

}
