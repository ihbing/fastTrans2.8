package com.mhook.MrSFastTranslation.mvp;
import android.view.*;
import android.widget.*;

public interface TransPresenter
{
	
	void onClicked(View view);
	
	void onProgressChanged(SeekBar p1, int p2, boolean p3);
	
	void onCheckedChanged(CompoundButton p1, boolean p2);
	
	void onLogAlertCopyClicked(String logStr);
	
	void onLogAlertClearClicked();
	
	void initSettings();
	
	Boolean isBrightTheme();
	
	void setShowTime(int time);
	
	int getShowTime();
	
	void setShowTransparency(int transparency);
	
	int getShowTransparency();
	
	String getOnInputPkgName();
	
	void onClickRebootInput();
	
	void onItemSelectInput(String inputPkgName);
	
}
