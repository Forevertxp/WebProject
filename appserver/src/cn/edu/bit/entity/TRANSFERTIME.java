package cn.edu.bit.entity;
                                
import java.util.Date;          
import javax.persistence.Entity;
import javax.persistence.Id;    
import javax.persistence.Table; 
                                
@Entity                         
@Table(name = "TRANSFERTIME")
public class TRANSFERTIME {  

	private String t_id;

	@Id
	public String getT_id() {              
		return t_id;                       
	}                                      

	public void setT_id(String t_id) {     
		this.t_id = t_id;                  
	}                                      
                
	private String t_condition;

	public String getT_condition() {
		return t_condition;
	}

	public void setT_condition(String t_condition) {
		this.t_condition = t_condition;
	}

	private String t_uid;

	public String getT_uid() {              
		return t_uid;                       
	}                                      

	public void setT_uid(String t_uid) {     
		this.t_uid = t_uid;                  
	}                                      
                                        

	private String t_interface_name;

	public String getT_interface_name() {              
		return t_interface_name;                       
	}                                      

	public void setT_interface_name(String t_interface_name) {     
		this.t_interface_name = t_interface_name;                  
	}                                      
                                        

	private Date t_transfertime;

	public Date getT_transfertime() {              
		return t_transfertime;                       
	}                                      

	public void setT_transfertime(Date t_transfertime) {     
		this.t_transfertime = t_transfertime;                  
	}                                      
                                        
}
