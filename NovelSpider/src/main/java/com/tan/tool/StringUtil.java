package com.tan.tool;

public class StringUtil {
	public static boolean ifempty(String str)
	{
		if((str==null)||(str.isEmpty()))
		{
			return true;
		}
		return false;
	}
}
