package com.wolf.commons.util;

import java.text.SimpleDateFormat;
import java.util.*;


/**
 * ID工具类
 */
public class IDUtil {

	/** 随机种子 */
	private static final String RANDOM_SEED = "ABCDEFGHIJKLMNOPQRSTUVWXYZ123456789";
	/** 数字随机种子 */
	private static final String NUM_RANDOM_SEED = "0123456789";

	private static final int TOP_NUM = 9999999;

	private static final String[] ZEROS = {
			"000000", "00000", "0000", "000", "00", "0", ""
	};

	private static int NUM;

	private static synchronized int getNum() {
		if (NUM >= TOP_NUM) {
			NUM = 0;
		}
		return ++NUM;
	}

	/**
	 * 单点可用于生成随机ID
	 *
	 * @return
	 */
	public static String getNumStr() {
		int num = getNum();
		return System.currentTimeMillis() + ZEROS[(num + "").length() - 1] + num;
	}

	/**
	 * 随机出32个字符,字母、数字和中划线的组合(可用于生成类似激活码)
	 * @return
	 */
	public static String random32Chars() {
		return UUID.randomUUID().toString().replace("-", "").toUpperCase();
	}


	/**
	 * 随机带时间戳的字符(17+n) (年月日时间+n个随机字符)
	 * @return
	 */
	public static String randomCharsWithTime(int n) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
		return sdf.format(new Date()) + randomNChars(n);
	}


	/**
	 * 随机n个字符 (可用于生成类似激活码)
	 * @return
	 */
	public static String randomNChars(int n) {
		if (n <= 0) {
			return "";
		}
		StringBuilder ret = new StringBuilder();
		Random random = new Random();
		int size = RANDOM_SEED.length();
		for (int i = 0; i < n; i++) {
			ret.append(RANDOM_SEED.charAt(random.nextInt(size)));
		}
		return ret.toString();
	}


	/**
	 * 随机带时间戳的数字串(13+n)
	 * @param n
	 * @return
	 */
	public static String randomNumWithTimeMillis(int n) {
		StringBuilder sb = new StringBuilder(System.currentTimeMillis() + "");// 13位
		Random random = new Random();
		int size = NUM_RANDOM_SEED.length();
		for (int i = 0; i < n; i++) {
			sb.append(NUM_RANDOM_SEED.charAt(random.nextInt(size)));
		}
		return sb.toString();
	}

	/**
	 * 随机带时间戳的数字串(13+n)
	 * @param n
	 * @return
	 */
	public static String randomNumWithTimeMillis(long time, int n) {
		StringBuilder sb = new StringBuilder(time + "");// 13位
		Random random = new Random();
		int size = NUM_RANDOM_SEED.length();
		for (int i = 0; i < n; i++) {
			sb.append(NUM_RANDOM_SEED.charAt(random.nextInt(size)));
		}
		return sb.toString();
	}


	/**
	 * 获取随机字符列表
	 * @param num
	 * @param len
	 * @return
	 */
	public static List<String> randomStrings(int num, int len) {
		List<String> list = new ArrayList<String>();
		for (int i = 0; i < num; i++) {
			list.add(randomCharsWithTime(len));
		}
		return list;
	}
}
