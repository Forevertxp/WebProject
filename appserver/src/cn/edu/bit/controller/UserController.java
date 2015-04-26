package cn.edu.bit.controller;

import java.io.FileOutputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.ezmorph.object.DateMorpher;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.util.JSONUtils;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.edu.bit.common.HelperCryptSafe;
import cn.edu.bit.common.HelperPager;
import cn.edu.bit.common.Json;
import cn.edu.bit.entity.M_User;
import cn.edu.bit.service.ICommonService;
import cn.edu.bit.service.IM_UserService;

@Controller
@RequestMapping("/user")
public class UserController {

	// 记录日志
	Logger logger = Logger.getLogger(this.getClass());

	@Autowired
	private IM_UserService m_UserService;

	@Autowired
	private ICommonService commonService;

	@RequestMapping("userList")
	public String userList() {
		return "user/userList";
	}

	@RequestMapping("query")
	public String query() {
		return "user/query";
	}

	/**
	 * 用户登录.
	 * 
	 * @param request
	 * @param u_name
	 * @param u_password
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "validateLoginUser", produces = "plain/text;charset=UTF-8")
	public String validateLoginUser(HttpServletRequest request, String u_name,
			String u_password) {
		u_password = HelperCryptSafe.encryptToMD5(u_password);
		String swhere = " u_name='" + u_name + "' and u_password='"
				+ u_password + "'";
		List<M_User> list = m_UserService.getEntityList(swhere);

		if (list != null && list.size() == 1) {

			request.getSession().setAttribute("LoginUser", list.get(0));
			return "1";
		}
		return "0";
	}
	
	@ResponseBody
	@RequestMapping(value = "userLogout", produces = "plain/text;charset=UTF-8")
	public String userLogout(HttpServletRequest request) {
		try {
			request.getSession().invalidate();
			return "1";
		} catch (Exception e) {
			return e.getMessage();
		}
	}

	@ResponseBody
	@RequestMapping(value = "saveUser", produces = "plain/text;charset=UTF-8")
	public String saveUser(String data) {

		logger.info(data);

		try {

			JSONArray array = JSONArray.fromObject(data);
			int totalCount = 0;
			for (Object object : array) {
				JSONObject jsonObj = (JSONObject) object;
				String active = jsonObj.get("_state").toString().toLowerCase();

				if (active.equals("modified")) {
					m_UserService.update((M_User) jsonObj.toBean(jsonObj,
							M_User.class));
				} else if (active.equals("removed")) {

					m_UserService.delete(((M_User) jsonObj.toBean(jsonObj,
							M_User.class)).getU_id());

					int rowCount = commonService
							.executeSql("delete from M_User where u_id='"
									+ ((M_User) jsonObj.toBean(jsonObj,
											M_User.class)).getU_id() + "'");

					totalCount += rowCount;

				} else {
					m_UserService.add((M_User) jsonObj.toBean(jsonObj,
							M_User.class));
				}
			}
			return totalCount > 0 ? "操作成功." : "操作失败";
		} catch (Exception e) {
			return e.getMessage();
		}
	}

	@RequestMapping(value = "loadUserList", produces = "plain/text;charset=UTF-8")
	@ResponseBody
	public String loadUserList(HttpServletRequest request,
			HttpServletResponse response) {

		HelperPager pager = new HelperPager(request, "M_User");

		logger.info("调用userList");
		String userid = request.getParameter("userid");
		String userName = request.getParameter("userName");
		String userPhone = request.getParameter("userPhone");

		pager.swhere += " u_id<>'999' ";
		String paramStr = "";
		if (userid != null && !userid.equals("")) {
			paramStr += " u_id like '%" + userid + "%'";
		}
		if (userName != null && !userName.equals("")) {
			if (!paramStr.equals("")) {
				paramStr += " or u_name like '%" + userName + "%'";
			} else {
				paramStr += " u_name like '%" + userName + "%'";
			}

		}
		if (userPhone != null && !userPhone.equals("")) {
			if (!paramStr.equals("")) {
				paramStr += " or u_phone like '%" + userPhone + "%'";
			} else {
				paramStr += " u_phone like '%" + userPhone + "%'";
			}
		}

		if (!paramStr.equals("")) {
			pager.swhere = pager.swhere + " and( " + paramStr + " )";
		}
		String updatetime = request.getParameter("updatetime");
		if (updatetime != null && !updatetime.equals("")) {
			String updatetimestart = updatetime + " 00:00:00";
			String updatetimeend = updatetime + " 23:59:59";
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:SS");
			try {
				Date starttime = sdf.parse(updatetimestart);
				Date endtime = sdf.parse(updatetimeend);

				Date[] params = { starttime, endtime };
				pager.setParams(params);
				pager.swhere += " and u_lastupdatetime  between ? and ? ";
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		HashMap users = m_UserService.getPageList(pager);

		return Json.Encode(users);

	}

	@RequestMapping(value = "exportUserList", produces = "plain/text;charset=UTF-8")
	@ResponseBody
	public String exportUserList(String data, String userid, String userName,
			String userPhone) {

		JSONArray usersArray = JSONArray.fromObject(data);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:SS");
		HSSFWorkbook wb = new HSSFWorkbook();
		HSSFSheet sheet = wb.createSheet("用户信息");
		// 设置excel每列宽度
		sheet.setColumnWidth(0, 4000);
		sheet.setColumnWidth(1, 3500);

		// 创建字体样式
		HSSFFont font = wb.createFont();
		font.setFontName("Verdana");
		font.setBoldweight((short) 100);
		font.setFontHeight((short) 300);
		font.setColor(HSSFColor.BLUE.index);

		// 创建单元格样式
		HSSFCellStyle style = wb.createCellStyle();
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		style.setFillForegroundColor(HSSFColor.LIGHT_TURQUOISE.index);
		style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);

		// 设置边框
		style.setBottomBorderColor(HSSFColor.RED.index);
		style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		style.setBorderRight(HSSFCellStyle.BORDER_THIN);
		style.setBorderTop(HSSFCellStyle.BORDER_THIN);

		style.setFont(font);// 设置字体

		// 创建Excel的sheet的一行
		HSSFRow head = sheet.createRow(0);
		head.setHeight((short) 500);// 设定行的高度

		// 画Excel列头
		Class<?> cla = M_User.class;

		Field[] fs = cla.getDeclaredFields();

		HSSFCell cell;
		for (int i = 0; i < fs.length; i++) {
			Field field = fs[i];
			cell = head.createCell(i);
			cell.setCellStyle(style);
			cell.setCellType(HSSFCell.CELL_TYPE_STRING);
			cell.setCellValue(field.getName());

		}
		// 填充内容
		Method method;
		HSSFRow row;
		if (usersArray.size() == 0) {

			String swhere = " u_id<>'999' ";

			String paramStr = "";
			if (userid != null && !userid.equals("")) {
				paramStr += " u_id like '%" + userid + "%'";
			}
			if (userName != null && !userName.equals("")) {
				if (!paramStr.equals("")) {
					paramStr += " or u_name like '%" + userName + "%'";
				} else {
					paramStr += " u_name like '%" + userName + "%'";
				}

			}
			if (userPhone != null && !userPhone.equals("")) {
				if (!paramStr.equals("")) {
					paramStr += " or u_phone like '%" + userPhone + "%'";
				} else {
					paramStr += " u_phone like '%" + userPhone + "%'";
				}
			}

			if (!paramStr.equals("")) {
				swhere = swhere + " and( " + paramStr + " )";
			}

			List<M_User> userList = m_UserService.getEntityList(swhere);
			for (int i = 0; i < userList.size(); i++) {
				M_User user = userList.get(i);

				row = sheet.createRow(i);

				cell = row.createCell(0);
				cell.setCellType(HSSFCell.CELL_TYPE_STRING);
				cell.setCellValue(user.getU_id() == null ? "" : user.getU_id());

				cell = row.createCell(1);
				cell.setCellType(HSSFCell.CELL_TYPE_STRING);
				cell.setCellValue(user.getU_phone() == null ? "" : user
						.getU_phone());

				cell = row.createCell(2);
				cell.setCellType(HSSFCell.CELL_TYPE_STRING);
				cell.setCellValue(user.getU_phoneimei() == null ? "" : user
						.getU_phoneimei());

				cell = row.createCell(3);
				cell.setCellType(HSSFCell.CELL_TYPE_STRING);
				cell.setCellValue(user.getU_name() == null ? "" : user
						.getU_name());

				cell = row.createCell(4);
				cell.setCellType(HSSFCell.CELL_TYPE_STRING);
				cell.setCellValue(user.getU_sex());

				cell = row.createCell(5);
				cell.setCellType(HSSFCell.CELL_TYPE_STRING);
				cell.setCellValue(user.getU_profession() == null ? "" : user
						.getU_profession());

				cell = row.createCell(6);
				cell.setCellType(HSSFCell.CELL_TYPE_STRING);
				cell.setCellValue(user.getU_regtime() == null ? "" : sdf
						.format(user.getU_regtime()));

				cell = row.createCell(7);
				cell.setCellType(HSSFCell.CELL_TYPE_STRING);
				cell.setCellValue(user.getStatus());

				cell = row.createCell(8);
				cell.setCellType(HSSFCell.CELL_TYPE_STRING);
				cell.setCellValue(user.getU_lastupdatetime() == null ? "" : sdf
						.format(user.getU_lastupdatetime()));

				cell = row.createCell(9);
				cell.setCellType(HSSFCell.CELL_TYPE_STRING);
				cell.setCellValue(user.getU_type() == null ? "" : user
						.getU_type());

				cell = row.createCell(10);
				cell.setCellType(HSSFCell.CELL_TYPE_STRING);
				cell.setCellValue(user.getU_istrans());
			}
		} else {
			for (int i = 0; i < usersArray.size(); i++) {
				JSONObject jsonObj = (JSONObject) usersArray.get(i);

				M_User user = m_UserService
						.getEntity(jsonObj.getString("u_id"));

				row = sheet.createRow(i);

				cell = row.createCell(0);
				cell.setCellType(HSSFCell.CELL_TYPE_STRING);
				cell.setCellValue(user.getU_id() == null ? "" : user.getU_id());

				cell = row.createCell(1);
				cell.setCellType(HSSFCell.CELL_TYPE_STRING);
				cell.setCellValue(user.getU_phone() == null ? "" : user
						.getU_phone());

				cell = row.createCell(2);
				cell.setCellType(HSSFCell.CELL_TYPE_STRING);
				cell.setCellValue(user.getU_phoneimei() == null ? "" : user
						.getU_phoneimei());

				cell = row.createCell(3);
				cell.setCellType(HSSFCell.CELL_TYPE_STRING);
				cell.setCellValue(user.getU_name() == null ? "" : user
						.getU_name());

				cell = row.createCell(4);
				cell.setCellType(HSSFCell.CELL_TYPE_STRING);
				cell.setCellValue(user.getU_sex());

				cell = row.createCell(5);
				cell.setCellType(HSSFCell.CELL_TYPE_STRING);
				cell.setCellValue(user.getU_profession() == null ? "" : user
						.getU_profession());

				cell = row.createCell(6);
				cell.setCellType(HSSFCell.CELL_TYPE_STRING);
				cell.setCellValue(user.getU_regtime() == null ? "" : sdf
						.format(user.getU_regtime()));

				cell = row.createCell(7);
				cell.setCellType(HSSFCell.CELL_TYPE_STRING);
				cell.setCellValue(user.getStatus());

				cell = row.createCell(8);
				cell.setCellType(HSSFCell.CELL_TYPE_STRING);
				cell.setCellValue(user.getU_lastupdatetime() == null ? "" : sdf
						.format(user.getU_lastupdatetime()));

				cell = row.createCell(9);
				cell.setCellType(HSSFCell.CELL_TYPE_STRING);
				cell.setCellValue(user.getU_type() == null ? "" : user
						.getU_type());

				cell = row.createCell(10);
				cell.setCellType(HSSFCell.CELL_TYPE_STRING);
				cell.setCellValue(user.getU_istrans());
			}
		}

		FileOutputStream os;
		try {
			os = new FileOutputStream("workbook.xls");
			wb.write(os);
			os.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "1";

	}

	/**
	 * 往服务器同步手机的用户数据.
	 * 
	 * @param userJson
	 *            手机端所有上次同步之后又变更的的用户信息Json串（包括新增的和修改的）
	 * @return "success"、"faild"
	 */
	@RequestMapping(value = "syncUsers", produces = "plain/text; charset=UTF-8")
	@ResponseBody
	public String syncUsers(String userJson) {
		try {

			JSONArray jsonArr = JSONArray.fromObject(userJson);

			List<M_User> list = new ArrayList<M_User>();

			for (Object object : jsonArr) {
				JSONObject jsonObj = (JSONObject) object;

				String[] dateFormats = new String[] { "yyyy-MM-dd",
						"yyyy-MM-dd HH:mm:ss" };
				JSONUtils.getMorpherRegistry().registerMorpher(
						new DateMorpher(dateFormats));
				M_User u = (M_User) jsonObj.toBean(jsonObj, M_User.class);

				list.add(u);

			}

			Boolean res = false;
			int count = 0;
			if (list.size() > 0) {
				for (M_User m_User : list) {
					if (m_User == null || m_User.getU_id() == "") {
						continue;
					}
					Object obj = commonService
							.getSingleValue("select u_id from M_User where u_id='"
									+ m_User.getU_id() + "'");
					m_User.setU_istrans(1);
					if (obj != null) {
						res = m_UserService.update(m_User);
					} else {
						res = m_UserService.add(m_User);
					}
					if (res) {
						count++;
					}
				}

				if (count > 0)
					return "success";
			}

			return "nodatafound";

		} catch (Exception e) {
			return e.getMessage();
		}
	}
}
