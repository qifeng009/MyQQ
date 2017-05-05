/**
 * 
 */
package com.server.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.net.InetAddress;
import java.net.UnknownHostException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import com.common.User;
import com.server.business.uiManager.Login;
/**
 * @author lenovo
 *
 */
public class JLoginFrm extends JFrame implements ActionListener,ItemListener{


	/**
	 * 
	 */
	private static final long serialVersionUID = 12L;

	private final JPanel contentPanel = new JPanel();

	JButton btn_Login;
	JButton btn_Cancel;
	JCheckBox ckb_remember;
	
	JTextField name;
	JPasswordField password;
	////////////////////////
	Login login = null;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			JLoginFrm dialog = new JLoginFrm();			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public JLoginFrm() {
		/*
		InetAddress localAddr = null;
		try {
			localAddr = InetAddress.getLocalHost();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String localIP = localAddr.getHostAddress();
		JOptionPane.showMessageDialog(null, localIP);
		*/
		
		setTitle("登录窗口");	
		setResizable(false);
		setBounds(500, 250, 400, 320);
		{//用户名标签
			JLabel l_name;
			String str_gm = "用户名:";
			l_name = new JLabel(str_gm);
			l_name.setFont(new java.awt.Font("楷体",1,25));
			l_name.setBounds(80, 30,150,50);
			contentPanel.add(l_name);
		}
		{//密码标签
			JLabel l_password;
			String str_gm = "密  码:";
			l_password = new JLabel(str_gm);
			l_password.setFont(new java.awt.Font("楷体",1,25));
			l_password.setBounds(80, 80,150,50);
			contentPanel.add(l_password);
		}
		{//用户名输入框			
			name = new JTextField();
			name.setBounds(200,40,150,30);
			contentPanel.add(name);
		}
		{//密码输入框			
			password = new JPasswordField(6);
			password.setBounds(200,90,150,30);	
			contentPanel.add(password);
		}
		{//记住密码
			ckb_remember = new JCheckBox("记住密码");
			login = new Login();
			String []rem = new String[2];
			if(login.isRemember()){
				login.ReadUserInfo(rem);
				name.setText(rem[0]);
				password.setText(rem[1]);
				ckb_remember.setSelected(true);
			}
			
			
			ckb_remember.setBounds(80,135,120,30);
			ckb_remember.setFont(new java.awt.Font("楷体",1,18));
			contentPanel.add(ckb_remember);
			ckb_remember.addActionListener(this);
		}
		{//登录按钮
			btn_Login = new JButton("登录");
			btn_Login.setFont(new java.awt.Font("楷体",1,18));
			btn_Login.setBounds(80, 180,90,50);
			contentPanel.add(btn_Login);
			btn_Login.addActionListener(this); //添加事件处理
		}
		
		{//取消按钮
		    btn_Cancel = new JButton("取消");
		    btn_Cancel.setFont(new java.awt.Font("楷体",1,18));
		    btn_Cancel.setBounds(260,180,90,50);
			contentPanel.add(btn_Cancel);	
			btn_Cancel.addActionListener(this);//添加事件处理
		}
		contentPanel.setLayout(new BorderLayout());
		//添加设置的背景面板到当前面板
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setVisible(true);
	}

	// 按钮响应事件
	public void actionPerformed(ActionEvent e) {
		Object source = e.getSource();

		String userName = name.getText().trim();
		String pwd = new String(password.getPassword());
		login = new Login();
		
		if (source == btn_Login) { // 登录
	      
			try {
				if (userName.equals("")) { // QQ号是否为空
					JOptionPane.showMessageDialog(null, "请输入用户名!");
				} else if (pwd.equals("")) {// 密码是否为空
					JOptionPane.showMessageDialog(null, "请输入密码!");
				} else if (login.checkUser(userName,pwd)) { // 核对用户是否合法
					if(ckb_remember.isSelected()){
						login.remember(userName,pwd);
					}else{
						login.remember("","");
					}
					
					ServerFrame serverFrame = new ServerFrame();
					this.dispose();
				} else {
					
					JOptionPane.showMessageDialog(null, "您输入的用户名或密码错误!");
					
				}
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		} else if (source == btn_Cancel) { // 退出主窗口，并释放资源
			System.exit(0);
			this.dispose();
		} 
	}
	
	public void itemStateChanged(ItemEvent e){
		Object source = e.getSource();
		
		
		if (source == ckb_remember){
			if(ckb_remember.isSelected()){
				ckb_remember.setSelected(false);
				
			}else{
				ckb_remember.setSelected(true);
				
			}
			
		}
	}
	

}
