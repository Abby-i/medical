package com.gxuwz.medical.web.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.gxuwz.medical.dao.ChronicdisDao;
import com.gxuwz.medical.domain.chronicdis.Chronicdis;
import com.gxuwz.medical.domain.vo.PageBean;

/**
 * 慢病分类管理
 */
public class ChronicdisServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String m=request.getParameter("m");//请求处理动作类型:list:显示列表；get：根据ID读取记录;input:新增数据;add:保存新记录；edit：更新记录
	    if("list".equals(m)){
	    	list(request,response);
	    }else if("input".equals(m)){
	    	input(request,response);
		}else if("get".equals(m)){
			  process(request, response, "/page/chronicdis/chronicdis_edit.jsp");
		} 
		else if("add".equals(m)){
			add(request,response);
		}else if("edit".equals(m)){
			edit(request, response);
		}else if("del".equals(m)){
			del(request,response);
		}
		
	}

	private void edit(HttpServletRequest request, HttpServletResponse response) {
		//1：接收参数
	  	  String illcode=request.getParameter("illcode");
	  	  String illname=request.getParameter("illname");
	  	  String pycode=request.getParameter("pycode");
	  	  String wbcode=request.getParameter("wbcode");
	  	  //2:构造新慢病信息对象
	  	  Chronicdis model=new Chronicdis(illcode, illname, pycode, wbcode);
	  	  //3：调用保存的方法
	  	  try{
	  		  model.edit();
	  		list(request, response);
	  	  }catch(Exception e){
	  		  e.printStackTrace();
	  	  }
	}

	private void del(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

	  	  //1：接收参数
	  	  String illcode=request.getParameter("id");
	  	  
	  	  //2:构造新慢病信息对象
	  	  Chronicdis model=new Chronicdis();
	  	  try{
	  		  model.del(illcode);
	  		process(request, response, "/page/chronicdis/chronicdis_list.jsp");
	  	  }catch(Exception e){
	  		  error(request, response);
	  	  }
		
	}
		

	private void add(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//1：接收参数
	  	  String illcode=request.getParameter("illcode");
	  	  String illname=request.getParameter("illname");
	  	  String pycode=request.getParameter("pycode");
	  	  String wbcode=request.getParameter("wbcode");
	  	//2. 系统校验疾病编号是否重复
	  	  ChronicdisDao chronicdisDao = new ChronicdisDao();
	  	if(chronicdisDao.isExist(illcode)==true){
	  		 Chronicdis model=new Chronicdis(illcode, illname, pycode, wbcode);
			try {
				model.add();
				list(request, response);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}else{
			response.setContentType("text/html;charset=UTF-8");
			PrintWriter out=response.getWriter();
	        out.println("<script>");//输出script标签
	        out.println("alert('该疾病编号已存在！');");//js语句：输出alert语句
	        out.println("history.back();");//js语句：输出网页回退语句
	        out.println("</script>");//输出script结尾标签
	        out.flush();//清空缓存
	        out.close();
		}
	  	 
		
	}

	private void input(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		process(request, response, "/page/chronicdis/chronicdis_add.jsp");
	}

	private void list(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		pageDao(request, response);
		process(request, response, "/page/chronicdis/chronicdis_list.jsp");
	}

	private void pageDao(HttpServletRequest request, HttpServletResponse response) {

		PageBean pageBean = new PageBean();
		
		ChronicdisDao chronicdisDao = new ChronicdisDao();
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
		int count = pageBean.queryCount("t_chronicdis");
		pageBean.setTotalCount(count);
		//1.5 每页显示记录数
		pageBean.setPageSize(Integer.parseInt(pageSize));		
		
		//从数据库中读取当前页数据
		List<Chronicdis> chronicdis = chronicdisDao.queryPages("select * from t_chronicdis limit ?,?",pageBean.getCurrentPage(), pageBean.getPageSize());
		pageBean.setData(chronicdis);
				
		//2)把PageBean对象放入域对象中
		request.setAttribute("pageBean", pageBean);
		
		HttpSession httpSession = request.getSession();
		httpSession.setAttribute("chronicdis", chronicdis);
		
	}
		
}
