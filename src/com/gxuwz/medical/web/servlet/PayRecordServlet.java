package com.gxuwz.medical.web.servlet;

import java.io.IOException;
import java.io.OutputStream;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.gxuwz.medical.dao.AccountArchivesDao;
import com.gxuwz.medical.dao.HomearchivesDao;
import com.gxuwz.medical.dao.PayRecordDao;
import com.gxuwz.medical.dao.PayStandardDao;
import com.gxuwz.medical.domain.accountArchives.AccountArchives;
import com.gxuwz.medical.domain.homearchives.Homearchives;
import com.gxuwz.medical.domain.payRecord.PayRecord;
import com.gxuwz.medical.domain.payStandard.PayStantard;
import com.gxuwz.medical.domain.util.AgeUtil;
import com.gxuwz.medical.domain.util.PoiUtil;
import com.gxuwz.medical.domain.vo.PageBean;

public class PayRecordServlet extends BaseServlet {

	private static final long serialVersionUID = 1L;
	AccountArchivesDao accArchivesDao = new AccountArchivesDao();
	PayStandardDao standardDao = new PayStandardDao();
	PayRecordDao payRecordDao = new PayRecordDao();
	HomearchivesDao homearchivesDao = new HomearchivesDao();


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String m =request.getParameter("m");
		if("list".equals(m)){
			list(request,response);
			process(request, response, "/page/payRecord/payRecord_list.jsp");
		}else if("input".equals(m)){
			
		}else if("calAccount".equals(m)){
			calAccount(request, response);
		}else if("add".equals(m)){
			
				try {
					add(request,response);
				} catch (Exception e) {
					e.printStackTrace();
				}
			
			
		}else if("del".equals(m)){
			//del(req, resp);
		} else if("edit".equals(m)){
			//edit(req, resp);
		} else if("detail".equals(m)){
			// 当有家庭成员未缴费时，获取该家庭成员的信息
			
			try {
				detail(request, response);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
			
		} else if("execel".equals(m)){
			//execel(request, response, null);
		}else if("search".equals(m)){
			search(request, response);
		}else {
			error(request, response);
		}
	}

	private void search(HttpServletRequest request, HttpServletResponse response) {
		
	}


	private void add(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String[] cardids=request.getParameterValues("ids");
		String operator =(String) request.getSession().getAttribute("username");
		String invoiceNum = request.getParameter("invoiceNum");
		String joinNum ="";
	    //-----------------------------------------------------------
	    PayRecord payRecord = new PayRecord();
	    AgeUtil ageUtil = new AgeUtil();
	    //-----------------------------------------------------------
	   
	    PayStantard  stantard = standardDao.findById(ageUtil.getNowyear());
	    if(check()==true){
	    	
	    String homeid ="";
	    for (int i = 0; i < cardids.length; i++) {
	        AccountArchives archives =  accArchivesDao.quertById(cardids[i].replace(" ", ""));
	        joinNum = accArchivesDao.queryNongheBycardid(cardids[i].replace(" ", ""));
	        homeid=archives.getHomeid();
	        payRecord.add(homeid,archives.getHousehold(),archives.getCardid(),archives.getName(),stantard.getAccount(),operator,invoiceNum,joinNum);
	        
	    }
	    
	    try{
	    	response.setContentType("application/x-excel");
	    	response.setHeader("Content-Disposition", "attachment;filename=" + new String("参合缴费凭证.xls".getBytes(), "ISO-8859-1"));
	    	OutputStream outputStream = response.getOutputStream();
	    	PoiUtil poiUtil = new PoiUtil();
	    		poiUtil.exportCredentials(cardids,invoiceNum,joinNum,String.valueOf(stantard.getAccount()),operator,outputStream);
		    	if(outputStream != null){
		    		outputStream.close();
		    	}
		
	    
	    }catch(IOException e){
	    	e.printStackTrace();
	    	
	    }
	   		}else{
	   			prompt(request, response, "不在缴费标准规定时间范围内");
	   		}

	}

	

	private boolean check() {
		try{
			boolean flag = false;
			// 获取系统当前年度以及当前时间
			Calendar date = Calendar.getInstance();
			String annual = String.valueOf(date.get(Calendar.YEAR));
			Date payTime = date.getTime();
			// 获取当前年度的缴费标准规定时间范围
			PayStantard stantard = standardDao.findById(annual);
			// 设置当前时间
			date.setTime(payTime);
			// 设置缴费开始时间
			Calendar begin = Calendar.getInstance();
			begin.setTime(stantard.getStartTime());
			// 设置缴费结束时间
			Calendar end = Calendar.getInstance();
			end.setTime(stantard.getEndTime());
				        
			// 判断当前时间是否等于缴费标准规定时间
			 if(payTime == stantard.getStartTime() || payTime == stantard.getEndTime()) {
					flag = true;
			}
			 // 判断当前时间是否在缴费标准规定时间范围内
			if (date.after(begin) && date.before(end)) {
				 flag = true;
				        } 
			if(flag) {
					return true;
				}else {
					return false;
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	private void detail(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
		//第一阶段：获取所有家庭成员信息 
		String homeid = request.getParameter("homeid");
		// 获取该家庭编号的所有家庭成员信息
		AccountArchivesDao archivesDao = new AccountArchivesDao(); 
		//获取所有已参合成员信息
		// 全部家庭成员的个人编号集合
		List<String> homeAllPersonsid = archivesDao.queryAllPersonid(homeid);
		request.setAttribute("homeAllPersonsid", homeAllPersonsid);
		// 已当前年度已参合家庭成员的个人户内编号集合
		List<String> hasPayPersonsid = payRecordDao.queryHasPayPersonid(homeid);
		request.setAttribute("hasPayPersonsid", hasPayPersonsid);
		process(request, response, "/page/payRecord/payRecord_add.jsp");
	}

	private void list(HttpServletRequest request, HttpServletResponse response) {
		pageDao(request,response);
		
	}
	
	private void pageDao(HttpServletRequest request, HttpServletResponse response) {
		PageBean pageBean = new PageBean();
		
		String household=request.getParameter("household");
	
		//1.3从用户参数中获取当前页数据（currPage）
		String currentPage = request.getParameter("currentPage");
		String pageSize = request.getParameter("pageSize");
		if(currentPage==null || currentPage.equals("")){
			  currentPage="1";
		}
		if(pageSize==null || pageSize.equals("")){
			pageSize = "10";
		}
		pageBean.setCurrentPage(Integer.parseInt(currentPage));
		//从数据库查询出总数
		int count = homearchivesDao.queryCount(household);
		pageBean.setTotalCount(count);
		//1.5 每页显示记录数
		pageBean.setPageSize(Integer.parseInt(pageSize));		
		
		//从数据库中读取当前页数据
		//System.out.println("household:"+household);
		List<Homearchives> payRecords = homearchivesDao.queryHomearchives(household, pageBean.getCurrentPage(), pageBean.getPageSize());
		pageBean.setData(payRecords);
				
		//2)把PageBean对象放入域对象中
		request.setAttribute("pageBean", pageBean);
		
		HttpSession httpSession = request.getSession();
		httpSession.setAttribute("payRecords", payRecords);
	}

	private void calAccount(HttpServletRequest request, HttpServletResponse response) {
		// 1:接收表单传过来的缴费人数
		int payNum = Integer.parseInt(request.getParameter("payNum"));
			try {
				// 2:获取当前年度的缴费标准金额
				Calendar date = Calendar.getInstance();
				String annual = String.valueOf(date.get(Calendar.YEAR));
				PayStantard psd = new PayStantard(annual);
				double account = psd.getAccount();
				// 3:执行计算方法
				double amount = payRecordDao.calAccount(payNum, account);
				response.getWriter().write(""+amount);
				
			}catch (Exception e) {
					e.printStackTrace();
		}
		
	}
	


}
