package cn.edu.bit.entity;
                                
import java.util.Date;          
import javax.persistence.Entity;
import javax.persistence.Id;    
import javax.persistence.Table; 
                                
@Entity                         
@Table(name = "PUSHMSG_READLOG_V")
public class PUSHMSG_READLOG_V {  

	private String r_id;

	@Id
	public String getR_id() {              
		return r_id;                       
	}                                      

	public void setR_id(String r_id) {     
		this.r_id = r_id;                  
	}                                      
                                        

	private String p_id;

	public String getP_id() {              
		return p_id;                       
	}                                      

	public void setP_id(String p_id) {     
		this.p_id = p_id;                  
	}                                      
                                        

	private String u_id;

	public String getU_id() {              
		return u_id;                       
	}                                      

	public void setU_id(String u_id) {     
		this.u_id = u_id;                  
	}                                      
                                        

	private Integer r_flg;

	public Integer getR_flg() {              
		return r_flg;                       
	}                                      

	public void setR_flg(Integer r_flg) {     
		this.r_flg = r_flg;                  
	}                                      
                                        

	private Date r_time;

	public Date getR_time() {              
		return r_time;                       
	}                                      

	public void setR_time(Date r_time) {     
		this.r_time = r_time;                  
	}                                      
                                        

	private Date r_createtime;

	public Date getR_createtime() {              
		return r_createtime;                       
	}                                      

	public void setR_createtime(Date r_createtime) {     
		this.r_createtime = r_createtime;                  
	}                                      
                                        

	private Integer status;

	public Integer getStatus() {              
		return status;                       
	}                                      

	public void setStatus(Integer status) {     
		this.status = status;                  
	}                                      
                                        

	private String u_name;

	public String getU_name() {              
		return u_name;                       
	}                                      

	public void setU_name(String u_name) {     
		this.u_name = u_name;                  
	}                                      
                                        

	private String p_title;

	public String getP_title() {              
		return p_title;                       
	}                                      

	public void setP_title(String p_title) {     
		this.p_title = p_title;                  
	}                                      
                                        

	private Date p_pushtime;

	public Date getP_pushtime() {              
		return p_pushtime;                       
	}                                      

	public void setP_pushtime(Date p_pushtime) {     
		this.p_pushtime = p_pushtime;                  
	}                                      
                                        
}
