/**
 * @Title:J_MainFrm.java
 * @Package:com.client.view
 * @Description:TODO(描述该文件做什么)
 * @author:  ShiLuoDeQin 
 * @date:2013-12-25下午01:43:10
 * @version V1.0
 */

package com.client.view;

import java.awt.AWTException;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.Toolkit;
import java.awt.TrayIcon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Enumeration;
import java.util.Hashtable;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTree;
import javax.swing.UIManager;
import javax.swing.border.LineBorder;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;

import com.client.business.mainRender.ColorConvertOp;
import com.client.business.mainRender.CombListRenderer;

import com.client.business.mainRender.IconNodeRenderer;
import com.client.business.mainRender.InformationFrm;
import com.client.business.mainRender.MyTreeUI1;
import com.client.business.mainRender.findFriendFrm;
import com.client.business.uiManager.Login;
import com.client.data.ClientToServer;
import com.common.Port;
import com.common.UserInfoBean;
import com.tools.ClientToServerThread;
import com.client.business.mainRender.IconNodeRenderer;
import com.client.business.mainRender.IconNode;

/**
 * @ClassName:J_MainFrm
 * @author Administrator
 * @Description:TODO(描述这个类的作用)
 * @date 2013-12-25 下午01:43:10
 *
 */

public class J_MainFrm extends JFrame{
	private static final long serialVersionUID = 1L;
	J_MainFrm MainFrm;  
	public IconNode Root=new IconNode(null,null);//定义根节点
	JPanel abc=new JPanel();//添加控件的面板
	int Mwide=300,Mheight=650;//窗体的长宽
	private int x,y;//窗体的位置，拖动时的位置
	int MAX_wide=500;//最大宽度
	private int offsetX, offsetY;//拖动窗体偏移位置
	Image ImageBk;	//背景图片
	JLabel Bk_label;//背景label
	boolean isHide=false;//窗体是否隐藏
	JLabel nickname=new JLabel();
	JLabel sign = new JLabel();
	public JTree tree;                       //好友列表
	public findFriendFrm FindFriendFrm=null;//查找好友面板
	public int groupNumber=0;//存放分组个数
	public String groupName[]=new String [20];//存放分组名字
	
	private JTabbedPane tabeld = null;
	/**
	 * 图片资源
	 */
	static String Background="Image/MainIcon/qq.png";
	static String Pclose="Image/MainIcon/close.png";//
	static String Pclose1="Image/MainIcon/close1.png";
	static String Pmin="Image/MainIcon/min.png";
	static String Pmin1="Image/MainIcon/min1.png";
	static String Pskin="Image/MainIcon/skin.png";
	static String Pskin1="Image/MainIcon/skin1.png";
	static String touxiang="Image/MainIcon/qqicons\\DefaultFace.png";
	static String select="Image/MainIcon/qqicons\\select.png";
	static String Ptask="Image/MainIcon/task.png";//
	
	static String touxiang1="Image/MainIcon/qqicons\\Catch00001.jpg";
	static String touxiang2="Image/MainIcon/qqicons\\Catch00002.jpg";
	static String touxiang3="Image/MainIcon/qqicons\\Catch00003.jpg";
	static String touxiang4="Image/MainIcon/qqicons\\Catch00004.jpg";
	static String touxiang5="Image/MainIcon/qqicons\\Catch00005.jpg";
	static String touxiang6="Image/MainIcon/qqicons\\Catch00006.jpg";
	static String treeNode="Image/MainIcon/treeNode.png";
	static String treeNode1="Image/MainIcon/treeNode1.png";
	static String Pmask="Image/MainIcon/mask.png";
	//////////// 状态图标
	static String Ponline="Image/MainIcon/Status\\imonline.png";
	static String Poffline="Image/MainIcon/Status\\imoffline.png";
	static String Pbusy="Image/MainIcon/Status\\busy.png";
	static String away="Image/MainIcon/Status\\away.png";
	static String invisible="Image/MainIcon/Status\\invisible.png";
	static String Qme="Image/MainIcon/Status\\Qme.png";
	static String mute="Image/MainIcon/Status\\mute.png";
	///C:\Users\Administrator\Desktop\课程设计\topIcon
//	/////////////////////////最顶层的那排图标按钮
	static String Pzone="Image/MainIcon/topIcon\\qzone.png";
	static String Pblog="Image/MainIcon/topIcon\\wblog.png";
	static String Pemail="Image/MainIcon/topIcon\\mail.png";
	static String Pshop="Image/MainIcon/topIcon\\paipai.png";
	static String Pmoney="Image/MainIcon/topIcon\\purse.png";
	static String Pmeber="Image/MainIcon/topIcon\\friend.png";
	static String Pweb="Image/MainIcon/topIcon\\soso.png";
//	//	/////////////////////////最底层的那排图标按钮
	static String PApp="Image/MainIcon/MainTopToolBar\\appbox_mgr_btn.png";
	static String QQSafe="Image/MainIcon/MainTopToolBar\\QQSafe.png";
	static String SystemSet="Image/MainIcon/MainTopToolBar\\SystemSet.png";
	static String find="Image/MainIcon/MainTopToolBar\\find.png";
	static String message="Image/MainIcon/MainTopToolBar\\message.png";
	static String groupmainpage="Image/MainIcon/MainTopToolBar\\groupmainpage.png";
	String face="";
	/////////////////////////////////
	public ClientToServer cts;
	Login logout;
	private UserInfoBean userInfo;
	public Hashtable friendInfoTable;
	public int n;
	//////////////////////////////
	/*
	 * 构造方法
	 */
	public J_MainFrm(ClientToServer cts,UserInfoBean userInfo,Hashtable friendInfoTable,int n) throws IOException{
		this.cts = cts;
		logout = new Login(cts);
		this.userInfo = userInfo;
		this.friendInfoTable = friendInfoTable;
		this.n = n;
		
		System.out.println(Background);		
		this.setTitle("QQ3014");
		nickname.setBounds(0, 0, 100, 30);
		nickname.setLocation(3, 10);
		nickname.setFont(new java.awt.Font("楷体",Font.BOLD,30));
		nickname.setForeground(Color.MAGENTA);
		nickname.setText(userInfo.getNickName());
		
		sign.setBounds(100,0,300,30);
		sign.setLocation(70, 10);
		sign.setFont(new java.awt.Font("楷体",Font.ITALIC,18));
		sign.setForeground(Color.GREEN);
		sign.setText("("+userInfo.getSign()+")");
		ShowTrayIcon();
		face="Image/MainIcon/qqicons\\Catch0000"+userInfo.getPhotoID()+".jpg";
		ShowTouXiang(this);
		
		abc.setBounds(this.getBounds());
		abc.setLayout(null);
		abc.setOpaque(false);
		abc.add(nickname); //昵称
		abc.add(sign);
		
		MainFrm=this;
		this.setResizable(false);
		this.setUndecorated(true);//不需要标题栏等装饰
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation((int)(screenSize.getWidth()-Mwide)/2,
				(int)(screenSize.getHeight()-Mheight)/2);
		this.setSize(Mwide,Mheight);
		this.setBackground(Color.blue);
		this.addMouseMotionListener(new MyMouseAdapter());
		this.addMouseListener(new MouseListener(){
			public void mouseClicked(MouseEvent e) {		
			}
			public void mouseEntered(MouseEvent e) {
				if(isHide){
				MainFrm.setLocation(MainFrm.getLocationOnScreen().x,0);
				isHide=false;
				MainFrm.setAlwaysOnTop(false);
				}
			}
			public void mouseExited(MouseEvent e) {
				if(MainFrm.getLocationOnScreen().y<=0){
			
					MainFrm.setLocation(MainFrm.getLocationOnScreen().x,3-Mheight);
					isHide=true;
					MainFrm.setAlwaysOnTop(true);
				}
			}
			public void mousePressed(MouseEvent e) {
				offsetX = e.getX();
				offsetY = e.getY();
			}
			public void mouseReleased(MouseEvent e) {		
			}});
		//////背景图片
		Bk_label=new JLabel();
		Bk_label.setBounds(MainFrm.getBounds());
		Bk_label.setLocation(0, 0);
		Bk_label.setIcon(new ImageIcon(Background));
					
		////////////状态按钮/隐身，在线等。。。
		
		JComboBox icb=new JComboBox();//定义标准组合框
		  icb.setMaximumRowCount(7);//设置最大显示行
		  icb.setRenderer(new CombListRenderer());//调用单元格设置（这里使用了我们刚才创建的类）
		  icb.setBackground(new Color(112,100,200,166));//设置背景色
		  
		  icb.addItem(new Object[]{new ImageIcon(Ponline),"在线"});//添加选项
		  icb.addItem(new Object[]{new ImageIcon(Poffline),"离线"});//添加选项
		  icb.addItem(new Object[]{new ImageIcon(Pbusy),"忙碌"});//添加选项
		  icb.addItem(new Object[]{new ImageIcon(away),"离开"});//添加选项
		  icb.addItem(new Object[]{new ImageIcon(invisible),"隐身"});//添加选项
		  icb.addItem(new Object[]{new ImageIcon(Qme),"Q我"});//添加选项
		  icb.addItem(new Object[]{new ImageIcon(mute),"勿扰"});//添加选项
		  
		
		icb.setBounds(0, 0, 80,20);
		icb.setLocation(75, 45);
		icb.setBorder(null);
		
		abc.add(icb);
		
		 
//		****************搜索栏***************************
		JTextArea serch=new JTextArea("搜索...");
		serch.setToolTipText("输入QQ号码、姓名/昵称、拼音、Email查找\n\r\t联系人，" +
				"还可以通过完整的QQ号码查找陌生\n人");
		serch.setBackground(Color.gray);
		serch.setBounds(0, 0, 296, 30);
		serch.setLocation(2, 100);
		abc.add(serch);
//		*****************最上面的那排图标按钮***************8 
		JButton BZone,BBlog,BEmail,BShop,BMoney,BMember,BWeb;
		BZone=new JButton();		//空间
		BZone.setBounds(0, 0, 20, 20);
		BZone.setLocation(80, 75);
		BZone.setIcon(new ImageIcon(Pzone));
		BZone.setContentAreaFilled(false);
		BZone.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				
					Update();
									
				JOptionPane.showMessageDialog(null,"刚刷新好友列表了，tree");
			}});
		abc.add(BZone);
		
		BBlog=new JButton();		//微博
		BBlog.setBounds(0, 0, 20, 20);
		BBlog.setLocation(100, 75);
		BBlog.setIcon(new ImageIcon(Pblog));
		BBlog.setContentAreaFilled(false);
		abc.add(BBlog);
		
		BEmail=new JButton();		//Email
		BEmail.setBounds(0, 0, 20, 20);
		BEmail.setLocation(120, 75);
		BEmail.setIcon(new ImageIcon(Pemail));
		BEmail.setContentAreaFilled(false);
		abc.add(BEmail);

		BShop=new JButton();		//购物
		BShop.setBounds(0, 0, 20, 20);
		BShop.setLocation(140, 75);
		BShop.setIcon(new ImageIcon(Pshop));
		BShop.setContentAreaFilled(false);
		abc.add(BShop);
		
		BMoney=new JButton();		//钱包
		BMoney.setBounds(0, 0, 20, 20);
		BMoney.setLocation(160, 75);
		BMoney.setIcon(new ImageIcon(Pmoney));
		BMoney.setContentAreaFilled(false);
		abc.add(BMoney);
		
		BMember=new JButton();		//会员
		BMember.setBounds(0, 0, 20, 20);
		BMember.setLocation(180, 75);
		BMember.setIcon(new ImageIcon(Pmeber));
		BMember.setContentAreaFilled(false);
		abc.add(BMember);
		
		BWeb=new JButton();			//迷你网站
		BWeb.setBounds(0, 0, 20, 20);
		BWeb.setLocation(200, 75);
		BWeb.setIcon(new ImageIcon(Pweb));
		BWeb.setContentAreaFilled(false);
		abc.add(BWeb);
		
//		 ******************上面的那排图标按钮结束**************************
		
//		******************下面面的那排图标按钮begin**************************
		JButton Bcai,Bgame,Bpet,Bmusic,Bvedio,Btuan,Bmanager,
		Bxun,Bapp,Bmain,Bset,Bmessage,Bfile,Bserch,BAPP;
		Bcai=new JButton();		//QQ彩票
		Bcai.setBounds(0, 0, 20, 20);
		Bcai.setLocation(5, 590);
		Bcai.setIcon(new ImageIcon(Pzone));
		Bcai.setContentAreaFilled(false);
		abc.add(Bcai);
		
		Bgame=new JButton();		//QQ游戏
		Bgame.setBounds(0, 0, 20, 20);
		Bgame.setLocation(35, 590);
		Bgame.setIcon(new ImageIcon(Pblog));
		Bgame.setContentAreaFilled(false);
		abc.add(Bgame);
		
		Bpet=new JButton();		//QQ宠物
		Bpet.setBounds(0, 0, 20, 20);
		Bpet.setLocation(65, 590);
		Bpet.setIcon(new ImageIcon(Pemail));
		Bpet.setContentAreaFilled(false);
		abc.add(Bpet);

		Bmusic=new JButton();		//QQ音乐
		Bmusic.setBounds(0, 0, 20, 20);
		Bmusic.setLocation(95, 590);
		Bmusic.setIcon(new ImageIcon(Pshop));
		Bmusic.setContentAreaFilled(false);
		abc.add(Bmusic);

		Bvedio=new JButton();		//腾讯视频
		Bvedio.setBounds(0, 0, 20, 20);
		Bvedio.setLocation(125, 590);
		Bvedio.setIcon(new ImageIcon(Pmoney));
		Bvedio.setContentAreaFilled(false);
		abc.add(Bvedio);
		
		Btuan=new JButton();		//QQ团购
		Btuan.setBounds(0, 0, 20, 20);
		Btuan.setLocation(155, 590);
		Btuan.setIcon(new ImageIcon(Pmeber));
		Btuan.setContentAreaFilled(false);
		abc.add(Btuan);
		
		Bmanager=new JButton();			//电脑管家
		Bmanager.setBounds(0, 0, 20, 20);
		Bmanager.setLocation(185, 590);
		Bmanager.setIcon(new ImageIcon(QQSafe));
		Bmanager.setContentAreaFilled(false);
		abc.add(Bmanager);

		Bxun=new JButton();			//易迅网
		Bxun.setBounds(0, 0, 20, 20);
		Bxun.setLocation(215, 590);
		Bxun.setIcon(new ImageIcon(Pweb));
		Bxun.setContentAreaFilled(false);
		abc.add(Bxun);

		Bapp=new JButton();			//应用管理器
		Bapp.setBounds(0, 0, 20, 20);
		Bapp.setLocation(270, 590);
		Bapp.setIcon(new ImageIcon(PApp));
		Bapp.setContentAreaFilled(false);
		abc.add(Bapp);
		

		Bmain=new JButton();			//主菜单
		Bmain.setBounds(0, 0, 20, 20);
		Bmain.setLocation(5, 620);
		Bmain.setIcon(new ImageIcon(Pweb));
		Bmain.setContentAreaFilled(false);
		abc.add(Bmain);
		
		Bset=new JButton();			//系统设置
		Bset.setBounds(0, 0, 20, 20);
		Bset.setLocation(35, 620);
		Bset.setIcon(new ImageIcon(SystemSet));
		Bset.setContentAreaFilled(false);
		abc.add(Bset);
		
		Bmessage=new JButton();			//消息管理
		Bmessage.setBounds(0, 0, 20, 20);
		Bmessage.setLocation(65, 620);
		Bmessage.setIcon(new ImageIcon(message));
		Bmessage.setContentAreaFilled(false);
		abc.add(Bmessage);
		
		Bfile=new JButton();			//文件管理
		Bfile.setBounds(0, 0, 20, 20);
		Bfile.setLocation(95, 620);
		Bfile.setIcon(new ImageIcon(Pweb));
		Bfile.setContentAreaFilled(false);
		abc.add(Bfile);
		
		Bserch=new JButton();			//查找
		Bserch.setBounds(0, 0, 20, 20);
		Bserch.setLocation(125, 620);
		Bserch.setIcon(new ImageIcon(find));
		Bserch.setContentAreaFilled(false);
		Bserch.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				if(FindFriendFrm==null){
				FindFriendFrm =new findFriendFrm(MainFrm,MainFrm.userInfo);
				}
			}});
		
		abc.add(Bserch);
        
		BAPP=new JButton();			//应用宝
		BAPP.setBounds(0, 0, 20, 20);
		BAPP.setLocation(240, 620);
		BAPP.setIcon(new ImageIcon(groupmainpage));
		BAPP.setContentAreaFilled(false);
		abc.add(BAPP);
		
//		******************上面的那排图标按钮end**************************
		final JButton B_close=new JButton("关闭");
		B_close.setBounds(0,0,30,20);
		B_close.setLocation(this.getBounds().width-20, 0);
		B_close.setIcon(new ImageIcon(Pclose1));
		B_close.setOpaque(false);
		B_close.setBorder(null);
		B_close.setBackground(Color.white);
		B_close.addMouseListener(new MouseListener(){
			public void mouseClicked(MouseEvent e) {
				if(JOptionPane.showConfirmDialog(null, "<html><font size=3>确定退出吗？</html>","系统提示",JOptionPane.OK_CANCEL_OPTION,JOptionPane.INFORMATION_MESSAGE)==0)
				{							
					try {
						logout.userLogout();
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					ClientToServerThread ctst = Port.comm.get(1);
					ctst.closeSocket();
					System.exit(0);
				}
				else
				{
					return;
				}
			}
			public void mouseEntered(MouseEvent e) {	
				B_close.setIcon(new ImageIcon(Pclose));
			}
			public void mouseExited(MouseEvent e) {	
				B_close.setIcon(new ImageIcon(Pclose1));
			}
			public void mousePressed(MouseEvent e) {
			}
			public void mouseReleased(MouseEvent e) {		
			}}	);
		abc.add(B_close);
		
		final JButton B_min=new JButton("min");
		B_min.setBounds(0,0,30,20);
		B_min.setLocation(this.getBounds().width-40, 0);
		B_min.setIcon(new ImageIcon(Pmin));
		B_min.setOpaque(false);
		B_min.setBorder(null);
		B_min.setBackground(Color.white);
		B_min.addMouseListener(new MouseListener(){
			public void mouseClicked(MouseEvent e) {
				dispose();//窗口最小化时dispose该窗口  

			}
			public void mouseEntered(MouseEvent e) {	
				B_min.setIcon(new ImageIcon(Pmin1));
			}
			public void mouseExited(MouseEvent e) {	
				B_min.setIcon(new ImageIcon(Pmin));
			}
			public void mousePressed(MouseEvent e) {
			}
			public void mouseReleased(MouseEvent e) {		
			}}	);
		abc.add(B_min);
		
		final JButton B_skin=new JButton("min");
		B_skin.setBounds(0,0,30,20);
		B_skin.setLocation(this.getBounds().width-60, 0);
		B_skin.setIcon(new ImageIcon(Pskin));
		B_skin.setOpaque(false);
		B_skin.setBorder(null);
		B_skin.setBackground(Color.white);
		B_skin.addMouseListener(new MouseListener(){
			public void mouseClicked(MouseEvent e) {
				
			}
			public void mouseEntered(MouseEvent e) {	
				B_skin.setIcon(new ImageIcon(Pskin1));
			}
			public void mouseExited(MouseEvent e) {	
				B_skin.setIcon(new ImageIcon(Pskin));
			}
			public void mousePressed(MouseEvent e) {
			}
			public void mouseReleased(MouseEvent e) {		
			}}	);
		abc.add(B_skin);
		//////////////
		///选项卡
		UpdateFriendList();
		
		////
		JLabel bk=new JLabel();
		 bk.setBounds(0,0,300,420);
		 bk.setLocation(0, 157);
		 bk.setIcon(new ImageIcon(Pmask));
		 abc.add(bk);
		 
		abc.add(Bk_label);
			
		this.add(abc);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		
		this.setVisible(true);
		
		/**窗体关闭按钮事件*/
		
	}
	class MyMouseAdapter extends MouseAdapter {
		
		
		public void mouseDragged(MouseEvent e) {
			x=e.getXOnScreen();
			y= e.getYOnScreen();
				
				if(y<=0){y=0;
					
				}
				setLocation(x-offsetX, y-offsetY);
		}
	}
	public void ShowTrayIcon(){
	/////托盘
		Toolkit tk = Toolkit.getDefaultToolkit();
		Image img = tk.getImage(Ptask);
		SystemTray systemTray = SystemTray.getSystemTray();//获得系统托盘的实例   
		  /////添加弹出菜单
		PopupMenu popupMenu = new PopupMenu(); // 创建弹出菜单   
	    MenuItem exit = new MenuItem("退出"); // 创建菜单项   
	    //响应方法   
	    exit.addActionListener(new ActionListener() {  
			public void actionPerformed(ActionEvent e) {
				System.exit(1);
			}  		      
	    });  			      
	    popupMenu.add(exit); // 为弹出菜单添加菜单项   			    
	    /////////		    
		final TrayIcon	trayIcon=new TrayIcon(img,Ptask);
		try  
		{
		  
		  systemTray.add(trayIcon);//设置托盘的图标，0.gif与该类文件同一目录
		 }
		catch (AWTException e2)  
		{
		  e2.printStackTrace();
		}
		
		trayIcon.setPopupMenu(popupMenu); // 为托盘图标加弹出菜弹   
		trayIcon.setToolTip("QQ:1064308261\n声音：开启\n消息提醒框：关闭\n会话消息：任务栏头像闪动");
		
		trayIcon.addMouseListener(new MouseListener(){
			public void mouseClicked(MouseEvent e) {
				if(e.getClickCount()==2)//双击托盘窗口再现
				{
					setExtendedState(JFrame.NORMAL);//状态
					setVisible(true);
				}
			}
			public void mouseEntered(MouseEvent e) {
			}
			public void mouseExited(MouseEvent e) {	
			}
			public void mousePressed(MouseEvent e) {	
			}
			public void mouseReleased(MouseEvent e) {	
			}});
		/////
	}
	public void ShowTouXiang(J_MainFrm MainFrm){
		/**
		 * 该方法显示头像的
		 */
		final JLabel Ltouxiang=new JLabel();
		final JLabel Lselect=new JLabel();
		Ltouxiang.setBounds(0, 0, 68, 68);
		Ltouxiang.setLocation(10, 30);
		ImageIcon icon=new ImageIcon();
		Ltouxiang.setIcon(icon);
		
		Lselect.setBounds(0, 0, 60, 60);
		Lselect.setLocation(14, 34);
		ImageIcon icon1=new ImageIcon(MainFrm.face);
		Lselect.setIcon(icon1);
		MainFrm.add(Lselect);
		
		Ltouxiang.addMouseListener(new MouseListener(){
			public void mouseClicked(MouseEvent e) {
				new InformationFrm( userInfo);
			}
			public void mouseEntered(MouseEvent e) {
				Ltouxiang.setIcon(new ImageIcon(select));
			}
			public void mouseExited(MouseEvent e) {
				Ltouxiang.setIcon(new ImageIcon());
			}
			public void mousePressed(MouseEvent e) {
			}
			public void mouseReleased(MouseEvent e) {
			}});
		MainFrm.add(Ltouxiang);
				
	}
	public JTabbedPane createFriendList(ClientToServer cts,final UserInfoBean userInfo,final Hashtable friendInfoTable,final int n) throws IOException{
//		*********************创建默认分组**
		groupNumber=0;
		IconNode haoyou=new IconNode("好友");
		IconNode strager=new IconNode("陌生人");
		IconNode black=new IconNode("黑名单");
		//haoyou.add(new IconNode(""));
		//strager.add(new IconNode(""));
		//black.add(new IconNode(""));
		Root=new IconNode(null,null);
		Root.add(haoyou);
		
		
		
//		***********************根据从数据库获取的分组和好友等信息创建好友列表
		
		IconNode root[]=new IconNode [20] ; //假设最多20个分组
		
		
		boolean isfind = false;//定义是找到相同分组
		
		String groupname="";//存放临时取出来的分组名字
		int total=0;//存放总的分组数
	
	    Enumeration it = friendInfoTable.elements();
	   
		for(int i = 0; i < n; i++){
			total=groupNumber;
			isfind=false;
			UserInfoBean friend = (UserInfoBean)it.nextElement();
			System.out.println("新建好友中："+friend.getQq()+" "+friend.getNickName()+" "+
					friend.getSubGroupName()+"在线否："+friend.getStatus());
		///进行和已有的分组名字比较
			groupname=friend.getSubGroupName();
			if(groupNumber==0){
				groupNumber++;
				groupName[groupNumber]=groupname;
				root[groupNumber]=new IconNode(groupName[groupNumber]);//创建分组
				 face="Image/MainIcon/qqicons\\Catch0000"+friend.getPhotoID()+".jpg";
				if(!friend.getStatus()){//如果不在线，则灰显头像
					try {
						IconNode friendNode=new IconNode(ColorConvertOp.getGrayPicture(face),friend.getNickName()+//添加好友到分组列表里
								"  "+"("+friend.getQq()+")",friend.getSign());
						root[groupNumber].add(friendNode);
						friendNode.setQQ(Integer.toString(friend.getQq()));
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
				else{
					IconNode friendNode=new IconNode(new ImageIcon(face),friend.getNickName()+//添加好友到分组列表里
				    		"  "+"("+friend.getQq()+")",friend.getSign());
				root[groupNumber].add(friendNode);
				friendNode.setQQ(Integer.toString(friend.getQq()));
				}
				Root.add(root[groupNumber]);
				
			}
			else{///进行和已有的分组名字比较
				for(int j=1;j<=total;j++){
					if(groupName[j].equals(groupname)){//如果相等
						isfind=true;//找到了
						 face="Image/MainIcon/qqicons\\Catch0000"+friend.getPhotoID()+".jpg";
						 if(!friend.getStatus()){//如果不在线，则灰显头像
							 try {
									IconNode friendNode=new IconNode(ColorConvertOp.getGrayPicture(face),friend.getNickName()+//添加好友到分组列表里
											"  "+"("+friend.getQq()+")",friend.getSign());
									root[groupNumber].add(friendNode);
									friendNode.setQQ(Integer.toString(friend.getQq()));
								} catch (IOException e1) {
									// TODO Auto-generated catch block
									e1.printStackTrace();
								}
							}
							else{
								IconNode friendNode=new IconNode(new ImageIcon(face),friend.getNickName()+//添加好友到分组列表里
							    		"  "+"("+friend.getQq()+")",friend.getSign());
							root[groupNumber].add(friendNode);
							friendNode.setQQ(Integer.toString(friend.getQq()));
							}
							Root.add(root[groupNumber]);
						break;
					}
					else if(j>=groupNumber && !isfind){//如果不相等
						groupNumber++;
						groupName[groupNumber]=groupname;
						root[groupNumber]=new IconNode(groupName[groupNumber]);//创建分组
						 face="Image/MainIcon/qqicons\\Catch0000"+friend.getPhotoID()+".jpg";
						 if(!friend.getStatus()){//如果不在线，则灰显头像
								try {
									IconNode friendNode=new IconNode(ColorConvertOp.getGrayPicture(face),friend.getNickName()+//添加好友到分组列表里
											"  "+"("+friend.getQq()+")",friend.getSign());
									root[groupNumber].add(friendNode);
									friendNode.setQQ(Integer.toString(friend.getQq()));
								} catch (IOException e1) {
									// TODO Auto-generated catch block
									e1.printStackTrace();
								}
							}
							else{
								IconNode friendNode=new IconNode(new ImageIcon(face),friend.getNickName()+//添加好友到分组列表里
							    		"  "+"("+friend.getQq()+")",friend.getSign());
							root[groupNumber].add(friendNode);
							friendNode.setQQ(Integer.toString(friend.getQq()));
							}
							Root.add(root[groupNumber]);
						}
				}
			}
			
			
		}
		Root.add(strager);
		Root.add(black);//添加黑名单
	    UIManager.put("Tree.collapsedIcon", new ImageIcon(treeNode));    
	    UIManager.put("Tree.expandedIcon", new ImageIcon(treeNode1)); 
	    UIManager.put("Tree.OffX", new ImageIcon(treeNode1)); 
	    
		final JTree tree=new JTree(Root);
		final IconNodeRenderer render=new IconNodeRenderer();
		
		
		render.setBackground(Color.pink);
		tree.setCellRenderer(render); //设置单元格描述    
	    tree.setEditable(false); //设置树是否可编辑
	    tree.setRootVisible(false);//设置树的根节点是否可视
	    tree.setRowHeight(50);	   //设置行距
	    
	    tree.setShowsRootHandles(true); //显示折叠/展开 图标
	  
	    tree.setToggleClickCount(1);//设置单击几次展开数节点
	 
	    tree.setOpaque(false);
	    tree.setUI(new MyTreeUI1(){});
		
		tree.addMouseListener(new MouseListener(){
			public void mouseClicked(MouseEvent e) {
				if(e.getClickCount()==2)//双击节点
			      {
					
			       TreePath path=tree.getSelectionPath();//获取选中节点路径
			       Object node=path.getLastPathComponent();//通过路径将指针指向该节点
			       if(((IconNode) node).isLeaf())//如果该节点是叶子节点
			    	   
			       {	System.out.println("叶子节点的名字为："+((IconNode)node).getText()+"QQ为："+((IconNode)node).getQQ());
			       		String nodeQQ=((IconNode)node).getQQ();
			      
			       		Enumeration it = friendInfoTable.elements();
				   
			       		for(int i = 0; i < n; i++){
						
						UserInfoBean friend = (UserInfoBean)it.nextElement();
						String qq=Integer.toString(friend.getQq());
						if(nodeQQ.equals(qq)){
							//TODO:
							String sendIP = "127.0.0.1";
							String receiveIP = "127.0.0.1";
							
							InetAddress localAddr = null;
							try {
								localAddr = InetAddress.getLocalHost();
							} catch (UnknownHostException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
							
							receiveIP = localAddr.getHostAddress();
							
							MainFrm.userInfo.setIP(receiveIP);
							friend.setIP(receiveIP);
							JChatFrm jcf = new JChatFrm(MainFrm.userInfo,friend);
							jcf.setVisible(true);
							break;
						}
					}
			    	       
//			    	   JOptionPane.showMessageDialog(null,"你双击了好友头像，此时应该弹出一个聊天对话框来");
			    	   
			       }
			       else//不是叶子节点
			       {       
			       }
			      }
			}
			public void mouseEntered(MouseEvent e) {
				
			      
			}
			public void mouseExited(MouseEvent e) {
			}
			public void mousePressed(MouseEvent e) {
				final JTree tree = (JTree) e.getSource();
	             int selRow = tree.getRowForLocation(e.getX(), e.getY());
	            TreePath selPath = tree.getPathForLocation(e.getX(), e.getY());
	            
	            if (selRow != -1 )//判断选中分
	            {
	            	final Object node=selPath.getLastPathComponent();//通过路径将指针指向该节点        
	                if (e.getModifiers()==InputEvent.BUTTON3_MASK && !((IconNode) node).isLeaf())
	                {
	                	JPopupMenu popup = new JPopupMenu();
	                	JMenuItem refresh = new JMenuItem("刷新好友列表");
	                	 popup.add(refresh);
	                	 
	                	JMenuItem showOnLine = new JMenuItem("显示在线联系人");
	                	 popup.add(showOnLine);
	                	 popup.addSeparator();
	                	 
	                	JMenuItem hide = new JMenuItem("隐身对该分组可见");
	                	 popup.add(hide);
	                	
	                	 
	                	JMenuItem online = new JMenuItem("在线对该分组隐身");
	                	 popup.add(online);
	                	 popup.addSeparator();
	                	 
	                	JMenuItem add = new JMenuItem("添加分组");
	                	 popup.add(add);
	                	 add.addActionListener(new ActionListener(){
								public void actionPerformed(ActionEvent e) {
									IconNode Root31=new IconNode(" ");//定义根节点
									IconNode Root3=new IconNode("未分组");//定义根节点
								    Root3.add(Root31);//定义二级节点
								    Root.add(Root3);
								    tree.updateUI();
//								    TODO
								}
		                		
		                	});
	                	 
	                	JMenuItem reName = new JMenuItem("重命名");
	                	 popup.add(reName);
	                	 add.setForeground(Color.red);
	                	 add.setBackground(Color.green);
	                	 
	                	JMenuItem delete = new JMenuItem("删除该组");
	                	 popup.add(delete);
	                	delete.setIcon(new ImageIcon(touxiang1));
	                	delete.setForeground(Color.red);
	                	
	                	JMenuItem manager = new JMenuItem("好友管理");
	                	 popup.add(manager);
	                	
	                	 popup.setForeground(Color.green);
	                	 	                	 		                
	                	  popup.show(e.getComponent(), e.getX(), e.getY());
	                	 	                					    	   
	                } 
	                else if (e.getModifiers()==InputEvent.BUTTON3_MASK && ((IconNode) node).isLeaf())
	                {//右键好友/叶子节点
	                	
	                	JPopupMenu popup = new JPopupMenu();
	                	JMenuItem sendMessage = new JMenuItem("发送即时信息");
	                	 popup.add(sendMessage);
	                	 
	                	JMenuItem sendEmail = new JMenuItem("发送电子邮件");
	                	 popup.add(sendEmail);
	                	 popup.addSeparator();
	                	 
	                	JMenuItem check = new JMenuItem("查看资料");
	                	 popup.add(check);
	                	check.addActionListener(new ActionListener(){

							@Override
							public void actionPerformed(ActionEvent e) {
								// TODO Auto-generated method stub
								Enumeration it = friendInfoTable.elements();
								String nodeQQ=((IconNode)node).getQQ();
					       		for(int i = 0; i < n; i++){
								
								UserInfoBean friend = (UserInfoBean)it.nextElement();
								String qq=Integer.toString(friend.getQq());
								if(nodeQQ.equals(qq)){
									
									new InformationFrm(friend);
								}
					       		}
								
							}});

	                	 JMenu chatRecord = new JMenu("消息记录");
 /******************************消息记录的二级子菜单begin****************************/
	                	 JMenuItem local = new JMenuItem("查看本地消息");
	                	 JMenuItem manyou = new JMenuItem("查看漫游消息");
	                	 JMenuItem shangchuan = new JMenuItem("查看上传消息");
	                	 
	                	 chatRecord.add(local);
	                	 chatRecord.add(manyou);
	                	 chatRecord.add(shangchuan);
	                	 
/******************************消息记录的二级子菜单end****************************/          	
	                	 
	                	 
	                	 popup.add(chatRecord);
	                	 popup.addSeparator();
	                	 
	                	 JMenu setAuthority = new JMenu("设置权限");
/******************************设置权限的二级子菜单begin****************************/ 
	                	 JMenuItem pingbi = new JMenuItem("屏蔽此人消息");
	                	 JMenuItem OH = new JMenuItem("在线对其隐身");
	                	 JMenuItem HO = new JMenuItem("隐身对其可见");
	                	 JMenuItem QX1 = new JMenuItem("取消对其可见");
	                	 JMenuItem QX2 = new JMenuItem("取消对其隐身");
	                	 
/******************************设置权限的二级子菜单end****************************/ 
	                	 setAuthority.add(pingbi);
	                	 setAuthority.add(OH);
	                	 setAuthority.add(HO);
	                	 setAuthority.add(QX1);
	                	 setAuthority.add(QX2);
	                	 popup.add(setAuthority);
	                	 
	                	JMenuItem reName = new JMenuItem("修改备注姓名");
	                	 popup.add(reName);
	                	 	                	 
	                	 JMenu move = new JMenu("移动联系人至");
/******************************移动联系人至的二级子菜单begin****************************/
	                	 JMenuItem friend = new JMenuItem("我的好友");
	                	 JMenuItem strager = new JMenuItem("陌生人");
	                	 JMenuItem black = new JMenuItem("黑名单");
	                	 
/******************************移动联系人至的二级子菜单end****************************/	 
	                	 move.add(friend);
	                	 move.add(strager);
	                	 move.add(black);
	                	 
	                	 popup.add(move);
	                	 move.setIcon(new ImageIcon(touxiang1));
	                	 move.setForeground(Color.red);
	                	
	                	JMenuItem delete = new JMenuItem("删除好友");
	                	delete.addActionListener(new ActionListener(){

							@Override
							public void actionPerformed(ActionEvent e) {
								
								// TODO Auto-generated method stub
								ClientToServer cts = new ClientToServer();
								new Thread(cts).start();
								
								
							       TreePath path=tree.getSelectionPath();//获取选中节点路径
							       Object node=path.getLastPathComponent();//通过路径将指针指向该节点
							       String nodeQQ = "10";
							       if(((IconNode) node).isLeaf())//如果该节点是叶子节点
					   
							       {	System.out.println("叶子节点的名字为："+((IconNode)node).getText()+"QQ为："+((IconNode)node).getQQ());
							       		nodeQQ=((IconNode)node).getQQ();
							       }
							       if(nodeQQ.equals("10")){
							    	   JOptionPane.showMessageDialog(null, "请选择要删除的好友!");
							       }
							       
							       tree.updateUI();
							    		   
							    		   
								int qq = MainFrm.userInfo.getQq();
								int fqq = Integer.parseInt(nodeQQ);
								cts.deleteFriend(qq, fqq);
								JOptionPane.showMessageDialog(null, "好友已删除!");
								
								cts.closeConnect1();
                              			
							     //  JOptionPane.showMessageDialog(null, nodeQQ);
								
							}
	                		 
	                	 });
	                	 popup.add(delete);
	                	 
	                	 
	                	 JMenuItem report = new JMenuItem("举报此用户");
	                	 popup.add(report);
	                	 
	                	 JMenu manager = new JMenu("好友管理");
/******************************好友管理的二级子菜单begin****************************/
	                	 JMenuItem introduce = new JMenuItem("给他推荐好友");
	                	 JMenuItem guanzhu = new JMenuItem("关注此好友");
	                	 JMenuItem tixing = new JMenuItem("好友上线提醒");
	                	 JMenuItem FM = new JMenuItem("好友管理器");
	                	 JMenuItem KJ = new JMenuItem("生成桌面快捷方式");
/******************************好友管理的二级子菜单end****************************/	 
	                	 manager.add(introduce);
	                	 manager.add(guanzhu);
	                	 manager.add(tixing);
	                	 manager.add(FM);
	                	 manager.add(KJ);
	                	 
	                	 popup.add(manager);
	                	 popup.addSeparator();
	                	 
	                	 JMenu QMF = new JMenu("会员快捷功能");
/******************************会员快捷功能的二级子菜单begin****************************/	
	                	 JMenuItem FM1 = new JMenuItem("设置动态炫铃");
	                	 JMenuItem FM2 = new JMenuItem("设置好友铃音");
	                	 JMenuItem FM3 = new JMenuItem("设置QQ装扮");
	                	 QMF.addSeparator();
	                	 JMenuItem FM4 = new JMenuItem("会员群特权");
	                	 JMenuItem FM5 = new JMenuItem("群克隆");
	                	 JMenuItem FM6 = new JMenuItem("好友克隆");
	                	 JMenuItem FM7 = new JMenuItem("好友上线通知");
	                	 QMF.addSeparator();
	                	 JMenuItem FM8 = new JMenuItem("开通会员");
	                	 JMenuItem FM9 = new JMenuItem("索要会员");
	                	
	                	 
/******************************会员快捷功能的二级子菜单end****************************/	
	                	 QMF.add(FM1);
	                	 QMF.add(FM2);
	                	 QMF.add(FM3);
	                	 QMF.add(FM4);
	                	 QMF.add(FM5);
	                	 QMF.add(FM6);
	                	 QMF.add(FM7);
	                	 QMF.add(FM8);
	                	 QMF.add(FM9);
	                	 
	                	 popup.add(QMF);
	                	 
	                	 JMenuItem enterQQZone = new JMenuItem("进入QQ空间");
	                	 popup.add(enterQQZone);
	                	 
	                	 JMenuItem enterBlog = new JMenuItem("进入腾讯微博");
	                	 popup.add(enterBlog);
	                	
	                	 popup.setForeground(Color.green);

	                	  popup.show(e.getComponent(), e.getX(), e.getY());
	                }
	               
	            }
			}
			public void mouseReleased(MouseEvent e) {
			}});
		tree.setBounds(20, 20, 300, 600);
		tree.setLocation(10, 300);
		tree.setRootVisible(false); //不再显示根节点
		tree.putClientProperty("JTree.lineStyle","None");
		
		UIManager.put("TabbedPane.contentOpaque", Boolean.FALSE);//此UIManager可以使JTabbedPane变的透明   
		JLabel Lfrind=new JLabel("好友");
		Lfrind.setOpaque(false);
		JLabel Lzone=new JLabel("空间");
		Lzone.setBackground(Color.red);
		Lzone.setOpaque(false);
		JLabel Lgroup=new JLabel("群组");
		Lgroup.setOpaque(false);
		JLabel Lblog=new JLabel("微博");
		Lblog.setOpaque(false);
		JLabel Llast=new JLabel("最近联系人");
		Llast.setOpaque(false);
		JTabbedPane tabeld=new JTabbedPane();
//		好友列表
		
		
		JScrollPane JSPane;        //滚动面板	  	
		JSPane=new JScrollPane(tree);
		JSPane.setOpaque(false);
		JSPane.getViewport().setOpaque(false);
		JSPane.setBackground(new Color(1,1,1,44));
		
		tabeld.addTab("", JSPane);
		tabeld.setIconAt(0, new ImageIcon(Pskin1));
		tabeld.addTab("空间", Lzone);
		tabeld.addTab("群组", Lgroup);
		tabeld.addTab("微博", Lblog);
		tabeld.addTab("最近联系人", Llast);
		tabeld.setBounds(0, 130, MainFrm.getBounds().width+2, Mheight-200);
		tabeld.setBackground(new Color(0,0,0,0));
		return tabeld;
	}
	public void UpdateFriendList() throws IOException{
				
		tabeld = createFriendList(cts,userInfo,friendInfoTable,n);			
		abc.add(tabeld);
	//	MainFrm.add(abc);
	}
	public void Update(){
		cts.Update();
	}
	public void Update1(Hashtable friendInfoTable,int n) throws IOException{
		if(tabeld != null){
			Root.removeAllChildren();
			this.friendInfoTable = friendInfoTable;
			System.out.println("重新创建好友列表");
		tabeld = createFriendList(cts,userInfo,this.friendInfoTable,n);			
		abc.add(tabeld);
		}

	}
	public void UpdateFriendList(Hashtable friendInfoTable,int n) throws IOException{

		Enumeration it = friendInfoTable.elements();
		   		
				
		int count = Root.getChildCount();
		//JOptionPane.showMessageDialog(null, ""+count);
		for(int i = 0; i < count; i++){
			IconNode node = (IconNode)Root.getChildAt(i);
			int count2 = node.getChildCount();
			//JOptionPane.showMessageDialog(null, ""+count2);
			for(int j = 0; j < count2; j++){
				IconNode nodechild = (IconNode)node.getChildAt(j);
				String[] str = nodechild.getText();
				int lstr = str[0].indexOf("(");
				int rstr = str[0].indexOf(")");
				String qqStr = str[0].substring(lstr+1, rstr);
			//	JOptionPane.showMessageDialog(null, qqStr);
				UserInfoBean friend = (UserInfoBean)it.nextElement();
				String ss = friend.getStatus()?"上线":"下线";
			//	JOptionPane.showMessageDialog(null, friend.getQq()+":"+ss);
				String strqq = ""+friend.getQq();
				if(qqStr.equals(strqq)){
					if(friend.getStatus()){
						face="Image/MainIcon/qqicons\\Catch0000"+friend.getPhotoID()+".jpg";
						nodechild.setIcon(new ImageIcon(face));
						//JOptionPane.showMessageDialog(null, friend.getQq()+"：在线");
					}else{
						face="Image/MainIcon/qqicons\\Catch0000"+friend.getPhotoID()+".jpg";
						nodechild.setIcon(ColorConvertOp.getGrayPicture(face));						
					}
						
				}
			}
		}		
	}
	
	
}
