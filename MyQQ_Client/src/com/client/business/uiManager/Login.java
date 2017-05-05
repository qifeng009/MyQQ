/**
 * 
 */
package com.client.business.uiManager;

import java.io.IOException;

import com.client.data.ClientToServer;
import com.common.User;
import com.client.data.ReadData;
import com.client.data.WriteData;

/**
 * @author lenovo
 *
 */
public class Login {
	
	ClientToServer cts;
	
	public Login(){}
	
	public Login(ClientToServer cts){
		this.cts = cts;
	}
	public boolean checkUser(User u) throws Exception{
		return cts.sendLoginInfoToServer(u);
	}
	public void userLogout() throws Exception{
		cts.logout();
	}
	
	public void remember(String user,String pwd){
		WriteData wd = new WriteData();
		String admin[] = new String[2];
		admin[0] = user;
		admin[1] = pwd;
		
		wd.writeData(admin,2);
 	}
	
	public boolean isRemember(){
		ReadData rd = new ReadData();
		String []admin = new String[2];
		rd.readData(admin, 2);
		
		if(admin[0].equals("")){
			return false;
		}else{
			return true;
		}
	}

	public void ReadUserInfo(String user[]) {

		ReadData rd = new ReadData();
		rd.readData(user, 2);

	}
}
