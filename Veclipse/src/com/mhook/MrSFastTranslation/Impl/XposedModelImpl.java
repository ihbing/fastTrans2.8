package com.mhook.MrSFastTranslation.Impl;
import android.app.*;
import android.content.*;
import android.os.*;
import android.text.*;
import android.view.*;
import com.mhook.MrSFastTranslation.*;
import com.mhook.MrSFastTranslation.Utils.*;
import com.mhook.MrSFastTranslation.mvp.*;
import de.robv.android.xposed.*;
import de.robv.android.xposed.callbacks.*;
import java.lang.reflect.*;
import java.net.*;
import java.util.*;
import org.json.*;

public class XposedModelImpl implements XposedModel,IXposedHookLoadPackage
{

	@Override
	public int getShowWindowTransparency()
	{
		// TODO: Implement this method
		try{

			XJsonRW.JObject XJObect=XJsonRW.JObject.getInstance();

			int result= XJObect.getInt(TransModelImpl.jsonShowWindowTransparency);

			XJObect.freed();

			return result;

		}catch(Throwable t){

			return 3;

		}
	}


	@Override
	public Boolean checkLabel(ClipData clipData)
	{
		// TODO: Implement this method
		
		String  label=((null==clipData.getDescription())?"unkown":((null==clipData.getDescription().getLabel())?"unkown":(clipData.getDescription().getLabel().toString())));

		Utils.printf("label:"+label);

		if("fanyi".equals(label))return false;
		
		return true;
	}


	private static final String jsonIsShowWindow="isShowWindow";
	
	@Override
	public void setIsShowedWindow(boolean isShowed)
	{
		// TODO: Implement this method
		

		try{

			XJsonRW.JObject XJObect=XJsonRW.JObject.getInstance();

			 XJObect.put(jsonIsShowWindow,isShowed);
			 
			 XJObect.save();
			 
			 XJObect.freed();

		}catch(Throwable t){
			
			Utils.printf("更改设置异常:"+t.getMessage());

		}
		
	}


	@Override
	public Boolean isShowedWindow()
	{
		// TODO: Implement this method

		try{

			XJsonRW.JObject XJObect=XJsonRW.JObject.getInstance();

			 boolean result= XJObect.getBoolean(jsonIsShowWindow);
			 
			 XJObect.freed();
			 
			 return result;

		}catch(Throwable t){

			Utils.printf("获取设置异常:"+t.getMessage());
			
			return false;

		}
	}


	@Override
	public int getShowTime()
	{
		try{

			XJsonRW.JObject XJObect=XJsonRW.JObject.getInstance();
			
			int result= XJObect.getInt(TransModelImpl.jsonShowWindowTime);
			
			XJObect.freed();
			
			return result;

		}catch(Throwable t){

			return 3;

		}
	}


	@Override
	public void receiverBroad(Context con)
	{
		// TODO: Implement this method
		
		BroadcastReceiver broadReceiverFanyi=new BroadcastReceiver(){

			@Override
			public void onReceive(final Context p1, Intent p2)
			{

				xpPresenter.onReceive(p1,p2);

				// TODO: Implement this method
				
				abortBroadcast();
				
			}

		};
		
		BroadcastUtils.Receiver(con,AllResources.ReceiverAction,broadReceiverFanyi);
		
	}

	@Override
	public String getYoudaoFanyiUrl( String[] key, String toLanguage, String q)
	{
		// TODO: Implement this method
		return "http://fanyi.youdao.com/openapi.do?keyfrom=" +
		
		key[0] + "&key=" + key[1] + "&type=data&doctype=json&version=1.1&only=translate&q=" +
		
		URLEncoder.encode(q);
		
	}


	@Override
	public String getAimsLanguage(String toLanguage, String fanyiData)
	{

		if(toLanguage.equals("")|toLanguage==null){

			toLanguage=(LanguageUtils.isjapen(fanyiData)?"zh-CN":(LanguageUtils.ischina(fanyiData) ?"en": "zh-CN"));

		}
		
		Utils.printf("翻译后语言:"+toLanguage);
		
		return toLanguage;
		
	}


	@Override
	public String sortOutCharacter(String character)
	{
		Utils.printf("整理前:"+character);

		 character=  character.replaceAll("(?<=[a-z])(?=[A-Z][a-z])", " ").replaceAll("_", " ").replaceAll("'", "’").replaceAll("\"", "“");

        Utils.printf("整理后:"+character);
		
		return character;
		
	}

	@Override
	public String clearOldProblem(String character)
	{
		// TODO: Implement this method
		return character.replaceAll("\0","");
	}

	@Override
	public Boolean checkCharacter(String character)
	{
		
		try{
		
		XJsonRW.JObject jObject=XJsonRW.JObject.getInstance();
		
		//黑名单处理

		if(jObject.getBoolean(TransModelImpl.jsonSwhBlackListEnabled)){

			Utils.printf("黑名单已开启");
			
			String strBlackList=jObject.getString(TransModelImpl.jsonEdtBlackList);
			
			if(!TextUtils.isEmpty(strBlackList)){
				
				String strBlackLists[]=strBlackList.split(",");
				
				for(String strBlackLisz:strBlackLists){
					
					if(character.replaceAll(strBlackLisz,"").length()!=character.length()){
						
						Utils.printf("符合黑名单");

						jObject.freed();

						return false;
						
					}
					
				}

			}

		}else{

			Utils.printf("黑名单已关闭！");

		}

		//白名单处理

		if(jObject.getBoolean(TransModelImpl.jsonSwhWhiteListEnabled)){

			Utils.printf("白名单已开启");
			
			String strWhiteList=jObject.getString(TransModelImpl.jsonEdtWhiteList);

			if(!TextUtils.isEmpty(strWhiteList)){

				String strWhiteLists[]=strWhiteList.split(",");

				for(String strWhiteLisz:strWhiteLists){
					
					 character= character.replaceAll(strWhiteLisz,"");

				}
				
				if(!TextUtils.isEmpty(character)){
					
					Utils.printf("符合白名单！");
					
					jObject.freed();
					
					return false;
					
					}

			}

		}else{

			Utils.printf("白名单已关闭！");

		}
		
		jObject.freed();
		
		return true;
		
		}catch(Throwable t){
			
			Utils.printf("名单设置异常:"+t.getMessage());
			
			return false;
			
		}
		
	}

	@Override
	public int[] getWindowXY(Context con)
	{
		// TODO: Implement this method
	
		SharedPreferences prefs=con.getSharedPreferences(AllResources.sharename,Context.MODE_PRIVATE);
		
		int xy[]=new int[2];
		
		xy[0]=prefs.getInt("windowX",120);
		
		xy[1]=prefs.getInt("windowY",0);
		
		return xy;
		
	}

	@Override
	public String getGoogleFanyiUrl(String toLanguage, String q)
	{
		// TODO: Implement this method
		return "http://ainixiang.cn/fanyi/?sl=auto&tl=" +toLanguage + "&q=" + URLEncoder.encode(q);
		
	}

	@Override
	public Boolean checkCharacterLength(String character)
	{
		// TODO: Implement this method
		if(character.length()>1951){

			Utils.printf("错误:要翻译内容过长");

			return false;

		}
		
		return true;
		
	}

	@Override
	public String[] getYoudaoRandomKey()
	{
		// TODO: Implement this method
		String fanyikey[][]={{"fanyissss","17056605"},{"fanyidjjdk","542884696"},{"fanyidjjdkhd","2021146716"},{"fanyidjjdkhdjx","996772878"},{"fanyidjjdkghhdjx","146039213"},{"hjggghg","944085764"},{"hjggghghh","1028323076"},{"hjggghghhhhg","1228861981"},{"hjgbkkgdd","888960650"},{"hjgbkkgddhjgg","577043732"}};

		int random=new Random().nextInt(10);

		String keyfrom=fanyikey[random][0];

		String key=fanyikey[random][1];
		
		return new String[]{keyfrom,key};
		
	}

	@Override
	public void getGoogleFanyiResult(final String fanyiUrl,final XposedModelImpl.OnFanyiListener onFyListener)
	{
		// TODO: Implement this method
		
		new Thread(){
			
			public void run(){
				
				String fanyiResult = HttpUtils.getHtml(fanyiUrl);

				try
				{
					JSONObject jsonobj=new JSONObject(fanyiResult);
					
					fanyiResult = new JSONArray(jsonobj.get("tran").toString()).get(0).toString();

					Utils.printf("返回谷歌翻译结果:" + fanyiResult);
					
					if (fanyiResult.equals("") || fanyiResult.startsWith("{\"tran\":\"<!DOCTYPE html>"))
					{

						Utils.printf("谷歌翻译:失败");

						if(onFyListener!=null)onFyListener.OnFailed();

						return;

					}
				
					if(onFyListener!=null)onFyListener.OnSuccess(fanyiResult);
					
					return;
					
				}
				catch (JSONException e)
				{

					Utils.printf("获取谷歌翻译结果失败:" + e);
					
					if(onFyListener!=null)onFyListener.OnFailed();

					return;
					
				}

			}

		}.start();
		
	}

	@Override
	public void getYoudaoFanyiResult(final String fanyiUrl,final XposedModelImpl.OnFanyiListener onFyListener)
	{
		// TODO: Implement this method
		
		new Thread(){
			
			public void run(){
				
				String html = HttpUtils.getHtml(fanyiUrl);

				Utils.printf("有道翻译结果:"+html);

				if (html.equals("")){

					Utils.printf("错误:获取有道翻译结果失败");

					if(onFyListener!=null)onFyListener.OnFailed();

					return;

				}

				try
				{

					JSONObject jsonobj=new JSONObject(html);
					
					if(jsonobj.getInt("errorCode")!=0){

						Utils.printf("有道翻译错误代码:"+jsonobj.get("errorCode"));
						
						if(onFyListener!=null)onFyListener.OnFailed();

						return;

					}

					JSONArray jsonarray=jsonobj.getJSONArray("translation");

					StringBuffer strbutt=new StringBuffer();
					
					for (int i= 0;i <  jsonarray.length();i++)
					{
						strbutt.append(jsonarray.get(i).toString());
						
						if (i != (jsonarray.length() - 1))
						{
							strbutt.append("\n");
						}

					}
					html = strbutt.toString();

					Utils.printf("有道翻译结果:"+html);
					
					if(onFyListener!=null)onFyListener.OnSuccess(html);

					return;
					
				}
				catch (JSONException e)
				{

					Utils.printf("获取有道翻译结果异常:"+e);
					
					if(onFyListener!=null)onFyListener.OnFailed();
					
					return;

				}
				
				
			}
			
		}.start();
		
		
	}

	@Override
	public Boolean checkIsSame(String oldStr, String newStr)
	{
		// TODO: Implement this method
		return oldStr.trim().equals(newStr.trim());
	}


	@Override
	public Boolean getModuleEnabledOnReceive()
	{
		// TODO: Implement this method
		
		try{
		
		XJsonRW.JObject XJObect=XJsonRW.JObject.getInstance();
		
		boolean result= XJObect.getBoolean(TransModelImpl.jsonSwhModuleEnabled);
		
		XJObect.freed();
		
		return result;
		
		}catch(Throwable t){
			
			return false;
			
		}
		
	}


	@Override
	public Context getOnInputContext(XC_MethodHook.MethodHookParam param)
	{
		// TODO: Implement this method
		if(param.thisObject instanceof Context){
			
			Utils.printf("上下文存在");
			
			return ((Context)param.thisObject).getApplicationContext();
			
		}
		
		Utils.printf("输入法上下文未知！");
		
		return null;
		
	}


	@Override
	public void SendClipDataToBroadcast( Context mContext, String character, String toLanguage)
	{

		if(TextUtils.isEmpty(character)){ 

			Utils.printf("错误:剪切板无内容");  

			return;

		}

		Utils.printf("剪切板内容:"+character);

		Utils.printf("当前包名:"+mContext.getPackageName());

		Bundle Bundle1=new Bundle();

		Bundle1.putString("tolanguage",toLanguage);

		Bundle1.putString("data",character);

		BroadcastUtils.sendOrderedBroadcast(mContext,AllResources.TransReceiver,Bundle1);

		Utils.printf("发送完毕！");
		
	}


	@Override
	public String getToLanguage(ClipData clipData)
	{
		// TODO: Implement this method
		String  label=((null==clipData.getDescription())?"unkown":((null==clipData.getDescription().getLabel())?"unkown":(clipData.getDescription().getLabel().toString())));

		Utils.printf("label:"+label);

		if("fanyi".equals(label))return "";

		String tolanguage=(label.equals("ja")|label.equals("ko"))?label:"";

		Utils.printf("language:"+tolanguage);
		
		return tolanguage;
		
	}


	@Override
	public ClipData getClipData(XC_MethodHook.MethodHookParam param)
	{
		// TODO: Implement this method
		if(!(param.args[0] instanceof ClipData)){

			Utils.printf("不是clipdata");

			return null;

		}

		Utils.printf("是clipdata");

		android.content.ClipData clipdata=(android.content.ClipData)param.args[0];
		if (clipdata == null){

			Utils.printf("错误:剪切板未知");

			return null;

		}

		Utils.printf("剪切板存在！,数据为:"+clipdata.toString());
		
		return clipdata;
		
	}


	@Override
	public Boolean fixSetPrimaryClipProblem(Context con)
	{
		// TODO: Implement this method
		
		SharedPreferences pref=con.getSharedPreferences("fyXposed",Context.MODE_PRIVATE);
		
		String prefSysTime="sysTime";
		
		if(System.currentTimeMillis()-pref.getLong(prefSysTime,0l)<500){ 

			pref.edit().putLong(prefSysTime,System.currentTimeMillis());

			return false;

		}

		pref.edit().putLong(prefSysTime,System.currentTimeMillis());

		return true;
	}


	@Override
	public Context getOnFanyiContext(XC_MethodHook.MethodHookParam param)
	{
		// TODO: Implement this metho
		
		Context result = null;
		
		Field Field1= XposedHelpers.findField(android.content.ClipboardManager.class,"mContext");

		try
		{
			result = (Context)Field1.get(param.thisObject);
		}
		catch (IllegalAccessException e)
		{}
		catch (IllegalArgumentException e)
		{}

		if(result==null)Utils.printf("错误:上下文未知");
		
		return result;
		
	}


	@Override
	public Boolean checkPremission(Context con)
	{
		// TODO: Implement this method
		if(!AppUtils.CheckPermission(con,android.Manifest.permission.INTERNET)){

			//T.ShowToast(mContext,"系统ui无联网权限！");

			Utils.printf("错误:当前输入法无联网权限！包名:"+con.getPackageName());

			return false;

		}

		if(!AppUtils.isFloatWindowOpAllowed(con)){

			Utils.printf("错误:当前输入法悬浮窗权限未开启！包名:"+con.getPackageName());

			return false;
			
		}
		
		return true;
		
	}

	@Override
	public void hookXposedActive(XC_LoadPackage.LoadPackageParam lpparam)
	{
		// TODO: Implement this method
		
		if (lpparam.packageName.equals(AllResources.pkgname))
			
			XposedHelpers.findAndHookMethod(AllResources.pkgname+".Impl.TransModelImpl", 
			
			lpparam.classLoader,
			
			"isXposedActive",
			
			XC_MethodReplacement.returnConstant(true));
		
	}

	@Override
	public void hookSetPrimaryClip(XC_LoadPackage.LoadPackageParam lpparam)
	{
		// TODO: Implement this method
		
		XC_MethodHook XC_MethodHook3=new XC_MethodHook(){
			
			public void	beforeHookedMethod(XC_MethodHook.MethodHookParam param){
				
				xpPresenter.beforeSetPrimaryClipHookedMethod(param);
				
			}

		};

		XposedBridge.hookAllMethods(XposedHelpers.findClass("android.content.ClipboardManager",lpparam.classLoader),"setPrimaryClip", XC_MethodHook3);
		
	}
	
	@Override
	public void hookInput(XC_LoadPackage.LoadPackageParam lpparam)
	{
		// TODO: Implement this method
		
		XC_MethodHook XC_MethodHook2=new XC_MethodHook(){

			public void beforeHookedMethod(XC_MethodHook.MethodHookParam param){

				xpPresenter.beforeInputHookedMethod(param);

				}

		};

		//注册广播
		
		Utils.printf("当前输入法包名:"+lpparam.packageName);
		
		try{
			
			if(lpparam.isFirstApplication&lpparam.packageName.equals(xPrefs.getString(AllResources.ShareInputPkgName,""))){

				Utils.printf("已hook输入法,包名:"+lpparam.packageName);
				
				XposedBridge.hookAllMethods(Application.class,"onCreate",XC_MethodHook2);

			}
			
			}catch(Throwable t){
				
				Utils.printf("hook输入法异常！"+t.getMessage());
				
			}
		
	}

	@Override
	public void fixToastClick(XC_LoadPackage.LoadPackageParam lpparam)
	{
		// TODO: Implement this method
		

		/*
		 修复toast类型悬浮窗在api19以下不能点击的问题

		 */
		XC_MethodHook XCMHookToastClick=new XC_MethodHook(){

			@Override
			public void beforeHookedMethod(XC_MethodHook.MethodHookParam param)
			{
				if (param.args[0] instanceof WindowManager.LayoutParams)
				{
					WindowManager.LayoutParams lyparams=(WindowManager.LayoutParams)param.args[0];
					if (lyparams.type == WindowManager.LayoutParams.TYPE_TOAST)
					{
						lyparams.type = 2;
					}
				}

			}

		};
		
		try{
		
		XposedBridge.hookAllMethods(
		
		XposedHelpers.findClass("com.android.internal.policy.impl.PhoneWindowManager",lpparam.classLoader),
		
		"adjustWindowParamsLw", 
		
		XCMHookToastClick);
		
		}catch(Throwable t){
			
			
			Utils.printf("fix toast error:"+t.getMessage());
			
		}

		
	}
	
	public interface OnFanyiListener{
		
		void OnSuccess(String result);
		
		void OnFailed();
		
	}

	
	private XposedPresenter xpPresenter;
	
	private XSharedPreferences xPrefs;
	
	@Override
	public void handleLoadPackage(XC_LoadPackage.LoadPackageParam lpparam)
	{
		// TODO: Implement this method
		
		try{
		
		xpPresenter=new XposedPresenterImpl(this);
		
		xPrefs=new XSharedPreferences(AllResources.pkgname, AllResources.sharename);
		
		hookXposedActive(lpparam);
		
		hookSetPrimaryClip(lpparam);
		
		//hookInput(lpparam);
		
		fixToastClick(lpparam);
		
		}catch(Throwable t){
			
			Utils.printf("global error:"+AppUtils.getStackTrace(t));
			
		}
		
	}

}
