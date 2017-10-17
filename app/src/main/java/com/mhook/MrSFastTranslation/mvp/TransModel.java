package com.mhook.MrSFastTranslation.mvp;

public interface TransModel
{

	void setIsShowTurnTransBtn(boolean isShowBtn);

	Boolean getIsShowTurnTransBtn();
	
	void saveSettings();
	
	void initSettings();
	
	Boolean isModuleEnabled();
	
	void setModuleEnabled(boolean status);
	
	Boolean isHideIcon();
	
	void setHideIcon(boolean bool);
	
	Boolean isBrightTheme();
	
	void setBrightTheme(boolean bool);
	
	int getShowWindowTime();
	
	int getShowWindowTransparency();
	
	void setShowWindowTime(int time);
	
	void setShowWindowTransparency(int transparency);
	
	Boolean isWhiteListEnabled();
	
	void setWhiteListEnabled(boolean bool);
	
	String getStrWhiteList();
	
	void setStrWhiteList(String StrWhiteList);
	
	String getStrBlackList();
	
	void setStrBlackList(String strBlackList);
	
	Boolean isBlackListEnabled();
	
	void setBlackListEnabled(boolean bool);
	
	Boolean isDebugMode();
	
	void setDebugMode(boolean bool);
	
	Boolean isPayed();
	
	void setPayed(boolean bool);
	
	Boolean isScored();
	
	void setScored(boolean bool);
	
	void openAliay();
	
	void openAppMarket();
	
	String getRunningLog();
	
	void JoinTestGroup();
	
	Boolean isXposedActive();
	
	Boolean isFirstRun();
	
	void setFirstRun(boolean bool);
	
	void clearSettings();
	
	Boolean clearLog();
	
	Boolean isRooted();
	
	void startGetLog();
	
	void stopGetLog();
	
	void setOnInputPkgName(String pkgName);
	
	String getOnInputPkgName();
	
    Boolean rebootOnInput();
	
}
