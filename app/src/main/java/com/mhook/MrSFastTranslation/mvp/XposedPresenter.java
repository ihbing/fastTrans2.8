package com.mhook.MrSFastTranslation.mvp;
import android.content.*;
import de.robv.android.xposed.*;
import android.view.*;

public interface XposedPresenter
{
	
	void onReceive(final Context p1, Intent p2);
	
	void beforeSetPrimaryClipHookedMethod(XC_MethodHook.MethodHookParam param);
	
	void beforeInputHookedMethod(XC_MethodHook.MethodHookParam param);
	
	Boolean onTouched(View p1, MotionEvent event);
}
