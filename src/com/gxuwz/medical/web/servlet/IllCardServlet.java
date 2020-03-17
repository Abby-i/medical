package com.gxuwz.medical.web.servlet;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.gxuwz.medical.dao.AccountArchivesDao;
import com.gxuwz.medical.dao.IllCardDao;
import com.gxuwz.medical.domain.accountArchives.AccountArchives;
import com.gxuwz.medical.domain.illCard.IllCard;
import com.gxuwz.medical.domain.util.AgeUtil;
import com.gxuwz.medical.domain.vo.PageBean;

/**
 * 慢性病报销
 */
public class IllCardServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;
       
   
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String m =request.getParameter("m");
		if("list".equals(m)){
			list(request,response);
		}else if("input".equals(m)){
			process(request, response, "/page/illCard/illCard_search.jsp");
		}else if("add".equals(m)){
			add(request,response);
		}else if("del".equals(m)){
			del(request,response);
		} else if("edit".equals(m)){
			edit(request,response);
		} else if("detail".equals(m)){
			
		} else if("execel".equals(m)){
			
		}else if("search".equals(m)){
			search(request,response);
			process(request, response, "/page/illCard/illCard_add.jsp");
		}else if("get".equals(m)){
			get(request,response);
		}

	}


	private void edit(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		IllCardDao cardDao = new IllCardDao();
		// 1.创建FileItemFactory对象
		FileItemFactory factory = new DiskFileItemFactory();

		// 2.创建ServletFileUpload对象
		ServletFileUpload upload = new ServletFileUpload(factory);
		upload.setHeaderEncoding("utf-8");// 解决file表单项的文件名中文乱码问题
		// 完善5：限制上传的单个和所有文件的大小。建议使用该方式
		upload.setFileSizeMax(16 * 1024);// 单个文件的上限
		upload.setSizeMax(5 * 16 * 1024);// 一次上传的所有文件的总大小的上限

		// 3.通过ServletFileUpload对象实现上传操作，将客户端一个个表单项封装到一个个FileItem中
		List<FileItem> itemList = null;
		try {
			itemList = upload.parseRequest(request);
		} catch (FileUploadException e) {
			prompt(request, response, "文件不能超过16kb！");
		}
		//4.遍历各个FileItem（相当于对各个表单项进行处理）
		String id = null;
		String illCard = null;
		String nongheCard = null;
		String idCard =null;
		String illCode =null;
		String startTimestr =null;
		String endTimestr =null;
		String attachment =null;
		String realName =null;
		for(int i=0;i<itemList.size();i++){
			//取出第i个表单项
			FileItem item = itemList.get(i);
			String fieldName = item.getFieldName();
			//对各个表单项进行处理（普通表单项和文件表单项要分开处理）
			if(item.isFormField()){//普通表单项
				if("id".equals(fieldName)){
					id = item.getString("utf-8");
					System.out.println("id:"+id);
				}
				if("illCard".equals(fieldName)){
					illCard = item.getString("utf-8");
					System.out.println("illCard:"+illCard);
				}
				if("nongheCard".equals(fieldName)){
					nongheCard = item.getString("utf-8");
					System.out.println("nongheCard:"+nongheCard);
				}
				if("idCard".equals(fieldName)){
					idCard = item.getString("utf-8");
					System.out.println("idCard:"+idCard);
				}
				if("illCode".equals(fieldName)){
					illCode = item.getString("utf-8");
					System.out.println("illCode:"+illCode);
				}
			
				if("startTime".equals(fieldName)){
					startTimestr = item.getString("utf-8");
					System.out.println("startTime:"+startTimestr);
				}
				if("endTime".equals(fieldName)){
					endTimestr = item.getString("utf-8");
					System.out.println("endTime:"+endTimestr);
				}
			}else{
				
				if("attachment".equals(fieldName)){
					//完善4：只上传jpg、jpeg和gif、png文件
					String contentType = item.getContentType();//images/jpg
					System.out.println("contentType:"+contentType);
					if(!"image/jpeg".equals(contentType) && !"image/gif".equals(contentType)&&!"image/png".equals(contentType) ){
						request.setAttribute("mess", "只能上传jpg和gif文件");
						prompt(request, response, "只能上传jpg、png、gif文件！");
					}
					String realPath = this.getServletContext().getRealPath("/upload");
					File dir = new File(realPath);
					//完善1：如果文件夹不存在，就创建
					if(!dir.exists()){
						dir.mkdirs();
					}
					//指定上传的文件名
					realName = item.getName(); //adfad.fadf.yi.jpg   
					System.out.println("realName"+realName);
					//指定长传的文件夹和文件名
					//完善2：为了防止文件的同名覆盖，上传到服务器端的文件重新命名
					UUID uuid = UUID.randomUUID();
					String extName = realName.substring(realName.lastIndexOf("."));
					attachment = uuid.toString()+extName; //535bc231-935a-427b-a1d7-b3e6d8b8293e.jpg
					
					File file = new File(dir, attachment);
					//上传该照片到指定位置
					try {
						Object[] params = {nongheCard,illCode} ;
						List<IllCard> cards=cardDao.queryObject("select * from t_ill_card where nongheCard=? and illCode=?", params);
						System.out.println(cards.toString());
						if(cards.size()>1){
							prompt(request,response,"该慢病已经存在记录！");
							return;
						}
						AgeUtil ageUtil = new AgeUtil();
						Date startTime = null;
						Date endTime = null;
						startTime = ageUtil.toDateFormat(startTimestr);
					    endTime = ageUtil.toDateFormat(endTimestr);
					    IllCard card = new IllCard(Integer.valueOf(id),illCard,nongheCard,idCard,illCode,realName,attachment,startTime,endTime);
					    card.edit();
						item.write(file);
					} catch (Exception e) {
						e.printStackTrace();
					}
					
				}
			}
		}
		//关闭layer弹窗
		closeLayer(request,response);
		
	}


	private void get(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String id = request.getParameter("id");
		IllCardDao cardDao = new IllCardDao();
		IllCard card = cardDao.querybyid(id);
		request.setAttribute("card", card);
		process(request, response, "/page/illCard/illCard_edit.jsp");
	}


	private void del(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException  {
		String id = request.getParameter("id");
		IllCard card = new IllCard();
		try {
			card.del(id);
			list(request, response);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}


	private void add(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		IllCardDao cardDao = new IllCardDao();
		// 1.创建FileItemFactory对象
		FileItemFactory factory = new DiskFileItemFactory();

		// 2.创建ServletFileUpload对象
		ServletFileUpload upload = new ServletFileUpload(factory);
		upload.setHeaderEncoding("utf-8");// 解决file表单项的文件名中文乱码问题
		// 完善5：限制上传的单个和所有文件的大小。建议使用该方式
		upload.setFileSizeMax(16 * 1024);// 单个文件的上限
		upload.setSizeMax(5 * 16 * 1024);// 一次上传的所有文件的总大小的上限

		// 3.通过ServletFileUpload对象实现上传操作，将客户端一个个表单项封装到一个个FileItem中
		List<FileItem> itemList = null;
		try {
			itemList = upload.parseRequest(request);
		} catch (FileUploadException e) {
			prompt(request, response, "文件不能超过16kb！");
		}
		//4.遍历各个FileItem（相当于对各个表单项进行处理）
		String illCard = null;
		String nongheCard = null;
		String idCard =null;
		String illCode =null;
		String startTimestr =null;
		String endTimestr =null;
		String attachment =null;
		String realName =null;
		for(int i=0;i<itemList.size();i++){
			//取出第i个表单项
			FileItem item = itemList.get(i);
			String fieldName = item.getFieldName();
			//对各个表单项进行处理（普通表单项和文件表单项要分开处理）
			if(item.isFormField()){//普通表单项
				if("illCard".equals(fieldName)){
					illCard = item.getString("utf-8");
					System.out.println("illCard:"+illCard);
				}
				if("nongheCard".equals(fieldName)){
					nongheCard = item.getString("utf-8");
					System.out.println("nongheCard:"+nongheCard);
				}
				if("idCard".equals(fieldName)){
					idCard = item.getString("utf-8");
					System.out.println("idCard:"+idCard);
				}
				if("illCode".equals(fieldName)){
					illCode = item.getString("utf-8");
					System.out.println("illCode:"+illCode);
				}
			
				if("startTime".equals(fieldName)){
					startTimestr = item.getString("utf-8");
					System.out.println("startTime:"+startTimestr);
				}
				if("endTime".equals(fieldName)){
					endTimestr = item.getString("utf-8");
					System.out.println("endTime:"+endTimestr);
				}
			}else{
				
				if("attachment".equals(fieldName)){
					//完善4：只上传jpg、jpeg和gif、png文件
					String contentType = item.getContentType();//images/jpg
					System.out.println("contentType:"+contentType);
					if(!"image/jpeg".equals(contentType) && !"image/gif".equals(contentType)&&!"image/png".equals(contentType) ){
						request.setAttribute("mess", "只能上传jpg和gif文件");
						prompt(request, response, "只能上传jpg、png、gif文件！");
					}
					String realPath = this.getServletContext().getRealPath("/upload");
					File dir = new File(realPath);
					//完善1：如果文件夹不存在，就创建
					if(!dir.exists()){
						dir.mkdirs();
					}
					//指定上传的文件名
					realName = item.getName(); //adfad.fadf.yi.jpg   
					System.out.println("realName"+realName);
					//指定长传的文件夹和文件名
					//完善2：为了防止文件的同名覆盖，上传到服务器端的文件重新命名
					UUID uuid = UUID.randomUUID();
					String extName = realName.substring(realName.lastIndexOf("."));
					attachment = uuid.toString()+extName; //535bc231-935a-427b-a1d7-b3e6d8b8293e.jpg
					
					File file = new File(dir, attachment);
					//上传该照片到指定位置
					try {
						Object[] params = {nongheCard,illCode} ;
						List<IllCard> cards=cardDao.queryObject("select * from t_ill_card where nongheCard=? and illCode=?", params);
						System.out.println("sssss"+cards.toString());
						if(cards.size()>=1){
							prompt(request,response,"该慢病已经存在记录！");
							return;
						}
						AgeUtil ageUtil = new AgeUtil();
						Date startTime = null;
						Date endTime = null;
						startTime = ageUtil.toDateFormat(startTimestr);
					    endTime = ageUtil.toDateFormat(endTimestr);
					    IllCard card = new IllCard(illCard,nongheCard,idCard,illCode,realName,attachment,startTime,endTime);
					    card.add();
						item.write(file);
					} catch (Exception e) {
						e.printStackTrace();
					}
					
				}
			}
		}
		//关闭layer弹窗
		closeLayer(request,response);
	}


	private void search(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		String name = "";
		try {
			name=URLDecoder.decode(request.getParameter("name"), "utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		
		request.setAttribute("name", name);
		PageBean pageBean = new PageBean();
	
		AccountArchivesDao accDao = new AccountArchivesDao();
		//1.3从用户参数中获取当前页数据（currPage）
		String currentPage = request.getParameter("currentPage");
		String pageSize = request.getParameter("pageSize");
		if(currentPage==null || currentPage.equals("")){
			  currentPage="1";
		}
		if(pageSize==null || pageSize.equals("")){
			pageSize = "3";
		}
		pageBean.setCurrentPage(Integer.parseInt(currentPage));
		//从数据库查询出总数
		int count = accDao.queryCount(name);
		pageBean.setTotalCount(count);
		//1.5 每页显示记录数
		pageBean.setPageSize(Integer.parseInt(pageSize));		
		
		//从数据库中读取当前页数据
		List<AccountArchives> archives = accDao.queryAndSearchPages(name,pageBean.getCurrentPage(), pageBean.getPageSize());
		System.out.println(archives.toString());
		if(archives.size()==0){
	        prompt(request, response, "不存在该姓名的信息！");
	        return;
		}
		pageBean.setData(archives);
				
		//2)把PageBean对象放入域对象中
		request.setAttribute("pageBean", pageBean);
		
		request.setAttribute("archives", archives);
	}


	private void list(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PageBean pageBean = new PageBean();
		
		String illCard=request.getParameter("illCard");
		String idCard=request.getParameter("idCard");
		System.out.println("idCard:"+idCard);
		String illCode=request.getParameter("illCode");
	
		IllCardDao illcardDao = new IllCardDao();
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
		int count = illcardDao.queryCount(illCard,idCard,illCode);
		System.out.println("count:"+count);
		pageBean.setTotalCount(count);
		//1.5 每页显示记录数
		pageBean.setPageSize(Integer.parseInt(pageSize));		
		
		//从数据库中读取当前页数据
		List<IllCard> illcards = illcardDao.queryAndSearchPages(illCard,idCard,illCode,pageBean.getCurrentPage(), pageBean.getPageSize());
		pageBean.setData(illcards);
				
		//2)把PageBean对象放入域对象中
		request.setAttribute("pageBean", pageBean);
		
		HttpSession httpSession = request.getSession();
		httpSession.setAttribute("illcards", illcards);
		process(request, response, "/page/illCard/illCard_list.jsp");
	}

}
