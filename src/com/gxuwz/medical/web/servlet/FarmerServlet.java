package com.gxuwz.medical.web.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLEncoder;
import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.gxuwz.medical.dao.AreaDao;
import com.gxuwz.medical.dao.FarmerDao;
import com.gxuwz.medical.domain.area.Area;
import com.gxuwz.medical.domain.farmer.Farmer;
import com.gxuwz.medical.domain.vo.PageBean;


public class FarmerServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String m = request.getParameter("m");
		if ("list".equals(m)) {
			list(request,response);
		} else if("del".equals(m)){
			try {
				del(request,response);
				list(request, response);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} else if ("input".equals(m)) {
			input(request,response);
		} else if("add".equals(m)){
			add(request,response);
		}else if("get".equals(m)){
			get(request,response);
		}else if("edit".equals(m)){
			edit(request,response);
		}
	}
	
	private void edit(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String farmerid = request.getParameter("farmerid");
		String farmername = request.getParameter("farmername");
		String areacode = request.getParameter("areacode");
		Farmer farmer = new Farmer();
		try {
			farmer.editFarmer(farmerid,farmername,areacode);
			list(request, response);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}

	private void get(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String farmerid  =request.getParameter("id");
		FarmerDao farmerDao = new FarmerDao();
		AreaDao areaDao = new AreaDao();
		Farmer farmer = farmerDao.queryById(farmerid);
		Area selected = areaDao.querySelected(farmer.getAreacode());
		
		List<Area> areas = areaDao.queryAreas();
		request.setAttribute("farmer", farmer);
		request.setAttribute("areas", areas);
		request.setAttribute("selected", selected);
		process(request, response, "/page/farmer/farmer_edit.jsp");
		
	}

	private void input(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		AreaDao areaDao = new AreaDao();
		List<Area> areas = areaDao.queryAreas();
		request.setAttribute("areas", areas);
		process(request, response, "/page/farmer/farmer_add.jsp");
		
	}

	private void add(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String farmerid = UUID.randomUUID().toString().replaceAll("-", "");
		String areacode = request.getParameter("areacode");
		String farmername = request.getParameter("farmername");
		FarmerDao farmerdao = new FarmerDao();
		//是否存在farmername
		if(farmerdao.isExist(farmername)==true){
			Farmer farmer = new Farmer();
			try {
				farmer.addFarmer(farmerid,farmername,areacode);
				list(request, response);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}else{
			response.setContentType("text/html;charset=UTF-8");
			PrintWriter out=response.getWriter();
			//String message = "该机构名称已存在！";
			//message = URLEncoder.encode(message, "UTF-8");
			//message = new String(message.getBytes("iso-8859-1"),"UTF-8");
	        out.println("<script>");//输出script标签
	        out.println("alert('该机构名称已存在！');");//js语句：输出alert语句
	        out.println("history.back();");//js语句：输出网页回退语句
	        out.println("</script>");//输出script结尾标签
	        out.flush();//清空缓存
	        out.close();
		}
		
	}

	private void del(HttpServletRequest request, HttpServletResponse response) throws SQLException {
		
		Farmer farmer = new Farmer();
		String farmerid = request.getParameter("id");
		System.out.println(farmerid);
		farmer.delFarmer(farmerid);
	}

	private void list(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PageDao(request,response);
		process(request, response, "/page/farmer/farmer_list.jsp");
	}

	private void PageDao(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
		PageBean pageBean = new PageBean();
		
		FarmerDao farmerDao = new FarmerDao();
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
		int count = pageBean.queryCount("t_farmer");
		pageBean.setTotalCount(count);
		//1.5 每页显示记录数
		pageBean.setPageSize(Integer.parseInt(pageSize));		
		
		//从数据库中读取当前页数据
		List<Farmer> farmers = farmerDao.queryPages("select * from t_farmer limit ?,?", pageBean.getCurrentPage(), pageBean.getPageSize());
		System.out.println(farmers);
		pageBean.setData(farmers);
				
		//2)把PageBean对象放入域对象中
		request.setAttribute("pageBean", pageBean);
		
		HttpSession httpSession = request.getSession();
		httpSession.setAttribute("farmers", farmers);
	
}

}
