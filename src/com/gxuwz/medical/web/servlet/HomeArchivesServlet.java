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
import com.gxuwz.medical.dao.AreaDao;
import com.gxuwz.medical.dao.HomearchivesDao;
import com.gxuwz.medical.domain.area.Area;
import com.gxuwz.medical.domain.homearchives.Homearchives;
import com.gxuwz.medical.domain.vo.PageBean;

/**
 * 参合家庭档案管理
 */
public class HomeArchivesServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;
       
 
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String m = request.getParameter("m");
		if ("list".equals(m)) {
			list(request,response);
		} else if("del".equals(m)){
			del(request,response);
		} else if ("input".equals(m)) {
			AreaDao areaDao = new AreaDao();
			List<Area> towns = areaDao.findtown();
			request.setAttribute("towns", towns);
			process(request, response, "/page/homeArchives/homeArchives_add.jsp");
		} else if ("get".equals(m)) {
			get(request, response);
		} else if("add".equals(m)){
		  add(request,response);
		}else if("edit".equals(m)){
			edit(request,response);
		}else if("toJsonStr".equals(m)){
			toJsonStr(request, response);
		}else if("addhomeRole".equals(m)){
			addhomeRole(request, response);
		}
		
	}

	private void addhomeRole(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		String homeid = request.getParameter("id");
		String households = request.getParameter("household");
		
		String household = new String(households.getBytes("ISO-8859-1"),"utf-8");
		
		System.out.println(household);
		request.setAttribute("homeid", homeid);
		request.setAttribute("household", household);
		process(request, response, "/page/accountArchives/accountArchives_add.jsp");
	}

	private void del(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		String homeid = request.getParameter("id");
		Homearchives h = new Homearchives();
		try {
			h.del(homeid);
			list(request, response);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}

	private void edit(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		String countyid = "450421";//梧州市
		String townid = request.getParameter("town");
		String villageid = request.getParameter("village");
		String groupid = request.getParameter("group");
		String homeid = request.getParameter("homeid");
		HomearchivesDao homearchivesDao = new HomearchivesDao();
	
		String property = request.getParameter("property");
		String household = request.getParameter("household");
		
		String createtimeStr = request.getParameter("createtime");
		String registrar = request.getParameter("registrar");
		String address = request.getParameter("address");
		
		Date createtime=null;
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Date date = sdf.parse(createtimeStr);
			// 将接收的时间转换成SimpleDateFormat格式
			String todateStr = sdf.format(date);
			// 将String格式的时间转换成Date类型
			createtime = sdf.parse(todateStr);
			Homearchives h = new Homearchives();
			h.edit(countyid,townid,villageid,groupid,homeid,property,household,createtime,address,registrar);
			list(request, response);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}

	private void get(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String homeid =request.getParameter("id");
		HomearchivesDao homearchivesDao = new HomearchivesDao();
		AreaDao areaDao = new AreaDao();
		Homearchives homearchives =homearchivesDao.querybyId(homeid);
		request.setAttribute("homearchives", homearchives);
		Area town =areaDao.querytownname(homearchives.getTownid());
		request.setAttribute("town", town);
		Area village =areaDao.queryVillagename(homearchives.getVillageid());
		request.setAttribute("village", village);
		Area group =areaDao.queryGroupname(homearchives.getGroupid());
		request.setAttribute("group", group);
		
		
		process(request, response, "/page/homeArchives/homeArchives_edit.jsp");
	}

	private void add(HttpServletRequest request, HttpServletResponse response) {
		
		HomearchivesDao homearchivesDao = new HomearchivesDao();
		AccountArchivesDao accountArchivesDao = new AccountArchivesDao();
		String countyid = "450421";//梧州市
		String townid = request.getParameter("town");
		String villageid = request.getParameter("village");
		String groupid = request.getParameter("group");
		//根据组编号生成家庭编号
		String homeid = "";
		if(homearchivesDao.Tohomeid(groupid)!=null){
			long homeidLong = Long.valueOf(homearchivesDao.Tohomeid(groupid))+1;
			 homeid = String.valueOf(homeidLong);
		}else{
			long homeidLong = Long.valueOf(groupid+"000")+1;
			 homeid = String.valueOf(homeidLong);
		}
	
		String property = request.getParameter("property");
		String household = request.getParameter("household");
		System.out.println("household:"+household);
		//String createtimeStr = request.getParameter("createtime");
		String registrar = request.getParameter("registrar");
		
		String address = request.getParameter("address");
		String cardid = request.getParameter("cardid");
		
		
		String phone = request.getParameter("phone");
		String sex = request.getParameter("sex");
		String healthstatus = request.getParameter("healthstatus");
		String educationlevel = request.getParameter("educationlevel");
		String birthdaystr = request.getParameter("birthday");
		String iscountryside = request.getParameter("iscountryside");
		String job = request.getParameter("job");
		String organization = request.getParameter("organization");
		
		
		Homearchives homearchives = new Homearchives();
		
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			//Date date = sdf.parse(createtimeStr);
			Date date2 = sdf.parse(birthdaystr);
			 // 将接收的时间转换成SimpleDateFormat格式
			//String todateStr = sdf.format(date);
			String todateStr2 = sdf.format(date2);
			// 将String格式的时间转换成Date类型
			//Date createtime = sdf.parse(todateStr);
			Date birthday = sdf.parse(todateStr2);
			if(accountArchivesDao.isExit(cardid)){
				Date createtime = new Date();
				homearchives.add(countyid,townid,villageid,groupid,homeid,household,property,address,createtime,registrar,cardid,phone,sex,healthstatus,educationlevel,birthday,property,iscountryside,job,organization);
				list(request, response);
			} else if(accountArchivesDao.isExit(cardid)==false){
				response.setContentType("text/html;charset=UTF-8");
				PrintWriter out=response.getWriter();
		        out.println("<script>");
		        out.println("alert('该户主身份证已存在！');");
		        out.println("history.back();");
		        out.println("</script>");
		        out.flush();
		        out.close();
		        //prompt(request, response, "该户主身份证已存在");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	private void toJsonStr(HttpServletRequest request, HttpServletResponse response) throws IOException {
		AreaDao areaDao = new AreaDao();
			List<Area> areas = areaDao.queryAll();
			int size =  areas.size();
			StringBuilder sb = new StringBuilder();
			sb.append("[");
			for(Area module:areas){
				size--;
				sb.append("{\"id\":\"").append(module.getAreacode());
				sb.append("\",\"pId\":\"").append(module.getAreaupcode());
				sb.append("\",\"name\":\"").append(module.getAreaname());
				sb.append("\",\"checked\":\"");
				sb.append("false");
				sb.append("\"}");
				if(size>0){
					sb.append(",");
				}
			}
			sb.append("]");
			response.setContentType("application/json;charset=UTF-8");
			response.setHeader("Cache-Control", "no-cache");
			System.out.println(sb.toString());
			response.getWriter().write(sb.toString());
			
		
	}

	private void list(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		pageDao(request, response);
		process(request, response, "/page/homeArchives/homeArchives_list.jsp");
		
	}

	private void pageDao(HttpServletRequest request, HttpServletResponse response) {
		
		PageBean pageBean = new PageBean();
		HomearchivesDao homearchivesDao = new HomearchivesDao();
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
		int count = pageBean.queryCount("t_homearchives");
		pageBean.setTotalCount(count);
		//1.5 每页显示记录数
		pageBean.setPageSize(Integer.parseInt(pageSize));		
		
		//从数据库中读取当前页数据
		List<Homearchives> homelist = homearchivesDao.queryPages("select * from t_homearchives limit ?,?",pageBean.getCurrentPage(), pageBean.getPageSize());
		pageBean.setData(homelist);
				
		//2)把PageBean对象放入域对象中
		request.setAttribute("pageBean", pageBean);
		
		HttpSession httpSession = request.getSession();
		System.out.println(homelist.toString());
		httpSession.setAttribute("homelist", homelist);
		
	}
		

}
