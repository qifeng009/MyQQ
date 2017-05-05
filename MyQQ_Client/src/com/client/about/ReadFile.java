package com.client.about;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class ReadFile {
	public  int line=0;
	public  int getLine(){
	//	ReadFile(fileName);
		return line;
	}
	public  String[] readFile(String fileName){
		//从当前images/file下读取文件
		fileName="file/"+fileName;
		String []string=new String [1024];  //开始创建很多的字符串数组
		File file = new File(fileName); 
		BufferedReader reader = null; 
		try { 
			reader = new BufferedReader(new FileReader(file)); 
			String tempString = null; 

			//一次读入一行，直到读入null为文件结束 
			while ((tempString = reader.readLine()) != null){ 
				//显示行号 
				string[line]=tempString;
			//	System.out.println("line " + line + ": " + string[line]); 
				line++; 
			} 
			reader.close(); 
		} catch (IOException e) { 
			e.printStackTrace(); 
		} finally { 
			if (reader != null){ 
				try { 
					reader.close(); 
				} catch (IOException e1) { 
				} 
			} 
		} 
		//消除多余的字符串数组
		String [] str=new String[line];
		for(int i=0;i<line;i++){
			str[i]=string[i];
		}
		return str;
	}
}
