package com.gxuwz.medical.domain.person;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.gxuwz.medical.database.DbUtil;
import com.gxuwz.medical.domain.family.Family;

/**
 * 个人信息
 * @author 演示
 *
 */
public class Person {
	/**
	 * 个人编号
	 */
	private String perscode;
	/**
	 * 对应家庭档案
	 */
	private Family family;
	/**
	 * 农合证卡号
	 */
	private String ruralCard;
	/**
	 * 与户主关系
	 */
	private String relation;//1:表示户主，2表示配偶,3表示子，4表示女，5表示孙子，6表示孙女
	/**
	 * 身份证号
	 */
	private String cardID;
	/**
	 * 姓名
	 */
	private String persname;
	/**
	 * 年龄
	 */
	private int age;
	/**
	 * 性别
	 */
	private String sex;
	/**
	 * 出生日期
	 */
	private String birthday;
	/**
	 * 人员属性
	 */
	private String persPro;
	/**
	 * 是否农村户口
	 */
	private boolean isRural;
	/**
	 * 职业
	 */
	private String occupation;
	/**
	 * 工作单位
	 */
	private String workUnit;
	/**
	 * 常住地址
	 */
	private String liveAddress;
	/**
	 * 联系电话
	 */
	private String telephone;
	
	/**
	 * 添加家庭成员
	 * @param conn
	 * @throws SQLException
	 */
	public void addPerson(Connection conn)throws SQLException{
		try{
			//生成个人编号
			this.perscode =createPerscode();
			saveToDB(conn);
		}catch(SQLException e){
			e.printStackTrace();
			throw e;
		}
	}
	/**
	 * 持久化数据
	 * @param conn
	 * @throws SQLException
	 */
	private void saveToDB(Connection conn)throws SQLException{

		  PreparedStatement pstmt=null;
		  try{
			  StringBuffer sqlBuff=new StringBuffer("insert into t_person(perscode,famicode,ruralCard,cardID,persname,age,sex,birthday,persPro,isRural,occupation,workUnit,liveAddress,telephone,relation)");
			  sqlBuff.append("values(?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
			  pstmt=conn.prepareStatement(sqlBuff.toString());
			  int index=1;
			  pstmt.setString(index++, this.perscode);
			  pstmt.setString(index++, this.family.getFamicode());
			  pstmt.setString(index++, this.ruralCard);
			  pstmt.setString(index++, this.cardID);
			  pstmt.setString(index++, this.persname);
			  pstmt.setInt(index++, this.age);
			  pstmt.setString(index++, this.sex);
			  pstmt.setString(index++, this.birthday);
			  pstmt.setString(index++, this.persPro);
			  pstmt.setBoolean(index++, this.isRural);
			  pstmt.setString(index++, this.occupation);
			  pstmt.setString(index++, this.workUnit);
			  pstmt.setString(index++, this.liveAddress);
			  pstmt.setString(index++, this.telephone);
			  pstmt.setString(index++, this.relation);
			  int count =pstmt.executeUpdate(); 
		  }catch(SQLException e){
			  e.printStackTrace();
			  throw e;
		  }finally{
			  DbUtil.close(pstmt);
		  } 
}
	
	
	/**
	 * 生成个人编号
	 * @return
	 * @throws SQLException
	 */
	private String createPerscode()throws SQLException{
		Connection conn =null;
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		String sql="select max(perscode) from t_person where perscode like'"+this.family.getFamicode()+"%' " ;
		
		try{

			conn=DbUtil.getConnection();
			pstmt=conn.prepareStatement(sql);
			rs=pstmt.executeQuery();
			String maxcode="";
			Integer number=1;
			if(rs.next()){
			   maxcode=rs.getString(1);
			}
			
			if(maxcode!=null){
			  int beginIndex=maxcode.length()-2;
			  String no=maxcode.substring(beginIndex);
			  number=Integer.parseInt(no);
			  ++number;
			  //使用0补足位数
			  no=String.format("%02d", number);
			  maxcode=this.family.getFamicode()+no;
			}else{
				String no=String.format("%02d", number);
				maxcode=this.family.getFamicode()+no;
			}
			return maxcode;
		
		}catch(SQLException e){
			
			throw new SQLException("生成个人编号失败:"+e.getMessage(), e);
		}finally{
			DbUtil.close(rs, pstmt, conn);
		}
		
	}
	public String getPerscode() {
		return perscode;
	}
	public void setPerscode(String perscode) {
		this.perscode = perscode;
	}
	public Family getFamily() {
		return family;
	}
	public void setFamily(Family family) {
		this.family = family;
	}
	public String getRuralCard() {
		return ruralCard;
	}
	public void setRuralCard(String ruralCard) {
		this.ruralCard = ruralCard;
	}
	public String getCardID() {
		return cardID;
	}
	public void setCardID(String cardID) {
		this.cardID = cardID;
	}
	public String getPersname() {
		return persname;
	}
	public void setPersname(String persname) {
		this.persname = persname;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public String getBirthday() {
		return birthday;
	}
	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}
	public String getPersPro() {
		return persPro;
	}
	public void setPersPro(String persPro) {
		this.persPro = persPro;
	}
	public boolean isRural() {
		return isRural;
	}
	public void setRural(boolean isRural) {
		this.isRural = isRural;
	}
	public String getOccupation() {
		return occupation;
	}
	public void setOccupation(String occupation) {
		this.occupation = occupation;
	}
	public String getWorkUnit() {
		return workUnit;
	}
	public void setWorkUnit(String workUnit) {
		this.workUnit = workUnit;
	}
	public String getLiveAddress() {
		return liveAddress;
	}
	public void setLiveAddress(String liveAddress) {
		this.liveAddress = liveAddress;
	}
	public String getTelephone() {
		return telephone;
	}
	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}
	public String getRelation() {
		return relation;
	}
	public void setRelation(String relation) {
		this.relation = relation;
	}

}
