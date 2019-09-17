package cn.lijiahao.md5;

import org.springframework.util.DigestUtils;

public class Md5Utils {
	public static String encrypt(String str,String salt) {
		String strWithsalt = str + salt;
		String md5 = DigestUtils.md5DigestAsHex(strWithsalt.getBytes());
		return md5;
	}
}
