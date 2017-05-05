/**
 * 
 */
package com.client.view;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import com.client.business.uiManager.ChatPanel;
import com.client.chat.PicsJWindow;
import com.client.data.LoadImages;
import com.common.UserInfoBean;
import com.tools.ClientToServerThread;

/**
 * @author lenovo
 *
 */
public class JChatFrm extends JFrame implements ActionListener{
	/**
	 * @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么)
	 */
	private static final long serialVersionUID = 1L;
	public ChatPanel chatpanel;
	ClientToServerThread ctsT = null;
	
	
	public MyButton btn_close;
	
	
	public PicsJWindow picWindow;
	public JChatFrm(UserInfoBean user,UserInfoBean friend){
		
		setTitle("聊天会话");
		setBounds(350, 150, 586, 508);//540,517 
		setResizable(false);
		setUndecorated(true);
		
		chatpanel=new ChatPanel(this,ctsT,user,friend);
		add(chatpanel);
		//FontPanel fontpanel=new FontPanel();
		//add(fontpanel);
		setVisible(false);
		LoadImages loadimage= new LoadImages();
	
		Image image=loadimage.loadImage("icon");
		this.setIconImage(image);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		
		picWindow = new PicsJWindow(this);
		picWindow.setVisible(false);
		/**窗体关闭按钮事件*/
		
		{//关闭窗体
			btn_close=new MyButton();
			btn_close.setBounds(558,1,28,26);
			btn_close.setContentAreaFilled(false);
			btn_close.setToolTipText("关闭");
			chatpanel.add(btn_close);	
			btn_close.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
			{
				
				
					if(JOptionPane.showConfirmDialog(null, "<html><font size=3>确定退出吗？</html>","系统提示",JOptionPane.OK_CANCEL_OPTION,JOptionPane.INFORMATION_MESSAGE)==0)
					{		
						
						chatpanel.closeCtst();
						closeWindow();
					}
					else
					{
						return;
					}
				}
			}
				
			}
			);//添加事件处理
			
		}
		
		this.addWindowListener(new WindowAdapter()
		{
			public void windowClosing(WindowEvent e)
			{
				if(JOptionPane.showConfirmDialog(null, "<html><font size=3>确定退出吗？</html>","系统提示",JOptionPane.OK_CANCEL_OPTION,JOptionPane.INFORMATION_MESSAGE)==0)
				{		
					
					chatpanel.closeCtst();
					closeWindow();
				}
				else
				{
					return;
				}
			}
		}
		);
		chatpanel.addMouseMotionListener(new MouseAdapter() {
		    private Point draggingAnchor = null;
            @Override
            public void mouseMoved(MouseEvent e) {
            	draggingAnchor = new Point(e.getX() + chatpanel.getX(), e.getY() + chatpanel.getY());
            	
           
            }
            
            @Override
            public void mouseDragged(MouseEvent e) {
            	setLocation(e.getLocationOnScreen().x - draggingAnchor.x, e.getLocationOnScreen().y - draggingAnchor.y);
            }
	});
	}
	

	public void closeWindow(){
		this.dispose();
		
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String sendIP = "127.0.0.1";
		String receiveIP = "127.0.0.1";
		UserInfoBean user = new UserInfoBean();
		UserInfoBean friend = new UserInfoBean();
		
		user.setIP(sendIP);
		user.setQq(10001);
		user.setNickName("小强");
		user.setSign("坚持就是胜利!");
		friend.setIP(receiveIP);
		friend.setQq(10002);
		friend.setNickName("小丽");
		new JChatFrm(user,friend);
	}


	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}
	public void Bt_erpression_true()
	{
		picWindow.setVisible(true);
	}
	public void Bt_erpression_false()
	{
		picWindow.setVisible(false);
	}

}
