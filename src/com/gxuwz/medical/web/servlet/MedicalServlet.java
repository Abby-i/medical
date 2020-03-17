package com.gxuwz.medical.web.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.gxuwz.medical.dao.MedicalDao;
import com.gxuwz.medical.dao.S201Dao;
import com.gxuwz.medical.domain.farmer.Farmer;
import com.gxuwz.medical.domain.medical.Medical;
import com.gxuwz.medical.domain.medical.S201;
import com.gxuwz.medical.domain.vo.PageBean;

/**
 * 医疗机构管理
 */
public class MedicalServlet extends BaseServlet {
	
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String  m=request.getParameter("m");//动作类型参数
		if("list".equals(m)){
			list(request,response);
		}else if("input".equals(m)){
			try {
				setListTypeToJsp(request,response);
				process(request, response, "/page/medical/medical_add.jsp");
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
			
		}else if("get".equals(m)){
			get(request,response);
		}else if("add".equals(m)){
			add(request, response);
		}else if("del".equals(m)){
			del(request, response);
		}else if("edit".equals(m)){
			edit(request,response);
		}
	}


	private void edit(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String jgbm=request.getParameter("jgbm");
		String zzjgbm=request.getParameter("zzjgbm");
		String jgmc=request.getParameter("jgmc");
		String dqbm=request.getParameter("dqbm");
		String areacode=request.getParameter("areacode");
		String lsgx=request.getParameter("lsgx");
		String jgjb=request.getParameter("jgjb");
		String sbddlx=request.getParameter("sbddlx");
		String pzddlx=request.getParameter("pzddlx");
		String ssjjlx=request.getParameter("ssjjlx");
		String wsjgdl=request.getParameter("wsjgdl");
		String wsjgxl=request.getParameter("wsjgxl");
		String zgdw=request.getParameter("zgdw");
		String kysjstr=request.getParameter("kysj");//日期
		String frdb=request.getParameter("frdb");
		String zczjstr=request.getParameter("zczj");//数值
		
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Date date = sdf.parse(kysjstr);
			// 将接收的时间转换成SimpleDateFormat格式
			String todateStr = sdf.format(date);
			// 将String格式的时间转换成Date类型
			Date kysj = sdf.parse(todateStr);
			 double zczjnum = Double.parseDouble(zczjstr);
			Medical medical = new Medical();
			medical.editToDB(jgbm, zzjgbm, jgmc, dqbm, areacode, lsgx, jgjb, sbddlx, pzddlx, ssjjlx, wsjgdl, wsjgxl, zgdw, kysj, frdb, zczjnum);
			list(request, response);
			
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		
	}


	private void get(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			
			setListTypeToJsp(request,response);
			String jgbm = request.getParameter("id");
			System.out.println(jgbm);
			MedicalDao medicalDao = new MedicalDao();
			Medical medical = medicalDao.queryById(jgbm);
			System.out.println(medical.toString());
			request.setAttribute("medical", medical);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		process(request, response, "/page/medical/medical_edit.jsp");
		
	}


	private void setListTypeToJsp(HttpServletRequest request, HttpServletResponse response) throws SQLException {
		S201Dao s201Dao = new S201Dao();
		List<S201> s20102List = s201Dao.findListByType("02");
		List<S201> s20106List = s201Dao.findListByType("06");
		List<S201> s20104List = s201Dao.findListByType("04");
		List<S201> s20101List = s201Dao.findListByType("01");
		List<S201> s2010301List = s201Dao.findListByType("03");
		List<S201> s2010302List = s201Dao.findListByType("0301");
		System.out.println(s2010302List.toString());
		request.setAttribute("s20102List", s20102List);
		request.setAttribute("s20106List", s20106List);
		request.setAttribute("s20104List", s20104List);
		request.setAttribute("s20101List", s20101List);
		request.setAttribute("s2010301List", s2010301List);
		request.setAttribute("s2010302List", s2010302List);
	}


	private void del(HttpServletRequest request, HttpServletResponse response) {
		String jgbm=request.getParameter("id");
		Medical medical = new Medical();
		try {
			medical.delToDB(jgbm);
			list(request, response);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	private void list(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PageDao(request,response);
		process(request, response, "/page/medical/medical_list.jsp");
	}



	private void add(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		 //接收页面传递过来的参数
		String jgbm=request.getParameter("jgbm");
		String zzjgbm=request.getParameter("zzjgbm");
		String jgmc=request.getParameter("jgmc");
		String dqbm=request.getParameter("dqbm");
		String areacode=request.getParameter("areacode");
		String lsgx=request.getParameter("lsgx");
		String jgjb=request.getParameter("jgjb");
		String sbddlx=request.getParameter("sbddlx");
		String pzddlx=request.getParameter("pzddlx");
		String ssjjlx=request.getParameter("ssjjlx");
		String wsjgdl=request.getParameter("wsjgdl");
		String wsjgxl=request.getParameter("wsjgxl");
		String zgdw=request.getParameter("zgdw");
		String kysjstr=request.getParameter("kysj");//日期
		String frdb=request.getParameter("frdb");
		String zczjnum=request.getParameter("zczj");//数值
		
		//Date  kysj=new java.util.Date();
		
		double zczj=Double.parseDouble(zczjnum);
		
		//调用添加方法
		 try{
			 	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				Date date = sdf.parse(kysjstr);
				 // 将接收的时间转换成SimpleDateFormat格式
				String todateStr = sdf.format(date);
				// 将String格式的时间转换成Date类型
				Date kysj = sdf.parse(todateStr);
			 Medical model=new Medical(jgbm, zzjgbm, jgmc, dqbm, areacode, lsgx, jgjb, sbddlx, pzddlx, ssjjlx, wsjgdl, wsjgxl, zgdw, kysj, frdb, zczj);
			 model.add();
			 list(request, response);
		 }catch(Exception e){
			 	//如果报错就是因为主键冲突
		        prompt(request, response, "该医疗机构编码已存在！");
		 }
		
	
	}

	private void PageDao(HttpServletRequest request, HttpServletResponse response) {
		PageBean pageBean = new PageBean();
		
		MedicalDao medicalDao = new MedicalDao();
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
		int count = pageBean.queryCount("t_medical");
		pageBean.setTotalCount(count);
		//1.5 每页显示记录数
		pageBean.setPageSize(Integer.parseInt(pageSize));		
		
		//从数据库中读取当前页数据
		List<Medical> medicals = medicalDao.queryPages("select * from t_medical limit ?,?", pageBean.getCurrentPage(), pageBean.getPageSize());
		pageBean.setData(medicals);
				
		//2)把PageBean对象放入域对象中
		request.setAttribute("pageBean", pageBean);
		
		HttpSession httpSession = request.getSession();
		httpSession.setAttribute("medicals", medicals);
	}
}
