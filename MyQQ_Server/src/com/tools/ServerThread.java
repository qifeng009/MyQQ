package com.tools;

import java.io.*;
import java.net.*;
import java.util.*;
import javax.swing.*;

import java.text.SimpleDateFormat;

/**
 * 这个类启动ServerSocket并等待客户端的连接，
 * 它主要完成以下功能：
 * 1、从文件dbProperties.txt中读取预先设定好的端口号以创建ServerSocet
 * 2、在服务器控制面板中显示用户的请求信息
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

    //读取端口号
    private int getPort() {
        int port = 9999;
      /*  Properties p = new Properties();
        String file_separator = System.getProperty("file.separator");
        try {
            FileInputStream in = new FileInputStream("property" +
                    file_separator +
                    "dbProperties.txt");
            p.load(in); //从输入流中读取属性列表
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
            area.append("正在等待客户的请求......" + line_separator);
            area.append(line_separator);
            while (flag) {
                System.out.println("服务器"+ flag);
                Socket socket = s.accept();

                area.append("************************" + line_separator);
                area.append("Connection accept:" + socket + line_separator);
                //System.out.println("Connection accept:" + socket);
                Date time = new java.util.Date();
              
                SimpleDateFormat format = new SimpleDateFormat(
                        "yyyy-MM-dd kk:mm:ss");
                String timeInfo = format.format(time);
                area.append("处理时间：" + timeInfo + line_separator);
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
