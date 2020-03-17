package com.gxuwz.medical.web.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.gxuwz.medical.dao.AccountArchivesDao;
import com.gxuwz.medical.domain.accountArchives.AccountArchives;
import com.gxuwz.medical.domain.util.AgeUtil;
import com.gxuwz.medical.domain.vo.PageBean;

public class AccountArchivesServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;
	AccountArchivesDao accountArchivesDao = new AccountArchivesDao();
   
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String m = request.getParameter("m");
		if ("list".equals(m)) {
			list(request,response);
		} else if("del".equals(m)){
			del(request,response);
		} else if ("input".equals(m)) {
			process(request, response, "/page/accountArchives/accountArchives_add.jsp");
		} else if ("get".equals(m)) {
			get(request, response);
		} else if("add".equals(m)){
		  add(request,response);
		}else if("edit".equals(m)){
			edit(request,response);
		}else if("quertyNongheCard".equals(m)){
			quertyNongheCard(request,response);
		}else if("checked".equals(m)){
			checked(request, response);
		}
		
	}

	private void checked(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String homeid = request.getParameter("id");
		System.out.println("homeid:"+homeid);
		String sql = "select * from t_accountarchives where homeid=? order by nongheCard ";
		Object[] params = {homeid};
		try {
			List<AccountArchives> list = accountArchivesDao.queryObject(sql, params);
			System.out.println(list);
			request.setAttribute("list", list);
			process(request, response, "/page/homeArchives/homeArchives_check.jsp");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}

	private void quertyNongheCard(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String cardid =request.getParameter("cardid");
		System.out.println("cardid:"+cardid);
		String nongheCard = accountArchivesDao.queryNongheBycardid(cardid);
		System.out.println("nongheCard:"+nongheCard);
		response.getWriter().write(nongheCard);
	}

	private void edit(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String cardid = request.getParameter("cardid");
		String name = request.getParameter("name");
		String household =request.getParameter("household");
		String relationship = request.getParameter("relationship");
		String sex = request.getParameter("sex");
		String healthstatus = request.getParameter("healthstatus");
		String educationlevel = request.getParameter("educationlevel");
		String birthdaystr = request.getParameter("birthday");
		int age = 0;
		try {
			age = AgeUtil.getAge(AgeUtil.parse(birthdaystr));
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		
		String property = request.getParameter("property");
		String iscountryside = request.getParameter("iscountryside");
		String job = request.getParameter("job");
		String organization = request.getParameter("organization");
		String phone = request.getParameter("phone");
		String address = request.getParameter("address");
		String information = request.getParameter("information");
		String homeid = request.getParameter("homeid");
	
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Date date = sdf.parse(birthdaystr);
			 // 将接收的时间转换成SimpleDateFormat格式
			String todateStr = sdf.format(date);
			// 将String格式的时间转换成Date类型
			Date birthday = sdf.parse(todateStr);
			AccountArchives archives = new AccountArchives(cardid,name,relationship,sex,healthstatus,educationlevel,age,birthday,property,iscountryside,job,organization,phone,address,information,homeid,household);
			archives.edit();
			list(request, response);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	private void add(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String cardid = request.getParameter("cardid");
		String name = request.getParameter("name");
	
		String household =request.getParameter("household");
		String relationship = request.getParameter("relationship");
		String sex = request.getParameter("sex");
		String healthstatus = request.getParameter("healthstatus");
		String educationlevel = request.getParameter("educationlevel");

		String birthdaystr = request.getParameter("birthday");
		int age = 0;
		try {
			age = AgeUtil.getAge(AgeUtil.parse(birthdaystr));
			System.out.println("age="+age);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		String property = request.getParameter("property");
		String iscountryside = request.getParameter("iscountryside");
		String job = request.getParameter("job");
		String organization = request.getParameter("organization");
		String phone = request.getParameter("phone");
		String address = request.getParameter("address");
		String information = request.getParameter("information");
		String homeid = request.getParameter("homeid");
		
		//根据家庭编号生成农合证号
		String nongheCard = "";
		if(accountArchivesDao.createNongheCard(homeid)!=null){
			long nongheCardLong = Long.valueOf(accountArchivesDao.createNongheCard(homeid))+1;
			nongheCard = String.valueOf(nongheCardLong);
		}else{
			long nongheCardLong = Long.valueOf(homeid+"00")+1;
			nongheCard = String.valueOf(nongheCardLong);
		}
		try {
			if(accountArchivesDao.isExit(cardid)==false){
		        prompt(request, response, "该身份证编号已存在！");
			}else{
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				Date date = sdf.parse(birthdaystr);
				 // 将接收的时间转换成SimpleDateFormat格式
				String todateStr = sdf.format(date);
				// 将String格式的时间转换成Date类型
				Date birthday = sdf.parse(todateStr);
				AccountArchives archives = new AccountArchives(cardid,name,relationship,sex,healthstatus,educationlevel,age,birthday,property,iscountryside,job,organization,phone,address,information,homeid,household,nongheCard);
				archives.add();
				process(request, response, "/system/HomeArchivesServlet?m=list");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	private void get(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String cardid = request.getParameter("id");
		
		AccountArchives archives = accountArchivesDao.quertById(cardid);
		request.setAttribute("archives", archives);
		process(request, response, "/page/accountArchives/accountArchives_edit.jsp");
	}

	private void del(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String cardid = request.getParameter("id");
		AccountArchives archives = new AccountArchives();
		try {
			archives.del(cardid);
			list(request, response);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	private void list(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		pageDao(request, response);
		process(request, response, "/page/accountArchives/accountArchives_list.jsp");
	}

	private void pageDao(HttpServletRequest request, HttpServletResponse response) {
		PageBean pageBean = new PageBean();
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
		int count = pageBean.queryCount("t_accountarchives");
		pageBean.setTotalCount(count);
		//1.5 每页显示记录数
		pageBean.setPageSize(Integer.parseInt(pageSize));		
		
		//从数据库中读取当前页数据
		List<AccountArchives> accountlist = accountArchivesDao.queryPages("select * from t_accountarchives ORDER BY cardid limit ?,?",pageBean.getCurrentPage(), pageBean.getPageSize());
		pageBean.setData(accountlist);
				
		//2)把PageBean对象放入域对象中
		request.setAttribute("pageBean", pageBean);
		
		HttpSession httpSession = request.getSession();
		System.out.println(accountlist.toString());
		httpSession.setAttribute("accountlist", accountlist);
		
	}

}
