package com.gxuwz.medical.domain.medical;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.gxuwz.medical.database.DbUtil;

/**
 * 基础数据模块
 * @author 演示
 *
 */
public class S201 {
	
	private int id;
	private String itemcode;
	private String itemname;
	private String type;
	
	public S201(){
		
	}
	
	public S201(String itemcode,String type)throws Exception{
		if(itemcode ==null||type==null){
			throw new IllegalStateException("parameter can't not be null");
		}
		load();
	}
    public void load()throws SQLException{
    	Connection conn=null;
    	PreparedStatement pstmt=null;
    	ResultSet rs =null;
    	try{
    		String sql="select * from s201 where itemcode=? and type=?";
    		conn=DbUtil.getConnection();
    		pstmt=conn.prepareStatement(sql);
    		pstmt.setString(1, this.itemcode);
    		pstmt.setString(2, this.type);
    		rs=pstmt.executeQuery();
    		if(rs.next()){
    			this.id=rs.getInt(1);
    			this.itemcode=rs.getString(2);
    			this.itemname=rs.getString(3);
    		}
    	}catch(SQLException e){
    		e.printStackTrace();
    		throw e;
    	}
    }
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getItemcode() {
		return itemcode;
	}

	public void setItemcode(String itemcode) {
		this.itemcode = itemcode;
	}

	public String getItemname() {
		return itemname;
	}

	public void setItemname(String itemname) {
		this.itemname = itemname;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@Override
	public String toString() {
		return "S201 [id=" + id + ", itemcode=" + itemcode + ", itemname=" + itemname + ", type=" + type + "]";
	}
	
	
}
