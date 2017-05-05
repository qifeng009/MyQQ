package com.tools;

import java.io.*;
import java.net.*;
import java.sql.*;
import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

import javax.swing.DefaultListModel;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;

import oracle.sql.DATE;

import com.common.Port;
import com.common.UserInfoBean;
import com.server.data.ConnectionFactory;
import com.server.data.DbClose;

/**
 *������Ǵ����û���������Ĺ����࣬����Ҫ������¹��ܣ�
 * 1���û���¼ 
 * 2��ע�����û�
 * 3�������û�
 * 4�����Һ���
 * 5�������û��Լ�����Ϣ
 * 6����ӡ�ɾ������
 * 7�������û�����
 * 8�� �һ�����
 */
public class Server implements Runnable {

    private Socket socket = null; //�����׽ӿ�
    private DefaultListModel listModel = null;
    private JLabel jLabel2 = null;
    private DataInputStream in = null; //����������
    private DataOutputStream out = null; //���������

    //private Hashtable th = new Hashtable();

    private Connection con = null;
    private PreparedStatement pre = null;
	private ResultSet rs = null;
    
	private UserInfoBean userInfo = null;
	private UserInfoBean friendInfo = null;
    private Boolean flag = true;//���Ʒ������̵߳�������ֹͣ

    private String IP = "";
    private int PORT = 0;
    
    private int qqnum;
    private String password;
    
    private int serverNo;
    
    public Server(Socket socket,DefaultListModel listModel,JLabel jLabel2,int serverNo) {
        this.socket = socket; //����׽ӿ�
        this.listModel = listModel;
        this.jLabel2 = jLabel2;
        this.serverNo = serverNo;
        
        try {
            //����������
            in = new DataInputStream(socket.getInputStream());
            //���������
            out = new DataOutputStream(socket.getOutputStream());
           
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public void run() {
        try {
            while (flag) {
                String str = in.readUTF();
                System.out.println(str);
                if (str.equals("end")) {
                    break;
                } else if (str.equals("registerNewUser")) {
                    registerNewUser();
                } else if (str.equals("login")) {
                	System.out.println("login");
                    login();
                } else if (str.equals("queryUser")) {
                    int qqnum = Integer.parseInt(in.readUTF());
                    queryUser(qqnum);
                } else if (str.equals("addFriend")) {
                    addFriend();
                } else if (str.equals("deleteFriend")) {
                    deleteFriend();
                } else if (str.equals("updateOwnInfo")) {
                    updateOwnInfo();
                } else if (str.equals("logout")) {
                    logout();    
                    break;
                } else if (str.equals("queryFriend")){
                	queryFriend(qqnum);
                } else if(str.equals("getPwd")){
                	getPassword();
                } else if (str.equals("queryUser1")) {
                    int qqnum = Integer.parseInt(in.readUTF());
                    String nickname = in.readUTF();
                    queryUser1(qqnum,nickname);
                } 
            }
        } catch (Exception e) {
            e.printStackTrace();
            try {
				socket.close();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
        }
    }

    //�����������ע�����û�
    public void registerNewUser() {
    	 //�������ݿ����Ӷ���
        con = ConnectionFactory.getConnnection();
        try {            
          
        	int newqq = 0;
        	 String sql = "SELECT MAX(QQ) FROM USERINFO ";
       
            pre = con.prepareStatement(sql);			
		    //ִ�в�ѯ�������ȡ���صĽ����
	    	rs = pre.executeQuery();
	    	while(rs.next()){
	    		newqq = rs.getInt(1);
	    	}
	    	newqq++;
	    	String pwd = in.readUTF();
	    	String sign = in.readUTF();
	    	int photoID = in.readInt();
	    	String nickname = in.readUTF();
	    	String sex = in.readUTF();
	    	String birthday = in.readUTF();
	    	String consel = in.readUTF();
	    	String bloodType = in.readUTF();
	    	String diploma = in.readUTF();
	    	String telephone = in.readUTF();
	    	String email = in.readUTF();
	    	String address = in.readUTF();
	    	
	    	int question1No = in.readInt();
	    	String answer1 = in.readUTF();
	    	int question2No = in.readInt();
	    	String answer2 = in.readUTF();
	    	int question3No = in.readInt();
	    	String answer3 = in.readUTF();
	    	
	    	
	    	DateFormat fmt =new SimpleDateFormat("yyyy-MM-dd");            
            Date date = fmt.parse(birthday);
            java.sql.Date time = new java.sql.Date(date.getTime()); 
            
            String sql1 = "INSERT INTO USERINFO VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?)";            
            
            pre = con.prepareCall(sql1);	
            pre.clearParameters();     
            
            pre.setInt(1, newqq);
            pre.setString(2,pwd);
            pre.setString(3, sign);
            pre.setInt(4, photoID);
            pre.setString(5, nickname);
            pre.setString(6, sex);
            pre.setDate(7, time);
            pre.setString(8,consel);
            pre.setString(9,bloodType);
            pre.setString(10, diploma);
            pre.setString(11, telephone);
            pre.setString(12, email);
            pre.setString(13, address);
           
            
			//ִ�в�ѯ�������ȡ���صĽ����
			pre.execute();
			
			String sql2 = "insert into GetPwdInfo values(getPwd_gno.NEXTVAL,?,?,"+newqq+")";
		    pre = con.prepareCall(sql2);	
	        pre.clearParameters();   
	        
			pre.setInt(1, question1No);
			pre.setString(2, answer1);
			pre.execute();
			
			String sql3 = "insert into GetPwdInfo values(getPwd_gno.NEXTVAL,?,?,"+newqq+")";
			pre = con.prepareCall(sql3);	
	        pre.clearParameters();   
	        
			pre.setInt(1, question2No);
			pre.setString(2, answer2);
			pre.execute();
			
			String sql4 = "insert into GetPwdInfo values(getPwd_gno.NEXTVAL,?,?,"+newqq+")";
			pre = con.prepareCall(sql4);	
	        pre.clearParameters();   
	        
			pre.setInt(1, question3No);
			pre.setString(2, answer3);
			pre.execute();
			
			out.writeInt(newqq);
            
        } catch (Exception ex) {
            ex.printStackTrace();
        }finally{
        	DbClose.close(rs, pre, con);
        }
    }

    //�˷������ڴ����û���¼
    public void login() {
    	 //�������ݿ����Ӷ���
        con = ConnectionFactory.getConnnection();
        try {
            qqnum = Integer.parseInt(in.readUTF()); //��ȡ�ͻ���QQ����
            password = in.readUTF(); //��ȡ�ͻ�������
            
            int port = Integer.parseInt(in.readUTF());
            //JOptionPane.showMessageDialog(null, "server:"+port);
            String ClientIP = in.readUTF();
            int qqnum1 = Integer.parseInt(in.readUTF());
            
            IP = socket.getInetAddress().toString();
            PORT = socket.getPort();
            
            System.out.println(socket.getInetAddress());
     //       System.out.println(socket.getLocalAddress());
     //       System.out.println(socket.getLocalSocketAddress());
     //       System.out.println(socket.getLocalPort());
            System.out.println(socket.getPort());
            
          //  int port = Integer.parseInt(in.readUTF());//��ȡ�ͻ��Ķ˿ں�
            String sql = "SELECT * FROM USERINFO WHERE QQ = " + qqnum +
                          " AND PWD = '" + password + "'";
            
            pre = con.prepareStatement(sql);			
			//ִ�в�ѯ�������ȡ���صĽ����
			rs = pre.executeQuery();
			
		    boolean isSuccess = false;
		    
		    boolean isExist = false;
		    while(rs.next()){
		    	isExist = true;
		    }
		    

            String sql1 = "SELECT * FROM LOGIN WHERE LQQ = "+qqnum;
            
            pre = con.prepareStatement(sql1);			
			//ִ�в�ѯ�������ȡ���صĽ����
			rs = pre.executeQuery();
			
			boolean isReLogin = false;
			while(rs.next()){
				isReLogin = true;
			}
			
			if(!isReLogin && isExist){
				isSuccess = true;
			}
				
			//�����¼�ɹ�,��ִ�����²���
            if (isSuccess) {
            	Port p = new Port();
                p.put(ClientIP, port);
                p.put(qqnum1, port);
                p.put(qqnum1, ClientIP);
                
              //  JOptionPane.showMessageDialog(null, ClientIP);
             //   JOptionPane.showMessageDialog(null, "Server:"+ClientIP+",�ͻ��˷���Ķ˿ڣ�"+p.port.get(ClientIP));
                
              //  JOptionPane.showMessageDialog(null, "Server:loginSucess");
            	out.writeUTF("loginSuccess");
              //  out.writeUTF("sendUserInfo");
            	setOnline(qqnum);
                queryUser(qqnum);            
                queryFriend(qqnum);
           //     noticeAll();
            }
            else{
            	if(isReLogin){
            		out.writeUTF("reLogin");
            	}else{
            		out.writeUTF("loginFail");
            	}
                
                in.close();
                out.close();
                socket.close();
                flag = false;
            }
            
        } catch (Exception ex) {
            try {
                out.writeUTF("loginFail");
                in.close();
                out.close();
                socket.close();
                flag = false;
            } catch (IOException ex1) {
                ex1.printStackTrace();
            }
            ex.printStackTrace();
        }finally{
        	DbClose.close(rs, pre, con);
        }
    }


    //�˷������ڲ��Һ���
    public void queryFriend(int qqnum) {
    	 //�������ݿ����Ӷ���
        con = ConnectionFactory.getConnnection();
               

        Vector friendNum = new Vector(); //���������ڴ洢���ѵ�QQ����
        try {
            //��������QQ�����SQL���
            String sql1 =
                    "SELECT fqq,fsno FROM FRIENDS WHERE QQ = "+qqnum;
            pre = con.prepareStatement(sql1);			
			//ִ�в�ѯ�������ȡ���صĽ����
			rs = pre.executeQuery();
			int j = 0;
			int subgroupNo[] = new int[100];
            //�����ѵ�QQ�������δ���������
            while (rs.next()) {
                friendNum.addElement(rs.getInt(1));             
                subgroupNo[j++] = rs.getInt(2);
            }
            
            out.writeInt(friendNum.size());
            friendInfo = new UserInfoBean();
            //����ȡ�����ѵĺ���,����������Ϣ
            for (int i = 0; i < friendNum.size(); i++) {
                int num = (Integer) friendNum.elementAt(i);
                String sql2 = "SELECT * FROM USERINFO WHERE QQ = " + num;
                pre = con.prepareStatement(sql2);			
    			//ִ�в�ѯ�������ȡ���صĽ����
    			rs = pre.executeQuery();
                rs.next();
                //��ͻ��˷��ͺ�����Ϣ
                out.writeUTF(""+rs.getInt(1));
			
				out.writeUTF(rs.getString(3));	
				out.writeInt(rs.getInt(4));
				out.writeUTF(rs.getString(5));
				out.writeUTF(rs.getString(6));
				out.writeUTF(rs.getDate(7).toString());
				out.writeUTF(rs.getString(8));
				out.writeUTF(rs.getString(9));
				out.writeUTF(rs.getString(10));
				out.writeUTF(rs.getString(11));
				out.writeUTF(rs.getString(12));
				out.writeUTF(rs.getString(13));
								
				friendInfo.setQq(rs.getInt(1));
				
				friendInfo.setSign(rs.getString(3));
				friendInfo.setPhotoID(rs.getInt(4));
				friendInfo.setNickName(rs.getString(5));
				friendInfo.setSex(rs.getString(6));
				friendInfo.setBirthday(""+rs.getDate(7));
				friendInfo.setConstellation(rs.getString(8));
				friendInfo.setBloodType(rs.getString(9));
				friendInfo.setDiploma(rs.getString(10));
				friendInfo.setTelephone(rs.getString(11));
				friendInfo.setEmail(rs.getString(12));
				friendInfo.setAddress(rs.getString(13));
				
				 String sql3 = "SELECT SNAME FROM SUBGROUP WHERE SNO = " + subgroupNo[i];
	             pre = con.prepareStatement(sql3);			
	    		 //ִ�в�ѯ�������ȡ���صĽ����
	    		 rs = pre.executeQuery();
	             rs.next();
	             
	             out.writeUTF(rs.getString(1));
	             
	             friendInfo.setSubGroupName(rs.getString(1));
	             
	             String sql4 = "SELECT LIP,LPORT FROM LOGIN WHERE LQQ = " + num;
	             pre = con.prepareStatement(sql4);			
	    		 //ִ�в�ѯ�������ȡ���صĽ����
	    		 rs = pre.executeQuery();
	    		 boolean isLogin = false;
	 		     if(rs.next()){
	 		    	isLogin = true;
	 		     }
				if (isLogin) {				
					
					out.writeUTF("onLine");
					
					out.writeUTF(rs.getString(1));
					out.writeInt(rs.getInt(2));

					friendInfo.setIP(rs.getString(1));
					friendInfo.setPort(rs.getInt(2));
					friendInfo.setStatus(true);
				}else{
					out.writeUTF("NotonLine");
				}
	            
            }
          //  out.writeUTF("queryFriendOver");
        } catch (Exception ex) {
          /*  try {
                out.writeUTF("queryFriendFail");
            } catch (IOException ex2) {
                ex2.printStackTrace();
            }
            */
            ex.printStackTrace();
        }finally{
        	 DbClose.close(rs, pre, con);
        }
    }

    //�һ�����
    public void getPassword(){
    	 //�������ݿ����Ӷ���
        con = ConnectionFactory.getConnnection();
        
        try {
        	
        	qqnum = Integer.parseInt(in.readUTF()); //��ȡ�ͻ���QQ����
        	
        	 String sql =
                     "select question,answer from getpwdinfo where gqq= "+qqnum;
             pre = con.prepareStatement(sql);			
 			//ִ�в�ѯ�������ȡ���صĽ����
 			rs = pre.executeQuery();

             //�����ѵ�QQ�������δ���������
             while (rs.next()) {          
            	 out.writeInt(rs.getInt(1));
            	 out.writeUTF(rs.getString(2));
             }
             
             String sql2 = "select pwd from userinfo where qq= "+qqnum;
             pre = con.prepareStatement(sql2);
             rs = pre.executeQuery();
             rs.next();
             
             out.writeUTF(rs.getString(1));
             
        }catch (Exception ex) {
        
            ex.printStackTrace();
        }finally{
    	 DbClose.close(rs, pre, con);
        }
    }
    //�˷������ڲ����û�
    public void queryUser(int qqnum) {   
    	 //�������ݿ����Ӷ���
        con = ConnectionFactory.getConnnection();
        try {
            //int qqnum = in.readInt();
            String sql = "SELECT * FROM USERINFO WHERE QQ = " + qqnum;
            pre = con.prepareStatement(sql);	
            userInfo = new UserInfoBean();
			//ִ�в�ѯ�������ȡ���صĽ����
			rs = pre.executeQuery();
            while (rs.next()) {          					
            	//��ͻ��˷����û���Ϣ
                out.writeUTF(""+rs.getInt(1));
			
				out.writeUTF(rs.getString(3));	
				out.writeInt(rs.getInt(4));
				out.writeUTF(rs.getString(5));
				out.writeUTF(rs.getString(6));
				out.writeUTF(rs.getDate(7).toString());
				out.writeUTF(rs.getString(8));
				out.writeUTF(rs.getString(9));
				out.writeUTF(rs.getString(10));
				out.writeUTF(rs.getString(11));
				out.writeUTF(rs.getString(12));
				out.writeUTF(rs.getString(13));
				
				userInfo.setQq(rs.getInt(1));
				
				userInfo.setSign(rs.getString(3));
				userInfo.setPhotoID(rs.getInt(4));
				userInfo.setNickName(rs.getString(5));
				userInfo.setSex(rs.getString(6));
				userInfo.setBirthday(""+rs.getDate(7));
				userInfo.setConstellation(rs.getString(8));
				userInfo.setBloodType(rs.getString(9));
				userInfo.setDiploma(rs.getString(10));
				userInfo.setTelephone(rs.getString(11));
				userInfo.setEmail(rs.getString(12));
				userInfo.setAddress(rs.getString(13));
				
				listModel.addElement(userInfo.getNickName()+"("+userInfo.getQq()+")");
				jLabel2.setText(""+listModel.getSize());
            }
            /*else{
             //   out.writeUTF("noUser");
            }
            */
            //out.writeUTF("queryUserOver");
        } catch (Exception ex) {
          /*  try {
            //    out.writeUTF("queryUserFail");
            } catch (IOException ex1) {
                ex1.printStackTrace();
            }
            */
            ex.printStackTrace();
            
        } finally {
            DbClose.close(rs, pre, con);
        }
    }

  //�˷������ڲ����û�
    public void queryUser1(int qqnum,String nickname) {   
    	 //�������ݿ����Ӷ���
        con = ConnectionFactory.getConnnection();
        try {
            //int qqnum = in.readInt();
            String sql = "SELECT * FROM USERINFO WHERE QQ = " + qqnum +" or nickname = '"+nickname+"'";
            pre = con.prepareStatement(sql);	
            userInfo = new UserInfoBean();
			//ִ�в�ѯ�������ȡ���صĽ����
			rs = pre.executeQuery();
			
			boolean isExist = false;
		   
            while (rs.next()) {          					
            	//��ͻ��˷����û���Ϣ
            	isExist = true;
            	out.writeUTF("ExistUser");
            	out.writeUTF(""+rs.getInt(1));
    			
				out.writeUTF(rs.getString(3));	
				out.writeInt(rs.getInt(4));
				out.writeUTF(rs.getString(5));
            }
            if(!isExist){
            	out.writeUTF("NotExistUser");
            }
        }catch (Exception ex) {
               
                  ex.printStackTrace();
                  
              } finally {
                  DbClose.close(rs, pre, con);
              }
    }
    
    //�˷��������û���Ӻ���
    public void addFriend() {
    	 //�������ݿ����Ӷ���
        con = ConnectionFactory.getConnnection();
        try {            
          
        	//int subGroupNo = 0;
        	String sql = "INSERT INTO FRIENDS VALUES(Friends_fno.nextval,?,?,?,?,?)";
        	Date time1 = new java.util.Date();           
            java.sql.Date time = new java.sql.Date(time1.getTime()); 
            
            pre = con.prepareCall(sql);	
            pre.clearParameters();           
            pre.setInt(1, in.readInt());
            pre.setInt(2, in.readInt());
            pre.setDate(3, time);
            pre.setInt(4, 0);
            pre.setInt(5, in.readInt());
           
			//ִ�в�ѯ�������ȡ���صĽ����
			pre.execute();
			
            
        } catch (Exception ex) {
            ex.printStackTrace();
        }finally{
        	DbClose.close(rs, pre, con);
        }
        
       
    }

    //�˷��������û�ɾ������
    public void deleteFriend() {
    	 //�������ݿ����Ӷ���
        con = ConnectionFactory.getConnnection();
        try {            
          
        	int fqqnum = in.readInt();
        	int qq = in.readInt();
        	String sql = "DELETE FROM FRIENDS WHERE QQ = "+qq+" and fqq="+fqqnum;
           
            pre = con.prepareCall(sql);	
            pre.clearParameters();           
          
			//ִ�в�ѯ�������ȡ���صĽ����
			pre.execute();
			
            
        } catch (Exception ex) {
            ex.printStackTrace();
        }finally{
        	DbClose.close(rs, pre, con);
        }
    }

    //�˷��������û������Լ�����Ϣ
    public void updateOwnInfo() {
    	 //�������ݿ����Ӷ���
        con = ConnectionFactory.getConnnection();
        try {            
          	    	
            String sql1 = "UPDATE USERINFO SET PWD = ?,SIGN = ?,PHOTOID = ?,"
            		+"nickName = ?,SEX = ?,BIRTHDAY = ?,CONSTELLATION = ?,"
            		+"BOOLDTYPE = ?,DIPLOMA = ?,TELEPHONE = ?,EMAIL = ?,ADDRESS = ?";                        
            
            pre.setString(1,in.readUTF());
            pre.setString(2, in.readUTF());
            pre.setInt(3, in.readInt());
            pre.setString(4, in.readUTF());
            pre.setString(5, in.readUTF());
            pre.setString(6,in.readUTF() );
            pre.setString(7, in.readUTF());
            pre.setString(8, in.readUTF());
            pre.setString(9, in.readUTF());
            pre.setString(10, in.readUTF());
            pre.setString(11, in.readUTF());
            pre.setString(12, in.readUTF());
            
			//ִ�в�ѯ�������ȡ���صĽ����
			pre.execute();
					
            
        } catch (Exception ex) {
            ex.printStackTrace();
        }finally{
        	DbClose.close(rs, pre, con);
        }
    }

    //�˷������ڴ����û�����
    public void logout() {
    	String strIndex = userInfo.getNickName()+"("+userInfo.getQq()+")";
    	System.out.println(strIndex);    	
    	int index = listModel.indexOf(strIndex);
    	listModel.remove(index);
    	jLabel2.setText(""+listModel.getSize());
    	 //�������ݿ����Ӷ���
        con = ConnectionFactory.getConnnection();
        try {            
          
            String sql = "DELETE FROM LOGIN WHERE LQQ = "+qqnum; 
            pre = con.prepareStatement(sql);	
            pre.clearParameters();
			//ִ�в�ѯ�������ȡ���صĽ����
			pre.execute();			
            
        } catch (Exception ex) {
            ex.printStackTrace();
        }finally{
        	DbClose.close(rs, pre, con);
        }
    	try{
    		out.writeUTF("logout");
    		out.close();
    		in.close();
    		socket.close();
    	}catch(Exception e){
    		e.printStackTrace();
    	}
    	/*
    	 //�������ݿ����Ӷ���
        con = ConnectionFactory.getConnnection();
        try {
            int qqnum = Integer.parseInt(in.readUTF()); //��ȡ�ͻ���QQ����
            String password = in.readUTF(); //��ȡ�ͻ�������
          
            String sql = "DELETE FROM LOGIN WHERE QQ = "+qqnum;
           
            pre = con.prepareStatement(sql);	
            pre.clearParameters();
          
			//ִ�в�ѯ�������ȡ���صĽ����
			pre.executeQuery();
			
            
        } catch (Exception ex) {
            ex.printStackTrace();
        }finally{
        	DbClose.close(rs, pre, con);
        }
        */
    }
    
    public void setOnline(int qq){
    	 //�������ݿ����Ӷ���
        con = ConnectionFactory.getConnnection();
        try {            
          
            String sql = "INSERT INTO LOGIN(LNO,LIP,LPORT,LDATE,LSTATUS,LQQ) VALUES(Login_lno.nextval,?,?,?,?,?)";
            Date time1 = new java.util.Date();           
            java.sql.Date time = new java.sql.Date(time1.getTime());    
            
            pre = con.prepareCall(sql);	
            pre.clearParameters();           
            pre.setString(1, IP);
            pre.setInt(2, PORT);
            pre.setDate(3, time);
            pre.setInt(4, 1);
            pre.setInt(5,qq);
			//ִ�в�ѯ�������ȡ���صĽ����
			pre.execute();
			
            
        } catch (Exception ex) {
            ex.printStackTrace();
        }finally{
        	DbClose.close(rs, pre, con);
        }
    }
    
    public void getPwd(){
    	 //�������ݿ����Ӷ���
        con = ConnectionFactory.getConnnection();
        try {
           
            String sql = "SELECT * FROM GETPWD WHERE ANSWER1 = ?"
            		+"AND ANSWER2 = ? AND ANSWER3 = ? AND QQ = ?";
            
            pre = con.prepareStatement(sql);	
            pre.setString(1, in.readUTF());
            pre.setString(2, in.readUTF());
            pre.setString(3, in.readUTF());
            int qq = in.readInt();
            pre.setInt(4, qq);
            
            
			//ִ�в�ѯ�������ȡ���صĽ����
			rs = pre.executeQuery();
		
		    boolean isExist = false;
		    while(rs.next()){
		    	isExist = true;
		    }
		    
			if (isExist) {
				out.writeUTF("correct");
				String sql1 = "SELECT PWD FROM USERINF WHERE QQ = " + qq;

				pre = con.prepareStatement(sql1);
				// ִ�в�ѯ�������ȡ���صĽ����
				rs = pre.executeQuery();
				if (rs.next()) {
					out.writeUTF(rs.getString(1));
				}
			}else{
				out.writeUTF("error");
			}
			
        } catch (Exception ex) {
            try {
              
                in.close();
                out.close();
                socket.close();
                
            } catch (IOException ex1) {
                ex1.printStackTrace();
            }
            ex.printStackTrace();
        }finally{
        	DbClose.close(rs, pre, con);
        }
    }
    public int getServerNo(){
    	return serverNo;
    }
    private void noticeAll(){
    	int n = ManageClientThread.clientNum;
    	for(int i = 0; i <= n; i++){
    		Server server = ManageClientThread.getClientThread(i+"");
			if (this.serverNo != server.getServerNo()) {
				server.noticeOnline(qqnum,IP,PORT);
			}
    	}
    }
    public void noticeOnline(int qq,String ip,int port){
    	
    	try {
			out.writeUTF("notice");
			out.writeInt(qq);
			out.writeUTF(ip);
			out.writeInt(port);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    }
    
}
