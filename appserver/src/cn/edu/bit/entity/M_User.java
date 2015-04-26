package cn.edu.bit.entity;
                                
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
                                
/*                                
 *数据库数据有效性约定：1、如果有status字段，数字0均代表删除 ，数字1代表有效 ，其它数字需着重标示
 */                               
@Entity                         
@Table(name = "m_User")
public class M_User {  
	
	private String u_password;
	public String getU_password() {
		return u_password;
	}
	public void setU_password(String u_password) {
		this.u_password = u_password;
	}

	/*系统生成GUID/UUID*/
	private String u_id;
	/*系统生成GUID/UUID*/
	@Id
	public String getU_id() {              
		return u_id;                       
	}                                      
	/*系统生成GUID/UUID*/
	public void setU_id(String u_id) {     
		this.u_id = u_id;                  
	}                                      
                                        
	/*相同IMEI码的用户表示在同一手机上。*/
	private String u_phoneimei;
	/*相同IMEI码的用户表示在同一手机上。*/
	public String getU_phoneimei() {              
		return u_phoneimei;                       
	}                                      
	/*相同IMEI码的用户表示在同一手机上。*/
	public void setU_phoneimei(String u_phoneimei) {     
		this.u_phoneimei = u_phoneimei;                  
	}                                      
                                        
	/*可选（如果有关注关系，必填）*/
	private String u_phone;
	/*可选（如果有关注关系，必填）*/
	public String getU_phone() {              
		return u_phone;                       
	}                                      
	/*可选（如果有关注关系，必填）*/
	public void setU_phone(String u_phone) {     
		this.u_phone = u_phone;                  
	}                                      
                                        
	/*必填*/
	private String u_name;
	/*必填*/
	public String getU_name() {              
		return u_name;                       
	}                                      
	/*必填*/
	public void setU_name(String u_name) {     
		this.u_name = u_name;                  
	}                                      
                                        
	/*必填*/
	private int u_sex;
	/*必填*/
	public int getU_sex() {              
		return u_sex;                       
	}                                      
	/*必填*/
	public void setU_sex(int u_sex) {     
		this.u_sex = u_sex;                  
	}                                      
                                        
	/*必填*/
	private Date u_birthdate;
	/*必填*/
	public Date getU_birthdate() {              
		return u_birthdate;                       
	}                                      
	/*必填*/
	public void setU_birthdate(Date u_birthdate) {     
		this.u_birthdate = u_birthdate;      
	}                                      
                                        
	/*可选*/
	private String u_profession;
	/*可选*/
	public String getU_profession() {              
		return u_profession;                       
	}                                      
	/*可选*/
	public void setU_profession(String u_profession) {     
		this.u_profession = u_profession;                  
	}                                      
                                        
	/*推送信息时可能用到*/
	private String u_type;
	/*推送信息时可能用到*/
	public String getU_type() {              
		return u_type;                       
	}                                      
	/*推送信息时可能用到*/
	public void setU_type(String u_type) {     
		this.u_type = u_type;                  
	}                                      
                                        
	/*注册日期*/
	private Date u_regtime;
	/*注册日期*/
	public Date getU_regtime() {              
		return u_regtime;                       
	}                                      
	/*注册日期*/
	public void setU_regtime(Date u_regtime) {     
		this.u_regtime = u_regtime;                  
	}                                      
                                        
	/*-1：未注册 0：删除 1：正常 2：禁用*/
	private int status;
	/*-1：未注册 0：删除 1：正常 2：禁用*/
	public int getStatus() {              
		return status;                       
	}                                      
	/*-1：未注册 0：删除 1：正常 2：禁用*/
	public void setStatus(int status) {     
		this.status = status;                  
	}                                      
                                        
	/*0：未与数据库同步，同步时执行insert操作  1：已经与数据库同步，同步时执行update操作*/
	private int u_istrans;
	/*0：未与数据库同步，同步时执行insert操作  1：已经与数据库同步，同步时执行update操作*/
	public int getU_istrans() {              
		return u_istrans;                       
	}                                      
	/*0：未与数据库同步，同步时执行insert操作  1：已经与数据库同步，同步时执行update操作*/
	public void setU_istrans(int u_istrans) {     
		this.u_istrans = u_istrans;                  
	}                                      
                                        
	/*可选，如果填写切换为当前用户时提示输入密码*/
	private int u_privatepassword;
	/*可选，如果填写切换为当前用户时提示输入密码*/
	public int getU_privatepassword() {              
		return u_privatepassword;                       
	}                                      
	/*可选，如果填写切换为当前用户时提示输入密码*/
	public void setU_privatepassword(int u_privatepassword) {     
		this.u_privatepassword = u_privatepassword;                  
	}                                      
                                        
	/*最后修改时间*/
	private Date u_lastupdatetime;
	/*最后修改时间*/
	public Date getU_lastupdatetime() {              
		return u_lastupdatetime;                       
	}                                      
	/*最后修改时间*/
	public void setU_lastupdatetime(Date u_lastupdatetime) {     
		this.u_lastupdatetime = u_lastupdatetime;                  
	}                                      
                                        
}
