package com.gxuwz.medical.domain.homearchives;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Date;

import com.gxuwz.medical.database.DbUtil;
import com.gxuwz.medical.domain.accountArchives.AccountArchives;

/**
 * 家庭档案管理
 * @author Administrator
 *
 */
public class Homearchives {
	private String id;
	private String countyid;//县级编号
	private String townid;//乡镇编号
	private String villageid;//村编号
	private String groupid;//组编号
	private String homeid;//家庭编号
	private String property;//户属性(农业/非农业)
	private String household;//户主姓名
	private int familysize;//家庭人口数
	private int farmersize;//农业人口数
	private String address;//家庭住址
	private Date createtime;//创建档案时间
	private String registrar;//登记员
	AccountArchives archives = new AccountArchives();
	
	public Homearchives() {
		
	}
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCountyid() {
		return countyid;
	}
	public void setCountyid(String countyid) {
		this.countyid = countyid;
	}
	public String getTownid() {
		return townid;
	}
	public void setTownid(String townid) {
		this.townid = townid;
	}
	public String getVillageid() {
		return villageid;
	}
	public void setVillageid(String villageid) {
		this.villageid = villageid;
	}
	public String getGroupid() {
		return groupid;
	}
	public void setGroupid(String groupid) {
		this.groupid = groupid;
	}
	public String getHomeid() {
		return homeid;
	}
	public void setHomeid(String homeid) {
		this.homeid = homeid;
	}
	public String getProperty() {
		return property;
	}
	public void setProperty(String property) {
		this.property = property;
	}
	public String getHousehold() {
		return household;
	}
	public void setHousehold(String household) {
		this.household = household;
	}
	
	public int getFamilysize() {
		return familysize;
	}
	public void setFamilysize(int familysize) {
		this.familysize = familysize;
	}
	public int getFarmersize() {
		return farmersize;
	}
	public void setFarmersize(int farmersize) {
		this.farmersize = farmersize;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	
	public Date getCreatetime() {
		return createtime;
	}
	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}
	public String getRegistrar() {
		return registrar;
	}
	public void setRegistrar(String registrar) {
		this.registrar = registrar;
	}
	@Override
	public String toString() {
		return "Homearchives [countyid=" + countyid + ", townid=" + townid + ", villageid=" + villageid + ", groupid="
				+ groupid + ", homeid=" + homeid + ", property=" + property + ", household=" + household
				+ ", familysize=" + familysize + ", farmersize=" + farmersize + ", address=" + address + ", createtime="
				+ createtime + ", registrar=" + registrar + "]";
	}
	
	

	public Homearchives(String countyid, String townid, String villageid, String groupid, String homeid,
			String property, String household, int familysize, int farmersize, String address, Date createtime,
			String registrar) {
		this.countyid = countyid;
		this.townid = townid;
		this.villageid = villageid;
		this.groupid = groupid;
		this.homeid = homeid;
		this.property = property;
		this.household = household;
		this.familysize = familysize;
		this.farmersize = farmersize;
		this.address = address;
		this.createtime = createtime;
		this.registrar = registrar;
	}



	public Homearchives(String countyid,String townid,String villageid,String groupid,String homeid, String property, String household, int familysize, int farmersize,
			Date createtime, String address, String registrar) {
		
		this.homeid = homeid;
		this.countyid = countyid;
		this.townid = townid;
		this.villageid = villageid;
		this.groupid = groupid;
		this.homeid = homeid;
		this.property = property;
		this.household = household;
		this.familysize = familysize;
		this.farmersize = farmersize;
		this.createtime = createtime;
		this.address = address;
		this.registrar = registrar;
	}

	public void add(String countyid, String townid, String villageid, String groupid, String homeid,String household,
			String property,String address,Date createtime,String registrar,String cardid,String phone, String sex, String healthstatus, String educationlevel, Date birthday, String property2, String iscountryside,String job,String organization) throws SQLException {
		Connection conn = null;
		PreparedStatement ptmt = null;
		try{
			conn =DbUtil.getConnection();
			conn.setAutoCommit(false);
			java.sql.Date createtime2=new java.sql.Date(createtime.getTime());
		    //保存到数据库
			 StringBuffer sqlBuff=new StringBuffer("insert into t_homearchives("
				  		+ "countyid,townid,villageid,groupid,homeid,property,household,"
				  		+ "address,createtime,registrar)");
				  sqlBuff.append("values(?,?,?,?,?,?,?,?,?,?)");
				  ptmt=conn.prepareStatement(sqlBuff.toString());
				  ptmt.setString(1, countyid);
				  ptmt.setString(2, townid);
				  ptmt.setString(3, villageid);
				  ptmt.setString(4, groupid);
				  ptmt.setString(5, homeid);
				  ptmt.setString(6, property);
				  ptmt.setString(7, household);
				  ptmt.setString(8, address);
				  ptmt.setDate(9, createtime2);
				  ptmt.setString(10, registrar);
				  ptmt.executeUpdate(); 
				  archives.bindAdd(household,address,homeid,cardid,phone,sex,healthstatus,educationlevel,property2,iscountryside,birthday,job,organization,conn);
				  conn.commit();
		}catch(SQLException e){
			conn.rollback();
			throw e;
		}finally{
			DbUtil.close(ptmt, conn);
		}
		
	}

	public void edit(String countyid, String townid, String villageid, String groupid, String homeid,String property, String household, Date createtime, String address, String registrar) throws SQLException {
		System.out.println("homeid="+homeid);
		Connection conn=null;
		  PreparedStatement ptmt=null;
		  try {
			  conn = DbUtil.getConnection();
			  conn.setAutoCommit(false);
			  StringBuffer sqlBuff=new StringBuffer("update t_homearchives t set "
			  		+ "t.countyid=?,t.townid=?,t.villageid=?,t.groupid=?,t.homeid=? "
			  		+ ",t.property=? ,t.household=?  "
			  		+ ",t.address=? ,t.createtime=? ,t.registrar=?  where t.homeid=?");
			  ptmt=conn.prepareStatement(sqlBuff.toString());
			  ptmt.setString(1, countyid);
			  ptmt.setString(2, townid);
			  ptmt.setString(3, villageid);
			  ptmt.setString(4, groupid);
			  ptmt.setString(5, homeid);
			  ptmt.setString(6, property);
			  ptmt.setString(7, household);
			  ptmt.setString(8, address);
			  ptmt.setDate(9, new java.sql.Date(createtime.getTime()));
			  ptmt.setString(10, registrar);
			  ptmt.setString(11, homeid);
			  ptmt.executeUpdate(); 
			  conn.commit();
	}catch(SQLException e){
		if(conn!=null){
			conn.rollback();
			throw new SQLException("Failed to update t_homearchives  !", e);
		}
	}finally{
		DbUtil.close(ptmt, conn);
	}
		
	}

	public void del(String homeid) throws SQLException {
		Connection conn=null;
		PreparedStatement ptmt=null;
		try {
			conn =DbUtil.getConnection();
			conn.setAutoCommit(false);
			StringBuffer sqlBuff=new StringBuffer("delete from t_homearchives where homeid=?");
			ptmt=conn.prepareStatement(sqlBuff.toString());
			ptmt.setString(1, homeid); 
			ptmt.executeUpdate();
			bindDel(homeid,conn);
			conn.commit();
		} catch (SQLException e) {
			conn.rollback();
			e.printStackTrace();
		}finally{
			  DbUtil.close(ptmt, conn);
		  } 
		
	}

	private void bindDel(String homeid2, Connection conn) throws SQLException {
		PreparedStatement ptmt=null;
		try{
			StringBuffer sqlBuff=new StringBuffer("delete from t_accountarchives where homeid=?");
			ptmt=conn.prepareStatement(sqlBuff.toString());
			ptmt.setString(1, homeid2); 
			ptmt.executeUpdate();
		} catch (SQLException e) {
			conn.rollback();
			e.printStackTrace();
		}finally{
			  DbUtil.close(ptmt);
		  } 
		
	}
	
}
