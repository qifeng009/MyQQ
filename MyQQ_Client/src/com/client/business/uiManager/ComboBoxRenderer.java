package com.client.business.uiManager;

import java.awt.Component;
import java.util.Map;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

public class ComboBoxRenderer extends JLabel implements ListCellRenderer{

	/**
	 * @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么)
	 */
	public static final long serialVersionUID = 1L;
	public Map<String,ImageIcon> content;
	public ComboBoxRenderer(Map<String,ImageIcon> content){
		this.content=content;
		setOpaque(false);
		this.setHorizontalAlignment(CENTER);
		this.setVerticalAlignment(CENTER);
	}
	
	
	@Override
	public Component getListCellRendererComponent(JList list, Object value,
			int index, boolean isSelected, boolean cellHasFocus) {
		// TODO Auto-generated method stub
		String key=(String)value;
		if(isSelected){
			this.setBackground(list.getSelectionBackground());
			this.setForeground(list.getSelectionForeground());
			
		}else{
			this.setBackground(list.getBackground());
			this.setForeground(list.getForeground());
		}
		setText(key);
		setIcon(content.get(key));
		setFont(list.getFont());
		
		return this;
	}

}
