package com.client.qipao;
import java.awt.Graphics;
import java.awt.Insets;

import javax.swing.Icon;
import javax.swing.JTextArea;


/**
 * <code>RTextPane</code> 
 * 
 * @author Jimmy
 * @since v1.0.0 (Oct 15, 2013)
 */
public class RTextPane extends JTextArea{
	
	private static final long serialVersionUID = 1L;
	
	private Icon mBgIcon;
	
	public RTextPane() {
		
		setOpaque(false);
		setLineWrap(true);
		setWrapStyleWord(true);
		
	}
	
	public void setBackgroundIcon(Icon icon) {
		mBgIcon = icon;
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		if (mBgIcon != null) {
			mBgIcon.paintIcon(this, g, 0, 0);
		}
		super.paintComponent(g);
	}
	
	@Override
	public Insets getInsets() {
		return new Insets(10,10,10,10);
	}
	
}
