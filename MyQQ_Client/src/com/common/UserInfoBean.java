/**
 * 
 */
package com.common;

import java.io.Serializable;
import java.util.Date;

/**
 * @author lenovo
 *
 */
public class UserInfoBean implements Serializable{

	private int qq;               //QQ号
	private String pwd;           //密码
	private String sign;          //签名
	private int photoID;          //头像ID
	private String nickname;      //昵称
	private String sex;           //性别
	private String birthday;        //出生日期
	private String constellation; //星座
	private String bloodType;     //血型
	private String diploma;       //学历
	private String telephone;     //电话
	private String email;         //电子邮件
	private String address;       //地址
	
	private boolean status;       //是否登录
	private String  subGroupName; //分组信息
	private String  groupName;    //群信息
	private String  IP;           //IP
	private int     PORT;         //端口
	
	public UserInfoBean(){}
	
	public void setQq(int qq){
		this.qq = qq;
	}
	
	public void setPwd(String pwd){
		this.pwd = pwd;
	}
	
	public void setSign(String sign){
		this.sign = sign;
	}
	
	public void setPhotoID(int photoID){
		this.photoID = photoID;
	}
	
	public void setSex(String sex){
		this.sex = sex;
	}
	
	public void setBirthday(String birthday){
		this.birthday = birthday;
	}
	
	public void setConstellation(String constellation){
		this.constellation = constellation;
	}
	
	public void setBloodType(String bloodType){
		this.bloodType = bloodType;
	}
	
	public void setDiploma(String diploma){
		this.diploma = diploma;
	}
	
	public void setTelephone(String telephone){
		this.telephone = telephone;
	}
	
	public void setEmail(String email){
		this.email = email;
	}
	
	public void setAddress(String address){
		this.address = address;
	}
	
	public void setNickName(String nickName){
		this.nickname = nickName;
	}
	
	public void setStatus(boolean status){
		this.status = status;
	}
	
	public void setSubGroupName(String subGroupName){
		this.subGroupName = subGroupName;
	}
	
	public void setGroupName(String groupName){
		this.groupName = groupName;
	}
	
	public void setIP(String IP){
		this.IP = IP;
	}
	
	public void setPort(int Port){
		this.PORT = Port;
	}
	///////////////////////////////////////////////
	
	public int getQq(){
		return qq;
	}
	
	public String getPwd(){
		return pwd;
	}
	
	public String getSign(){
		return sign;
	}
	
	public int getPhotoID(){
		return photoID;
	}
	
	public String getNickName(){
		return nickname;
	}
	
	public String getSex(){
		return sex;
	}
	
	public String getBirthday(){
		return birthday;
	}
	
	public String getConstellation(){
		return constellation;
	}
	
	public String getBloodType(){
		return bloodType;
	}
	
	public String getDiploma(){
		return diploma;
	}
	
	public String getTelephone(){
		return telephone;
	}
	
	public String getEmail(){
		return email;
	}
	
	public String getAddress(){
		return address;
	}
	
	public boolean getStatus(){
		return status;
	}
	
	public String getSubGroupName(){
		return subGroupName;
	}
	
	public String getGroupName(){
		return groupName;
	}
	
	public String getIP(){
		return IP;
	}
	
	public int getPort(){
		return PORT;
	}
}
