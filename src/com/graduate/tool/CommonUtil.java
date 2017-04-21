package com.graduate.tool;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 
 * @author falcon 新增通用类工具方法 add by cxk 2015年3月10日10:34:51
 */

public class CommonUtil {

	/**
	 * 随机获取UUID字符串(无中划线,32位)
	 * 
	 * @return UUID字符串
	 */
	public static String getUUID() {
		String uuid = UUID.randomUUID().toString();
		return (uuid.substring(0, 8) + uuid.substring(9, 13)
				+ uuid.substring(14, 18) + uuid.substring(19, 23) + uuid
					.substring(24)).toUpperCase();
	}

	/**
	 * 获取系统当前时间 add by cxk 2015年3月10日14:57:11
	 * @return
	 */
	public static String getLocalSysTime1() {
		Date now = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
		String timeString = dateFormat.format(now);
		return timeString;
	}
	
	/***
	 * 获取系统当前时间 add by anke 2016年12月23日15:19:08
	 * @return
	 */
	public static String getLocalSysTime2() {
		Date dates = new Date();
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd-HH:mm:ss");
		String time = format.format(dates);
		return time;
	}

	/**
	 * 在0~m之间生成随机数(包括m)
	 * 
	 */
	public static int getRandomInt(int m) {
		if (m < 0)
			return 0;
		float d = (float) Math.random() * m;
		return Math.round(d);
	}

	/**
	 * 将数组转换为regex分割的字符串
	 * 
	 * @param st 要转化的数组
	 * @param regex 分割符
	 * @return String
	 */
	public static String arrayToString(String[] st, String regex) {
		if (st == null) {
			return "";
		}
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < st.length; i++) {
			if (sb.length() == 0) {
				sb.append(st[i]);
			} else {
				sb.append(regex).append(st[i]);
			}
		}
		return sb.toString();
	}

	/**
	 * 按字节截取固定长度的字符串
	 * 
	 * @param s 字符串
	 * @param len 长度
	 */
	public String interceptStr(String s, int len) {
		try {
			byte[] b = s.getBytes("GBK");
			if (b.length > len) {
				if (b[len] < 0) {
					s = new String(b, 0, --len, "GBK");
				} else {
					s = new String(b, 0, len, "GBK");
				}
			}

		} catch (Exception e) {
			// System.out.println("String to GBK Error ");
			// logger.error("String to GBK Error ", e);
			throw new RuntimeException(e);
		}
		return s;
	}

	/**
	 * 求绝对值
	 * 
	 * @param a int值
	 * @return a的绝对值
	 */
	public String abs(int a) {
		int b = (a < 0) ? -a : a;
		return Integer.toString(b);
	}

	/**
	 * 将返回的分转换为元
	 * 
	 * @param 名称: 含义
	 * @return String
	 * @exception
	 */
	public static String centToYuan(String money) {
		if (money == null || "".equals(money)) {
			return "0.00";
		}
		Pattern p = Pattern.compile("\\D*");
		Matcher m = p.matcher(money);
		boolean b = m.matches();

		if (b) {
			return money;
		}
		String symbol = "";
		// 负数
		if (money.indexOf("-") == 0) {
			symbol = "-";
			money = money.substring(1);
		}
		String yuanStr;
		if (money.indexOf(".") != -1) {
			yuanStr = money;
		} else if (money.length() == 2) {
			yuanStr = "0." + money;
		} else if (money.length() == 1) {
			yuanStr = "0.0" + money;
		} else {
			String beforDot;
			String atferDot;
			beforDot = money.substring(0, money.length() - 2);
			atferDot = money.substring(money.length() - 2);
			yuanStr = beforDot + "." + atferDot;
		}
		return symbol + yuanStr;
	}

	/**
	 * 将返回的元转换为格式为0.00
	 * 
	 * @param 名称: 含义
	 * @return String
	 */
	public String yuanToYuan(String money) {
		String yuanStr;
		if (money == null || "".equals(money)) {
			return "&nbsp;";
		} else {
			yuanStr = money + ".00";
		}
		return yuanStr;
	}

	/**
	 * 将数值转换为格式为规定有效小数位的小数 2011-07-18 chenyunhua
	 * 比如 入参为 2.3 和 2 则返回值为 2.30
	 * @param oldValue:要转换的数值
	 * @param floatLenth:要保留的有效小数位
	 * @return String 保留两位邮箱小数后的数值
	 */
	public static String TransToFloat(String oldValue, int floatLenth) {
		if (oldValue == null || "".equals(oldValue)) {
			oldValue = "0.00";
		}
		String newValue = String.valueOf((new BigDecimal(oldValue)).setScale(
				floatLenth, BigDecimal.ROUND_HALF_UP));
		return newValue;
	}

	/**
	 * 计算字符长度 中文字符2，英文 1
	 * 
	 * @param chr
	 * @return
	 * @see [类、类#方法、类#成员]
	 */
	private static int byteLength(char chr) {
		if (Integer.toHexString(chr).length() == 4) {
			return 2;
		} else {
			return 1;
		}
	}

	/**
	 * 按指定长度，省略字符串部分字符
	 * 
	 * @param String字符串
	 * @param length保留字符串长度
	 * @return 省略后的字符串
	 */
	public static String subString(String string, int length) {
		return subString(string, length, "...");
	}

	/**
	 * 按指定长度，省略字符串部分字符
	 * 
	 * @param String被处理字符串
	 * @param length保留字符串长度
	 * @param more后缀字符串
	 * @return 省略后的字符串
	 */
	public static String subString(String string, int length, String more) {
		if (string.getBytes().length < length) {
			return string;
		}
		StringBuffer sb = new StringBuffer();
		int moreLen = more.length();
		int count = 0;
		char[] charArray = string.toCharArray();
		for (int i = 0; i < charArray.length; i++) {
			char tempChar = charArray[i];
			count += byteLength(tempChar);

			if (count < length - moreLen) {
				sb.append(tempChar);
			} else if (count == length - moreLen) {
				sb.append(tempChar);
				break;
			} else if (count > length - moreLen) {
				break;
			}
		}
		sb.append(more);
		return sb.toString();
	}

	/**
	 * 功能: 将金额值(String)转换成long,以分为单位
	 * 
	 * @param sMoney long 金额值(以元为单位)
	 * @return long(以分为单位)
	 */
	public static long moneyToLong(String sMoney) {
		long DMoney = Math.round(Double.parseDouble(sMoney) * 100);
		return DMoney;
	}

	/**
	 * 功能：把以分为单位的字符串金额信息转换为以元为单位的金额信息（）
	 * 
	 * @param money以分为单位的金额信息
	 * @return 以元为单位的金额信息
	 * @see [类、类#方法、类#成员]
	 */
	public static String moneyString(String money) {
		String tempAmount = Integer.parseInt(money) + "";
		int length = tempAmount.length();
		if (tempAmount.length() > 2) {
			tempAmount = tempAmount.substring(0, length - 2) + "."
					+ tempAmount.substring(length - 2);
		} else {
			if (tempAmount.length() == 2) {
				tempAmount = "0." + tempAmount;
			} else {
				tempAmount = "0.0" + tempAmount;
			}
		}
		return tempAmount;
	}

	/**
	 * 功能：把字符串类型的金额信息（以分为单位）转换为double类型的金额信息（以元为单位）
	 * 
	 * @param money字符串类型的金额信息（以分为单位）
	 * @return double类型的金额信息（以元为单位）
	 * @see [类、类#方法、类#成员]
	 */
	public static double moneyStringToDouble(String money) {
		return Double.valueOf(moneyString(money));
	}

	/**
	 * 转换为日期格式 yyyy-MM-dd add by zqb 2015年4月9日14:34:58
	 * 
	 * @param newDate
	 * @return
	 */
	public static String FormatDate10(Date newDate) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		String date = dateFormat.format(newDate);
		return date;
	}

	/**
	 * 转换为日期格式yyyy-MM-dd hh42:mi:ss add by zqb 2015年4月9日14:35:21
	 * 
	 * @param newDate
	 * @return
	 */
	public static String FormatDate19(Date newDate) {
		SimpleDateFormat dateFormat = new SimpleDateFormat(
				"yyyy-MM-dd hh42:mi:ss");
		String date = dateFormat.format(newDate);
		return date;
	}

	/**
	 * 日期转换为String yyyyMMdd add by zqb 2015年4月9日14:35:21
	 * 
	 * @param newDate
	 * @return
	 * @throws ParseException
	 */
	public static String DateToString10(String newDate) throws ParseException {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd");
		Date tmpDate = dateFormat.parse(newDate);
		SimpleDateFormat dateFormatNew = new SimpleDateFormat("yyyymmdd");
		String date = dateFormatNew.format(tmpDate);
		return date;
	}

	/**
	 * 在原来的日期基础上增加天数
	 */
	public static Date addDays(Date date, int day) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.set(Calendar.DAY_OF_MONTH, c.get(Calendar.DAY_OF_MONTH) + day);
		return c.getTime();
	}

	/**
	 * 在原来的日期基础上增加天数
	 */
	public static String addDays(String date, int day) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		try {
			Date d = sdf.parse(date);
			Calendar c = Calendar.getInstance();
			c.setTime(d);
			c.set(Calendar.DAY_OF_MONTH, c.get(Calendar.DAY_OF_MONTH) + day);
			return sdf.format(c.getTime());
		} catch (ParseException e) {
			return null;
		}
	}

	/**
	 * 转换数据库中下划线分割字段名为驼峰命名格式
	 * 
	 * @param orignal原始字段
	 * @return 驼峰命名字段
	 */
	public static String getBeanProName(String orignal) {
		if (orignal == null || "".equals(orignal.trim())) {
			return "";
		}
		int len = orignal.length();
		StringBuilder sb = new StringBuilder(len);
		for (int i = 0; i < len; i++) {
			char c = orignal.charAt(i);
			if (c == '_') {
				if (++i < len) {
					sb.append(Character.toUpperCase(orignal.charAt(i)));
				}
			} else {
				sb.append(Character.toLowerCase(c));
			}
		}
		return sb.toString();
	}

	/**
	 * 将从数据库获取的_分割字段名改为驼峰命名
	 * 
	 * @param dbMap
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static Map<String, Object> convertDbPro(Map<String, Object> dbMap) {
		if (dbMap == null || dbMap.isEmpty())
			return dbMap;

		Map<String, Object> mapNew = new HashMap<String, Object>();
		Set<String> keys = dbMap.keySet();
		for (String key : keys) {
			Object value = dbMap.get(key);
			if (value instanceof Map) {
				value = convertDbPro((Map<String, Object>) value);
			}
			String _key = getBeanProName(key);
			mapNew.put(_key, value);
		}
		return mapNew;
	}

	/**
	 * 验证是否为空
	 * 
	 * @param obj
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static boolean isEmpty(Object obj) {
		if (obj == null)
			return true;

		if (obj instanceof Object[]) {
			Object[] arr = (Object[]) obj;
			return arr.length == 0;
		}
		if (obj instanceof List) {
			List list = (List) obj;
			return list.isEmpty();
		}
		if (obj instanceof Map) {
			Map map = (Map) obj;
			return map.isEmpty();
		}
		return false;
	}
}
