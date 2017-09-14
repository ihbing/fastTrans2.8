package com.mhook.MrSFastTranslation.mvp;
import de.robv.android.xposed.callbacks.*;
import android.content.*;
import de.robv.android.xposed.*;
import com.mhook.MrSFastTranslation.Impl.*;

public interface XposedModel
{
	
	void hookXposedActive( final XC_LoadPackage.LoadPackageParam lpparam);
	
	void hookSetPrimaryClip(final XC_LoadPackage.LoadPackageParam lpparam);
	
	Context getOnFanyiContext(XC_MethodHook.MethodHookParam param);
	
	Boolean fixSetPrimaryClipProblem(Context con);
	
	ClipData getClipData(XC_MethodHook.MethodHookParam param);
	
	String getToLanguage(ClipData clipData);
	
	void SendClipDataToBroadcast(Context con,  String character,String toLanguage);
	
	void hookInput(final XC_LoadPackage.LoadPackageParam lpparam);
	
	Context getOnInputContext(XC_MethodHook.MethodHookParam param);
	
	Boolean getModuleEnabledOnReceive();
	
	void fixToastClick(final XC_LoadPackage.LoadPackageParam lpparam);
	
	String sortOutCharacter(String character);//整理字符
	
	String clearOldProblem(String character);//清除遗留问题
	
	Boolean checkCharacter(String character);//检查字符
	
	Boolean checkLabel(android.content.ClipData clipData);
	
	int[] getWindowXY(Context con);//获取窗口坐标0:x,1:y
	
	String getAimsLanguage(String toLanguage,String fanyiData);//获取目标语言
	
	String getGoogleFanyiUrl(String toLanguage,String q);//获取谷歌翻译url
	
	String getYoudaoFanyiUrl( String[] key, String toLanguage,String q);//获取有道翻译url
	
	Boolean checkCharacterLength(String character);//检测字符长度
	
	String[] getYoudaoRandomKey();//获取随机有道key
	
	void getGoogleFanyiResult(String fanyiUrl,XposedModelImpl.OnFanyiListener onFyListener);//获取谷歌翻译结果
	
	void getYoudaoFanyiResult(String fanyiUrl,XposedModelImpl.OnFanyiListener onFyListener);//获取有道翻译结果
	
	Boolean checkIsSame(String oldStr,String newStr);//过滤翻译结果与源字符相同的情况
	
	Boolean checkPremission(Context con);//检查权限
	
	void receiverBroad(Context con);//注册广播
	
	int getShowTime();
	
	int getShowWindowTransparency();
	
	Boolean isShowedWindow();
	
	void setIsShowedWindow(boolean isShowed);
	
}
