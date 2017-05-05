package com.tools;

import java.io.*;
import java.net.*;
import java.util.*;
import javax.swing.*;

import java.text.SimpleDateFormat;

/**
 * ���������ServerSocket���ȴ��ͻ��˵����ӣ�
 * ����Ҫ������¹��ܣ�
 * 1�����ļ�dbProperties.txt�ж�ȡԤ���趨�õĶ˿ں��Դ���ServerSocet
 * 2���ڷ����������������ʾ�û���������Ϣ
 */

public class ServerThread extends Thread {

    JTextArea area = new JTextArea();
    DefaultListModel listModel = new DefaultListModel();
    JLabel jLabel2 = new JLabel();
    Boolean flag = true;
    String line_separator = System.getProperty("line.separator");

    public ServerThread(){}
    
    public ServerThread(DefaultListModel listModel,JLabel jLabel2,JTextArea area) {
    	this.listModel = listModel;
    	this.jLabel2 = jLabel2;
        this.area = area;
    }

    //��ȡ�˿ں�
    private int getPort() {
        int port = 9999;
      /*  Properties p = new Properties();
        String file_separator = System.getProperty("file.separator");
        try {
            FileInputStream in = new FileInputStream("property" +
                    file_separator +
                    "dbProperties.txt");
            p.load(in); //���������ж�ȡ�����б�
            port = Integer.parseInt(p.getProperty("tcp.ip.port"));
            in.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        */
        return port;
    }

    public void pauseThread(){
        this.flag = false;
    }

    public void reStartThread(){
        this.flag = true;
    }

    public void run() {
        try {
            ServerSocket s = new ServerSocket(getPort());
            area.append("���ڵȴ��ͻ�������......" + line_separator);
            area.append(line_separator);
            while (flag) {
                System.out.println("������"+ flag);
                Socket socket = s.accept();

                area.append("************************" + line_separator);
                area.append("Connection accept:" + socket + line_separator);
                //System.out.println("Connection accept:" + socket);
                Date time = new java.util.Date();
              
                SimpleDateFormat format = new SimpleDateFormat(
                        "yyyy-MM-dd kk:mm:ss");
                String timeInfo = format.format(time);
                area.append("����ʱ�䣺" + timeInfo + line_separator);
                area.append("************************" + line_separator);
                area.append(line_separator);
                area.append(line_separator);

                int serverNo = ManageClientThread.clientNum;
                Server server = new Server(socket,listModel,jLabel2,serverNo);
                ManageClientThread.addClientThread(server);
                
                new Thread(server).start();
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
