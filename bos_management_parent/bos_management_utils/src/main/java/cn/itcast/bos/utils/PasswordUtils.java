package cn.itcast.bos.utils;

import org.apache.shiro.crypto.hash.Md5Hash;

public class PasswordUtils {

	public static void main(String[] args) {
		String password="123456";
//		source 原密码, salt 盐, hashIterations 散次列  加盐的次数
//		Md5Hash md5 = new Md5Hash(password, "sunyunkuaidi-itcast", 100);
//		String string = md5.toString();
		Md5Hash md5 = new Md5Hash("admin", "admin", 2);
		String string = md5.toString();
		System.out.println(string);
	}
}
