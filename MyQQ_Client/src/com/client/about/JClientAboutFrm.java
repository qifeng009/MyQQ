package com.client.about;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Timer;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class JClientAboutFrm extends JFrame {
	String[] sfile;
	String[] s;
	int i = 0;
	int y = 0;
	int num = 0;
	int number = 0;
	float R = 0.3f, G = 0.4f, B = 0.1f;
	Timer timer = new Timer();
	JClientAboutPanel gdpanel = new JClientAboutPanel();
	/**
	 * @Fields serialVersionUID:TODO(用一句话描述这个变量表示什么)
	 */
	private static final long serialVersionUID = 1L;

	public JClientAboutFrm() {
		// setTitle("游戏介绍");
		this.setBounds(20, 20, 400, 250);
		// 将Key事件同时添加给JPanel和JTextFieil组件
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.add(gdpanel);
		this.setVisible(true);
	}

	public static void main(String[] agrs) {
		new JClientAboutFrm();
	}

	public void CloseWindow() {
		this.dispose();
	}

}
