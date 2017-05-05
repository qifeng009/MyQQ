/**
 * 
 */
package com.tools;

import java.awt.Color;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Properties;

import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.text.SimpleAttributeSet;

import com.common.LinkPort;
import com.common.Message;
import com.common.Port;
import com.common.TwoPortMsg;
import com.common.User;
import com.common.UserInfoBean;
import com.server.business.uiManager.MyTextPane;
import com.server.data.Dml;
import com.server.data.Query;

/**
 * @author lenovo
 *
 */
public class ClientToServerThread extends Thread implements Serializable{

	private String sendIP = "127.0.0.1";	
	private int sendPORT = 6666;
	private int receivePORT = 8888;
	//声明发送信息的数据报套结字
    private DatagramSocket sendSocket = null;
    //声明发送信息的数据包
    private DatagramPacket sendPacket = null;
    //声明接受信息的数据报套结字
    private DatagramSocket receiveSocket = null;
    //声明接受信息的数据报
    private DatagramPacket receivePacket = null;
    //收发数据的端口
    private int myPort = 0;
    //接收数据主机的IP地址
    private String friendIP = null;
    private int friendPort = 0;
    
    private boolean isClose = false;
    private boolean isStopThread = false;
    //缓冲数组的大小
    public static final int BUFFER_SIZE = 81920;

    private byte inBuf[] = null; //接收数据的缓冲数组
    private byte outBuf[] = null; //发送数据的缓冲数组
    
    MyTextPane  tp_message;
    
    private boolean isStop = false;
	// 构造函数
	public ClientToServerThread() {		
		getPropertiesInfo();
		
	}

	public void run() {
		String receiveInfo = "";
	

		try{
			inBuf = new byte[BUFFER_SIZE];
			receivePacket = new DatagramPacket(inBuf,inBuf.length);
			receiveSocket = new DatagramSocket(receivePORT);
		}catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		}
		
		while (true) {
			if(receiveSocket == null){
				break;
			} else {				
				try {
					receiveSocket.receive(receivePacket);
					
					if(isStop){
						receiveSocket.close();
						return;
					}
					/*
					String message = new String(receivePacket.getData(),0,receivePacket.getLength());
					int sendIP_index = message.indexOf("*");
					int receiveIP_index = message.indexOf("/");
					int sendPort_index = message.indexOf("#");
					
					sendIP = message.substring(0,sendIP_index);
					String receiveIP = message.substring(sendIP_index+1,receiveIP_index);
					sendPORT = Integer.parseInt(message.substring(receiveIP_index+1,sendPort_index));
					String info = message.substring(sendPort_index+1);
					byte buffer[] = info.getBytes();
					
					*/
	                receiveSocket.receive(receivePacket);
					
					Message message = (Message)ByteToObject(receivePacket.getData());
					
					int mixqq = message.getReceiveQq()+message.getSendQq();
					
					//LinkPort.hash.put(mixqq,two);
					
					if (message.getInfoType().equals("Record")) {
				//		sendIP = message.getReceiveIP();						
				//		sendPORT = Port.port2.get(message.getSendQq());
						
						
			            sendIP = Port.port3.get(message.getSendQq());	
			      //      JOptionPane.showMessageDialog(null, "p1:"+sendIP);
					    sendPORT = message.getReceivePort();
				//	    JOptionPane.showMessageDialog(null, "p2"+sendPORT);
					  //  JOptionPane.showMessageDialog(null, "p"+sendPORT);
						//JOptionPane.showMessageDialog(null, ""+sendPORT);
						Query query = new Query();
						Hashtable record = query.getRecord(message.getSendQq(),message.getReceiveQq());
						sendData(message);
					
					//	getRecord(record);
						sendData(record);
					//	JOptionPane.showMessageDialog(null, "server");
					}else if (message.getInfoType().equals("TXT")) {
						common(mixqq);
		             
		               
						if(LinkPort.isfirst.get(mixqq)){
			                sendIP = Port.port3.get(message.getReceiveQq());
						//	sendIP = message.getSendIP();
							sendPORT = Port.port2.get(message.getReceiveQq());
							//JOptionPane.showMessageDialog(null, "shake:"+sendPORT+"");
							message.setInfoType("Notice");
							UserInfoBean user = new UserInfoBean();
							UserInfoBean friend = new UserInfoBean();
							
							user.setQq(message.getReceiveQq());
							user.setNickName(message.getReceiveNickname());
							user.setPhotoID(message.getreceivePhotoID());
							user.setSign(message.getreceiveSign());
							message.setUserBean(user);
							
							friend.setQq(message.getSendQq());
							friend.setNickName(message.getSendNickname());
							friend.setPhotoID(message.getsendPhotoID());
							friend.setSign(message.getsendSign());
							message.setFriendBean(friend);
							
							sendData(message);
														
							receiveSocket.receive(receivePacket);
							
							Message msg1 = (Message)ByteToObject(receivePacket.getData());
							
							sendPORT = msg1.getReceivePort();
							
							TwoPortMsg two = new TwoPortMsg();
							two.receiveQQ = message.getReceiveQq();
							two.receiveIP = message.getReceiveIP();
							two.receivePort = message.getReceivePort();
							
							two.sendQQ = message.getSendQq();
							sendIP = Port.port3.get(message.getReceiveQq());
							two.sendIP = sendIP;
							two.sendPort = sendPORT;
							LinkPort.hash.put(mixqq, two);
							
							}else{
								int mix = message.getSendQq()+message.getReceiveQq();
								TwoPortMsg two = new TwoPortMsg();
								two = LinkPort.hash.get(mix);
								if(message.getReceiveQq() == two.receiveQQ){
									sendIP = two.sendIP;
									sendPORT = two.sendPort;
									
								}else{
									
									sendIP = two.receiveIP;
									sendPORT = two.receivePort;
								}
							//	JOptionPane.showMessageDialog(null,sendIP);
							}
					//	sendIP = message.getSendIP();
					//	sendPORT = message.getSendPort();
						
						Dml dml = new Dml();
						dml.insert(message);
						
						
						sendData(message);
					}else if (message.getInfoType().equals("Shake")) {
					//	sendIP = Port.port3.get(message.getReceiveQq());
					//	sendPORT = Port.port2.get(message.getReceiveQq());
						common(mixqq);
					
						if(LinkPort.isfirst.get(mixqq)){
			                sendIP = Port.port3.get(message.getReceiveQq());
						//	sendIP = message.getSendIP();
							sendPORT = Port.port2.get(message.getReceiveQq());
							//JOptionPane.showMessageDialog(null, "shake:"+sendPORT+"");
							message.setInfoType("Notice");
							UserInfoBean user = new UserInfoBean();
							UserInfoBean friend = new UserInfoBean();
							
							user.setQq(message.getReceiveQq());
							user.setNickName(message.getReceiveNickname());
							user.setPhotoID(message.getreceivePhotoID());
							user.setSign(message.getreceiveSign());
							message.setUserBean(user);
							
							friend.setQq(message.getSendQq());
							friend.setNickName(message.getSendNickname());
							friend.setPhotoID(message.getsendPhotoID());
							friend.setSign(message.getsendSign());
							message.setFriendBean(friend);
							
							sendData(message);
														
							receiveSocket.receive(receivePacket);
							
							Message msg1 = (Message)ByteToObject(receivePacket.getData());
							
							sendPORT = msg1.getReceivePort();
							
							TwoPortMsg two = new TwoPortMsg();
							two.receiveQQ = message.getReceiveQq();
							two.receiveIP = message.getReceiveIP();
							two.receivePort = message.getReceivePort();
							
							two.sendQQ = message.getSendQq();
							sendIP = Port.port3.get(message.getReceiveQq());
							two.sendIP = sendIP;
							two.sendPort = sendPORT;
							LinkPort.hash.put(mixqq, two);
							
							String rqq = two.receiveQQ+"";
							String rIP = two.receiveIP;
							
							String sqq = two.sendQQ+"";
							String sIP = two.sendIP;
							
						//	JOptionPane.showMessageDialog(null,rqq+rIP);
						//	JOptionPane.showMessageDialog(null,sqq+sIP);
							}else{
								int mix = message.getSendQq()+message.getReceiveQq();
								TwoPortMsg two = new TwoPortMsg();
								two = LinkPort.hash.get(mix);
								if(message.getReceiveQq() == two.receiveQQ){
									sendIP = two.sendIP;
									sendPORT = two.sendPort;
									
								}else{
									
									sendIP = two.receiveIP;
									sendPORT = two.receivePort;
								}
								//JOptionPane.showMessageDialog(null,sendIP);
							}
						//this.sleep(50);
						sendData(message);
					} else if (message.getInfoType().equals("Icon")) {
						common(mixqq);
			             		               
						if(LinkPort.isfirst.get(mixqq)){
			                sendIP = Port.port3.get(message.getReceiveQq());
						//	sendIP = message.getSendIP();
							sendPORT = Port.port2.get(message.getReceiveQq());
							//JOptionPane.showMessageDialog(null, "shake:"+sendPORT+"");
							message.setInfoType("Notice");
							UserInfoBean user = new UserInfoBean();
							UserInfoBean friend = new UserInfoBean();
							
							user.setQq(message.getReceiveQq());
							user.setNickName(message.getReceiveNickname());
							user.setPhotoID(message.getreceivePhotoID());
							user.setSign(message.getreceiveSign());
							message.setUserBean(user);
							
							friend.setQq(message.getSendQq());
							friend.setNickName(message.getSendNickname());
							friend.setPhotoID(message.getsendPhotoID());
							friend.setSign(message.getsendSign());
							message.setFriendBean(friend);
							
							sendData(message);
														
							receiveSocket.receive(receivePacket);
							
							Message msg1 = (Message)ByteToObject(receivePacket.getData());
							
							sendPORT = msg1.getReceivePort();
							
							TwoPortMsg two = new TwoPortMsg();
							two.receiveQQ = message.getReceiveQq();
							two.receiveIP = message.getReceiveIP();
							two.receivePort = message.getReceivePort();
							
							two.sendQQ = message.getSendQq();
							sendIP = Port.port3.get(message.getReceiveQq());
							two.sendIP = sendIP;
							two.sendPort = sendPORT;
							LinkPort.hash.put(mixqq, two);
							
							}else{
								int mix = message.getSendQq()+message.getReceiveQq();
								TwoPortMsg two = new TwoPortMsg();
								two = LinkPort.hash.get(mix);
								if(message.getReceiveQq() == two.receiveQQ){
									sendIP = two.sendIP;
									sendPORT = two.sendPort;
									
								}else{
									
									sendIP = two.receiveIP;
									sendPORT = two.receivePort;
								}
							//	JOptionPane.showMessageDialog(null,sendIP);
							}
					
						sendData(message);
					}else if (message.getInfoType().equals("CutImage")) {
						common(mixqq);
		             
		               
						if(LinkPort.isfirst.get(mixqq)){
			                sendIP = Port.port3.get(message.getReceiveQq());
						//	sendIP = message.getSendIP();
							sendPORT = Port.port2.get(message.getReceiveQq());
							//JOptionPane.showMessageDialog(null, "shake:"+sendPORT+"");
							message.setInfoType("Notice");
							UserInfoBean user = new UserInfoBean();
							UserInfoBean friend = new UserInfoBean();
							
							user.setQq(message.getReceiveQq());
							user.setNickName(message.getReceiveNickname());
							user.setPhotoID(message.getreceivePhotoID());
							user.setSign(message.getreceiveSign());
							message.setUserBean(user);
							
							friend.setQq(message.getSendQq());
							friend.setNickName(message.getSendNickname());
							friend.setPhotoID(message.getsendPhotoID());
							friend.setSign(message.getsendSign());
							message.setFriendBean(friend);
							
							sendData(message);
														
							receiveSocket.receive(receivePacket);
							
							Message msg1 = (Message)ByteToObject(receivePacket.getData());
							
							sendPORT = msg1.getReceivePort();
							
							TwoPortMsg two = new TwoPortMsg();
							two.receiveQQ = message.getReceiveQq();
							two.receiveIP = message.getReceiveIP();
							two.receivePort = message.getReceivePort();
							
							sendIP = Port.port3.get(message.getReceiveQq());
							two.sendQQ = message.getSendQq();
							two.sendIP = sendIP;
							two.sendPort = sendPORT;
							LinkPort.hash.put(mixqq, two);
							
							}else{
								int mix = message.getSendQq()+message.getReceiveQq();
								TwoPortMsg two = new TwoPortMsg();
								two = LinkPort.hash.get(mix);
								if(message.getReceiveQq() == two.receiveQQ){
									sendIP = two.sendIP;
									sendPORT = two.sendPort;
									
								}else{
									
									sendIP = two.receiveIP;
									sendPORT = two.receivePort;
								}
							//	JOptionPane.showMessageDialog(null,sendIP);
							}
				
						
						sendData(message);
					}else if (message.getInfoType().equals("Voice")) {
						common(mixqq);
		             
		               
						if(LinkPort.isfirst.get(mixqq)){
			                sendIP = Port.port3.get(message.getReceiveQq());
						//	sendIP = message.getSendIP();
							sendPORT = Port.port2.get(message.getReceiveQq());
							//JOptionPane.showMessageDialog(null, "shake:"+sendPORT+"");
							message.setInfoType("Notice");
							UserInfoBean user = new UserInfoBean();
							UserInfoBean friend = new UserInfoBean();
							
							user.setQq(message.getReceiveQq());
							user.setNickName(message.getReceiveNickname());
							user.setPhotoID(message.getreceivePhotoID());
							user.setSign(message.getreceiveSign());
							message.setUserBean(user);
							
							friend.setQq(message.getSendQq());
							friend.setNickName(message.getSendNickname());
							friend.setPhotoID(message.getsendPhotoID());
							friend.setSign(message.getsendSign());
							message.setFriendBean(friend);
							
							sendData(message);
														
							receiveSocket.receive(receivePacket);
							
							Message msg1 = (Message)ByteToObject(receivePacket.getData());
							
							sendPORT = msg1.getReceivePort();
							
							TwoPortMsg two = new TwoPortMsg();
							two.receiveQQ = message.getReceiveQq();
							two.receiveIP = message.getReceiveIP();
							two.receivePort = message.getReceivePort();
							
							two.sendQQ = message.getSendQq();
							sendIP = Port.port3.get(message.getReceiveQq());
							two.sendIP = sendIP;
							two.sendPort = sendPORT;
							LinkPort.hash.put(mixqq, two);
							
							}else{
								int mix = message.getSendQq()+message.getReceiveQq();
								TwoPortMsg two = new TwoPortMsg();
								two = LinkPort.hash.get(mix);
								if(message.getReceiveQq() == two.receiveQQ){
									sendIP = two.sendIP;
									sendPORT = two.sendPort;
									
								}else{
									sendIP = two.receiveIP;
									sendPORT = two.receivePort;
									
								}
							}
				
						
						sendData(message);
						
						receiveSocket.receive(receivePacket);
						
						Message msg1 = (Message)ByteToObject(receivePacket.getData());
						
					}else if (message.getInfoType().equals("Voice1")){
						common(mixqq);
			             
			               
						if(LinkPort.isfirst.get(mixqq)){
			                sendIP = Port.port3.get(message.getReceiveQq());
						//	sendIP = message.getSendIP();
							sendPORT = Port.port2.get(message.getReceiveQq());
							//JOptionPane.showMessageDialog(null, "shake:"+sendPORT+"");
							message.setInfoType("Notice");
							UserInfoBean user = new UserInfoBean();
							UserInfoBean friend = new UserInfoBean();
							
							user.setQq(message.getReceiveQq());
							user.setNickName(message.getReceiveNickname());
							user.setPhotoID(message.getreceivePhotoID());
							user.setSign(message.getreceiveSign());
							message.setUserBean(user);
							
							friend.setQq(message.getSendQq());
							friend.setNickName(message.getSendNickname());
							friend.setPhotoID(message.getsendPhotoID());
							friend.setSign(message.getsendSign());
							message.setFriendBean(friend);
							
							sendData(message);
														
							receiveSocket.receive(receivePacket);
							
							Message msg1 = (Message)ByteToObject(receivePacket.getData());
							
							sendPORT = msg1.getReceivePort();
							
							TwoPortMsg two = new TwoPortMsg();
							two.receiveQQ = message.getReceiveQq();
							two.receiveIP = message.getReceiveIP();
							two.receivePort = message.getReceivePort();
							
							two.sendQQ = message.getSendQq();
							sendIP = Port.port3.get(message.getReceiveQq());
							two.sendIP = sendIP;
							two.sendPort = sendPORT;
							LinkPort.hash.put(mixqq, two);
							
							}else{
								int mix = message.getSendQq()+message.getReceiveQq();
								TwoPortMsg two = new TwoPortMsg();
								two = LinkPort.hash.get(mix);
								if(message.getReceiveQq() == two.receiveQQ){
									
								  sendIP = two.receiveIP;
									sendPORT = two.receivePort;
								}else{
									
									  sendIP = two.sendIP;
									sendPORT = two.sendPort;
								}
							}
				
						
						
						//JOptionPane.showMessageDialog(null,"Server:");
					
						sendData(message);
					}else if (message.getInfoType().equals("File")) {
						common(mixqq);
		             
		               
						if(LinkPort.isfirst.get(mixqq)){
			                sendIP = Port.port3.get(message.getReceiveQq());
						//	sendIP = message.getSendIP();
							sendPORT = Port.port2.get(message.getReceiveQq());
							//JOptionPane.showMessageDialog(null, "shake:"+sendPORT+"");
							message.setInfoType("Notice");
							UserInfoBean user = new UserInfoBean();
							UserInfoBean friend = new UserInfoBean();
							
							user.setQq(message.getReceiveQq());
							user.setNickName(message.getReceiveNickname());
							user.setPhotoID(message.getreceivePhotoID());
							user.setSign(message.getreceiveSign());
							message.setUserBean(user);
							
							friend.setQq(message.getSendQq());
							friend.setNickName(message.getSendNickname());
							friend.setPhotoID(message.getsendPhotoID());
							friend.setSign(message.getsendSign());
							message.setFriendBean(friend);
							
							sendData(message);
														
							receiveSocket.receive(receivePacket);
							
							Message msg1 = (Message)ByteToObject(receivePacket.getData());
							
							sendPORT = msg1.getReceivePort();
							
							TwoPortMsg two = new TwoPortMsg();
							two.receiveQQ = message.getReceiveQq();
							two.receiveIP = message.getReceiveIP();
							two.receivePort = message.getReceivePort();
							
							two.sendQQ = message.getSendQq();
							sendIP = Port.port3.get(message.getReceiveQq());
							two.sendIP = sendIP;
							two.sendPort = sendPORT;
							LinkPort.hash.put(mixqq, two);
							
							}else{
								int mix = message.getSendQq()+message.getReceiveQq();
								TwoPortMsg two = new TwoPortMsg();
								two = LinkPort.hash.get(mix);
								if(message.getReceiveQq() == two.receiveQQ){
									sendIP = two.sendIP;
									sendPORT = two.sendPort;
									
								}else{
									sendIP = two.receiveIP;
									sendPORT = two.receivePort;
									
								}
							}
				
						
						sendData(message);
						
						
					}else if (message.getInfoType().equals("CloseVoice")) {
						int mix = message.getSendQq()+message.getReceiveQq();
						TwoPortMsg two = new TwoPortMsg();
						two = LinkPort.hash.get(mix);
						if(message.getReceiveQq() == two.receiveQQ){
							sendIP = two.sendIP;
							sendPORT = two.sendPort;
							
						}else{
							sendIP = two.receiveIP;
							sendPORT = two.receivePort;
							
						}
						sendData(message);
					}
					
					
					
				//	byte buffer[] = ObjectToByte(message);
				//	sendData(buffer);
					
				//	tp_message.setdo("收到数据:"+message+"\n", new SimpleAttributeSet());
					//tp_message.setDocs("收到数据:"+message+"\n", Color.RED, "楷体", true, false, false, 18);
				} catch (Exception e) {
					e.printStackTrace();
					// TODO: handle exception
				}
			}
		}
	}
	private void common(int mixqq){		
		
		//	TwoPortMsg two = new TwoPortMsg();
			
			int i = 0;
			while(i < LinkPort.count){
				if(mixqq == LinkPort.mixstore[i]){
					break;
				}
				i++;
			}
			if(i < LinkPort.count){
				LinkPort.isfirst.put(mixqq, false);
			//	JOptionPane.showMessageDialog(null, ""+mixqq+false);
			}
			if(i == LinkPort.count){						
				
				LinkPort.mixstore[i] = mixqq;
				LinkPort.count++;
				LinkPort.isfirst.put(mixqq, true);
			
			}
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
		
		receivePORT = Integer.parseInt(prop.getProperty("serverudp.port"));
		
		
		      
	}
	public void sendData(byte buffer[]){
		try{
			InetAddress address = InetAddress.getByName(sendIP);
		//	outBuf = new byte[BUFFER_SIZE];
			sendPacket = new DatagramPacket(buffer,buffer.length,address,sendPORT);
			sendSocket = new DatagramSocket();
			sendSocket.send(sendPacket);
		}catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		}
	}
	
	public void sendData(Object obj){
		byte buffer[] = ObjectToByte(obj);
		sendData(buffer);
	}
	public void closeSocket(){
		isStop = true;					
    }
	
	
    private Object ByteToObject(byte[] bytes){
        Object obj = null;
        try {
        //bytearray to object
        ByteArrayInputStream bi = new ByteArrayInputStream(bytes);
        ObjectInputStream oi = new ObjectInputStream(bi);

        obj = oi.readObject();

        bi.close();
        oi.close();
        }
        catch(Exception e) {
            e.printStackTrace();
        }
        return obj;
    }


    public byte[] ObjectToByte(Object obj)
    {
        byte[] bytes = null;
        try {
            //object to bytearray
            ByteArrayOutputStream bo = new ByteArrayOutputStream();
            ObjectOutputStream oo = new ObjectOutputStream(bo);
            oo.writeObject(obj);

            bytes = bo.toByteArray();

            bo.close();
            oo.close();    
        }
        catch(Exception e) {
            e.printStackTrace();
        }
        return(bytes);

    }
    
    public void getRecord(Hashtable record){
		int size = record.size();
		
		Enumeration it = record.elements();
		for(int i = 0; i < size; i++){
			Message message = (Message)it.nextElement();
			String str1 = message.getSendQq()+"  "+message.getDate().toString()+"\n";
			//messageloggingpanel.tp_messagelogging.setDocs(str1,Color.BLUE,"楷体",false,false,true,14);
			//tp_message.setDocs(str1,Color.BLUE,"楷体",false,false,true,14);
			String str = message.getInfo()+"\n";
			Color col = message.getFontColor();
			String font = message.getFontType();
			boolean bold = message.getIsBold();
			boolean Italic = message.getIsItatic();
			boolean Underline = message.getIsUnderline();
			int fontSize = message.getFontSize();
			
			JOptionPane.showMessageDialog(null, str1+str);
			//messageloggingpanel.tp_messagelogging.setDocs(str, col, font, bold, Italic, Underline, fontSize);
			//tp_message.setDocs(str, col, font, bold, Italic, Underline, fontSize);
		}
	}
}
