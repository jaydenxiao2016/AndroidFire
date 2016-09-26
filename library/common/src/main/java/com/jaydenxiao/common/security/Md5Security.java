package com.jaydenxiao.common.security;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * MD5加密类
 */
public class Md5Security {
	/**
	 * md5加密
	 * @param info
	 * @return
	 */
	public static String getMD5(String info)
	{
		try
		{
			MessageDigest md5 = MessageDigest.getInstance("MD5");
			md5.update(info.getBytes("UTF-8"));
			byte[] encryption = md5.digest();

			StringBuffer strBuf = new StringBuffer();
			for (int i = 0; i < encryption.length; i++)
			{
				if (Integer.toHexString(0xff & encryption[i]).length() == 1)
				{
					strBuf.append("0").append(Integer.toHexString(0xff & encryption[i]));
				}
				else
				{
					strBuf.append(Integer.toHexString(0xff & encryption[i]));
				}
			}

			return strBuf.toString();
		}
		catch (NoSuchAlgorithmException e)
		{
			return "";
		}
		catch (UnsupportedEncodingException e)
		{
			return "";
		}
	}
}
