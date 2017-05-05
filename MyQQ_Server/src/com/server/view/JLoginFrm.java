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
		
		setTitle("��¼����");	
		setResizable(false);
		setBounds(500, 250, 400, 320);
		{//�û�����ǩ
			JLabel l_name;
			String str_gm = "�û���:";
			l_name = new JLabel(str_gm);
			l_name.setFont(new java.awt.Font("����",1,25));
			l_name.setBounds(80, 30,150,50);
			contentPanel.add(l_name);
		}
		{//�����ǩ
			JLabel l_password;
			String str_gm = "��  ��:";
			l_password = new JLabel(str_gm);
			l_password.setFont(new java.awt.Font("����",1,25));
			l_password.setBounds(80, 80,150,50);
			contentPanel.add(l_password);
		}
		{//�û��������			
			name = new JTextField();
			name.setBounds(200,40,150,30);
			contentPanel.add(name);
		}
		{//���������			
			password = new JPasswordField(6);
			password.setBounds(200,90,150,30);	
			contentPanel.add(password);
		}
		{//��ס����
			ckb_remember = new JCheckBox("��ס����");
			login = new Login();
			String []rem = new String[2];
			if(login.isRemember()){
				login.ReadUserInfo(rem);
				name.setText(rem[0]);
				password.setText(rem[1]);
				ckb_remember.setSelected(true);
			}
			
			
			ckb_remember.setBounds(80,135,120,30);
			ckb_remember.setFont(new java.awt.Font("����",1,18));
			contentPanel.add(ckb_remember);
			ckb_remember.addActionListener(this);
		}
		{//��¼��ť
			btn_Login = new JButton("��¼");
			btn_Login.setFont(new java.awt.Font("����",1,18));
			btn_Login.setBounds(80, 180,90,50);
			contentPanel.add(btn_Login);
			btn_Login.addActionListener(this); //����¼�����
		}
		
		{//ȡ����ť
		    btn_Cancel = new JButton("ȡ��");
		    btn_Cancel.setFont(new java.awt.Font("����",1,18));
		    btn_Cancel.setBounds(260,180,90,50);
			contentPanel.add(btn_Cancel);	
			btn_Cancel.addActionListener(this);//����¼�����
		}
		contentPanel.setLayout(new BorderLayout());
		//������õı�����嵽��ǰ���
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setVisible(true);
	}

	// ��ť��Ӧ�¼�
	public void actionPerformed(ActionEvent e) {
		Object source = e.getSource();

		String userName = name.getText().trim();
		String pwd = new String(password.getPassword());
		login = new Login();
		
		if (source == btn_Login) { // ��¼
	      
			try {
				if (userName.equals("")) { // QQ���Ƿ�Ϊ��
					JOptionPane.showMessageDialog(null, "�������û���!");
				} else if (pwd.equals("")) {// �����Ƿ�Ϊ��
					JOptionPane.showMessageDialog(null, "����������!");
				} else if (login.checkUser(userName,pwd)) { // �˶��û��Ƿ�Ϸ�
					if(ckb_remember.isSelected()){
						login.remember(userName,pwd);
					}else{
						login.remember("","");
					}
					
					ServerFrame serverFrame = new ServerFrame();
					this.dispose();
				} else {
					
					JOptionPane.showMessageDialog(null, "��������û������������!");
					
				}
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		} else if (source == btn_Cancel) { // �˳������ڣ����ͷ���Դ
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
