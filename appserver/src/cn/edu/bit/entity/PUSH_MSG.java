package cn.edu.bit.entity;

import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**                                
 *注:健康提示信息也可以看作是推送信息，也是需要推送的，健康提示信息的发送规则就是推送规则，推送范围.
 当一条信息被创建后，会根据推送范围和推送规则初始化被推送用户信息到《推送信息阅读记录表》中
 */
@Entity
@Table(name = "PUSH_MSG")
public class PUSH_MSG {
	
	private String p_desc;
	/**
	 * 消息简介
	 * @return
	 */
	public String getP_desc() {
		return p_desc;
	}
	/**
	 * 消息简介
	 * @return
	 */
	public void setP_desc(String p_desc) {
		this.p_desc = p_desc;
	}

	private String p_imgurl;
	/** 走马灯新闻图片路径 */
	public String getP_imgurl() {
		return p_imgurl;
	}
	/** 走马灯新闻图片路径 */
	public void setP_imgurl(String p_imgurl) {
		this.p_imgurl = p_imgurl;
	}

	/** 推送信息ID */
	private String p_id;

	/** 推送信息ID */
	@Id
	public String getP_id() {
		return p_id;
	}

	/** 推送信息ID */
	public void setP_id(String p_id) {
		this.p_id = p_id;
	}

	private int p_type;

	/**1：走马灯新闻   2:列表新闻*/
	public int getP_type() {
		return p_type;
	}
	/**1：走马灯新闻   2:列表新闻*/
	public void setP_type(int p_type) {
		this.p_type = p_type;
	}

	/** 消息标题 */
	private String p_title;

	/** 消息标题 */
	public String getP_title() {
		return p_title;
	}

	/** 消息标题 */
	public void setP_title(String p_title) {
		this.p_title = p_title;
	}

	/** 消息内容 */
	private String p_content;

	/*** 消息内容 */
	public String getP_content() {
		return p_content;
	}

	/** 消息内容 */
	public void setP_content(String p_content) {
		this.p_content = p_content;
	}

	/***
	 * 1：1次 11:一天一次循环推送 71:一周一次循环推送 301:一月一次循环推送
	 */
	private int p_mode;

	/**
	 * 1：1次 11:一天一次循环推送 71:一周一次循环推送 301:一月一次循环推送
	 */
	public int getP_mode() {
		return p_mode;
	}

	/**
	 * 1：1次 11:一天一次循环推送 71:一周一次循环推送 301:一月一次循环推送
	 */
	public void setP_mode(int p_mode) {
		this.p_mode = p_mode;
	}

	/** 这里可能涉及到给用户归类的问题 */
	private String p_range;

	/** 这里可能涉及到给用户归类的问题 */
	public String getP_range() {
		return p_range;
	}

	/** 这里可能涉及到给用户归类的问题 */
	public void setP_range(String p_range) {
		this.p_range = p_range;
	}

	/** 推送范围，选择该信息的接收范围，而推送规则需要再次在推送范围中验证用户是否需要被推送该信息 */
	private String p_inst;

	/** 推送范围，选择该信息的接收范围，而推送规则需要再次在推送范围中验证用户是否需要被推送该信息 */
	public String getP_inst() {
		return p_inst;
	}

	/** 推送范围，选择该信息的接收范围，而推送规则需要再次在推送范围中验证用户是否需要被推送该信息 */
	public void setP_inst(String p_inst) {
		this.p_inst = p_inst;
	}

	/** 开始推送时间，默认为立即 */
	private Date p_pushtime;

	/** 开始推送时间，默认为立即 */
	public Date getP_pushtime() {
		return p_pushtime;
	}

	/** 开始推送时间，默认为立即 */
	public void setP_pushtime(Date p_pushtime) {
		this.p_pushtime = p_pushtime;
	}

	/** 状态 */
	private int status;

	/** 状态 */
	public int getStatus() {
		return status;
	}

	/** 状态 */
	public void setStatus(int status) {
		this.status = status;
	}

	/** 创建时间 */
	private Date p_createtime;

	/** 创建时间 */
	public Date getP_createtime() {
		return p_createtime;
	}

	/** 创建时间 */
	public void setP_createtime(Date p_createtime) {
		this.p_createtime = p_createtime;
	}

	/** 创建人 */
	private String p_creater;

	/** 创建人 */
	public String getP_creater() {
		return p_creater;
	}

	/** 创建人 */
	public void setP_creater(String p_creater) {
		this.p_creater = p_creater;
	}

	/** 最后修改时间 */
	private Date p_lastupdatetime;

	/** 最后修改时间 */
	public Date getP_lastupdatetime() {
		return p_lastupdatetime;
	}

	/** 最后修改时间 */
	public void setP_lastupdatetime(Date p_lastupdatetime) {
		this.p_lastupdatetime = p_lastupdatetime;
	}

	/** 修改人 */
	private String p_lastupdater;

	/** 修改人 */
	public String getP_lastupdater() {
		return p_lastupdater;
	}

	/** 修改人 */
	public void setP_lastupdater(String p_lastupdater) {
		this.p_lastupdater = p_lastupdater;
	}

}
