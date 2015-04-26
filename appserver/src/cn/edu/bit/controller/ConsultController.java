package cn.edu.bit.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import cn.edu.bit.common.FinalTypes;
import cn.edu.bit.common.HelperPager;
import cn.edu.bit.common.HelperString;
import cn.edu.bit.common.Json;
import cn.edu.bit.entity.CONSULT_V;
import cn.edu.bit.entity.Consult;
import cn.edu.bit.entity.TRANSFERTIME;
import cn.edu.bit.service.ICommonService;
import cn.edu.bit.service.IConsultService;
import cn.edu.bit.service.IM_UserService;
import cn.edu.bit.service.ITRANSFERTIMEService;

@Controller
@RequestMapping("/consult")
public class ConsultController {

	@Autowired
	private IConsultService consultService;
	@Autowired
	private ICommonService commonService;
	@Autowired
	private IM_UserService m_UserService;
	@Autowired
	private ITRANSFERTIMEService tRANSFERTIMEService;

	/**
	 * 咨询管理列表
	 * 
	 * @return
	 */
	@RequestMapping(value = "consultList")
	public String consultList() {
		return "consult/consultList";
	}

	/**
	 * 加载咨询主题列表
	 * 
	 * @param request
	 * @param response
	 * @param key
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "loadListData", produces = "plain/text;charset=UTF-8")
	public String loadListData(HttpServletRequest request,
			HttpServletResponse response, String key) {

		HelperPager pager = new HelperPager(request, "CONSULT_V");
		pager.swhere = " C_MID is null";
		if (key != null && !key.equals("")) {
			pager.swhere += " and u_name||'_'||C_TITLE like '%" + key + "%' ";
		}
		
		pager.setSort(" c_createtime desc");
		
		HashMap hm = commonService.getPageList(pager);

		return Json.Encode(hm);
	}

	/**
	 * 新增咨询回复
	 * 
	 * @param request
	 * @param response
	 * @param content
	 * @param title
	 * @param mid
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "submitConsultReply", produces = "plain/text;charset=UTF-8")
	public String submitConsultReply(HttpServletRequest request,
			HttpServletResponse response, String content, String title,
			String mid) {

		Consult consult = new Consult();
		consult.setC_content(content);
		consult.setC_title(title);
		Date now = commonService.getCurrentDBServiceDate();
		consult.setC_createtime(now);
		consult.setC_id(UUID.randomUUID().toString());
		consult.setC_lastupdatetime(now);
		consult.setC_mid(mid);
		consult.setC_readflag(0);
		consult.setC_type(0);
		consult.setStatus(1);
		consult.setT_id("");
		consult.setU_id("999");

		return consultService.add(consult) ? "回复成功." : "回复失败";
		// return consultReplyList(request, response, mid);
	}

	/**
	 * 批量删除咨询主题
	 * 
	 * @param ids
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "batchDeleteConsultData", produces = "plain/text;charset=UTF-8")
	public String batchDeleteConsultData(String ids) {

		String sql = "";

		if (ids != null && ids.trim() != "") {

			ids = HelperString.joinStringSurrWithSep(ids.split(","), ",", "'");
			sql = "delete from CONSULT_V where c_mid in ("+ids+") or c_id in (" + ids + ") ";

			int count = commonService.executeSql(sql);
			return count > 0 ? "成功." : "失败.";

		}

		return "失败.";
	}

	/**
	 * 加载咨询回复详情页面
	 * 
	 * @param request
	 * @param response
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "consultReplyList", produces = "plain/text;charset=UTF-8")
	public ModelAndView consultReplyList(HttpServletRequest request,
			HttpServletResponse response, String id) {

		HashMap<String, Object> replyList = new HashMap<String, Object>();
		String swhere = " C_MID='" + id + "' order by C_CREATETIME ";

		replyList.put("data", commonService.getEntityList("CONSULT_V", swhere));

		CONSULT_V mainConsult = (CONSULT_V) commonService.getEntityList(
				"CONSULT_V", " c_id='" + id + "'").get(0);

		replyList.put("mainConsult", mainConsult);

		return new ModelAndView("consult/consultReplyList", replyList);
	}

	/**
	 * 根据咨询ID获取对该咨询最新的咨询回复. c_Type=1:标示是一个咨询的主题 c_Type=2:标示是用户的追加咨询
	 * c_Type=9：标示是web端对咨询的回复
	 * c_ReadFlag:阅读标示,if(c_Type为1或2){c_ReadFlag=0:未传输到web端
	 * ,c_ReadFlag=1:已传输到web端}else{c_ReadFlag=0:未传输到手机端,c_ReadFlag=1:已传输到手机端}，
	 * 
	 * @param mid
	 *            咨询主题的ID
	 * @param phone_Consults
	 *            手机端新增的针对mid的回复
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "loadNoReadConsultById", produces = "plain/text;charset=UTF-8")
	public String loadNoReadConsultById(String mid, String phone_Consults,
			String uid) {
		try {

			// 将手机端传过来的资讯信息json存入数据库中
			if (!HelperString.isNullOrEmpty(phone_Consults)) {
				JSONArray jsonArr = JSONArray.fromObject(phone_Consults);

				List<Consult> list = new ArrayList<Consult>();
				Date now = commonService.getCurrentDBServiceDate();
				for (Object object : jsonArr) {
					JSONObject jsonObj = (JSONObject) object;
					// String[] dateFormats = new String[]
					// {"yyyy-MM-dd HH:mm:ss"};
					// JSONUtils.getMorpherRegistry().registerMorpher(new
					// DateMorpher(dateFormats));

					Consult c = (Consult) jsonObj
							.toBean(jsonObj, Consult.class);

					c.setC_createtime(now);
					c.setC_lastupdatetime(now);

					consultService.add(c);

				}

			}
			// 根据用户ID和接口名，获取上次同步时间.
			List<TRANSFERTIME> listTime = tRANSFERTIMEService
					.getEntityList(" t_uid='" + uid + "' and t_condition='"
							+ mid + "' and t_interface_name='"
							+ FinalTypes.INTERFACE_LOADNOREADCONSULTBYID + "' ");

			Date now = commonService.getCurrentDBServiceDate();
			Date lastTime = null;

			TRANSFERTIME tt = null;
			Boolean isFirst = true;
			if (listTime != null && listTime.size() > 0) {

				isFirst = false;

				tt = listTime.get(0);

				lastTime = tt.getT_transfertime();
				tt.setT_transfertime(now);

			} else {

				tt = new TRANSFERTIME();
				tt.setT_id(UUID.randomUUID().toString());
				tt.setT_interface_name(FinalTypes.INTERFACE_LOADNOREADCONSULTBYID);
				tt.setT_uid(uid);
				tt.setT_condition(mid);
				tt.setT_transfertime(now);

			}

			// 查询web端上还未传送到手机端的回复
			// status='1' 有效未删除的
			// c_MID='" + mid + "' " :关于某一主题的咨询回复
			String swh = " status='1' and c_MID='" + mid + "' ";
			if (lastTime != null) {
				DateFormat format2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				String reTime = format2.format(lastTime);

				swh += " and c_createtime>to_date('" + reTime
						+ "','yyyy-mm-dd hh24:mi:ss') ";
			}
			swh += " and rownum<50 order by c_createtime";
			// List<Consult> list = consultService.getEntityList(swh, lastTime);

			List<CONSULT_V> list = commonService
					.getEntityList("CONSULT_V", swh);

			// for(int i=1;i<list.size();i++){
			// Consult c = list.get(i);
			// String userid = c.getU_id();
			// m_UserService.getEntity(userid);
			// list.get(i).setU_name(m_UserService.getEntity(userid).getU_name());
			// }
			String jsonStr = JSONArray.fromObject(list).toString();

			if (isFirst) {
				if (tRANSFERTIMEService.add(tt)) {

					return jsonStr;
				} else {
					return "[]";
				}
			} else {
				if (tRANSFERTIMEService.update(tt)) {

					return jsonStr;
				} else {
					return "[]";
				}
			}

		} catch (Exception e) {

			return e.getMessage();
		}
	}

	/**
	 * 获取未同步过的新咨询主题
	 * 
	 * @param lastTime
	 *            最近一次的同步时间，格式：yyyy-MM-dd hh:mm:ss
	 * @param uid
	 *            当前用户ID
	 * @param phone_Consults
	 *            手机端新增的咨询主题.
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "loadConsultList", produces = "plain/text;charset=UTF-8")
	public String loadConsultList(String phone_Consults, String uid) {
		try {
			// 将手机端传过来的资讯信息json存入数据库中
			if (!HelperString.isNullOrEmpty(phone_Consults)) {

				JSONArray jsonArr = JSONArray.fromObject(phone_Consults);

				List<Consult> list = new ArrayList<Consult>();
				Date now = commonService.getCurrentDBServiceDate();

				for (Object object : jsonArr) {
					JSONObject jsonObj = (JSONObject) object;
					// String[] dateFormats = new String[]
					// {"yyyy-MM-dd HH:mm:ss"};
					// JSONUtils.getMorpherRegistry().registerMorpher(new
					// DateMorpher(dateFormats));

					Consult c = (Consult) jsonObj
							.toBean(jsonObj, Consult.class);
					c.setC_createtime(now);
					c.setC_lastupdatetime(now);

					list.add(c);
				}
				if (list.size() > 0) {
					consultService.updateOrSaveEntityList(list);
				}
			}
			// 根据用户ID和接口名，获取上次同步时间.
			List<TRANSFERTIME> listTime = tRANSFERTIMEService
					.getEntityList(" t_uid='" + uid
							+ "' and t_interface_name='"
							+ FinalTypes.INTERFACE_LOADCONSULTLIST + "' ");

			Date now = commonService.getCurrentDBServiceDate();
			Date lastTime = null;

			TRANSFERTIME tt = null;
			Boolean isFirst = true;
			if (listTime != null && listTime.size() > 0) {

				isFirst = false;

				tt = listTime.get(0);

				lastTime = tt.getT_transfertime();

				tt.setT_transfertime(now);

			} else {

				tt = new TRANSFERTIME();
				tt.setT_id(UUID.randomUUID().toString());
				tt.setT_interface_name(FinalTypes.INTERFACE_LOADCONSULTLIST);
				tt.setT_uid(uid);
				tt.setT_transfertime(now);

			}

			// 非用户发送的主题.并且创建时间晚于上次同步时间
			// c_MID is null 主题
			String swhere = " c_mid is null and u_id<>'" + uid + "' ";
			if (lastTime != null) {
				DateFormat format2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				String reTime = format2.format(lastTime);

				swhere += " and c_createtime>to_date('" + reTime
						+ "','yyyy-mm-dd hh24:mi:ss')";
			}

			// List<Consult> list = consultService.getEntityList(swhere,
			// lastTime);
			// for(int i=1;i<list.size();i++){
			// Consult c = list.get(i);
			// String consultid = c.getC_id();
			// String sswhere = " c_mid = '" + consultid+"'";
			// int count = consultService.getEntityList(sswhere).size();
			// list.get(i).setReplyCount(count);
			// }
			List<CONSULT_V> list = commonService.getEntityList("CONSULT_V",
					swhere);

			String jsonStr = JSONArray.fromObject(list).toString();

			if (isFirst) {
				if (tRANSFERTIMEService.add(tt)) {

					return jsonStr;
				} else {
					return "[]";
				}
			} else {
				if (tRANSFERTIMEService.update(tt)) {

					return jsonStr;
				} else {
					return "[]";
				}
			}

		} catch (Exception e) {
			return e.getMessage();
		}
	}
}
