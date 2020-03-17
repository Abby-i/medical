package com.gxuwz.medical.domain.util;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.util.CellRangeAddress;

import com.gxuwz.medical.dao.AccountArchivesDao;
import com.gxuwz.medical.dao.ChronicdisDao;
import com.gxuwz.medical.domain.accountArchives.AccountArchives;
import com.gxuwz.medical.domain.illExpense.IllExpense;

public class PoiUtil {
	
	AccountArchivesDao archivesDao = new AccountArchivesDao();
	ChronicdisDao chronicdisDao = new ChronicdisDao();
	

	public void exportCredentials(String[] cardids, String invoiceNum, String joinNum,String payaccount ,String operator,
			OutputStream outputStream) {
		try {
			//1、创建工作簿
			HSSFWorkbook workbook = new HSSFWorkbook();
			//1.1、创建合并单元格对象
			CellRangeAddress cellRangeAddress = new CellRangeAddress(0, 0, 0, 5);//起始行号，结束行号，起始列号，结束列号
			//1.2、创建头标题行样式并创建字体
			HSSFCellStyle style1 = createCellStyle(workbook, (short)16);
			
			//1.3、创列标题样式
			HSSFCellStyle style2 = createCellStyle(workbook, (short)13);
			
			//1.3、创内容样式
			HSSFCellStyle style3 = createContentCellStyle(workbook, (short)10);
			
			//2、创建工作表
			HSSFSheet sheet = workbook.createSheet("参合缴费凭条");
			//2.1、加载合并单元格对象
			sheet.addMergedRegion(cellRangeAddress);
			//2.2、设置默认列宽
			sheet.setDefaultColumnWidth(25);
			
			//3、创建行
			//3.1、创建头标题行并写入头标题
			HSSFRow row1 = sheet.createRow(0);
			HSSFCell cell1 = row1.createCell(0);
			cell1.setCellStyle(style1);
			cell1.setCellValue("参合缴费凭条");
			
			//3.2、创建列标题并写入列标题
			HSSFRow row2 = sheet.createRow(1);
			String[] titles = {"身份证号/参合证号","姓名","性别","缴费金额","联系电话","操作人"};
			for(int i = 0; i < titles.length; i++){
				HSSFCell cell2 = row2.createCell(i);
				cell2.setCellStyle(style2);
				cell2.setCellValue(titles[i]);
			}
			AccountArchives archives = null; 
			String sexs = "";
			
			if(cardids != null && cardids.length > 0){
				for(int j = 0; j < cardids.length ; j++){
					HSSFRow row = sheet.createRow(j+2);
					archives = archivesDao.quertById(cardids[j].replace(" ", ""));
					//row.setRowStyle(style3);
					row.createCell(0).setCellStyle(style3);
					row.createCell(1).setCellStyle(style3);
					row.createCell(2).setCellStyle(style3);
					row.createCell(3).setCellStyle(style3);
					row.createCell(4).setCellStyle(style3);
					row.createCell(5).setCellStyle(style3);
					row.getCell(0).setCellValue(archives.getCardid());
					row.getCell(1).setCellValue(archives.getName());
					if(archives.getSex()=="0" || "0".equals(archives.getSex())){
						sexs = "女";
					}else if(archives.getSex()=="1" || "1".equals(archives.getSex())){
						sexs = "男";
					}
					row.getCell(2).setCellValue(sexs);
					row.getCell(3).setCellValue(payaccount);
					row.getCell(4).setCellValue(archives.getPhone());
					row.getCell(5).setCellValue(operator);
				}
					
			}	
			
		
			
		System.out.println(outputStream);
			//5、输出
			workbook.write(outputStream);
			workbook.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 创建单元格样式
	 * @param workbook 工作簿
	 * @param fontSize 字体大小
	 * @return 单元格样式
	 */
	private static HSSFCellStyle createCellStyle(HSSFWorkbook workbook, short fontSize){
		HSSFCellStyle style = workbook.createCellStyle();
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER);//水平居中
		style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);//垂直居中
		//创建字体
		HSSFFont font = workbook.createFont();
		font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);//加粗字体
		font.setFontHeightInPoints(fontSize);
		//在样式中加载字体
		style.setFont(font);
		return style;
	}
		
	private static HSSFCellStyle createContentCellStyle(HSSFWorkbook workbook, short fontSize){
		HSSFCellStyle style = workbook.createCellStyle();
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER);//水平居中
		style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);//垂直居中
		//创建字体
		HSSFFont font = workbook.createFont();
		font.setFontHeightInPoints(fontSize);
		//在样式中加载字体
		style.setFont(font);
		return style;
	}

	public void exportExpenseInfo(List<IllExpense> expenseInfolist, OutputStream outputStream) {
		
		try {
			//1、创建工作簿
			HSSFWorkbook workbook = new HSSFWorkbook();
			//1.1、创建合并单元格对象
			CellRangeAddress cellRangeAddress = new CellRangeAddress(0, 0, 0, 5);//起始行号，结束行号，起始列号，结束列号
			//1.2、创建头标题行样式并创建字体
			HSSFCellStyle style1 = createCellStyle(workbook, (short)16);
			
			//1.3、创列标题样式
			HSSFCellStyle style2 = createCellStyle(workbook, (short)13);
			
			//1.3、创内容样式
			HSSFCellStyle style3 = createContentCellStyle(workbook, (short)10);
			
			//2、创建工作表
			HSSFSheet sheet = workbook.createSheet("慢性病报销情况");
			//2.1、加载合并单元格对象
			sheet.addMergedRegion(cellRangeAddress);
			//2.2、设置默认列宽
			sheet.setDefaultColumnWidth(25);
			
			//3、创建行
			//3.1、创建头标题行并写入头标题
			HSSFRow row1 = sheet.createRow(0);
			HSSFCell cell1 = row1.createCell(0);
			cell1.setCellStyle(style1);
			cell1.setCellValue("慢性病报销情况");
			
			//3.2、创建列标题并写入列标题
			HSSFRow row2 = sheet.createRow(1);
			String[] titles = {"身份证号","姓名","报销慢性病","医疗总费用金额","报销金额","医院发票号","医院名称","就诊时间","报销登记时间","审核状态","操作员","审批人"};
			for(int i = 0; i < titles.length; i++){
				HSSFCell cell2 = row2.createCell(i);
				cell2.setCellStyle(style2);
				cell2.setCellValue(titles[i]);
			}
			
			if(expenseInfolist != null && expenseInfolist.size() > 0){
				for(int j = 0; j < expenseInfolist.size() ; j++){
					HSSFRow row = sheet.createRow(j+2);
					row.createCell(0).setCellStyle(style3);
					row.createCell(1).setCellStyle(style3);
					row.createCell(2).setCellStyle(style3);
					row.createCell(3).setCellStyle(style3);
					row.createCell(4).setCellStyle(style3);
					row.createCell(5).setCellStyle(style3);
					row.createCell(6).setCellStyle(style3);
					row.createCell(7).setCellStyle(style3);
					row.createCell(8).setCellStyle(style3);
					row.createCell(9).setCellStyle(style3);
					row.createCell(10).setCellStyle(style3);
					row.createCell(11).setCellStyle(style3);
					row.getCell(0).setCellValue(expenseInfolist.get(j).getIdCard());
					row.getCell(1).setCellValue(expenseInfolist.get(j).getName());
					row.getCell(2).setCellValue(chronicdisDao.queryById(expenseInfolist.get(j).getIllcode()).getIllname());
					row.getCell(3).setCellValue(expenseInfolist.get(j).getMedicalCost());
					row.getCell(4).setCellValue(expenseInfolist.get(j).getExpenseAccount());
					row.getCell(5).setCellValue(expenseInfolist.get(j).getInvoiceNum());
					row.getCell(6).setCellValue(expenseInfolist.get(j).getHospitalName());
					row.getCell(7).setCellValue(expenseInfolist.get(j).getExpenseTime());
					row.getCell(8).setCellValue(expenseInfolist.get(j).getExpenseTime());
					String auditStatus = expenseInfolist.get(j).getAuditStatus();
					if(auditStatus.equals("1")){
						auditStatus = "已审核";
					}else if(auditStatus.equals("0")){
						auditStatus = "不审核";
					}else if(auditStatus.equals("2")){
						auditStatus = "同意";
					}else if(auditStatus.equals("3")){
						auditStatus = "不同意";
					}
					row.getCell(9).setCellValue(auditStatus);
					row.getCell(10).setCellValue(expenseInfolist.get(j).getOperator());
					row.getCell(11).setCellValue(expenseInfolist.get(j).getAgreetor());
				}

			}	
			
			//5、输出
			workbook.write(outputStream);
			workbook.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

}
