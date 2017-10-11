package com.mhook.MrSFastTranslation.Utils;
import java.lang.reflect.*;

public class XposedUtils
{
	

	/*禁用 xposed*/

	public static Boolean disXposed(boolean isDisXposed){

//		try
//		{
//			Field fieldDisHooks=ClassLoader.getSystemClassLoader().loadClass("de.robv.android.xposed.XposedBridge").getDeclaredField("disableHooks");
//
//			fieldDisHooks.setAccessible(true);
//
//			fieldDisHooks.set(null,Boolean.valueOf(isDisXposed));
//		}
//		catch (Exception e)
//		{
//
//			return false;
//
//		}
//
		return true;

	}
	
}
