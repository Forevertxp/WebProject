package cn.edu.bit.entity;
                                
import java.util.Date;          
import javax.persistence.Entity;
import javax.persistence.Id;    
import javax.persistence.Table; 
                                
@Entity                         
@Table(name = "CONSULT_V")
public class CONSULT_V {  

	private String u_name;
	private String u_phone;
	public String getU_name() {
		return u_name;
	}

	public void setU_name(String u_name) {
		this.u_name = u_name;
	}

	public String getU_phone() {
		return u_phone;
	}

	public void setU_phone(String u_phone) {
		this.u_phone = u_phone;
	}


	private String c_id;

	@Id
	public String getC_id() {              
		return c_id;                       
	}                                      

	public void setC_id(String c_id) {     
		this.c_id = c_id;                  
	}                                      
                                        

	private String u_id;

	public String getU_id() {              
		return u_id;                       
	}                                      

	public void setU_id(String u_id) {     
		this.u_id = u_id;                  
	}                                      
                                        

	private Integer c_type;

	public Integer getC_type() {              
		return c_type;                       
	}                                      

	public void setC_type(Integer c_type) {     
		this.c_type = c_type;                  
	}                                      
                                        

	private String c_mid;

	public String getC_mid() {              
		return c_mid;                       
	}                                      

	public void setC_mid(String c_mid) {     
		this.c_mid = c_mid;                  
	}                                      
                                        

	private String c_title;

	public String getC_title() {              
		return c_title;                       
	}                                      

	public void setC_title(String c_title) {     
		this.c_title = c_title;                  
	}                                      
                                        

	private String c_content;

	public String getC_content() {              
		return c_content;                       
	}                                      

	public void setC_content(String c_content) {     
		this.c_content = c_content;                  
	}                                      
                                        

	private String t_id;

	public String getT_id() {              
		return t_id;                       
	}                                      

	public void setT_id(String t_id) {     
		this.t_id = t_id;                  
	}                                      
                                        

	private Date c_createtime;

	public Date getC_createtime() {              
		return c_createtime;                       
	}                                      

	public void setC_createtime(Date c_createtime) {     
		this.c_createtime = c_createtime;                  
	}                                      
                                        

	private Date c_lastupdatetime;

	public Date getC_lastupdatetime() {              
		return c_lastupdatetime;                       
	}                                      

	public void setC_lastupdatetime(Date c_lastupdatetime) {     
		this.c_lastupdatetime = c_lastupdatetime;                  
	}                                      
                                        

	private Integer c_readflag;

	public Integer getC_readflag() {              
		return c_readflag;                       
	}                                      

	public void setC_readflag(Integer c_readflag) {     
		this.c_readflag = c_readflag;                  
	}                                      
                                        

	private Integer status;

	public Integer getStatus() {              
		return status;                       
	}                                      

	public void setStatus(Integer status) {     
		this.status = status;                  
	}                                      
                                        

	private Integer replycount;

	public Integer getReplycount() {              
		return replycount;                       
	}                                      

	public void setReplycount(Integer replycount) {     
		this.replycount = replycount;                  
	}                                      
                                        

	private Date lastreplytime;

	public Date getLastreplytime() {              
		return lastreplytime;                       
	}                                      

	public void setLastreplytime(Date lastreplytime) {     
		this.lastreplytime = lastreplytime;                  
	}                                      
                                        

	private String mid;

	public String getMid() {              
		return mid;                       
	}                                      

	public void setMid(String mid) {     
		this.mid = mid;                  
	}                                      
                                        
}
