package com.server.view;
/**
 *�������Ŀ��ƽ��棬��ʵ�����¹��ܣ�
 * 1���������ߵ��û�
 * 2����ʾ�û���¼��ʱ��
 * 3�����Ʒ�������������ֹͣ
 */
import java.awt.*;
import java.awt.event.*;

import java.io.*;
import java.net.*;
import java.sql.*;
import java.util.*;

import javax.swing.*;
import javax.swing.border.*;

import com.server.data.ConnectionFactory;
import com.tools.ClientToServerThread;
import com.tools.Server;
import com.tools.ServerThread;

public class ServerFrame extends JFrame implements ActionListener{
	
    JPanel contentPane;
    JPanel leftPane = new JPanel();
    JPanel rightPane = new JPanel();
    JLabel timeLabel = new JLabel();
    Border border1 = BorderFactory.createLineBorder(UIManager.getColor(
            "ProgressBar.selectionBackground"), 1);
    Border border2 = new TitledBorder(border1, "�����û��б�");
    JPanel jPanel2 = new JPanel();
    JScrollPane jScrollPane1 = new JScrollPane();
    DefaultListModel listModel = new DefaultListModel();
    JList userList = new JList(listModel);
    JLabel jLabel1 = new JLabel();
    JButton lookInfoButton = new JButton();
    JButton jButton2 = new JButton();
    JLabel jLabel2 = new JLabel();
    JLabel userNum = new JLabel();
    JLabel jLabel3 = new JLabel();
    JScrollPane jScrollPane2 = new JScrollPane();
    JTextArea serverInfo = new JTextArea();
    JPanel jPanel1 = new JPanel();
    JButton pauseButton = new JButton();
    JButton exitButton = new JButton();
    JButton getServerIP = new JButton();
    BorderLayout borderLayout1 = new BorderLayout();
    BorderLayout borderLayout2 = new BorderLayout();
    GridBagLayout gridBagLayout1 = new GridBagLayout();
    GridBagLayout gridBagLayout2 = new GridBagLayout();
    FlowLayout flowLayout1 = new FlowLayout();


    private Hashtable userTable= new Hashtable();

    private Connection con = null; //���ݿ����Ӷ���
    ServerThread serverThread = null;
    ClientToServerThread ctst = null;
    
    boolean isStartThread = false;
    /**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new ServerFrame();
	}
	
    public ServerFrame() {
        try {
            //�������ݿ�����
     //       con = ConnectionFactory.getConnnection();
      
        	ctst = new ClientToServerThread();
        	ctst.start();
        	
            setDefaultCloseOperation(EXIT_ON_CLOSE);
            jbInit();
            serverThread = new ServerThread(listModel,userNum,serverInfo);
 //           serverThread.start();

            //�����ڱ�ǩ�ж�̬��ʾʱ��
            java.util.Timer myTimer1 = new java.util.Timer();
   //         java.util.TimerTask task1 = new showTimeTask(timeLabel);
   //         myTimer1.schedule(task1, 0, 1000);

            java.util.Timer myTimer2 = new java.util.Timer();
  //          java.util.TimerTask task2 = new LoginUser(listModel,userList,userNum,userTable,con);
   //         myTimer2.schedule(task2, 0, 10000);//ÿ10��ˢ��һ��

        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    protected void processWindowEvent(WindowEvent e){
        if(e.getID() == WindowEvent.WINDOW_CLOSING){
            int option = JOptionPane.showConfirmDialog(this,"��ȷ��Ҫ�˳�ô��");
            if(option == JOptionPane.YES_OPTION){
  //              DBcon.closeConnection();//�ر����ݿ����Ӷ���
  //              Server.DBcon.closeConnection();//�رշ������̵߳����ݿ�����
            	ctst.closeSocket();

                System.exit(0);
            }
        }
    }

    private void jbInit() throws Exception {
        contentPane = (JPanel) getContentPane();
        contentPane.setLayout(gridBagLayout1);
        setBounds(400,150,650,475);
     //   setSize(new Dimension(640, 475));
        setTitle("MyQQ���������ƽ���");
        setVisible(true);
       
        leftPane.setBorder(BorderFactory.createEtchedBorder());
        leftPane.setPreferredSize(new Dimension(202, 150));
        leftPane.setLayout(borderLayout1);
        rightPane.setLayout(borderLayout2);
        timeLabel.setBorder(null);
        timeLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        timeLabel.setText("jLabel1");
        jPanel2.setLayout(gridBagLayout2);
        jLabel1.setMaximumSize(new Dimension(82, 50));
        jLabel1.setPreferredSize(new Dimension(82, 25));
        jLabel1.setHorizontalAlignment(SwingConstants.CENTER);
        jLabel1.setText("�����û��б�");
        lookInfoButton.setText("�鿴��Ϣ");
        lookInfoButton.addActionListener(this);
        jButton2.setText("�߳�");
        jButton2.addActionListener(this);
        jLabel2.setHorizontalAlignment(SwingConstants.RIGHT);        
        jLabel2.setText("��������:");
        jLabel3.setBorder(null);
        jLabel3.setMaximumSize(new Dimension(100, 50));
        jLabel3.setMinimumSize(new Dimension(100, 25));
        jLabel3.setPreferredSize(new Dimension(100, 25));
        jLabel3.setText("    ��������־��");
        serverInfo.setEditable(false);
        pauseButton.setText("��������");
        pauseButton.addActionListener(this);
        exitButton.setText("�˳�");
        exitButton.addActionListener(this);
        getServerIP.setText("������IP");
        getServerIP.addActionListener(this);
        jPanel2.setBorder(null);
        jPanel1.setBorder(null);
        jPanel1.setLayout(flowLayout1);
        rightPane.setBorder(BorderFactory.createEtchedBorder());
        flowLayout1.setHgap(30);
        jScrollPane1.getViewport().add(userList);
        jScrollPane2.getViewport().add(serverInfo);
        jPanel1.add(pauseButton);
        jPanel1.add(exitButton);
        jPanel1.add(getServerIP);
        leftPane.add(jLabel1, java.awt.BorderLayout.NORTH);
        leftPane.add(jScrollPane1, java.awt.BorderLayout.CENTER);
        leftPane.add(jPanel2, java.awt.BorderLayout.SOUTH);
        rightPane.add(jLabel3, java.awt.BorderLayout.NORTH);
        rightPane.add(jScrollPane2, java.awt.BorderLayout.CENTER);
        rightPane.add(jPanel1, java.awt.BorderLayout.SOUTH);
        jPanel2.add(jLabel2, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0
                , GridBagConstraints.WEST, GridBagConstraints.NONE,
                new Insets(9, 12, 0, 0), 20, 12));
        jPanel2.add(userNum, new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0
                , GridBagConstraints.WEST, GridBagConstraints.NONE,
                new Insets(10, 23, 0, 22), 19, 9));
        jPanel2.add(lookInfoButton, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0
                , GridBagConstraints.CENTER, GridBagConstraints.NONE,
                new Insets(13, 9, 0, 0), 0, 0));
        jPanel2.add(jButton2, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0
                , GridBagConstraints.CENTER, GridBagConstraints.NONE,
                new Insets(13, 18, 0, 7), 24, 0));
        contentPane.add(rightPane, new GridBagConstraints(1, 0, 1, 1, 0.7, 0.9
                , GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 0, 0), 300, 334));
        contentPane.add(leftPane, new GridBagConstraints(0, 0, 1, 1, 0.3, 0.9
                , GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 0, 0), 10, 186));
        contentPane.add(timeLabel, new GridBagConstraints(0, 1, 2, 1, 1.0, 0.1
                , GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
                new Insets(0, 0, 0, 0), 570, 9));
    }

    public void removeUser(int QQNUM){
        String sql = "UPDATE USER_INFO SET STATUS = 0 WHERE QQNUM = "+ QQNUM;
        try {
            Statement stmt = con.createStatement();
            stmt.executeUpdate(sql);
            stmt.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public void lookInfoButton_actionPerformed(ActionEvent e) {
        String selectedUser = null;
        Integer QQNUM = null;
        selectedUser = (String)userList.getSelectedValue();
        if(selectedUser == null){
            JOptionPane.showMessageDialog(this,"�뵥�����ѡ��һ���û���");
        }else{
            System.out.println(selectedUser);
            QQNUM = new Integer(selectedUser.substring(selectedUser.indexOf("[") + 1,
                selectedUser.indexOf("]")));
            //���ݺ��ѵ�QQ�Ų��Һ��ѵ���Ϣ��
 //           UserInfoBean user = (UserInfoBean)userTable.get(QQNUM);
//            UserInfo userInfo = new UserInfo(this,"�û��Ļ�����Ϣ",true,user);
 //           SetCenter.setDialogCenter(this,userInfo);
 //           userInfo.setVisible(true);
        }
    }

    public void jButton2_actionPerformed(ActionEvent e) {
        int index = userList.getSelectedIndex();
        Integer QQNUM = null;
        if(index == -1){
            JOptionPane.showMessageDialog(this,"�뵥�����ѡ��һ���û���");
        }else{
            String userInfo = (String)listModel.getElementAt(index);
            QQNUM = new Integer(userInfo.substring(userInfo.indexOf("[") + 1,
                userInfo.indexOf("]")));
            removeUser(QQNUM);
            listModel.remove(index);
            int num = Integer.parseInt(userNum.getText())-1;
            userNum.setText(new Integer(num).toString());
        }
    }

    public void pauseButton_actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        if(command.equals("��������")){
        	
        	isStartThread = true;
        	 serverThread.start();
             pauseButton.setText("��ͣ����");
        }else if(command.equals("��ͣ����")){
            serverThread.pauseThread();
            pauseButton.setText("�ָ�����");
        }else if(command.equals("�ָ�����")){
            serverThread.reStartThread();
            pauseButton.setText("��ͣ����");
        }
    }

    public void exitButton_actionPerformed(ActionEvent e) {
        int option = JOptionPane.showConfirmDialog(this,"��ȷ��Ҫ�˳�ô��");
        if(option == JOptionPane.YES_OPTION){
        	ctst.closeSocket();

  //          DBcon.closeConnection();//�ر����ݿ����Ӷ���
            System.exit(0);
            }
    }
    
    public void getServerIPButton_actionPerformed(ActionEvent e) {
    	InetAddress localAddr = null;
		try {
			localAddr = InetAddress.getLocalHost();
		} catch (UnknownHostException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		String localIP = localAddr.getHostAddress();

		JOptionPane.showMessageDialog(null, localIP);
    }
    public void actionPerformed(ActionEvent e){
       if (e.getSource() == pauseButton)
          pauseButton_actionPerformed(e);
       else if (e.getSource() == exitButton)
          exitButton_actionPerformed(e);
       else if (e.getSource() == jButton2)
          jButton2_actionPerformed(e);
       else if (e.getSource() == lookInfoButton)
          lookInfoButton_actionPerformed(e);
       else if (e.getSource() == getServerIP)
    	   getServerIPButton_actionPerformed(e);
    }
}