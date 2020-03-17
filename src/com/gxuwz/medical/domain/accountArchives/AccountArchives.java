package com.gxuwz.medical.domain.accountArchives;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Date;

import com.gxuwz.medical.dao.AccountArchivesDao;
import com.gxuwz.medical.dao.HomearchivesDao;
import com.gxuwz.medical.database.DbUtil;
import com.gxuwz.medical.domain.homearchives.Homearchives;
import com.gxuwz.medical.domain.util.AgeUtil;

public class AccountArchives {
	
	private String cardid;//身份证号
	private String name;
	private String relationship;//与户主关系
	private String sex;//性别   0:女  1：男
	private String healthstatus;//健康状况
	private String educationlevel;//文化程度
	private int age;//年龄
	private Date birthday;//出生日期
	private String property;//人员属性 （正式职工、合同工、兼职人员、临时工。政府部门或行政事业单位还需要看是否有编制、职称）
	private String iscountryside;//是否农村户口    1:是，0否
	private String job;//职业
	private String organization;//工作单位
	private String phone;//联系电话
	private String address;//常住地址址
	private String information;//联系方式
	private String homeid;
	private String household;
	private String nongheCard;
	AgeUtil ageUtil = new AgeUtil();
	AccountArchivesDao archivesDao = new AccountArchivesDao();
	public String getHousehold(){
		return household;
	}
	

	public void setHousehold(String household) {
		this.household = household;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCardid() {
		return cardid;
	}
	
	public AccountArchives(){}
	
	public AccountArchives(String cardid,String name, String relationship, String sex, String healthstatus, String educationlevel,
			int age, Date birthday, String property, String iscountryside, String job, String organization,
			String phone, String address, String information, String homeid,String household,String nongheCard) {
		
		this.cardid = cardid;
		this.name = name;
		this.relationship = relationship;
		this.sex = sex;
		this.healthstatus = healthstatus;
		this.educationlevel = educationlevel;
		this.age = age;
		this.birthday = birthday;
		this.property = property;
		this.iscountryside = iscountryside;
		this.job = job;
		this.organization = organization;
		this.phone = phone;
		this.address = address;
		this.information = information;
		this.homeid = homeid;
		this.household = household;
		this.nongheCard = nongheCard;
	}
	
	public AccountArchives(String cardid,String name, String relationship, String sex, String healthstatus, String educationlevel,
			int age, Date birthday, String property, String iscountryside, String job, String organization,
			String phone, String address, String information, String homeid, String nongheCard) {

		this.cardid = cardid;
		this.name = name;
		this.relationship = relationship;
		this.sex = sex;
		this.healthstatus = healthstatus;
		this.educationlevel = educationlevel;
		this.age = age;
		this.birthday = birthday;
		this.property = property;
		this.iscountryside = iscountryside;
		this.job = job;
		this.organization = organization;
		this.phone = phone;
		this.address = address;
		this.information = information;
		this.homeid = homeid;
		this.nongheCard = nongheCard;
	}


	public void setCardid(String cardid) {
		this.cardid = cardid;
	}
	public String getRelationship() {
		return relationship;
	}
	public void setRelationship(String relationship) {
		this.relationship = relationship;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public String getHealthstatus() {
		return healthstatus;
	}
	
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public void setHealthstatus(String healthstatus) {
		this.healthstatus = healthstatus;
	}
	public String getEducationlevel() {
		return educationlevel;
	}
	public void setEducationlevel(String educationlevel) {
		this.educationlevel = educationlevel;
	}

	
	public Date getBirthday() {
		return birthday;
	}
	public String getProperty() {
		return property;
	}
	public void setProperty(String property) {
		this.property = property;
	}
	public String getIscountryside() {
		return iscountryside;
	}
	public void setIscountryside(String iscountryside) {
		this.iscountryside = iscountryside;
	}
	public String getJob() {
		return job;
	}
	public void setJob(String job) {
		this.job = job;
	}
	public String getOrganization() {
		return organization;
	}
	public void setOrganization(String organization) {
		this.organization = organization;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	
	public String getNongheCard() {
		return nongheCard;
	}

	public void setNongheCard(String nongheCard) {
		this.nongheCard = nongheCard;
	}


	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getInformation() {
		return information;
	}
	public void setInformation(String information) {
		this.information = information;
	}
	public String getHomeid() {
		return homeid;
	}
	public void setHomeid(String homeid) {
		this.homeid = homeid;
	}
	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}
	

	@Override
	public String toString() {
		return "AccountArchives [cardid=" + cardid + ", name=" + name + ", relationship=" + relationship + ", sex="
				+ sex + ", healthstatus=" + healthstatus + ", educationlevel=" + educationlevel + ", age=" + age
				+ ", birthday=" + birthday + ", property=" + property + ", iscountryside=" + iscountryside + ", job="
				+ job + ", organization=" + organization + ", phone=" + phone + ", address=" + address
				+ ", information=" + information + ", homeid=" + homeid + ", household=" + household + ", nongheCard="
				+ nongheCard + ", ageUtil=" + ageUtil + "]";
	}


	public void add() throws Exception {
		Connection conn=null;
		try{
			conn =DbUtil.getConnection();
			//开启手动提交事务
			conn.setAutoCommit(false);
			//保存到数据库
			saveToDB(conn);
			//提交事务
			conn.commit();
		}catch (Exception e) {
			if(conn!=null){
				conn.rollback();
			}
			throw e;
		}finally{
			DbUtil.close(conn);
		}
		
	}

	private void saveToDB(Connection conn) throws SQLException {
		 PreparedStatement ptmt=null;
		 try{
			  StringBuffer sqlBuff=new StringBuffer("insert into t_accountarchives(cardid,name,relationship,sex,healthstatus,educationlevel,age,birthday,property,iscountryside,job,organization,phone,address,information,homeid,household,nongheCard)");
			  sqlBuff.append("values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
			  ptmt=conn.prepareStatement(sqlBuff.toString());
			  ptmt.setString(1, this.cardid);
			  ptmt.setString(2, this.name);
			  ptmt.setString(3, this.relationship);
			  ptmt.setString(4, this.sex);
			  ptmt.setString(5, this.healthstatus);
			  ptmt.setString(6, this.educationlevel);
			  ptmt.setInt(7, this.age);
			  ptmt.setDate(8, new java.sql.Date(this.birthday.getTime()));
			  ptmt.setString(9, this.property);
			  ptmt.setString(10, this.iscountryside);
			  ptmt.setString(11, this.job);
			  ptmt.setString(12, this.organization);
			  ptmt.setString(13, this.phone);
			  ptmt.setString(14, this.address);
			  ptmt.setString(15, this.information);
			  ptmt.setString(16, this.homeid);
			  ptmt.setString(17, this.household);
			  ptmt.setString(18, this.nongheCard);
			  ptmt.executeUpdate(); 
		  }catch(SQLException e){
			  throw e;
		  }finally{
			  DbUtil.close(ptmt);
		  } 
		
	}

	public void edit() throws Exception {
		 Connection conn=null;
		  PreparedStatement ptmt=null;
		  try {
			  conn = DbUtil.getConnection();
			  conn.setAutoCommit(false);
			  StringBuffer sqlBuff=new StringBuffer("update t_accountarchives set cardid=?,name=?,relationship=?,sex=?,healthstatus=?,educationlevel=?,age=?,birthday=?,property=?,iscountryside=?,job=?,organization=?,phone=?,address=?,information=?,homeid=?  where cardid=?");
			  ptmt=conn.prepareStatement(sqlBuff.toString());
			  ptmt.setString(1, this.cardid);
			  ptmt.setString(2, this.name);
			  ptmt.setString(3, this.relationship);
			  ptmt.setString(4, this.sex);
			  ptmt.setString(5, this.healthstatus);
			  ptmt.setString(6, this.educationlevel);
			  ptmt.setInt(7, this.age);
			  ptmt.setDate(8, new java.sql.Date(this.birthday.getTime()));
			  ptmt.setString(9, this.property);
			  ptmt.setString(10, this.iscountryside);
			  ptmt.setString(11, this.job);
			  ptmt.setString(12, this.organization);
			  ptmt.setString(13, this.phone);
			  ptmt.setString(14, this.address);
			  ptmt.setString(15, this.information);
			  ptmt.setString(16, this.homeid);
			  ptmt.setString(17, this.cardid);
			 
			  ptmt.executeUpdate(); 
			  conn.commit();
		} catch (SQLException e) {
			if(conn!=null){
				conn.rollback();
			}
			throw new SQLException("Failed to update t_accountarchives  !", e);
		}finally{
			 DbUtil.close(ptmt, conn);
		}
		
	}

	public void del(String cardid) throws Exception {
		Connection conn=null;
		PreparedStatement ptmt=null;
		this.cardid = cardid;
		try{
			conn = DbUtil.getConnection();
			conn.setAutoCommit(false);
			
			StringBuffer sqlBuff=new StringBuffer("delete from t_accountarchives where cardid=?");
			ptmt=conn.prepareStatement(sqlBuff.toString());
			ptmt.setString(1, this.cardid);
			ptmt.executeUpdate(); 
			
			 conn.commit();
		}catch(SQLException e){
			conn.rollback();
			throw new SQLException("Failed to delete record from table !", e);
		}finally {
			DbUtil.close(conn);
		}
	}


	public void bindAdd(String household2, String address2, String homeid2,String cardid2,String phone2,String sex,String healthstatus,String educationlevel,String property2,String iscountryside,Date birthday,String job,String organization, Connection conn) throws SQLException {
		PreparedStatement ptmt=null;					
		System.out.println("property2:"+property2);
		try{
			  if(property2=="1" || "1".equals("1")){
				  property2 = "农业";
			  }else{
				  property2 = "非农业";
			  }
			  
			  //生成nongheCard
			
				//根据家庭编号生成农合证号
				String nongheCard = "";
				if(archivesDao.createNongheCard(homeid2)!=null){
					long nongheCardLong = Long.valueOf(archivesDao.createNongheCard(homeid2))+1;
					nongheCard = String.valueOf(nongheCardLong);
				}else{
					long nongheCardLong = Long.valueOf(homeid2+"00")+1;
					nongheCard = String.valueOf(nongheCardLong);
				}
			  
			  System.out.println("property2"+property2);
			  int age = ageUtil.getAge(birthday);
			  System.out.println("aaa"+household2);
			  StringBuffer sqlBuff=new StringBuffer("insert into t_accountarchives(household,address,homeid,cardid,relationship,phone,sex,healthstatus,educationlevel,property,iscountryside,name,birthday,age,job,organization,nongheCard)");
			  sqlBuff.append("values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
			  ptmt=conn.prepareStatement(sqlBuff.toString());
			  ptmt.setString(1, household2);
			  ptmt.setString(2, address2);
			  ptmt.setString(3, homeid2);
			  ptmt.setString(4, cardid2);
			  ptmt.setString(5, "户主");
			  ptmt.setString(6, phone2);
			  ptmt.setString(7, sex);
			  ptmt.setString(8, healthstatus);
			  ptmt.setString(9, educationlevel);
			  ptmt.setString(10, property2);
			  ptmt.setString(11, iscountryside);
			  ptmt.setString(12, household2);
			  ptmt.setDate(13, new java.sql.Date(birthday.getTime()) );
			  ptmt.setInt(14, age );
			  ptmt.setString(15, job );
			  ptmt.setString(16, organization );
			  ptmt.setString(17, nongheCard );
			  ptmt.executeUpdate(); 
		}catch(Exception e){
			conn.rollback();
			throw new SQLException("Failed to delete record from table !", e);
		}finally {
			DbUtil.close(ptmt);
		}
		
	}
	
	
}
