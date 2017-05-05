package com.client.view;

import java.awt.AWTEvent;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.font.FontRenderContext;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RoundRectangle2D;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
public class ClarityButton extends JComponent {
	Font f=new Font("黑体",30,20);
	boolean isChange;	MouseListener ml;
	String label;	Image i;	Color c;
	ClarityButton(String buttonShow)
	{
		label=buttonShow;
		enableEvents(AWTEvent.MOUSE_EVENT_MASK);
		this.setVisible(true);
		c=new Color(0);	}	
	public void setColor(Color c)	{		this.c=c;	}
	public void setLabel(String label)	{		this.label =label;	}
	public void setFont(Font f)	{		this.f=f;	}	
	@Override
	protected void paintComponent(Graphics g) {
		// TODO 自动生成方法存根
		super.paintComponent(g);
		Graphics2D g2=(Graphics2D) g;
		g2.setColor(Color.white);
		g2.draw3DRect(0, 0, this.getWidth()-1, this.getHeight()-1,!isChange);
		//System.out.print("ccc");
		/*RoundRectangle2D r=new RoundRectangle2D.Double(0, 0, this.getWidth()-1,
				this.getHeight()-1,this.getWidth()/4,this.getHeight()/4);
		g2.draw(r);
		/*FontRenderContext context=g2.getFontRenderContext();
		Rectangle2D bounds=f.getStringBounds(label, context);
		double x=(this.getWidth()-bounds.getWidth())/2;
		double y=(this.getHeight()-bounds.getHeight())/2;
		double baseY=-bounds.getY()+y;
		g2.setColor(c);
		g2.setFont(f);
		g2.drawString(label, (int)x, (int)baseY);*/		
	}
	public void addMouseListener(MouseListener ml)
	{		this.ml=ml;	}	
	protected void processMouseEvent (MouseEvent e)
	{
		if(e.getID()==MouseEvent.MOUSE_ENTERED)
		{			this.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			if(ml!=null)	ml.mouseEntered(e);
		}
		if(e.getID()==MouseEvent.MOUSE_PRESSED) {
			isChange=true;
			this.repaint();
			if(ml!=null)
				ml.mousePressed(e);}
		if(e.getID()==MouseEvent.MOUSE_RELEASED){
			isChange=false;
			this.repaint();
			if(ml!=null)
				ml.mouseReleased(e);	}
		if(e.getID()==MouseEvent.MOUSE_CLICKED){
			if(ml!=null)ml.mouseClicked(e);		}
	}
}