package com.gxuwz.medical.domain.family;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.gxuwz.medical.database.DbUtil;
import com.gxuwz.medical.domain.person.Person;

/**
 * 家庭档案管理模块
 * @author 演示
 *
 */
public class Family {
	/**
	 * 家庭编号
	 */
	private String famicode;
	/**
	 * 乡镇编号
	 */
	private String town;
	/**
	 * 村编号
	 */
	private String village;
	/**
	 * 组编号
	 */
	private String group;
	/**
	 * 户主信息
	 */
	private Person holder;
	/**
	 *户属性 
	 */
	private String housePro;
	/**
	 * 人口数
	 */
	private int popuNum;
	/**
	 * 农业人口数
	 */
	private int agriNum;
	/**
	 * 家庭住址
	 */
	private String address;
	/**
	 * 建档时间
	 */
	private java.util.Date creattime;
	/**
	 * 建档人
	 */
	private String creator;
	
	/**
	 * 新建家庭档案
	 */
	public void addFamily()throws Exception{
		
		Connection conn=null;
		
		try{
			conn=DbUtil.getConnection();
			
			if(conn==null){
			   throw new Exception("获取数据库连接对象失败！");
			}
			//1:生成家庭编号
			this.famicode =createFamicode();
			//2:户主个人信息关联家庭档案信息
			this.holder.setFamily(this);
			this.popuNum=1;
			this.agriNum=1;
			
			//3:开启手动提交事务
			conn.setAutoCommit(false);
			//4:添加户主个人信息
			this.holder.addPerson(conn);
			//5:保存家庭档案信息
			saveToDB(conn);
			conn.commit();
		}catch(SQLException e){
			if(conn!=null){
			   conn.rollback();
			}
			e.printStackTrace();
			throw e;
		}finally{
			DbUtil.close(conn);
		}
		
	}
	/**
	 * 生成家庭编号
	 * @return
	 * @throws SQLException
	 */
	private String createFamicode()throws SQLException{
		Connection conn =null;
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		String sql="select max(famicode) from t_family where famicode like'"+this.group+"%' " ;
		
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
			  int beginIndex=maxcode.length()-4;
			  String no=maxcode.substring(beginIndex);
			  number=Integer.parseInt(no);
			  ++number;
			  //使用0补足位数
			  no=String.format("%04d", number);
			  maxcode=this.group+no;
			}else{
				String no=String.format("%04d", number);
				maxcode=this.group+no;
			}
			return maxcode;
		
		}catch(SQLException e){
			
			throw new SQLException("生成家庭编号失败:"+e.getMessage(), e);
		}
		
	}
	/**
	 * 插入记录
	 * @param conn
	 * @throws SQLException
	 */
	private void saveToDB(Connection conn)throws SQLException{

		  PreparedStatement pstmt=null;
		  this.creattime =new java.util.Date();
		  try{
			  StringBuffer sqlBuff=new StringBuffer("insert into t_family(famicode,town,village,group,perscode,housePro,popuNum,agriNum,address,creattime,creator)");
			  sqlBuff.append("values(?,?,?,?,?,?,?,?,?,?,?)");
			  pstmt=conn.prepareStatement(sqlBuff.toString());
			  int index=1;
			  pstmt.setString(index++, this.famicode);
			  pstmt.setString(index++, this.town);
			  pstmt.setString(index++, this.village);
			  pstmt.setString(index++, this.group);
			  pstmt.setString(index++, this.holder.getPerscode());
			  pstmt.setString(index++, this.housePro);
			  pstmt.setInt(index++, this.popuNum);
			  pstmt.setInt(index++, this.agriNum);
			  pstmt.setString(index++, this.address);
			  //注意将java.util.Date 转换为java.sql.Date
			  pstmt.setDate(index++, new java.sql.Date(this.creattime.getTime()));
			  pstmt.setString(index++, this.creator);
			  int count =pstmt.executeUpdate();
		  }catch(SQLException e){
			  e.printStackTrace();
			  throw e;
		  }finally{
			  DbUtil.close(pstmt);
		  } 
}
	public String getFamicode() {
		return famicode;
	}
	public void setFamicode(String famicode) {
		this.famicode = famicode;
	}
	public String getTown() {
		return town;
	}
	public void setTown(String town) {
		this.town = town;
	}
	public String getVillage() {
		return village;
	}
	public void setVillage(String village) {
		this.village = village;
	}
	public String getGroup() {
		return group;
	}
	public void setGroup(String group) {
		this.group = group;
	}
	
	public int getPopuNum() {
		return popuNum;
	}
	public void setPopuNum(int popuNum) {
		this.popuNum = popuNum;
	}
	public int getAgriNum() {
		return agriNum;
	}
	public void setAgriNum(int agriNum) {
		this.agriNum = agriNum;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getHousePro() {
		return housePro;
	}
	public void setHousePro(String housePro) {
		this.housePro = housePro;
	}
	public java.util.Date getCreattime() {
		return creattime;
	}
	public void setCreattime(java.util.Date creattime) {
		this.creattime = creattime;
	}
	public String getCreator() {
		return creator;
	}
	public void setCreator(String creator) {
		this.creator = creator;
	}


	public Person getHolder() {
		return holder;
	}


	public void setHolder(Person holder) {
		this.holder = holder;
	}

}
