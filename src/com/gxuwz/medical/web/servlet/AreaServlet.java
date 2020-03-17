package com.gxuwz.medical.web.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gson.Gson;
import com.gxuwz.medical.dao.AreaDao;
import com.gxuwz.medical.dao.RoleDao;
import com.gxuwz.medical.domain.area.Area;
import com.gxuwz.medical.domain.role.Role;
import com.gxuwz.medical.domain.vo.PageBean;

public class AreaServlet extends BaseServlet {
	AreaDao areaDao = new AreaDao();

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String m = request.getParameter("m");
		if ("list".equals(m)) {
			list(request,response);
		} else if("del".equals(m)){
			del(request,response);
		} else if ("input".equals(m)) {
			input(request,response);
		} else if ("get".equals(m)) {
			get(request, response);
			process(request, response, "/page/area/area_edit.jsp");
		} else if("add".equals(m)){
		    add(request, response);
		   //跳转到list页面
		    PageDao(request, response);
		    process(request, response, "/page/area/area_list.jsp");
		}else if("edit".equals(m)){
			edit(request,response);
		}else if("tojson".equals(m)){
			tojson(request, response);
		}
		
	}

	private void input(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		  Object[]params={};
			try {
				List<Area> areas= areaDao.queryObject("select * from t_area where 1=1 ORDER BY areacode", params);
				request.setAttribute("areas", areas);
				process(request, response, "/page/area/area_add.jsp");
			} catch (SQLException e) {
				e.printStackTrace();
			}
	}

	private void tojson(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//设置请求编码格式
		request.setCharacterEncoding("utf-8");
		//设置响应编码格式
		response.setContentType("text/html;charset=utf-8");
		String areaupcode = request.getParameter("areaupcode");
		List<Area> areas = areaDao.queryByupId(areaupcode);
		response.getWriter().write(new Gson().toJson(areas));
		
	}

	private void del(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String areacode = request.getParameter("id");
		System.out.println(areacode);
		Area area = new Area();
		try {
			area.delArea(areacode);
			list(request,response);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}

	private void list(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PageDao(request, response);
		process(request, response, "/page/area/area_list.jsp");
	}

	private void edit(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String areacode = request.getParameter("areacode");
		String areaname = request.getParameter("areaname");
		String grades = request.getParameter("grade");
		System.out.println(grades);
		int grade = 0;
		if(grades.equals("县")){
			grade = 1;
		}else if(grades.equals("镇")){
			grade = 2;
		}else if(grades.equals("乡")){
			grade = 3;
		}else if(grades.equals("组")){
			grade = 4;
		}
		Area area = new Area();
		try {
			area.editToDB(areacode,areaname,grade);
			list(request,response);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private void get(HttpServletRequest request, HttpServletResponse response) {
		String areacode = request.getParameter("id");
		try {
			Area area = new Area(areacode);
			request.setAttribute("area", area);

		} catch (Exception e) {
			e.printStackTrace();
		}
				
	}

	private void PageDao(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
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
			int count = pageBean.queryCount("t_area");
			pageBean.setTotalCount(count);
			//1.5 每页显示记录数
			pageBean.setPageSize(Integer.parseInt(pageSize));		
			
			//从数据库中读取当前页数据
			List<Area> areas = areaDao.queryPages("select * from t_area limit ?,?", pageBean.getCurrentPage(), pageBean.getPageSize());
			System.out.println(areas);
			pageBean.setData(areas);
					
			//2)把PageBean对象放入域对象中
			request.setAttribute("pageBean", pageBean);
			
			HttpSession httpSession = request.getSession();
			httpSession.setAttribute("areas", areas);
		
	}

	/**
	 * 添加行政区域
	 * 
	 * @param req
	 * @param resp
	 * @throws ServletException
	 * @throws IOException
	 */
	private void add(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
		try {
			// 接收行政区域编码+行政区域名称，行政区域名称有可能出现中文乱码
			String areapid = request.getParameter("areacode");
			String areaname = request.getParameter("areaname");
			// 实例化Area，并调用添加子行政区域方法
			Area area = new Area();
			area.addArea(areapid, areaname);
			} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
