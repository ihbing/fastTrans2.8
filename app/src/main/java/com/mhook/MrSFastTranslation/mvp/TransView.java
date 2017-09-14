package com.mhook.MrSFastTranslation.mvp;

public interface TransView
{
	
	void showError(String msg);
	
	void showInfo(String msg);
	
	void showWarn(String msg);
	
	void showLogAlert(String logStr);
	
	void showShowTimeSettingAlert();
	
	void showShowTransparencySettingAlert();
	
	void setWhiteListLayout(boolean isShowLayout);
	
	void setBlackListLayout(boolean isShowLayout);
	
	void setXposedActiveLayout(boolean isHideLayout);
	
	//Boolean isModuleEnabled();

	void setModuleEnabled(boolean status);

	//Boolean isHideIcon();

	void setHideIcon(boolean bool);

	void setBrightTheme(boolean bool);

	//int getShowWindowTime();

	//void setShowWindowTime(int time);

	//Boolean isWhiteListEnabled();

	void setWhiteListEnabled(boolean bool);

	String getStrWhiteList();

	void setStrWhiteList(String StrWhiteList);

	String getStrBlackList();

	void setStrBlackList(String strBlackList);

	//Boolean isBlackListEnabled();

	void setBlackListEnabled(boolean bool);

	//Boolean isDebugMode();

	void setDebugMode(boolean bool);

	Boolean isPayed();

	void setPayed(boolean bool);

	Boolean isScored();

	void setScored(boolean bool);
	
	void showRebootInputAlert();
	
}
