package com.mhook.MrSFastTranslation.factory;

public interface TransFactory
{
	
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

}
