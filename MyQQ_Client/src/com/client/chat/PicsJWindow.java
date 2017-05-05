package com.client.chat;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.SystemColor;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JWindow;
import javax.swing.SwingConstants;

import com.client.view.JChatFrm;
public class PicsJWindow  extends JWindow { 
	private static final long serialVersionUID = 1L;
	GridLayout gridLayout1 = new GridLayout(5,5); 
  JLabel[] ico=new JLabel[25]; /*·Å±íÇé*/
  int  i;
  JChatFrm owner;
  String[] intro = {"¿ÞÆü","Á÷º¹","¿ÉÁ¯","Àäº¹","×¥¿ñ","ßÚÑÀ","Æ²×ì","º©Ð¦","²Áº¹","´ó±ø","·¢Å­","º¦Ðß","°×ÑÛ","¿Ù±Ç","¹þÇ·",
  		"ÇÃ´ò","¹ÄÕÆ","ÔÙ¼û","ÒÉÎÊ","¾ª¿Ö","Î¢Ð¦","±Õ×ì","Ë¥","Î¯Çü","ÄÑ¹ý","","","","","",
  		"","","","","","","","","","","","","","","",
  		"","","","","","","","","","","","","","","",
  		"","","","","","","","","","","","","","","",
  		"","","","","","","","","","","","","","","",
  		"","","","","","","","","","","","","","","",};/*Í¼Æ¬ÃèÊö*/
  public PicsJWindow(JChatFrm owner) { 
    super(owner); 
    this.owner=owner; 
    try { 
    init(); 
    this.setAlwaysOnTop(true); 
    } 
    catch (Exception exception) { 
    exception.printStackTrace(); 
    } 
  } 
  private void init() throws Exception { 
		this.setPreferredSize(new Dimension(28*5,28*5));
		JPanel p = new JPanel();
		p.setOpaque(true);
		this.setContentPane(p);
  	p.setLayout(gridLayout1);
    p.setBackground(SystemColor.text);		
		String fileName = "";
    for(i=0;i <ico.length;i++){ 
    fileName= "12/"+i+".gif";/*ÐÞ¸ÄÍ¼Æ¬Â·¾¶*/ 
    ico[i] =new JLabel(new  ChatPic(PicsJWindow.class.getResource(fileName),i),SwingConstants.CENTER);
    ico[i].setBorder(BorderFactory.createLineBorder(new Color(225,225,225), 1));
   // ico[i].setToolTipText(i+"");
	ico[i].setToolTipText(intro[i]);
    ico[i].addMouseListener(new MouseAdapter(){ 
      public void mouseClicked(MouseEvent  e){ 
      	if(e.getButton()==1){
      		JLabel cubl = (JLabel)(e.getSource());
      		ChatPic cupic = (ChatPic)(cubl.getIcon());
      		owner.chatpanel.set_ep_number(cupic.getIm());
      		owner.chatpanel.insertSendPic(cupic);
      		cubl.setBorder(BorderFactory.createLineBorder(new Color(225,225,225), 1));
        	getObj().dispose();
      	}
      }
				@Override
				public void mouseEntered(MouseEvent e) {
		    ((JLabel)e.getSource()).setBorder(BorderFactory.createLineBorder(Color.BLUE));
				}
				@Override
				public void mouseExited(MouseEvent e) {
					((JLabel)e.getSource()).setBorder(BorderFactory.createLineBorder(new Color(225,225,225), 1));
				} 
      
    }); 
    p.add(ico[i]); 
    } 
    p.addMouseListener(new MouseAdapter(){
			@Override
			public void mouseExited(MouseEvent e) {
    	getObj().dispose(); 
			}
    	
    });
  } 
	@Override
	public void setVisible(boolean show) {
		if (show) {
			determineAndSetLocation();
		}
		super.setVisible(show);
	}	
	private void determineAndSetLocation() {
		Point loc = owner.chatpanel.getPicBtn().getLocationOnScreen();/*¿Ø¼þÏà¶ÔÓÚÆÁÄ»µÄÎ»ÖÃ*/
		setBounds(loc.x-getPreferredSize().width/3, loc.y-getPreferredSize().height,
				getPreferredSize().width, getPreferredSize().height);
	}
  private JWindow getObj(){ 
    return this; 
  } 

}