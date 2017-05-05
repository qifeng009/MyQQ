/**
 * ���ݿ����DML
 */
package com.server.data;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.common.Message;

/**
 * @author ���ǳ�
 *
 */
public class Dml {

	 private Connection con = null;
	 private PreparedStatement pre = null;
	 private ResultSet rs = null;
		
	 public Dml(){}
	/**
	 * �����ݿ��в�������
	 * @param id
	 * @param password
	 * @param nickName
	 * @param sex
	 * @param birthday
	 * @param city
	 */
	public void insert(int id,String password,String nickName,
			String sex,Date birthday,String city){
		Connection conn = null;
		CallableStatement cs = null;
		ResultSet rs = null;
		
		try{
			conn = ConnectionFactory.getConnnection();
			String sql = "{call SP_INSERTUSRS(?,?,?,?,?,?)}";
			cs = conn.prepareCall(sql);
			cs.setInt(1, id);
			cs.setString(2, password);
			cs.setString(3, nickName);
			cs.setString(4, sex);
			cs.setDate(5, birthday);
			cs.setString(6,city);
			//ִ��SQL���
			cs.execute();
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			DbClose.close(rs, cs, conn);
		}
	}
	
	public void insert(Message message){
		 //�������ݿ����Ӷ���
        con = ConnectionFactory.getConnnection();
        try {            
          
        	String sql = "INSERT INTO TEXT VALUES(Text_tno.NEXTVAL,?,?,?,?,?,?,?)";
        	pre = con.prepareCall(sql);	
            pre.clearParameters();    
            pre.setString(1, message.getInfo());
            pre.setString(2, message.getFontType());
            pre.setInt(3, message.getFontSize());
            String str = ""+message.getFontColor().getRed()+"*"+message.getFontColor().getGreen()+"*"+message.getFontColor().getBlue();
            pre.setString(4,str);
            pre.setInt(5, message.getIsBold()?1:0);
            pre.setInt(6, message.getIsItatic()?1:0);
            pre.setInt(7, message.getIsUnderline()?1:0);
            
            //ִ�в�ѯ�������ȡ���صĽ����
			pre.execute();
			
            String sql1 = "INSERT INTO CHATINFO VALUES(ChatInfo_cno.NEXTVAL,?,?, Current_timestamp,Text_tno.CURRVAL)";
            
            
            pre = con.prepareCall(sql1);	
            pre.clearParameters();           
            pre.setInt(1, message.getSendQq());
            pre.setInt(2, message.getReceiveQq());
           
          
			//ִ�в�ѯ�������ȡ���صĽ����
			pre.execute();
			
            
        } catch (Exception ex) {
            ex.printStackTrace();
        }finally{
        	DbClose.close(rs, pre, con);
        }
	}
	
	public void update(int id,String password,String nickName,
			String sex,Date birthday,String city){
		Connection conn = null;
		CallableStatement cs = null;
		ResultSet rs = null;
		
		try{
			conn = ConnectionFactory.getConnnection();
			String sql = "{call SP_UPDATEUSRS(?,?,?,?,?,?)}";
			cs = conn.prepareCall(sql);
			cs.setInt(1, id);
			cs.setString(2, password);
			cs.setString(3, nickName);
			cs.setString(4, sex);
			cs.setDate(5, birthday);
			cs.setString(6,city);
			//ִ��SQL���
			cs.execute();
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			DbClose.close(rs, cs, conn);
		}
	}
	
	public void delete(int id){
		Connection conn = null;
		CallableStatement cs = null;
		ResultSet rs = null;
		
		try{
			conn = ConnectionFactory.getConnnection();
			String sql = "{call SP_DELETEUSERS(?)}";
			cs = conn.prepareCall(sql);
			cs.setInt(1, id);
			//ִ��SQL���
			cs.execute();
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			DbClose.close(rs, cs, conn);
		}
	}
}
