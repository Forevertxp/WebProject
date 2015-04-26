package cn.edu.bit.controller;

import java.io.File;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import cn.edu.bit.common.FinalTypes;
import cn.edu.bit.common.HelperCryptSafe;
import cn.edu.bit.common.HelperPager;
import cn.edu.bit.common.Json;
import cn.edu.bit.entity.PUSHMSG_READLOG;
import cn.edu.bit.entity.PUSH_MSG;
import cn.edu.bit.entity.TRANSFERTIME;
import cn.edu.bit.service.ICommonService;
import cn.edu.bit.service.IPushMsg_ReadLogService;
import cn.edu.bit.service.IPush_MsgService;
import cn.edu.bit.service.ITRANSFERTIMEService;

@Controller
@RequestMapping("/pushMsg")
public class PushMsgController {

	@Autowired
	private IPush_MsgService push_MsgService;
	@Autowired
	private ICommonService commonService;

	@Autowired
	private ITRANSFERTIMEService tRANSFERTIMEService;

	@Autowired
	private IPushMsg_ReadLogService pushMsg_ReadLogService;

	@RequestMapping("query")
	public String query() {
		return "pushMsg/query";
	}

	@ResponseBody
	@RequestMapping(value = "loadPushMsg", produces = "plain/text;charset=UTF-8")
	public String loadPushMsg(HttpServletRequest request,
			HttpServletResponse response, String key) {

		HelperPager pager = new HelperPager(request, "PUSH_MSG");
		if (key != null && !key.equals("")) {
			pager.swhere += "p_title like '%" + key + "%'";
		}
		pager.setSort(" p_pushtime desc");
		
		
		HashMap hm = commonService.getPageList(pager);

		return Json.Encode(hm);

	}

	@ResponseBody
	@RequestMapping(value = "loadPushMsgInfoById", produces = "plain/text;charset=UTF-8")
	public String loadPushMsgInfoById(String id) {

		return Json.Encode(push_MsgService.getEntity(id));

	}

	@RequestMapping(value = "editPushMsg", produces = "plain/text;charset=UTF-8")
	public String editPushMsg() {

		return "pushMsg/editPushMsg";

	}

	/**
	 * 发布新消息。
	 * 
	 * @param msg
	 * @param imgFile
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "pushNewMsg", produces = "plain/text;charset=UTF-8")
	public ModelAndView pushNewMsg(@ModelAttribute PUSH_MSG msg,
			MultipartFile imgFile, String p_pushtime2,
			HttpServletRequest request) {
		String result = "发布失败：没有找到上传文件.";
		
		boolean saveFlag=true;
		try {

			if (msg != null && imgFile != null
					&& imgFile.getOriginalFilename() != null
					&& imgFile.getOriginalFilename() != "") {

				if (imgFile.getSize() > 1024 * 200) {
					result = "图片不能超过200K";
					saveFlag=false;
				} else {

					if (msg.getP_id() == null || msg.getP_id() == "") {
						msg.setP_id(UUID.randomUUID().toString());
					}
					msg.setP_creater("999");

					SimpleDateFormat formatter = new SimpleDateFormat(
							"yyyy-MM-dd HH:mm");
					ParsePosition pos = new ParsePosition(0);
					Date pushTime = formatter.parse(p_pushtime2, pos);

					msg.setP_pushtime(new java.sql.Date(pushTime.getTime()));

					msg.setP_createtime(new Date());
					msg.setP_lastupdater("999");
					msg.setP_lastupdatetime(new Date());
					msg.setStatus(1);
					msg.setP_imgurl(uploadImg(imgFile, msg.getP_id(), request));
					if(push_MsgService.add(msg) ){
						result="发布成功.";
						saveFlag=true;
					}else{
						result="发布失败.";
						saveFlag=false;
					}
				}
			} else {
				result = "请为信息指定一个图片.";
				saveFlag=false;
			}
		} catch (Exception e) {
			result = e.getMessage();
			saveFlag=false;
		}
		if(saveFlag){
			ModelAndView view = new ModelAndView("pushMsg/result");
			return view.addObject("uploadResult", result);
		}else{
			ModelAndView view = new ModelAndView("pushMsg/editPushMsg");
			view.addObject("msg", msg);
			view.addObject("uploadResult", result);
			return view;
		}
		
	}

	public String uploadImg(MultipartFile imgFile, String msgid,
			HttpServletRequest request) throws Exception {

		String path = request.getSession().getServletContext()
				.getRealPath("/images");

		/* 从当时时间MD5强制重命名图片 */

		String pic_time = new SimpleDateFormat("yyyyMMddHHmmss")
				.format(new Date())
				+ String.valueOf(new Random().nextInt(1000));

		String newName = pic_time;// HelperCryptSafe.encryptToMD5(pic_time);//
									// msgid;

		String fileName = imgFile.getOriginalFilename();
		newName = newName + fileName.substring(fileName.indexOf("."));
		File targetFile = new File(path, newName);

		if (!targetFile.exists()) {
			targetFile.mkdirs();
		}
		// 保存
		imgFile.transferTo(targetFile);
		return newName;
	}

	@ResponseBody
	@RequestMapping(value = "saveData", produces = "plain/text;charset=UTF-8")
	public String saveData(String data) {

		JSONArray array = JSONArray.fromObject(data);

		for (Object object : array) {
			JSONObject jsonObj = (JSONObject) object;
			String active = jsonObj.get("_state").toString().toLowerCase();

			Date now = commonService.getCurrentDBServiceDate();
			if (active.equals("modified")) {
				PUSH_MSG msg = (PUSH_MSG) jsonObj.toBean(jsonObj,
						PUSH_MSG.class);
				msg.setP_lastupdater("999");
				msg.setP_lastupdatetime(now);
				push_MsgService.update(msg);
			} else if (active.equals("removed")) {

				push_MsgService.delete(((PUSH_MSG) jsonObj.toBean(jsonObj,
						PUSH_MSG.class)).getP_id());

			} else {
				PUSH_MSG msg = (PUSH_MSG) jsonObj.toBean(jsonObj,
						PUSH_MSG.class);
				msg.setP_lastupdater("999");
				msg.setP_lastupdatetime(now);
				msg.setP_creater("999");
				msg.setP_createtime(now);
				push_MsgService.add((PUSH_MSG) jsonObj.toBean(jsonObj,
						PUSH_MSG.class));
			}
		}
		return "修改成功.";
	}

	@RequestMapping(value = "readInfoList")
	public String readInfoList(HttpServletRequest request,
			HttpServletResponse response, String key) {

		return "pushMsg/readInfoList";

	}

	@RequestMapping(value = "loadListData", produces = "plain/text;charset=UTF-8")
	@ResponseBody
	public String loadListData(HttpServletRequest request,
			HttpServletResponse response, String key) {

		HelperPager pager = new HelperPager(request, "PUSHMSG_READLOG_V");

		if (key != null && !key.equals("")) {
			pager.swhere += " p_title like '%" + key + "%'";
		}
		
		pager.setSort(" r_time desc");
		
		
		HashMap hm = commonService.getPageList(pager);

		return Json.Encode(hm);

	}

	/**
	 * 从服务器获取客户应该阅读的信息 走马灯新闻图片路径 p_imgurl
	 * 
	 * @param request
	 * @param response
	 * @param userId
	 *            用户ID
	 * @param lastTime
	 *            上次接收时间
	 * @return 阅读信息Json串。
	 */
	@ResponseBody
	@RequestMapping(value = "loadPushMsgFromService", produces = "plain/text; charset=UTF-8")
	public String loadPushMsgFromService(String uid) {

		try {
			// 根据用户ID和接口名，获取上次同步时间.
			List<TRANSFERTIME> listTime = tRANSFERTIMEService
					.getEntityList(" t_uid='" + uid
							+ "' and t_interface_name='"
							+ FinalTypes.INTERFACE_LOADPUSHMSGFROMSERVICE
							+ "' ");

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
				tt.setT_interface_name(FinalTypes.INTERFACE_LOADPUSHMSGFROMSERVICE);
				tt.setT_uid(uid);
				tt.setT_transfertime(now);

			}
			String swhere = " P_createtime>:lastTranTime";
			if (lastTime == null) {
				swhere = "";
			}

			swhere += "  and rownum<50 order by P_createtime";
			List<PUSH_MSG> list = push_MsgService.getEntityList(swhere,
					lastTime);

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
	 * 将推送消息的阅读记录发送给服务器。
	 * 
	 * @param uid
	 * @param pid
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "giveReadLogToService", produces = "plain/text; charset=UTF-8")
	public String giveReadLogToService(String uid, String pid) {

		try {

			List<PUSHMSG_READLOG> list = pushMsg_ReadLogService
					.getEntityList(" u_id='" + uid + "' and p_id='" + pid + "'");
			PUSHMSG_READLOG readLog = null;
			if (list != null && list.size() > 0) {

				readLog = list.get(0);
				readLog.setR_flg(readLog.getR_flg() + 1);

				commonService
						.executeSql(" update PUSHMSG_READLOG set R_flg=nvl(R_flg,0)+1 where u_id='"
								+ uid + "' and p_id='" + pid + "'");
				return "success";

			} else {
				readLog = new PUSHMSG_READLOG();
				readLog.setR_id(UUID.randomUUID().toString());
				readLog.setR_createtime(new Date());
				readLog.setP_id(pid);
				readLog.setR_time(new Date());
				readLog.setStatus(1);
				readLog.setR_flg(1);
				readLog.setU_id(uid);
				pushMsg_ReadLogService.add(readLog);
				return "success";
			}
		} catch (Exception e) {
			// TODO: handle exception
			return e.getMessage();
		}

	}

	@ResponseBody
	@RequestMapping(value = "saveReadLog", produces = "plain/text;charset=UTF-8")
	public String saveReadLog(String data) {

		try {

			JSONArray array = JSONArray.fromObject(data);

			int totalCount = 0;
			for (Object object : array) {
				JSONObject jsonObj = (JSONObject) object;
				String active = jsonObj.get("_state").toString().toLowerCase();

				if (active.equals("modified")) {
					pushMsg_ReadLogService.update((PUSHMSG_READLOG) jsonObj
							.toBean(jsonObj, PUSHMSG_READLOG.class));
				} else if (active.equals("removed")) {

					int rowCount = commonService
							.executeSql("delete from PUSHMSG_READLOG where r_id='"
									+ ((PUSHMSG_READLOG) jsonObj.toBean(
											jsonObj, PUSHMSG_READLOG.class))
											.getR_id() + "'");

					totalCount += rowCount;

				} else {
					pushMsg_ReadLogService.add((PUSHMSG_READLOG) jsonObj
							.toBean(jsonObj, PUSHMSG_READLOG.class));
				}

			}
			return totalCount > 0 ? "操作成功." : "操作失败";

		} catch (Exception e) {
			// TODO: handle exception
			return e.getMessage();
		}
	}

	@RequestMapping(value = "filetest")
	public String filetest() {

		return "pushMsg/filetest";
	}

	@ResponseBody
	@RequestMapping(value = "uploadMsgImg", method = RequestMethod.POST)
	public String uploadMsgImg(MultipartFile imgFile, String msgid,
			HttpServletRequest request) {

		String path = request.getSession().getServletContext()
				.getRealPath("/images");

		/* 从当时时间MD5强制重命名图片 */

		String pic_time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
				.format(new Date()) + String.valueOf(Math.random());

		String newName = HelperCryptSafe.encryptToMD5(pic_time);

		String fileName = imgFile.getOriginalFilename();
		newName = newName + fileName.substring(fileName.indexOf("."));
		File targetFile = new File(path, newName);

		if (!targetFile.exists()) {
			targetFile.mkdirs();
		}

		// 保存
		try {
			imgFile.transferTo(targetFile);

			PUSH_MSG msg = push_MsgService.getEntity(msgid);
			msg.setP_imgurl(newName);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return newName;

	}

	@ResponseBody
	@RequestMapping(value = "getComboxType", produces = "plain/text; charset=UTF-8")
	public String getComboxType(String type) {
		String str = "";
		if (type.equals("p_type")) {
			str = "[{'type_id':'1','type_text':'走马灯新闻 '},{'type_id':'2','type_text':'列表新闻'}]";
		} else if (type.equals("p_mode")) {
			str = "[{'mode_id':'1','mode_text':'一次'},{'mode_id':'11','mode_text':'一天一次循环推送'},"
					+ "{'mode_id':'71','mode_text':'一周一次循环推送'},{'mode_id':'301','mode_text':'一月一次循环推送'}]";
		} else if (type.equals("p_range")) {
			str = "[{'range_id':'0','range_text':'全部用户'},{'range_id':'9','range_text':'VIP用户'},"
					+ "{'range_id':'1','range_text':'普通用户'},{'range_id':'2','range_text':'未注册用户'}]";
		}

		return str;// JSONArray.fromObject(str).toString();
	}
}
