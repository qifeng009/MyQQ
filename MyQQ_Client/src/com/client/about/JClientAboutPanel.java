package com.client.about;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

import com.client.business.resourceManager.ResourceManager;

public class JClientAboutPanel extends JPanel {

	/**
	 * @Fields serialVersionUID:TODO(用一句话描述这个变量表示什么)
	 */
	private static final long serialVersionUID = 1L;
	String[] sfile = {}; // 从文件中读出的字符串数组
	String[] s; // 显示字符串数组
	int i = 0; // 循环变量
	int y = 0; // 文字显示时 向上移动
	int num = 0; // 记录字符串数组 第几个字符串
	int number = 0;// 记录当前打印的文字个数 每增加20个文字 换种颜色
	int[] R = {0,255,128} ;
	int[] G = {255,0,128};
	int[] B = {255,128,0};
	Timer timer = new Timer();
	// public SoundManager music = new SoundManager();
	Color c;
	String bk = "bk";// 背景图片

	/**
	 * @Fields serialVersionUID:TODO(用一句话描述这个变量表示什么)
	 */

	public JClientAboutPanel() {

		 ReadFile rf= new ReadFile(); //
		 sfile=rf.readFile("1.txt"); //读取文件 返回字符串数组 每行一个字符串
		 s= new String[sfile.length]; //创建一个和文件中字符串数组长度一样的字符串数组，为显示是使用
		 for(int j=0;j<sfile.length;j++){
		 s[j]=sfile[j]; //每个字符串数组赋值为空字符
		 }
		timer.schedule(new TimerTask() {
			/*
			 * (non-Javadoc) <p>Title: run</p> <p>Description: </p>
			 * 
			 * @see java.util.TimerTask#run()
			 */
			@Override
			public void run() {
				
				display(); // 每添加一个文字重绘一次 (达到打印文字效果)
				y -= 1;
				System.out.println(y);
				if(y<-300)
				{
					y=200;
				}
				
			}

		}, 100, 10);
		this.setVisible(true);

	}

	/*
	 * (non-Javadoc) <p>Title: paintComponent</p> <p>Description: </p>
	 * 
	 * @param g
	 * 
	 * @see javax.swing.JComponent#paintComponent(java.awt.Graphics)
	 */
	public void paintComponent(Graphics g) {
		drawBackGround(g);
		drawText(g);
	}

	/*
	 * 绘制文字
	 */
	public void drawText(Graphics g) {
		number++;
		int i=number%3;
		

	//	R = 0;
	//	G = 255;
	//	B = 255;
		c = new Color(255, 0, 0);
		g.setFont(new java.awt.Font("楷体", 0, 20));
		for (int j = 0; j < sfile.length; j++) {

			if (s[j].length() != 0) {
				g.setColor(c);
				g.drawString(s[j], 20, (20 + j * 30) + y);
			} else
				continue;

		}
	}

	public void display() {
		this.repaint();
	}

	/*
	 * 绘制背景图片
	 */
	public void drawBackGround(Graphics g) {
		ResourceManager imageResource = new ResourceManager();
		ImageIcon icon = new ImageIcon();
		icon = imageResource.GetImage(bk);
		// TODO:将背景进行循环移动

		// 绘制窗口
		g.drawImage(icon.getImage(), 0, 0, getSize().width, getSize().height,
				this);

	}
}
