package cn.edu.bit.entity;
                                
import java.util.Date;          
import javax.persistence.Entity;
import javax.persistence.Id;    
import javax.persistence.Table; 
                                
/*                                
 * 推送信息阅读记录表
 */                               
@Entity                         
@Table(name = "PUSHMSG_READLOG")
public class PUSHMSG_READLOG {  
	/*阅读记录*/
	private String r_id;
	/*阅读记录*/
	@Id
	public String getR_id() {              
		return r_id;                       
	}                                      
	/*阅读记录*/
	public void setR_id(String r_id) {     
		this.r_id = r_id;                  
	}                                      
                                        
	/*推送消息ID*/
	private String p_id;
	/*推送消息ID*/
	public String getP_id() {              
		return p_id;                       
	}                                      
	/*推送消息ID*/
	public void setP_id(String p_id) {     
		this.p_id = p_id;                  
	}                                      
                                        
	/*用户消息*/
	private String u_id;
	/*用户消息*/
	public String getU_id() {              
		return u_id;                       
	}                                      
	/*用户消息*/
	public void setU_id(String u_id) {     
		this.u_id = u_id;                  
	}                                      
                                        
	/*阅读标记*/
	private int r_flg;
	/*阅读标记*/
	public int getR_flg() {              
		return r_flg;                       
	}                                      
	/*阅读标记*/
	public void setR_flg(int r_flg) {     
		this.r_flg = r_flg;                  
	}                                      
                                        
	/*阅读时间*/
	private Date r_time;
	/*阅读时间*/
	public Date getR_time() {              
		return r_time;                       
	}                                      
	/*阅读时间*/
	public void setR_time(Date r_time) {     
		this.r_time = r_time;                  
	}                                      
                                        
	/*创建时间*/
	private Date r_createtime;
	/*创建时间*/
	public Date getR_createtime() {              
		return r_createtime;                       
	}                                      
	/*创建时间*/
	public void setR_createtime(Date r_createtime) {     
		this.r_createtime = r_createtime;                  
	}                                      
                                        

	private int status;

	public int getStatus() {              
		return status;                       
	}                                      

	public void setStatus(int status) {     
		this.status = status;                  
	}                                      
                                        
}
