package cn.edu.bit.entity;

import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/*                                
 *咨询信息
 */
@Entity
@Table(name = "consult")
public class Consult {
	/* 咨询信息ID */
	private String c_id;

	/* 咨询信息ID */
	@Id
	public String getC_id() {
		return c_id;
	}

	/* 咨询信息ID */
	public void setC_id(String c_id) {
		this.c_id = c_id;
	}

	/* 如果是回复，可能是管理员的ID */
	private String u_id;

	/* 如果是回复，可能是管理员的ID */
	public String getU_id() {
		return u_id;
	}

	/* 如果是回复，可能是管理员的ID */
	public void setU_id(String u_id) {
		this.u_id = u_id;
	}

	/* 主题:1 用户回复2 管理员回复:9 */
	private int c_type;

	/* 主题:1 用户回复2 管理员回复:9 */
	public int getC_type() {
		return c_type;
	}

	/* 主题:1 用户回复2 管理员回复:9 */
	public void setC_type(int c_type) {
		this.c_type = c_type;
	}

	/* 如果是回复，这里就是回复的主题ID */
	private String c_mid;

	/* 如果是回复，这里就是回复的主题ID */
	public String getC_mid() {
		return c_mid;
	}

	/* 如果是回复，这里就是回复的主题ID */
	public void setC_mid(String c_mid) {
		this.c_mid = c_mid;
	}

	/* 可不填 */
	private String c_title;

	/* 可不填 */
	public String getC_title() {
		return c_title;
	}

	/* 可不填 */
	public void setC_title(String c_title) {
		this.c_title = c_title;
	}

	/* 咨询内容 */
	private String c_content;

	/* 咨询内容 */
	public String getC_content() {
		return c_content;
	}

	/* 咨询内容 */
	public void setC_content(String c_content) {
		this.c_content = c_content;
	}

	/* 检测项，属于哪一类检测的咨询 */
	private String t_id;

	/* 检测项，属于哪一类检测的咨询 */
	public String getT_id() {
		return t_id;
	}

	/* 检测项，属于哪一类检测的咨询 */
	public void setT_id(String t_id) {
		this.t_id = t_id;
	}

	/* 创建时间 */
	private Date c_createtime;

	/* 创建时间 */
	public Date getC_createtime() {
		return c_createtime;
	}

	/* 创建时间 */
	public void setC_createtime(Date c_createtime) {
		this.c_createtime = c_createtime;
	}

	/* 修改时间 */
	private Date c_lastupdatetime;

	/* 修改时间 */
	public Date getC_lastupdatetime() {
		return c_lastupdatetime;
	}

	/* 修改时间 */
	public void setC_lastupdatetime(Date c_lastupdatetime) {
		this.c_lastupdatetime = c_lastupdatetime;
	}

	/* 如果是管理员的回复，这里的标记标示用户是否阅读 */
	private int c_readflag;

	/* 如果是管理员的回复，这里的标记标示用户是否阅读 */
	public int getC_readflag() {
		return c_readflag;
	}

	/* 如果是管理员的回复，这里的标记标示用户是否阅读 */
	public void setC_readflag(int c_readflag) {
		this.c_readflag = c_readflag;
	}

	/* 1有效 0:已删除 */
	private int status;

	/* 1有效 0:已删除 */
	public int getStatus() {
		return status;
	}

	/* 1有效 0:已删除 */
	public void setStatus(int status) {
		this.status = status;
	}
	
	private String u_name;
	
	public void setU_name(String uname){
		this.u_name = uname;
	}
	private int replyCount;
	public void setReplyCount(int count){
		this.replyCount = count;
	}
}
