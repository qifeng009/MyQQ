/**
 * 
 */
package com.client.business.uiManager;

import java.util.Hashtable;

import com.client.data.ClientToServer;
import com.common.UserInfoBean;

/**
 * @author lenovo
 *
 */
public class Register {

	ClientToServer cts =  null;
	
	public Register(){}
	
	public int toRegister(UserInfoBean newUser,int no[],Hashtable<Integer,String> hash){
		cts = new ClientToServer(true);
		new Thread(cts).start();
		return cts.toRegister(newUser,no,hash);
	}
	public void close(){
		cts.closeConnect();
	}
}
