package com.mhook.MrSFastTranslation.Utils;

import android.content.*;
import android.os.*;
import android.content.pm.*;
import java.util.*;

public class BroadcastUtils
{

	public static Boolean isReceiver(Context con,String action){
		
		Intent intent=new Intent();
		
		intent.setAction(action);
		
		PackageManager pm = con.getPackageManager();
		List<ResolveInfo> resolveInfos = pm.queryBroadcastReceivers(intent, 0);
		if(resolveInfos != null && !resolveInfos.isEmpty()){
			//查询到相应的BroadcastReceiver
			
			return true;
			
		}  
		
		return false;
		
	}
	
	//注册广播

	public static void Receiver(Context con,  String action,  BroadcastReceiver broadcastreceiver){

		IntentFilter IntentFilter1=new IntentFilter();

		IntentFilter1.addAction(action);

		IntentFilter1.setPriority(Integer.MAX_VALUE);
		
		try{con.unregisterReceiver(broadcastreceiver);}catch(Throwable t){
			
			con.registerReceiver(broadcastreceiver,IntentFilter1);
			
			return;
			
		}
		
		return ;

	}

	//发送广播
	
	public static void SendBroadcast(Context con,String action,String data){

		Bundle Bundle1=new Bundle();
		
		Bundle1.putString("data",data);
		
		SendBroadcast(con,action,Bundle1);
		
	}

	public static void SendBroadcast(Context con,String action,Bundle bundle){

		Intent Intent1=new Intent();

		Intent1.setAction(action);
		
		Intent1.putExtra("bundle",bundle);

		con.sendBroadcast(Intent1);

	}
	
	//发送有序广播
	
	//发送广播

	public static void sendOrderedBroadcast(Context con,String action,String data){

		Bundle Bundle1=new Bundle();

		Bundle1.putString("data",data);

		sendOrderedBroadcast(con,action,Bundle1);

	}

	public static void sendOrderedBroadcast(Context con,String action,Bundle bundle){

		Intent Intent1=new Intent();

		Intent1.setAction(action);

		Intent1.putExtra("bundle",bundle);

		con.sendOrderedBroadcast(Intent1,null);

	}
	
	}
	
