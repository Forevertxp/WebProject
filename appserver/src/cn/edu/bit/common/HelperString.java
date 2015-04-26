package cn.edu.bit.common;

import java.util.Collection;
import java.util.Iterator;

public class HelperString {
	/**
	 * 判断对象是否是null或者空
	 * @param obj
	 * @return
	 */
	public static boolean isNullOrEmpty(Object obj) {
		return obj == null || "".equals(obj.toString());
	}

	/**
	 * 如果是null返回空
	 * @param obj
	 * @return
	 */
	public static String toString(Object obj) {
		
		
		
		if (obj == null)
			return "";
		return obj.toString();
	}

	public static String join(Collection s, String delimiter) {
		StringBuffer buffer = new StringBuffer();
		Iterator iter = s.iterator();
		while (iter.hasNext()) {
			buffer.append(iter.next());
			if (iter.hasNext()) {
				buffer.append(delimiter);
			}
		}
		return buffer.toString();
	}

	/**
	 * tianyue 
	 * surround包围字符串数组中的元素，并用separator将包围后的元素拼接在一起
	 * @param idArr
	 *            字符串数组
	 * @param separator
	 *            分隔符字符
	 * @param surround
	 *            包围字符
	 * @return 处理后的字符串
	 */
	public static String joinStringSurrWithSep(String[] idArr, String separator,
			String surround) {

		String str = "";

		if (idArr == null || idArr.length == 0) {
			return "";
		}
		if (separator == null) {
			separator = "";
		}
		if (surround == null) {
			surround = "";
		}

		for (String id : idArr) {
			if (id.trim().equals("")) {
				continue;
			}
			str += surround + id + surround + separator;
		}
		if (str.endsWith(separator)) {
			str = str.substring(0, str.length() - separator.length());
		}

		return str;
	}
	
	public static String firstCharUpper(String str)
	{
		if (isNullOrEmpty(str)) {
			return "";
		}
		return str.substring(0, 1).toUpperCase()+str.substring(1);
	}

}
