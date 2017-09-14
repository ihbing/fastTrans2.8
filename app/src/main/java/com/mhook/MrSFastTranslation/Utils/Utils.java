package com.mhook.MrSFastTranslation.Utils;

import android.widget.*;
import android.content.*;
import android.util.*;
import java.util.regex.*;

public class Utils
{
	static Toast toast=null;
	
	public static void show(Context context,String Text){
		
		if(toast==null){
			
			toast=Toast.makeText(context,Text,Toast.LENGTH_LONG);

		}else{
			
			toast.setText(Text);
			
		}
		
		toast.show();

	}
	
	public static void printf(Context con,String str){
		
		printf(str+"包名:"+con.getPackageName());
		
	}

	public static void printf( String str){
		
		Log.e("fanyi",str);
		
	}
	public static String UnicodeToString(String str) {
		Pattern pattern = Pattern.compile("(\\\\u(\\p{XDigit}{4}))");
		Matcher matcher = pattern.matcher(str);
		char ch;
		while (matcher.find()) {
			ch = (char) Integer.parseInt(matcher.group(2), 16);
			str = str.replace(matcher.group(1), ch + "");
		}
		return str;
	}

}
