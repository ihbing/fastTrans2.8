package com.mhook.MrSFastTranslation;
import android.app.*;
import android.content.*;

public class MyApplication extends Application
{
	
	static Context mContext;

	@Override
	public void onCreate()
	{
		// TODO: Implement this method
		super.onCreate();
		
		mContext=this;
		
	}
	
	public static Context getContext(){
		
		return mContext;
	}

	
	
}
