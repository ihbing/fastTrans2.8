package com.mhook.MrSFastTranslation.mvp;
import android.view.*;
import android.content.*;

public interface XposedView
{
	
	void showWindow(String fanyiData);
	
	void hideWindow();
	
	Boolean isShowingWindow();
	
	public void setWindowIsClickAbled(boolean isClickAbled);

	public boolean isWindowClickAbled();
	
	void setShowTime(int showTime);
	
	void setShowTransparency(int transparency);
	
	void setIsShowBottomBtn(boolean isShow);
	
	void updateWindow();
	
    void setMParams(WindowManager.LayoutParams mParams);
	
	WindowManager.LayoutParams getMParams();
	
	void saveWindowXY(int windowX,int windowY);
	
	void showInfo(String msg);
	
	void copyFanyiStrToClipData();
	
	void createWindow(Context context,int x,int y);
	
}
