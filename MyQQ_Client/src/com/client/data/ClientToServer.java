/**
 * 
 */
package com.client.data;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Date;
import java.util.Hashtable;
import java.util.Properties;

import javax.swing.JOptionPane;

import com.client.view.J_MainFrm;
import com.common.FriendsInfoBean;
import com.common.Port;
import com.common.ReceivePort;
import com.common.User;
import com.common.UserInfoBean;
import com.tools.ClientToServerThread;
import com.tools.ManageClientToServerThread;

/**
 * @author lenovo
 *
 */
public class ClientToServer implements Runnable{

	//Socket对象
	private Socket s;
	private DataInputStream in = null; //定义输入流
	private DataOutputStream out = null; //定义输出流
	
    private String IP = "";
    private int PORT = 0;
    private String QQNum;
    private boolean flag = false;
    private boolean isSuccessLogin = false;
    
    private UserInfoBean friendInfo;
    private UserInfoBean userInfo;
    private String loginInfo;
    private boolean isRegister;
    J_MainFrm mainFrm;
    
    public ClientToServer(boolean isRegister){
    	getPropertiesInfo();
    	this.isRegister = isRegister;
    }
    public ClientToServer(J_MainFrm mainFrm){
    	this.mainFrm = mainFrm;
    	getPropertiesInfo();
    }
    public ClientToServer(){
    	getPropertiesInfo();
    }
    
    public ClientToServer(String IP,int port){
    	this.IP = IP;
    	PORT = port;
    	
    }
    /**
	 * 该方法用来获得服务器属性文件中的IP、PORT
	 */
	private void getPropertiesInfo(){
		Properties prop = new Properties();
		InputStream inStream = Thread.currentThread().getContextClassLoader()
				.getResourceAsStream("ServerInfo.properties");
		try{
			//获得相应的键值对
			prop.load(inStream);
		}catch(IOException e){
			e.printStackTrace();
		}
		
		//根据相应的键获得对应的值
		IP = prop.getProperty("serverip");
		PORT = Integer.parseInt(prop.getProperty("servertcp.port"));
		
		      
	}
	// 发送第一次请求
	public boolean sendLoginInfoToServer(User u) throws IOException {
		
		
		try {
		//	System.out.println("kk");
			s = new Socket(IP,PORT);
			out = new DataOutputStream(s.getOutputStream());
			QQNum = u.getUserId();
			
			out.writeUTF("login");
			out.writeUTF(u.getUserId());
			out.writeUTF(u.getPasswd());
			
			ReceivePort receiveport = new ReceivePort();
			Port por = new Port();
			por.commonPort = receiveport.getReceivePort();
		//	JOptionPane.showMessageDialog(null, "Client"+IP+",客户端分配的端口："+receiveport.getReceivePort());
			int p = receiveport.getReceivePort();
			out.writeUTF(""+p);
			
			InetAddress localAddr = null;
			try {
				localAddr = InetAddress.getLocalHost();
			} catch (UnknownHostException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			String localIP = localAddr.getHostAddress();
			IP = localIP;
			String ClientIP = IP;
			//String ClientIP = "127.0.0.1";
		    out.writeUTF(ClientIP);
		    out.writeUTF(QQNum+"");
			//out.writeUTF("9998");
			
			in = new DataInputStream(s.getInputStream());
			loginInfo = in.readUTF();
			System.out.println(loginInfo);
			
			// 这里就是验证用户登录的地方
			if (loginInfo.equals("loginSuccess")) {

				
				//QQNum = Integer.parseInt(qq);
				
				userInfo = new UserInfoBean();
				
				userInfo.setQq(Integer.parseInt(in.readUTF()));
				
				userInfo.setSign(in.readUTF());
				userInfo.setPhotoID(in.readInt());
				userInfo.setNickName(in.readUTF());
				userInfo.setSex(in.readUTF());
				userInfo.setBirthday(in.readUTF());
				userInfo.setConstellation(in.readUTF());
				userInfo.setBloodType(in.readUTF());
				userInfo.setDiploma(in.readUTF());
				userInfo.setTelephone(in.readUTF());
				userInfo.setEmail(in.readUTF());
				userInfo.setAddress(in.readUTF());
							
				
				Hashtable friendInfoTable = new Hashtable();
				int n = in.readInt();
				getFriendInfo(friendInfoTable,n);
				
			//	JMainFrm mainFrm = new JMainFrm(userInfo,friendInfoTable,n);
			//	mainFrm.setVisible(true);
				
				mainFrm = new J_MainFrm(this,userInfo,friendInfoTable,n);
				//mainFrm.setVisible(tr)
			
				isSuccessLogin = true;
				flag = true;
				
				ClientToServerThread ctst = new ClientToServerThread(true);
				ctst.start();
				Port.comm.put(1, ctst);
			} else {
				
				in.close();
	            out.close();
				//关闭Scoket
				s.close();
			}

		} catch (Exception e) {
			
			//in.close();
            out.close();
			//关闭Scoket
			s.close();
			e.printStackTrace();
			// TODO: handle exception
		} finally {
			
		}
		return isSuccessLogin;
	}
	private String getLocalIP(){
		InetAddress localAddr = null;
		try {
			localAddr = InetAddress.getLocalHost();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String localIP = localAddr.getHostAddress();
		
		return localIP;
		//JOptionPane.showMessageDialog(null, localIP);
	}
	
	public String getLoginInfo(){
		return loginInfo;
	}
	public void logout() {
		try {
			
			out.writeUTF("logout");
	
			String msg = in.readUTF();
			if(msg.equals("logout")){
				in.close();
				out.close();
				// 关闭Scoket
				s.close();
				
				flag = false;
			}
			

		} catch (Exception e) {
			try {
				in.close();
				out.close();
				// 关闭Scoket
				s.close();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			e.printStackTrace();
			// TODO: handle exception
		} finally {

		}
	}

	public void closeConnect(){
        try {
			
			
        	out.writeUTF("end");
        	
				in.close();
				out.close();
				// 关闭Scoket
				s.close();
				
				flag = false;
			
			

		} catch (Exception e) {
			
			
			e.printStackTrace();
			// TODO: handle exception
		} finally {

		}
	}
	
	public void closeConnect1(){
        try {
			
			
        	out.writeUTF("end");
        	
				out.close();
				// 关闭Scoket
				s.close();
				
				flag = false;
			
			

		} catch (Exception e) {
			
			
			e.printStackTrace();
			// TODO: handle exception
		} finally {

		}
	}
	@Override
	public void run() {
		// TODO Auto-generated method stub
		try {
			while (true) {
				if(!isRegister){
		//		Thread.sleep(5000);				
		//		noticeUpdate();
				}
			}
        }catch (Exception e) {
            e.printStackTrace();
            try {
				s.close();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
        }
	}
	
	private void getFriendInfo(Hashtable friendInfoTable,int n){
		try {
			for (int i = 0; i < n; i++) {

				friendInfo = new UserInfoBean();
				friendInfo.setQq(Integer.parseInt(in.readUTF()));

				friendInfo.setSign(in.readUTF());
				friendInfo.setPhotoID(in.readInt());
				friendInfo.setNickName(in.readUTF());
				friendInfo.setSex(in.readUTF());
				friendInfo.setBirthday(in.readUTF());
				friendInfo.setConstellation(in.readUTF());
				friendInfo.setBloodType(in.readUTF());
				friendInfo.setDiploma(in.readUTF());
				friendInfo.setTelephone(in.readUTF());
				friendInfo.setEmail(in.readUTF());
				friendInfo.setAddress(in.readUTF());
				friendInfo.setSubGroupName(in.readUTF());

				String isLogin = in.readUTF();
				if (isLogin.equals("onLine")) {
					friendInfo.setIP(in.readUTF());
					friendInfo.setPort(in.readInt());
					friendInfo.setStatus(true);
				}
				friendInfoTable.put(i, friendInfo);
				String str = friendInfo.getQq() + " "
						+ friendInfo.getNickName() + " "
						+ friendInfo.getSubGroupName() + "在线否："
						+ friendInfo.getStatus();
				
				//JOptionPane.showMessageDialog(null, str);
			}
		} catch (Exception e) {
			try {
				in.close();
				out.close();
				// 关闭Scoket
				s.close();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		}
	}
	public int toRegister(UserInfoBean user,int no[],Hashtable<Integer,String> hash){
		int qq = 0;
		try {
			s = new Socket(IP,PORT);
			out = new DataOutputStream(s.getOutputStream());
			
			out.writeUTF("registerNewUser");
			out.writeUTF(user.getPwd());
			out.writeUTF(user.getSign());
			out.writeInt(user.getPhotoID());
			out.writeUTF(user.getNickName());
			out.writeUTF(user.getSex());
			out.writeUTF(user.getBirthday());
			out.writeUTF(user.getConstellation());
			out.writeUTF(user.getBloodType());
			out.writeUTF(user.getDiploma());
			out.writeUTF(user.getTelephone());
			out.writeUTF(user.getEmail());
			out.writeUTF(user.getAddress());

			out.writeInt(no[0]);
			out.writeUTF(hash.get(no[0]));
			out.writeInt(no[1]);
			out.writeUTF(hash.get(no[1]));
			out.writeInt(no[2]);
			out.writeUTF(hash.get(no[2]));
			
			in = new DataInputStream(s.getInputStream());
			qq  = in.readInt();
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		return qq;
	}
	public String getPwd(int qq,int no[],Hashtable<Integer,String> hash){
		
		String pwd  = "";
		try {
			s = new Socket(IP,PORT);
			out = new DataOutputStream(s.getOutputStream());
			
			out.writeUTF("getPwd");
			out.writeUTF(""+qq);
			
			in = new DataInputStream(s.getInputStream());
			no[0] = in.readInt();
			hash.put(no[0], in.readUTF());
			no[1] = in.readInt();
			hash.put(no[1], in.readUTF());
			no[2] = in.readInt();
			hash.put(no[2], in.readUTF());
			
			pwd = in.readUTF();
			
		}catch (Exception e1) {
			e1.printStackTrace();
		}
		return pwd;
	}
	
	public UserInfoBean getUserInfo(int qq,String nickname){
		UserInfoBean user = null;
		String pwd  = "";
		try {
			s = new Socket(IP,PORT);
			out = new DataOutputStream(s.getOutputStream());
			
			out.writeUTF("queryUser1");
			out.writeUTF(""+qq);
			out.writeUTF(nickname);
						
			
			in = new DataInputStream(s.getInputStream());
			
			String info = in.readUTF();
			
			if(info.equals("ExistUser"))
			{
				user = new UserInfoBean();
				user.setQq(Integer.parseInt(in.readUTF()));
				user.setSign(in.readUTF());
				user.setPhotoID(in.readInt());
				user.setNickName(in.readUTF());
				
			}
		}catch (Exception e1) {
			try {
				in.close();
				out.close();
				// 关闭Scoket
				s.close();
			} catch (Exception e11) {
				e11.printStackTrace();
			}
				e1.printStackTrace();
			}
		return user;
	}
	
	public void addFriend(int qq,int fqq,int subno){
		try {
			s = new Socket(IP,PORT);
			out = new DataOutputStream(s.getOutputStream());
			
			out.writeUTF("addFriend");
			out.writeInt(fqq);
			out.writeInt(subno);
			out.writeInt(qq);
			
		}catch (Exception e1) {
			try {
				//in.close();
				out.close();
				// 关闭Scoket
				s.close();
			} catch (Exception e11) {
				e11.printStackTrace();
			}
				e1.printStackTrace();
			}
	}
	
	public void deleteFriend(int qq, int fqq){
		try {
			s = new Socket(IP,PORT);
			out = new DataOutputStream(s.getOutputStream());
			
			out.writeUTF("deleteFriend");
			out.writeInt(fqq);
			out.writeInt(qq);
			
		}catch (Exception e1) {
			try {
			//	in.close();
				out.close();
				// 关闭Scoket
				s.close();
			} catch (Exception e11) {
				e11.printStackTrace();
			}
				e1.printStackTrace();
			}
	}
	private void noticeUpdate(){		
		
		try{
			if (isSuccessLogin) {
				out.writeUTF("queryFriend");

				Hashtable friendInfoTable = new Hashtable();
				int n = in.readInt();
				getFriendInfo(friendInfoTable, n);
				mainFrm.UpdateFriendList(friendInfoTable, n);
			}
			
		}catch(Exception e1){
			e1.printStackTrace();
		}				
		
	}
	
public void Update(){		
		
		try{
			if (isSuccessLogin) {
				out.writeUTF("queryFriend");

				Hashtable friendInfoTable = new Hashtable();
				int n = in.readInt();
				getFriendInfo(friendInfoTable, n);
				mainFrm.Update1(friendInfoTable, n);
			}
			
		}catch(Exception e1){
			e1.printStackTrace();
		}				
		
	}
	public void close(){
		
			try {
				in.close();
				out.close();
				// 关闭Scoket
				s.close();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		this.close();
	}
	public Socket getSocket(){
		return s;
	}
}
