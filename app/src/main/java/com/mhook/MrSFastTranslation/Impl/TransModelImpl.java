package com.mhook.MrSFastTranslation.Impl;
import com.mhook.MrSFastTranslation.mvp.*;

import android.content.*;

import com.mhook.MrSFastTranslation.AllResources;
import com.mhook.MrSFastTranslation.MyApplication;
import com.mhook.MrSFastTranslation.Utils.*;

import android.os.*;
import android.text.*;

public class TransModelImpl implements TransModel
{

	@Override
	public int getShowWindowTransparency()
	{
		// TODO: Implement this method
		return showWindowTransparency;
	}

	@Override
	public void setShowWindowTransparency(int transparency)
	{
		// TODO: Implement this method
		
		this.showWindowTransparency=transparency;
		
	}


	@Override
	public Boolean rebootOnInput()
	{
		// TODO: Implement this method
		
		if(TextUtils.isEmpty(onInputPkgName)){
			
			Utils.printf("重启失败,输入法未知");
			
			return false;
			
		}
		
		return AppUtils.reStartApp(MyApplication.getContext(),onInputPkgName);
		
	}


	@Override
	public void setOnInputPkgName(String pkgName)
	{
		// TODO: Implement this method
		
		this.onInputPkgName=pkgName;
		
	}

	@Override
	public String getOnInputPkgName()
	{
		// TODO: Implement this method
		return this.onInputPkgName;
		
	}



	private int showWindowTime=3;
	
	private int showWindowTransparency=50;

	private boolean isModuleEnabled=false;
	
	private boolean isHideIcon=false;
	
	private boolean isBrightTheme=false;
	
	private boolean isWhiteListEnabled=false;
	
	private boolean isBlackListEnabled=false;
	
	private boolean isDebugMode=false;
	
	private boolean isPayed=false;
	
	private boolean isScored=false;

	private boolean isShowTurnTransBtn=true;
	
	private String strBlackList="[%@&#$],％,＆,＄,＃";
	
	private String strWhiteList="[\\u4E00-\\u9FA5|\\uAC00-\\uD7A3|\\u3130-\\u318f\\u0800-\\u4e00],[a-zA-Z 0-9],[_?:;.\"()'!，。？！：；……·（）‘’“””\\[\\]《》、～——『』〔〕｛｝【】〖〗「」\\r\\n\\-]";
	
	private String prefName="fyXposed";
	
	private String prefFirstRun="firstRun";
	
	public static String jsonSwhModuleEnabled="moduleEnabled";
	
	private String prefSwhHideIcon="hideIcon";
	
	private String prefSwhBrightTheme="brightTheme";
	
	public static String jsonSwhWhiteListEnabled="whiteListEnabled";
	
	public static String jsonSwhBlackListEnabled="blackListEnabled";
	
	public static String jsonSwhDebugMode="debugMode";
	
	public static String jsonEdtWhiteList="edtWhiteList";
	
	public static String jsonEdtBlackList="edtBlackList";
	
	public static String jsonShowWindowTime="showWindowTime";
	
	public static String prefOnInputPkgName="onInputPkgName";
	
	public static String jsonShowWindowTransparency="showWindowTransparency";

	public static String jsonIsShowTurnTransBtn="isShowTurnTransBtn";
	
	private String prefIsPayed="isPayed";
	
	private String prefIsScored="isScored";
	
	private String onInputPkgName="";
	
	private SharedPreferences prefs;
	
	private TransPresenter transPreaenter;
	
	private JsonRW.JObject jObject;
	
	private Thread threadGetLog=null;
	
	public TransModelImpl(TransPresenter transPresenter){
		
		try{
			
		if(transPresenter==null)return;
			
		prefs=MyApplication.getContext().getSharedPreferences(prefName,Context.MODE_WORLD_READABLE);
		
		jObject=JsonRW.JObject.getInstance();
		
		this.transPreaenter=transPresenter;
		
		initSettings();
		
		}catch(Throwable t){
			
			
		}
		
	}
	
	public void initSettings(){
		
		try{
		
		isModuleEnabled=jObject.getBoolean(jsonSwhModuleEnabled);
		
		isDebugMode=jObject.getBoolean(jsonSwhDebugMode);
		
		strWhiteList=jObject.getString(jsonEdtWhiteList);
		
		strBlackList=jObject.getString(jsonEdtBlackList);
		
		isWhiteListEnabled=jObject.getBoolean(jsonSwhWhiteListEnabled);
		
		isBlackListEnabled=jObject.getBoolean(jsonSwhBlackListEnabled);
		
		showWindowTime=jObject.getInt(jsonShowWindowTime);
		
		showWindowTransparency=jObject.getInt(jsonShowWindowTransparency);

		isShowTurnTransBtn=jObject.getBoolean(jsonIsShowTurnTransBtn);
		
		}catch(Throwable t){
			
			
			
		}
		
		isBrightTheme=prefs.getBoolean(prefSwhBrightTheme,false);
		
		isHideIcon=prefs.getBoolean(prefSwhHideIcon,false);
		
		onInputPkgName=prefs.getString(prefOnInputPkgName,"");
		
		if(isDebugMode)startGetLog();
		
	}

	@SuppressWarnings("deprecation")
	@Override
	public void stopGetLog()
	{
		// TODO: Implement this method
		
		try{

		if(null!=threadGetLog)threadGetLog.stop();
		
		threadGetLog=null;
		
		}catch(Throwable t){
			
			threadGetLog.interrupt();
			
		}
	}

	@Override
	public void startGetLog()
	{
		// TODO: Implement this method
		
try{
		
		if(null!=threadGetLog){
			
			threadGetLog.start();
			
			return;
			
			}
		
		threadGetLog=  new Thread(){

			public void run(){

				ShellUtils.execCommand("logcat -s fanyi -v long", true, new ShellUtils.OnExecResultInfoListener(){

						@Override
						public void OnResult(String result)
						{
							// TODO: Implement this method

							FileUtils.WriteFileNoCover(getLogPath(),result+"\n");

						}
					});

			}

		};
		
		threadGetLog.start();
		
		}catch(Throwable t){
			
			Utils.printf(t.getMessage());
			
		}

	}

	@Override
	public Boolean isRooted()
	{
		// TODO: Implement this method
		return ShellUtils.checkRootPermission();
	}


	@Override
	public Boolean clearLog()
	{

		ShellUtils.CommandResult  CommandResult2= ShellUtils.execCommand("logcat -c",true);
		
		boolean isClearLog=FileUtils.WriteFileCover(getLogPath(),"");

		if((CommandResult2.result==0)&isClearLog)return true;

		return false;

	}


	@Override
	public void clearSettings()
	{
		// TODO: Implement this method

		if(null==prefs)return;

		prefs.edit().clear().commit();

	}


	@Override
	public void setFirstRun(boolean bool)
	{
		// TODO: Implement this method

		if(null==prefs)return;

		prefs.edit().putBoolean(prefFirstRun,bool).commit();

	}
	
	@Override
	public Boolean isFirstRun()
	{
		// TODO: Implement this method
		
		if(prefs.getBoolean(prefFirstRun,true)){
			
			return true;
			
		}
		
		return false;
	}


	@Override
	public void setIsShowTurnTransBtn(boolean isShowBtn) {this.isShowTurnTransBtn=isShowBtn;}

	@Override
	public Boolean getIsShowTurnTransBtn() {return this.isShowTurnTransBtn;}

	@Override
	public void saveSettings()
	{
		// TODO: Implement this method
		
		if(null==prefs)return;
		
		SharedPreferences.Editor editor=prefs.edit();
		
		//editor.putBoolean(prefSwhModuleEnabled,isModuleEnabled);
		
		editor.putBoolean(prefSwhHideIcon,isHideIcon);
		
		editor.putBoolean(prefSwhBrightTheme,isBrightTheme);
		
		//editor.putBoolean(prefSwhWhiteListEnabled,isWhiteListEnabled);
		
		//editor.putBoolean(prefSwhBlackListEnabled,isBlackListEnabled);
		
		//editor.putBoolean(prefSwhDebugMode,isDebugMode);
		
		editor.putBoolean(prefIsPayed,isPayed);
		
		editor.putBoolean(prefIsScored,isScored);
		
		//editor.putInt(prefShowWindowTime,showWindowTime);
		
		//editor.putString(prefEdtWhiteList,strWhiteList);
		
		//editor.putString(prefEdtBlackList,strBlackList);
		
		editor.putString(prefOnInputPkgName,onInputPkgName);
		
		editor.commit();
		
		try{
		
		jObject.put(jsonSwhModuleEnabled,isModuleEnabled);
		
		jObject.put(jsonSwhDebugMode,isDebugMode);
		
		jObject.put(jsonSwhWhiteListEnabled,isWhiteListEnabled);
		
		jObject.put(jsonSwhBlackListEnabled,isBlackListEnabled);
		
		jObject.put(jsonShowWindowTime,showWindowTime);
		
		jObject.put(jsonEdtWhiteList,strWhiteList);
		
		jObject.put(jsonEdtBlackList,strBlackList);
		
		jObject.put(jsonShowWindowTransparency,showWindowTransparency);

		jObject.put(jsonIsShowTurnTransBtn,isShowTurnTransBtn);
		
		jObject.save();
		
		}catch(Throwable t){
			
			
		}
		
		
	}

	@Override
	public Boolean isModuleEnabled()
	{
		// TODO: Implement this method
		
		return isModuleEnabled;
		
	}

	@Override
	public void setModuleEnabled(boolean status)
	{
		// TODO: Implement this method
		
		isModuleEnabled=status;
		
	}

	@Override
	public Boolean isHideIcon()
	{
		// TODO: Implement this method
		return isHideIcon;
	}

	@Override
	public void setHideIcon(boolean bool)
	{
		// TODO: Implement this method
		
		isHideIcon=bool;
		
	}

	@Override
	public Boolean isBrightTheme()
	{
		// TODO: Implement this method
		return isBrightTheme;
	}

	@Override
	public void setBrightTheme(boolean bool)
	{
		// TODO: Implement this method
		
		isBrightTheme=bool;
		
	}

	@Override
	public int getShowWindowTime()
	{
		// TODO: Implement this method
		return showWindowTime;
	}

	@Override
	public void setShowWindowTime(int time)
	{
		// TODO: Implement this method
		
		showWindowTime=time;
		
	}

	@Override
	public Boolean isWhiteListEnabled()
	{
		// TODO: Implement this method
		return isWhiteListEnabled;
	}

	@Override
	public void setWhiteListEnabled(boolean bool)
	{
		// TODO: Implement this method
		
		isWhiteListEnabled=bool;
		
	}

	@Override
	public String getStrWhiteList()
	{
		// TODO: Implement this method
		return strWhiteList;
	}

	@Override
	public void setStrWhiteList(String strWhiteList)
	{
		// TODO: Implement this method
		
		this.strWhiteList=strWhiteList;
		
	}

	@Override
	public String getStrBlackList()
	{
		// TODO: Implement this method
		return strBlackList;
	}

	@Override
	public void setStrBlackList(String strBlackList)
	{
		// TODO: Implement this method
		
		this.strBlackList=strBlackList;
		
	}

	@Override
	public Boolean isBlackListEnabled()
	{
		// TODO: Implement this method
		return isBlackListEnabled;
	}

	@Override
	public void setBlackListEnabled(boolean bool)
	{
		// TODO: Implement this method
		
		isBlackListEnabled=bool;
		
	}

	@Override
	public Boolean isDebugMode()
	{
		// TODO: Implement this method
		return isDebugMode;
	}

	@Override
	public void setDebugMode(boolean bool)
	{
		// TODO: Implement this method
		
		isDebugMode=bool;
		
	}

	@Override
	public Boolean isPayed()
	{
		// TODO: Implement this method
		return isPayed;
	}

	@Override
	public void setPayed(boolean bool)
	{
		// TODO: Implement this method
		
		isPayed=bool;
		
	}

	@Override
	public Boolean isScored()
	{
		// TODO: Implement this method
		return isScored;
	}

	@Override
	public void setScored(boolean bool)
	{
		// TODO: Implement this method
		
		isScored=bool;
		
	}

	@Override
	public void openAliay()
	{
		// TODO: Implement this method
		
		IntentUtils.OpenAlipay(MyApplication.getContext(),AllResources.alipayurl);
		
	}

	@Override
	public void openAppMarket()
	{
		// TODO: Implement this method
		
		IntentUtils.OpenMarket(MyApplication.getContext(),AllResources.pkgname);
	}

	@Override
	public String getRunningLog()
	{
		// TODO: Implement this method
		return FileUtils.ReadFile(getLogPath());
	}

	@Override
	public void JoinTestGroup()
	{
		// TODO: Implement this method
		
		IntentUtils.OpenQQGroup(MyApplication.getContext(),AllResources.QQgroup);
		
	}

	@Override
	public Boolean isXposedActive()
	{
		// TODO: Implement this method
		return false;
	}
	
	String getLogPath(){
		
		
		return Environment.getDataDirectory().getAbsolutePath()+

			"/data/"+

			AllResources.pkgname+

			"/files/FanyiDebugLog.txt";
	}

}
